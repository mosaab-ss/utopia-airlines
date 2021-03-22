package com.ss.UtopiaAirlines.entity;

import javax.persistence.*;
import java.util.List;

@Entity
public class Airplane {
	private Integer id;
	private Integer typeId;
	private AirplaneType airplaneType;
	private List<Flight> flights;

	@Id
	@Column(name = "id", nullable = false)
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Basic
	@Column(name = "type_id", nullable = false)
	public Integer getTypeId() {
		return typeId;
	}

	public void setTypeId(Integer typeId) {
		this.typeId = typeId;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		Airplane airplane = (Airplane) o;

		if (id != null ? !id.equals(airplane.id) : airplane.id != null) return false;
		if (typeId != null ? !typeId.equals(airplane.typeId) : airplane.typeId != null) return false;

		return true;
	}

	@Override
	public int hashCode() {
		int result = id != null ? id.hashCode() : 0;
		result = 31 * result + (typeId != null ? typeId.hashCode() : 0);
		return result;
	}

	@ManyToOne
	@JoinColumn(name = "type_id", referencedColumnName = "id", nullable = false)
	public AirplaneType getAirplaneType() {
		return airplaneType;
	}

	public void setAirplaneType(AirplaneType airplaneType) {
		this.airplaneType = airplaneType;
	}

	@OneToMany(mappedBy = "airplane")
	public List<Flight> getFlights() {
		return flights;
	}

	public void setFlights(List<Flight> flights) {
		this.flights = flights;
	}
}
