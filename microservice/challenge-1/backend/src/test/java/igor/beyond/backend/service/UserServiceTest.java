package igor.beyond.backend.service;

//import org.junit.After;
//import org.junit.Assert;
//import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
//import static org.junit.jupiter.api.Assertions.assertTrue;

//import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
//import org.mockito.MockitoSession;
//import org.mockito.quality.Strictness;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
//import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
//import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.boot.test.context.TestConfiguration;
//import org.springframework.boot.test.mock.mockito.MockBean;
//import org.springframework.context.annotation.Bean;
//import org.springframework.test.context.ContextConfiguration;

import org.springframework.test.context.junit.jupiter.SpringExtension;

import igor.beyond.backend.entities.User;
import igor.beyond.backend.exception.DuplicateEmailException;
import igor.beyond.backend.repositories.UserRepository;
import igor.beyond.backend.utils.DataProvider;

@ExtendWith(SpringExtension.class)
//@SpringBootTest
//@DataJpaTest
//@AutoConfigureTestDatabase
public class UserServiceTest {

	private final String EXISTING_EMAIL = "igor@gmail.com";
	private final String NEW_EMAIL = "jack@gmail.com";
	
	
	@InjectMocks
    private UserService userService = new UserService();
 
    @Mock
    private UserRepository userRepository;
    
    @BeforeEach
  	public void setUp() {
    	MockitoAnnotations.initMocks(this);
    }
    
    @Tag("unit")
    @Test
	@DisplayName("Saving new user with distinct email")
	public void whenCreateUserNewEmail_saveSuccess() throws DuplicateEmailException {

		Mockito.when(userRepository.findByEmail(NEW_EMAIL)).thenReturn(null);
		Mockito.when(userRepository.save(Mockito.any(User.class))).thenReturn(DataProvider.userToSave());
		
		User newDbUser = userService.createUser(DataProvider.userToSave());
		
		assertEquals(DataProvider.userToSave().getId(), newDbUser.getId());
				
	}
    
    @Tag("unit")
    @Test
    @DisplayName("Saving user with the same email. Throws exception")
    void whenCreateUserExistingEmail_exceptionThrown() throws DuplicateEmailException {
    	Mockito.when(userRepository.findByEmail(EXISTING_EMAIL)).thenReturn(DataProvider.getDbUser());
    	
    	DuplicateEmailException exception = assertThrows(DuplicateEmailException.class, () ->
    	userService.createUser(DataProvider.getDbUser()));
    	assertEquals("User with email " + DataProvider.getDbUser().getEmail() + " already exists. Please enter another email.", exception.getMessage());
    }
}
