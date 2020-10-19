package com.capgemini.addressbook;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class AddressBookMain {

	private List<ContactPerson> contactPersonList;
	private Map<String, ContactPerson> contactPersonMap;

	public AddressBookMain() {
		contactPersonList = new ArrayList<ContactPerson>();
		contactPersonMap = new HashMap<String, ContactPerson>();
	}

	/**
	 * UC3
	 * 
	 * @param sc
	 */
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
		contactPersonMap.put(c.getFirstName(), c);
		System.out.println("Contact Added");
	}

	/**
	 * UC4
	 * 
	 * @param firstName
	 */
	public void editContactPerson(Scanner sc) {
		System.out.println("Enter First Name of the person to be edited :");
		ContactPerson cp = contactPersonMap.get(sc.next());
		System.out.println("Here is the Person Details to be edited " + cp);
		System.out.println("Enter new Address :");
		cp.setAddress(sc.next());
		System.out.println("Enter new City :");
		cp.setCity(sc.next());
		System.out.println("Enter new State :");
		cp.setState(sc.next());
		System.out.println("Enter new Zip :");
		cp.setZip(sc.next());
		System.out.println("Enter new Phone :");
		cp.setPhone(sc.next());
		System.out.println("Enter new e-mail :");
		cp.setEmail(sc.next());
		System.out.println("Updated Contact Details :");
		System.out.println(cp);
	}

	public List<ContactPerson> getContactPersonList() {
		return contactPersonList;
	}

	public void setContactPersonList(List<ContactPerson> contactPersonList) {
		this.contactPersonList = contactPersonList;
	}

}
