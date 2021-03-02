package com.github.tanhao1410.thesis.server.comm;

import com.alibaba.fastjson.JSON;
import com.github.tanhao1410.thesis.common.domain.AlarmDO;
import com.github.tanhao1410.thesis.common.domain.DeviceDO;
import com.github.tanhao1410.thesis.common.domain.HistoryAlarmDO;
import com.github.tanhao1410.thesis.common.mapper.AlarmDOMapper;
import com.github.tanhao1410.thesis.common.mapper.DeviceDOMapper;
import com.github.tanhao1410.thesis.common.mapper.HistoryAlarmDOMapper;
import com.github.tanhao1410.thesis.mq.MQConstant;
import com.github.tanhao1410.thesis.mq.RedisService;
import com.github.tanhao1410.thesis.mq.bean.AlarmChangeMsg;
import com.github.tanhao1410.thesis.protocol.bean.ClientInfo;
import io.netty.channel.ChannelHandlerContext;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.net.InetSocketAddress;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * 管理客户端的连接
 *
 * @author tanhao
 * @date 2021/02/25 12:52
 */
@Component
public class ClientChannelManagment {

    @Resource
    private DeviceDOMapper deviceDOMapper;

    @Resource
    private RedisService redisService;

    @Resource
    private HistoryAlarmDOMapper historyAlarmDOMapper;

    @Resource
    private AlarmDOMapper alarmDOMapper;

    private HashMap<String, ChannelHandlerContext> clientChannelMap = new HashMap();
    private HashMap<String, ClientInfo> clientInfoMap = new HashMap();

    /**
     * 加入一个客户端连接
     *
     * @param ctx
     * @param clientInfo
     */
    public void clientChannelJoin(ChannelHandlerContext ctx, ClientInfo clientInfo) {
        //得到client的ip 和端口号
        final InetSocketAddress socketAddress = (InetSocketAddress) ctx.channel().remoteAddress();
        String ip = socketAddress.getAddress().getHostAddress();
        int port = socketAddress.getPort();
        clientChannelMap.put(ip + ":" + port, ctx);

        clientInfoMap.put(ip + ":" + port, clientInfo);

        //客户端上线，对于原来在线状态为断线的应该恢复
        DeviceDO queryDo = new DeviceDO();
        queryDo.setIp(ip);
        queryDo.setPort(port);
        final List<DeviceDO> deviceDOS = deviceDOMapper.selectPageSelective(queryDo, new PageRequest(0, 1, null));
        //设备存在于管理系统中才会产生告警
        if (deviceDOS != null && deviceDOS.size() > 0) {
            //当客户端移除时，需要产生一个断线的告警
            AlarmDO queryAlarmDo = new AlarmDO();
            queryAlarmDo.setItemId(0l);
            queryAlarmDo.setDeviceId(deviceDOS.get(0).getId());
            final List<AlarmDO> alarmDOS = alarmDOMapper.selectPageSelective(queryAlarmDo, new PageRequest(0, 1, null));
            //该设备在系统中的状态是在线 才会产生断线的告警
            if (alarmDOS != null && alarmDOS.size() > 0) {

                AlarmDO alarm = alarmDOS.get(0);

                //原来的是断线，现在在线了
                if(!alarm.getIsNormal()){
                    //由不在线-->在线，需要记录历史记录
                    HistoryAlarmDO historyAlarmDO = new HistoryAlarmDO();
                    historyAlarmDO.setValue(alarm.getValue());
                    historyAlarmDO.setStartTime(alarm.getStartTime());
                    historyAlarmDO.setItemId(alarm.getItemId());
                    historyAlarmDO.setName(alarm.getName());
                    historyAlarmDO.setEndTime(new Date(System.currentTimeMillis()));
                    historyAlarmDO.setDeviceId(alarm.getDeviceId());

                    historyAlarmDOMapper.insert(historyAlarmDO);

                    alarm.setValue("在线");
                    alarm.setIsNormal(true);
                    alarm.setStartTime(new Date(System.currentTimeMillis()));
                    alarmDOMapper.updateByPrimaryKey(alarm);

                    //通知管理系统
                    AlarmChangeMsg alarmMsg = new AlarmChangeMsg();
                    alarmMsg.setDeviceId(alarm.getDeviceId());
                    alarmMsg.setAlarmId(alarm.getId());
                    redisService.pubMessage(MQConstant.ALARM_CHANGE_MESSAGE_NAME, JSON.toJSONString(alarmMsg));
                }
            }else{
                //系统中不存在这个告警
                queryAlarmDo.setIsNormal(true);
                queryAlarmDo.setValue("在线");
                queryAlarmDo.setStartTime(new Date(System.currentTimeMillis()));
                queryAlarmDo.setName("在线状态");

                alarmDOMapper.insert(queryAlarmDo);
            }
        }

    }

