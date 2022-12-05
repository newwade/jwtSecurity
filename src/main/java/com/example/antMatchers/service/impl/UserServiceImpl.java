package com.example.antMatchers.service.impl;

import com.example.antMatchers.dto.UserDto;
import com.example.antMatchers.entity.User;
import com.example.antMatchers.repository.UserRepository;
import com.example.antMatchers.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public User createUserService(UserDto userDto) throws BadCredentialsException {
        Optional<User> existing_user = userRepository.findByUsername(userDto.getUsername());
        if(existing_user.isEmpty()){
            User user = new User();
            user.setUsername(userDto.getUsername());
            user.setPassword(passwordEncoder.encode(userDto.getPassword()));
            return userRepository.save(user);
        }
        else{
            throw new BadCredentialsException("username already exist");
        }

    }

}
