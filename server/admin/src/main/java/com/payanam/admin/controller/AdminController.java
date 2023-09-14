package com.payanam.admin.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.payanam.admin.model.Bus;
import com.payanam.admin.model.template.AuthResponse;
import com.payanam.admin.model.template.Response;
import com.payanam.admin.service.AdminService;
import com.payanam.admin.service.AuthenticationService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api/admin")
public class AdminController {
	
	private final AdminService adminService;
	private final AuthenticationService authService;

	
	@PostMapping("/register-conductor")
	public ResponseEntity<AuthResponse> register(@RequestBody AuthBody req){
		log.info("Inside Auth Register");
		AuthResponse res = authService.register(req.name(),req.email(),req.password());
		if(res == null) {
			System.out.println(authService.respondWith("User exists"));
			return new ResponseEntity<AuthResponse>(authService.respondWith("User exists"), HttpStatus.CONFLICT);
		}
		return ResponseEntity.ok(res);
	}
	
	@PostMapping("/add-bus")
	public ResponseEntity<Response> addBus(@RequestBody AddBusRequest req){
		log.info(req.routeName() + " " + req.busType());
		Response res = adminService.addBus(req.routeName(), req.busType(), req.licenceNumber());
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
		Response res = adminService.addStop(req.routeId(), req.stoppingName(), req.stoppingOrder());
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
	
	@GetMapping("/get-bus/{route_id}")
	public ResponseEntity<List<Bus>> getBusOfRoute(@PathVariable("route_id") Integer routeId) throws JsonProcessingException{
		log.info(String.valueOf(routeId));
		List<Bus> buses = adminService.getAllBusOf(routeId);
		 ObjectMapper mapper = new ObjectMapper();
	     mapper.enable(SerializationFeature.INDENT_OUTPUT);
	     String postJson = mapper.writeValueAsString(buses);
	     log.info(postJson);
//		return null;
		return new ResponseEntity<List<Bus>>(buses,HttpStatus.ACCEPTED);
	}
}

record AddBusRequest (String routeName, String busType, String licenceNumber) {}

record AddStopRequest (Integer routeId, String stoppingName, Integer stoppingOrder) {}
