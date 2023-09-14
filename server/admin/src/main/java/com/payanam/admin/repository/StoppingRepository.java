package com.payanam.admin.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.payanam.admin.model.Stoppings;

@Repository
public interface StoppingRepository extends JpaRepository<Stoppings, Integer>{

	@Query("select count(*) > 0 from Stoppings s where s.stoppingName=:stoppingName")
	public boolean existsByName(String stoppingName);

	public Stoppings findByStoppingName(String stoppingName);

	@Query("select s.stoppingId from Stoppings s where s.stoppingName=:stoppingName")
	public Integer getIdOf(String stoppingName);

	@Query("select rs.stoppingOrder from Stoppings s join RouteStop rs on rs.stoppings.stoppingId=s.stoppingId join Route r on r.routeId=rs.route.routeId join Bus b on b.route.routeId=r.routeId where b.conductor.userId=:conductorId and s.stoppingName=:stop")
	public Integer orderOf(String stop, Integer conductorId);

}
