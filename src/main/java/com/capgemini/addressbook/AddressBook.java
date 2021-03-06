package com.capgemini.addressbook;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.TreeMap;
import java.util.function.Predicate;

public class AddressBook {

	public Map<String, AddressBookMain> addressBooks = new TreeMap<String, AddressBookMain>();
	public Map<String, List<ContactPerson>> personsByState = new TreeMap<String, List<ContactPerson>>();
	public Map<String, List<ContactPerson>> personsByCity = new TreeMap<String, List<ContactPerson>>();

	/**
	 * UC1
	 * 
	 * @param args
	 */

	public AddressBook() {
		this.addressBooks = new AddressBookCSVFileIO().readData();
	}

	public enum FileType {
		TEXT, CSV, JSON
	}

	public static void main(String[] args) {
		System.out.println("\n***** Welcome to Address Book Program *****\n");
		Scanner sc = new Scanner(System.in);
		AddressBook a = new AddressBook();

		while (true) {
			System.out.println("\n1. Add a new Address Book");
			System.out.println("\n2. Edit Existing Address Book");
			System.out.println("\n3. Search a person in a city or state across all address books");
			System.out.println("\n4. Show names of Address Books");
			System.out.println("\n5. View persons by city or state");
			System.out.println("\n6. Show Count of persons by city or state");
			System.out.println("\n7. Sort");
			System.out.println("\n8. Write to a file");
			System.out.println("\n9. Read from a file");
			System.out.println("\n10. Exit");
			System.out.println("\nEnter your choice");
			int choice = sc.nextInt();

			switch (choice) {
			case 1:
				System.out.println("Enter name of the Address Book");
				String name = sc.next();
				if (a.addressBooks.containsKey(name)) {
					System.out.println("\nAddress Book already exists !!!\n");
				} else {
					AddressBookMain addressBookMain = new AddressBookMain();
					a.addressBooks.put(name, addressBookMain);
					System.out.println("\n In Address Book : " + name);
					addressBookMain.maintainAddressBook(sc);
				}
				break;

			case 2:
				System.out.println("Enter name of the Address Book");
				String nameOfBook = sc.next();
				if (a.addressBooks.containsKey(nameOfBook)) {
					AddressBookMain addressBookMain = a.addressBooks.get(nameOfBook);
					System.out.println("\n In Address Book : " + nameOfBook);
					addressBookMain.maintainAddressBook(sc);
				} else {
					System.err.println("No such Address Book Availble !!");
				}
				break;

			case 3:
				a.searchPersonInCityOrState(sc);
				break;

			case 4:
				a.showAddressBooks();
				break;

			case 5:
				a.viewPersonByCityOrState(sc);
				break;

			case 6:
				a.countPerson();
				break;
			case 7:
				a.sort(sc);
				break;
			case 8:
				new AddressBookFileIO().writeAddressBooks(a.addressBooks, FileType.JSON);
				break;
			case 9:
				Map<String, AddressBookMain> mapFromFile = new AddressBookJsonFileIO().readData();
				System.out.println(mapFromFile);
				break;
			case 10:
				break;

			default:
				System.err.println("Please enter correct option :");
				continue;
			}
			if (choice == 10)
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
		if (checkIfEmpty())
			return;
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
			System.err.println("No Persons Found !!");
		else
			list.forEach(System.out::println);
	}

	/**
	 * UC9
	 * 
	 * @param sc
	 */
	private void viewPersonByCityOrState(Scanner sc) {
		if (checkIfEmpty())
			return;
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
			System.err.println("Wrong Input");
			return;
		}
	}

	/**
	 * UC10
	 */
	public void countPerson() {
		if (checkIfEmpty())
			return;
		System.out.println("Showing Count of Persons by City and State");
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
	public void sort(Scanner sc) {
		if (checkIfEmpty())
			return;
		while (true) {
			System.out.println("Sort by : [Name] [City] [State] [Zip] \nEnter Option");
			String sortOption = sc.next();
			if (sortOption.equalsIgnoreCase("name")) {
				sortByName();
				break;
			}
			if (sortOption.equalsIgnoreCase("state")) {
				sortByState();
				break;
			}
			if (sortOption.equalsIgnoreCase("city")) {
				sortByCity();
				break;
			}
			if (sortOption.equalsIgnoreCase("zip")) {
				sortByZip();
				break;
			} else
				System.err.println("Enter correct option");
		}
	}

	/**
	 * UC12
	 */
	public void sortByName() {
		List<ContactPerson> list = new ArrayList<ContactPerson>();
		addressBooks.values().forEach(n -> n.getContactPersonList().forEach(n1 -> list.add(n1)));
		Collections.sort(list, (ContactPerson c1, ContactPerson c2) -> c1.getFirstName().compareTo(c2.getFirstName()));
		list.forEach(n -> System.out.println(n));
	}

	public void sortByCity() {
		List<ContactPerson> list = new ArrayList<ContactPerson>();
		addressBooks.values().forEach(n -> n.getContactPersonList().forEach(n1 -> list.add(n1)));
		Collections.sort(list, (ContactPerson c1, ContactPerson c2) -> c1.getCity().compareTo(c2.getCity()));
		list.forEach(n -> System.out.println(n));
	}

	public void sortByState() {
		List<ContactPerson> list = new ArrayList<ContactPerson>();
		addressBooks.values().forEach(n -> n.getContactPersonList().forEach(n1 -> list.add(n1)));
		Collections.sort(list, (ContactPerson c1, ContactPerson c2) -> c1.getState().compareTo(c2.getState()));
		list.forEach(n -> System.out.println(n));
	}

	public void sortByZip() {
		List<ContactPerson> list = new ArrayList<ContactPerson>();
		addressBooks.values().forEach(n -> n.getContactPersonList().forEach(n1 -> list.add(n1)));
		Collections.sort(list, (ContactPerson c1, ContactPerson c2) -> c1.getZip().compareTo(c2.getZip()));
		list.forEach(n -> System.out.println(n));
	}

	public boolean checkIfEmpty() {
		if (addressBooks.entrySet().isEmpty()) {
			System.err.println("No Address Book added yet !!");
			return true;
		}
		return false;
	}

	@Override
	public String toString() {
		return "AddressBook addressBooks=" + addressBooks;
	}
}
