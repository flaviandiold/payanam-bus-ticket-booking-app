package com.payanam.user.model;

import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Table(name="stoppings")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class Stoppings {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="stopping_id")
	private Integer stoppingId;
	
	@Column(name="stopping_name", unique=true, nullable=false)
	private String stoppingName;
	
	@Column(name="latitude")
	private double latitude;
	
	@Column(name="longitude")
	private double longitude;
	
	
}
