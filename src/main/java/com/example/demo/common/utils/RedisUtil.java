package com.example.demo.common.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;


@Component
public class RedisUtil {
    @Autowired
    private RedisTemplate redisTemplate;

    /**
     * 向redis中存值
     * @param key
     * @param value
     * @return
     */
    public boolean setString(final String key, final String value) {
        boolean result = (boolean) redisTemplate.execute(new RedisCallback<Boolean>() {
            @Override
            public Boolean doInRedis(RedisConnection connection) throws DataAccessException {
                RedisSerializer<String> serializer = redisTemplate.getStringSerializer();
                connection.set(serializer.serialize(key), serializer.serialize(value));
                return true;
            }
        });
        return result;
    }

    /**
     * 从redis中获取值
     * @param key
     */
    public String getString(final String key) {
        String result = (String) redisTemplate.execute(new RedisCallback<String>() {
            @Override
            public String doInRedis(RedisConnection connection) throws DataAccessException {
                RedisSerializer<String> serializer = redisTemplate.getStringSerializer();
                byte[] value = connection.get(serializer.serialize(key));
                return serializer.deserialize(value);
            }
        });
        return result;
    }

    /**
     * 删除redis中某值
     * @param key
     */
    public void del(String key) {
        redisTemplate.delete(key);
    }

    /**
     * 向redis中存值并且设置超时时间，时间单位为秒
     * @param key
     * @param value
     * @param expires
     * @return
     */
    public boolean setStringKeyAndExpire(String key, String value, long expires) {
        boolean result = (boolean) redisTemplate.execute(new RedisCallback<Boolean>() {
            @Override
            public Boolean doInRedis(RedisConnection connection) throws DataAccessException {
                RedisSerializer<String> serializer = redisTemplate.getStringSerializer();
                connection.set(serializer.serialize(key), serializer.serialize(value));
                return true;
            }
        });
        Boolean expire = redisTemplate.expire(key, expires, TimeUnit.SECONDS);
        return result && expire;
    }
}
