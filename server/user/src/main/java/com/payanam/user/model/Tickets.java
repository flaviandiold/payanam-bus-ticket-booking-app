package com.payanam.user.model;

import java.time.LocalDateTime;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

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
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name="ticket")
@Builder
@Data
@Getter
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
	@JsonIgnore
	private User userTickets;
	
	@ManyToOne
	@JoinColumn(
			name="bus_id"
			)
	@JsonIgnore
	private Bus bus;
	
	@Column(name="_from")
	private String from;
	
	@Column(name="_to")
	private String to;
	
	@Column(name = "valid_from", columnDefinition = "TIMESTAMP")
//	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
//	@JsonIgnore
	private LocalDateTime validFrom;
	
//	@Column(name = "valid_to", columnDefinition = "TIMESTAMP")
////	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd hh:mm:ss")
////	@JsonIgnore
//	private LocalDateTime validTo;
	
	
	@Column(name = "payed_at", columnDefinition = "TIMESTAMP")
//	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
//	@JsonIgnore
	private LocalDateTime payedAt;
	
	@Column(name="privilege")
	@Builder.Default
	private Boolean privilege = false;
	
	@Column(name="payment_status")
	private boolean status;
	
	@Column(name="valid")
	private boolean valid;
		
	@OneToOne
	@JoinColumn(name="order_id")
	private PaymentDetails details;
	
	@Column(name="price")
	private Integer price;

	public boolean getValidity() {
		return this.valid;
	}

	
}
