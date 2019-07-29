package igor.beyond.backend.web;

import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import igor.beyond.backend.utils.DataProvider;


@ExtendWith(SpringExtension.class)
@SpringBootTest()
@AutoConfigureMockMvc
@TestPropertySource(locations = "classpath:application.properties")
public class UserControllerIntegrationTest {

	@Autowired
    private MockMvc mvc;
 
	@Test
	@Tag("integration")
    public void givenValidId_returnUser_thenStatus200()
      throws Exception {
    	
        mvc.perform(get("/users/1")
          .contentType(MediaType.APPLICATION_JSON))
          .andExpect(status().isOk())
          .andExpect(jsonPath("$.result.length()", is(4)))
	      .andExpect(jsonPath("$.result.id", is(DataProvider.realDbUser().getId().intValue())))
	      .andExpect(jsonPath("$.result.name", is(DataProvider.realDbUser().getName())))
	      .andExpect(jsonPath("$.result.email", is(DataProvider.realDbUser().getEmail())))
	      .andExpect(jsonPath("$.result.description", is(DataProvider.realDbUser().getDescription())));
    }
	
}
