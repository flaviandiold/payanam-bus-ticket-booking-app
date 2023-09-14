package com.payanam.user.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name="payment_details")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
public class PaymentDetails {
	
	@Column(name="payment_id")
	private String paymentId;
	
	@Id
	@Column(name="order_id")
	private String orderId;
	
	@OneToOne
	@JoinColumn(name="ticket_id")
	private Tickets ticket;
	
	@Column(name="payment_signature")
	private String paymentSign;
	


	
}
