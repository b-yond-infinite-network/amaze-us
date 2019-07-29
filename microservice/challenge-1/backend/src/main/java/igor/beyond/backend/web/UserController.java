package igor.beyond.backend.web;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import igor.beyond.backend.entities.User;
import igor.beyond.backend.exception.DuplicateEmailException;
import igor.beyond.backend.service.UserService;
import igor.beyond.backend.web.ApiResponse;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping(value = "/users", produces = MediaType.APPLICATION_JSON_VALUE)
public class UserController {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);
	
	private final UserService userService;
	
	@Autowired
	public UserController(final UserService userService) {
	  this.userService = userService;
	}
	
	@GetMapping
	public ResponseEntity<ApiResponse<List<User>>> getUsers() {
		return ResponseEntity.ok(new ApiResponse<>(HttpStatus.OK.value(), "List of users fetched successfully.", userService.getUsers()));
	}
	
	@GetMapping(value = "/{id}")
	public ResponseEntity<ApiResponse<User>> getUserById(@PathVariable("id") final Long userId) {
		
		Optional<User> dbUser = userService.getUserById(userId);
		ApiResponse<User> apiRes = new ApiResponse<>();
		
		if (!dbUser.isPresent()) {
			
	          LOGGER.warn("User with Id " + userId + " does not exist in database");
	          apiRes.setStatus(HttpStatus.BAD_REQUEST.value());
	          apiRes.setMessage("User with id " + userId + " does not exist in database");
	          apiRes.setResult(null);
	          
	          return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(apiRes);
	      }
		
		apiRes.setStatus(HttpStatus.OK.value());
		apiRes.setMessage("Successfully fetcheed user with id " + userId);
		apiRes.setResult(dbUser.get());
        
        return ResponseEntity.status(HttpStatus.OK).body(apiRes);
	}
	  
	@PostMapping
	public ResponseEntity<ApiResponse<User>> createUser(@Valid @RequestBody User user) throws DuplicateEmailException {
	      return ResponseEntity.ok(new ApiResponse<>(HttpStatus.CREATED.value(), "Created user successfully", userService.createUser(user)));
	}
	
	@PutMapping(value = "/{id}")
	public ResponseEntity<ApiResponse<User>> updateUser(@PathVariable("id") Long userId, @Valid @RequestBody User user) {
		
		Optional<User> dbUser = userService.getUserById(userId);
		ApiResponse<User> apiRes = new ApiResponse<>();
		
		if (!dbUser.isPresent()) {
			
	          LOGGER.warn("User with Id " + userId + " does not exist in database");
	          apiRes.setStatus(HttpStatus.BAD_REQUEST.value());
	          apiRes.setMessage("User with Id " + userId + " does not exist in database");
	          apiRes.setResult(null);
	          
	          return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(apiRes);
	      }
		
		apiRes.setStatus(HttpStatus.OK.value());
		apiRes.setMessage("Successfully updated user with id " + userId);
		apiRes.setResult(userService.updateUser(userId, user));
        
        return ResponseEntity.status(HttpStatus.OK).body(apiRes);
	}
	
	@DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<User>> deleteUser(@PathVariable("id") Long userId) {
		
		Optional<User> dbUser = userService.getUserById(userId);
		ApiResponse<User> apiRes = new ApiResponse<>();
		
		if (!dbUser.isPresent()) {
			
	          LOGGER.warn("User with Id " + userId + " does not exist in database");
	          apiRes.setStatus(HttpStatus.BAD_REQUEST.value());
	          apiRes.setMessage("User with Id " + userId + " does not exist in database");
	          apiRes.setResult(null);
	          
	          return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(apiRes);
	      }
		
		apiRes.setStatus(HttpStatus.OK.value());
		apiRes.setMessage("Successfully deleted user with id " + userId);
		apiRes.setResult(dbUser.get());
        
        userService.deleteByUserId(userId);
        return ResponseEntity.status(HttpStatus.OK).body(apiRes);
    }
	   
}
