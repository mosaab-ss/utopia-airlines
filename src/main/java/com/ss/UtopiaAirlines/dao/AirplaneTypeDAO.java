package com.ss.UtopiaAirlines.dao;

import com.ss.UtopiaAirlines.entity.AirplaneType;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AirplaneTypeDAO extends BaseDAO<AirplaneType> {

	public AirplaneTypeDAO(Connection conn) {
		super(conn);
	}

	public Integer addAirplaneType(AirplaneType airplaneType) throws ClassNotFoundException, SQLException {
		return save("INSERT INTO airplane_type (max_capacity, business_class, first_class) VALUES (?, ?, ?)", new Object[] {
				airplaneType.getMaxCapacity(),
				airplaneType.getBusinessClass(),
				airplaneType.getFirstClass()
		}, true);
	}

	public void updateAirplaneType(AirplaneType airplaneType) throws ClassNotFoundException, SQLException {
		save("UPDATE airplane_type SET max_capacity = ?, business_class = ?, first_class = ? WHERE id = ?", new Object[] {
				airplaneType.getMaxCapacity(),
				airplaneType.getBusinessClass(),
				airplaneType.getFirstClass(),
				airplaneType.getId()
		});
	}

	public void deleteAirplaneType(AirplaneType airplaneType) throws ClassNotFoundException, SQLException {
		save("DELETE FROM airplane_type WHERE id = ?", new Object[] {
				airplaneType.getId()
		});
	}

	public List<AirplaneType> readAllAirplaneTypes() throws ClassNotFoundException, SQLException {
		return read("SELECT * FROM airplane_type", null);
	}

	public List<AirplaneType> readAllAirplaneTypes(int offset, int limit) throws ClassNotFoundException, SQLException {
		return read("SELECT * FROM airplane_type ORDER BY id ASC LIMIT ?, ?", new Object[] {offset, limit});
	}

	public List<AirplaneType> readAirplaneTypesById(AirplaneType airplaneType) throws ClassNotFoundException, SQLException {
		return read("SELECT * FROM airplane_type WHERE id = ?", new Object[] {
				airplaneType.getId()
		});
	}

	@Override
	public List<AirplaneType> extractData(ResultSet resultSet) throws ClassNotFoundException, SQLException {
		List<AirplaneType> airplaneTypes = new ArrayList<>();

		while(resultSet.next()) {
			AirplaneType at = new AirplaneType();

			at.setId(resultSet.getInt("id"));
			at.setMaxCapacity(resultSet.getInt("max_capacity"));
			at.setBusinessClass(resultSet.getInt("business_class"));
			at.setFirstClass(resultSet.getInt("first_class"));

			airplaneTypes.add(at);
		}

		return airplaneTypes;
	}
}
