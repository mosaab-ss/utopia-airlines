package com.ss.UtopiaAirlines.entity;

import javax.persistence.*;

@Entity
@Table(name = "booking_user", schema = "utopia", catalog = "")
public class BookingUser {
	private Integer bookingId;
	private Integer userId;
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
	@Column(name = "user_id", nullable = false)
	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		BookingUser that = (BookingUser) o;

		if (bookingId != null ? !bookingId.equals(that.bookingId) : that.bookingId != null) return false;
		if (userId != null ? !userId.equals(that.userId) : that.userId != null) return false;

		return true;
	}

	@Override
	public int hashCode() {
		int result = bookingId != null ? bookingId.hashCode() : 0;
		result = 31 * result + (userId != null ? userId.hashCode() : 0);
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
	@JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false)
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
}
