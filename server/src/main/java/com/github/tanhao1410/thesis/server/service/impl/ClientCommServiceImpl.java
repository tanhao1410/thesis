package com.github.tanhao1410.thesis.server.service.impl;

import com.alibaba.fastjson.JSON;
import com.github.tanhao1410.thesis.common.domain.DeviceDO;
import com.github.tanhao1410.thesis.common.mapper.DeviceDOMapper;
import com.github.tanhao1410.thesis.protocol.MessageProtocolInfo;
import com.github.tanhao1410.thesis.protocol.MessageTypeEnum;
import com.github.tanhao1410.thesis.protocol.TCPProtocolConstant;
import com.github.tanhao1410.thesis.protocol.bean.MonitoringConfig;
import com.github.tanhao1410.thesis.server.comm.ClientChannelManagment;
import com.github.tanhao1410.thesis.server.service.ClientCommService;
import com.github.tanhao1410.thesis.server.service.MonitoringConfigService;
import io.netty.channel.ChannelHandlerContext;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author tanhao
 * @date 2021/03/01 13:47
 */
@Service
public class ClientCommServiceImpl implements ClientCommService {

    @Resource
    private MonitoringConfigService monitoringConfigService;

    @Resource
    private DeviceDOMapper deviceDOMapper;

    @Override
    public void sendMonitoringConfig(Long deviceId) {

        final DeviceDO deviceDO = deviceDOMapper.selectByPrimaryKey(deviceId);
        if (deviceDO != null){
            //查询出该设备所有的监控项
            final List<MonitoringConfig> configs = monitoringConfigService.getMonitoringConfigsByDeviceId(deviceId);

            String content = JSON.toJSONString(configs);
            final MessageProtocolInfo.MessageProtocol configMsg = MessageProtocolInfo.MessageProtocol.newBuilder()
                    .setHead(TCPProtocolConstant.HEAD)
                    .setContent(content)
                    .setLen(content.length())
                    .setType(MessageTypeEnum.MONITORING_CONFIG.getId())
                    .build();

            String channelName = deviceDO.getIp() + ":" + deviceDO.getPort();
            //String channelName = deviceDO.getIp();

            final ChannelHandlerContext ctx = ClientChannelManagment.getClientChannelByName(channelName);
            ctx.writeAndFlush(configMsg);
        }
    }
}
