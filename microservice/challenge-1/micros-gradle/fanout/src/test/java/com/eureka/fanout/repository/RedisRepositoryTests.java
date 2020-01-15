package com.eureka.fanout.repository;

import com.eureka.fanout.config.TestRedisConfiguration;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.BoundListOperations;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.data.redis.support.collections.RedisList;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.anyString;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = TestRedisConfiguration.class)
public class RedisRepositoryTests {
    StringRedisTemplate stringRedisTemplate;

    @Autowired
    private RedisRepository redisRepository;


    @Before
    public void setUp() {

    }

    @Test
    public void testLset(){

        boolean res = redisRepository.lSet("test", "tes1");

        assertTrue(res);

    }



}
