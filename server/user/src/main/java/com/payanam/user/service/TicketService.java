package com.payanam.user.service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.payanam.user.model.Bus;
import com.payanam.user.model.Stoppings;
import com.payanam.user.model.Tickets;
import com.payanam.user.model.User;
import com.payanam.user.model.template.TicketResponse;
import com.payanam.user.model.template.TicketsDTO;
import com.payanam.user.repository.BusRepository;
import com.payanam.user.repository.TicketRepository;
import com.payanam.user.repository.UserRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class TicketService {
	
	private final UserRepository userRepo;
	private final BusRepository busRepo;
	private final TicketRepository ticketRepo;	
	

	public TicketResponse bookTicket(Integer userId, UUID busId, String from, String to) {
			Optional<User> user = userRepo.findById(userId);
			if(!user.isEmpty()) {
				Optional<Bus> bus = busRepo.findById(busId);
				if(!bus.isEmpty()) {
					List<String> stoppings = filterOutStoppingNames(busRepo.getStoppingsOf(bus.get().getRoute().getRouteId()));
					int stage = findStage(stoppings, from, to);
					String busType = bus.get().getBusType();
					System.out.println(busType + " " + stage);
					int price = calculateFare(busType, stage);
					System.out.println(price);
					Tickets entry = Tickets.builder()
										.userTickets(user.get())
										.bus(bus.get())
										.from(from)
										.to(to)
										.validFrom(LocalDateTime.now(ZoneId.systemDefault()))
										.valid(false)
										.price(price)
										.details(null)
										.build();
					ticketRepo.save(entry);
					
//					List<Tickets> tickets = ticketRepo.findAllByUserTickets_UserId(userId);
////					System.out.println(tickets);
//					ObjectMapper mapper = new ObjectMapper();
//				     mapper.enable(SerializationFeature.INDENT_OUTPUT);
//				     String postJson;
//					try {
//						postJson = mapper.writeValueAsString(tickets);
//						log.info(postJson);
//					} catch (JsonProcessingException e) {
//						// TODO Auto-generated catch block
//						System.out.println(e.getLocalizedMessage());
//					}
//					bus.get().setTickets(tickets);
//					
////					
//					bus = Optional.ofNullable(Bus.builder()
//							.busId(bus.get().getBusId())
//							.busType(bus.get().getBusType())
//							.route(bus.get().getRoute())
//							.licenceNumber(bus.get().getLicenceNumber())
//							.tickets(bus.get().addTickets(entry))
//							.build());	
					
//					busRepo.save(bus.get());
					
					return TicketResponse.builder()
							.ticketId(entry.getTicketId())
							.userName(user.get().getName())
							.routeName(bus.get().getRoute().getRouteName() + " " + bus.get().getBusType())
							.privilege(entry.getPrivilege())
							.price(price)
							.from(entry.getFrom())
							.to(entry.getTo())
							.validFrom(entry.getValidFrom())
							.valid(entry.getValidity())
							.build();
										
			}
		}
		return null;
	}
	
	private List<String> filterOutStoppingNames(List<Stoppings> stoppings) {
		List<String> stops = new ArrayList<>();
		for(Stoppings stop : stoppings) {
			stops.add(stop.getStoppingName());
		}
		return stops;
	}

	public Integer calculateFare(String busType, Integer stage) {
		int price = 0;
		switch(busType) {
		case "DELUXE":
			switch(stage) {
				case 0: price = 0; break;
				case 1: price = 11; break;
				case 2: price = 13; break;
				case 3: price = 15; break;
				case 4: price = 17; break;
				case 5: price = 19; break;
				case 6: price = 21; break;
				case 7: price = 23; break;
				case 8: price = 25; break;
				case 9: price = 27; break;
				case 10: price = 29; break;
				case 11: price = 31; break;
				case 12: price = 31; break;
				case 13: price = 33; break;
				case 14: price = 33; break;
				case 15: price = 35; break;
				case 16: price = 35; break;
				case 17: price = 37; break;
				case 18: price = 37; break;
				case 19: price = 39; break;
				case 20: price = 39; break;
				case 21: price = 41; break;
				case 22: price = 41; break;
				case 23: price = 43; break;
				case 24: price = 43; break;
				case 25: price = 45; break;
				case 26: price = 45; break;
				case 27: price = 47; break;
				case 28: price = 47; break;
				case 29: price = 49; break;
				case 30: price = 49; break;
			} break;
			
		case "NORMAL":
			switch(stage) {
				case 0: price = 0; break;
				case 1: price = 5; break;
				case 2: price = 6; break;
				case 3: price = 7; break;
				case 4: price = 8; break;
				case 5: price = 9; break;
				case 6: price = 10; break;
				case 7: price = 11; break;
				case 8: price = 12; break;
				case 9: price = 13; break;
				case 10: price = 14; break;
				case 11: price = 15; break;
				case 12: price = 15; break;
				case 13: price = 16; break;
				case 14: price = 16; break;
				case 15: price = 17; break;
				case 16: price = 17; break;
				case 17: price = 18; break;
				case 18: price = 18; break;
				case 19: price = 19; break;
				case 20: price = 19; break;
				case 21: price = 20; break;
				case 22: price = 20; break;
				case 23: price = 21; break;
				case 24: price = 21; break;
				case 25: price = 22; break;
				case 26: price = 22; break;
				case 27: price = 23; break;
				case 28: price = 23; break;
				case 29: price = 24; break;
				case 30: price = 24; break;
			} break;
			
		case "EXPRESS":
			switch(stage) {
				case 0: price = 0; break;
				case 1: price = 7; break;
				case 2: price = 9; break;
				case 3: price = 10; break;
				case 4: price = 12; break;
				case 5: price = 13; break;
				case 6: price = 15; break;
				case 7: price = 16; break;
				case 8: price = 18; break;
				case 9: price = 19; break;
				case 10: price = 21; break;
				case 11: price = 22; break;
				case 12: price = 22; break;
				case 13: price = 24; break;
				case 14: price = 24; break;
				case 15: price = 25; break;
				case 16: price = 25; break;
				case 17: price = 27; break;
				case 18: price = 27; break;
				case 19: price = 28; break;
				case 20: price = 28; break;
				case 21: price = 30; break;
				case 22: price = 30; break;
				case 23: price = 31; break;
				case 24: price = 31; break;
				case 25: price = 33; break;
				case 26: price = 33; break;
				case 27: price = 34; break;
				case 28: price = 34; break;
				case 29: price = 35; break;
				case 30: price = 35; break;
			} break;
		
		case "AC":
			switch(stage) {
				case 0: price = 0; break;
				case 1: price = 15; break;
				case 2: price = 15; break;
				case 3: price = 20; break;
				case 4: price = 20; break;
				case 5: price = 20; break;
				case 6: price = 30; break;
				case 7: price = 30; break;
				case 8: price = 30; break;
				case 9: price = 40; break;
				case 10: price = 40; break;
				case 11: price = 40; break;
				case 12: price = 40; break;
				case 13: price = 40; break;
				case 14: price = 40; break;
				case 15: price = 50; break;
				case 16: price = 50; break;
				case 17: price = 50; break;
				case 18: price = 50; break;
				case 19: price = 60; break;
				case 20: price = 60; break;
				case 21: price = 60; break;
				case 22: price = 60; break;
				case 23: price = 60; break;
				case 24: price = 60; break;
				case 25: price = 70; break;
				case 26: price = 70; break;
				case 27: price = 70; break;
				case 28: price = 80; break;
				case 29: price = 80; break;
				case 30: price = 80; break;
			} break;
	
	}
		return price;
	}

	public int findStage(List<String> stoppings, String from, String to) {
		int fromInd = stoppings.indexOf(from);
		int toInd = stoppings.indexOf(to);
		int stops = Math.abs(fromInd - toInd);
		
		System.out.println(stoppings);
		return stops/2;
	}

	public Page<TicketsDTO> getAllTickets(Integer userId, Pageable pageRequest) {
		Page<TicketsDTO> tickets = ticketRepo.findAllTicketsOf(userId, pageRequest);
		return tickets;
	}

	public String couldDelete(UUID id) {
		if(ticketRepo.existsById(id)) {
			if(ticketRepo.isPaid(id)) {
				return "paid ticket";
			}
			ticketRepo.deleteById(id);
			return "deleted";
		}else {
			return "no such ticket";
		}
	}

	public String validate(Integer userId, UUID busId, UUID ticketId) {
		if(ticketRepo.isValidBusToEnter(busId,ticketId)) {
			Integer count = ticketRepo.validate(userId,busId);
			if(count == 0) return "Already validated";
			return "Successfully validated";
		}
		return "This bus does not go to the destination on your ticket";
	}

}
