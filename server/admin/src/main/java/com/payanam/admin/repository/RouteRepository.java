package com.payanam.admin.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.payanam.admin.model.Route;

public interface RouteRepository extends JpaRepository<Route, Integer>{

	boolean existsByRouteName(String busRoute);

	Optional<Route> findByRouteName(String busRoute);

	@Query("select r.routeId from Route r where routeName=:routeName")
	Integer getRouteId(String routeName);

}
