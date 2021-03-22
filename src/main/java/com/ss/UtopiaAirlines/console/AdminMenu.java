package com.ss.UtopiaAirlines.console;

import com.ss.UtopiaAirlines.entity.User;
import com.ss.UtopiaAirlines.service.AdminService;

import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;

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

		User user = adminService.getUser(username, password);

		if (user == null || !"Administrator".equals(user.getUserRole().getName())) {
			System.out.println("Username or password wrong.");
		} else {
			getCRUDMenu();
		}
	}

	public void getCRUDMenu() {
		loop: while (true) {
			System.out.println("Welcome admin!");
			if ("0".equals(app.getStringChoice()))
				break loop;
		}
	}
}
