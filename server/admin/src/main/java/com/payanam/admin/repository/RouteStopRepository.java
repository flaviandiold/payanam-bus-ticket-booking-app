package com.payanam.admin.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.payanam.admin.model.RouteStop;
import com.payanam.admin.model.RouteStopKey;

@Repository
public interface RouteStopRepository extends JpaRepository<RouteStop, RouteStopKey>{

//	@Query("select count(*) = 0 from BusStop bs where bs.id.busId=:entry.id.busId and bs.stoppingOrder=:entry.stoppingOrder")
//	public boolean isValid(BusStop entry);

	@Query("select count(*) = 0 from RouteStop rs where rs.id.routeId=:routeId and rs.stoppingOrder=:stoppingOrder")
	public boolean isValid(Integer routeId, int stoppingOrder);

}
