package com.ss.UtopiaAirlines.entity;

import javax.persistence.Column;
import javax.persistence.Id;
import java.io.Serializable;

public class FlightBookingsPK implements Serializable {
	private Integer flightId;
	private Integer bookingId;

	@Column(name = "flight_id", nullable = false)
	@Id
	public Integer getFlightId() {
		return flightId;
	}

	public void setFlightId(Integer flightId) {
		this.flightId = flightId;
	}

	@Column(name = "booking_id", nullable = false)
	@Id
	public Integer getBookingId() {
		return bookingId;
	}

	public void setBookingId(Integer bookingId) {
		this.bookingId = bookingId;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		FlightBookingsPK that = (FlightBookingsPK) o;

		if (flightId != null ? !flightId.equals(that.flightId) : that.flightId != null) return false;
		if (bookingId != null ? !bookingId.equals(that.bookingId) : that.bookingId != null) return false;

		return true;
	}

	@Override
	public int hashCode() {
		int result = flightId != null ? flightId.hashCode() : 0;
		result = 31 * result + (bookingId != null ? bookingId.hashCode() : 0);
		return result;
	}
}
