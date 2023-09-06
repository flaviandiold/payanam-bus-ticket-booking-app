package com.backend.payanam.model.template;

import java.time.LocalTime;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Builder
@JsonInclude(Include.NON_NULL)
public record TicketResponse (String message, UUID ticketId, String userName, String busName, String from, String to, LocalTime validFrom, LocalTime validTo, String privilege, Integer price){}

