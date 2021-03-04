var canvas;
var stage;
var scene;
$(document).ready(function () {

    //拓扑图初始化
    canvas = document.getElementById('canvas');
    stage = new JTopo.Stage(canvas);
    scene = new JTopo.Scene();
    stage.mode = "select";
    scene.mode = "select";
    scene.background = '../img/bg.jpg';
    stage.add(scene);
    //初始化网络和设备拓扑图
    initNetwork();


});

function initNetwork() {

    //菜单隐藏和调整
    //$("#returnRootNetworkButton").hide();
    //$("#addNetworkButton").show();

    //清空拓扑图，以备重新绘制
    scene.clear();

    //获取所有的网络
    $.ajax({
        type: "GET",
        dataType: "json",
        url: "/group",
        data: "",
        success: function (dataResult, textStatus) {
            for (var i = 0; i < dataResult.length; i++) {
                var node = createNetwork(dataResult[i]);
                scene.add(node);
            }

            //展示设备组的告警
            $.ajax({
                type: "GET",
                dataType: "json",
                url: "/alarm/groupAlarmInfo",
                success: function (data) {

                    for (var i = 0; i < data.length; i++) {
                        var group = getGroupById(data[i].groupId);

                        if (group != undefined) {
                            setGroupAlarm(group, data[i].maxLevel);
                            group.alarmInfo = data[i]
                        }
                    }
                },
                error: function (data) {
                    console.info(data);
                }
            });
        },
        error: function (data) {
            console.info(data);
        }
    });

}

function createNetwork(data) {
    var node = new JTopo.Node(data.name);
    node.groupName = data.name;
    node.setImage('../img/group.png', true);
    node.setLocation(parseInt(data.x), parseInt(data.y));
    node.id = data.id;
    node.type = "group";
    node.addEventListener("dbclick", networkDBclick);

    //鼠标移入事件
    node.addEventListener("mouseover", function (event) {

        console.info(this.alarmInfo)
        if (this.alarmInfo != undefined) {
            $("#groupName").text("设备组名称：" + this.groupName);
            $("#deviceNum").text("总设备数量：" + this.alarmInfo.deviceNumber + "台");
            $("#oneLevelNum").text("轻微告警数量：" + this.alarmInfo.oneLevelNumber + "个");
            $("#twoLevelNum").text("普通告警数量：" + this.alarmInfo.twoLevelNumber + "个");
            $("#threeLevelNum").text("严重告警数量：" + this.alarmInfo.threeLevelNumber + "个");
            $("#fourLevelNum").text("离线设备数量：" + this.alarmInfo.fourLevelNumber + "台");
        }

        $("#groupTip").css({
            top: event.pageY,
            left: event.pageX
        }).show();
    })

    node.addEventListener("mouseout", function () {
        $("#groupTip").hide();
    })

    return node;
}

function networkDBclick(event) {
    $("#contextmenu").hide();

    var groupId = event.target.id;

    currentNetwork = groupId;
    //清空拓扑图，以备重新绘制
    scene.clear();
    //获取根下面的所有的设备
    $.ajax({
        type: "GET",
        dataType: "json",
        url: "/device",
        data: "groupId=" + groupId,
        success: function (data) {
            for (var i = 0; i < data.length; i++) {
                var node = createNode(data[i]);
                scene.add(node);
            }

            //展示告警
            $.ajax({
                type: "GET",
                dataType: "json",
                url: "/alarm",
                data: "groupId=" + groupId,
                success: function (data) {

                    for (var key in data) {

                        var node = getNodeById(parseInt(key));
                        if (node != undefined) {
                            node.alarmInfo = data[key];
                            console.info(data[key])
                            if (data[key].length > 0) {
                                setNodeAlarm(node, data[key][0].level);
                            }
                        }
                    }

                },
                error: function (data) {
                    console.info(data);
                }
            });

        },
        error: function (data) {
            console.info(data);
        }
    });

}

function createNode(data) {
    var node = new JTopo.Node(data.name);
    node.deviceName = data.name;
    node.setImage('../img/server.png', true);
    node.setLocation(parseInt(data.x), parseInt(data.y));
    node.id = data.id;
    node.type = "node";

    //鼠标移入事件
    node.addEventListener("mouseover", function (event) {

        console.info(this.alarmInfo)
        if (this.alarmInfo != undefined && this.alarmInfo.length > 0) {
            $("#deviceTip").empty();
            var deviceName  = "<li><a>设备名称："+ this.deviceName + "</a></li>";
            $("#deviceTip").append(deviceName);
            //先删除原来的
            for (var i = 0; i < this.alarmInfo.length; i++) {
                var content = "<li><a>" + this.alarmInfo[i].itemName +":" +
                    this.alarmInfo[i].value + " 告警状态："+ getLevelName(this.alarmInfo[i].level) +"</a></li>"
                $("#deviceTip").append(content);
            }
        }

        $("#deviceTip").css({
            top: event.pageY,
            left: event.pageX
        }).show();
    })

    node.addEventListener("mouseout", function () {
        $("#deviceTip").hide();
    })


    return node;
}

