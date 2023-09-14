package com.payanam.admin.model;

import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name="conductor")
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Conductor implements UserDetails{
	
	private static final long serialVersionUID = -2668649576510901712L;

	@Id
	@GeneratedValue
	@Column(name="conductor_id")
	private Integer userId;
	
	@Column(name="conductor_name")
	private String name;
	
	@Column(name="conductor_email")
	private String email;
	
	@Column(name="conductor_password")
	@JsonIgnore
	private String password;
	
	@Column
	@Enumerated(EnumType.STRING)
	private Role role;
	
	@Column(name="service_status")
	@Enumerated(EnumType.STRING)
	private ServiceStatus serviceStatus;

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
		// TODO Auto-generated method stub
		return true;
	}
	
//	@OneToOne
//	@JoinColumn(name="bus_id")
//	private Bus bus;

}
