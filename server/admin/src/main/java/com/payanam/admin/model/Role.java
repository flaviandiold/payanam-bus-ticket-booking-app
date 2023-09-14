package com.payanam.admin.model;

import org.springframework.security.core.GrantedAuthority;

public enum Role implements GrantedAuthority{
	ADMIN, CONDUCTOR;

	@Override
	public String getAuthority() {
		return this.name();
	}
}