//回到根
function rebackRoot() {
    initNetwork()
}

function getLevelName(level) {
    if (level == 0){
        return "正常"
    }else if (level == 1){
        return "轻微告警"
    }else if(level == 2){
        return "普通告警"
    }else if (level == 3){
        return "严重告警"
    }
    return "离线"
}

function getNodeById(id) {
    var nodes = scene.childs;
    for (var i = 0; i < nodes.length; i++) {
        if (nodes[i].id == id && nodes[i].type == "node") {
            return nodes[i];
        }
    }
}

function getGroupById(id) {
    var nodes = scene.childs;
    for (var i = 0; i < nodes.length; i++) {
        if (nodes[i].id == id & nodes[i].type == "group") {
            return nodes[i];
        }
    }
}

function setNodeAlarm(node, level) {
    if (level == 0) {
        //node.alarm = '正常';
        //node.alarmColor = '0,255,0';
    } else if (level == 1) {

        if (node.interval != undefined){
            clearInterval(node.interval)
        }

        node.interval = setInterval(function () {
            if (node.alarm == '轻微告警') {
                node.alarm = null;
            } else {
                node.alarm = '轻微告警'
                node.alarmColor = '0,0,255';
            }
        }, 600);

    } else if (level == 2) {
        if (node.interval != undefined){
            clearInterval(node.interval)
        }
        node.interval = setInterval(function () {
            if (node.alarm == '普通告警') {
                node.alarm = null;
            } else {
                node.alarm = '普通告警'
                node.alarmColor = '233,150,122';
            }
        }, 600);

    } else if (level == 3) {
        if (node.interval != undefined){
            clearInterval(node.interval)
        }
        node.interval = setInterval(function () {
            if (node.alarm == '严重告警') {
                node.alarm = null;
            } else {
                node.alarm = '严重告警'
                node.alarmColor = '255,0,0';
            }
        }, 600);

    } else if (level == 4) {
        if (node.interval != undefined){
            clearInterval(node.interval)
        }
        node.interval = setInterval(function () {
            if (node.alarm == '断线') {
                node.alarm = null;
            } else {
                node.alarm = '断线'
                node.alarmColor = '204,204,204';
            }
        }, 600);
    }
}

function setGroupAlarm(node, level) {
    if (level == 0) {
        //node.alarm = '正常';
        //node.alarmColor = '0,255,0';
    } else {

        if (node.interval != undefined){
            clearInterval(node.interval)
        }

        node.interval = setInterval(function () {
            if (node.alarm == '') {
                node.alarm = null;
            } else {
                node.alarm = ''
                node.alarmColor = '0,0,255';
            }
        }, 600);
    }
}

function saveLocation() {

    var params = [];
    //获取当前scene下的所有节点
    var nodes = scene.childs;

    for (var i = 0; i < nodes.length; i++) {
        var node = nodes[i];
        var param = {
            id: node.id,
            type: node.type,
            x: node.x,
            y: node.y
        };
        params.push(param);
    }
    //更新位置信息
    //格式：[{},{}]
    $.ajax({
        type: "POST",
        dataType: "json",
        url: "/group/saveLocation",
        data: JSON.stringify(params),
        contentType: "application/json;charset=utf-8",
        success: function (data) {
            alert("保存成功！");
        },
        error: function (data) {
            console.info(data)
        }
    });
}

function createDevice() {
    var param = {
        id: "",
        name: $("#nodeName").val(),
        locationX: Math.floor(Math.random() * 1000),
        locationY: Math.floor(Math.random() * 500),
        ip: $("#nodeIp").val(),
        password: $("#passwordInput").val(),
        networkId: currentNetwork,
    };
    $.ajax({
        type: "POST",
        dataType: "json",
        url: "/node",
        data: JSON.stringify(param),
        contentType: "application/json;charset=utf-8",
        success: function (data) {
            scene.add(createNode(data))
            $("#addNodeModal").modal("hide");
        },
        error: function (data) {
            console.info(data)
        }
    });
}

function saveTopoOfPic() {
    stage.saveImageInfo();
}

function zoomOut() {
    stage.zoomOut();
}

function zoomIn() {
    stage.zoomIn();
}

function fullScreen() {
    runPrefixMethod(stage.canvas, "RequestFullScreen")
}

function runPrefixMethod(element, method) {
    var usablePrefixMethod;
    ["webkit", "moz", "ms", "o", ""].forEach(function (prefix) {
            if (usablePrefixMethod) return;
            if (prefix === "") {
                // 无前缀，方法首字母小写
                method = method.slice(0, 1).toLowerCase() + method.slice(1);
            }
            var typePrefixMethod = typeof element[prefix + method];
            if (typePrefixMethod + "" !== "undefined") {
                if (typePrefixMethod === "function") {
                    usablePrefixMethod = element[prefix + method]();
                } else {
                    usablePrefixMethod = element[prefix + method];
                }
            }
        }
    );
}
