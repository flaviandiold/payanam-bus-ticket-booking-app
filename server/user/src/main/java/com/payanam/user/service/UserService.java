package com.payanam.user.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.payanam.user.model.Bus;
import com.payanam.user.model.PaymentDetails;
import com.payanam.user.model.Route;
import com.payanam.user.model.Stoppings;
import com.payanam.user.model.User;
import com.payanam.user.model.template.OrderResponse;
import com.payanam.user.model.template.Response;
import com.payanam.user.model.template.TicketResponse;
import com.payanam.user.model.template.TicketsDTO;
import com.payanam.user.repository.BusRepository;
import com.payanam.user.repository.UserRepository;
import com.payanam.user.utils.MapDistance;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService{
	
	private final UserRepository userRepo;
	private final BusRepository busRepo;
	private final TicketService ticketService;
	private final JwtTokenService tokenService;
	private final PaymentService paymentService;



	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		Optional<User> user = userRepo.findByEmail(email);
		if(user.isEmpty()) {
			throw new UsernameNotFoundException("USER DOES NOT EXIST");
		}
		return user.get();
	}

	public TicketResponse bookTicket(Integer userId, UUID busId, String from, String string) {
		return ticketService.bookTicket(userId, busId, from, string);
	}

	public Integer getSubject(String token) {
		return Integer.valueOf(tokenService.getSubject(token));
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
		if(userRepo.existsById(userId)){
			return userRepo.getEmail(userId);
		}
		return null;
	}

	public List<String> getStoppingsOf(UUID busId, Double latitude, Double longitude) {
		Optional<Bus> bus = busRepo.findById(busId);
		if(bus.isEmpty()) return null;
		Route cur = bus.get().getRoute();
		if(cur != null) {			
			List<Stoppings> stoppings =  busRepo.getStoppingsOf(cur.getRouteId());
//			for()
			Double min = Double.MAX_VALUE;
			int ind = 0;
			for(int i = 0; i < stoppings.size()-1; i++) {
				double distStartToX = MapDistance.find(stoppings.get(i).getLatitude(), stoppings.get(i).getLongitude(), latitude, longitude);
				double distXToEnd = MapDistance.find(stoppings.get(i+1).getLatitude(), stoppings.get(i+1).getLongitude(), latitude, longitude);
				double distStartToEnd = MapDistance.find(stoppings.get(i).getLatitude(), stoppings.get(i).getLongitude(), stoppings.get(i+1).getLatitude(), stoppings.get(i+1).getLongitude() );
				double difference = (distStartToX + distXToEnd) - distStartToEnd;
				if(difference < min) {
					min = difference;
					ind = i;
				}
//				System.out.println(distStartToEnd + " " + distStartToX + " " + distXToEnd + " " + Math.round(difference) + " " + ind + " " + stoppings.get(i).getStoppingName());
			}
//			Syst/em.out.println(distance);
			List<String> stops = new ArrayList<>();
			stops.add(stoppings.get(0).getStoppingName());
			for(int i = ind; i < stoppings.size(); i++) {
				stops.add(stoppings.get(i).getStoppingName());
			}
//			stops.add(stoppings.get(stoppings.size()-1).getStoppingName());
			return stops;
		}else {
			return List.of("No Route is matched up with this bus");
		}
	}

	public String validate(Integer userId, UUID busId, UUID ticketId) {
		String message = ticketService.validate(userId,busId,ticketId);
		return message;
	}


}
