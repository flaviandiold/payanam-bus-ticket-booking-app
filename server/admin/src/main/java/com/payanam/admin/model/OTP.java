package com.payanam.admin.model;

import java.time.LocalTime;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
//
//@Entity
//@Table(name="otp_details")
//@Builder
//@Getter
//@NoArgsConstructor
//@AllArgsConstructor
//public class OTP {
//	
//	@Id
//	@Column(name="device_id")
//	private String deviceId;
//	
//	@ManyToOne(cascade = CascadeType.ALL)
//	private User user;
//	
//	@Column(name="otp")
//	private int otp;
//	
//	@Column(name="verified_status")
//	@Builder.Default
//	private boolean status = false;
//	
//	@Column(name="creation_time")
//	private LocalTime validFrom;
//	
//	@Column(name="expiry_time")
//	private LocalTime validTo;
//
//}
