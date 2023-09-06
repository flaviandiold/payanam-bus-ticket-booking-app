package com.backend.payanam.controller;


import java.util.UUID;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.backend.payanam.model.template.Response;
import com.backend.payanam.model.template.StoppingResponse;
import com.backend.payanam.service.PayaneeService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api/admin")
public class AdminController {
	
	private final PayaneeService payaneeService;
	
	@PostMapping("/add-bus")
	public ResponseEntity<Response> addBus(@RequestBody AddBusRequest req){
		log.info(req.busRoute() + " " + req.busType());
		Response res = payaneeService.addBus(req.busRoute(), req.busType());
		if(res == null) {
			res = Response.builder()
					.message("Such a Bus Exists")
					.build();
			return new ResponseEntity<Response>(res, HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<Response>(res, HttpStatus.ACCEPTED);
	}

	@PostMapping("/add-stop")
	public ResponseEntity<Response> addStop(@RequestBody AddStopRequest req){
//		log.info(req.busRoute() + " " + req.busType());
		Response res = payaneeService.addStop(req.busId(), req.stoppingName(), req.stoppingOrder());
		if(res == null) {
			res = Response.builder()
					.message("Such a Entry Exists")
					.build();
			return new ResponseEntity<Response>(res, HttpStatus.BAD_REQUEST);
		}
		if(res.message().equals("No Such Bus exists")) {
			return new ResponseEntity<Response>(res, HttpStatus.NOT_FOUND);
		}
		if(res.message().equals("Enter a valid stopping order")) {
			return new ResponseEntity<Response>(res, HttpStatus.CONFLICT);
		}
		return new ResponseEntity<Response>(res, HttpStatus.ACCEPTED);
	} 
}

record AddBusRequest (String busRoute, String busType) {}

record AddStopRequest (UUID busId, String stoppingName, Integer stoppingOrder) {}


