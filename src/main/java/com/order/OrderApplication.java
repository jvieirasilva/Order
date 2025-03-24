package com.order;


import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.order.repository.UserRepository;
import com.order.serviceImpl.AuthenticationService;


@SpringBootApplication
public class OrderApplication {
	
	public static void main(String[] args) {
		SpringApplication.run(OrderApplication.class, args);
	}
	
	@Bean
    public UserDetailsService userDetailsService(UserRepository repository) {
        return username -> repository.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }
	
	@Bean
	public CommandLineRunner createDefaultUser(AuthenticationService authenticationService) {
	    return args -> {
	        authenticationService.createDefaultUserIfNotExists(
	                "test@gmail.com",
	                "12345678",
	                "Usuário de Teste"
	        );
	    };
	}

}
