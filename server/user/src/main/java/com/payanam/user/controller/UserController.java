package com.payanam.user.controller;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.payanam.user.model.PaymentDetails;
import com.payanam.user.model.template.OrderResponse;
import com.payanam.user.model.template.Response;
import com.payanam.user.model.template.StoppingResponse;
import com.payanam.user.model.template.TicketResponse;
import com.payanam.user.model.template.TicketsDTO;
import com.payanam.user.service.UserService;

import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
@Slf4j
public class UserController {
	
	private final UserService service;
	
	@GetMapping("/bus/{bus_id}")
	public ResponseEntity<StoppingResponse> getStoppings(@PathVariable("bus_id") UUID busId, @RequestBody LocationRequest req){
		List<String> stoppings = service.getStoppingsOf(busId, req.latitude(), req.longitude());
		if(stoppings == null) {
			StoppingResponse res = StoppingResponse.builder()
					.message("No Such Bus ID")
					.build();
			return new ResponseEntity<StoppingResponse>(res, HttpStatus.NOT_FOUND);
		}
		String start = stoppings.get(0);
		if(start.equals("No Route is matched up with this bus")) {
			StoppingResponse res = StoppingResponse.builder()
					.message(start)
					.build();
			return new ResponseEntity<StoppingResponse>(res, HttpStatus.FAILED_DEPENDENCY);
		}
		String end = stoppings.get(stoppings.size() - 1);
		stoppings.remove(0);
		return ResponseEntity.ok(StoppingResponse.builder()
									.start(start)
									.end(end)
									.stoppings(stoppings)
									.build());
	}
	
	@PostMapping("/book-ticket")
	public ResponseEntity<TicketResponse> bookTicket(@RequestBody TicketRequest req, HttpServletRequest request){
		TicketResponse res = service.bookTicket(service.getSubject(request.getHeader("Authorization").split(" ")[1]), req.busId(), req.from(), req.to());
		if(res == null) {
			res = TicketResponse.builder()
			.message("No Such Bus ID")
			.build();
			return new ResponseEntity<TicketResponse>(res,HttpStatus.NOT_FOUND);
		}
		return ResponseEntity.ok(res);
	}
	
	@PostMapping("/create-order")
	public ResponseEntity<OrderResponse> addOrder(@RequestBody OrderRequest req){
		log.info("INSIDE CREATE ORDER");
		OrderResponse res = service.createTransaction(req.ticketId(), req.amount());
		if(res == null) {
			return ResponseEntity.ofNullable(null);
		}
		return ResponseEntity.ok(res);
	}
	
	@PostMapping("/payment-success")
	public ResponseEntity<PaymentDetails> paymentDone(@RequestBody PaymentRequest req, HttpServletRequest request){
		log.info("Inside Payment Success");
		PaymentDetails res = service.onSuccess(req.orderId(),req.paymentId(),req.paymentSignature());
		if(res == null) {
			return ResponseEntity.ofNullable(null);
		}
		return ResponseEntity.ok(res);
	}
	
	@GetMapping("/all-tickets/{page}")
	public ResponseEntity<Page<TicketsDTO>> allTickets(@PathVariable("page") Integer pageNumber, HttpServletRequest req){
		Pageable pageRequest = PageRequest.of(pageNumber, 2, Sort.by("payedAt").and(Sort.by("validFrom")));
		return ResponseEntity.ok(service.getAllTickets(service.getSubject(req.getHeader("Authorization").split(" ")[1]), pageRequest));
	}
	
	@DeleteMapping("/ticket/{id}")
	public ResponseEntity<Response> deleteTicket(@PathVariable("id") UUID id){
		Response res = service.deleteTicketBy(id);
		if(res.message().equals("deleted")) {
			return new ResponseEntity<Response>(res, HttpStatus.OK);
//					Response.builder()
//					.message("Success")
//					.build();
		}else if(res.message().equals("paid ticket")) {
			return new ResponseEntity<Response>(res, HttpStatus.BAD_REQUEST);
//			return Response.builder()
//					.message("Sorry, You cannot delete a paid ticket")
//					.build();
		}else {
			return new ResponseEntity<Response>(res, HttpStatus.NOT_FOUND);
//			return Response.builder()
//					.message("No such Ticket ID exists")
//					.build();
		}
	} 
	
	@PostMapping("/validate-ticket")
	public ResponseEntity<Map<String,String>> validateTicket(@RequestBody ValidationRequest request, HttpServletRequest req){
		String message = service.validate(service.getSubject(req.getHeader("Authorization").split(" ")[1]),request.busId(),request.ticketId());
		if(message.equals("This bus does not go to the destination on your ticket")) {
			return new ResponseEntity<Map<String,String>>(Map.of("message",message), HttpStatus.EXPECTATION_FAILED);
		}
		if(message.equals("Already validated")) {
			return new ResponseEntity<Map<String,String>>(Map.of("message",message), HttpStatus.ALREADY_REPORTED);
		}
		return new ResponseEntity<Map<String,String>>(Map.of("message",message), HttpStatus.OK);
	}
	
	
//	@GetMapping
//	public ResponseEntity<List<User>> users(){
//		return ResponseEntity.ok(service.getUsers());
//	}

}

record TicketRequest (UUID busId, String from, String to) {}

record OrderRequest (UUID ticketId, Integer amount) {}

record PaymentRequest (String orderId, String paymentId, String paymentSignature) {}

record LocationRequest(Double latitude, Double longitude) {} 

record ValidationRequest(UUID busId, UUID ticketId) {} 

