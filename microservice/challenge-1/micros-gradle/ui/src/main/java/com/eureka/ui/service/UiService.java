package com.eureka.ui.service;

import com.eureka.common.domain.Range;
import com.eureka.common.domain.User;
import com.eureka.common.domain.dto.FollowDTO;
import com.eureka.common.domain.dto.SignUpRequest;
import com.eureka.common.domain.dto.TweetDTO;
import com.eureka.common.web.WebPost;
import com.eureka.ui.RetwisSecurity;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class UiService {

    private RestTemplate restTemplate;

    private static final Pattern MENTION_REGEX = Pattern.compile("@[\\w]+");


    public UiService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public Optional<Long> isUserValid(String username){
        ResponseEntity<User> response
                = restTemplate.getForEntity("http://ZUUL-SERVER/auth/" + username, User.class);

        if (response.getStatusCode() == HttpStatus.OK && response.getBody() != null){
            return Optional.of(response.getBody().getId());
        } else {
            return Optional.empty();
        }
    }

    public Optional<Long> findUid(String name) {
        ResponseEntity<User> response
                = restTemplate.getForEntity("http://ZUUL-SERVER/auth/"+name, User.class );

        if (response.getStatusCode() == HttpStatus.OK &&  response.getBody() != null) {
            return Optional.of(response.getBody().getId());
        }
        return Optional.empty();
    }

    public Optional<User> findName(String id) {
        ResponseEntity<User> response
                = restTemplate.getForEntity("http://ZUUL-SERVER/auth/users/"+id, User.class );

        if (response.getStatusCode() == HttpStatus.OK && response.getBody() != null) {
            return Optional.of(response.getBody());
        }
        return Optional.empty();
    }

    public Optional<User> auth(String username, String password){
        SignUpRequest request = new SignUpRequest();
        request.setUsername(username);
        request.setPassword(password);

        System.out.println(" requestuser: "+ request.getUsername());

        System.out.println("request pass: "+ request.getPassword());

        HttpHeaders headers = new HttpHeaders();
        HttpEntity<SignUpRequest> entity = new HttpEntity<>(request, headers);
        ResponseEntity<Void> response
                = restTemplate.postForEntity("http://ZUUL-SERVER/auth", entity, Void.class );

        if (response.getStatusCode() == HttpStatus.OK){
            HttpHeaders response2Headers = response.getHeaders();
            String token = Objects.requireNonNull(response2Headers.get("Authorization")).get(0);

            ResponseEntity<User> response2
                    = restTemplate.getForEntity("http://ZUUL-SERVER/auth/"+username, User.class );

            if (response2.getStatusCode() == HttpStatus.OK){
               User userResponse = response2.getBody();
                userResponse.setAuthKey(token);

                return Optional.of(userResponse);
            }
        }

        return Optional.empty();
    }

    public Optional<User> addUser (String username, String password){

        SignUpRequest request = new SignUpRequest();
        request.setUsername(username);
        request.setPassword(password);

        HttpHeaders headers = new HttpHeaders();
        HttpEntity<SignUpRequest> entity = new HttpEntity<>(request, headers);

        ResponseEntity<Void> response
                = restTemplate.postForEntity("http://ZUUL-SERVER/auth/signup", entity, Void.class );

        Long userId = -1L;

        if (response.getStatusCode() == HttpStatus.CREATED){
            HttpHeaders headers2 = new HttpHeaders();
            HttpEntity<SignUpRequest> entity2 = new HttpEntity<>(request, headers2);
            ResponseEntity<Void> response2
                    = restTemplate.postForEntity("http://ZUUL-SERVER/auth", entity2, Void.class );
            String [] locationArr = Objects.requireNonNull(response.getHeaders().getLocation()).toString().split("/");

            userId = Long.valueOf(locationArr[locationArr.length -1] );

            if (response2.getStatusCode() == HttpStatus.OK){
                HttpHeaders response2Headers = response2.getHeaders();
                String token = response2Headers.get("Authorization").get(0);
                System.out.println(token);

                User user = new User();
                user.setAuthKey(token);
                user.setId(userId);
                user.setUsername(username);
                user.setPassword(password);

                return Optional.of(user);

            }

        } else {
            // nothing
        }
        return Optional.empty();
    }

    public List<String> getFollowers(String uid, String token){
        List<String>  result = new ArrayList<>();
        System.out.println("token "+ token);

        HttpHeaders header = new HttpHeaders();
        header.put("Authorization", Collections.singletonList(token));

        HttpEntity<?> entity = new HttpEntity<>(header);
        ParameterizedTypeReference<List<FollowDTO>> typeRef = new ParameterizedTypeReference<List<FollowDTO>>() {};
        ResponseEntity<List<FollowDTO>> response = restTemplate.exchange("http://ZUUL-SERVER/social/" + uid+"/followers", HttpMethod.GET, entity,typeRef);


        if (response.getStatusCode() == HttpStatus.OK){
           for (FollowDTO followDTO: Objects.requireNonNull(response.getBody())){
               System.out.println("URL auth users http://ZUUL-SERVER/auth/users/"+followDTO.getSourceId());
               ResponseEntity<User> res
                       = restTemplate.getForEntity("http://ZUUL-SERVER/auth/users/"+followDTO.getSourceId(), User.class );

               if (res.getStatusCode() == HttpStatus.OK && res.getBody() != null)
                   result.add(res.getBody().getUsername());
           }
        }

        return result;

    }

    public List<String> getFollowing(String uid, String token){
        List<String>  result = new ArrayList<>();

        System.out.println("token "+ token);

        HttpHeaders header = new HttpHeaders();
        header.put("Authorization", Collections.singletonList(token));

        HttpEntity<?> entity = new HttpEntity<>(header);
        ParameterizedTypeReference<List<FollowDTO>> typeRef = new ParameterizedTypeReference<List<FollowDTO>>() {};
        ResponseEntity<List<FollowDTO>> response = restTemplate.exchange("http://ZUUL-SERVER/social/" + uid+"/following", HttpMethod.GET, entity,typeRef);


        if (response.getStatusCode() == HttpStatus.OK){
            for (FollowDTO followDTO: Objects.requireNonNull(response.getBody())){
                ResponseEntity<User> res
                        = restTemplate.getForEntity("http://ZUUL-SERVER/auth/users/"+followDTO.getDestinationId(), User.class );

                if (res.getStatusCode() == HttpStatus.OK && res.getBody() != null)
                    result.add(res.getBody().getUsername());
            }
        }

        return result;
    }

    public void post(Long userId, WebPost post, String token){

        TweetDTO newPost = new TweetDTO();
        newPost.setUserId(userId);
        newPost.setContent(post.getContent());
        System.out.println(post.toString());
/*
        if (post.getReplyPid()!=null && !post.getReplyPid().isEmpty())
            newPost.setReplyPid(UUID.fromString(post.getReplyPid()));
        if (post.getPid()!=null && !post.getPid().isEmpty() )
            newPost.setReplyUid(Long.valueOf(post.getPid()));


  */
        String replyName = post.getReplyTo();
        if (StringUtils.hasText(replyName)) {
            Optional<Long> mentionUid = findUid(replyName);
            newPost.setReplyUid(mentionUid.get());
            // handle mentions below
            newPost.setReplyPid(UUID.fromString(post.getReplyPid()));
        }

        HttpHeaders header = new HttpHeaders();
        header.put("Authorization", Collections.singletonList(token));
        header.put(HttpHeaders.CONTENT_TYPE, Collections.singletonList("application/json"));
        HttpEntity<?> entity = new HttpEntity<>(newPost, header);
        ResponseEntity<TweetDTO> response = restTemplate.exchange("http://ZUUL-SERVER/tweet/", HttpMethod.POST, entity,TweetDTO.class);


        if (response.getStatusCode() == HttpStatus.CREATED && response.getBody() != null){
            TweetDTO tweetDTO = response.getBody();
            handleMentions(newPost, tweetDTO.getId().toString(), replyName);

        }


    }

    public void follow(String targetUser) {
        Optional<Long> targetUid = findUid(targetUser);

        Long uid = Long.valueOf(RetwisSecurity.getUid());
        FollowDTO followDTO = new FollowDTO();
        followDTO.setSourceId(uid);
        followDTO.setDestinationId(targetUid.get());

        HttpHeaders header = new HttpHeaders();
        header.put("Authorization", Collections.singletonList(RetwisSecurity.getToken()));
        header.put(HttpHeaders.CONTENT_TYPE, Collections.singletonList("application/json"));

        HttpEntity<?> entity = new HttpEntity<>(followDTO, header);
        restTemplate.exchange("http://ZUUL-SERVER/social/", HttpMethod.POST, entity,Void.class);

    }

    public void stopFollowing(String targetUser) {
        Optional<Long> targetUid = findUid(targetUser);

        Long uid = Long.valueOf(RetwisSecurity.getUid());
        FollowDTO followDTO = new FollowDTO();
        followDTO.setSourceId(uid);
        followDTO.setDestinationId(targetUid.get());

        HttpHeaders header = new HttpHeaders();
        header.put("Authorization", Collections.singletonList(RetwisSecurity.getToken()));
        header.put(HttpHeaders.CONTENT_TYPE, Collections.singletonList("application/json"));

        HttpEntity<?> entity = new HttpEntity<>(followDTO, header);
        restTemplate.exchange("http://ZUUL-SERVER/social/", HttpMethod.DELETE, entity,Void.class);
    }


    public Optional<TweetDTO> isPostValid(String id) {
        HttpHeaders header = new HttpHeaders();
        header.put("Authorization", Collections.singletonList(RetwisSecurity.getToken()));
        header.put(HttpHeaders.CONTENT_TYPE, Collections.singletonList("application/json"));
        HttpEntity<?> entity = new HttpEntity<>(header);

        ResponseEntity<TweetDTO> response = restTemplate.exchange("http://ZUUL-SERVER/tweet/"+id, HttpMethod.GET,entity,TweetDTO.class);

        if (response.getStatusCode() == HttpStatus.FOUND && response.getBody() != null){
            return Optional.of(response.getBody());
        }

        return Optional.empty();
    }

    public WebPost getPost(Optional<TweetDTO> res){
        TweetDTO p = res.get();
        WebPost w = new WebPost();
        w.setContent(p.getContent());
        w.setPid(p.getId().toString());
        w.setTime(p.getTimestamp().toString());

        if (p.getReplyPid() != null){
            w.setReplyPid(p.getReplyPid().toString());

        }
        if (p.getReplyUid() != null && p.getReplyUid() != 0L){
            w.setReplyPid(p.getReplyPid().toString());
            Optional<User> userReply = findName(p.getReplyUid().toString());

            userReply.ifPresent(user -> w.setReplyTo(user.getUsername()));
        }

        Optional<User> user = findName(p.getUserId().toString());

        user.ifPresent(value -> w.setName(value.getUsername()));

        return w;
    }

    public boolean isFollowing(String sourceId, String destinationId) {

        HttpHeaders header = new HttpHeaders();
        header.put("Authorization", Collections.singletonList(RetwisSecurity.getToken()));

        HttpEntity<?> entity = new HttpEntity<>(header);

        String url = "http://ZUUL-SERVER/social/"+sourceId+"/"+destinationId;

        System.out.println("social link "+ url);
        ResponseEntity<Boolean> response = restTemplate.exchange( url, HttpMethod.GET, entity,Boolean.class);

        if (response.getBody() != null && response.getStatusCode() == HttpStatus.OK){
            return response.getBody();
        }
        return false;
    }


    public List<String> commonFollowers(String sourceId, String destinationId) {

        HttpHeaders header = new HttpHeaders();
        header.put("Authorization", Collections.singletonList(RetwisSecurity.getToken()));

        HttpEntity<?> entity = new HttpEntity<>(header);
        ParameterizedTypeReference<Set<Long>> typeRef = new ParameterizedTypeReference<Set<Long>>() {};
        ResponseEntity<Set<Long>> response = restTemplate.exchange("http://ZUUL-SERVER/social/common/"+sourceId+"/"+destinationId , HttpMethod.GET, entity,typeRef);

        if (response.getBody() != null && response.getStatusCode() == HttpStatus.OK){
            List<String> names = new ArrayList<>();
            for (Long l : response.getBody()){
                String s = String.valueOf(l);

                Optional<User> user = findName(s);
                user.ifPresent(value -> names.add(value.getUsername()));

            }
            return names;
        }


        return new ArrayList<>();
    }


    public List<WebPost> getTimeline(String targetUid, Integer page){

        page = (page != null ? Math.abs(page) : 1);

        HttpHeaders header = new HttpHeaders();
        header.put("Authorization", Collections.singletonList(RetwisSecurity.getToken()));

        HttpEntity<?> entity = new HttpEntity<>(header);
        ParameterizedTypeReference<List<TweetDTO>> typeRef = new ParameterizedTypeReference<List<TweetDTO>>() {};

        UriComponentsBuilder builder = UriComponentsBuilder.fromUriString("http://ZUUL-SERVER/timeline/"+ targetUid)
                .queryParam("page", page);
        UriComponents uriComponents = builder.build().encode();

        ResponseEntity<List<TweetDTO>> response = restTemplate.exchange(uriComponents.toUri(), HttpMethod.GET, entity,typeRef);
        List<WebPost> result = new ArrayList<>();

        if (response.getBody() != null && response.getStatusCode() == HttpStatus.OK){

            for (TweetDTO tweetDTO:response.getBody()){
                WebPost wp = getPost(Optional.of(tweetDTO));
                result.add(wp);
            }

            return result;

        }

        return new ArrayList<>();

    }

    public boolean hasMoreTimeline(String targetUid, Integer page){
        HttpHeaders header = new HttpHeaders();
        header.put("Authorization", Collections.singletonList(RetwisSecurity.getToken()));

        HttpEntity<?> entity = new HttpEntity<>(header);

        String url = "http://ZUUL-SERVER/timeline/"+targetUid+"/more";

        UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(url)
            .queryParam("page", page);
        UriComponents uriComponents = builder.build().encode();

        ResponseEntity<Boolean> response = restTemplate.exchange( uriComponents.toUri(), HttpMethod.GET, entity,Boolean.class);

        if (response.getBody() != null && response.getStatusCode() == HttpStatus.OK){
            return response.getBody();
        }
        return false;
    }

    public List<String> alsoFollowed(String uid, String targetUid){
        List<String> following = getFollowing(uid, RetwisSecurity.getToken());
        List<String> followers = getFollowers(targetUid, RetwisSecurity.getToken());

        Set<String> set= new HashSet<>(followers);
        set.retainAll(following);


        return new ArrayList<String>(set);
    }

    public static Collection<String> findMentions(String content) {
        Matcher regexMatcher = MENTION_REGEX.matcher(content);
        List<String> mentions = new ArrayList<String>(4);

        while (regexMatcher.find()) {
            mentions.add(regexMatcher.group().substring(1));
        }

        return mentions;
    }


    private void handleMentions(TweetDTO post, String pid, String name) {
        // find mentions
        Collection<String> mentions = findMentions(post.getContent());


        for (String mention : mentions) {
            Optional<Long> uid = findUid(mention);
            if (uid.isPresent()) {
                //mentions(uid).addFirst(pid);
                HttpHeaders header = new HttpHeaders();
                header.put("Authorization", Collections.singletonList(RetwisSecurity.getToken()));
                header.put(HttpHeaders.CONTENT_TYPE, Collections.singletonList("application/json"));
                HttpEntity<?> entity = new HttpEntity<>(header);
                restTemplate.exchange("http://ZUUL-SERVER/tweet/"+pid+"/mention/"+uid.get(), HttpMethod.POST, entity,Void.class);
            }
        }




    }

}
