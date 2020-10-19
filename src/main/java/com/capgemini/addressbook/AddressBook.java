package com.capgemini.addressbook;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.TreeMap;
import java.util.function.Predicate;

public class AddressBook {
	/**
	 * UC6
	 */
	public Map<String, AddressBookMain> addressBooks = new TreeMap<String, AddressBookMain>();
	public Map<String, List<ContactPerson>> personsByState = new TreeMap<String, List<ContactPerson>>();
	public Map<String, List<ContactPerson>> personsByCity = new TreeMap<String, List<ContactPerson>>();

	public static void main(String[] args) {
		System.out.println("\n***** Welcome to Address Book Program *****\n");
		Scanner sc = new Scanner(System.in);
		AddressBook a = new AddressBook();

		while (true) {
			System.out.println("\n1. Add a new Address Book");
			System.out.println("\n2. Search a person in a city or state across all address books");
			System.out.println("\n3. Show names of Address Books");
			System.out.println("\n4. View persons by city or state");
			System.out.println("\n5. Show Count of persons by city or state");
			System.out.println("\n6. Sort By First Name");
			System.out.println("\n7. Exit");
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

			case 3:
				a.showAddressBooks();
				break;

			case 4:
				a.viewPersonByCityOrState(sc);
				break;

			case 5:
				System.out.println("Showing Count of Persons by City and State");
				a.countPerson();
				break;
			case 6:
				a.sort();
				break;
			case 7:
				break;

			default:
				System.out.println("Please enter correct option :");
				continue;
			}
			if (choice == 7)
				break;
		}
		System.out.println("\nThank You !!!");
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

	/**
	 * UC9
	 * 
	 * @param sc
	 */
	private void viewPersonByCityOrState(Scanner sc) {

		System.out.println("Enter city or state");
		String searchIn = sc.next();
		if (searchIn.equalsIgnoreCase("city")) {
			personsByCity = new TreeMap<String, List<ContactPerson>>();
			createMapForCity();
			personsByCity.forEach((k, v) -> {
				System.out.println(k);
				v.stream().forEach(n -> System.out.println(n));
			});
		} else if (searchIn.equalsIgnoreCase("state")) {
			personsByState = new TreeMap<String, List<ContactPerson>>();
			createMapForState();
			personsByState.forEach((k, v) -> {
				System.out.println(k);
				v.stream().forEach(n -> System.out.println(n));
			});
		} else {
			System.out.println("Wrong Input");
			return;
		}
	}

	/**
	 * UC10
	 */
	public void countPerson() {
		personsByCity = new TreeMap<String, List<ContactPerson>>();
		createMapForCity();
		System.out.println("Cities");
		personsByCity.forEach((k, v) -> {
			System.out.print(k);
			System.out.print("  " + v.stream().count() + "\n");
		});
		personsByState = new TreeMap<String, List<ContactPerson>>();
		createMapForState();
		System.out.println("\nStates");
		personsByState.forEach((k, v) -> {
			System.out.print(k);
			System.out.print("  " + v.stream().count() + "\n");
		});

	}

	private void createMapForCity() {
		addressBooks.forEach((k, v) -> {
			v.getContactPersonList().forEach(n -> {
				if (personsByCity.containsKey(n.getCity())) {
					List<ContactPerson> list = personsByCity.get(n.getCity());
					list.add(n);
				} else {
					List<ContactPerson> list = new ArrayList<ContactPerson>();
					list.add(n);
					personsByCity.put(n.getCity(), list);
				}
			});
		});
	}

	private void createMapForState() {
		addressBooks.forEach((k, v) -> {
			v.getContactPersonList().forEach(n -> {
				if (personsByState.containsKey(n.getState())) {
					List<ContactPerson> list = personsByState.get(n.getState());
					list.add(n);
				} else {
					List<ContactPerson> list = new ArrayList<ContactPerson>();
					list.add(n);
					personsByState.put(n.getState(), list);
				}
			});
		});
	}

	/**
	 * UC11
	 */
	public void sort() {
		List<ContactPerson> list = new ArrayList<ContactPerson>();
		addressBooks.values().forEach(n -> n.getContactPersonList().forEach(n1 -> list.add(n1)));
		Collections.sort(list, (ContactPerson c1, ContactPerson c2) -> c1.getFirstName().compareTo(c2.getFirstName()));
		list.forEach(n -> System.out.println(n));
	}
}
