package com.github.tanhao1410.thesis.server.handler;

import com.alibaba.fastjson.JSON;
import com.github.tanhao1410.thesis.common.domain.AlarmDO;
import com.github.tanhao1410.thesis.common.domain.DeviceDO;
import com.github.tanhao1410.thesis.common.domain.HistoryAlarmDO;
import com.github.tanhao1410.thesis.common.domain.MonitoringDataDO;
import com.github.tanhao1410.thesis.common.mapper.AlarmDOMapper;
import com.github.tanhao1410.thesis.common.mapper.DeviceDOMapper;
import com.github.tanhao1410.thesis.common.mapper.HistoryAlarmDOMapper;
import com.github.tanhao1410.thesis.common.mapper.MonitoringDataDOMapper;
import com.github.tanhao1410.thesis.mq.MQConstant;
import com.github.tanhao1410.thesis.mq.RedisService;
import com.github.tanhao1410.thesis.protocol.MessageProtocolInfo;
import com.github.tanhao1410.thesis.protocol.MessageTypeEnum;
import com.github.tanhao1410.thesis.protocol.TCPProtocolConstant;
import com.github.tanhao1410.thesis.protocol.bean.ClientInfo;
import com.github.tanhao1410.thesis.protocol.bean.MonitoringAlarm;
import com.github.tanhao1410.thesis.protocol.bean.MonitoringData;
import com.github.tanhao1410.thesis.server.spring.SpringBeanManagement;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.timeout.IdleStateEvent;
import org.springframework.data.domain.PageRequest;

import java.net.InetSocketAddress;
import java.util.Date;
import java.util.List;

/*
说明
1. 我们自定义一个Handler 需要继续netty 规定好的某个HandlerAdapter(规范)
2. 这时我们自定义一个Handler , 才能称为一个handler
 */

public class HeartBeatHeadler extends SimpleChannelInboundHandler<MessageProtocolInfo.MessageProtocol> {

    //处理异常, 一般是需要关闭通道

    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        SpringBeanManagement.clientChannelManagment.removeClient(ctx);
        super.handlerRemoved(ctx);
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        super.channelInactive(ctx);
    }

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {

        if (evt instanceof IdleStateEvent) {
            IdleStateEvent e = (IdleStateEvent) evt;
            switch (e.state()) {
                case ALL_IDLE:
                    final InetSocketAddress socketAddress = (InetSocketAddress) ctx.channel().remoteAddress();
                    String ip = socketAddress.getAddress().getHostAddress();
                    int port = socketAddress.getPort();
                    //客户端信息上传
                    System.out.println("客户端信息上传ip :" + ip + " port : " + port + " 长时间未有读写请求");
                    break;
                default:
                    break;
            }
        }
        super.userEventTriggered(ctx, evt);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        SpringBeanManagement.clientChannelManagment.removeClient(ctx);
        ctx.close();
    }

    //读取数据实际(这里我们可以读取客户端发送的消息)
    /*
    1. ChannelHandlerContext ctx:上下文对象, 含有 管道pipeline , 通道channel, 地址
    2. Object msg: 就是客户端发送的数据 默认Object
     */
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, MessageProtocolInfo.MessageProtocol msg) throws Exception {
        if(msg.getType() == MessageTypeEnum.PING.getId()){

            //得到client的ip 和端口号
            final InetSocketAddress socketAddress = (InetSocketAddress) ctx.channel().remoteAddress();
            String ip = socketAddress.getAddress().getHostAddress();
            int port = socketAddress.getPort();
            //客户端信息上传
            System.out.println("客户端ip :" + ip + " port : " + port + " 发送ping消息");

            //回复pong
            final MessageProtocolInfo.MessageProtocol configMsg = MessageProtocolInfo.MessageProtocol.newBuilder()
                    .setHead(TCPProtocolConstant.HEAD)
                    .setContent("")
                    .setLen(0)
                    .setType(MessageTypeEnum.PONG.getId())
                    .build();
            ctx.writeAndFlush(configMsg);
        }
        ctx.fireChannelRead(msg);
    }
}
