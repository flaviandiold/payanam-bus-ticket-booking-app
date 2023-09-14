package com.payanam.admin.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.payanam.admin.model.Bus;
import com.payanam.admin.model.Conductor;
import com.payanam.admin.model.Route;
import com.payanam.admin.repository.BusRepository;
import com.payanam.admin.repository.ConductorRepository;
import com.payanam.admin.repository.RouteRepository;
import com.payanam.admin.repository.StoppingRepository;
import com.payanam.admin.repository.TicketRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ConductorService {
	
	private final JwtTokenService tokenService;
	private final BusRepository busRepo;
	private final RouteRepository routeRepo;
	private final ConductorRepository conductorRepo;
	private final TicketRepository ticketRepo;
	private final StoppingRepository stopRepo;

	public Integer getSubject(String token) {
		return Integer.valueOf(tokenService.getSubject(token));
	}

	public boolean mapConductor(Integer conductorId, UUID busId) {
		Optional<Bus> bus = busRepo.findById(busId);
		if(!bus.isEmpty()) {
			Optional<Conductor> conductor = conductorRepo.findById(conductorId);
			if(bus.get().getConductor()!= null) {
				return false;
			}
			bus.get().setConductor(conductor.get());
			busRepo.save(bus.get());
			return true;
		}
		return false;
		
	}

	public List<String> getRouteOf(UUID busId) {
		Optional<Bus> bus = busRepo.findById(busId);
		if(bus.isEmpty()) return null;
		Route cur = bus.get().getRoute();
		if(cur != null) {			
			return busRepo.getStoppingsOf(cur.getRouteId());
		}else {
			return List.of("No Route is matched up with this bus");
		}
	}

	public String invalidate(String stop, Integer conductorId) {
		if(!conductorRepo.isMapped(conductorId)) {
			return "You are not mapped with any bus";
		}
		Integer stopOrder = stopRepo.orderOf(stop,conductorId);
		if(stopOrder == null) return "No Such Stopping Exists";
//		System.out.println(stopOrder + " " + conductorId + " " + stop);
		Integer count = ticketRepo.invalidate(stopOrder,conductorId,stop);
//		System.out.println(count);
		if(count == 0) return "Service has not yet started";
		return "Successfully invalidated";
	}

	public String relieve(Integer conductorId) {
		if(conductorRepo.isMapped(conductorId))
			conductorRepo.relieve(conductorId);
		else
			return "You are not mapped with any bus";
//		Optional<Bus> bus = busRepo.findByConductor_UserId(conductorId);
//		if(!bus.isEmpty()) {
//			bus.get().setConductor(null);
//			busRepo.save(bus.get());
//		}
		return "Successfully relieved";
	}

	public String changeRoute(Integer conductorId, String routeName) {
		if(conductorRepo.isMapped(conductorId)) {
			Integer routeId = routeRepo.getRouteId(routeName);
			if(routeId != null) {
				Integer curRouteId = busRepo.curRouteId(conductorId);
				if(routeId.equals(curRouteId)) return "Your current route is the same as the one chosen";
				busRepo.changeRoute(conductorId, routeName);
				
			}
			else
				return "There is no such Route";
		}
		else {
			
			return "You are not mapped with any bus";
		}
		return "Successfully changed";
	}

	public String startService(Integer conductorId) {
		if(conductorRepo.isMapped(conductorId)) {
			if(busRepo.hasRouteSet(conductorId)) {
				if(!conductorRepo.started(conductorId)) {
					conductorRepo.start(conductorId);
				}else {
					return "Service has already begun";
				}
			}else {
				return "There is no route set for this bus";
			}
		}else {
			return "You are not mapped with any bus";
		}
		return "Service started";
	}

	public String endService(Integer conductorId) {
		if(conductorRepo.isMapped(conductorId)) {
			if(conductorRepo.started(conductorId)) {
				conductorRepo.end(conductorId);
			}else {
				return "Service has already ended";
			}
		}else {
			return "You are not mapped with any bus";
		}
		return "Service ended";
	}

	public String breakdown(Integer conductorId) {
		ticketRepo.priveleged(conductorId);
		return "";
	}

}
