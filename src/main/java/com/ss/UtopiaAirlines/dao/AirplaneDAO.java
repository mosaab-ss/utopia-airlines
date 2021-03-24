package com.ss.UtopiaAirlines.dao;

import com.ss.UtopiaAirlines.entity.Airplane;
import com.ss.UtopiaAirlines.entity.AirplaneType;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AirplaneDAO extends BaseDAO<Airplane> {
	
	public AirplaneDAO(Connection conn) {
		super(conn);
	}

	public Integer addAirplane(Airplane airplane) throws ClassNotFoundException, SQLException {
		return save("INSERT INTO airplane (type_id) VALUES (?)", new Object[] {
				airplane.getTypeId()
		}, true);
	}

	public void updateAirplane(Airplane airplane) throws ClassNotFoundException, SQLException {
		save("UPDATE airplane SET type_id = ? WHERE id = ?", new Object[] {
				airplane.getTypeId()
		});
	}

	public void deleteAirplane(Airplane airplane) throws ClassNotFoundException, SQLException {
		save("DELETE FROM airplane WHERE id = ?", new Object[] {
				airplane.getId()
		});
	}

	public List<Airplane> readAllAirplanes() throws ClassNotFoundException, SQLException {
		return read("SELECT * FROM airplane", null);
	}

	public List<Airplane> readAllAirplanes(int offset, int limit) throws ClassNotFoundException, SQLException {
		return read("SELECT * FROM airplane ORDER BY id ASC LIMIT ?, ?", new Object[] {offset, limit});
	}

	public List<Airplane> readAirplanesById(int id) throws ClassNotFoundException, SQLException {
		return read("SELECT * FROM airplane WHERE id = ?", new Object[] {
				id
		});
	}

	public List<Airplane> getAirplaneById(int id) throws ClassNotFoundException, SQLException {
		return read("SELECT a.id, a.type_id, at.max_capacity, at.business_class, at.first_class FROM airplane AS a " +
				"INNER JOIN airplane_type AS at " +
					"ON a.type_id = at.id " +
				"WHERE a.id = ?", new Object[] {
				id
		});
	}

	@Override
	public List<Airplane> extractData(ResultSet resultSet) throws ClassNotFoundException, SQLException {
		List<Airplane> airplanes = new ArrayList<>();

		while(resultSet.next()) {
			Airplane a = new Airplane();

			a.setId(resultSet.getInt("id"));
			a.setTypeId(resultSet.getInt("type_id"));

			if (doesColumnExist(resultSet, "max_capacity")) {
				AirplaneType at = new AirplaneType();

				at.setId(resultSet.getInt("type_id"));
				at.setMaxCapacity(resultSet.getInt("max_capacity"));
				at.setBusinessClass(resultSet.getInt("business_class"));
				at.setFirstClass(resultSet.getInt("first_class"));

				a.setAirplaneType(at);
			}

			airplanes.add(a);
		}

		return airplanes;
	}
}
