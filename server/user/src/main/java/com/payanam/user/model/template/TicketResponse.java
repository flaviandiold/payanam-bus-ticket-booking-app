package com.payanam.user.model.template;

import java.time.LocalDateTime;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Builder;

@Builder
@JsonInclude(Include.NON_NULL)
public record TicketResponse (String message, UUID ticketId, String userName, String routeName, String from, String to, LocalDateTime validFrom, boolean valid, boolean privilege, Integer price){}

