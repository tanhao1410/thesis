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
 * 处理客户端告警信息上报
 */
public class MonitoringDataHandler extends SimpleChannelInboundHandler<MessageProtocolInfo.MessageProtocol> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, MessageProtocolInfo.MessageProtocol msg) throws Exception {
        //根据dataType 来显示不同的信息
        if (msg.getType() == MessageTypeEnum.MONITORING_DATA.getId()) {
            final MonitoringDataDOMapper monitoringDataDOMapper = SpringBeanManagement.monitoringDataDOMapper;
            MonitoringData monitoringData = JSON.parseObject(msg.getContent(), MonitoringData.class);
            //转换成数据库格式，直接保存即可
            MonitoringDataDO dataDO = new MonitoringDataDO();
            dataDO.setDeviceId(monitoringData.getDeviceId());
            dataDO.setName(monitoringData.getName());
            dataDO.setItemId(monitoringData.getItemId());
            dataDO.setStartTime(new Date(monitoringData.getTime()));
            dataDO.setValue(monitoringData.getValue());
            //存入数据库
            monitoringDataDOMapper.insert(dataDO);
        }
        ctx.fireChannelRead(msg);
    }
}
