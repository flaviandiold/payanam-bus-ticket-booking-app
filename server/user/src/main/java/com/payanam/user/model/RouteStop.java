package com.payanam.user.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Table(name="route_stops")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class RouteStop {
	
	@EmbeddedId
//	@JsonIgnore
	private RouteStopKey id;
	
	@ManyToOne
    @MapsId("routeId")
    @JoinColumn(name = "route_id")
    Route route;
		
    @ManyToOne
    @MapsId("stoppingId")
    @JoinColumn(name = "stopping_id")
    Stoppings stoppings;

    @Column(name="stopping_order", nullable=false)
    private int stoppingOrder;
	
}
