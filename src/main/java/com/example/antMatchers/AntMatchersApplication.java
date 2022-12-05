package com.example.antMatchers;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.WebApplicationInitializer;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;

@SpringBootApplication
public class AntMatchersApplication {

	public static void main(String[] args) {
		SpringApplication.run(AntMatchersApplication.class, args);
	}
	
	@Bean
	public PasswordEncoder passwordEncoder(){

		return new BCryptPasswordEncoder(15);

	}


}
