package com.ss.UtopiaAirlines.console;

import com.ss.UtopiaAirlines.entity.Flight;
import com.ss.UtopiaAirlines.entity.Route;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BaseMenu {

	Application app = null;

	BaseMenu(Application app) {
		this.app = app;
	}

	public void getDetailedFlight(Flight flight) {
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
	}

	public List<String> getFlightList(List<Flight> flights) {
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

		return ids;
	}
}
