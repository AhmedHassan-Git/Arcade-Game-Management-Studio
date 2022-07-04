package com.game.studio;

import java.util.ArrayList;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.game.studio.models.user.AppRole;
import com.game.studio.services.AppUserService;
import com.game.studio.models.user.AppUser;

@SpringBootApplication
public class StudioApplication {

	public static void main(String[] args) {
		SpringApplication.run(StudioApplication.class, args);
	}

	@Bean
	CommandLineRunner run(AppUserService userService) {
		return args -> {
			userService.saveRole(new AppRole(null, "RU"));

			userService.saveRole(new AppRole(null, "RM"));
			userService.saveRole(new AppRole(null, "RA"));
			userService.saveRole(new AppRole(null, "RSA"));

			userService.persist(new AppUser(null, "Ahmed", "Ahmed", "1234", new ArrayList<>()));

			userService.addRole("Ahmed", "RM");
		};
	}

	@Bean
	PasswordEncoder passwordEncoder(){
		return new BCryptPasswordEncoder();
	}
}
