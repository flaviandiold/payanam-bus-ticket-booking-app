package com.payanam.user.repository;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.payanam.user.model.Tickets;
import com.payanam.user.model.template.TicketsDTO;

import jakarta.transaction.Transactional;

@Repository
@Transactional
public interface TicketRepository extends JpaRepository<Tickets, UUID>{

	@Query("select t.validFrom from Tickets t where t.ticketId=:ticketId")
	public LocalTime getValidFromOf(UUID ticketId);

//	@Query("select t.validTo from Tickets t where t.ticketId=:ticketId")
//	public LocalTime getValidToOf(UUID ticketId);

	@Modifying
	@Query("update Tickets t set t.status=true, t.payedAt=:localTime, t.valid=true where t.details.orderId=:orderId")
	public void changePaymentStatusOf(String orderId, LocalDateTime localTime);

	@Modifying
	@Query("update Tickets t set t.details.orderId=:orderId where t.ticketId=:ticketId")
	public void updateOrderIdOf(UUID ticketId, String orderId);
	
	@Query("select new com.payanam.user.model.template.TicketsDTO(t.ticketId,t.bus.route.routeName,t.bus.busType,t.details.orderId,t.price,t.from,t.to,t.privilege,t.status,t.validFrom,t.valid,t.payedAt) from Tickets t where t.userTickets.userId=:userId order by (case when t.status then 1 else 2 end) asc")
	public Page<TicketsDTO> findAllTicketsOf(Integer userId, Pageable pageable);

	@Query("select count(*) > 0 from Tickets t where t.status=true and t.ticketId=:id")
	public boolean isPaid(UUID id);

//	@Query("select t from Tickets t where t.userTickets.userId=:userId")
	public List<Tickets> findAllByUserTickets_UserId(Integer userId);


	@Query(value="select count(*) > 0 from bus b join route_stops rs on b.route_id=rs.route_id join stoppings s on rs.stopping_id=s.stopping_id where b.bus_id=:busId and s.stopping_name=(select t._to from ticket t where t.ticket_id=:ticketId)",nativeQuery = true)
	public boolean isValidBusToEnter(UUID busId, UUID ticketId);

	@Modifying
	@Transactional
	@Query(value="update ticket set bus_id=:busId, valid=true where user_id=:userId and privilege is true and valid is false",nativeQuery = true)
	public Integer validate(Integer userId, UUID busId);
	
}
