package com.capgemini.addressbook;

import java.util.Scanner;

public class AddressBook {

	public static void main(String[] args) {
		System.out.println("***** Welcom to Address Book Program *****");
		Scanner sc = new Scanner(System.in);
		AddressBookMain addressBookMain = new AddressBookMain();
		addressBookMain.addContactPerson(sc);
	}
}
