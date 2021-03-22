package com.ss.UtopiaAirlines.entity;

import javax.persistence.*;

@Entity
@Table(name = "flight_bookings", schema = "utopia", catalog = "")
@IdClass(FlightBookingsPK.class)
public class FlightBookings {
	private Integer flightId;
	private Integer bookingId;
	private Flight flight;
	private Booking booking;

	@Id
	@Column(name = "flight_id", nullable = false)
	public Integer getFlightId() {
		return flightId;
	}

	public void setFlightId(Integer flightId) {
		this.flightId = flightId;
	}

	@Id
	@Column(name = "booking_id", nullable = false)
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

		FlightBookings that = (FlightBookings) o;

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

	@ManyToOne
	@JoinColumn(name = "flight_id", referencedColumnName = "id", nullable = false)
	public Flight getFlight() {
		return flight;
	}

	public void setFlight(Flight flight) {
		this.flight = flight;
	}

	@ManyToOne
	@JoinColumn(name = "booking_id", referencedColumnName = "id", nullable = false)
	public Booking getBooking() {
		return booking;
	}

	public void setBooking(Booking booking) {
		this.booking = booking;
	}
}
