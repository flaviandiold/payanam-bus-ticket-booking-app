package com.backend.payanam.model.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.backend.payanam.model.User;
import com.backend.payanam.model.template.TicketsDTO;

import jakarta.transaction.Transactional;

@Repository
@Transactional
public interface UserRepository extends JpaRepository<User, Integer>{
	
	@Query("select u from User u where u.email=:email")
	public User findByEmail(String email);
	
	@Query("select u from User u")
	public List<User> findAllUser();
	
	public boolean existsUserByEmail(String email);
	
	@Query("select u.userId from User u where u.email=:email")
	public Integer getIdOf(@Param("email") String email);
	
	@Modifying
	@Query("update User u set u.password=:password where u.email=:email")
	public void changeUserPassword(String email, String password);
	
	@Query("select u.password from User u where u.email=:email")
	public String getPasswordOf(String email);

	@Query("select u.name from User u where u.userId=:userId")
	public String getName(Integer userId);
	
	@Query("select u.email from User u where u.userId=:userId")
	public String getEmail(Integer userId);
	
	
}
