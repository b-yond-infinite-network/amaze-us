package com.example.userservice;

import com.example.userservice.model.User;
import com.example.userservice.repository.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@DirtiesContext
public class IntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserRepository userRepository;

    @Before
    public void setUp() {
        populateDatabase();
    }

    @After
    public void tearDown() {
        cleanDatabase();
    }

    private void cleanDatabase() {
        userRepository.deleteAll();
    }

    @Test
    public void testShouldFetchAllUsers() throws Exception {
        mockMvc.perform(get("/users"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$.[0].name", is("User1")))
                .andExpect(jsonPath("$.[0].email", is("email1@mail.com")))
                .andExpect(jsonPath("$.[0].description", is("description1")))
                .andExpect(jsonPath("$.[1].name", is("User2")))
                .andExpect(jsonPath("$.[1].email", is("email2@mail.com")))
                .andExpect(jsonPath("$.[1].description", is("description2")));
    }

    @Test
    public void testShouldSaveAUser() throws Exception {
        User user3 = new User("User3", "email3@mail.com", "description3");
        String payload = new ObjectMapper().writeValueAsString(user3);

        mockMvc.perform(post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(payload)
        ).andExpect(status().isCreated());

        assertThat(userRepository.findAll()).hasSize(3);
        assertThat(userRepository.findById("User3")).isNotEmpty();
    }

    private void populateDatabase() {
        User user1 = new User("User1", "email1@mail.com", "description1");
        User user2 = new User("User2", "email2@mail.com", "description2");
        userRepository.save(user1);
        userRepository.save(user2);
    }
}
