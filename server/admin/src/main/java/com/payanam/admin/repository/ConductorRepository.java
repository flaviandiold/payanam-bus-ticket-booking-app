package com.payanam.admin.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.payanam.admin.model.Conductor;

import jakarta.transaction.Transactional;

public interface ConductorRepository extends JpaRepository<Conductor, Integer>{

	public Optional<Conductor> findByEmail(String email);

	@Query("select count(*) > 0 from Conductor c where c.email=:email")
	public boolean existsUserByEmail(String email);

	@Query("select c.userId from Conductor c where c.email=:email")
	public Integer getIdOf(@Param("email") String email);

	@Modifying
	@Transactional
	@Query("update Bus set conductor=null where conductor.userId=:conductorId")
	public void relieve(Integer conductorId);

	@Query("select count(*) > 0 from Bus b where b.conductor.userId=:conductorId")
	public boolean isMapped(Integer conductorId);

	@Query(value="select count(*) > 0 from conductor c where c.conductor_id=:conductorId and c.service_status='STARTED'",nativeQuery = true)
	public boolean started(Integer conductorId);

	@Modifying
	@Transactional
	@Query(value="update conductor set service_status='STARTED' where conductor_id=:conductorId",nativeQuery = true)
	public void start(Integer conductorId);

	@Modifying
	@Transactional
	@Query(value="update conductor set service_status='ENDED' where conductor_id=:conductorId",nativeQuery = true)
	public void end(Integer conductorId);

//	@Query
//	public void relieve(Integer conductorId);

}
