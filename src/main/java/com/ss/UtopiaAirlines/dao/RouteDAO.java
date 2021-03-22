package com.ss.UtopiaAirlines.dao;

import com.ss.UtopiaAirlines.entity.AirplaneType;
import com.ss.UtopiaAirlines.entity.Airport;
import com.ss.UtopiaAirlines.entity.Route;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class RouteDAO extends BaseDAO<Route>{

	public RouteDAO(Connection conn) {
		super(conn);
	}

	public Integer addRoute(Route route) throws ClassNotFoundException, SQLException {
		return save("INSERT INTO route (origin_id, destination_id) VALUES (?, ?)", new Object[] {
				route.getOriginId(),
				route.getDestinationId()
		}, true);
	}

	public void updateRoute(Route route) throws ClassNotFoundException, SQLException {
		save("UPDATE route SET origin_id = ?, destination_id = ? WHERE id = ?", new Object[] {
				route.getOriginId(),
				route.getDestinationId(),
				route.getId()
		});
	}

	public void deleteRoute(Route route) throws ClassNotFoundException, SQLException {
		save("DELETE FROM route WHERE id = ?", new Object[] {
				route.getId()
		});
	}

	public List<Route> readAllRoutes() throws ClassNotFoundException, SQLException {
		return read("SELECT * FROM route", null);
	}

	public List<Route> readAllRoutes(int offset, int limit) throws ClassNotFoundException, SQLException {
		return read("SELECT * FROM route ORDER BY id ASC LIMIT ?, ?", new Object[] {offset, limit});
	}

	public List<Route> readRoutesById(int id) throws ClassNotFoundException, SQLException {
		return read("SELECT * FROM route WHERE id = ?", new Object[] {
				id
		});
	}

	public List<Route> readRouteById(int id) throws ClassNotFoundException, SQLException {
		return read("SELECT r.id, r.origin_id, r.destination_id, oa.city AS origCity, da.city AS destCity FROM route AS r " +
				"INNER JOIN airport AS oa " +
					"ON r.origin_id = oa.iata_id " +
				"INNER JOIN airport AS da " +
					"ON r.destination_id = da.iata_id " +
				"WHERE r.id = ?", new Object[] {
				id
		});
	}

	@Override
	public List<Route> extractData(ResultSet resultSet) throws ClassNotFoundException, SQLException {
		List<Route> routes = new ArrayList<>();

		while(resultSet.next()) {
			Route r = new Route();

			r.setId(resultSet.getInt("id"));
			r.setOriginId(resultSet.getString("origin_id"));
			r.setDestinationId(resultSet.getString("destination_id"));

			try {
				resultSet.findColumn("origCity");

				Airport oa = new Airport();
				Airport da = new Airport();

				oa.setIataId(resultSet.getString("origin_id"));
				oa.setCity(resultSet.getString("origCity"));

				da.setIataId(resultSet.getString("destination_id"));
				da.setCity(resultSet.getString("destCity"));

				r.setOriginAirport(oa);
				r.setDestAirport(da);
			} catch (SQLException e) {
				// We didn't query for relational this time
			}

			routes.add(r);
		}

		return routes;
	}
}
