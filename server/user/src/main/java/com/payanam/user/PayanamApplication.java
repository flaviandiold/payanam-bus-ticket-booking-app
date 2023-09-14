package com.payanam.user;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.payanam.user.model.Role;
import com.payanam.user.model.User;
import com.payanam.user.repository.UserRepository;

import jakarta.annotation.PreDestroy;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@SpringBootApplication
@Slf4j
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
	
	@PreDestroy
	public void onExit() {
			log.info("###Stoping###");
//	   try {
//		   Thread.sleep(5);
//	   } catch (InterruptedException e) {
//		   	log.error("", e);;
//	   }
	    	log.info("###Stop FROM THE LIFECYCLE###");
	}
	
}
