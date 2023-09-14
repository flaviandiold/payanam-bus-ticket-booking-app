package com.payanam.user.model.template;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
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
	private boolean privilege;
	private boolean paymentStatus;
	private LocalDateTime validFrom;
	private boolean valid;
	private LocalDateTime payedAt;

	
	public TicketsDTO(UUID ticketId, String routeName, String busType, String orderId, Integer price, String from, String to, boolean privilege,
			boolean paymentStatus, LocalDateTime validFrom, boolean valid, LocalDateTime payedAt) {
		this.ticketId = ticketId;
		this.busName = routeName + " " + busType;
		this.from = from;
		this.to = to;
		this.orderId = orderId;
		this.price = price;
		this.privilege = privilege;
		this.paymentStatus = paymentStatus;
		this.validFrom = validFrom;
		this.valid = valid;
		this.payedAt = payedAt;
	}

	
	

}
