package com.example.antMatchers.service;

import com.example.antMatchers.dto.UserDto;
import com.example.antMatchers.entity.User;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.stereotype.Service;

@Service
public interface UserService {
    User createUserService(UserDto user) throws BadCredentialsException;
}
