package com.backend.payanam.model.repository;

import java.time.LocalTime;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.backend.payanam.model.Tickets;
import com.backend.payanam.model.template.TicketsDTO;

import jakarta.transaction.Transactional;

@Repository
@Transactional
public interface TicketRepository extends JpaRepository<Tickets, UUID>{

	@Query("select t.validFrom from Tickets t where t.ticketId=:ticketId")
	public LocalTime getValidFromOf(UUID ticketId);

	@Query("select t.validTo from Tickets t where t.ticketId=:ticketId")
	public LocalTime getValidToOf(UUID ticketId);

	@Modifying
	@Query("update Tickets t set t.status=true, t.payedAt=:localTime where t.details.orderId=:orderId")
	public void changePaymentStatusOf(String orderId, LocalTime localTime);

	@Modifying
	@Query("update Tickets t set t.details.orderId=:orderId where t.ticketId=:ticketId")
	public void updateOrderIdOf(UUID ticketId, String orderId);
	
	@Query("select new com.backend.payanam.model.template.TicketsDTO(t.ticketId,t.bus.routeName,t.bus.busType,t.details.orderId,t.price,t.from,t.to,t.privilegeMessage,t.status,t.validFrom,t.validTo,t.payedAt) from Tickets t where t.userTickets.userId=:userId order by (case when t.status then 1 else 2 end) asc")
	public Page<TicketsDTO> findAllTicketsOf(Integer userId, Pageable pageable);

	@Query("select count(*) > 0 from Tickets t where t.status=true and t.ticketId=:id")
	public boolean isPaid(UUID id);

}
