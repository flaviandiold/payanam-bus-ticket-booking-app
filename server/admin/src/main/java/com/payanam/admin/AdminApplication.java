package com.payanam.admin;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.payanam.admin.model.Role;
import com.payanam.admin.model.Conductor;
import com.payanam.admin.repository.ConductorRepository;

import lombok.RequiredArgsConstructor;

@SpringBootApplication
@RequiredArgsConstructor
public class AdminApplication {
	
	private final PasswordEncoder encoder;
	
	public static void main(String[] args) {
		SpringApplication.run(AdminApplication.class, args);
	}
	
//	@Bean
//	CommandLineRunner run(ConductorRepository userRepository) {
//		return args -> {
//			Conductor admin1 = Conductor.builder()
//					.name("Flavian Diol D")
//					.email("flaviandiold@gmail.com")
//					.password(encoder.encode("admin"))
//					.role(Role.ADMIN)
//					.build();
//			Conductor admin2 = Conductor.builder()
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
