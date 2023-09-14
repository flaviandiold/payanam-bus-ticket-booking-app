package com.payanam.admin.service;

import java.util.Optional;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.payanam.admin.model.Conductor;
import com.payanam.admin.repository.BusRepository;
import com.payanam.admin.repository.ConductorRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService{
	
	private final ConductorRepository conductorRepo;
	private final BusRepository busRepo;
//	private final TicketService ticketService;
	private final JwtTokenService tokenService;
//	private final PaymentService paymentService;



	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		Optional<Conductor> user = conductorRepo.findByEmail(email);
		System.out.println("HERE");
		if(user.isEmpty()) {
			
			throw new UsernameNotFoundException("USER DOES NOT EXIST");
		}
		return user.get();
	}


	public String getSubject(String token) {
		return tokenService.getSubject(token);
	}


//	public Page<TicketsDTO> getAllTickets(Integer userId, Pageable pageRequest) {
//		Page<TicketsDTO> tickets = ticketService.getAllTickets(userId, pageRequest);
//		return tickets;
//	}

//	public Response deleteTicketBy(UUID id) {
//		String res = ticketService.couldDelete(id);
//		return Response.builder().message(res).build();
//	}

//	public String userOf(Integer userId) {
//		Optional<Conductor> conductor = conductorRepo.findById(userId);
//		if(conductor.isPresent()){
//			return conductor.get().getEmail();
//		}
//		return null;
//	}

//	public List<String> getStoppingsOf(UUID busId) {
//		Optional<Bus> bus = busRepo.findById(busId);
//		if(bus.isPresent()) {			
//			return busRepo.getStoppingsOf(bus.get().getRoute().getRouteId());
//		}
//		return null;
//	}


	

}
