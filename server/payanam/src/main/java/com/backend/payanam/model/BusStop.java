package com.backend.payanam.model;

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
@Table(name="bus_stop_junction")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class BusStop {
	
	@EmbeddedId
	private BusStopKey id;
	
	@ManyToOne
    @MapsId("busId")
    @JoinColumn(name = "bus_id")
    Bus bus;
		
    @ManyToOne
    @MapsId("stoppingId")
    @JoinColumn(name = "stopping_id")
    Stoppings stoppings;

    @Column(name="stopping_order", nullable=false)
    private int stoppingOrder;
	
}
