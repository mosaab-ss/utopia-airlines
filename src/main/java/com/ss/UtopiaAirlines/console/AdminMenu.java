package com.ss.UtopiaAirlines.console;

import com.ss.UtopiaAirlines.entity.Booking;
import com.ss.UtopiaAirlines.entity.Flight;
import com.ss.UtopiaAirlines.entity.Route;
import com.ss.UtopiaAirlines.entity.User;
import com.ss.UtopiaAirlines.service.AdminService;

import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AdminMenu {
	private AdminService adminService = null;
	private Application app = null;

	public AdminMenu(Application app) {
		this.adminService = new AdminService();
		this.app = app;
	}

	public void getMainMenu() throws ClassNotFoundException, SQLException, NoSuchAlgorithmException {
		System.out.print("Username: ");
		String username = app.getStringChoice();
		System.out.print("Password: ");
		String password = app.shaHash(app.getStringChoice());

		User user = adminService.readUserByUP(username, password);

		if (user == null || user.getUserRole() != null && !"Administrator".equals(user.getUserRole().getName())) {
			System.out.println("Username or password wrong.");
		} else {
			getCRUDMenu();
		}
	}

	public void getCRUDMenu() throws SQLException, ClassNotFoundException {
		loop: while (true) {
			System.out.println("Which one you would like to manage: ");
			System.out.printf("%s%n%s%n%s%n%s%n%s%n%s%n%s%n%s%n",
					"1) Flights",
					"2) Seats",
					"3) Tickets and Passengers",
					"4) Airports",
					"5) Travellers",
					"6) Employees",
					"7) Override Trip cancellation",
					"0) Quit"
			);

			String choice = app.getStringChoice();

			String prompt = String.format("Would you like to do on %%s:%n%s%n%s%n%s%n%s%n%s%n",
					"1) Add",
					"2) List",
					"3) Update",
					"4) Delete",
					"0) Cancel"
			);

			switch (choice) {
				case "1":
					getFlightCRUDMenu(prompt);
					break;
				case "2":
					getSeatsCRUDMenu(prompt);
					break;
				case "3":
					getTicketsCRUDMenu(prompt);
					break;
				case "4":
					getAirportCRUDMenu(prompt);
					break;
				case "5":
					getTravellerCRUDMenu(prompt);
					break;
				case "6":
					getEmployeeCRUDMenu(prompt);
					break;
				case "7":
					getOverrideMenu(0);
					break;
				case "0":
					break loop;
			}
		}
	}

	//TODO: Finish up the next few menus using the Admin Service
	public void getFlightCRUDMenu(String prompt) {
		System.out.printf(prompt, "Flights");
	}

	public void getSeatsCRUDMenu(String prompt) {
		System.out.printf(prompt, "Seats");
	}

	public void getTicketsCRUDMenu(String prompt) {
		System.out.printf(prompt, "Tickets");
	}

	public void getAirportCRUDMenu(String prompt) {
		System.out.printf(prompt, "Airports");
	}

	public void getTravellerCRUDMenu(String prompt) {
		System.out.printf(prompt, "Travellers");
	}

	public void getEmployeeCRUDMenu(String prompt) {
		System.out.printf(prompt, "Employees");
	}

	public void getOverrideMenu(int offset) throws SQLException, ClassNotFoundException {
		System.out.println("Select user to over-ride cancellation for:");

		int pageOffset = offset;

		loop: while(true) {
			System.out.printf("%-3s) %-15s, %-15s, %-15s, %-30s, %-10s%n",
					"ID",
					"First Name",
					"Last Name",
					"Username",
					"Email",
					"Phone"
			);
			List<User> users = adminService.readUsersByRole(pageOffset, 3);
			List<String> ids = getUsersList(users);
			String choice = app.getStringChoice();
			switch (choice) {
				case "0":
					break loop;
				case "n":
					pageOffset = (users.size() < 10)? pageOffset : pageOffset + 10;
					break;
				case "p":
					pageOffset = (pageOffset > 10) ? pageOffset - 10 : 0;
				default:
					if (ids.contains(choice)) {
						getCancellationMenu(0, Integer.parseInt(choice));
					}
			}
		}
	}

	public List<String> getUsersList(List<User> users) {
		List<String> ids = new ArrayList<>();

		for (User user : users) {
			ids.add(user.getId().toString());

			System.out.printf("%-3s) %-15s, %-15s, %-15s, %-30s, %-10s%n",
					user.getId(),
					user.getGivenName(),
					user.getFamilyName(),
					user.getUsername(),
					user.getEmail(),
					user.getPhone()
			);
		}

		System.out.println("0) Quit to previous");
		System.out.println("n) Next page \tp) Previous page");

		return ids;
	}

	public void getCancellationMenu(int offset, int id) throws SQLException, ClassNotFoundException {
		int pageOffset = offset;
		loop: while(true) {
			System.out.printf("%-3s) %-15s, %-15s, %-15s%n",
					"ID",
					"Status",
					"Seat Class",
					"Confirmation Code"
			);
			List<Booking> bookings = adminService.readTicketsByUserId(offset, id);
			List<String> ids = getBookingList(bookings);
			String choice = app.getStringChoice();
			switch (choice) {
				case "0":
					break loop;
				case "n":
					pageOffset = (bookings.size() < 10)? pageOffset : pageOffset + 10;
					break;
				case "p":
					pageOffset = (pageOffset > 10) ? pageOffset - 10 : 0;
				default:
					if (ids.contains(choice)) {
						confirmLoop: while (true) {
							System.out.printf("Do you really want to over-ride cancellation?%n%s%n%s%n",
									"1) Yes",
									"2) No"
							);
							String confirmChoice = app.getStringChoice();
							if ("1".equals(confirmChoice)) {
								Booking booking = adminService.readTicketById(Integer.parseInt(choice));
								booking.setActive(true);
								System.out.println(adminService.updateTicket(booking));
								break confirmLoop;
							} else if ("2".equals(confirmChoice)){
								break confirmLoop;
							}
						}
					}
			}
		}
	}

	public List<String> getBookingList(List<Booking> bookings) {
		List<String> ids = new ArrayList<>();

		for (Booking booking : bookings) {
			ids.add(booking.getId().toString());

			System.out.printf("%-3s) %-15s, %-15s, %-15s%n",
					booking.getId(),
					(booking.getActive()) ? "Yes" : "No",
					booking.getSeatClass(),
					booking.getConfirmationCode()
			);
		}

		System.out.println("0) Quit to previous");
		System.out.println("n) Next page \tp) Previous page");

		return ids;
	}
}
