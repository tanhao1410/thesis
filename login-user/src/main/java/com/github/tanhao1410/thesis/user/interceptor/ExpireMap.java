package com.github.tanhao1410.thesis.user.interceptor;

import lombok.extern.slf4j.Slf4j;
import net.jodah.expiringmap.ExpirationPolicy;
import net.jodah.expiringmap.ExpiringMap;
import org.springframework.util.StringUtils;

import java.util.concurrent.TimeUnit;

/**
 * @Author tanhao
 * @date 2020-01-26
 */
@Slf4j
public class ExpireMap {

    public static ExpiringMap<String, String> map = ExpiringMap.builder()
            .maxSize(1000)
            .expiration(15, TimeUnit.MINUTES)//设置每个key有效时间10s, 如果key不设置过期时间，key永久有效。
            .variableExpiration().expirationPolicy(ExpirationPolicy.ACCESSED).build();


    /**
     * 将string值放入map中,时间单位默认为秒
     * @param key
     * @param ob
     * @param timeOut
     * @return
     */
    public static Boolean set(String key, String ob, Integer timeOut) {
        if (!StringUtils.isEmpty(key)) {
            map.put(key,ob,timeOut,TimeUnit.SECONDS);
            return false;
        }
        return true;
    }

    /**
     * 从map中获取值
     * @param s
     * @return
     */
    public static String get(String s) {
        return map.get(s);
    }

    /**
     * 重设过期时间
     * @param s
     * @param expiredTimeHour1
     * @param seconds
     */
    public static void expireKey(String s, Integer expiredTimeHour1, TimeUnit seconds) {
        map.setExpiration(s, expiredTimeHour1, seconds);
    }

    /**
     * 移除一个key
     * @param key
     */
    public static void deleteKey(String key) {
        map.remove(key);
    }
}
