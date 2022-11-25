package com.example.antMatchers.service;

import com.example.antMatchers.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Optional;

@Service
public class JwtUserDetailService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Optional<com.example.antMatchers.entity.User> user = userRepository.findByUsername(username);
        if(!user.isEmpty()){
            return new User(user.get().getUsername(), user.get().getPassword(), new ArrayList<>());
        }
        else{
            throw new UsernameNotFoundException("Invalid Username or Password");
        }

    }
}
