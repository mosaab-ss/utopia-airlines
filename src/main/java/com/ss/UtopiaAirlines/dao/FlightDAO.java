package com.ss.UtopiaAirlines.dao;

import com.ss.UtopiaAirlines.entity.Airplane;
import com.ss.UtopiaAirlines.entity.AirplaneType;
import com.ss.UtopiaAirlines.entity.Airport;
import com.ss.UtopiaAirlines.entity.Flight;
import com.ss.UtopiaAirlines.entity.Route;
import com.ss.UtopiaAirlines.entity.User;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class FlightDAO extends BaseDAO<Flight> {
	
	public FlightDAO(Connection conn) {
		super(conn);
	}

	public Integer addFlight(Flight flight) throws ClassNotFoundException, SQLException {
		return save("INSERT INTO flight " +
				"(route_id, airplane_id, departure_time, arrival_time, reserved_seats, reserved_business, reserved_first, seat_price, business_price, first_price)" +
				" VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)", new Object[] {
				flight.getRouteId(),
				flight.getAirplaneId(),
				flight.getDepartureTime(),
				flight.getReservedSeats(),
				flight.getReservedBusiness(),
				flight.getReservedFirst(),
				flight.getSeatPrice(),
				flight.getBusinessPrice(),
				flight.getFirstPrice()
		}, true);
	}

	public void updateFlight(Flight flight) throws ClassNotFoundException, SQLException {
		save("UPDATE flight SET route_id = ?, airplane_id = ?, departure_time = ?, arrival_time = ?, reserved_seats = ?, reserved_business = ?, reserved_first = ?, seat_price = ?, business_price = ?, first_price = ? WHERE id = ?", new Object[] {
				flight.getRouteId(),
				flight.getAirplaneId(),
				flight.getDepartureTime(),
				flight.getArrivalTime(),
				flight.getReservedSeats(),
				flight.getReservedBusiness(),
				flight.getReservedFirst(),
				flight.getSeatPrice(),
				flight.getBusinessPrice(),
				flight.getFirstPrice(),
				flight.getId()
		});
	}

	public void deleteFlight(Flight flight) throws ClassNotFoundException, SQLException {
		save("DELETE FROM flight WHERE id = ?", new Object[] {
				flight.getId()
		});
	}

	public List<Flight> readAllFlights() throws ClassNotFoundException, SQLException {
		return read("SELECT * FROM flight", null);
	}

	public List<Flight> readAllFlights(int offset, int limit) throws ClassNotFoundException, SQLException {
		return read("SELECT * FROM flight ORDER BY id ASC LIMIT ?, ?", new Object[] {offset, limit});
	}

	public List<Flight> readFlightsById(int id) throws ClassNotFoundException, SQLException {
		return read("SELECT * FROM flight WHERE id = ?", new Object[] {
				id
		});
	}

	public List<Flight> readAllFlightsByUser(int offset, int limit, User user) throws ClassNotFoundException, SQLException {
		return read("SELECT * FROM flight " +
				"INNER JOIN booking_user AS bu " +
					"ON bu.user_id = ? " +
				"INNER JOIN flight_bookings AS fb " +
					"ON fb.booking_id = bu.booking_id " +
				"INNER JOIN booking " +
					"ON booking.id = fb.booking_id " +
				"WHERE flight.id = fb.flight_id AND booking.is_active = 1 " +
				"ORDER BY id ASC LIMIT ?, ?", new Object[] {
						user.getId(),
						offset,
						limit
		});
	}

	public List<Flight> readFlightById(int id) throws ClassNotFoundException, SQLException {
		return read("SELECT f.id, f.route_id, f.airplane_id, f.departure_time, f.arrival_time, f.reserved_seats, f.reserved_business, f.reserved_first, f.seat_price, f.business_price, f.first_price, " +
				"r.id AS rId, r.origin_id, r.destination_id, " +
				"oa.city AS origCity, da.city AS destCity, " +
				"a.id AS aId, a.type_id, " +
				"at.id AS atId, at.max_capacity, at.business_class, at.first_class " +
				"FROM flight AS f " +
				"INNER JOIN route AS r " +
				"ON f.route_id = r.id " +
				"INNER JOIN airport AS oa " +
				"ON r.origin_id = oa.iata_id " +
				"INNER JOIN airport AS da " +
				"ON r.destination_id = da.iata_id " +
				"INNER JOIN airplane AS a " +
				"ON f.airplane_id = a.id " +
				"INNER JOIN airplane_type as at " +
				"ON a.type_id = at.id " +
				"WHERE f.id = ?", new Object[] {
				id
		});
	}

	@Override
	public List<Flight> extractData(ResultSet resultSet) throws ClassNotFoundException, SQLException {
		List<Flight> flights = new ArrayList<>();

		while(resultSet.next()) {
			Flight f = new Flight();

			f.setId(resultSet.getInt("id"));
			f.setRouteId(resultSet.getInt("route_id"));
			f.setAirplaneId(resultSet.getInt("airplane_id"));
			f.setDepartureTime(resultSet.getTimestamp("departure_time"));
			f.setArrivalTime(resultSet.getTimestamp("arrival_time"));
			f.setReservedSeats(resultSet.getInt("reserved_seats"));
			f.setReservedBusiness(resultSet.getInt("reserved_business"));
			f.setReservedFirst(resultSet.getInt("reserved_first"));
			f.setSeatPrice(resultSet.getFloat("seat_price"));
			f.setBusinessPrice(resultSet.getFloat("business_price"));
			f.setFirstPrice(resultSet.getFloat("first_price"));

			try {
				resultSet.findColumn("rId");

				Route r = new Route();
				Airport oa = new Airport();
				Airport da = new Airport();
				Airplane a = new Airplane();
				AirplaneType at = new AirplaneType();

				at.setId(resultSet.getInt("atId"));
				at.setMaxCapacity(resultSet.getInt("max_capacity"));
				at.setBusinessClass(resultSet.getInt("business_class"));
				at.setFirstClass(resultSet.getInt("first_class"));

				oa.setIataId(resultSet.getString("origin_id"));
				oa.setCity(resultSet.getString("origCity"));

				da.setIataId(resultSet.getString("destination_id"));
				da.setCity(resultSet.getString("destCity"));

				r.setId(resultSet.getInt("rId"));
				r.setOriginAirport(oa);
				r.setDestAirport(da);
				r.setOriginId(oa.getIataId());
				r.setDestinationId(da.getIataId());

				a.setAirplaneType(at);

				a.setId(resultSet.getInt("aId"));
				a.setTypeId(resultSet.getInt("type_id"));

				f.setAirplane(a);
				f.setRoutes(Collections.singletonList(r));
			} catch (SQLException e) {
				// We didn't query for relational this time
			}

			flights.add(f);
		}

		return flights;
	}
}
