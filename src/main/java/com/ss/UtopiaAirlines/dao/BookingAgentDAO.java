package com.ss.UtopiaAirlines.dao;

import com.ss.UtopiaAirlines.entity.BookingAgent;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BookingAgentDAO extends BaseDAO<BookingAgent> {

	public BookingAgentDAO(Connection conn) {
		super(conn);
	}

	public Integer addBookingAgent(BookingAgent bookingAgent) throws ClassNotFoundException, SQLException {
		return save("INSERT INTO booking_agent (booking_id, agent_id) VALUES (?, ?)", new Object[] {
				bookingAgent.getBookingId(),
				bookingAgent.getAgentId()
		}, true);
	}

	public void updateBookingAgent(BookingAgent bookingAgent) throws ClassNotFoundException, SQLException {
		save("UPDATE booking_agent SET agent_id = ? WHERE booking_id = ?", new Object[] {
				bookingAgent.getAgentId(),
				bookingAgent.getBookingId()
		});
	}

	public void deleteBookingAgent(BookingAgent bookingAgent) throws ClassNotFoundException, SQLException {
		save("DELETE FROM booking_agent WHERE booking_id = ?", new Object[] {
				bookingAgent.getBookingId()
		});
	}

	public List<BookingAgent> readAllBookingAgents() throws ClassNotFoundException, SQLException {
		return read("SELECT * FROM booking_agent", null);
	}

	public List<BookingAgent> readAllBookingAgents(int offset, int limit) throws ClassNotFoundException, SQLException {
		return read("SELECT * FROM booking_agent ORDER BY booking_id ASC LIMIT ?, ?", new Object[] {offset, limit});
	}

	public List<BookingAgent> readBookingAgentsById(BookingAgent bookingAgent) throws ClassNotFoundException, SQLException {
		return read("SELECT * FROM booking_agent WHERE booking_id = ?", new Object[] {
				bookingAgent.getBookingId()
		});
	}

	@Override
	public List<BookingAgent> extractData(ResultSet resultSet) throws ClassNotFoundException, SQLException {
		List<BookingAgent> bookingAgents = new ArrayList<>();

		while(resultSet.next()) {
			BookingAgent ba = new BookingAgent();

			ba.setBookingId(resultSet.getInt("booking_id"));
			ba.setAgentId(resultSet.getInt("agent_id"));

			bookingAgents.add(ba);
		}

		return bookingAgents;
	}
}
