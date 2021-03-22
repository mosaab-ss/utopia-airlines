package com.ss.UtopiaAirlines.dao;

import com.ss.UtopiaAirlines.entity.Passenger;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PassengerDAO extends BaseDAO<Passenger> {

	public PassengerDAO(Connection conn) {
		super(conn);
	}

	public Integer addPassenger(Passenger passenger) throws ClassNotFoundException, SQLException {
		return save("INSERT INTO passenger (booking_id, given_name, family_name, dob, gender, address) VALUES (?, ?, ?, ?, ?, ?)", new Object[] {
				passenger.getBookingId(),
				passenger.getGivenName(),
				passenger.getFamilyName(),
				passenger.getDob(),
				passenger.getGender(),
				passenger.getAddress()
		}, true);
	}

	public void updatePassenger(Passenger passenger) throws ClassNotFoundException, SQLException {
		save("UPDATE passenger SET booking_id = ?, given_name = ?, family_name = ?, dob = ?, gender = ?, address = ? WHERE id = ?", new Object[] {
				passenger.getBookingId(),
				passenger.getGivenName(),
				passenger.getFamilyName(),
				passenger.getDob(),
				passenger.getGender(),
				passenger.getAddress(),
				passenger.getId()
		});
	}

	public void deletePassenger(Passenger passenger) throws ClassNotFoundException, SQLException {
		save("DELETE FROM passenger WHERE id = ?", new Object[] {
				passenger.getId()
		});
	}

	public List<Passenger> readAllPassengers() throws ClassNotFoundException, SQLException {
		return read("SELECT * FROM passenger", null);
	}

	public List<Passenger> readAllPassengers(int offset, int limit) throws ClassNotFoundException, SQLException {
		return read("SELECT * FROM passenger ORDER BY id ASC LIMIT ?, ?", new Object[] {offset, limit});
	}

	public List<Passenger> readPassengersById(Passenger passenger) throws ClassNotFoundException, SQLException {
		return read("SELECT * FROM passenger WHERE id = ?", new Object[] {
				passenger.getId()
		});
	}

	@Override
	public List<Passenger> extractData(ResultSet resultSet) throws ClassNotFoundException, SQLException {
		List<Passenger> passengers = new ArrayList<>();

		while(resultSet.next()) {
			Passenger p = new Passenger();

			p.setId(resultSet.getInt("id"));
			p.setBookingId(resultSet.getInt("booking_id"));
			p.setGivenName(resultSet.getString("given_name"));
			p.setFamilyName(resultSet.getString("family_name"));
			p.setDob(resultSet.getDate("dob"));
			p.setGender(resultSet.getString("gender"));
			p.setAddress(resultSet.getString("address"));

			passengers.add(p);
		}

		return passengers;
	}
}
