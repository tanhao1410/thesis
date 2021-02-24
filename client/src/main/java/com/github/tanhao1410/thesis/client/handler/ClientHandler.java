package com.github.tanhao1410.thesis.client.handler;

import com.github.tanhao1410.thesis.protocol.MessageProtocolInfo;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

public class ClientHandler extends SimpleChannelInboundHandler<MessageProtocolInfo.MessageProtocol> {

    //当通道就绪就会触发该方法
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {

        //随机的发送Student 或者 Workder 对象
        MessageProtocolInfo.MessageProtocol myMessage = null;

        myMessage = MessageProtocolInfo.MessageProtocol.newBuilder()
                .setHead(111)
                .setContent("dddd")
                .setLen(15)
                .setType(1)
                .build();

        ctx.writeAndFlush(myMessage);
    }


    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, MessageProtocolInfo.MessageProtocol messageProtocol) throws Exception {
        //根据dataType 来显示不同的信息
        System.out.println(messageProtocol.getContent());
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
