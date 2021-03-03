var canvas ;
var stage;
var scene;
$(document).ready(function(){

    //按钮绑定事件
    //buttonInit();
    console.info("......")
    //拓扑图初始化
    canvas = document.getElementById('canvas');
    stage = new JTopo.Stage(canvas);
    scene = new JTopo.Scene();
    stage.mode = "select";
    scene.mode = "select";
    scene.background = '../img/bg.jpg';
    //scene.backgroundColor='243,247,250'
    stage.add(scene);

    //初始化网络和设备拓扑图
    initNetwork();

    //webSocketInit
    webSocketInit();

});

function initNetwork() {

    //菜单隐藏和调整
    //$("#returnRootNetworkButton").hide();
    //$("#addNetworkButton").show();

    //清空拓扑图，以备重新绘制
    scene.clear();

    //获取所有的网络
    $.ajax({
        type : "GET",
        dataType : "json",
        url : "/group",
        data : "",
        success : function(dataResult, textStatus) {
            for(var i =0;i <dataResult.length;i ++){
                var node = createNetwork(dataResult[i]);
                scene.add(node);
            }
        },
        error : function(data) {
            console.info(data);
        }
    });

}

function createNetwork(data){
    var node = new JTopo.Node(data.name);
    node.setImage('../img/group.png', true);
    node.setLocation(parseInt(data.x), parseInt(data.y));
    node.id = data.id;
    node.type = "network";
    node.addEventListener("dbclick",networkDBclick);
    return node;
}

function networkDBclick(event){

    var groupId = event.target.id;

    currentNetwork =groupId;
    //清空拓扑图，以备重新绘制
    scene.clear();
    //获取根下面的所有的设备
    $.ajax({
        type : "GET",
        dataType : "json",
        url : "/device",
        data : "groupId="+groupId,
        success : function(data) {
            for(var i =0;i <data.length;i ++){
                var node = createNode(data[i]);
                scene.add(node);
            }

            //展示告警
            $.ajax({
                type : "GET",
                dataType : "json",
                url : "/alarm",
                data : "groupId="+groupId,
                success : function(data) {

                    for(var key in data){

                        var node = getNodeById(parseInt(key));
                        if (node != undefined){
                            node.alarmInfo = data[key];
                            console.info(data[key])
                            if(data[key].length > 0) {
                                setNodeAlarm(node, data[key][0].level);
                            }
                        }
                    }

                },
                error : function(data) {
                    console.info(data);
                }
            });

        },
        error : function(data) {
            console.info(data);
        }
    });

}

function createNode(data){
    var node = new JTopo.Node(data.name);
    node.setImage('../img/server.png', true);
    node.setLocation(parseInt(data.x), parseInt(data.y));
    node.id = data.id;
    node.type = "node";
    return node;
}

function getNodeById(id){
    var nodes = scene.childs;
    for(var i = 0;i < nodes.length;i ++){
        if(nodes[i].id == id){
            return nodes[i];
        }
    }
}

function setNodeAlarm(node,level){
    if(level == 0){
        //node.alarm = '正常';
        //node.alarmColor = '0,255,0';
    }else if(level == 1){

        node.interval = setInterval(function(){
            if(node.alarm == '轻微告警'){
                node.alarm = null;
            }else{
                node.alarm = '轻微告警'
                node.alarmColor = '0,0,255';
            }
        }, 600);

    }else if(level == 2){
        node.alarm = '普通告警';
        node.alarmColor = '233,150,122';
    }else if(level == 3){
        node.alarm = '严重告警';
        node.alarmColor = '255,0,0';
    }else if(level == 4){

        node.interval = setInterval(function(){
            if(node.alarm == '断线'){
                node.alarm = null;
            }else{
                node.alarm = '断线'
                node.alarmColor = '204,204,204';
            }
        }, 600);
    }
}

function saveLocation() {

    var params= [];
    //获取当前scene下的所有节点
    var nodes = scene.childs;

    for(var i = 0;i < nodes.length;i++){
        var node = nodes[i];
        var param = {
            id:node.id,
            type:node.type,
            x:node.x,
            y:node.y
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
        contentType:"application/json;charset=utf-8",
        success: function(data) {
            alert("保存成功！");
        },
        error: function(data) {
            console.info(data)
        }
    });
}
