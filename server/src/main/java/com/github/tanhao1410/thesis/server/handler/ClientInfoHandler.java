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
import com.github.tanhao1410.thesis.protocol.bean.ClientInfo;
import com.github.tanhao1410.thesis.protocol.bean.MonitoringAlarm;
import com.github.tanhao1410.thesis.protocol.bean.MonitoringData;
import com.github.tanhao1410.thesis.server.spring.SpringBeanManagement;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.springframework.data.domain.PageRequest;

import java.net.InetSocketAddress;
import java.util.Date;
import java.util.List;

/**
 * 处理客户端信息上报信息
 */
public class ClientInfoHandler extends SimpleChannelInboundHandler<MessageProtocolInfo.MessageProtocol> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, MessageProtocolInfo.MessageProtocol msg) throws Exception {
        //客户端信息上报
        if (msg.getType() == MessageTypeEnum.CLIENT_INFO.getId()) {

            final ClientInfo clientInfo = JSON.parseObject(msg.getContent(), ClientInfo.class);
            //得到client的ip 和端口号
            final InetSocketAddress socketAddress = (InetSocketAddress) ctx.channel().remoteAddress();
            String ip = socketAddress.getAddress().getHostAddress();
            int port = socketAddress.getPort();
            //客户端信息上传
            System.out.println("客户端信息上传ip :" + ip + " port : " + port);

            //将客户端放入管理类中管理
            SpringBeanManagement.clientChannelManagment.clientChannelJoin(ctx, clientInfo);

            final DeviceDOMapper deviceDOMapper = SpringBeanManagement.deviceDOMapper;
            DeviceDO queryDo = new DeviceDO();
            queryDo.setIp(ip);
            queryDo.setPort(port);
            final List<DeviceDO> deviceDOS = deviceDOMapper.selectPageSelective(queryDo, new PageRequest(0, 1, null));
            if (deviceDOS != null && deviceDOS.size() > 0) {
                //下发配置信息
                SpringBeanManagement.clientCommService.sendMonitoringConfig(deviceDOS.get(0).getId());
            }
        }
        ctx.fireChannelRead(msg);
    }
}
