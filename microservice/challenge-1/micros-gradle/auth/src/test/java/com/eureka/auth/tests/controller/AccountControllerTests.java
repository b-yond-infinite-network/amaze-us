package com.eureka.auth.tests.controller;


import com.eureka.auth.controller.AccountController;
import com.eureka.auth.domain.User;
import com.eureka.auth.service.AccountService;
import com.eureka.auth.tests.util.SignupMatcher;
import com.eureka.common.domain.dto.SignUpRequest;
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
import org.springframework.web.util.NestedServletException;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.argThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(loader= AnnotationConfigWebContextLoader.class)
public class AccountControllerTests {

    private MockMvc mockMvc;
    private AccountService accountServiceMock;
    private AccountController accountController;
    private static final MediaType APPLICATION_JSON_UTF8 = new MediaType(MediaType.APPLICATION_JSON.getType(), MediaType.APPLICATION_JSON.getSubtype(), Charset.forName("utf8"));

    @Autowired
    protected WebApplicationContext wac;
    SignUpRequest payload;
    User created;

    @Before
    public void setUp() throws Exception {

        payload = new SignUpRequest();
        payload.setPassword("lol1234567000");
        payload.setUsername("admin");
        created =new User();
        created.setPassword("lol1234567000");
        created.setUsername("admin");
        created.setId(1L);

        mockMvc = webAppContextSetup(wac).alwaysExpect(status().isOk()).build();
        MockitoAnnotations.initMocks(this);

        accountServiceMock = Mockito.mock(AccountService.class);
        accountController = new AccountController(accountServiceMock);
        Mockito.when(accountServiceMock.checkExistance("magg")).thenReturn(true);
        Mockito.when(accountServiceMock.checkExistance("admin")).thenReturn(false);


        Mockito.when(accountServiceMock.find("magg")).thenReturn(Optional.of(created));
        Mockito.when(accountServiceMock.find("admin")).thenReturn((Optional.empty()));

        Mockito.when(accountServiceMock.create(argThat(new SignupMatcher(payload.getUsername(), payload.getPassword())))).thenReturn(Optional.of(created)  );

        mockMvc = MockMvcBuilders.standaloneSetup(accountController).build();

    }


    @Test
    public void createTest() throws Exception {

        mockMvc.perform(post("/auth/signup")
                .contentType(APPLICATION_JSON_UTF8)
                .content(convertObjectToJsonBytes(payload))
                )
                .andExpect(status().isCreated());
    }

    @Test
    public void createTestFailAlreadyUsed() throws Exception {

        SignUpRequest p = new SignUpRequest();
        p.setPassword("lol1234567000");
        p.setUsername("magg");

        mockMvc.perform(post("/auth/signup")
                .contentType(APPLICATION_JSON_UTF8)
                .content(convertObjectToJsonBytes(p))
        )
                .andExpect(status().isBadRequest());
    }



    @Test
    public void findTest() throws Exception {

        mockMvc.perform(get("/auth/magg")
                .contentType(APPLICATION_JSON_UTF8)
        )
                .andExpect(status().isOk());
    }

    @Test(expected = NestedServletException.class)
    public void findTestFail() throws Exception {
            mockMvc.perform(get("/auth/admin")
                    .contentType(APPLICATION_JSON_UTF8)
            );
    }

    public  byte[] convertObjectToJsonBytes(Object object) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        return mapper.writeValueAsBytes(object);
    }

}
