package com.millenium.devopsbuddy;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories(basePackages = "com.millenium.devopsbuddy.backend.persistence.repositories")
public class DevopsbuddyApplication {

	public static void main(String[] args) {
		SpringApplication.run(DevopsbuddyApplication.class, args);
	}

}
