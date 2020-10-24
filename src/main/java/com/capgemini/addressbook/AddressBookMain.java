package com.capgemini.addressbook;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.function.Predicate;

public class AddressBookMain {

	private List<ContactPerson> contactPersonList;
	private Map<String, ContactPerson> addressBookByName;

	public AddressBookMain() {
		contactPersonList = new ArrayList<ContactPerson>();
		addressBookByName = new HashMap<String, ContactPerson>();
	}

	public Map<String, ContactPerson> getAddressBookByName() {
		return addressBookByName;
	}

	public List<ContactPerson> getContactPersonList() {
		return contactPersonList;
	}

	public void setContactPersonList(List<ContactPerson> contactPersonList) {
		this.contactPersonList = contactPersonList;
		createMap();
	}

	/**
	 * UC6
	 * 
	 * @param a
	 */
	public void maintainAddressBook(Scanner sc) {

		while (true) {
			System.out.println("\n1. Add Contact Details");
			System.out.println("\n2. Edit Contact Details");
			System.out.println("\n3. Delete Contact Details");
			System.out.println("\n4. Exit");
			System.out.println("\nEnter your choice");
			int choice = sc.nextInt();

			switch (choice) {
			case 1:
				addContactPerson(sc);
				break;
			case 2:
				editContactPerson(sc);
				break;
			case 3:
				deleteContactPerson(sc);
				break;
			case 4:
				break;
			default:
				System.out.println("Please enter correct option :");
				continue;
			}
			if (choice == 4)
				break;
		}
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

		if (checkForDuplicateName(c)) {
			System.out.println("Person already exist in the Address Book !!  Please try with a different name");
			return;
		}
		contactPersonList.add(c);
		addressBookByName.put(c.getFirstName(), c);
		System.out.println("Contact Added");
	}

	/**
	 * UC7
	 * 
	 * @param person
	 * @return
	 */
	public boolean checkForDuplicateName(ContactPerson person) {
		Predicate<ContactPerson> compareName = n -> n.equals(person);
		boolean value = contactPersonList.stream().anyMatch(compareName);
		return value;
	}

	/**
	 * UC4
	 * 
	 * @param firstName
	 */
	public void editContactPerson(Scanner sc) {
		if (checkListIsEmpty())
			return;
		System.out.println("Enter First Name of the person to be edited :");
		String name = sc.next();
		if (!(addressBookByName.containsKey(name))) {
			System.out.println("No such person available !!");
			return;
		}
		ContactPerson cp = addressBookByName.get(name);
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

	/**
	 * UC5
	 * 
	 * @param name
	 */
	public void deleteContactPerson(Scanner sc) {
		if (checkListIsEmpty())
			return;
		System.out.println("Enter the first name to delete the contact details");
		String name = sc.next();
		if (!(addressBookByName.containsKey(name))) {
			System.out.println("No such person available !!");
			return;
		}
		int index = 0;
		for (ContactPerson c : contactPersonList) {
			if (c.getFirstName().equals(name)) {
				contactPersonList.remove(index);
				break;
			}
			index++;
		}
		addressBookByName.remove(name);
		System.out.println("Contact Deleted !!!");
	}

	public boolean checkListIsEmpty() {
		if (contactPersonList.isEmpty()) {
			System.out.println("Address Book is empty !!");
			return true;
		}
		return false;
	}

	public void createMap() {
		if (contactPersonList.size() != 0) {
			for (ContactPerson c : contactPersonList) {
				addressBookByName.put(c.getFirstName(), c);
			}
		}
	}

	@Override
	public String toString() {
		return "AddressBookMain contactPersonList=" + contactPersonList;
	}

}
