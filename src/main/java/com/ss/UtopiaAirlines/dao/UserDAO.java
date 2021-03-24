package com.ss.UtopiaAirlines.dao;

import com.ss.UtopiaAirlines.entity.Airplane;
import com.ss.UtopiaAirlines.entity.AirplaneType;
import com.ss.UtopiaAirlines.entity.Airport;
import com.ss.UtopiaAirlines.entity.Route;
import com.ss.UtopiaAirlines.entity.User;
import com.ss.UtopiaAirlines.entity.UserRole;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class UserDAO extends BaseDAO<User> {

	public UserDAO(Connection conn) {
		super(conn);
	}

	public Integer addUser(User user) throws ClassNotFoundException, SQLException {
		return save("INSERT INTO user (role_id, given_name, family_name, username, email, password, phone) VALUES (?, ?, ?, ?, ?, ?, ?)", new Object[] {
				user.getRoleId(),
				user.getGivenName(),
				user.getFamilyName(),
				user.getUsername(),
				user.getEmail(),
				user.getPassword(),
				user.getPhone()
		}, true);
	}

	public void updateUser(User user) throws ClassNotFoundException, SQLException {
		save("UPDATE user SET role_id = ?, give_name = ?, family_name = ?, username = ?, email = ?, password = ?, phone = ? WHERE id = ?", new Object[] {
				user.getRoleId(),
				user.getGivenName(),
				user.getFamilyName(),
				user.getUsername(),
				user.getEmail(),
				user.getPassword(),
				user.getPhone(),
				user.getId()
		});
	}

	public void deleteUser(User user) throws ClassNotFoundException, SQLException {
		save("DELETE FROM user WHERE id = ?", new Object[] {
				user.getId()
		});
	}

	public List<User> readAllUser() throws ClassNotFoundException, SQLException {
		return read("SELECT * FROM user", null);
	}

	public List<User> readAllUser(int offset, int limit) throws ClassNotFoundException, SQLException {
		return read("SELECT * FROM user ORDER BY id ASC LIMIT ?, ?", new Object[] {offset, limit});
	}

	public List<User> readAllUser(int offset, int limit, int roleId) throws ClassNotFoundException, SQLException {
		return read("SELECT * FROM user WHERE role_id = ? ORDER BY id ASC LIMIT ?, ?", new Object[] {roleId, offset, limit});
	}

	public List<User> readUserById(User user) throws ClassNotFoundException, SQLException {
		return read("SELECT * FROM user WHERE id = ?", new Object[] {
				user.getId()
		});
	}

	public List<User> readUserByUP(String username, String password) throws ClassNotFoundException, SQLException {
		return read("SELECT u.id, u.role_id, u.given_name, u.family_name, u.username, u.email, u.password, u.phone, ur.name FROM user AS u " +
				"INNER JOIN user_role AS ur " +
					"ON u.role_id = ur.id " +
				"WHERE username = ? AND password = ?", new Object[] {
				username, password
		});
	}

	@Override
	public List<User> extractData(ResultSet resultSet) throws ClassNotFoundException, SQLException {
		List<User> users = new ArrayList<>();

		while(resultSet.next()) {
			User u = new User();

			u.setId(resultSet.getInt("id"));
			u.setRoleId(resultSet.getInt("role_id"));
			u.setGivenName(resultSet.getString("given_name"));
			u.setFamilyName(resultSet.getString("family_name"));
			u.setUsername(resultSet.getString("username"));
			u.setEmail(resultSet.getString("email"));
			u.setPassword(resultSet.getString("password"));
			u.setPhone(resultSet.getString("phone"));

			if (doesColumnExist(resultSet, "name")) {
				UserRole ur = new UserRole();

				ur.setId(resultSet.getInt("role_id"));
				ur.setName(resultSet.getString("name"));

				u.setUserRole(ur);
			}

			users.add(u);
		}

		return users;
	}
}
