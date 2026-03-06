package com.example.Quora.Config;

import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.tags.Tag;

@Configuration
@OpenAPIDefinition(info = @Info(title = "Quora APIs", version = "1.0", description = "API documentation for Quora application"), tags = {
		@Tag(name = "User APIs", description = "User management APIs"),
		@Tag(name = "Question APIs", description = "APIs for managing questions"),
		@Tag(name = "Answer APIs", description = "APIs for creating and retrieving answers"),
		@Tag(name = "Comment APIs", description = "Operation related to comments"),
		@Tag(name = "Like APIs", description = "Operation related to likes"),
		@Tag(name = "Role APIs", description = "APIs for managing roles") })
public class SwaggerConfig {

}