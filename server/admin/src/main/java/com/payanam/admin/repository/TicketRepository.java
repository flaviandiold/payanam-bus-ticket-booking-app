package com.payanam.admin.repository;

import java.time.LocalTime;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.payanam.admin.model.Tickets;
import com.payanam.admin.model.template.TicketsDTO;

import jakarta.transaction.Transactional;

@Repository
@Transactional
public interface TicketRepository extends JpaRepository<Tickets, UUID>{

	@Query("select t.validFrom from Tickets t where t.ticketId=:ticketId")
	public LocalTime getValidFromOf(UUID ticketId);

//	@Query("select t.validTo from Tickets t where t.ticketId=:ticketId")
//	public LocalTime getValidToOf(UUID ticketId);

	@Modifying
	@Query("update Tickets t set t.status=true, t.payedAt=:localTime,t.valid=true where t.details.orderId=:orderId")
	public void changePaymentStatusOf(String orderId, LocalTime localTime);

	@Modifying
	@Query("update Tickets t set t.details.orderId=:orderId where t.ticketId=:ticketId")
	public void updateOrderIdOf(UUID ticketId, String orderId);
	
	@Query("select new com.payanam.admin.model.template.TicketsDTO(t.ticketId,t.bus.route.routeName,t.bus.busType,t.details.orderId,t.price,t.from,t.to,t.privilege,t.status,t.validFrom,t.valid,t.payedAt) from Tickets t where t.userTickets.userId=:userId order by (case when t.status then 1 else 2 end) asc")
	public Page<TicketsDTO> findAllTicketsOf(Integer userId, Pageable pageable);

	@Query("select count(*) > 0 from Tickets t where t.status=true and t.ticketId=:id")
	public boolean isPaid(UUID id);

	@Modifying
	@Transactional
	@Query(value="update ticket set valid=false from bus b join route_stops rs on b.route_id=rs.route_id join stoppings s on rs.stopping_id = s.stopping_id where b.conductor_id=:conductorId and (select count(*) > 0 from conductor c where c.conductor_id=:conductorId and c.service_status is true) and s.stopping_name=:stop and rs.stopping_order<=:stopOrder and valid=true",nativeQuery=true)
	public Integer invalidate(Integer stopOrder, Integer conductorId, String stop);

	@Modifying
	@Transactional
	@Query(value="update ticket set privilege=true,valid=false,bus_id=null from bus b join conductor c on c.conductor_id=b.conductor_id where c.conductor_id=:conductorId and payment_status is true and valid is true", nativeQuery = true)
	public void priveleged(Integer conductorId);


}
