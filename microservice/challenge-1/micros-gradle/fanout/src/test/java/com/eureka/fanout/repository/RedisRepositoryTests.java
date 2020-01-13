package com.eureka.fanout.repository;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.data.redis.core.BoundListOperations;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.data.redis.support.collections.RedisList;

import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.anyString;

@RunWith(MockitoJUnitRunner.class)
public class RedisRepositoryTests {
    StringRedisTemplate stringRedisTemplate;

    private RedisRepository redisRepository;

    private ValueOperations<String, String> valueOperations;
    private ListOperations<String, String> listOperations;
    private RedisOperations<String, String> redisOperations;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        stringRedisTemplate = Mockito.mock(StringRedisTemplate.class);
        valueOperations = Mockito.mock(ValueOperations.class);
        listOperations = Mockito.mock(ListOperations.class);
        Mockito.when(stringRedisTemplate.opsForValue()).thenReturn(valueOperations);

        redisRepository = new RedisRepository(stringRedisTemplate);
        Mockito.when(valueOperations.getOperations()).thenReturn(redisOperations);
        Mockito.when(redisOperations.opsForList()).thenReturn(listOperations);

        Mockito.when(listOperations.rightPush(anyString(), anyString())).thenReturn(1L);

    }

    @Test
    public void testLset(){

        boolean res = redisRepository.lSet("test", "tes1");

        assertTrue(res);

    }



}
