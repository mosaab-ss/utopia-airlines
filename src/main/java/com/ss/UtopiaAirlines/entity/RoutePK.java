package com.ss.UtopiaAirlines.entity;

import javax.persistence.Column;
import javax.persistence.Id;
import java.io.Serializable;

public class RoutePK implements Serializable {
	private Integer id;
	private Integer originId;
	private String destinationId;

	@Column(name = "id", nullable = false)
	@Id
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Column(name = "origin_id", nullable = false, length = 3)
	@Id
	public Integer getOriginId() {
		return originId;
	}

	public void setOriginId(Integer originId) {
		this.originId = originId;
	}

	@Column(name = "destination_id", nullable = false, length = 3)
	@Id
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

		RoutePK routePK = (RoutePK) o;

		if (id != null ? !id.equals(routePK.id) : routePK.id != null) return false;
		if (originId != null ? !originId.equals(routePK.originId) : routePK.originId != null) return false;
		if (destinationId != null ? !destinationId.equals(routePK.destinationId) : routePK.destinationId != null)
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
}
