package com.backend.payanam.model.template;

import lombok.Builder;

@Builder
public record OrderResponse (String orderId, String key, String currency, Integer amount){}
