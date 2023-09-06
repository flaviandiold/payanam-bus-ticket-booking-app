package com.backend.payanam.model;

import java.util.List;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="bus",  uniqueConstraints=
	@UniqueConstraint(columnNames={"bus_route", "bus_type"}))
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class Bus {
	
	@Id
	@Column(name="bus_id")
	@GeneratedValue(strategy = GenerationType.UUID)
	private UUID busId;
	
	@Column(name="bus_route", nullable=false)
	private String routeName;
	
	@Builder.Default
	@Column(name="bus_type", nullable=false)
	private String busType = "NORMAL";
	
	@OneToMany(mappedBy="bus")
	private List<Tickets> tickets;
//	
	@OneToMany(mappedBy = "bus")
    List<BusStop> stoppings;
}
