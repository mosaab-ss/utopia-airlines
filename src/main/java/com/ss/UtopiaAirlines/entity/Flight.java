package com.ss.UtopiaAirlines.entity;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.List;

@Entity
public class Flight {
	private Integer id;
	private Integer routeId;
	private Integer airplaneId;
	private Timestamp departureTime;
	private Timestamp arrivalTime;
	private Integer reservedSeats;
	private Integer reservedBusiness;
	private Integer reservedFirst;
	private Float seatPrice;
	private Float businessPrice;
	private Float firstPrice;
	private List<Route> routes;
	private Airplane airplane;
	private List<FlightBookings> flightBookings;

	@Id
	@Column(name = "id", nullable = false)
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Basic
	@Column(name = "route_id", nullable = false)
	public Integer getRouteId() {
		return routeId;
	}

	public void setRouteId(Integer routeId) {
		this.routeId = routeId;
	}

	@Basic
	@Column(name = "airplane_id", nullable = false)
	public Integer getAirplaneId() {
		return airplaneId;
	}

	public void setAirplaneId(Integer airplaneId) {
		this.airplaneId = airplaneId;
	}

	@Basic
	@Column(name = "departure_time", nullable = false)
	public Timestamp getDepartureTime() {
		return departureTime;
	}

	public void setDepartureTime(Timestamp departureTime) {
		this.departureTime = departureTime;
	}

	@Basic
	@Column(name = "arrival_time", nullable = false)
	public Timestamp getArrivalTime() {
		return arrivalTime;
	}

	public void setArrivalTime(Timestamp arrivalTime) {
		this.arrivalTime = arrivalTime;
	}

	@Basic
	@Column(name = "reserved_seats", nullable = false)
	public Integer getReservedSeats() {
		return reservedSeats;
	}

	public void setReservedSeats(Integer reservedSeats) {
		this.reservedSeats = reservedSeats;
	}

	@Basic
	@Column(name = "reserved_business", nullable = false)
	public Integer getReservedBusiness() {
		return reservedBusiness;
	}

	public void setReservedBusiness(Integer reservedBusiness) {
		this.reservedBusiness = reservedBusiness;
	}

	@Basic
	@Column(name = "reserved_first", nullable = false)
	public Integer getReservedFirst() {
		return reservedFirst;
	}

	public void setReservedFirst(Integer reservedFirst) {
		this.reservedFirst = reservedFirst;
	}

	@Basic
	@Column(name = "seat_price", nullable = false, precision = 0)
	public Float getSeatPrice() {
		return seatPrice;
	}

	public void setSeatPrice(Float seatPrice) {
		this.seatPrice = seatPrice;
	}

	@Basic
	@Column(name = "business_price", nullable = false, precision = 0)
	public Float getBusinessPrice() {
		return businessPrice;
	}

	public void setBusinessPrice(Float businessPrice) {
		this.businessPrice = businessPrice;
	}

	@Basic
	@Column(name = "first_price", nullable = false, precision = 0)
	public Float getFirstPrice() {
		return firstPrice;
	}

	public void setFirstPrice(Float firstPrice) {
		this.firstPrice = firstPrice;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		Flight flight = (Flight) o;

		if (id != null ? !id.equals(flight.id) : flight.id != null) return false;
		if (routeId != null ? !routeId.equals(flight.routeId) : flight.routeId != null) return false;
		if (airplaneId != null ? !airplaneId.equals(flight.airplaneId) : flight.airplaneId != null) return false;
		if (departureTime != null ? !departureTime.equals(flight.departureTime) : flight.departureTime != null)
			return false;
		if (arrivalTime != null ? !arrivalTime.equals(flight.arrivalTime) : flight.arrivalTime != null)
			return false;
		if (reservedSeats != null ? !reservedSeats.equals(flight.reservedSeats) : flight.reservedSeats != null)
			return false;
		if (reservedBusiness != null ? !reservedBusiness.equals(flight.reservedBusiness) : flight.reservedBusiness != null)
			return false;
		if (reservedFirst != null ? !reservedFirst.equals(flight.reservedFirst) : flight.reservedFirst != null)
			return false;
		if (seatPrice != null ? !seatPrice.equals(flight.seatPrice) : flight.seatPrice != null) return false;
		if (businessPrice != null ? !businessPrice.equals(flight.businessPrice) : flight.businessPrice != null)
			return false;
		if (firstPrice != null ? !firstPrice.equals(flight.firstPrice) : flight.firstPrice != null) return false;

		return true;
	}

	@Override
	public int hashCode() {
		int result = id != null ? id.hashCode() : 0;
		result = 31 * result + (routeId != null ? routeId.hashCode() : 0);
		result = 31 * result + (airplaneId != null ? airplaneId.hashCode() : 0);
		result = 31 * result + (departureTime != null ? departureTime.hashCode() : 0);
		result = 31 * result + (arrivalTime != null ? arrivalTime.hashCode() : 0);
		result = 31 * result + (reservedSeats != null ? reservedSeats.hashCode() : 0);
		result = 31 * result + (reservedBusiness != null ? reservedBusiness.hashCode() : 0);
		result = 31 * result + (reservedFirst != null ? reservedFirst.hashCode() : 0);
		result = 31 * result + (seatPrice != null ? seatPrice.hashCode() : 0);
		result = 31 * result + (businessPrice != null ? businessPrice.hashCode() : 0);
		result = 31 * result + (firstPrice != null ? firstPrice.hashCode() : 0);
		return result;
	}

	@OneToMany(mappedBy = "flight")
	public List<Route> getRoutes() {
		return routes;
	}

	public void setRoutes(List<Route> routes) {
		this.routes = routes;
	}

	@ManyToOne
	@JoinColumn(name = "airplane_id", referencedColumnName = "id", nullable = false)
	public Airplane getAirplane() {
		return airplane;
	}

	public void setAirplane(Airplane airplane) {
		this.airplane = airplane;
	}

	@OneToMany(mappedBy = "flight")
	public List<FlightBookings> getFlightBookings() {
		return flightBookings;
	}

	public void setFlightBookings(List<FlightBookings> flightBookings) {
		this.flightBookings = flightBookings;
	}
}
