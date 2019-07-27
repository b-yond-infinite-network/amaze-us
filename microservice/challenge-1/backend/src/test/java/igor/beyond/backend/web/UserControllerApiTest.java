package igor.beyond.backend.web;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;

import org.mockito.Mockito;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import igor.beyond.backend.service.UserService;
import igor.beyond.backend.utils.DataProvider;

import java.util.Optional;

@ExtendWith(SpringExtension.class)
@WebMvcTest(UserController.class)
public class UserControllerApiTest {

    @Autowired
	private MockMvc mockMvc;
 
    @MockBean
    private UserService userService;
    
    @Tag("unit")
    @Test
	@DisplayName("Get user with id 1 from db")
    public void givenIdOfUser_returnUserFromDatabase_success() throws Exception {
         
         given(userService.getUserById(Mockito.anyLong())).willReturn(Optional.of(DataProvider.getDbUser()));
         
         mockMvc.perform(get("/users/1")
        	      .contentType(MediaType.APPLICATION_JSON))
        	      .andExpect(status().isOk())
        	      .andExpect(jsonPath("$.result.length()", is(4)))
        	      .andExpect(jsonPath("$.result.id", is(DataProvider.getDbUser().getId().intValue())))
        	      .andExpect(jsonPath("$.result.name", is(DataProvider.getDbUser().getName())))
        	      .andExpect(jsonPath("$.result.email", is(DataProvider.getDbUser().getEmail())))
        	      .andExpect(jsonPath("$.result.description", is(DataProvider.getDbUser().getDescription())));
    }
    
    
}
