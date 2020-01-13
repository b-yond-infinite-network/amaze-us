package com.eureka.ui.controller;


import com.eureka.common.domain.User;
import com.eureka.common.domain.dto.TweetDTO;
import com.eureka.common.web.NoSuchDataException;
import com.eureka.common.web.WebPost;
import com.eureka.ui.RetwisSecurity;
import com.eureka.ui.service.UiService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PowerMockIgnore;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.modules.junit4.PowerMockRunnerDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.AnnotationConfigWebContextLoader;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import javax.servlet.http.Cookie;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static com.eureka.ui.web.CookieInterceptor.RETWIS_COOKIE;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.hasProperty;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.contains;
import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_FORM_URLENCODED;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;


@RunWith(PowerMockRunner.class)
@PowerMockRunnerDelegate(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(loader= AnnotationConfigWebContextLoader.class)
@PrepareForTest(RetwisSecurity.class)
@PowerMockIgnore({ "javax.management.*", "com.sun.org.apache.xerces.*", "javax.xml.*",
        "org.xml.*", "org.w3c.dom.*", "com.sun.org.apache.xalan.*", "javax.activation.*" })
public class UiControllerTests {

    private RetwisController retwisController;

    private MockMvc mockMvc;
    private UiService uiService;

    @Autowired
    protected WebApplicationContext wac;

    SimpleDateFormat sdf = new SimpleDateFormat("yyyy MMM dd HH:mm:ss");
    Calendar calendar = new GregorianCalendar(2013,0,31);

    @Before
    public void setUp() throws Exception {

        mockMvc = webAppContextSetup(wac).alwaysExpect(status().isOk()).build();
        MockitoAnnotations.initMocks(this);

        Mockito.mock(UiService.class);
        uiService = Mockito.mock(UiService.class);

        retwisController = new RetwisController(uiService);

        this.mockMvc = standaloneSetup(retwisController).build();
        PowerMockito.mockStatic(RetwisSecurity.class);

        InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
        viewResolver.setPrefix("/WEB-INF/jsp/");
        viewResolver.setSuffix(".jsp");

        mockMvc = MockMvcBuilders.standaloneSetup(retwisController).build();


        User user1 = new User();
        user1.setId(1L);
        user1.setUsername("testUser2");
        user1.setPassword("test1234");
        user1.setAuthKey("AUTH-testUser2");

        TweetDTO tweetDTO = new TweetDTO();
        tweetDTO.setUserId(1L);
        tweetDTO.setContent("Hello world");
        tweetDTO.setTimestamp(calendar.getTimeInMillis());
        tweetDTO.setId(UUID.fromString("6d716315-2af4-49b8-ac0f-59343b249343"));

        WebPost post = new WebPost();
        post.setContent("Hello world");
        post.setTime(String.valueOf(tweetDTO.getTimestamp()));
        post.setName("testUser2");
        post.setPid("6d716315-2af4-49b8-ac0f-59343b249343");

        List<String> follow = new ArrayList<>();
        follow.add("jona1");
        follow.add("papmela");
        follow.add("google");
        follow.add("francelyabreu");

        User user2 = new User();
        user1.setId(2L);
        user1.setUsername("testUser1");
        user1.setPassword("test1234");
        user1.setAuthKey("AUTH-testUser1");


        when(uiService.isUserValid("testUser1")).thenReturn(Optional.of(2L));
        when(uiService.isUserValid("testUser2")).thenReturn(Optional.empty());
        when(uiService.addUser("testUser2", "test1234")).thenReturn(Optional.of(user1));
        when(uiService.auth("testUser2", "test1234")).thenReturn(Optional.of(user1));
        when(uiService.isPostValid("6d716315-2af4-49b8-ac0f-59343b249343")).thenReturn(Optional.of(tweetDTO));
        when(uiService.getPost(Optional.of(tweetDTO))).thenReturn(post);

        when(uiService.isPostValid("6d716315-2af4-49b8-ac0f-59343b249344")).thenReturn(Optional.empty());
        when(uiService.getPost(Optional.empty())).thenReturn(null);
        when(uiService.findName("testUser1")).thenReturn(Optional.of(user2));

        when(uiService.getFollowers("2", "AUTH-testUser1")).thenReturn(follow);
        when(uiService.getFollowing("2", "AUTH-testUser1")).thenReturn(follow);
        when(uiService.commonFollowers("2", "1")).thenReturn(follow);
        when(uiService.findUid("testUser1")).thenReturn(Optional.of(2L));

    }

    @Test
    public void testHomePageUnsigned() throws Exception {

        when(RetwisSecurity.isSignedIn()).thenReturn(false);


        mockMvc.perform(get("/"))
                .andExpect(status().isOk())
                .andExpect(view().name("timeline"))
        ;
    }

    @Test
    public void testHomePageSigned() throws Exception {

        when(RetwisSecurity.isSignedIn()).thenReturn(true);
        when(RetwisSecurity.getName()).thenReturn("testUser2");


        mockMvc.perform(get("/"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/!testUser2"))
        ;
    }

    @Test
    public void testSignUpAlreadyUsedName() throws Exception {
        mockMvc.perform(post("/signUp")
                .contentType(APPLICATION_FORM_URLENCODED) //from MediaType
                .param("name", "testUser1")
                .param("pass", "test1234")
                .param("pass2", "test1234"))
                .andExpect(status().isOk())
                .andExpect(view().name("signin"))
                .andExpect(model().attribute("errorduplicateuser", true))
        ;

    }

    @Test
    public void testSignUpPassMismatch() throws Exception {
        mockMvc.perform(post("/signUp")
                .contentType(APPLICATION_FORM_URLENCODED) //from MediaType
                .param("name", "testUser2")
                .param("pass", "test1234")
                .param("pass2", "test124"))
                .andExpect(status().isOk())
                .andExpect(view().name("signin"))
                .andExpect(model().attribute("errormatch", true))
        ;

    }



    @Test
    public void testSignUpValid() throws Exception {
        mockMvc.perform(post("/signUp")
                .contentType(APPLICATION_FORM_URLENCODED) //from MediaType
                .param("name", "testUser2")
                .param("pass", "test1234")
                .param("pass2", "test1234"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/!testUser2"))
        ;

    }

    @Test
    public void testSignInValid() throws Exception {
        mockMvc.perform(post("/signIn")
                .contentType(APPLICATION_FORM_URLENCODED) //from MediaType
                .param("name", "testUser2")
                .param("pass", "test1234"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/!testUser2"))
        ;

    }

    @Test
    public void testSignInPassMismatch() throws Exception {
        mockMvc.perform(post("/signIn")
                .contentType(APPLICATION_FORM_URLENCODED) //from MediaType
                .param("name", "testUser2"))
                .andExpect(status().isOk())
                .andExpect(view().name("signin"))
                .andExpect(model().attribute("errorpass", true))
        ;

    }

    @Test
    public void testSignInNameMismatch() throws Exception {
        mockMvc.perform(post("/signIn")
                .contentType(APPLICATION_FORM_URLENCODED) //from MediaType
                .param("pass", "test13"))
                .andExpect(status().isOk())
                .andExpect(view().name("signin"))
                .andExpect(model().attribute("errorpass", true))
        ;

    }

    @Test
    public void testPosts() throws Exception {

        String name = "testUser2";
        WebPost post = new WebPost();
        mockMvc.perform(post("/!" + name)
                .contentType(APPLICATION_FORM_URLENCODED) //from MediaType
                .param("content", "Hello world")
                )
                .andExpect(status().isOk())
        ;

    }

    @Test
    public void testFollow() throws Exception {

        String name = "testUser2";
        mockMvc.perform(post("/!" + name+"/follow")
                .contentType(APPLICATION_FORM_URLENCODED) //from MediaType
        )
                .andExpect(status().isOk())
                ;

    }

    @Test
    public void testUnFollow() throws Exception {

        String name = "testUser2";
        mockMvc.perform(post("/!" + name+"/stopfollowing")
                .contentType(APPLICATION_FORM_URLENCODED) //from MediaType
        )
                .andExpect(status().isOk())
        ;

    }

    @Test
    public void testStatusValid() throws Exception {

        WebPost post = new WebPost();
        post.setContent("Hello world");
        post.setTime(String.valueOf(calendar.getTimeInMillis()));
        post.setName("testUser2");
        post.setPid("6d716315-2af4-49b8-ac0f-59343b249343");

        mockMvc.perform(get("/status")
                .param("pid", "6d716315-2af4-49b8-ac0f-59343b249343"))
                .andExpect(status().isOk())
                .andExpect(view().name("statis"))
                .andExpect(model().attribute("posts",
                                hasProperty("content", is(post.getContent()))))
                .andExpect(model().attribute("posts",
                                hasProperty("name", is("testUser2"))))

                ;
        ;
    }

    @Test
    public void testStatusInValid() throws Exception {

        mockMvc.perform(get("/status")
                .param("pid", "6d716315-2af4-49b8-ac0f-59343b2493434"))
                .andExpect(status().isOk())
                .andExpect(view().name("nodata"))
        ;
    }

    @Test
    public void testPostsList() throws Exception {

        when(RetwisSecurity.isSignedIn()).thenReturn(true);
        when(RetwisSecurity.getUid()).thenReturn("2");

        final Cookie someInformationCookie = new Cookie(RETWIS_COOKIE, "AUTH-testUser1");
        String name = "testUser1";
        mockMvc.perform(get("/!" + name)
                .cookie(someInformationCookie))
                .andExpect(status().isOk())
                .andExpect(view().name("home"))
                .andExpect(model().attribute("name", name))
                .andExpect(model().attribute("followers", hasItem(
                        "google"
                )))
                .andExpect(model().attribute("following", hasItem(
                        "google"
                )));
        ;

    }





}
