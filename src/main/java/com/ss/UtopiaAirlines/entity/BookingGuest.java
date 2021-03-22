package com.ss.UtopiaAirlines.entity;

import javax.persistence.*;

@Entity
@Table(name = "booking_guest", schema = "utopia", catalog = "")
public class BookingGuest {
	private Integer bookingId;
	private String contactEmail;
	private String contactPhone;
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
	@Column(name = "contact_email", nullable = false, length = 255)
	public String getContactEmail() {
		return contactEmail;
	}

	public void setContactEmail(String contactEmail) {
		this.contactEmail = contactEmail;
	}

	@Basic
	@Column(name = "contact_phone", nullable = false, length = 45)
	public String getContactPhone() {
		return contactPhone;
	}

	public void setContactPhone(String contactPhone) {
		this.contactPhone = contactPhone;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		BookingGuest that = (BookingGuest) o;

		if (bookingId != null ? !bookingId.equals(that.bookingId) : that.bookingId != null) return false;
		if (contactEmail != null ? !contactEmail.equals(that.contactEmail) : that.contactEmail != null) return false;
		if (contactPhone != null ? !contactPhone.equals(that.contactPhone) : that.contactPhone != null) return false;

		return true;
	}

	@Override
	public int hashCode() {
		int result = bookingId != null ? bookingId.hashCode() : 0;
		result = 31 * result + (contactEmail != null ? contactEmail.hashCode() : 0);
		result = 31 * result + (contactPhone != null ? contactPhone.hashCode() : 0);
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
