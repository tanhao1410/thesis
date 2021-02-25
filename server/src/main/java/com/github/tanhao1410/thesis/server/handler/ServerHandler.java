package com.github.tanhao1410.thesis.server.handler;

import com.alibaba.fastjson.JSON;
import com.github.tanhao1410.thesis.common.mapper.AlarmDOMapper;
import com.github.tanhao1410.thesis.common.mapper.HistoryAlarmDOMapper;
import com.github.tanhao1410.thesis.common.mapper.MonitoringDataDOMapper;
import com.github.tanhao1410.thesis.mq.MQConstant;
import com.github.tanhao1410.thesis.mq.RedisService;
import com.github.tanhao1410.thesis.protocol.MessageProtocolInfo;
import com.github.tanhao1410.thesis.protocol.MessageTypeEnum;
import com.github.tanhao1410.thesis.protocol.bean.ClientInfo;
import com.github.tanhao1410.thesis.server.comm.ClientChannelManagment;
import com.github.tanhao1410.thesis.server.spring.SpringBeanManagement;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.net.InetSocketAddress;

/*
说明
1. 我们自定义一个Handler 需要继续netty 规定好的某个HandlerAdapter(规范)
2. 这时我们自定义一个Handler , 才能称为一个handler
 */

public class ServerHandler extends SimpleChannelInboundHandler<MessageProtocolInfo.MessageProtocol> {

    //处理异常, 一般是需要关闭通道

    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        ClientChannelManagment.removeClient(ctx);
        super.handlerRemoved(ctx);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ClientChannelManagment.removeClient(ctx);
        ctx.close();
    }

    //读取数据实际(这里我们可以读取客户端发送的消息)
    /*
    1. ChannelHandlerContext ctx:上下文对象, 含有 管道pipeline , 通道channel, 地址
    2. Object msg: 就是客户端发送的数据 默认Object
     */
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, MessageProtocolInfo.MessageProtocol msg) throws Exception {
        //根据dataType 来显示不同的信息

        //客户端信息上报
        if(msg.getType() == MessageTypeEnum.CLIENT_INFO.getId()){

            final ClientInfo clientInfo = JSON.parseObject(msg.getContent(), ClientInfo.class);
            //得到client的ip 和端口号
            final InetSocketAddress socketAddress = (InetSocketAddress)ctx.channel().remoteAddress();
            String ip = socketAddress.getAddress().getHostAddress();
            int port = socketAddress.getPort();
            System.out.println(clientInfo.getOperationSystem());
            System.out.println(clientInfo.getManufacturer());
            System.out.println(ip);
            System.out.println(port);

            //将客户端放入管理类中管理
            ClientChannelManagment.clientChannelJoin(ctx,clientInfo);

        }else if(msg.getType() == MessageTypeEnum.MONITORING_ALARM.getId()){

            //
            final AlarmDOMapper alarmDOMapper = SpringBeanManagement.alarmDOMapper;
            final HistoryAlarmDOMapper historyAlarmDOMapper = SpringBeanManagement.historyAlarmDOMapper;
            //将客户端传过来的告警消息转换成数据库中的形式

            //查询是否和当前告警一样，如果一样，则跳过即可。
            //
            //不一样，更新告警，更新历史告警

            //更新历史告警

            //通知management系统，告警发生变更
            final RedisService redisService = SpringBeanManagement.redisService;
            String alarmMsg = "";
            redisService.pubMessage(MQConstant.ALARM_CHANGE_MESSAGE_NAME,alarmMsg);


        }else if(msg.getType() == MessageTypeEnum.MONITORING_DATA.getId()){

            final MonitoringDataDOMapper monitoringDataDOMapper = SpringBeanManagement.monitoringDataDOMapper;

            //转换成数据库格式，直接保存即可

        }
    }
}
