package com.payanam.admin.model.template;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class AuthResponse {
	
	public String message; 
	@JsonInclude(Include.NON_NULL)
	public String token;




	@Override
	public String toString() {
		return "AuthResponse [message=" + message + "]";
	}


	
	
}
