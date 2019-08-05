package com.example.userservice;

import com.example.userservice.model.User;
import com.example.userservice.repository.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@DirtiesContext
public class ExceptionHandlingTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserRepository userRepository;

    @Test
    public void testShouldReturnInternalServerErrorWhenExceptionOccursForGet() throws Exception {
        when(userRepository.findAll()).thenThrow(new RuntimeException());
        mockMvc.perform(get("/users")).andExpect(status().isInternalServerError());
    }

    @Test
    public void testShouldReturnInternalServerErrorWhenExceptionOccursForPost() throws Exception {
        when(userRepository.save(any(User.class))).thenThrow(new RuntimeException());
        User user = new User("User3", "email3@mail.com", "description3");
        String payload = new ObjectMapper().writeValueAsString(user);

        mockMvc.perform(post("/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(payload)
        ).andExpect(status().isInternalServerError());
    }
}
