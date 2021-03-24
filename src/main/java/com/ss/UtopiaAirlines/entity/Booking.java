package com.ss.UtopiaAirlines.entity;

import javax.persistence.*;
import java.util.List;

@Entity
public class Booking {
	private Integer id;
	private Boolean isActive;
	private String confirmationCode;
	private Integer seatClass;
	private BookingAgent bookingAgent;
	private BookingGuest bookingGuest;
	private BookingPayment bookingPayment;
	private BookingUser bookingUser;
	private List<FlightBookings> flightBookings;
	private List<Passenger> passengers;

	@Id
	@Column(name = "id", nullable = false)
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Basic
	@Column(name = "is_active", nullable = false)
	public Boolean getActive() {
		return isActive;
	}

	public void setActive(Boolean active) {
		isActive = active;
	}

	@Basic
	@Column(name = "confirmation_code", nullable = false, length = 255)
	public String getConfirmationCode() {
		return confirmationCode;
	}

	public void setConfirmationCode(String confirmationCode) {
		this.confirmationCode = confirmationCode;
	}

	@Basic
	@Column(name = "seat_class", nullable = false)
	public Integer getSeatClass() {
		return seatClass;
	}

	public void setSeatClass(Integer seatClass) {
		this.seatClass = seatClass;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		Booking booking = (Booking) o;

		if (id != null ? !id.equals(booking.id) : booking.id != null) return false;
		if (isActive != null ? !isActive.equals(booking.isActive) : booking.isActive != null) return false;
		if (confirmationCode != null ? !confirmationCode.equals(booking.confirmationCode) : booking.confirmationCode != null)
			return false;
		if (seatClass != null ? !seatClass.equals(booking.seatClass) : booking.seatClass != null) return false;

		return true;
	}

	@Override
	public int hashCode() {
		int result = id != null ? id.hashCode() : 0;
		result = 31 * result + (isActive != null ? isActive.hashCode() : 0);
		result = 31 * result + (confirmationCode != null ? confirmationCode.hashCode() : 0);
		result = 31 * result + (seatClass != null ? seatClass.hashCode() : 0);
		return result;
	}

	@OneToOne(mappedBy = "booking")
	public BookingAgent getBookingAgent() {
		return bookingAgent;
	}

	public void setBookingAgent(BookingAgent bookingAgent) {
		this.bookingAgent = bookingAgent;
	}

	@OneToOne(mappedBy = "booking")
	public BookingGuest getBookingGuest() {
		return bookingGuest;
	}

	public void setBookingGuest(BookingGuest bookingGuest) {
		this.bookingGuest = bookingGuest;
	}

	@OneToOne(mappedBy = "booking")
	public BookingPayment getBookingPayment() {
		return bookingPayment;
	}

	public void setBookingPayment(BookingPayment bookingPayment) {
		this.bookingPayment = bookingPayment;
	}

	@OneToOne(mappedBy = "booking")
	public BookingUser getBookingUser() {
		return bookingUser;
	}

	public void setBookingUser(BookingUser bookingUser) {
		this.bookingUser = bookingUser;
	}

	@OneToMany(mappedBy = "booking")
	public List<FlightBookings> getFlightBookings() {
		return flightBookings;
	}

	public void setFlightBookings(List<FlightBookings> flightBookings) {
		this.flightBookings = flightBookings;
	}

	@OneToMany(mappedBy = "booking")
	public List<Passenger> getPassengers() {
		return passengers;
	}

	public void setPassengers(List<Passenger> passengers) {
		this.passengers = passengers;
	}
}
