package com.ss.UtopiaAirlines.dao;

import com.ss.UtopiaAirlines.entity.UserRole;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserRoleDAO extends BaseDAO<UserRole> {

	public UserRoleDAO(Connection conn) {
		super(conn);
	}

	public Integer addUserRole(UserRole userRole) throws ClassNotFoundException, SQLException {
		return save("INSERT INTO user_role (name) VALUES (?)", new Object[] {
				userRole.getName()
		}, true);
	}

	public void updateUserRole(UserRole userRole) throws ClassNotFoundException, SQLException {
		save("UPDATE user_role SET name = ? WHERE id = ?", new Object[] {
				userRole.getName(),
				userRole.getId()
		});
	}

	public void deleteUserRole(UserRole userRole) throws ClassNotFoundException, SQLException {
		save("DELETE FROM user_role WHERE id = ?", new Object[] {
				userRole.getId()
		});
	}

	public List<UserRole> readAllUserRole() throws ClassNotFoundException, SQLException {
		return read("SELECT * FROM user_role", null);
	}

	public List<UserRole> readAllUserRole(int offset, int limit) throws ClassNotFoundException, SQLException {
		return read("SELECT * FROM user_role ORDER BY id ASC LIMIT ?, ?", new Object[] {offset, limit});
	}

	public List<UserRole> readUserRoleById(UserRole userRole) throws ClassNotFoundException, SQLException {
		return read("SELECT * FROM user_role WHERE id = ?", new Object[] {
				userRole.getId()
		});
	}

	@Override
	public List<UserRole> extractData(ResultSet resultSet) throws ClassNotFoundException, SQLException {
		List<UserRole> userRoles = new ArrayList<>();

		while(resultSet.next()) {
			UserRole ur = new UserRole();

			ur.setId(resultSet.getInt("id"));
			ur.setName(resultSet.getString("name"));

			userRoles.add(ur);
		}

		return userRoles;
	}
}
