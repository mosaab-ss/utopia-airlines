package com.ss.UtopiaAirlines.entity;

import javax.persistence.*;

@Entity
@Table(name = "booking_payment", schema = "utopia", catalog = "")
public class BookingPayment {
	private Integer bookingId;
	private String stripeId;
	private Boolean refunded;
	private Booking booking;

	@Id
	@Column(name = "booking_id", nullable = false)
	public Integer getBookingId() {
		return bookingId;
	}

	public void setBookingId(Integer bookingId) {
		this.bookingId = bookingId;
	}

	@Basic
	@Column(name = "stripe_id", nullable = false, length = 255)
	public String getStripeId() {
		return stripeId;
	}

	public void setStripeId(String stripeId) {
		this.stripeId = stripeId;
	}

	@Basic
	@Column(name = "refunded", nullable = false)
	public Boolean getRefunded() {
		return refunded;
	}

	public void setRefunded(Boolean refunded) {
		this.refunded = refunded;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		BookingPayment that = (BookingPayment) o;

		if (bookingId != null ? !bookingId.equals(that.bookingId) : that.bookingId != null) return false;
		if (stripeId != null ? !stripeId.equals(that.stripeId) : that.stripeId != null) return false;
		if (refunded != null ? !refunded.equals(that.refunded) : that.refunded != null) return false;

		return true;
	}

	@Override
	public int hashCode() {
		int result = bookingId != null ? bookingId.hashCode() : 0;
		result = 31 * result + (stripeId != null ? stripeId.hashCode() : 0);
		result = 31 * result + (refunded != null ? refunded.hashCode() : 0);
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
}
