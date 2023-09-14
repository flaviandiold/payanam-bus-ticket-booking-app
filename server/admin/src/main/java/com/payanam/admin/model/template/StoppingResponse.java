package com.payanam.admin.model.template;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Builder;

@Builder
@JsonInclude(Include.NON_NULL)
public record StoppingResponse (String message, String start, String end, List<String> stoppings) {}
