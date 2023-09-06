package com.backend.payanam.model.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.backend.payanam.model.BusStop;
import com.backend.payanam.model.BusStopKey;

@Repository
public interface BusStopRepository extends JpaRepository<BusStop, BusStopKey>{

//	@Query("select count(*) = 0 from BusStop bs where bs.id.busId=:entry.id.busId and bs.stoppingOrder=:entry.stoppingOrder")
//	public boolean isValid(BusStop entry);

	@Query("select count(*) = 0 from BusStop bs where bs.id.busId=:busId and bs.stoppingOrder=:stoppingOrder")
	public boolean isValid(UUID busId, int stoppingOrder);

}
