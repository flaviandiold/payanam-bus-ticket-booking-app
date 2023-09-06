package com.backend.payanam.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.backend.payanam.model.template.AuthResponse;
import com.backend.payanam.service.AuthenticationService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("api/auth")
@RequiredArgsConstructor
@Slf4j
public class AuthenticationController {
	
	private final AuthenticationService service;
	
	@PostMapping("/register")
	public ResponseEntity<AuthResponse> register(@RequestBody AuthBody req){
		log.info("Inside Auth Register");
		AuthResponse res = service.register(req.name(),req.email(),req.password());
		if(res == null) {
			System.out.println(service.respondWith("User exists"));
			return new ResponseEntity<AuthResponse>(service.respondWith("User exists"), HttpStatus.CONFLICT);
		}
		return ResponseEntity.ok(res);
	}
	
	@PostMapping("/login")
	public ResponseEntity<AuthResponse> login(@RequestBody AuthBody req){
		log.info("Inside Auth Login");
		AuthResponse res = service.login(req.email(),req.password());
		if(res == null) {
			return new ResponseEntity<AuthResponse>(service.respondWith("No User Found"),HttpStatus.NOT_FOUND);
		}
		if(res.getToken() == null) {
			return new ResponseEntity<AuthResponse>(res,HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<AuthResponse>(res,HttpStatus.OK);
	}
	
	@PostMapping("/forgot-password/username")
	public ResponseEntity<AuthResponse> existUser(@RequestBody AuthBody req){
		AuthResponse res = service.existsUser(req.email());
		if(res == null) {
			return new ResponseEntity<AuthResponse>(service.respondWith("No such user exists"), HttpStatus.NOT_FOUND);
		}
		return ResponseEntity.ok(res);
	}
	
	@PostMapping("/forgot-password/verify-otp")
	public ResponseEntity<AuthResponse> verifyOtp(@RequestBody OTPBody req){
		AuthResponse res = service.verifyOTP(req.deviceId(),req.otp());
		if(res == null) {
			return new ResponseEntity<AuthResponse>(service.respondWith("INVALID"),HttpStatus.BAD_REQUEST);
		}
		if(res.getToken() == null) {
			return new ResponseEntity<AuthResponse>(res,HttpStatus.BAD_REQUEST);
		}
		return ResponseEntity.ok(res);
	}
	
	@PostMapping("/forgot-password/changePassword")
	public ResponseEntity<AuthResponse> changePassword(@RequestBody OTPBody req){
		AuthResponse res = service.changePasswordTo(req.deviceId(),req.password());
		if(res == null) {
			return new ResponseEntity<AuthResponse>(service.respondWith("No such user"),HttpStatus.NOT_FOUND);
		}
		if(res.getMessage().equals("Not verified")) {
			return new ResponseEntity<AuthResponse>(service.respondWith("OTP Not verified"),HttpStatus.BAD_REQUEST);
		}
		if(res.getMessage().equals("Same")) {
			return new ResponseEntity<AuthResponse>(service.respondWith("Your current password is the same as this"),HttpStatus.BAD_REQUEST);
		}
		return ResponseEntity.ok(res);
	}
}


record OTPBody (String deviceId, Integer otp, String password) {}

record AuthBody (String name, String email, String password) {}
