package com.backend.payanam.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.backend.payanam.model.Stoppings;

@Repository
public interface StoppingRepository extends JpaRepository<Stoppings, Integer>{

	@Query("select count(*) > 0 from Stoppings s where s.stoppingName=:stoppingName")
	public boolean existsByName(String stoppingName);

	public Stoppings findByStoppingName(String stoppingName);

	@Query("select s.stoppingId from Stoppings s where s.stoppingName=:stoppingName")
	public Integer getIdOf(String stoppingName);

}
