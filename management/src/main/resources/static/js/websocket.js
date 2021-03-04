var ws;
function webSocketInit(url){
    ws = new WebSocket(url);
    ws.onopen = function(evn){
    };

    ws.onmessage = function(evn){
        console.info(evn.data)
        if(evn.data.indexOf("[") != 0){
            var data  = $.parseJSON(evn.data);

            for(var key in data){
                var node = getNodeById(parseInt(key));
                if (node != undefined && node.type == 'node'){
                    node.alarmInfo = data[key];
                    console.info(data[key])
                    if(data[key].length > 0) {
                        setNodeAlarm(node, data[key][0].level);
                    }
                }
            }
        }else{
            //传回来的是设备组的告警信息
            var data  = $.parseJSON(evn.data);

            for (var i = 0; i < data.length; i++) {
                var group = getGroupById(data[i].groupId);

                if (group != undefined && group.type == "group") {
                    setGroupAlarm(group, data[i].maxLevel);
                    group.alarmInfo = data[i]
                }
            }
        }

    };

    ws.onclose = function(){
    };

};
