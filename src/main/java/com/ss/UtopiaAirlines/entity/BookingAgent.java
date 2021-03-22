package com.ss.UtopiaAirlines.entity;

import javax.persistence.*;

@Entity
@Table(name = "booking_agent", schema = "utopia", catalog = "")
public class BookingAgent {
	private Integer bookingId;
	private Integer agentId;
	private Booking booking;
	private User user;

	@Id
	@Column(name = "booking_id", nullable = false)
	public Integer getBookingId() {
		return bookingId;
	}

	public void setBookingId(Integer bookingId) {
		this.bookingId = bookingId;
	}

	@Basic
	@Column(name = "agent_id", nullable = false)
	public Integer getAgentId() {
		return agentId;
	}

	public void setAgentId(Integer agentId) {
		this.agentId = agentId;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		BookingAgent that = (BookingAgent) o;

		if (bookingId != null ? !bookingId.equals(that.bookingId) : that.bookingId != null) return false;
		if (agentId != null ? !agentId.equals(that.agentId) : that.agentId != null) return false;

		return true;
	}

	@Override
	public int hashCode() {
		int result = bookingId != null ? bookingId.hashCode() : 0;
		result = 31 * result + (agentId != null ? agentId.hashCode() : 0);
		return result;
	}

	@OneToOne
	@JoinColumn(name = "booking_id", referencedColumnName = "id", nullable = false)
	public Booking getBooking() {
		return booking;
	}

	public void setBooking(Booking booking) {
		this.booking = booking;
	}

	@ManyToOne
	@JoinColumn(name = "agent_id", referencedColumnName = "id", nullable = false)
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
}
