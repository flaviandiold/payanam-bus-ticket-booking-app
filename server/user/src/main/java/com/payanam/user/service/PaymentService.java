package com.payanam.user.service;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.OffsetDateTime;
import java.util.Optional;
import java.util.UUID;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.payanam.user.model.PaymentDetails;
import com.payanam.user.model.template.OrderResponse;
import com.payanam.user.repository.PaymentRepository;
import com.payanam.user.repository.TicketRepository;
import com.razorpay.Order;
import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class PaymentService {
	
	@Value("${rzp_key_id}")
    private String keyId;

    @Value("${rzp_key_secret}")
    private String secret;
    
    @Value("${rzp_currency}")
    private String currency;
    
    private final TicketRepository ticketRepo;
    private final PaymentRepository payRepo;
	
	public OrderResponse createTransaction(UUID ticketId, Integer amount) {
		log.info("INSIDE CREATE TRANSACTION");
		if(ticketRepo.existsById(ticketId)) {
			try {
				  RazorpayClient razorpayClient = new RazorpayClient(keyId, secret);
				  int receiptInd = ticketId.toString().lastIndexOf('-');
				  JSONObject orderRequest = new JSONObject();
				  orderRequest.put("amount", (amount * 100)); // amount in the smallest currency unit
				  orderRequest.put("currency", currency);
				  orderRequest.put("receipt", "order_"+ticketId.toString().substring(receiptInd+1));
//				  orderRequest.put("", false);
//				  System.out.println(true);
				  Order order = razorpayClient.orders.create(orderRequest);
				  
				  PaymentDetails entry = PaymentDetails.builder()
				  	.ticket(ticketRepo.findById(ticketId).get())
				  	.orderId(order.get("id"))
				  	.build();
				  payRepo.save(entry);
				  System.out.println(order);
				  
				  log.info("BELOW CREATED ORDER");
				  
				  ticketRepo.updateOrderIdOf(ticketId, order.get("id"));
				  
				  return OrderResponse.builder()
						  	.orderId(order.get("id"))
						  	.key(keyId)
						  	.currency(order.get("currency"))
						  	.amount(order.get("amount"))
						  	.build();
				} catch (RazorpayException e) {
				  System.out.println(e.getMessage());
				}
		}
		return null;
	}

	public PaymentDetails onSuccess(String orderId, String paymentId, String paymentSignature){
		log.info("Inside On Success");
		Optional<PaymentDetails> order = payRepo.findByOrderId(orderId);
		if(order.isEmpty()) {
			log.info("Empty");
			return null;
		}
		RazorpayClient razorpayClient;
		ticketRepo.changePaymentStatusOf(orderId, LocalDateTime.now());
		payRepo.updatePaymentDetailsBy(orderId,paymentId,paymentSignature);
		return order.get();
	}


}
