package com.capgemini.addressbook;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.TreeMap;
import java.util.function.Consumer;
import java.util.function.Predicate;

public class AddressBook {
	/**
	 * UC6
	 */
	public Map<String, AddressBookMain> addressBooks = new TreeMap<String, AddressBookMain>();

	public static void main(String[] args) {
		System.out.println("\n***** Welcome to Address Book Program *****\n");
		Scanner sc = new Scanner(System.in);
		AddressBook a = new AddressBook();

		while (true) {
			System.out.println("\n1. Add a new Address Book");
			System.out.println("\n2. Search a person in a city or state across all address books");
			System.out.println("\n3. Exit");
			System.out.println("\nEnter your choice");
			int choice = sc.nextInt();

			switch (choice) {
			case 1:
				while (true) {
					System.out.println("Enter name of the Address Book");
					String name = sc.next();
					if (a.addressBooks.containsKey(name)) {
						System.out.println("\nAddress Book already exists !!!\n");
						continue;
					} else {
						AddressBookMain addressBookMain = new AddressBookMain();
						a.addressBooks.put(name, addressBookMain);
						System.out.println("\n In Address Book : " + name);
						addressBookMain.maintainAddressBook(sc);
						break;
					}
				}
				break;

			case 2:
				a.searchPersonInCityOrState(sc);
				break;

			default:
				break;
			}

			if (choice == 3)
				break;
			else
				System.out.println("\nEnter option");
		}
		a.showAddressBooks();
	}

	public void showAddressBooks() {

		System.out.println("\nList of Address Books Added: \n");
		addressBooks.forEach((k, v) -> System.out.println(k + " " + v.getContactPersonList() + "\n"));
	}

	/**
	 * UC8
	 * 
	 * @param searchIn
	 */

	public void searchPersonInCityOrState(Scanner sc) {

		List<ContactPerson> list = new ArrayList<ContactPerson>();
		System.out.println("Enter Name of the person to search :");
		String searchName = sc.next();
		System.out.println("Search in [city] or [state]");
		String searchIn = sc.next();
		if (searchIn.equalsIgnoreCase("city")) {
			System.out.println("Enter the City Name :");
			String cityName = sc.next();
			Predicate<ContactPerson> search = n -> n.getFirstName().equals(searchName) & n.getCity().equals(cityName)
					? true
					: false;
			addressBooks.forEach((k, v) -> {
				v.getContactPersonList().stream().filter(search).forEach(n -> list.add(n));
			});
		}

		else if (searchIn.equalsIgnoreCase("state")) {
			System.out.println("Enter the State Name :");
			String stateName = sc.next();
			Predicate<ContactPerson> search = n -> n.getFirstName().equals(searchName) & n.getState().equals(stateName)
					? true
					: false;
			addressBooks.forEach((k, v) -> {
				v.getContactPersonList().stream().filter(search).forEach(n -> list.add(n));
			});
		} else {
			System.out.println("Invalid input !!");
			return;
		}
		if (list.size() == 0)
			System.out.println("No Persons Found !!");
		else
			list.forEach(System.out::println);
	}
}
