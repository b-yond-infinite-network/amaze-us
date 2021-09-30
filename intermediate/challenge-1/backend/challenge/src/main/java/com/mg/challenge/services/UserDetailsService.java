package com.mg.challenge.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.mg.challenge.pojos.Role;
import com.mg.challenge.pojos.User;
import com.mg.challenge.repositories.UserRepository;

@Service
public class UserDetailsService {

	@Autowired
	private UserRepository userRepository;

	public User loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userRepository.findByUsername(username);
		if (user == null) {
			throw new UsernameNotFoundException("User not found - username: " + username);
		}
		return user;
	}
	
	public List<Role> getUserRoles(String username) throws UsernameNotFoundException {
		User user = userRepository.findByUsername(username);
		if (user == null) {
			throw new UsernameNotFoundException("User not found - username: " + username);
		}
		return user.getRoles();
	}

}
