package com.payanam.admin.service;

import java.util.List;
import java.util.Optional;
import java.util.Random;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.payanam.admin.model.Bus;
import com.payanam.admin.model.Route;
import com.payanam.admin.model.RouteStop;
import com.payanam.admin.model.RouteStopKey;
import com.payanam.admin.model.Stoppings;
import com.payanam.admin.model.template.Response;
import com.payanam.admin.repository.BusRepository;
import com.payanam.admin.repository.RouteRepository;
import com.payanam.admin.repository.RouteStopRepository;
import com.payanam.admin.repository.StoppingRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AdminService {
	
	private final BusRepository busRepo;
	private final StoppingRepository stopRepo;
	private final RouteRepository routeRepo;
	private final RouteStopRepository routeStopRepo;	
	
	public Response addBus(String busRoute, String busType, String licenceNumber) {
		if(busRepo.existsByLicenceNumber(licenceNumber)) {
			return null;
		}
		Optional<Route> routeEntry = routeRepo.findByRouteName(busRoute);
		if(routeEntry.isEmpty() && busRoute != null) {
			Random rand = new Random();
			int routeId = rand.nextInt(1000,10000);
			while(routeRepo.existsById(routeId)) routeId = rand.nextInt(1000,10000);
			routeEntry = Optional.ofNullable(Route.builder().routeId(routeId).routeName(busRoute).build());
			routeRepo.save(routeEntry.get());
		}
		
		Bus busEntry = null;
		if(routeEntry.isEmpty()) busEntry = Bus.builder()
//				.busId(id)
				.route(null)
				.busType(busType)
				.licenceNumber(licenceNumber)
				.build();
		else	busEntry = Bus.builder()
//				.busId(id)
				.route(routeEntry.get())
				.busType(busType)
				.licenceNumber(licenceNumber)
				.build();
		busRepo.save(busEntry);
		return Response.builder().message(busEntry.getBusId().toString()).build();
	}


	public Response addStop(Integer routeId, String stoppingName, Integer stoppingOrder) {
		Optional<Route> route = routeRepo.findById(routeId);
		if(route.isEmpty()) {
			return Response.builder().message("No Such Route exists").build();
		}
		if(!stopRepo.existsByName(stoppingName)) {
			Stoppings entry = Stoppings.builder()
									.stoppingName(stoppingName)
									.build();
//			System.out.println("Inside creating a stopping");
			stopRepo.save(entry);
		}
		
		RouteStopKey stopKey = RouteStopKey.builder()
								.routeId(routeId)
								.stoppingId(stopRepo.getIdOf(stoppingName))
								.build();
		
		RouteStop entry = RouteStop.builder()
							.id(stopKey)
							.route(route.get())
							.stoppings(stopRepo.findByStoppingName(stoppingName))
							.stoppingOrder(stoppingOrder)
							.build();
		
//		System.out.println(entry);
		if(routeStopRepo.existsById(stopKey)) {
			return null;
		}
		if(!routeStopRepo.isValid(entry.getId().getRouteId(), entry.getStoppingOrder())) {
			return Response.builder().message("Enter a valid stopping order").build();
		}
		routeStopRepo.save(entry);
		
		return Response.builder().message("Successfully saved").build();
	}


	public List<Bus> getAllBusOf(Integer routeId) {
		List<Bus> buses = busRepo.findAllByRoute_RouteId(routeId);
		return buses;
	}

}
