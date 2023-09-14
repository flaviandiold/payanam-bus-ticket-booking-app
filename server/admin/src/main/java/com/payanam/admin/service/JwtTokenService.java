	package com.payanam.admin.service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Service;

import com.payanam.admin.repository.ConductorRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class JwtTokenService {

	private final JwtEncoder encoder;
	
	private final JwtDecoder decoder;
	
	private final ConductorRepository repo;
	
	public String generateToken(Authentication auth) {
		
		Instant now = Instant.now();
		
		String authorities = auth.getAuthorities()
				.stream().map(GrantedAuthority::getAuthority)
				.collect(Collectors.joining(" "));
		
		JwtClaimsSet claims = JwtClaimsSet.builder()
				.subject(String.valueOf(repo.getIdOf(auth.getName())))
				.issuer("Payanam")
				.audience(List.of("Payanee"))
				.issuedAt(now)
				.expiresAt(now.plus(3,ChronoUnit.HOURS))
				.claim("roles",authorities)
				.build();
				
		return encoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();
	}

	public boolean validateAccessToken(String token) {
		return repo.existsById(Integer.valueOf(getSubject(token)));
	}

	public String getSubject(String token) {
		Jwt jwt = decoder.decode(token);
		return jwt.getSubject();
	}

}
