package igor.beyond.backend.service.impl;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import igor.beyond.backend.entities.User;
import igor.beyond.backend.exception.DuplicateEmailException;
import igor.beyond.backend.repositories.UserRepository;
import igor.beyond.backend.service.UserService;

@Service
public class UserServiceImpl implements UserService {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(UserServiceImpl.class);

	@Autowired
	private UserRepository userRepo;
	
	public List<User> getUsers() {
		LOGGER.info("Getting user all users from the database");
		return userRepo.findAll();
	}
	
	public Optional<User> getUserById(Long userId) {
		LOGGER.info("Getting user with id: " + userId + " from the database");
		return userRepo.findById(userId);
	}
	
	public User getUserByEmail(String email) {
		LOGGER.info("Getting user with email: " + email + " from the database");
		return userRepo.findByEmail(email);
	}
	
	public User createUser(User user) throws DuplicateEmailException {
		
		if (userRepo.findByEmail(user.getEmail()) != null) {
			throw new DuplicateEmailException("User with email " + user.getEmail() + " already exists. Please enter another email");
		}
		
		LOGGER.info("Saving user " + user.getName() + " to the database");
		return userRepo.save(user);
	}
	
	public User updateUser(Long userId, User user) throws DuplicateEmailException {
		
		Optional<User> updatedUser = userRepo.findById(userId);
		
		if (userRepo.findByEmail(user.getEmail()) != null) {
			throw new DuplicateEmailException("User with email " + user.getEmail() + " already exists. Please enter another email");
		}
	
		updatedUser.get().setName(user.getName());
		updatedUser.get().setDescription(user.getDescription());
		updatedUser.get().setEmail(user.getEmail());
		
		LOGGER.info("Updating user " + user.getName() + " in the database");
		return userRepo.save(updatedUser.get());
	}
	
	public void deleteByUserId(Long userId) {
		LOGGER.info("Deleting user with id: " + userId + " from the database");
		userRepo.deleteById(userId);
	}
}
