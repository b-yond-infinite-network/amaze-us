package igor.beyond.backend.web;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
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
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import igor.beyond.backend.entities.User;
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
        
    @Test
    @Tag("unit")
	@DisplayName("Retrive existing user from database")
    public void givenIdOfUser_returnUserFromDatabase_success() throws Exception {
         
         given(userService.getUserById(Mockito.anyLong())).willReturn(Optional.of(DataProvider.getDbUser()));
         
         mockMvc.perform(get(DataProvider.VALID_ID_URI)
        	      .contentType(MediaType.APPLICATION_JSON))
        	      .andExpect(status().isOk())
        	      .andExpect(jsonPath("$.result.length()", is(4)))
        	      .andExpect(jsonPath("$.result.id", is(DataProvider.getDbUser().getId().intValue())))
        	      .andExpect(jsonPath("$.result.name", is(DataProvider.getDbUser().getName())))
        	      .andExpect(jsonPath("$.result.email", is(DataProvider.getDbUser().getEmail())))
        	      .andExpect(jsonPath("$.result.description", is(DataProvider.getDbUser().getDescription())));
         verify(userService, times(1)).getUserById(Mockito.anyLong());
    }
   
    @Test
    @Tag("unit")
	@DisplayName("Retrive non-existing user from database")
    public void givenIdOfUser_returnUserNonExistentFromDatabase_badRequest() throws Exception {
    	 String res = null;
         Optional<User> nonExistentUser = Optional.empty();
         given(userService.getUserById(Mockito.anyLong())).willReturn(nonExistentUser);
         
         mockMvc.perform(get(DataProvider.INVALID_ID_URI))
        	      .andExpect(status().isBadRequest())
        	      .andExpect(jsonPath("$.length()", is(3)))
        	      .andExpect(jsonPath("$.status", is(HttpStatus.BAD_REQUEST.value())))
        	      .andExpect(jsonPath("$.message", is("User with id 500 does not exist in database")))
        	      .andExpect(jsonPath("$.result", is(res)));
         
         verify(userService, times(1)).getUserById(Mockito.anyLong());
    }
    	
    @Test
    @Tag("unit")
    @DisplayName("Update existing user in database")
    public void givenUserIdandUserObject_updateUserbyId_returnstatus200() throws Exception {
    	
    	User user = DataProvider.userToUpdate();
        String f = "{\"name\":\"test10\",\"email\":\"test10@gmail.com\",\"description\":\"to update\"}}";
       
    	when(userService.getUserById(user.getId())).thenReturn(Optional.of(user));
    	when(userService.updateUser(user.getId(), user)).thenReturn(user);
    	    
    	mockMvc.perform(
    	         put("/users/{id}", user.getId())
    	                  .contentType(MediaType.APPLICATION_JSON)
    	                  .characterEncoding("UTF-8")
    	                  .content(f))
    	                  .andExpect(status().isOk())
    	                  .andExpect(jsonPath("$.length()", is(3)));
    	    
    	    verify(userService, times(1)).getUserById(user.getId());

    	}
   
}
