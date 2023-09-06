package com.backend.payanam;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.backend.payanam.model.Role;
import com.backend.payanam.model.User;
import com.backend.payanam.model.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@SpringBootApplication
@RequiredArgsConstructor
public class PayanamApplication {
	
	private final PasswordEncoder encoder;

	public static void main(String[] args) {
		SpringApplication.run(PayanamApplication.class, args);
	}
	
//	@Bean
//	CommandLineRunner run(UserRepository userRepository) {
//		return args -> {
//			User admin1 = User.builder()
//					.name("Flavian Diol D")
//					.email("flaviandiold@gmail.com")
//					.password(encoder.encode("admin"))
//					.role(Role.ADMIN)
//					.build();
//			User admin2 = User.builder()
//					.name("Mahesh Kumar G")
//					.email("maheshofficial0138@gmail.com")
//					.password(encoder.encode("admin"))
//					.role(Role.ADMIN)
//					.build();
//			userRepository.save(admin1);
//			userRepository.save(admin2);
//		};
//	}

}
