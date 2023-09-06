package com.backend.payanam.model;

import java.time.LocalTime;
import java.util.UUID;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="ticket")
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Tickets{

	@Id
	@GeneratedValue
	@Column(name="ticket_id")
	private UUID ticketId;
	
	@ManyToOne
	@JoinColumn(
			name="user_id"
			)
	private User userTickets;

	@ManyToOne
	@JoinColumn(
			name="bus_id"
			)
	private Bus bus;
	
	@Column(name="_from")
	private String from;
	
	@Column(name="_to")
	private String to;
	
	@Column(name = "valid_from", columnDefinition = "TIME")
	private LocalTime validFrom;
	
	@Column(name = "valid_to", columnDefinition = "TIME")
	private LocalTime validTo;
	
	@Column(name = "payed_at", columnDefinition = "TIME")
	private LocalTime payedAt;
	
	@Column(name="privilege")
	@Builder.Default
	private String privilegeMessage = "NONE";
	
	@Column(name="payment_status")
	private boolean status;
	
	
	
//	@OneToMany(mappedBy="ticket")
//	private Set<PaymentDetails> details;
	
	@OneToOne
	@JoinColumn(name="order_id")
	private PaymentDetails details;
	
	@Column(name="price")
	private Integer price;
//
//public Tickets(Integer ticketId, User user, Bus bus, String from, String to, LocalTime validFrom, LocalTime validTo,
//		String privilegeMessage, boolean status, PaymentDetails details) {
//	super();
//	this.ticketId = ticketId;
//	this.user = user;
//	this.bus = bus;
//	this.from = from;
//	this.to = to;
//	this.validFrom = validFrom;
//	this.validTo = validTo;
//	this.privilegeMessage = privilegeMessage;
//	this.status = status;
//	this.details = details;
//}
//	
	
	
}
