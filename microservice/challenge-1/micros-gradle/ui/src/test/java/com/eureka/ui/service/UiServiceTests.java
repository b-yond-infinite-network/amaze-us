package com.eureka.ui.service;


import com.eureka.common.domain.User;
import com.eureka.common.domain.dto.FollowDTO;
import com.eureka.common.domain.dto.SignUpRequest;
import com.eureka.common.domain.dto.TweetDTO;
import com.eureka.common.web.WebPost;
import com.eureka.ui.RetwisSecurity;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PowerMockIgnore;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.modules.junit4.PowerMockRunnerDelegate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.AnnotationConfigWebContextLoader;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@RunWith(PowerMockRunner.class)
@PowerMockRunnerDelegate(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(loader= AnnotationConfigWebContextLoader.class)
@PrepareForTest(RetwisSecurity.class)
@PowerMockIgnore({ "javax.management.*", "com.sun.org.apache.xerces.*", "javax.xml.*",
        "org.xml.*", "org.w3c.dom.*", "com.sun.org.apache.xalan.*", "javax.activation.*" })
public class UiServiceTests {

    private UiService uiService;
    private RestTemplate restTemplate;

    @Before
    public void setUp() throws Exception {
        PowerMockito.mockStatic(RetwisSecurity.class);

        restTemplate = Mockito.mock(RestTemplate.class);
        uiService = new UiService(restTemplate);

        String name = "testUser2";
        String name1 = "testUser1";

        User user1 = new User();
        user1.setId(1L);
        user1.setUsername(name);
        user1.setPassword("test1234");
        user1.setAuthKey("AUTH-testUser2");

        User user3 = new User();
        user3.setId(3L);
        user3.setUsername("magg");
        user3.setPassword("test12345");
        user3.setAuthKey("AUTH-testUser3");

        User user4 = new User();
        user4.setId(4L);
        user4.setUsername("matisse");
        user4.setPassword("test123456");
        user4.setAuthKey("AUTH-testUser4");

        Mockito
                .when(restTemplate.getForEntity(
                        "http://ZUUL-SERVER/auth/"+name, User.class))
                .thenReturn(new ResponseEntity(user1, HttpStatus.OK));
        Mockito
                .when(restTemplate.getForEntity(
                        "http://ZUUL-SERVER/auth/"+name1, User.class))
                .thenReturn(new ResponseEntity(null, HttpStatus.OK));

        Mockito
                .when(restTemplate.getForEntity(
                        "http://ZUUL-SERVER/auth/users/"+1, User.class))
                .thenReturn(new ResponseEntity(user1, HttpStatus.OK));
        Mockito
                .when(restTemplate.getForEntity(
                        "http://ZUUL-SERVER/auth/users/"+3, User.class))
                .thenReturn(new ResponseEntity(user3, HttpStatus.OK));
        Mockito
                .when(restTemplate.getForEntity(
                        "http://ZUUL-SERVER/auth/users/"+4, User.class))
                .thenReturn(new ResponseEntity(user4, HttpStatus.OK));

        Mockito
                .when(restTemplate.getForEntity(
                        "http://ZUUL-SERVER/auth/users/"+2, User.class))
                .thenReturn(new ResponseEntity(null, HttpStatus.NOT_FOUND));


        HttpHeaders header = new HttpHeaders();
        header.set(HttpHeaders.AUTHORIZATION, "AUTH-testUser2");
        HttpEntity<?> entity = new HttpEntity<>(header);
        ParameterizedTypeReference<List<FollowDTO>> typeRef = new ParameterizedTypeReference<List<FollowDTO>>() {};

        ParameterizedTypeReference<Set<Long>> typeRefSET = new ParameterizedTypeReference<Set<Long>>() {};
        Set<Long> common = new HashSet<>();
        common.add(4L);
        common.add(3L);

        List<FollowDTO> followers = new ArrayList<>();
        List<FollowDTO> following = new ArrayList<>();

        FollowDTO f1 = new FollowDTO();
        f1.setSourceId(3L);
        f1.setDestinationId(1L);
        FollowDTO f2 = new FollowDTO();
        f2.setSourceId(1L);
        f2.setDestinationId(4L);


        followers.add(f1);
        followers.add(f2);
        following.add(f1);

        TweetDTO tweetDTO = new TweetDTO();
        tweetDTO.setContent("Hello world test");
        UUID uid2 = UUID.fromString("f0d12829-cb9b-4be9-837d-d75e931ba898");
        tweetDTO.setUserId(1L);
        tweetDTO.setId(uid2);
        tweetDTO.setTimestamp(LocalDateTime.now().toEpochSecond(ZoneOffset.UTC));

        Mockito
                .when(restTemplate.exchange(
                        "http://ZUUL-SERVER/social/1/followers",  HttpMethod.GET, entity,typeRef))
                .thenReturn(new ResponseEntity(followers, HttpStatus.OK));

        Mockito
                .when(restTemplate.exchange(
                        "http://ZUUL-SERVER/social/3/followers",  HttpMethod.GET, entity,typeRef))
                .thenReturn(new ResponseEntity(new ArrayList<>(), HttpStatus.OK));

        Mockito
                .when(restTemplate.exchange(
                        "http://ZUUL-SERVER/social/3/following",  HttpMethod.GET, entity,typeRef))
                .thenReturn(new ResponseEntity(following, HttpStatus.OK));
        Mockito
                .when(restTemplate.exchange(
                        "http://ZUUL-SERVER/social/5/following",  HttpMethod.GET, entity,typeRef))
                .thenReturn(new ResponseEntity(new ArrayList<>(), HttpStatus.OK));


        HttpHeaders headers = new HttpHeaders();
        SignUpRequest request = new SignUpRequest();
        request.setPassword("test1234");
        request.setUsername("testUser2");
        HttpEntity<SignUpRequest> entity2 = new HttpEntity<>(request, headers);

        HttpHeaders header3 = new HttpHeaders();
        header3.set(HttpHeaders.AUTHORIZATION, "AUTH-testUser2");
        header3.setLocation(new URI("http://ZUUL-SERVER/auth/users/1"));

        HttpEntity<Void> entityRes = new HttpEntity<>(header3);

        Mockito
                .when( restTemplate.postForEntity(eq("http://ZUUL-SERVER/auth"),
                        argThat((HttpEntity<SignUpRequest> aBar) -> aBar.getBody().getUsername() .equals( "testUser2") &&
                                aBar.getBody().getPassword() .equals( "test1234")),
                        eq(Void.class) ))
                .thenReturn(new ResponseEntity(header3, HttpStatus.OK));

        Mockito
                .when( restTemplate.postForEntity(eq("http://ZUUL-SERVER/auth/signup"),
                        argThat((HttpEntity<SignUpRequest> aBar) -> aBar.getBody().getUsername() .equals( "testUser2") &&
                                aBar.getBody().getPassword() .equals( "test1234")),
                        eq(Void.class) ))
                .thenReturn(new ResponseEntity(header3, HttpStatus.CREATED));

        Mockito
                .when( restTemplate.postForEntity(eq("http://ZUUL-SERVER/auth/signup"),
                        argThat((HttpEntity<SignUpRequest> aBar) -> aBar.getBody().getUsername() .equals( "testUser2") &&
                                aBar.getBody().getPassword() .equals( "test123")),
                        eq(Void.class) ))
                .thenReturn(new ResponseEntity(HttpStatus.FORBIDDEN));





        Mockito
                .when( restTemplate.postForEntity(eq("http://ZUUL-SERVER/auth"),
                        argThat((HttpEntity<SignUpRequest> aBar) -> aBar.getBody().getUsername() .equals( "testUser2") &&
                                aBar.getBody().getPassword() .equals( "test123")),
                        eq(Void.class) ))
                .thenReturn(new ResponseEntity(HttpStatus.FORBIDDEN));


        HttpHeaders header4 = new HttpHeaders();
        header4.put(HttpHeaders.AUTHORIZATION, Collections.singletonList("AUTH-testUser2"));
        header4.put(HttpHeaders.CONTENT_TYPE, Collections.singletonList("application/json"));
        TweetDTO newPost = new TweetDTO();
        newPost.setUserId(tweetDTO.getUserId());
        newPost.setContent(tweetDTO.getContent());

        HttpEntity<TweetDTO> entity4 = new HttpEntity<>(newPost, header4);

        Mockito
                .when(restTemplate.exchange(
                        eq("http://ZUUL-SERVER/tweet/"),
                        eq(HttpMethod.POST),
                        argThat((HttpEntity<TweetDTO> aBar) -> aBar.getBody().getContent() .equals( newPost.getContent()) &&
                                aBar.getBody().getUserId() .equals( newPost.getUserId())),
                        eq(TweetDTO.class)))
                .thenReturn(new ResponseEntity(tweetDTO, HttpStatus.CREATED));



        Mockito
                .when( restTemplate.exchange(eq("http://ZUUL-SERVER/social/"),
                        eq(HttpMethod.POST),
                        argThat((HttpEntity<FollowDTO> aBar) -> aBar.getBody().getDestinationId() == 1L &&
                                aBar.getBody().getSourceId() == 2L),
                        eq(Void.class) ))
                .thenReturn(new ResponseEntity( HttpStatus.CREATED));

        Mockito
                .when( restTemplate.exchange(eq("http://ZUUL-SERVER/social/"),
                        eq(HttpMethod.DELETE),
                        argThat((HttpEntity<FollowDTO> aBar) -> aBar.getBody().getDestinationId() == 1L &&
                                aBar.getBody().getSourceId() == 2L),
                        eq(Void.class) ))
                .thenReturn(new ResponseEntity( HttpStatus.ACCEPTED));


        HttpEntity<TweetDTO> entity5 = new HttpEntity<>(header4);


        Mockito
                .when( restTemplate.exchange(eq("http://ZUUL-SERVER/tweet/"+tweetDTO.getId().toString()),
                        eq(HttpMethod.GET),
                        eq(entity5)
                        ,
                        eq(TweetDTO.class)) )
                .thenReturn(new ResponseEntity(tweetDTO, HttpStatus.FOUND));

        Mockito
                .when( restTemplate.exchange(eq("http://ZUUL-SERVER/tweet/f0d12829-cb9b-4be9-837d-d75e931ba891"),
                        eq(HttpMethod.GET),
                        eq(entity5)
                        ,
                        eq(TweetDTO.class)) )
                .thenReturn(new ResponseEntity(HttpStatus.NOT_FOUND));

        HttpHeaders header7 = new HttpHeaders();
        header7.put(HttpHeaders.AUTHORIZATION, Collections.singletonList("AUTH-testUser2"));
        HttpEntity<?> entity7 = new HttpEntity<>(header7);

        Mockito
                .when( restTemplate.exchange(eq("http://ZUUL-SERVER/social/1/2"),
                        eq(HttpMethod.GET),
                        eq(entity7)
                        ,
                        eq(Boolean.class)) )
                .thenReturn(new ResponseEntity(Boolean.TRUE,HttpStatus.OK));

        Mockito
                .when( restTemplate.exchange(eq("http://ZUUL-SERVER/social/1/4"),
                        eq(HttpMethod.GET),
                        eq(entity7)
                        ,
                        eq(Boolean.class)) )
                .thenReturn(new ResponseEntity(Boolean.FALSE,HttpStatus.NOT_FOUND));


        Mockito
                .when(restTemplate.exchange(
                        "http://ZUUL-SERVER/social/common/1/2",  HttpMethod.GET, entity,typeRefSET))
                .thenReturn(new ResponseEntity(common, HttpStatus.OK));

        Mockito
                .when(restTemplate.exchange(
                        "http://ZUUL-SERVER/social/common/1/7",  HttpMethod.GET, entity,typeRefSET))
                .thenReturn(new ResponseEntity(new HashSet<>(), HttpStatus.OK));



        List<TweetDTO> tweetDTOList = new ArrayList<>();
        tweetDTOList.add(tweetDTO);

        ParameterizedTypeReference<List<TweetDTO>> typeRefTweet = new ParameterizedTypeReference<List<TweetDTO>>() {};
        UriComponentsBuilder builder = UriComponentsBuilder.fromUriString("http://ZUUL-SERVER/timeline/1")
                .queryParam("page", 1);
        UriComponents uriComponents = builder.build().encode();
        UriComponentsBuilder builder2 = UriComponentsBuilder.fromUriString("http://ZUUL-SERVER/timeline/2")
                .queryParam("page", 1);
        UriComponents uriComponents2 = builder2.build().encode();

        Mockito
                .when(restTemplate.exchange(uriComponents.toUri(),  HttpMethod.GET, entity7,typeRefTweet))
                .thenReturn(new ResponseEntity(tweetDTOList, HttpStatus.OK));

        Mockito
                .when(restTemplate.exchange(uriComponents2.toUri(),  HttpMethod.GET, entity7,typeRefTweet))
                .thenReturn(new ResponseEntity(new ArrayList<>(), HttpStatus.OK));


        UriComponentsBuilder builder3 = UriComponentsBuilder.fromUriString("http://ZUUL-SERVER/timeline/1/more")
                .queryParam("page", 1);
        UriComponents uriComponents3 = builder3.build().encode();
        UriComponentsBuilder builder4 = UriComponentsBuilder.fromUriString("http://ZUUL-SERVER/timeline/2/more")
                .queryParam("page", 1);
        UriComponents uriComponents4 = builder4.build().encode();


        Mockito
                .when(restTemplate.exchange(uriComponents3.toUri(),  HttpMethod.GET, entity7,Boolean.class))
                .thenReturn(new ResponseEntity(true, HttpStatus.OK));

        Mockito
                .when(restTemplate.exchange(uriComponents4.toUri(),  HttpMethod.GET, entity7,Boolean.class))
                .thenReturn(new ResponseEntity(false, HttpStatus.OK));


    }




    @Test
    public void testIsUserValid_valid() throws Exception {
        Optional<Long> result =
                uiService.isUserValid("testUser2");

        assertFalse(result.isEmpty());

        assertThat(result).hasValue(1L);
    }

    @Test
    public void testFindUid_valid() throws Exception {
        Optional<Long> result =
                uiService.findUid("testUser1");

        assertTrue(result.isEmpty());

    }

    @Test
    public void testFindUid_invalid() throws Exception {
        Optional<Long> result =
                uiService.findUid("testUser1");

        assertTrue(result.isEmpty());

    }

    @Test
    public void testFindName_valid() throws Exception {
        Optional<User> result =
                uiService.findName("1");

        assertFalse(result.isEmpty());

        assertEquals(1L, (long) result.get().getId());

    }

    @Test
    public void testFindName_invalid() throws Exception {
        Optional<User> result =
                uiService.findName("2");
        assertTrue(result.isEmpty());

    }


    @Test
    public void testGetFollowers_valid() throws Exception {
        List<String> followers = uiService.getFollowers("1", "AUTH-testUser2" );
        assertFalse(followers.isEmpty());
    }


    @Test
    public void testGetFollowers_noData() throws Exception {
        List<String> followers = uiService.getFollowers("3", "AUTH-testUser2" );
        assertTrue(followers.isEmpty());
    }

    @Test
    public void testGetFollowing_valid() throws Exception {
        List<String> following = uiService.getFollowing("3", "AUTH-testUser2" );
        assertFalse(following.isEmpty());
    }

    @Test
    public void testGetFollowing_invvalid() throws Exception {
        List<String> following = uiService.getFollowing("5", "AUTH-testUser2" );
        assertTrue(following.isEmpty());
    }

    @Test
    public void testAuth_invalid() throws Exception {

        Optional<User> user = uiService.auth("testUser2", "test123");

        assertTrue(user.isEmpty());

    }

    @Test
    public void testAuth_valid() throws Exception {
        Optional<User> user = uiService.auth("testUser2", "test1234");
        assertFalse(user.isEmpty());

        assertEquals("testUser2", user.get().getUsername());

    }

    @Test
    public void testAddUser_valid() throws Exception {
        Optional<User> user = uiService.addUser("testUser2", "test1234");
        assertFalse(user.isEmpty());

        assertEquals("testUser2", user.get().getUsername());
    }

    @Test
    public void testAddUser_invalid() throws Exception {
        Optional<User> user = uiService.addUser("testUser2", "test123");

        assertTrue(user.isEmpty());
    }

    @Test
    public void testPost_valid() throws Exception {
        TweetDTO tweetDTO = new TweetDTO();
        tweetDTO.setContent("Hello world test");
        UUID uid2 = UUID.fromString("f0d12829-cb9b-4be9-837d-d75e931ba898");
        tweetDTO.setUserId(1L);
        tweetDTO.setId(uid2);
        WebPost post = new WebPost();
        post.setContent("Hello world test");
        uiService.post(1L,post,"AUTH-testUser2");
    }

    @Test
    public void testFollow_valid() throws Exception {
        when(RetwisSecurity.getUid()).thenReturn("2");
        uiService.follow("testUser2");
    }

    @Test
    public void testUNFollow_valid() throws Exception {
        when(RetwisSecurity.getUid()).thenReturn("2");

        uiService.stopFollowing("testUser2");

    }

    @Test
    public void testIsPostValid() {

        when(RetwisSecurity.getToken()).thenReturn("AUTH-testUser2");


        Optional<TweetDTO> tweet = uiService.isPostValid("f0d12829-cb9b-4be9-837d-d75e931ba898");

        assertFalse(tweet.isEmpty());
    }

    @Test
    public void testIsPostValid_invalid() {

        when(RetwisSecurity.getToken()).thenReturn("AUTH-testUser2");


        Optional<TweetDTO> tweet = uiService.isPostValid("f0d12829-cb9b-4be9-837d-d75e931ba891");

        assertTrue(tweet.isEmpty());
    }

    @Test
    public void testIsFollowing_valid() throws Exception {
        when(RetwisSecurity.getToken()).thenReturn("AUTH-testUser2");


        boolean result = uiService.isFollowing("1", "2");
        assertTrue(result);
    }

    @Test
    public void testIsFollowing_invalid() throws Exception {
        when(RetwisSecurity.getToken()).thenReturn("AUTH-testUser2");


        boolean result = uiService.isFollowing("1", "4");
        assertFalse(result);
    }

    @Test
    public void testCommonFollowers_valid()  throws Exception {
        when(RetwisSecurity.getToken()).thenReturn("AUTH-testUser2");

        List<String> common = uiService.commonFollowers("1","2");

        assertFalse(common.isEmpty());
        assertEquals(2, common.size());

    }



    @Test
    public void testCommonFollowers_invalid()  throws Exception {
        when(RetwisSecurity.getToken()).thenReturn("AUTH-testUser2");

        List<String> common = uiService.commonFollowers("1","7");

        assertTrue(common.isEmpty());
        assertEquals(0, common.size());

    }


    @Test
    public void testGetTimeline_valid()  throws Exception {
        when(RetwisSecurity.getToken()).thenReturn("AUTH-testUser2");

        List<WebPost> posts = uiService.getTimeline("1", 1);

        assertFalse(posts.isEmpty());
        assertEquals(1, posts.size());
    }

    @Test
    public void testGetTimeline_invalid()  throws Exception {
        when(RetwisSecurity.getToken()).thenReturn("AUTH-testUser2");

        List<WebPost> posts = uiService.getTimeline("2", 1);

        assertTrue(posts.isEmpty());
        assertEquals(0, posts.size());

    }

    @Test
    public void testHasMoreTimeline_valid()  throws Exception {
        when(RetwisSecurity.getToken()).thenReturn("AUTH-testUser2");
        boolean result = uiService.hasMoreTimeline("1", 1);

        assertTrue(result);

    }


    @Test
    public void testHasMoreTimeline_invalid()  throws Exception {
        when(RetwisSecurity.getToken()).thenReturn("AUTH-testUser2");
        boolean result = uiService.hasMoreTimeline("2", 1);

        assertFalse(result);


    }

    @Test
    public void testAlsoFollowed_valid()  throws Exception {
        when(RetwisSecurity.getToken()).thenReturn("AUTH-testUser2");

        List<String> list = uiService.alsoFollowed("3","1");
        assertFalse(list.isEmpty());

        assertEquals(1, list.size());

        assertEquals("testUser2", list.get(0));

    }

}
