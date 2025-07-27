package com.example.security.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
	
	// This is used for Filter request reached before Servlet / server
	
	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
	    http
	        .csrf(csrf -> csrf.disable()) // Disable CSRF for testing / non-browser clients like Postman
	        .authorizeHttpRequests(auth -> auth
	            .anyRequest().permitAll() // ✅ Allow ALL requests
	        )
	        .httpBasic(); // Optional: Can be removed if no auth at all

	    return http.build();
	}

	// manual user here used for Praticing Purpose 
	public void configure(AuthenticationManagerBuilder auth) throws Exception {
	   auth.inMemoryAuthentication().withUser("john").password(this.passwordEncoder().encode("DSouza"));
	   auth.inMemoryAuthentication().withUser("Dhirendra").password(this.passwordEncoder().encode("1234"));
	}

	public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(10);
    }

}
