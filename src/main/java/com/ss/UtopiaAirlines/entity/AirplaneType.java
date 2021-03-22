package com.ss.UtopiaAirlines.entity;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "airplane_type", schema = "utopia", catalog = "")
public class AirplaneType {
	private Integer id;
	private Integer maxCapacity;
	private Integer businessClass;
	private Integer firstClass;
	private List<Airplane> airplanes;

	@Id
	@Column(name = "id", nullable = false)
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Basic
	@Column(name = "max_capacity", nullable = false)
	public Integer getMaxCapacity() {
		return maxCapacity;
	}

	public void setMaxCapacity(Integer maxCapacity) {
		this.maxCapacity = maxCapacity;
	}

	@Basic
	@Column(name = "business_class", nullable = false)
	public Integer getBusinessClass() {
		return businessClass;
	}

	public void setBusinessClass(Integer businessClass) {
		this.businessClass = businessClass;
	}

	@Basic
	@Column(name = "first_class", nullable = false)
	public Integer getFirstClass() {
		return firstClass;
	}

	public void setFirstClass(Integer firstClass) {
		this.firstClass = firstClass;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		AirplaneType that = (AirplaneType) o;

		if (id != null ? !id.equals(that.id) : that.id != null) return false;
		if (maxCapacity != null ? !maxCapacity.equals(that.maxCapacity) : that.maxCapacity != null) return false;
		if (businessClass != null ? !businessClass.equals(that.businessClass) : that.businessClass != null)
			return false;
		if (firstClass != null ? !firstClass.equals(that.firstClass) : that.firstClass != null) return false;

		return true;
	}

	@Override
	public int hashCode() {
		int result = id != null ? id.hashCode() : 0;
		result = 31 * result + (maxCapacity != null ? maxCapacity.hashCode() : 0);
		result = 31 * result + (businessClass != null ? businessClass.hashCode() : 0);
		result = 31 * result + (firstClass != null ? firstClass.hashCode() : 0);
		return result;
	}

	@OneToMany(mappedBy = "airplaneType")
	public List<Airplane> getAirplanes() {
		return airplanes;
	}

	public void setAirplanes(List<Airplane> airplanes) {
		this.airplanes = airplanes;
	}
}
