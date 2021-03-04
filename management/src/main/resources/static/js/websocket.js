var ws;
function webSocketInit(url){
    ws = new WebSocket(url);
    ws.onopen = function(evn){
    };

    ws.onmessage = function(evn){
        var data  = $.parseJSON(evn.data);

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
    };

    ws.onclose = function(){
    };

};
