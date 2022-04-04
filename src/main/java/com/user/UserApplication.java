package com.user;

import org.springdoc.core.GroupedOpenApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityScheme;

@SpringBootApplication
@OpenAPIDefinition(info = @Info(title = "Api for Users ", version = "3.0", description = "API for user administration "))
@SecurityScheme(name = "Jwt_Token", scheme = "bearer", type = SecuritySchemeType.HTTP, in = SecuritySchemeIn.HEADER)
public class UserApplication {

	@Autowired
	private Environment environment;

	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public GroupedOpenApi i1() {
		if (environment.getProperty("show.internal.api", "true").equalsIgnoreCase("true")) {
			return GroupedOpenApi.builder().group("i1").packagesToScan("com.user.i1").build();
		} else {
			return null;
		}

	}

	@Bean
	public GroupedOpenApi V2() {
		return GroupedOpenApi.builder().group("v1").packagesToScan("com.user.v1").build();
	}

	public static void main(String[] args) {
		SpringApplication.run(UserApplication.class, args);
	}

}
