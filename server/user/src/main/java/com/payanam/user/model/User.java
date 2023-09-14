package com.payanam.user.model;

import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="users")
@Builder
@NoArgsConstructor
@Data
@AllArgsConstructor
public class User implements UserDetails{
	
	private static final long serialVersionUID = -8161592882436622543L;

	@Id
	@GeneratedValue
	@Column(name="user_id")
	private Integer userId;
	
	@Column(name="email", unique=true, nullable=false)
	private String email;
	
	@Column(name="_name", nullable=false)
	private String name;
	
	@Column(name="password", unique=true, nullable=false)
	private String password;
	
	@Column
	@Enumerated(EnumType.STRING)
	private Role role;
//	
//	@OneToMany(mappedBy="user")
//	private List<OTP> otps;
//	
//	@OneToMany(mappedBy="userTickets")
//	private List<Tickets> tickets;
	

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return List.of(new SimpleGrantedAuthority(role.getAuthority()));
	}

	@Override
	public String getPassword() {
		return this.password;
	}

	@Override
	public String getUsername() {
		return this.email;
	}
	
	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}

}
