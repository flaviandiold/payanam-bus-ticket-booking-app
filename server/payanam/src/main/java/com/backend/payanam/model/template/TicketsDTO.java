package com.backend.payanam.model.template;

import java.time.LocalTime;
import java.util.UUID;

import org.springframework.stereotype.Component;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Component
@NoArgsConstructor
public class TicketsDTO {
	
	private UUID ticketId;
	private String busName;
	private String orderId;
	private Integer price;
	private String from;
	private String to;
	private String privilege;
	private boolean paymentStatus;
	private LocalTime validFrom;
	private LocalTime validTo;
	private LocalTime payedAt;

	
	public TicketsDTO(UUID ticketId, String routeName, String busType, String orderId, Integer price, String from, String to, String privilege,
			boolean paymentStatus, LocalTime validFrom, LocalTime validTo, LocalTime payedAt) {
		this.ticketId = ticketId;
		this.busName = routeName + " " + busType;
		this.from = from;
		this.to = to;
		this.orderId = orderId;
		this.price = price;
		this.privilege = privilege;
		this.paymentStatus = paymentStatus;
		this.validFrom = validFrom;
		this.validTo = validTo;
		this.payedAt = payedAt;
	}

	
	

}
