package com.ss.UtopiaAirlines.console;

import com.ss.UtopiaAirlines.entity.BookingPayment;
import com.ss.UtopiaAirlines.entity.Flight;
import com.ss.UtopiaAirlines.entity.Passenger;
import com.ss.UtopiaAirlines.entity.Route;
import com.ss.UtopiaAirlines.entity.User;
import com.ss.UtopiaAirlines.service.TravellerService;

import java.security.NoSuchAlgorithmException;
import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TravellerMenu extends EmployeeMenu {

	private TravellerService travellerService = null;
	private Application app = null;
	private User user = null;

	public TravellerMenu(Application app) {
		super(app);
		this.travellerService = new TravellerService();
		this.app = app;
	}

	public void getMainMenu() throws ClassNotFoundException, SQLException, NoSuchAlgorithmException {
		System.out.print("Username: ");
		String username = app.getStringChoice();
		System.out.print("Password: ");
		String password = app.shaHash(app.getStringChoice());

		User user = travellerService.getUser(username, password);

		if (user == null || !"Traveller".equals(user.getUserRole().getName())) {
			System.out.println("Username or password wrong.");
		} else {
			this.user = user;
			getTravellerMain();
		}
	}

	public void getTravellerMain() throws SQLException, ClassNotFoundException {
		loop: while(true) {
			System.out.println("1) Book a ticket");
			System.out.println("2) Cancel an upcoming trip");
			System.out.println("0) Quit to previous");


			String choice = app.getStringChoice();
			switch (choice) {
				case "0":
					break loop;
				case "1":
					getBookTicketMenu(0);
					break;
				case "2":
					getCancelTripMenu(0);
			}
		}
	}

	public void getBookTicketMenu(int offset) throws SQLException, ClassNotFoundException {
		int pageOffset = offset;

		loop: while(true) {
			List<Flight> flights = travellerService.getFlights(pageOffset);
			List<String> ids = new ArrayList<>();

			for (Flight flight : flights) {
				ids.add(flight.getId().toString());

				Route route = flight.getRoutes().get(0);

				System.out.printf("%s) %s, %-15s -> %s, %s%n",
						flight.getId(),
						route.getOriginAirport().getIataId(),
						route.getOriginAirport().getCity(),
						route.getDestAirport().getIataId(),
						route.getDestAirport().getCity()
				);
			}

			System.out.println("0) Quit to previous");
			System.out.println("n) Next page \tp) Previous page");


			String choice = app.getStringChoice();
			switch (choice) {
				case "0":
					break loop;
				case "n":
					pageOffset = (flights.size() < 10)? pageOffset : pageOffset + 10;
					break;
				case "p":
					pageOffset = (pageOffset > 10) ? pageOffset - 10 : 0;
				default:
					if (ids.contains(choice)) {
						getSeatMenu(Integer.parseInt(choice));
					}
			}
		}
	}

	public void getSeatMenu(int id) throws SQLException, ClassNotFoundException {
		loop: while (true) {
			Flight flight = travellerService.getFlight(id);

			;

			System.out.println("1) View FLight Details");
			System.out.printf("2) First %s%n", (flight.getAirplane().getAirplaneType().getFirstClass() - flight.getReservedFirst() > 0) ? "" : "(Unavailable)");
			System.out.printf("3) Business %s%n", (flight.getAirplane().getAirplaneType().getBusinessClass() - flight.getReservedBusiness() > 0) ? "" : "(Unavailable)");
			System.out.printf("4) Economy %s%n", (flight.getAirplane().getAirplaneType().getMaxCapacity() - flight.getReservedSeats() > 0) ? "" : "(Unavailable)");
			System.out.println("0) Quit to cancel operation");

			String choice = app.getStringChoice();

			if ("0".equals(choice)) {
				break loop;
			} else if ("1".equals(choice)) {
				getDetailedFlight(flight);
			} else if ("2".equals(choice) && flight.getAirplane().getAirplaneType().getFirstClass() - flight.getReservedFirst() > 0) {
				System.out.println(reserveSeat(flight, 1));
			} else if ("3".equals(choice) && flight.getAirplane().getAirplaneType().getBusinessClass() - flight.getReservedBusiness() > 0) {
				System.out.println(reserveSeat(flight, 2));
			} else if ("4".equals(choice) && flight.getAirplane().getAirplaneType().getMaxCapacity() - flight.getReservedSeats() > 0) {
				System.out.println(reserveSeat(flight, 3));
			}
		}
	}

	public String reserveSeat(Flight flight, int seatClass) throws SQLException {
		loop: while (true) {
			String ticketConfirmFormat = "Ticket price $%s. Are you sure you want to book this ticket?%n1) Yes%n2) Cancel";
			switch (seatClass) {
				case 1:
					System.out.printf(ticketConfirmFormat, flight.getFirstPrice());
					if ("1".equals(app.getStringChoice()))
						return passengerDetailsMenu(flight, seatClass);
					else if ("2".equals(app.getStringChoice()))
						return "Cancelled";
				case 2:
					System.out.printf(ticketConfirmFormat, flight.getBusinessPrice());
					if ("1".equals(app.getStringChoice()))
						return passengerDetailsMenu(flight, seatClass);
					else if ("2".equals(app.getStringChoice()))
						return "Cancelled";
				case 3:
					System.out.printf(ticketConfirmFormat, flight.getSeatPrice());
					if ("1".equals(app.getStringChoice()))
						return passengerDetailsMenu(flight, seatClass);
					else if ("2".equals(app.getStringChoice()))
						return "Cancelled";
			}
		}
	}

	public String passengerDetailsMenu(Flight flight, int seatClass) throws SQLException {
		Passenger passenger = new Passenger();
		String choice = "";

		System.out.println("Enter First Name: ");
		choice = app.getStringChoice();
		passenger.setGivenName(choice);

		System.out.println("Enter Last Name: ");
		choice = app.getStringChoice();
		passenger.setFamilyName(choice);

		loop: while (true) {
			System.out.println("Enter Date of Birth (YYYY-MM-DD): ");
			choice = app.getStringChoice();
			if (choice.matches("^\\d{4}-\\d{2}-\\d{2}"))
				break loop;
		}
		passenger.setDob(Date.valueOf(choice));

		System.out.println("Enter Gender: ");
		choice = app.getStringChoice();
		passenger.setGender(choice);

		System.out.println("Enter Address: ");
		choice = app.getStringChoice();
		passenger.setAddress(choice);

		System.out.println("Enter Credit Card Number: ");
		choice = app.getStringChoice();
		BookingPayment bookingPayment = new BookingPayment();
		bookingPayment.setStripeId(choice);

		return travellerService.bookTicket(flight, seatClass, this.user, passenger, bookingPayment);
	}

	public void getCancelTripMenu(int offset) throws SQLException, ClassNotFoundException {
		int pageOffset = offset;

		loop: while(true) {
			List<Flight> flights = travellerService.getFlightsForUser(this.user, pageOffset);
			List<String> ids = new ArrayList<>();

			for (Flight flight : flights) {
				ids.add(flight.getId().toString());

				Route route = flight.getRoutes().get(0);

				System.out.printf("%s) %s, %-15s -> %s, %s%n",
						flight.getId(),
						route.getOriginAirport().getIataId(),
						route.getOriginAirport().getCity(),
						route.getDestAirport().getIataId(),
						route.getDestAirport().getCity()
				);
			}

			System.out.println("0) Quit to previous");
			System.out.println("n) Next page \tp) Previous page");


			String choice = app.getStringChoice();
			switch (choice) {
				case "0":
					break loop;
				case "n":
					pageOffset = (flights.size() < 10)? pageOffset : pageOffset + 10;
					break;
				case "p":
					pageOffset = (pageOffset > 10) ? pageOffset - 10 : 0;
				default:
					if (ids.contains(choice)) {
						System.out.printf("Are you sure you want to cancel this flight?%n1) Yes%n2) No");

						switch (app.getStringChoice()) {
							case "1":
								System.out.println(travellerService.cancelBooking(Integer.parseInt(choice), this.user));
								break;
							case "2":
								break loop;
						}
					}
			}
		}
	}

}
