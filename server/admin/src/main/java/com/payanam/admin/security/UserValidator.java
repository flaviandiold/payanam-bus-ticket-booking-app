package com.payanam.admin.security;

import org.springframework.security.oauth2.core.OAuth2Error;
import org.springframework.security.oauth2.core.OAuth2TokenValidator;
import org.springframework.security.oauth2.core.OAuth2TokenValidatorResult;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Component;

import com.payanam.admin.repository.ConductorRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@RequiredArgsConstructor
@Slf4j
public class UserValidator implements OAuth2TokenValidator<Jwt> {
    OAuth2Error expired = new OAuth2Error("404", "Expird JWT", null);
    OAuth2Error userErr = new OAuth2Error("404", "No such user", "/login");

	 private final ConductorRepository repo;
	
	@Override
	public OAuth2TokenValidatorResult validate(Jwt token) {
		Integer id = Integer.valueOf(token.getSubject());
//		Instant exp = token.getExpiresAt();
//		log.info(exp.toString());
	       if(repo.existsById(id)) {
//	    	   if(exp.isBefore(Instant.now())) {
	    		   return OAuth2TokenValidatorResult.success();
//	    	   } else {
//	    		   return OAuth2TokenValidatorResult.failure(expired);
//	    	   }
	       }else {
	    	   return OAuth2TokenValidatorResult.failure(userErr);
	       }
	}

}
