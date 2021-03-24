package com.ss.UtopiaAirlines.service;

import com.ss.UtopiaAirlines.entity.Flight;


public abstract class BaseService {

	public Integer[] getRemainingSeats(Flight flight) {
		if (flight.getAirplane() != null && flight.getAirplane().getAirplaneType() != null) {
			return new Integer[] {
					flight.getAirplane().getAirplaneType().getFirstClass() - flight.getReservedFirst(),
					flight.getAirplane().getAirplaneType().getBusinessClass() - flight.getReservedBusiness(),
					flight.getAirplane().getAirplaneType().getMaxCapacity() - flight.getReservedSeats()
			};
		} else {
			return null;
		}
	}
}
