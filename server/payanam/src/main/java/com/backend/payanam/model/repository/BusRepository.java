package com.backend.payanam.model.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.backend.payanam.model.Bus;
import com.backend.payanam.model.BusStop;

import jakarta.transaction.Transactional;

@Repository
public interface BusRepository extends JpaRepository<Bus, UUID> {
	
	@Query("SELECT s.stoppingName from Bus b inner join BusStop bs on b.busId=bs.id.busId inner join Stoppings s on s.stoppingId=bs.id.stoppingId WHERE b.busId = :busId ORDER BY stoppingOrder ASC")
	public List<String> getStoppingsOf(UUID busId);

	@Query("select b.routeName || ' ' || b.busType AS busName from Bus b where b.busId=:busId")
	public String getName(UUID busId);

	@Query("select count(b) > 0 from Bus b where b.routeName=:routeName and b.busType=:busType")
	public boolean existsBus(String routeName, String busType);

	@Query("select b.routeName from Bus b where b.busId=:busId")
	public String findRouteNameById(UUID busId);

//	@Modifying
//	@Transactional
//	@Query(value="INSERT INTO bus_stop_junction ( bus_id, stopping_id, stopping_order ) values ( :entry.bus.busId, :entry.stoppings.stoppingId, :entry.stoppingOrder",nativeQuery = true)
//	public void saveStopping(BusStop entry);
	
	
	
}
