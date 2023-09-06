package com.backend.payanam.service;

import java.util.Random;

import org.springframework.stereotype.Service;

import com.backend.payanam.model.Bus;
import com.backend.payanam.model.repository.BusRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AdminService {
	
//	private final BusRepository busRepo;
//	
//	public String addBus(String busRoute, String busType) {
//		if(busRepo.existsBus(busRoute, busType)) {
//			return null;
//		}
//		Random rand = new Random();
//		int id = rand.nextInt(1000,10000);
//		while(busRepo.existsById(id)) {
//			id = rand.nextInt(1000,10000);
//		}
//		Bus entry = Bus.builder()
//						.busId(id)
//						.routeName(busRoute)
//						.busType(busType)
//						.build();
//		busRepo.save(entry);
//		return "SuccessFully Added";
//	}

}
