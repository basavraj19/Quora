package com.example.Quora.Config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SpringSecurity {

	@Bean
	public SecurityFilterChain security(HttpSecurity http) throws Exception {
		http.csrf().disable()
				.authorizeHttpRequests(
						auth -> auth.requestMatchers("/user/**").permitAll().anyRequest().authenticated())
				.httpBasic();

		return http.build();
	}
	
	@Bean
	public BCryptPasswordEncoder PasswordEncoder() {
		return new BCryptPasswordEncoder();
	}
}
