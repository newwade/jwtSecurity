package com.example.antMatchers.controller;


import com.example.antMatchers.dto.UserDto;
import com.example.antMatchers.entity.User;
import com.example.antMatchers.model.JwtRequest;
import com.example.antMatchers.model.JwtResponse;
import com.example.antMatchers.service.JwtUserDetailService;
import com.example.antMatchers.service.UserService;
import com.example.antMatchers.util.JwtTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@RestController
@RequestMapping("api/v1/auth")
@CrossOrigin
public class JwtAuthenticationController {

    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JwtTokenUtil jwtTokenUtil;
    @Autowired
    private UserService userService;
    @Autowired
    private JwtUserDetailService jwtUserDetailService;

    @PostMapping(value = "/register")
    public ResponseEntity<?> createUser(@RequestBody @Valid UserDto userDto) {
        try {
            return new ResponseEntity(userService.createUserService(userDto), HttpStatus.OK);
        } catch (Exception exception) {
            return new ResponseEntity(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping(value = "/authenticate")
    public ResponseEntity<?> createAuthenticationToken(@RequestBody JwtRequest jwtRequest) throws Exception {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(jwtRequest.getUsername(), jwtRequest.getPassword()));
        } catch (DisabledException e) {
            throw new Exception("User Disabled", e);
        } catch (BadCredentialsException e) {
            throw new Exception("Invalid Credentials", e);
        }

        final UserDetails userDetails = jwtUserDetailService.loadUserByUsername(jwtRequest.getUsername());
        final String token = jwtTokenUtil.generateToken(userDetails);

        return new ResponseEntity(new JwtResponse(token), HttpStatus.OK);
    }


}
