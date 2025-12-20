package com.example.Quora.Config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.example.Quora.CustomAccessDeniedHandler;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SpringSecurity {

	@Autowired
	private JwtAuthenticationFilter jwtAuthenticationFilter;

	@Autowired
	private CustomAccessDeniedHandler accessDeniedHandler;
	
	@Bean
	public SecurityFilterChain security(HttpSecurity http) throws Exception {

		http.csrf(csrf -> csrf.disable()).exceptionHandling(ex -> ex.accessDeniedHandler(accessDeniedHandler))
				.authorizeHttpRequests(auth -> auth.requestMatchers("/user/signUp", "/user/login",  "/swagger-ui/**",
				        "/v3/api-docs/**").permitAll()
						.anyRequest().authenticated());

		http.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

		return http.build();
	}

	@Bean
	public BCryptPasswordEncoder PasswordEncoder() {
		return new BCryptPasswordEncoder();
	}
}
