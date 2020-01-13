package com.eureka.social.controller;

import com.eureka.common.domain.dto.FollowDTO;
import com.eureka.social.controllers.SocialController;
import com.eureka.social.domain.Followers;
import com.eureka.social.domain.Following;
import com.eureka.social.service.FollowersService;
import com.eureka.social.service.FollowingService;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.AnnotationConfigWebContextLoader;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(loader= AnnotationConfigWebContextLoader.class)
public class SocialControllerTests {

    private MockMvc mockMvc;
    private FollowersService followersService;
    private FollowingService followingService;
    private SocialController socialController;
    private static final MediaType APPLICATION_JSON_UTF8 = new MediaType(MediaType.APPLICATION_JSON.getType(), MediaType.APPLICATION_JSON.getSubtype(), Charset.forName("utf8"));

    @Autowired
    protected WebApplicationContext wac;


    @Before
    public void setUp() throws Exception {


        mockMvc = webAppContextSetup(wac).alwaysExpect(status().isOk()).build();
        MockitoAnnotations.initMocks(this);

        followersService = Mockito.mock(FollowersService.class);
        followingService = Mockito.mock(FollowingService.class);

        socialController = new SocialController(followersService, followingService);

        List<Followers> followers = new ArrayList<>();
        Followers f1 = new Followers();
        f1.setDestinationId(1L);
        f1.setSourceId(2L);
        Followers f2 = new Followers();
        f2.setDestinationId(1L);
        f2.setSourceId(3L);


        List<Followers> followers2 = new ArrayList<>();
        Followers f11 = new Followers();
        f11.setDestinationId(2L);
        f11.setSourceId(3L);
        Followers f22 = new Followers();
        f22.setDestinationId(2L);
        f22.setSourceId(5L);




        followers.add(f1);
        followers.add(f2);


        List<Following> following = new ArrayList<>();
        Following f3 = new Following();
        f1.setDestinationId(2L);
        f1.setSourceId(1L);
        Following f4 = new Following();
        f2.setDestinationId(3L);
        f2.setSourceId(1L);

        following.add(f3);
        following.add(f4);

        Mockito.when(followersService.getFollowers(1L)).thenReturn(followers);
        Mockito.when(followersService.getFollowers(2L)).thenReturn(followers2);


        Mockito.when(followingService.getFollowing(1L)).thenReturn(following);


        FollowDTO dto = new FollowDTO();
        dto.setSourceId(1L);
        dto.setDestinationId(2L);


        Mockito.when(followingService.find(dto)).thenReturn(Optional.of(f3));


        mockMvc = MockMvcBuilders.standaloneSetup(socialController).build();

    }


    @Test
    public void createTest() throws Exception {

        FollowDTO followDTO = new FollowDTO();
        followDTO.setSourceId(1L);
        followDTO.setDestinationId(2L);


        mockMvc.perform(post("/social")
                .contentType(APPLICATION_JSON_UTF8)
                .content(convertObjectToJsonBytes(followDTO))
        )
                .andExpect(status().isCreated());
    }

    @Test
    public void deleteTest() throws Exception {

        FollowDTO followDTO = new FollowDTO();
        followDTO.setSourceId(1L);
        followDTO.setDestinationId(2L);


        mockMvc.perform(delete("/social")
                .contentType(APPLICATION_JSON_UTF8)
                .content(convertObjectToJsonBytes(followDTO))
        )
                .andExpect(status().isNoContent());
    }


    @Test
    public void getFollowers() throws Exception {


        mockMvc.perform(get("/social/1/followers")
                .contentType(APPLICATION_JSON_UTF8)
        )
                .andExpect(status().isOk());
    }


    @Test
    public void getFollowing() throws Exception {

        mockMvc.perform(get("/social/1/following")
                .contentType(APPLICATION_JSON_UTF8)
        )
                .andExpect(status().isOk());
    }


    @Test
    public void getFindRelation() throws Exception {

        mockMvc.perform(get("/social/1/2")
                .contentType(APPLICATION_JSON_UTF8)
        )
                .andExpect(status().isOk());
    }

    @Test
    public void getFindCommon() throws Exception {

        mockMvc.perform(get("/social/common/1/2")
                .contentType(APPLICATION_JSON_UTF8)
        )
                .andExpect(status().isOk());
    }



    public  byte[] convertObjectToJsonBytes(Object object) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        return mapper.writeValueAsBytes(object);
    }
}
