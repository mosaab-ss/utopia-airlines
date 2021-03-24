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

	public void deleteFlight(int id) throws ClassNotFoundException, SQLException {
		save("DELETE FROM flight WHERE id = ?", new Object[] {
				id
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
		return read("SELECT f.id, f.route_id, f.airplane_id, f.departure_time, f.arrival_time, f.reserved_seats, f.reserved_business, f.reserved_first, f.seat_price, f.business_price, f.first_price, " +
				"b.booking_id AS booking_id, b.is_active, b.confirmation_code, b.seat_class, " +
				"bu.user_id " +
				"FROM flight AS f " +
				"INNER JOIN booking_user AS bu " +
					"ON bu.user_id = ? " +
				"INNER JOIN flight_bookings AS fb " +
					"ON fb.booking_id = bu.booking_id " +
				"INNER JOIN booking AS b " +
					"ON b.id = fb.booking_id " +
				"WHERE f.id = fb.flight_id AND booking.is_active = 1 " +
				"ORDER BY flight.id ASC LIMIT ?, ?", new Object[] {
						user.getId(),
						offset,
						limit
		});
	}

	public List<Flight> readFlightObjectById(int id) throws ClassNotFoundException, SQLException {
		return read("SELECT f.id, f.route_id, f.airplane_id, f.departure_time, f.arrival_time, f.reserved_seats, f.reserved_business, f.reserved_first, f.seat_price, f.business_price, f.first_price, " +
				"r.origin_id, r.destination_id, " +
				"oa.city AS origCity, da.city AS destCity, " +
				"a.type_id, " +
				"at.max_capacity, at.business_class, at.first_class " +
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

			if (doesColumnExist(resultSet, "origin_id")) {
				Route r = new Route();

				r.setId(resultSet.getInt("route_id"));
				r.setOriginId(resultSet.getString("origin_id"));
				r.setDestinationId(resultSet.getString("destination_id"));

				if (doesColumnExist(resultSet, "origCity")) {
					Airport oa = new Airport();

					oa.setIataId(resultSet.getString("origin_id"));
					oa.setCity(resultSet.getString("origCity"));

					r.setOriginAirport(oa);
				}

				if (doesColumnExist(resultSet, "destCity")) {
					Airport da = new Airport();

					da.setIataId(resultSet.getString("destination_id"));
					da.setCity(resultSet.getString("destCity"));

					r.setDestAirport(da);
				}

				f.setRoutes(Collections.singletonList(r));
			}

			if (doesColumnExist(resultSet, "type_id")) {
				Airplane a = new Airplane();

				a.setId(resultSet.getInt("airplane_id"));
				a.setTypeId(resultSet.getInt("type_id"));

				if (doesColumnExist(resultSet, "max_capacity")) {
					AirplaneType at = new AirplaneType();

					at.setId(resultSet.getInt("type_id"));
					at.setMaxCapacity(resultSet.getInt("max_capacity"));
					at.setBusinessClass(resultSet.getInt("business_class"));
					at.setFirstClass(resultSet.getInt("first_class"));

					a.setAirplaneType(at);
				}

				f.setAirplane(a);
			}

			flights.add(f);
		}

		return flights;
	}
}
