package com.backend.payanam.service;

import java.util.List;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.backend.payanam.model.PaymentDetails;
import com.backend.payanam.model.repository.UserRepository;
import com.backend.payanam.model.template.AuthResponse;
import com.backend.payanam.model.template.OrderResponse;
import com.backend.payanam.model.template.Response;
import com.backend.payanam.model.template.TicketResponse;
import com.backend.payanam.model.template.TicketsDTO;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService{
	
	private final UserRepository repo;
	private final PayaneeService payaneeService;
	private final TicketService ticketService;
	private final JwtTokenService tokenService;
	private final PaymentService paymentService;



	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		if(!repo.existsUserByEmail(email)) {
			throw new UsernameNotFoundException("USER DOES NOT EXIST");
		}
		return repo.findByEmail(email);
	}


	public List<String> getStoppingsOf(UUID busId) {
		return payaneeService.getStoppingsOf(busId);
	}

	public TicketResponse bookTicket(Integer userId, UUID busId, String from, String string) {
		return ticketService.bookTicket(userId, busId, from, string);
	}

	public String getSubject(String token) {
		return tokenService.getSubject(token);
	}

	public OrderResponse createTransaction(UUID ticketId, Integer amount) {
		return paymentService.createTransaction(ticketId, amount);
	}

	public PaymentDetails onSuccess(String orderId, String paymentId, String paymentSignature) {
		return paymentService.onSuccess(orderId, paymentId, paymentSignature);
	}

	public Page<TicketsDTO> getAllTickets(Integer userId, Pageable pageRequest) {
		Page<TicketsDTO> tickets = ticketService.getAllTickets(userId, pageRequest);
		return tickets;
	}


	public Response deleteTicketBy(UUID id) {
		String res = ticketService.couldDelete(id);
		return Response.builder().message(res).build();
	}


	public String userOf(Integer userId) {
		if(repo.existsById(userId)){
			return repo.getEmail(userId);
		}
		return null;
	}


	

}
