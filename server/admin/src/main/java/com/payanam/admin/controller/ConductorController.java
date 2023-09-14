package com.payanam.admin.controller;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.print.DocFlavor.READER;

import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.payanam.admin.service.ConductorService;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api/conductor")
public class ConductorController {
	
	private final ConductorService service;
	
	@PutMapping("/assign-bus/{bus_id}")
	public ResponseEntity<Map<String,String>> allocateBus(@PathVariable("bus_id") UUID busId, HttpServletRequest req){
		if(!service.mapConductor(Integer.valueOf(service.getSubject(req.getHeader("Authorization").split(" ")[1])), busId )) {
			return new ResponseEntity<Map<String, String>>(Map.of("message","Not assigned"),HttpStatus.BAD_REQUEST);
		}
		List<String> stoppings = service.getRouteOf(busId);
		Map<String,String> map = new LinkedHashMap<>();
		map.put("message","You are assigned to bus");
		int i = 0;
		for(String stop : stoppings) {
			map.put(String.valueOf(i++),stop);
		}
		return new ResponseEntity<Map<String, String>>(map,HttpStatus.OK);
	}
	
	@PostMapping("/start-service")
	public ResponseEntity<Map<String,String>> startService(HttpServletRequest req){
		String message = service.startService(service.getSubject(req.getHeader("Authorization").split(" ")[1]));
		if(message.equals("You are not mapped with any bus")) {
			return new ResponseEntity<Map<String,String>>(Map.of("message",message),HttpStatus.BAD_REQUEST);
		}else if(message.equals("There is no route set for this bus")) {
			return new ResponseEntity<Map<String,String>>(Map.of("message",message),HttpStatus.NOT_FOUND);
		}else if(message.equals("Service has already begun")) {
			return new ResponseEntity<Map<String,String>>(Map.of("message",message),HttpStatus.ALREADY_REPORTED);
		}else{
			return new ResponseEntity<Map<String,String>>(Map.of("message",message),HttpStatus.OK);
		}
	}
	
	@PostMapping("/invalidate")
	public ResponseEntity<Map<String,String>> invalidate(@RequestBody InvalidateRequest stop, HttpServletRequest req){
//		System.out.println(stop.stopping());
		String message = service.invalidate(stop.stopping(),service.getSubject(req.getHeader("Authorization").split(" ")[1]));
		if(message.equals("You are not mapped with any bus")) {
			return new ResponseEntity<Map<String,String>>(Map.of("message",message),HttpStatus.BAD_REQUEST);
		}
//		if(message.equals("Already invalidated")) {
//			return new ResponseEntity<Map<String,String>>(Map.of("message",message),HttpStatus.ALREADY_REPORTED);
//		}
		if(message.equals("Service has not yet started")) {
			return new ResponseEntity<Map<String,String>>(Map.of("message",message),HttpStatus.BAD_REQUEST);
		}
		if(message.equals("No Such Stopping Exists")) {
			return new ResponseEntity<Map<String,String>>(Map.of("message",message),HttpStatus.NOT_FOUND);			
		}
		if(message.isEmpty()) {
			return new ResponseEntity<Map<String,String>>(Map.of("message","Cannot Invalidate"),HttpStatus.SERVICE_UNAVAILABLE);
		}
		return new ResponseEntity<Map<String,String>>(Map.of("message",message),HttpStatus.OK);
	}
	
	@PostMapping("/relieve-bus")
	public ResponseEntity<Map<String,String>> relieve(HttpServletRequest req){
		String message = service.relieve(service.getSubject(req.getHeader("Authorization").split(" ")[1]));
		if(message.equals("You are not mapped with any bus")) {
			return new ResponseEntity<Map<String,String>>(Map.of("message",message),HttpStatus.BAD_REQUEST);
		}else {
			return new ResponseEntity<Map<String,String>>(Map.of("message",message),HttpStatus.OK);
		}
	}
	
	@PostMapping("/change-route/{route-name}")
	public ResponseEntity<Map<String,String>> changeRoute(@PathVariable("route-name") String routeName, HttpServletRequest req){
		String message = service.changeRoute(service.getSubject(req.getHeader("Authorization").split(" ")[1]),routeName);
		if(message.equals("You are not mapped with any bus")) {
			return new ResponseEntity<Map<String,String>>(Map.of("message",message),HttpStatus.BAD_REQUEST);
		}else if(message.equals("There is no such Route")) {
			return new ResponseEntity<Map<String,String>>(Map.of("message",message),HttpStatus.NOT_FOUND);
		}else if(message.equals("Your current route is the same as the one chosen")) {
			return new ResponseEntity<Map<String,String>>(Map.of("message",message),HttpStatus.ALREADY_REPORTED);
		}else{
			return new ResponseEntity<Map<String,String>>(Map.of("message",message),HttpStatus.OK);
		}
	}
	
	@PostMapping("/breakdown")
	public ResponseEntity<Map<String,String>> breakdown(HttpServletRequest req){
		String message = service.breakdown(service.getSubject(req.getHeader("Authorization").split(" ")[1]));
		if(message.equals("You are not mapped with any bus")) {
			return new ResponseEntity<Map<String,String>>(Map.of("message",message),HttpStatus.BAD_REQUEST);
		}else if(message.equals("There is no such Route")) {
			return new ResponseEntity<Map<String,String>>(Map.of("message",message),HttpStatus.NOT_FOUND);
		}else if(message.equals("Your current route is the same as the one chosen")) {
			return new ResponseEntity<Map<String,String>>(Map.of("message",message),HttpStatus.ALREADY_REPORTED);
		}else{
			return new ResponseEntity<Map<String,String>>(Map.of("message",message),HttpStatus.OK);
		}
	}
	
	
	@PostMapping("/end-service")
	public ResponseEntity<Map<String,String>> endService(HttpServletRequest req){
		String message = service.endService(service.getSubject(req.getHeader("Authorization").split(" ")[1]));
		if(message.equals("You are not mapped with any bus")) {
			return new ResponseEntity<Map<String,String>>(Map.of("message",message),HttpStatus.BAD_REQUEST);
		}else if(message.equals("Service has already ended")) {
			return new ResponseEntity<Map<String,String>>(Map.of("message",message),HttpStatus.ALREADY_REPORTED);
		}else{
			return new ResponseEntity<Map<String,String>>(Map.of("message",message),HttpStatus.OK);
		}
	}
	
}

record InvalidateRequest (String stopping) {}
