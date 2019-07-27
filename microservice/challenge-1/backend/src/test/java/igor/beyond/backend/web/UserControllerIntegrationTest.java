package igor.beyond.backend.web;

import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
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


@ExtendWith(SpringExtension.class)
@SpringBootTest()
@AutoConfigureMockMvc
@TestPropertySource(locations = "classpath:application.properties")
public class UserControllerIntegrationTest {

	@Autowired
    private MockMvc mvc;
 
	@Tag("integration")
	@Test
    public void givenEmployees_whenGetEmployees_thenStatus200()
      throws Exception {
    	
        mvc.perform(get("/users/99")
          .contentType(MediaType.APPLICATION_JSON))
          .andExpect(status().isOk());
          //.andExpect(content()
          //.contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
          //.andExpect(jsonPath("$[0].name", is("bob")));
    }
}
