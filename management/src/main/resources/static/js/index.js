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
            $("#returnRootNetworkButton").show();
            $("#addNetworkButton").hide();
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