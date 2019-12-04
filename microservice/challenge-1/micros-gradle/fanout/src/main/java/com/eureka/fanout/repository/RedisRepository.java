package com.eureka.fanout.repository;

import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Repository;

@Repository
public class RedisRepository {

    private final StringRedisTemplate stringRedisTemplate;
    private final ValueOperations<String, String> valueOps;


    public RedisRepository(StringRedisTemplate stringRedisTemplate) {
        this.stringRedisTemplate = stringRedisTemplate;
        this.valueOps = stringRedisTemplate.opsForValue();

    }

    public boolean lSet(String key, String value) {
        try {
            valueOps.getOperations().opsForList().rightPush(key, value);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

}
