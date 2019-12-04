package com.eureka.auth.service;

import com.eureka.auth.domain.User;
import com.eureka.auth.repository.UserRepository;
import com.eureka.auth.security.Bcrypt;
import com.eureka.common.domain.dto.SignUpRequest;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AccountService {


    private UserRepository userRepository;
    private Bcrypt bcrypt;

    public AccountService(UserRepository userRepository, Bcrypt bcrypt) {
        this.userRepository = userRepository;
        this.bcrypt = bcrypt;
    }

    public boolean checkExistance(String username) {
        Optional<User> account = userRepository.findOneByUsername(username);

        if(!account.isPresent()) {
            return false;
        }

        return true;
    }


    public Optional<User> create (SignUpRequest request){
        // Creating user's account
        User user = new User();

        user.setUsername(request.getUsername());

        user.setPassword(bcrypt.passwordEncoder().encode(request.getPassword()));

        return Optional.of(userRepository.save(user));
    }


    public Optional<User> find(String username){
        return userRepository.findOneByUsername(username);
    }

    public Optional<User> findByID(Long id){
        return userRepository.findById(id);
    }

}
