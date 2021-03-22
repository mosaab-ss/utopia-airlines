package com.ss.UtopiaAirlines.dao;

import com.ss.UtopiaAirlines.entity.Airport;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AirportDAO extends BaseDAO<Airport> {

	public AirportDAO(Connection conn) {
		super(conn);
	}

	public Integer addAirport(Airport airport) throws ClassNotFoundException, SQLException {
		return save("INSERT INTO airport VALUES (?, ?)", new Object[] {
				airport.getIataId(),
				airport.getCity()
		}, true);
	}

	public void updateAirport(Airport airport) throws ClassNotFoundException, SQLException {
		save("UPDATE airport SET city = ? WHERE iata_id = ?", new Object[] {
				airport.getCity(),
				airport.getIataId()
		});
	}

	public void deleteAirport(Airport airport) throws ClassNotFoundException, SQLException {
		save("DELETE FROM airport WHERE iata_id = ?", new Object[] {
				airport.getIataId()
		});
	}

	public List<Airport> readAllAirports() throws ClassNotFoundException, SQLException {
		return read("SELECT * FROM airport", null);
	}

	public List<Airport> readAllAirports(int offset, int limit) throws ClassNotFoundException, SQLException {
		return read("SELECT * FROM airport ORDER BY iata_id ASC LIMIT ?, ?", new Object[] {offset, limit});
	}

	public List<Airport> readAirportsByCode(Airport airport) throws ClassNotFoundException, SQLException {
		return read("SELECT * FROM airport WHERE iata_id = ?", new Object[] {
				airport.getIataId()
		});
	}

	@Override
	public List<Airport> extractData(ResultSet resultSet) throws ClassNotFoundException, SQLException {
		List<Airport> airports = new ArrayList<>();

		while(resultSet.next()) {
			Airport a = new Airport();

			a.setIataId(resultSet.getString("iata_id"));
			a.setCity(resultSet.getString("city"));

			airports.add(a);
		}

		return airports;
	}
}
