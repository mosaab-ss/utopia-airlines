package com.ss.UtopiaAirlines.entity;

import javax.persistence.*;
import java.sql.Date;

@Entity
public class Passenger {
	private Integer id;
	private Integer bookingId;
	private String givenName;
	private String familyName;
	private Date dob;
	private String gender;
	private String address;
	private Booking booking;

	@Id
	@Column(name = "id", nullable = false)
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Basic
	@Column(name = "booking_id", nullable = false)
	public Integer getBookingId() {
		return bookingId;
	}

	public void setBookingId(Integer bookingId) {
		this.bookingId = bookingId;
	}

	@Basic
	@Column(name = "given_name", nullable = false, length = 255)
	public String getGivenName() {
		return givenName;
	}

	public void setGivenName(String givenName) {
		this.givenName = givenName;
	}

	@Basic
	@Column(name = "family_name", nullable = false, length = 255)
	public String getFamilyName() {
		return familyName;
	}

	public void setFamilyName(String familyName) {
		this.familyName = familyName;
	}

	@Basic
	@Column(name = "dob", nullable = false)
	public Date getDob() {
		return dob;
	}

	public void setDob(Date dob) {
		this.dob = dob;
	}

	@Basic
	@Column(name = "gender", nullable = false, length = 45)
	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	@Basic
	@Column(name = "address", nullable = false, length = 45)
	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		Passenger passenger = (Passenger) o;

		if (id != null ? !id.equals(passenger.id) : passenger.id != null) return false;
		if (bookingId != null ? !bookingId.equals(passenger.bookingId) : passenger.bookingId != null) return false;
		if (givenName != null ? !givenName.equals(passenger.givenName) : passenger.givenName != null) return false;
		if (familyName != null ? !familyName.equals(passenger.familyName) : passenger.familyName != null) return false;
		if (dob != null ? !dob.equals(passenger.dob) : passenger.dob != null) return false;
		if (gender != null ? !gender.equals(passenger.gender) : passenger.gender != null) return false;
		if (address != null ? !address.equals(passenger.address) : passenger.address != null) return false;

		return true;
	}

	@Override
	public int hashCode() {
		int result = id != null ? id.hashCode() : 0;
		result = 31 * result + (bookingId != null ? bookingId.hashCode() : 0);
		result = 31 * result + (givenName != null ? givenName.hashCode() : 0);
		result = 31 * result + (familyName != null ? familyName.hashCode() : 0);
		result = 31 * result + (dob != null ? dob.hashCode() : 0);
		result = 31 * result + (gender != null ? gender.hashCode() : 0);
		result = 31 * result + (address != null ? address.hashCode() : 0);
		return result;
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
