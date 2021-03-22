package com.ss.UtopiaAirlines.entity;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "user_role", schema = "utopia", catalog = "")
public class UserRole {
	private Integer id;
	private String name;
	private List<User> users;

	@Id
	@Column(name = "id", nullable = false)
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Basic
	@Column(name = "name", nullable = false, length = 45)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		UserRole userRole = (UserRole) o;

		if (id != null ? !id.equals(userRole.id) : userRole.id != null) return false;
		if (name != null ? !name.equals(userRole.name) : userRole.name != null) return false;

		return true;
	}

	@Override
	public int hashCode() {
		int result = id != null ? id.hashCode() : 0;
		result = 31 * result + (name != null ? name.hashCode() : 0);
		return result;
	}

	@OneToMany(mappedBy = "userRole")
	public List<User> getUsers() {
		return users;
	}

	public void setUsers(List<User> users) {
		this.users = users;
	}
}
