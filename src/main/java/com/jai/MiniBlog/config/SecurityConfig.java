package com.jai.MiniBlog.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


@Configuration
public class SecurityConfig {
    private JwtFilter jwtFilter;
    
	public SecurityConfig(JwtFilter jwtFilter) {
		super();
		this.jwtFilter = jwtFilter;
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
		http
		    .authorizeHttpRequests(auth -> auth
		    		.requestMatchers("/users/**","/posts","/posts/{id}").permitAll()
		    		.anyRequest().authenticated()
		    		)
		    .csrf(csrf -> csrf.disable())
		    .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);
		return http.build();
	}
}
