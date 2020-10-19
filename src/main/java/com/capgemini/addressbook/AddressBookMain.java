package com.capgemini.addressbook;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class AddressBookMain {

	private List<ContactPerson> contactPersonList;

	public AddressBookMain() {
		contactPersonList = new ArrayList<ContactPerson>();
	}

	public void addContactPerson(Scanner sc) {
		System.out.println("Enter Contact details of the person\n");
		String[] details = new String[8];
		System.out.println("Enter First Name :");
		details[0] = sc.next();
		System.out.println("Enter Last Name :");
		details[1] = sc.next();
		System.out.println("Enter Address :");
		details[2] = sc.next();
		System.out.println("Enter City :");
		details[3] = sc.next();
		System.out.println("Enter State :");
		details[4] = sc.next();
		System.out.println("Enter Zip :");
		details[5] = sc.next();
		System.out.println("Enter Phone :");
		details[6] = sc.next();
		System.out.println("Enter e-mail :");
		details[7] = sc.next();
		ContactPerson c = new ContactPerson(details[0], details[1], details[2], details[3], details[4], details[5],
				details[6], details[7]);
		contactPersonList.add(c);
	}

	public List<ContactPerson> getContactPersonList() {
		return contactPersonList;
	}

	public void setContactPersonList(List<ContactPerson> contactPersonList) {
		this.contactPersonList = contactPersonList;
	}

}
