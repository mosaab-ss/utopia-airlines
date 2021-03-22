package com.ss.UtopiaAirlines.entity;

import javax.persistence.*;
import java.util.List;

@Entity
public class Airport {
	private String iataId;
	private String city;
	private List<Route> originRoutes;
	private List<Route> destRoutes;

	@Id
	@Column(name = "iata_id", nullable = false, length = 3)
	public String getIataId() {
		return iataId;
	}

	public void setIataId(String iataId) {
		this.iataId = iataId;
	}

	@Basic
	@Column(name = "city", nullable = false, length = 45)
	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		Airport airport = (Airport) o;

		if (iataId != null ? !iataId.equals(airport.iataId) : airport.iataId != null) return false;
		if (city != null ? !city.equals(airport.city) : airport.city != null) return false;

		return true;
	}

	@Override
	public int hashCode() {
		int result = iataId != null ? iataId.hashCode() : 0;
		result = 31 * result + (city != null ? city.hashCode() : 0);
		return result;
	}

	@OneToMany(mappedBy = "originAirport")
	public List<Route> getOriginRoutes() {
		return originRoutes;
	}

	public void setOriginRoutes(List<Route> originRoutes) {
		this.originRoutes = originRoutes;
	}

	@OneToMany(mappedBy = "destAirport")
	public List<Route> getDestRoutes() {
		return destRoutes;
	}

	public void setDestRoutes(List<Route> destRoutes) {
		this.destRoutes = destRoutes;
	}
}
