package com.bookstorestaticwebsite.StaticBookStoreWebsite;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;


@SpringBootApplication
public class StaticBookStoreWebsiteApplication {

	public static void main(String[] args) {
		SpringApplication.run(StaticBookStoreWebsiteApplication.class, args);

		// Hash a password for testing
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		String hashedPassword = encoder.encode("password123");
		System.out.println("Hashed Password: " + hashedPassword);
	}
}
