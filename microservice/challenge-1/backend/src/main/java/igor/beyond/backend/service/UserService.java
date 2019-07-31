package igor.beyond.backend.service;

import java.util.List;
import java.util.Optional;

import igor.beyond.backend.entities.User;
import igor.beyond.backend.exception.DuplicateEmailException;

public interface UserService {

	public List<User> getUsers();
		
	public Optional<User> getUserById(Long userId);
		
	public User getUserByEmail(String email);
		
	public User createUser(User user) throws DuplicateEmailException;
		
	public User updateUser(Long userId, User user) throws DuplicateEmailException;
		
	public void deleteByUserId(Long userId);
}