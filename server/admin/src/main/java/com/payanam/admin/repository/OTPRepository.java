package com.payanam.admin.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;


import jakarta.transaction.Transactional;
//
//@Repository
//@Transactional
//public interface OTPRepository extends JpaRepository<OTP, String>{
//
//	@Query("select o.otp from OTP o where o.deviceId=:deviceId")
//	int getOtpOf(String deviceId);
//
//	@Query("select o.validTo > CURRENT_TIME from OTP o where o.deviceId=:deviceId")
//	boolean isValid(String deviceId);
//
//	@Modifying
//	@Query("update OTP o set o.status=true where o.deviceId=:deviceId")
//	void changeStatusOf(String deviceId);
//
//	@Query("select u.email from User u join OTP o on o.user.userId=u.userId where o.deviceId=:deviceId")
//	public String getEmailOf(String deviceId);
//
//	@Query("select count(*) > 0 from OTP o where o.deviceId=:deviceId and o.status=true")
//	public boolean isStatusValid(String deviceId);
//	
//}
