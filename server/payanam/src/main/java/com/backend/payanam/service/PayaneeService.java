package com.backend.payanam.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.backend.payanam.model.Bus;
import com.backend.payanam.model.BusStop;
import com.backend.payanam.model.BusStopKey;
import com.backend.payanam.model.Stoppings;
import com.backend.payanam.model.repository.BusRepository;
import com.backend.payanam.model.repository.BusStopRepository;
import com.backend.payanam.model.repository.StoppingRepository;
import com.backend.payanam.model.template.Response;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PayaneeService {
	
	private final BusRepository busRepo;
	private final StoppingRepository stopRepo;
	private final BusStopRepository busStopRepo;

	public List<String> getStoppingsOf(UUID busId) {
		Optional<Bus> bus = busRepo.findById(busId);
		if(bus.isPresent()) {			
			return busRepo.getStoppingsOf(busId);
		}
		return null;
	}
	
	
	public Response addBus(String busRoute, String busType) {
		if(busRepo.existsBus(busRoute, busType)) {
			return null;
		}
//		Random rand = new Random();
//		int id = rand.nextInt(1000,10000);
//		while(repo.existsById(id)) {
//			id = rand.nextInt(1000,10000);
//		}
		Bus entry = Bus.builder()
//						.busId(id)
						.routeName(busRoute)
						.busType(busType)
						.build();
		busRepo.save(entry);
		return Response.builder().message(entry.getBusId().toString()).build();
	}


	public Response addStop(UUID busId, String stoppingName, Integer stoppingOrder) {
		if(!busRepo.existsById(busId)) {
			return Response.builder().message("No Such Bus exists").build();
		}
		if(!stopRepo.existsByName(stoppingName)) {
			Stoppings entry = Stoppings.builder()
									.stoppingName(stoppingName)
									.build();
//			System.out.println("Inside creating a stopping");
			stopRepo.save(entry);
		}
		
		BusStopKey stopKey = BusStopKey.builder()
								.busId(busId)
								.stoppingId(stopRepo.getIdOf(stoppingName))
								.build();
		
		BusStop entry = BusStop.builder()
							.id(stopKey)
							.bus(busRepo.findById(busId).get())
							.stoppings(stopRepo.findByStoppingName(stoppingName))
							.stoppingOrder(stoppingOrder)
							.build();
		
//		System.out.println(entry);
		if(busStopRepo.existsById(stopKey)) {
			return null;
		}
		if(!busStopRepo.isValid(entry.getId().getBusId(), entry.getStoppingOrder())) {
			return Response.builder().message("Enter a valid stopping order").build();
		}
		busStopRepo.save(entry);
		
		return Response.builder().message("Successfully saved").build();
	}
	
	
}
