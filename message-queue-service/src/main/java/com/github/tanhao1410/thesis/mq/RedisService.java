package com.github.tanhao1410.thesis.mq;

import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.Collection;
import java.util.Date;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @Author hushanwen
 * @date 2019/11/27 19:17
 */
@Component
@Slf4j
public class RedisService {

    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    @Resource
    private StringRedisTemplate stringRedisTemplate;

//    @Autowired
//    private RedisTemplate<String, Object> redisTemplateo;

    /**
     * 往channel中发送消息
     * @param channel
     * @param message
     */
    @Async
    public void pubMessage(String channel,String message){
        redisTemplate.convertAndSend(channel, message);
    }


    public Boolean lock(String key,Integer timeOut){
//        if(StringUtils.isEmpty(key)){
//            return true;
//        }
//        if(existsKey(key)){
//            return false;
//        }
//        set(key,"lock",timeOut);
//        return true;
        return key != null && !"".equals(key.trim()) ? ((Boolean)this.stringRedisTemplate.execute(new RedisCallback<Boolean>() {
            public Boolean doInRedis(RedisConnection connection) throws DataAccessException {
                Boolean nx = connection.setNX(key.getBytes(), String.valueOf(System.currentTimeMillis()).getBytes());
                if (!nx.booleanValue()) {
                    try {
                        Thread.sleep(100L);
                    } catch (InterruptedException var4) {
                        var4.printStackTrace();
                    }

                    nx = connection.setNX(key.getBytes(), String.valueOf(System.currentTimeMillis()).getBytes());
                }

                if (nx.booleanValue()) {
                    connection.expire(key.getBytes(), (long)timeOut);
                }

                return nx;
            }
        })).booleanValue() : false;
    }

    public boolean unLock(String key){
        if (key != null && !"".equals(key.trim())) {
            try {
                this.stringRedisTemplate.delete(key);
                return true;
            } catch (Exception var3) {
                var3.printStackTrace();
                return false;
            }
        } else {
            return false;
        }
    }


    public String getString(String key){
        return get(key);
    }

    /**
     * 获取值为String的值
     * @param key
     * @return
     */
    public String get(String key){
        if(stringRedisTemplate.hasKey(key)){
            ValueOperations<String, String> valueops = stringRedisTemplate.opsForValue();
            return valueops.get(key);
        }
        return null;
    }

    /**
     * 获取对象
     * @param key
     * @param c
     * @param <T>
     * @return
     */
    public <T> T getObj(String key,Class<T> c){
        if(redisTemplate.hasKey(key)){
            Object obj = null;
            try {
                obj = redisTemplate.opsForValue().get(key);
                if (obj != null) {
                    return c.cast(obj);
                }
            } catch (Exception e) {
                log.error("[获取Redis对象失败  redisKey] = {} "+ key);
                log.error("[获取Redis对象失败  异常] = {}", e);
            }
        }
        return null;
    }

    /**
     * 将string值放入redis中
     * @param key
     * @param ob
     * @param timeOut
     * @return
     */
    public Boolean set(String key,String ob,Integer timeOut){
        if(StringUtils.isEmpty(key)){
            return false;
        }
        ValueOperations<String, String> valueops = stringRedisTemplate.opsForValue();
        valueops.set(key,ob);
        if(timeOut!=null&&timeOut.intValue()>0){
            expireKey(key,timeOut.longValue(),TimeUnit.SECONDS);
        }
        return true;
    }

    /**
     * 将对象放入redis中
     * @param key
     * @param obj
     * @param timeOut
     * @return
     */
    public Boolean setObj(String key,Object obj,Integer timeOut){
        if(StringUtils.isEmpty(key)){
            return false;
        }
        redisTemplate.opsForValue().set(key,obj);
        if(timeOut!=null&&timeOut.intValue()>0){
            expireObjKey(key,timeOut.longValue(),TimeUnit.SECONDS);
        }
        return true;
    }

    /**
     * 判断对象的的key是否存在
     * @param key
     * @return
     */
    public boolean existsObjecKey(String key) {
        return redisTemplate.hasKey(key);
    }

    /**
     * 判断string值的key是否存在
     * @param key
     * @return
     */
    public boolean existsStringValueKey(String key){
        return stringRedisTemplate.hasKey(key);
    }

    /**
     * 重名名key，如果newKey已经存在，则newKey的原值被覆盖
     *
     * @param oldKey
     * @param newKey
     */
    private void renameKey(String oldKey, String newKey) {
        redisTemplate.rename(oldKey, newKey);
    }

    /**
     * newKey不存在时才重命名
     *
     * @param oldKey
     * @param newKey
     * @return 修改成功返回true
     */
    private boolean renameKeyNotExist(String oldKey, String newKey) {
        return redisTemplate.renameIfAbsent(oldKey, newKey);
    }

    /**
     * 删除String值的key
     *
     * @param key
     */
    public void deleteKey(String key) {
        stringRedisTemplate.delete(key);
    }

    /**
     * 删除对象值的key
     * @param key
     */
    public void deleteObjKey(String key){
        redisTemplate.delete(key);
    }

    /**
     * 删除多个key
     *
     * @param keys
     */
    private void deleteKey(String... keys) {
        Set<String> kSet = Stream.of(keys).map(k -> k).collect(Collectors.toSet());
        redisTemplate.delete(kSet);
    }

    /**
     * 删除Key的集合
     *
     * @param keys
     */
    private void deleteKey(Collection<String> keys) {
        Set<String> kSet = keys.stream().map(k -> k).collect(Collectors.toSet());
        redisTemplate.delete(kSet);
    }

    /**
     * 设置String 值的key的生命周期
     *
     * @param key
     * @param time
     * @param timeUnit
     */
    public void expireKey(String key, long time, TimeUnit timeUnit) {
        stringRedisTemplate.expire(key, time, timeUnit);
    }

    /**
     * 设置object值的key的生命周期
     * @param key
     * @param time
     * @param timeUnit
     */
    public void expireObjKey(String key, long time, TimeUnit timeUnit){
        redisTemplate.expire(key,time,timeUnit);
    }

    /**
     * 指定key在指定的日期过期
     *
     * @param key
     * @param date
     */
    private void expireKeyAt(String key, Date date) {
        redisTemplate.expireAt(key, date);
    }

    /**
     * 查询key的生命周期
     *
     * @param key
     * @param timeUnit
     * @return
     */
    private long getKeyExpire(String key, TimeUnit timeUnit) {
        return redisTemplate.getExpire(key, timeUnit);
    }

    /**
     * 将key设置为永久有效
     *
     * @param key
     */
    private void persistKey(String key) {
        redisTemplate.persist(key);
    }
}
