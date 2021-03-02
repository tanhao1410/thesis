package com.github.tanhao1410.thesis.client.collect;

import org.reflections.Reflections;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;


public class CollectionMethodFactory {

    /**
     * Bean对象容器
     */
    private static final Map<String, AbstractCollectMethod> beanContainer = new HashMap<String, AbstractCollectMethod>();

    /**
     * 初始化指定包下的所有@Service注解标记的类
     * 
     * @param packageName 初始化包路径
     * @throws InstantiationException
     * @throws IllegalAccessException
     */
    public static void init(String packageName) throws InstantiationException, IllegalAccessException {
        Reflections f = new Reflections(packageName);
        Set<Class<?>> set = f.getTypesAnnotatedWith(Collection.class);
        for (Class<?> c : set) {
            AbstractCollectMethod bean = (AbstractCollectMethod)c.newInstance();
            Collection annotation = c.getAnnotation(Collection.class);
            beanContainer.put(annotation.value(), bean);
        }
    }

    /**
     * 根据注解名获取实例
     * 
     * @param beanName 注解的名称
     * @return 对应实例
     */
    public static AbstractCollectMethod getCollectMethodBean(String beanName) {
        return beanContainer.get(beanName);
    }


    static {
        String packageName = "com.github.tanhao1410.thesis.client";
        try {
            CollectionMethodFactory.init(packageName);
        }  catch (Exception e) {
            System.out.println("自动扫描数据采集类出错，包名：" + packageName);
            e.printStackTrace();
        }
    }

}