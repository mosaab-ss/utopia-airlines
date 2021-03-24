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

public class TravellerService extends BaseService implements FlightCRUD, TicketCRUD, UserCRUD {

	Util util = new Util();

	@Override
	public String addFlight(Flight flight) {
		return null;
	}

	@Override
	public String updateFlight(Flight flight) {
		return null;
	}

	@Override
	public String deleteFlight(int id) {
		return null;
	}

	@Override
	public List<Booking> readTickets(int offset) {
		return null;
	}

	@Override
	public Booking readTicketById(int id) {
		return null;
	}

	@Override
	public String deleteTicket(int id) {
		return null;
	}

	@Override
	public String updateTicket(Booking booking) {
		return null;
	}

	@Override
	public String addUser(User user) {
		return null;
	}

	@Override
	public List<User> readUsers(int offset) {
		return null;
	}

	@Override
	public List<User> readUsersByRole(int offset, int roleId) throws SQLException, ClassNotFoundException {
		return null;
	}

	@Override
	public User readUserById(int id) {
		return null;
	}

	@Override
	public String updateUser(User user) {
		return null;
	}

	@Override
	public String deleteUser(int id) {
		return null;
	}

	public String bookTicket(Flight flight, int seatClass, User user, Passenger passenger, BookingPayment bookingPayment) throws SQLException {
		Connection conn = null;

		try {
			conn = util.getConnection();

			Booking booking = new Booking();
			booking.setActive(true);
			booking.setConfirmationCode(UUID.randomUUID().toString().replace("-", ""));
			booking.setSeatClass(seatClass);

			BookingUser bookingUser = new BookingUser();
			FlightBookings flightBookings = new FlightBookings();

			BookingDAO bookingDAO = new BookingDAO(conn);
			BookingPaymentDAO bookingPaymentDAO = new BookingPaymentDAO(conn);
			PassengerDAO passengerDAO = new PassengerDAO(conn);
			BookingUserDAO bookingUserDAO = new BookingUserDAO(conn);
			FlightBookingsDAO flightBookingsDAO = new FlightBookingsDAO(conn);
			FlightDAO flightDAO = new FlightDAO(conn);

			Integer[] remainingSeats = getRemainingSeats(flight);

			// Update flight to reduce seats
			if (booking.getSeatClass() == 1 && remainingSeats != null && getRemainingSeats(flight)[1] > 0) {
				flight.setReservedFirst(flight.getReservedFirst() + 1);
			} else if (booking.getSeatClass() == 2 && remainingSeats != null && getRemainingSeats(flight)[2] > 0) {
				flight.setReservedBusiness(flight.getReservedBusiness() + 1);
			} else if (booking.getSeatClass() == 3 && remainingSeats != null && getRemainingSeats(flight)[3] > 0) {
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

			return null;
		} finally {
			if (conn != null) {
				conn.close();
			}
		}
	}


	public String cancelBooking(int id, User user) throws SQLException, ClassNotFoundException {
		Connection conn = null;

		conn = util.getConnection();

		FlightDAO flightDAO = new FlightDAO(conn);

		Flight flight = flightDAO.readFlightsById(id).get(0);

		if (conn != null) {
			conn.close();
		}

		if ("Ticket updated successfully!".equals(updateTicketStateByUser(user, flight, false))) {
			return "Flight cancelled successfully! Expect refund soon!";
		} else {
			return "Flight could not be cancelled.";
		}
	}
}
