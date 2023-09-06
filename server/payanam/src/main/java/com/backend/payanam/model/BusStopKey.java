package com.backend.payanam.model;

import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Embeddable
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BusStopKey implements Serializable{

	private static final long serialVersionUID = -3532160296179831041L;
	
	@Column(name="bus_id")
	private UUID busId;
	
	@Column(name="stopping_id")
	private Integer stoppingId;

	@Override
	public int hashCode() {
		return Objects.hash(busId, stoppingId);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		BusStopKey other = (BusStopKey) obj;
		return busId == other.busId && stoppingId == other.stoppingId;
	}
	
	
	
}
