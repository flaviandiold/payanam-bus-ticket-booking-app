package com.payanam.admin.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name="routes")
@Getter
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Route {
	
	@Id
	@Column(name="route_id")
	private Integer routeId;
	
	@Column(name="route_name")
	private String routeName;
	
//	@OneToMany(mappedBy = "")
//	@JoinColumn(name = "stopping_id")
//    List<Stoppings> stoppings;
	
//	@Column(name="bus_id")
//	private UUID busId;
		
//	@OneToMany(mappedBy = "route")
//	private List<Bus> bus;

}
