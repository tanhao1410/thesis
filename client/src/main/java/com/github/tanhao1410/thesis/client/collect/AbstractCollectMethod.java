package com.github.tanhao1410.thesis.client.collect;

import com.github.tanhao1410.thesis.client.collect.impl.*;

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
        return CollectionMethodFactory.getCollectMethodBean(name);
    }



}
