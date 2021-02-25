package com.github.tanhao1410.thesis.server.spring;

import com.github.tanhao1410.thesis.common.mapper.AlarmDOMapper;
import com.github.tanhao1410.thesis.common.mapper.DeviceDOMapper;
import com.github.tanhao1410.thesis.common.mapper.HistoryAlarmDOMapper;
import com.github.tanhao1410.thesis.common.mapper.MonitoringDataDOMapper;
import com.github.tanhao1410.thesis.mq.RedisService;
import org.springframework.context.ConfigurableApplicationContext;

/**
 * 数据存储服务
 * @author tanhao
 * @date 2021/02/25 14:01
 */
public class SpringBeanManagement {

    public static AlarmDOMapper alarmDOMapper;
    public static HistoryAlarmDOMapper historyAlarmDOMapper;
    public static MonitoringDataDOMapper monitoringDataDOMapper;
    public static DeviceDOMapper deviceDOMapper;
    public static RedisService redisService;

    /**
     * 初始化Spring中的Bean，放入到该管理器中。以方便使用。
     * @param context
     */
    public static void initMapper(ConfigurableApplicationContext context){
        //注入mapper
        alarmDOMapper = context.getBean(AlarmDOMapper.class);
        historyAlarmDOMapper = context.getBean(HistoryAlarmDOMapper.class);
        monitoringDataDOMapper = context.getBean(MonitoringDataDOMapper.class);
        deviceDOMapper = context.getBean(DeviceDOMapper.class);
        redisService = context.getBean(RedisService.class);
    }
}
