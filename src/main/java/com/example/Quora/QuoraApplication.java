package com.example.Quora;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing(auditorAwareRef = "autoSetCreatedAndModifiedByConfig")
public class QuoraApplication {

	public static void main(String[] args) {
		SpringApplication.run(QuoraApplication.class, args);
	}

}
