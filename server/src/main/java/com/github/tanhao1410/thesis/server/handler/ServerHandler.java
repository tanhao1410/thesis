package com.github.tanhao1410.thesis.server.handler;

import com.github.tanhao1410.thesis.protocol.MessageProtocolInfo;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.CharsetUtil;

/*
说明
1. 我们自定义一个Handler 需要继续netty 规定好的某个HandlerAdapter(规范)
2. 这时我们自定义一个Handler , 才能称为一个handler
 */

public class ServerHandler extends SimpleChannelInboundHandler<MessageProtocolInfo.MessageProtocol> {


    //数据读取完毕
    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {

        MessageProtocolInfo.MessageProtocol myMessage = null;


        myMessage = MessageProtocolInfo.MessageProtocol.newBuilder()
                .setHead(111)
                .setContent("eeee")
                .setLen(15)
                .setType(1)
                .build();


        ctx.writeAndFlush(myMessage);
    }

    //处理异常, 一般是需要关闭通道

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.close();
    }

    //读取数据实际(这里我们可以读取客户端发送的消息)
    /*
    1. ChannelHandlerContext ctx:上下文对象, 含有 管道pipeline , 通道channel, 地址
    2. Object msg: 就是客户端发送的数据 默认Object
     */
    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, MessageProtocolInfo.MessageProtocol messageProtocol) throws Exception {
        //根据dataType 来显示不同的信息
        System.out.println(messageProtocol.getContent());
    }
}
