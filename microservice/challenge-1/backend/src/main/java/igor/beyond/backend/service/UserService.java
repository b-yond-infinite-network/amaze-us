package igor.beyond.backend.service;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import igor.beyond.backend.entities.User;
import igor.beyond.backend.exception.DuplicateEmailException;
import igor.beyond.backend.repositories.UserRepository;

@Service
public class UserService {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(UserService.class);

	@Autowired
	private UserRepository userRepo;
	
	public List<User> getUsers() {
		LOGGER.info("Getting user all users from the database");
		return userRepo.findAll();
	}
	
	public Optional<User> getUserById(Long userId) {
		LOGGER.info("Getting user " + userId + " from the database");
		return userRepo.findById(userId);
	}
	
	public User getUserByEmail(String email) {
		LOGGER.info("Getting user " + email + " from the database");
		return userRepo.findByEmail(email);
	}
	
	public User createUser(User user) throws DuplicateEmailException {
		if (userRepo.findByEmail(user.getEmail()) != null) {
			throw new DuplicateEmailException("User with email " + user.getEmail() + " already exists. Please enter another email.");
		}
		LOGGER.info("Saving user " + user.getName() + " to the database");
		return userRepo.save(user);
	}
	
	public User updateUser(Long userId, User user) {
		Optional<User> u = userRepo.findById(userId);
		u.get().setId(userId);
		u.get().setName(user.getName());
		u.get().setDescription(user.getDescription());
		u.get().setEmail(user.getEmail());
		
		LOGGER.info("Saving user " + user.getName() + " to the database");
		return userRepo.save(u.get());
	}
	
	public void deleteByUserId(Long userId) {
		userRepo.deleteById(userId);
	}
}
