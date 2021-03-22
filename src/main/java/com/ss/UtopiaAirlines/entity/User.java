package com.ss.UtopiaAirlines.entity;

import javax.persistence.*;
import java.util.List;

@Entity
public class User {
	private Integer id;
	private Integer roleId;
	private String givenName;
	private String familyName;
	private String username;
	private String email;
	private String password;
	private String phone;
	private List<BookingAgent> bookingAgents;
	private List<BookingUser> bookingUsers;
	private UserRole userRole;

	@Id
	@Column(name = "id", nullable = false)
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Basic
	@Column(name = "role_id", nullable = false)
	public Integer getRoleId() {
		return roleId;
	}

	public void setRoleId(Integer roleId) {
		this.roleId = roleId;
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
	@Column(name = "username", nullable = false, length = 45)
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	@Basic
	@Column(name = "email", nullable = false, length = 255)
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Basic
	@Column(name = "password", nullable = false, length = 255)
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Basic
	@Column(name = "phone", nullable = false, length = 45)
	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		User user = (User) o;

		if (id != null ? !id.equals(user.id) : user.id != null) return false;
		if (roleId != null ? !roleId.equals(user.roleId) : user.roleId != null) return false;
		if (givenName != null ? !givenName.equals(user.givenName) : user.givenName != null) return false;
		if (familyName != null ? !familyName.equals(user.familyName) : user.familyName != null) return false;
		if (username != null ? !username.equals(user.username) : user.username != null) return false;
		if (email != null ? !email.equals(user.email) : user.email != null) return false;
		if (password != null ? !password.equals(user.password) : user.password != null) return false;
		if (phone != null ? !phone.equals(user.phone) : user.phone != null) return false;

		return true;
	}

	@Override
	public int hashCode() {
		int result = id != null ? id.hashCode() : 0;
		result = 31 * result + (roleId != null ? roleId.hashCode() : 0);
		result = 31 * result + (givenName != null ? givenName.hashCode() : 0);
		result = 31 * result + (familyName != null ? familyName.hashCode() : 0);
		result = 31 * result + (username != null ? username.hashCode() : 0);
		result = 31 * result + (email != null ? email.hashCode() : 0);
		result = 31 * result + (password != null ? password.hashCode() : 0);
		result = 31 * result + (phone != null ? phone.hashCode() : 0);
		return result;
	}

	@OneToMany(mappedBy = "user")
	public List<BookingAgent> getBookingAgents() {
		return bookingAgents;
	}

	public void setBookingAgents(List<BookingAgent> bookingAgents) {
		this.bookingAgents = bookingAgents;
	}

	@OneToMany(mappedBy = "user")
	public List<BookingUser> getBookingUsers() {
		return bookingUsers;
	}

	public void setBookingUsers(List<BookingUser> bookingUsers) {
		this.bookingUsers = bookingUsers;
	}

	@ManyToOne
	@JoinColumn(name = "role_id", referencedColumnName = "id", nullable = false)
	public UserRole getUserRole() {
		return userRole;
	}

	public void setUserRole(UserRole userRole) {
		this.userRole = userRole;
	}
}
