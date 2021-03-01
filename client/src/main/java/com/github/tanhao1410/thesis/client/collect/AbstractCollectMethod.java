package com.github.tanhao1410.thesis.client.collect;

import com.github.tanhao1410.thesis.client.collect.impl.CPUUsageCollectMethod;
import com.github.tanhao1410.thesis.client.collect.impl.MemUsageCollectMethod;

import java.util.HashMap;
import java.util.Map;

/**
 * 抽象采集方法
 *
 * @author tanhao
 * @date 2021/02/24 11:25
 */
public abstract class AbstractCollectMethod {

    /**
     * 返回采集方法的名称
     *
     * @return
     */
    public abstract String getName();

    /**
     * 采集方法，返回值
     * @param param
     * @return
     */
    public abstract String getValue(String param);


    /**
     * 根据采集方法的名称获取采集方法实例对象
     *
     * @return
     */
    public static AbstractCollectMethod getCollectMethodByName(String name) {
        return methods.get(name);
    }

    private static Map<String, AbstractCollectMethod> methods = new HashMap<>();

    static {
        final CPUUsageCollectMethod cpuUsageCollectMethod = new CPUUsageCollectMethod();
        final MemUsageCollectMethod memUsageCollectMethod = new MemUsageCollectMethod();

        methods.put(cpuUsageCollectMethod.getName(), cpuUsageCollectMethod);
        methods.put(memUsageCollectMethod.getName(), memUsageCollectMethod);
    }

}
