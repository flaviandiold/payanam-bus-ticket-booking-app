package com.backend.payanam.model.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.backend.payanam.model.PaymentDetails;
import com.backend.payanam.model.Tickets;

import jakarta.transaction.Transactional;

@Repository
@Transactional
public interface PaymentRepository extends JpaRepository<PaymentDetails, Integer> {

	@Query("select t from Tickets t join PaymentDetails pd on t.details.orderId = pd.orderId where pd.orderId=:orderId")
	public Tickets getTicket(String orderId);

	public Optional<PaymentDetails> findByOrderId(String orderId);

	@Modifying
	@Query("update PaymentDetails pd set pd.paymentId=:paymentId, pd.paymentSign=:paymentSign where pd.orderId=:orderId")
	public void updatePaymentDetailsBy(String orderId, String paymentId, String paymentSign);
	
}