    /**
     * 根据名称获取到一个连接 名称：ip+":"+port
     *
     * @param name
     * @return
     */
    public ChannelHandlerContext getClientChannelByName(String name) {
        return clientChannelMap.get(name);
    }

    /**
     * 根据名称获取到一个连接 名称：ip+":"+port
     *
     * @param name
     * @return
     */
    public ClientInfo getClientInfoByName(String name) {
        return clientInfoMap.get(name);
    }

    /**
     * 移除掉一个客户端连接
     *
     * @param ctx
     */
    public void removeClient(ChannelHandlerContext ctx) {
        //得到client的ip 和端口号
        final InetSocketAddress socketAddress = (InetSocketAddress) ctx.channel().remoteAddress();
        String ip = socketAddress.getAddress().getHostAddress();
        int port = socketAddress.getPort();

        if (clientChannelMap.containsKey(ip + ":" + port)) {
            clientChannelMap.remove(ip + ":" + port);
        }
        if (clientInfoMap.containsKey(ip + ":" + port)) {
            clientInfoMap.remove(ip + ":" + port);
        }

        DeviceDO queryDo = new DeviceDO();
        queryDo.setIp(ip);
        queryDo.setPort(port);
        final List<DeviceDO> deviceDOS = deviceDOMapper.selectPageSelective(queryDo, new PageRequest(0, 1, null));
        //设备存在于管理系统中才会产生告警
        if (deviceDOS != null && deviceDOS.size() > 0) {
            //当客户端移除时，需要产生一个断线的告警
            AlarmDO queryAlarmDo = new AlarmDO();
            queryAlarmDo.setItemId(0L);
            queryAlarmDo.setDeviceId(deviceDOS.get(0).getId());
            final List<AlarmDO> alarmDOS = alarmDOMapper.selectPageSelective(queryAlarmDo, new PageRequest(0, 1, null));
            //该设备在系统中的状态是在线 才会产生断线的告警
            if (alarmDOS != null && alarmDOS.size() > 0) {
                AlarmDO alarm = alarmDOS.get(0);
                if(alarm.getIsNormal()){
                    alarm.setIsNormal(false);
                    alarm.setValue("断线");
                    alarm.setStartTime(new Date(System.currentTimeMillis()));
                    alarmDOMapper.updateByPrimaryKey(alarm);
                    //通知管理系统
                    AlarmChangeMsg alarmMsg = new AlarmChangeMsg();
                    alarmMsg.setDeviceId(alarm.getDeviceId());
                    alarmMsg.setAlarmId(alarm.getId());
                    redisService.pubMessage(MQConstant.ALARM_CHANGE_MESSAGE_NAME, JSON.toJSONString(alarmMsg));
                }
            }else{
                //系统中不存在这个告警
                queryAlarmDo.setIsNormal(false);
                queryAlarmDo.setValue("断线");
                queryAlarmDo.setStartTime(new Date(System.currentTimeMillis()));
                queryAlarmDo.setName("在线状态");
                alarmDOMapper.insert(queryAlarmDo);
            }
        }
    }


}
