package com.ss.UtopiaAirlines.service;

import com.ss.UtopiaAirlines.dao.BookingDAO;
import com.ss.UtopiaAirlines.dao.BookingPaymentDAO;
import com.ss.UtopiaAirlines.dao.BookingUserDAO;
import com.ss.UtopiaAirlines.dao.FlightBookingsDAO;
import com.ss.UtopiaAirlines.dao.FlightDAO;
import com.ss.UtopiaAirlines.dao.PassengerDAO;
import com.ss.UtopiaAirlines.dao.RouteDAO;
import com.ss.UtopiaAirlines.entity.Booking;
import com.ss.UtopiaAirlines.entity.BookingPayment;
import com.ss.UtopiaAirlines.entity.BookingUser;
import com.ss.UtopiaAirlines.entity.Flight;
import com.ss.UtopiaAirlines.entity.FlightBookings;
import com.ss.UtopiaAirlines.entity.Passenger;
import com.ss.UtopiaAirlines.entity.User;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.UUID;

public class TravellerService extends EmployeeService {

	Util util = new Util();

	public String bookTicket(Flight flight, int seatClass, User user, Passenger passenger, BookingPayment bookingPayment) throws SQLException {
		Connection conn = null;

		try {
			conn = util.getConnection();

			Booking booking = new Booking();
			booking.setActive(true);
			booking.setConfirmationCode(UUID.randomUUID().toString().replace("-", ""));

			BookingUser bookingUser = new BookingUser();
			FlightBookings flightBookings = new FlightBookings();

			BookingDAO bookingDAO = new BookingDAO(conn);
			BookingPaymentDAO bookingPaymentDAO = new BookingPaymentDAO(conn);
			PassengerDAO passengerDAO = new PassengerDAO(conn);
			BookingUserDAO bookingUserDAO = new BookingUserDAO(conn);
			FlightBookingsDAO flightBookingsDAO = new FlightBookingsDAO(conn);
			FlightDAO flightDAO = new FlightDAO(conn);

			// Update flight to reduce seats
			if (seatClass == 1 && (flight.getAirplane().getAirplaneType().getFirstClass() - flight.getReservedFirst()) > 0) {
				flight.setReservedFirst(flight.getReservedFirst() + 1);
			} else if (seatClass == 2 && (flight.getAirplane().getAirplaneType().getBusinessClass() - flight.getReservedBusiness()) > 0) {
				flight.setReservedBusiness(flight.getReservedBusiness() + 1);
			} else if (seatClass == 3 && (flight.getAirplane().getAirplaneType().getMaxCapacity() - flight.getReservedSeats()) > 0) {
				flight.setReservedSeats(flight.getReservedSeats() + 1);
			} else {
				conn.rollback();
				return "Flight is full!";
			}

			// Add booking
			int bPK = bookingDAO.addBooking(booking);

			// Add booking payment
			bookingPayment.setBookingId(bPK);
			bookingPayment.setRefunded(false);
			bookingPaymentDAO.addBookingPayment(bookingPayment);

			// Add passenger
			passenger.setBookingId(bPK);
			int passengerPK = passengerDAO.addPassenger(passenger);

			// Add booking user
			bookingUser.setBookingId(bPK);
			bookingUser.setUserId(user.getId());
			bookingUserDAO.addBookingUser(bookingUser);

			// Add flight bookings
			flightBookings.setBookingId(bPK);
			flightBookings.setFlightId(flight.getId());
			flightBookingsDAO.addFlightBookings(flightBookings);

			// update flight seats
			flightDAO.updateFlight(flight);

			conn.commit();

			return "Flight booked successfully! Confirmation code: "+ booking.getConfirmationCode();
		} catch (SQLException | ClassNotFoundException e) {
			if (conn != null) {
				conn.rollback();
			}
			e.printStackTrace();
			return "Flight could not be booked.";
		} finally {
			if (conn != null) {
				conn.close();
			}
		}
	}

	public List<Flight> getFlightsForUser(User user, int offset) throws SQLException {
		Connection conn = null;

		try {
			conn = util.getConnection();

			FlightDAO flightDao = new FlightDAO(conn);
			RouteDAO routeDao = new RouteDAO(conn);

			List<Flight> flights = flightDao.readAllFlightsByUser(offset, 10, user);

			for (Flight flight : flights) {
				flight.setRoutes(routeDao.readRouteById(flight.getRouteId()));
			}

			conn.commit();

			return flights;
		} catch (SQLException | ClassNotFoundException e) {
			if (conn != null) {
				conn.rollback();
			}
			e.printStackTrace();
			return null;
		} finally {
			if (conn != null) {
				conn.close();
			}
		}
	}

	public String cancelBooking(int id, User user) throws SQLException {
		Connection conn = null;

		try {
			conn = util.getConnection();

			BookingDAO bookingDAO = new BookingDAO(conn);
			bookingDAO.updateBookingByUser(user, false);

			conn.commit();

			return "Flight cancelled successfully! Expect refund soon!";
		} catch (SQLException | ClassNotFoundException e) {
			if (conn != null) {
				conn.rollback();
			}
			e.printStackTrace();
			return "Flight could not be cancelled.";
		} finally {
			if (conn != null) {
				conn.close();
			}
		}
	}

}
