package igor.beyond.backend.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;

import org.springframework.test.context.junit.jupiter.SpringExtension;

import igor.beyond.backend.entities.User;
import igor.beyond.backend.exception.DuplicateEmailException;
import igor.beyond.backend.repositories.UserRepository;
import igor.beyond.backend.utils.DataProvider;

@ExtendWith(SpringExtension.class)
public class UserServiceTest {

	private final String EXISTING_EMAIL = "igor@gmail.com";
	private final String NEW_EMAIL = "jack@gmail.com";
	
	
	@InjectMocks
    private UserService userService = new UserService();
 
    @Mock
    private UserRepository userRepository;
    
    @Test
    @Tag("unit")
	@DisplayName("Saving new user with distinct email")
	public void whenCreateUserNewEmail_saveSuccess() throws DuplicateEmailException {

		Mockito.when(userRepository.findByEmail(NEW_EMAIL)).thenReturn(null);
		Mockito.when(userRepository.save(Mockito.any(User.class))).thenReturn(DataProvider.userToSave());
		
		User newDbUser = userService.createUser(DataProvider.userToSave());
		
		assertEquals(DataProvider.userToSave().getId(), newDbUser.getId());
				
	}
    
    @Test
    @Tag("unit")
    @DisplayName("Saving user with the same email. Throws exception")
    void whenCreateUserExistingEmail_exceptionThrown() throws DuplicateEmailException {
    	Mockito.when(userRepository.findByEmail(EXISTING_EMAIL)).thenReturn(DataProvider.getDbUser());
    	
    	DuplicateEmailException exception = assertThrows(DuplicateEmailException.class, () ->
    													 userService.createUser(DataProvider.getDbUser()));
    	assertEquals("User with email " + DataProvider.getDbUser().getEmail() + " already exists. Please enter another email.", exception.getMessage());
    }
}
