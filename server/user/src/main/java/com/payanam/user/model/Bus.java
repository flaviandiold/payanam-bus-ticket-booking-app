package com.payanam.user.model;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name="bus")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder
@Data
public class Bus {
	
	@Id
	@Column(name="bus_id")
	@GeneratedValue(strategy = GenerationType.UUID)
	private UUID busId;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name="route_id")
	private Route route;
	
	@Builder.Default
	@Column(name="bus_type", nullable=false)
	private String busType = "NORMAL";
	
	@Column(name="licence_number")
	private String licenceNumber;
	
//	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "bus")
//	private List<Tickets> tickets;
	
//	@OneToOne(fetch=FetchType.LAZY)
//	@JoinColumn(name="conductor_id")
//	private Conductor conductor;
	
//	public List<Tickets> getTickets() {
//        return this.tickets;
//    }
//
//    public void setTickets(List<Tickets> tickets) {
//        this.tickets = tickets;
//    }
//
//    public void add(Tickets ticket) {
//
//        if (ticket != null) {
//            if (this.tickets == null) {
//                this.tickets = new ArrayList<>();
//            }
//
//            this.tickets.add(ticket);
//        }
//    }

	
//	public void addTickets(Tickets entry) {
//		this.tickets.add(entry);
//		System.out.print(this.tickets);
//	}

}
