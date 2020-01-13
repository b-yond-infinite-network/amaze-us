package com.eureka.timeline.service;


import com.eureka.common.domain.Post;
import com.eureka.common.domain.Range;
import com.eureka.common.domain.dto.TweetDTO;
import com.eureka.common.domain.dto.TweetList;
import com.eureka.common.redis.KeyUtils;
import com.eureka.common.web.WebPost;
import com.netflix.ribbon.proxy.annotation.Http;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.data.redis.core.BoundHashOperations;
import org.springframework.data.redis.core.BulkMapper;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.data.redis.core.query.SortQuery;
import org.springframework.data.redis.core.query.SortQueryBuilder;
import org.springframework.data.redis.hash.BeanUtilsHashMapper;
import org.springframework.data.redis.hash.DecoratingStringHashMapper;
import org.springframework.data.redis.hash.HashMapper;
import org.springframework.data.redis.support.atomic.RedisAtomicLong;
import org.springframework.data.redis.support.collections.DefaultRedisList;
import org.springframework.data.redis.support.collections.RedisList;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class RetwisRepository {

    private static final Pattern MENTION_REGEX = Pattern.compile("@[\\w]+");

    private final StringRedisTemplate template;
    private final ValueOperations<String, String> valueOps;

    private ListOperations<String, Object> listOperations;

    private RestTemplate restTemplate;

    // global timeline
    private final RedisList<String> timeline;

    private final HashMapper<Post, String, String> postMapper = new DecoratingStringHashMapper<Post>(
            new BeanUtilsHashMapper<>(Post.class));

    public RetwisRepository(StringRedisTemplate template, RestTemplate restTemplate) {
        this.template = template;
        valueOps = template.opsForValue();
        this.restTemplate = restTemplate;
        timeline = new DefaultRedisList<String>(KeyUtils.timeline(), template);
    }

    private RedisList<String> timeline(String uid) {
        return new DefaultRedisList<String>(KeyUtils.timeline(uid), template);
    }

    public List<TweetDTO> getTimeline(String uid, Range range) {

        String timelineId = KeyUtils.timeline(uid);

        List<String> timeline = lGet(timelineId, range);


        if (timeline.size() > 0){
            List<TweetDTO> result = getTweets(timeline);
            return result;
        }


        return new ArrayList<>();

    }



    public List<TweetDTO> getTweets(List<String> idList){
        HttpHeaders header = new HttpHeaders();

        TweetList list = new TweetList();
        list.setList(idList);

        HttpEntity<?> entity = new HttpEntity<>(list, header);
        ParameterizedTypeReference<List<TweetDTO>> typeRef = new ParameterizedTypeReference<List<TweetDTO>>(){};
        ResponseEntity<List<TweetDTO>> response = restTemplate.exchange("http://tweet-service/tweets", HttpMethod.POST, entity,typeRef);


        if (response.getBody() != null && response.getStatusCode() == HttpStatus.OK){
            return response.getBody();
        }
        return response.getBody();
    }



    public boolean hasMoreTimeline(String targetUid, Range range) {
        return timeline(targetUid).size() > range.end + 1;
    }


    public List<String> lGet(String key, Range range){
        try {
            return valueOps.getOperations().opsForList().range(key, range.begin, range.end);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
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


    public boolean hasMoreTimeline(Range range) {
        return timeline.size() > range.end + 1;
    }


}
