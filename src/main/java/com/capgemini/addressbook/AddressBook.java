package com.capgemini.addressbook;

import java.util.Map;
import java.util.Scanner;
import java.util.TreeMap;

public class AddressBook {
	public Map<String, AddressBookMain> addressBooks = new TreeMap<String, AddressBookMain>();

	public static void main(String[] args) {
		System.out.println("\n***** Welcome to Address Book Program *****\n");
		Scanner sc = new Scanner(System.in);
		AddressBook a = new AddressBook();

		while (true) {
			AddressBookMain addressBookMain = new AddressBookMain();
			System.out.println("Enter name of the Address Book");
			String name = sc.next();
			if (a.addressBooks.containsKey(name))
				System.out.println("\nAddress Book already exists !!!\n");
			else {
				a.addressBooks.put(name, addressBookMain);
				System.out.println("\nEnter Details for " + name);
				addressBookMain.maintainAddressBook(addressBookMain, sc);
			}
			System.out.println("Want to add more Address Books (y/n)");
			String response = sc.next();
			if (response.equals("y"))
				continue;
			else
				break;
		}
		a.showAddressBooks();
	}

	public void showAddressBooks() {

		System.out.println("\nList of Address Books Added: \n");
		addressBooks.forEach((k, v) -> System.out.println(k + " " + v.getContactPersonList() + "\n"));
	}
}
