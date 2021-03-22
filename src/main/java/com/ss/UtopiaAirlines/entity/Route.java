package com.ss.UtopiaAirlines.entity;

import javax.persistence.*;

@Entity
@IdClass(RoutePK.class)
public class Route {
	private Integer id;
	private String originId;
	private String destinationId;
	private Flight flight;
	private Airport originAirport;
	private Airport destAirport;

	@Id
	@Column(name = "id", nullable = false)
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Id
	@Column(name = "origin_id", nullable = false, length = 3)
	public String getOriginId() {
		return originId;
	}

	public void setOriginId(String originId) {
		this.originId = originId;
	}

	@Id
	@Column(name = "destination_id", nullable = false, length = 3)
	public String getDestinationId() {
		return destinationId;
	}

	public void setDestinationId(String destinationId) {
		this.destinationId = destinationId;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		Route route = (Route) o;

		if (id != null ? !id.equals(route.id) : route.id != null) return false;
		if (originId != null ? !originId.equals(route.originId) : route.originId != null) return false;
		if (destinationId != null ? !destinationId.equals(route.destinationId) : route.destinationId != null)
			return false;

		return true;
	}

	@Override
	public int hashCode() {
		int result = id != null ? id.hashCode() : 0;
		result = 31 * result + (originId != null ? originId.hashCode() : 0);
		result = 31 * result + (destinationId != null ? destinationId.hashCode() : 0);
		return result;
	}

	@ManyToOne
	@JoinColumn(name = "id", referencedColumnName = "route_id", nullable = false)
	public Flight getFlight() {
		return flight;
	}

	public void setFlight(Flight flight) {
		this.flight = flight;
	}

	@ManyToOne
	@JoinColumn(name = "origin_id", referencedColumnName = "iata_id", nullable = false)
	public Airport getOriginAirport() {
		return originAirport;
	}

	public void setOriginAirport(Airport originAirport) {
		this.originAirport = originAirport;
	}

	@ManyToOne
	@JoinColumn(name = "destination_id", referencedColumnName = "iata_id", nullable = false)
	public Airport getDestAirport() {
		return destAirport;
	}

	public void setDestAirport(Airport destAirport) {
		this.destAirport = destAirport;
	}
}
