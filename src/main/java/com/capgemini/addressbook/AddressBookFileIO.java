package com.capgemini.addressbook;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import com.capgemini.addressbook.AddressBook.FileType;

/**
 * UC13
 * 
 * @author LENOVO
 *
 */
public class AddressBookFileIO {
	private static String FILE_NAME = "C:\\Users\\LENOVO\\eclipse-workspace"
			+ "\\AddressBookProblem\\src\\AddressBooksData\\Text\\address-book-details.txt";

	public void writeAddressBooks(Map<String, AddressBookMain> addressBooks, FileType fileIO) {
		if (fileIO.equals(FileType.TEXT)) {
			StringBuffer addressBuffer = new StringBuffer();
			addressBooks.forEach((name, addressBook) -> {
				String addressBookString = name + " " + addressBook.toString().concat("\n");
				addressBuffer.append(addressBookString);
			});
			try {
				if (!Files.exists(Paths.get(FILE_NAME)))
					Files.createFile(Paths.get(FILE_NAME));
				Files.write(Paths.get(FILE_NAME), addressBuffer.toString().getBytes());
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else if (fileIO.equals(FileType.CSV)) {
			new AddressBookCSVFileIO().writeAddressBooks(addressBooks);
		} else {
			new AddressBookJsonFileIO().writeAddressBooks(addressBooks);
		}

	}

	public Map<String, AddressBookMain> readData() {
		List<String> list = new ArrayList<String>();
		List<ContactPerson> contactPersonList = new ArrayList<ContactPerson>();
		Map<String, AddressBookMain> addressBooks = new TreeMap<>();
		try {
			Files.lines(new File(FILE_NAME).toPath()).map(line -> line.trim()).forEach(n -> {
				list.add(n.toString());
			});
			int index = 0;
			String addressBookName = "";
			String firstName = "";
			String lastName = "";
			String address = "";
			String city = "";
			String state = "";
			String zip = "";
			String phone = "";
			String email = "";
			while (index < list.size()) {
				if (list.get(index).contains("AddressBookMain")) {
					addressBookName = list.get(index).substring(0, list.get(index).length() - 36);
					index++;
				}
				if (list.get(index).contains("Name")) {
					String[] nameData = list.get(index).split(" ");
					firstName = nameData[1];
					lastName = nameData[2];
					index++;
				}
				address = list.get(index++).substring(8);
				city = list.get(index++).substring(5);
				state = list.get(index++).substring(6);
				zip = list.get(index++).substring(4);
				phone = list.get(index++).substring(6);
				email = list.get(index).substring(6, list.get(index).length() - 1);
				if (list.get(index).charAt(list.get(index).length() - 1) == ',') {
					ContactPerson c = new ContactPerson(firstName, lastName, address, city, state, zip, phone, email);
					contactPersonList.add(c);
					index++;
				}
				if (list.get(index).charAt(list.get(index).length() - 1) == ']') {
					ContactPerson c = new ContactPerson(firstName, lastName, address, city, state, zip, phone, email);
					contactPersonList.add(c);
					AddressBookMain a = new AddressBookMain();
					a.setContactPersonList(contactPersonList);
					addressBooks.put(addressBookName, a);
					contactPersonList = new ArrayList<ContactPerson>();
					index++;
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return addressBooks;
	}
}
