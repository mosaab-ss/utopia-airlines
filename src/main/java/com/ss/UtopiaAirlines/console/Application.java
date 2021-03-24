package com.ss.UtopiaAirlines.console;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Scanner;

public class Application {

	public Scanner scan = null;

	public Application() {
		this.scan = new Scanner(System.in);
	}

	public static void main(String[] args) {
		Application app = new Application();
		EmployeeMenu employeeMenu = new EmployeeMenu(app);
		AdminMenu adminMenu = new AdminMenu(app);
		TravellerMenu travellerMenu = new TravellerMenu(app);

		try {
			loop:
			while (true) {
				switch (app.getMain()) {
					case 1:
						employeeMenu.getMainMenu();
						break;
					case 2:
						adminMenu.getMainMenu();
						break;
					case 3:
						travellerMenu.getMainMenu();
						break;
					case 0:
						break loop;
					default:
						System.out.println("Please provide a valid choice.");
				}
			}
		} catch (Exception e) {
			System.out.println("Something went wrong, please contact admin.");
		}
	}

	private int getMain() {
		System.out.println("Welcome to the Utopia Airlines Management System. Which category of a user are you");
		System.out.printf("%s%n%s%n%s%n%s%n",
				"1) Employee",
				"2) Administrator",
				"3) Traveler",
				"0) Quit"
		);

		return getIntChoice();
	}

	public int getIntChoice() {
		int input = scan.nextInt();
		while (input < 0) input = scan.nextInt();
		return input;
	}

	public String getStringChoice() {
		String input = scan.nextLine();
		while (input.isEmpty()) input = scan.nextLine();
		return input;
	}

	public String shaHash(String input) throws NoSuchAlgorithmException {
		// getInstance() method is called with algorithm SHA-512
		MessageDigest md = MessageDigest.getInstance("SHA-512");

		// digest() method is called to calculate message digest of the input string returned as array of byte
		byte[] messageDigest = md.digest(input.getBytes());

		// Convert byte array into signum representation
		BigInteger no = new BigInteger(1, messageDigest);

		// Convert message digest into hex value
		String hashtext = no.toString(16);

		// Add preceding 0s to make it 32 bit
		while (hashtext.length() < 32) {
			hashtext = "0" + hashtext;
		}

		// return the HashText
		return hashtext;
	}
}
