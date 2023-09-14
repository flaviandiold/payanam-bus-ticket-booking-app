package com.payanam.user.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.payanam.user.model.Bus;
import com.payanam.user.model.RouteStop;
import com.payanam.user.model.Stoppings;

import jakarta.transaction.Transactional;

@Repository
public interface BusRepository extends JpaRepository<Bus, UUID> {
	
	@Query("select s from Route r inner join RouteStop rs on r.routeId=rs.id.routeId inner join Stoppings s on s.stoppingId=rs.id.stoppingId WHERE r.routeId = :routeId ORDER BY stoppingOrder ASC")
	public List<Stoppings> getStoppingsOf(Integer routeId);

	@Query("select b.route.routeName || ' ' || b.busType AS busName from Bus b where b.busId=:busId")
	public String getName(UUID busId);

	@Query("select count(b) > 0 from Bus b where b.route.routeName=:routeName and b.busType=:busType")
	public boolean existsBus(String routeName, String busType);

	@Query("select b.route.routeName from Bus b where b.busId=:busId")
	public String findRouteNameById(UUID busId);

//	@Modifying
//	@Transactional
//	@Query(value="INSERT INTO bus_stop_junction ( bus_id, stopping_id, stopping_order ) values ( :entry.bus.busId, :entry.stoppings.stoppingId, :entry.stoppingOrder",nativeQuery = true)
//	public void saveStopping(BusStop entry);
	
	
	
}
