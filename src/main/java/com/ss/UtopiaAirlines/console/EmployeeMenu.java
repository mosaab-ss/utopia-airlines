package com.ss.UtopiaAirlines.console;

import com.ss.UtopiaAirlines.entity.Flight;
import com.ss.UtopiaAirlines.entity.Route;
import com.ss.UtopiaAirlines.entity.User;
import com.ss.UtopiaAirlines.service.EmployeeService;

import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class EmployeeMenu {

	private EmployeeService employeeService = null;
	private Application app = null;

	public EmployeeMenu(Application app) {
		this.employeeService = new EmployeeService();
		this.app = app;
	}

	public void getMainMenu() throws ClassNotFoundException, SQLException, NoSuchAlgorithmException {
		System.out.print("Username: ");
		String username = app.getStringChoice();
		System.out.print("Password: ");
		String password = app.shaHash(app.getStringChoice());

		User user = employeeService.getUser(username, password);

		if (user == null || !"Employee".equals(user.getUserRole().getName())) {
			System.out.println("Username or password wrong.");
		} else {
			getFlightListMenu(0);
		}
	}

	public void getFlightListMenu(int offset) throws ClassNotFoundException, SQLException {
		int pageOffset = offset;

		loop: while(true) {
			List<Flight> flights = employeeService.getFlights(pageOffset);
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
						getFlightMenu(Integer.parseInt(choice));
					}
			}
		}
	}

	public void getFlightMenu(int id) throws ClassNotFoundException, SQLException {
		loop: while(true) {
			Flight flight = employeeService.getFlight(id);

			System.out.println("1) View more details about the flight");
			System.out.println("2) Update the details of the flight");
			System.out.println("3) Add seats to the flight");

			System.out.println("0) Quit to previous");


			String choice = app.getStringChoice();
			switch (choice) {
				case "0":
					break loop;
				case "1":
					getDetailedFlight(flight);
					break;
				case "2":
					getUpdateFlight(flight);
					break;
				case "3":
					getUpdateFlightSeat(flight);
			}
		}
	}

	public void getDetailedFlight(Flight flight) throws ClassNotFoundException, SQLException {
		loop: while(true) {
			System.out.printf("You have chosen to view the flight with Flight ID: %s and Departure Airport %s, %s and Arrival Airport: %s, %s%n",
					flight.getId(),
					flight.getRoutes().get(0).getOriginAirport().getIataId(),
					flight.getRoutes().get(0).getOriginAirport().getCity(),
					flight.getRoutes().get(0).getDestAirport().getIataId(),
					flight.getRoutes().get(0).getDestAirport().getCity()
			);

			System.out.printf("Departure Airport: %s, %s | Arrival Airport: %s, %s%n",
					flight.getRoutes().get(0).getOriginAirport().getIataId(),
					flight.getRoutes().get(0).getOriginAirport().getCity(),
					flight.getRoutes().get(0).getDestAirport().getIataId(),
					flight.getRoutes().get(0).getDestAirport().getCity()
			);

			System.out.printf("Departure Date: %s | Departure Time: %s%n",
					flight.getDepartureTime().toLocalDateTime().toLocalDate(),
					flight.getDepartureTime().toLocalDateTime().toLocalTime()
			);

			System.out.printf("Arrival Date: %s | Arrival Time: %s%n",
					flight.getArrivalTime().toLocalDateTime().toLocalDate(),
					flight.getArrivalTime().toLocalDateTime().toLocalTime()
			);

			System.out.println("Available seats by class");
			System.out.printf("First -> %s%nBusiness -> %s%nEconomy -> %s%n",
					flight.getAirplane().getAirplaneType().getFirstClass() - flight.getReservedFirst(),
					flight.getAirplane().getAirplaneType().getBusinessClass() - flight.getReservedBusiness(),
					flight.getAirplane().getAirplaneType().getMaxCapacity() - flight.getReservedSeats()
			);

			System.out.println("0) Quit to previous");


			String choice = app.getStringChoice();
			if ("0".equals(choice)) {
				break loop;
			}
		}
	}

	public void getUpdateFlight(Flight flight) throws ClassNotFoundException, SQLException {
		System.out.printf("You have chosen to update the flight with Flight ID: %s and Flight Origin: %s, %s and Flight Destination: %s, %s%n",
				flight.getId(),
				flight.getRoutes().get(0).getOriginAirport().getIataId(),
				flight.getRoutes().get(0).getOriginAirport().getCity(),
				flight.getRoutes().get(0).getDestAirport().getIataId(),
				flight.getRoutes().get(0).getDestAirport().getCity()
		);

		System.out.println("Enter 'quit' at any prompt to cancel operation.");

		String changesChoice = "";

		Flight updatedFlight = new Flight();
		Route newRoute = new Route();

		newRoute.setId(flight.getRouteId());
		newRoute.setOriginId(flight.getRoutes().get(0).getOriginId());
		newRoute.setDestinationId(flight.getRoutes().get(0).getDestinationId());

		updatedFlight.setId(flight.getId());
		updatedFlight.setRoutes(Collections.singletonList(newRoute));
		updatedFlight.setRouteId(flight.getRouteId());
		updatedFlight.setAirplaneId(flight.getAirplaneId());
		updatedFlight.setDepartureTime(flight.getDepartureTime());
		updatedFlight.setArrivalTime(flight.getArrivalTime());
		updatedFlight.setReservedSeats(flight.getReservedSeats());
		updatedFlight.setReservedBusiness(flight.getReservedBusiness());
		updatedFlight.setReservedFirst(flight.getReservedFirst());
		updatedFlight.setSeatPrice(flight.getSeatPrice());
		updatedFlight.setBusinessPrice(flight.getBusinessPrice());
		updatedFlight.setFirstPrice(flight.getFirstPrice());


		System.out.println("Please enter new Origin Airport or enter N/A for no change:");
		changesChoice = app.getStringChoice();
		if ("quit".equals(changesChoice)) {
			System.out.println("Cancelling update, no modifications.");
			return;
		} else if (!"N/A".equals(changesChoice) && changesChoice.length() == 3) {
			// Changes
			updatedFlight.getRoutes().get(0).setOriginId(changesChoice);
		}

		System.out.println("Please enter new Destination Airport or enter N/A for no change:");
		changesChoice = app.getStringChoice();
		if ("quit".equals(changesChoice)) {
			System.out.println("Cancelling update, no modifications.");
			return;
		} else if (!"N/A".equals(changesChoice) && changesChoice.length() == 3) {
			// Changes
			updatedFlight.getRoutes().get(0).setDestinationId(changesChoice);
		}

		String date = "";
		System.out.println("Please enter new Departure Date (YYYY-MM-DD) or enter N/A for no change:");
		changesChoice = app.getStringChoice();
		if ("quit".equals(changesChoice)) {
			System.out.println("Cancelling update, no modifications.");
			return;
		} else if (!"N/A".equals(changesChoice)) {
			// Changes
			date = changesChoice;

			System.out.println("Please enter new Departure Time (HH:MM:SS) or enter N/A for no change:");
			changesChoice = app.getStringChoice();
			if ("quit".equals(changesChoice)) {
				System.out.println("Cancelling update, no modifications.");
				return;
			} else if (!"N/A".equals(changesChoice)) {
				// Changes
				date += " " + changesChoice;
				updatedFlight.setDepartureTime(Timestamp.valueOf(date));
			}
		}

		System.out.println("Please enter new Arrival Date (YYYY-MM-DD) or enter N/A for no change:");
		changesChoice = app.getStringChoice();
		if ("quit".equals(changesChoice)) {
			System.out.println("Cancelling update, no modifications.");
			return;
		} else if (!"N/A".equals(changesChoice)) {
			// Changes
			date = changesChoice;

			System.out.println("Please enter new Arrival Time (HH:MM:SS) or enter N/A for no change:");
			changesChoice = app.getStringChoice();
			if ("quit".equals(changesChoice)) {
				System.out.println("Cancelling update, no modifications.");
				return;
			} else if (!"N/A".equals(changesChoice)) {
				// Changes
				date += " " + changesChoice;
				updatedFlight.setArrivalTime(Timestamp.valueOf(date));
			}
		}


		System.out.println(employeeService.updateFlight(flight, updatedFlight));
	}

	public void getUpdateFlightSeat(Flight flight) throws ClassNotFoundException, SQLException {
		loop: while (true) {
			String changesChoice = "";

			System.out.println("Pick the seat class you want to add seats of, to your flight:");
			System.out.printf("1) First%n2) Business%n3) Economy%n4) Quit and cancel");
			changesChoice = app.getStringChoice();

			switch (changesChoice) {
				case "1":
					System.out.printf("Existing number of seats: %d%nEnter new number of seats: %n", flight.getReservedFirst());
					System.out.println(employeeService.updateSeating(1, app.getIntChoice(), flight));
					break;
				case "2":
					System.out.printf("Existing number of seats: %d%nEnter new number of seats: %n", flight.getReservedBusiness());
					System.out.println(employeeService.updateSeating(2, app.getIntChoice(), flight));
					break;
				case "3":
					System.out.printf("Existing number of seats: %d%nEnter new number of seats: %n", flight.getReservedSeats());
					System.out.println(employeeService.updateSeating(3, app.getIntChoice(), flight));
					break;
				case "4":
					break loop;
			}
		}
	}
}
