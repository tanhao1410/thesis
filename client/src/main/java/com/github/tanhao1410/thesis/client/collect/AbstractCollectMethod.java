package com.github.tanhao1410.thesis.client.collect;

import com.github.tanhao1410.thesis.client.collect.impl.CPUCollectMethod;
import com.github.tanhao1410.thesis.client.collect.impl.MemCollectMethod;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

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
        final CPUCollectMethod cpuCollectMethod = new CPUCollectMethod();
        final MemCollectMethod memCollectMethod = new MemCollectMethod();

        methods.put(cpuCollectMethod.getName(), cpuCollectMethod);
        methods.put(memCollectMethod.getName(), memCollectMethod);
    }

}
