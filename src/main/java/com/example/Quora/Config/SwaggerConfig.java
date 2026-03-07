package com.example.Quora.Config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;

@Configuration
@OpenAPIDefinition(info = @Info(title = "Quora APIs", version = "1.0", description = "API documentation for Quora application"))
public class SwaggerConfig {

	@Bean
	public OpenAPI openAPI() {

		return new OpenAPI().addSecurityItem(new SecurityRequirement().addList("cookieAuth"))
				.components(new Components().addSecuritySchemes("cookieAuth", new SecurityScheme()
						.type(SecurityScheme.Type.APIKEY).in(SecurityScheme.In.COOKIE).name("jwtToken")

				));
	}
}