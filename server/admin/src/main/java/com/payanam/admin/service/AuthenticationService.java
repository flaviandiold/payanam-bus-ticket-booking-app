package com.payanam.admin.service;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.payanam.admin.model.Conductor;
import com.payanam.admin.model.Role;
import com.payanam.admin.model.template.AuthResponse;
import com.payanam.admin.repository.ConductorRepository;
import com.payanam.admin.utils.Mail;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthenticationService {
	
	private final ConductorRepository conductorRepo;
	private final PasswordEncoder encoder;
//	private final ConductorRepository conductorRepo;
	private final AuthenticationManager authManager;
	private final JwtTokenService service;
	private final Mail mail;
//	private final OTPRepository otpRepo;

	public AuthResponse register(String name, String email, String password) {
		if(conductorRepo.existsUserByEmail(email)) {
			log.info("User exists");
			return null;
		}
		Conductor conductor = Conductor.builder()
									.name(name)
									.email(email)
									.password(encoder.encode(password))
									.role(Role.CONDUCTOR)
									.build();
		conductorRepo.save(conductor);
		log.info("Successfully registered");
		return AuthResponse.builder()
				.message(name+" has been registered")
				.build();
	}

	public AuthResponse login(String email, String password) {
		if(conductorRepo.existsUserByEmail(email)) {
			try {
				Authentication auth = authManager
						.authenticate(new UsernamePasswordAuthenticationToken(email, password));
				return AuthResponse.builder()
						.message("Successfully logged in")
						.token(service.generateToken(auth))
						.build();
			} catch (Exception e) {
				System.out.println(e);
				return AuthResponse.builder()
					.message("Bad Credentials")
					.build();
			}
			
		}
		return null;
	}

//	public AuthResponse existsUser(String email) {
//		log.info("Insider existsUser");
//		Optional<Conductor> user = conductorRepo.findByEmail(email);
//		if(user.isPresent()){
//			Random rand = new Random();
//			Formatter form = new Formatter();
//			int otp = rand.nextInt(1000,10000);
//			String deviceId = form.format("%d-%d-%d-%d",rand.nextInt(1000,10000),rand.nextInt(1000,10000),rand.nextInt(1000,10000),rand.nextInt(1000,10000)).toString();
//			mail.sendMail(email, "Payanam Change Password", "Enter "+otp+" to verify your email");
//			form.close();
//			OTP entry = OTP.builder()
//							.user(user.get())
//							.deviceId(deviceId)
//							.otp(otp)
//							.validFrom(LocalTime.now(ZoneId.systemDefault()))
//							.validTo(LocalTime.now(ZoneId.systemDefault()).plus(3,ChronoUnit.MINUTES))
//							.build();
//			otpRepo.save(entry);
//			return AuthResponse.builder()
//					.message(deviceId)
//					.build();
//		}
//		return null;
//	}
//
//	public AuthResponse changePasswordTo(String deviceId, String password) {
//		if(otpRepo.existsById(deviceId)) {
//			String email = otpRepo.getEmailOf(deviceId);
//			String prevPassword = userRepo.getPasswordOf(email);
//			String encodedPassword = encoder.encode(password);
//			
//			if(encoder.matches(password, prevPassword)) {
//				return AuthResponse.builder()
//						.message("Same")
//						.build();
//			}
//			if(otpRepo.isStatusValid(deviceId)) {
//				userRepo.changeUserPassword(email, encodedPassword);
//				return AuthResponse.builder()
//						.message("Successfully changed your password")
//						.build();
//			}
//			return AuthResponse.builder()
//					.message("Not verified")
//					.build();
//		}
//		return null;
//	}
//
//	public AuthResponse verifyOTP(String deviceId, Integer otp) {
//		if(otpRepo.existsById(deviceId)) {
//			Integer summa = otpRepo.getOtpOf(deviceId);
//			if(otpRepo.getOtpOf(deviceId) == otp) {
//				if(otpRepo.isValid(deviceId)) {
//					otpRepo.changeStatusOf(deviceId);
//					return AuthResponse.builder()	
//							.message("Successfully verified")
//							.token(deviceId)
//							.build();
//				}
//				return AuthResponse.builder()
//						.message("OTP Expired")
//						.build();
//			}
//			log.info(String.valueOf(summa));
//			return AuthResponse.builder()
//						.message("Invalid OTP")
//						.build();
//		}
//		return null;
//	}

	public AuthResponse respondWith(String message) {
		return AuthResponse.builder()
					.message(message)
					.build();
	}

	
	
	
	
}
