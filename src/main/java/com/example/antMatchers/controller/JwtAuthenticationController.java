package com.example.antMatchers.controller;


import com.example.antMatchers.dto.UserDto;
import com.example.antMatchers.entity.User;
import com.example.antMatchers.model.ErrorResponse;
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
        } catch (BadCredentialsException exception) {
            return new ResponseEntity(new ErrorResponse(exception.getMessage(),HttpStatus.BAD_REQUEST), HttpStatus.BAD_REQUEST);
        }
        catch(Exception exception){
            return new ResponseEntity(new ErrorResponse(exception.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping(value = "/authenticate")
    public ResponseEntity<?> createAuthenticationToken(@RequestBody JwtRequest jwtRequest) throws Exception {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(jwtRequest.getUsername(), jwtRequest.getPassword()));
        } catch (DisabledException e) {
            return new ResponseEntity(new ErrorResponse(e.getMessage(),HttpStatus.NOT_FOUND),HttpStatus.NOT_FOUND);
        } catch (BadCredentialsException e) {
            return new ResponseEntity(new ErrorResponse(e.getMessage(),HttpStatus.NOT_FOUND),HttpStatus.NOT_FOUND);
        }
        final UserDetails userDetails = jwtUserDetailService.loadUserByUsername(jwtRequest.getUsername());
        final String token = jwtTokenUtil.generateToken(userDetails);

        return new ResponseEntity(new JwtResponse(token), HttpStatus.OK);
    }


}
