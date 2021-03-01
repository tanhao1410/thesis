package com.github.tanhao1410.thesis.client.handler;

import com.alibaba.fastjson.JSON;
import com.github.tanhao1410.thesis.client.comm.ClientChannelManagement;
import com.github.tanhao1410.thesis.client.task.MonitoringThreadManagment;
import com.github.tanhao1410.thesis.client.utils.SystemInfoUtils;
import com.github.tanhao1410.thesis.protocol.MessageProtocolInfo;
import com.github.tanhao1410.thesis.protocol.MessageTypeEnum;
import com.github.tanhao1410.thesis.protocol.TCPProtocolConstant;
import com.github.tanhao1410.thesis.protocol.bean.ClientInfo;
import com.github.tanhao1410.thesis.protocol.bean.MonitoringConfig;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.util.List;

public class ClientHandler extends SimpleChannelInboundHandler<MessageProtocolInfo.MessageProtocol> {

    //当通道就绪就会触发该方法
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {

        //client端启动后，会连接服务端，并上报自身信息，包括ip，端口

        //随机的发送Student 或者 Workder 对象
        MessageProtocolInfo.MessageProtocol myMessage = null;

        //封装设备基本信息
        final ClientInfo client = new ClientInfo();
        client.setOperationSystem(SystemInfoUtils.getOperationSystem());
        client.setManufacturer(SystemInfoUtils.getManufacturer());
        final String clientInfoStr = JSON.toJSONString(client);

        myMessage = MessageProtocolInfo.MessageProtocol.newBuilder()
                .setHead(TCPProtocolConstant.HEAD)
                .setContent(clientInfoStr)
                .setLen(clientInfoStr.length())
                .setType(MessageTypeEnum.CLIENT_INFO.getId())
                .build();

        ClientChannelManagement.initCTX(ctx);

        ctx.writeAndFlush(myMessage);
    }


    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, MessageProtocolInfo.MessageProtocol messageProtocol) throws Exception {
        //根据dataType 来显示不同的信息
        System.out.println(messageProtocol.getContent());

        if(MessageTypeEnum.MONITORING_CONFIG.getId() == messageProtocol.getType() ){
            System.out.println("收到服务端下发配置数据："+ messageProtocol.getContent());
            final List<MonitoringConfig> monitoringConfigs = JSON.parseArray(messageProtocol.getContent(), MonitoringConfig.class);
            MonitoringThreadManagment.startMonitoringThread(monitoringConfigs);
        }else{
            System.out.println("服务端下发了错误信息");
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.close();
    }
}
