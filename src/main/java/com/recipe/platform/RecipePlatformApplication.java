package com.recipe.platform;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class RecipePlatformApplication {

	public static void main(String[] args) {

		SpringApplication.run(RecipePlatformApplication.class, args);
	}

}
