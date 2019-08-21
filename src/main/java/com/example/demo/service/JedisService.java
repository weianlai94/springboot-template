package com.example.demo.service;



import java.util.List;

public interface JedisService {

    boolean set(String key, String value);
    
    String get(String key);

    boolean setKeyAndExpire(final String key, final String value, final long expire);

    <T> boolean setList(String key, List<T> list);

    <T> List<T> getList(String key, Class<T> clz);

    long lpush(String key, Object obj);

    long rpush(String key, Object obj);

    String lpop(String key);

    void send(String key, String value);
    
    void del(String key);
}