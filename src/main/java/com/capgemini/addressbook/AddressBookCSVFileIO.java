package com.capgemini.addressbook;

import java.io.File;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;

/**
 * UC14
 * 
 * @author LENOVO
 *
 */
public class AddressBookCSVFileIO {
	private String PATH = "C:\\Users\\LENOVO\\eclipse-workspace\\AddressBookProblem" + "\\src\\AddressBooksData\\CSV";
	private List<ContactPerson> contactPersonList;
	Map<String, AddressBookMain> addressBooks = new TreeMap<>();

	public void writeAddressBooks(Map<String, AddressBookMain> addressBooks) {
		addressBooks.forEach((name, addressBook) -> {
			String FILE_NAME = PATH + "\\" + name + ".csv";
			Path filePath = Paths.get(FILE_NAME);
			if (!Files.exists(filePath)) {
				try {
					Files.createFile(filePath);
				} catch (IOException e) {
				}
			}
			try (Writer writer = Files.newBufferedWriter(Paths.get(FILE_NAME));) {
				CSVWriter csvWriter = new CSVWriter(writer, CSVWriter.DEFAULT_SEPARATOR, CSVWriter.NO_QUOTE_CHARACTER,
						CSVWriter.DEFAULT_ESCAPE_CHARACTER, CSVWriter.DEFAULT_LINE_END);
				List<ContactPerson> contactPersonList = addressBook.getContactPersonList();
				for (ContactPerson c : contactPersonList) {
					String[] userData = { c.getFirstName(), c.getLastName(), c.getAddress(), c.getCity(), c.getState(),
							c.getZip(), c.getPhone(), c.getEmail() };
					csvWriter.writeNext(userData);
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		});
	}

	public Map<String, AddressBookMain> readData() {
		List<String> fileNames = new ArrayList<String>();
		File[] files = new File(PATH).listFiles();
		for (File file : files) {
			if (file.isFile())
				fileNames.add(file.getName());
		}
		fileNames.forEach(fileName -> {
			contactPersonList = new ArrayList<ContactPerson>();

			try {
				Reader reader = Files.newBufferedReader(Paths.get(PATH + "\\" + fileName));
				CSVReader csvReader = new CSVReader(reader);

				String[] contactPersonArray;
				while ((contactPersonArray = csvReader.readNext()) != null) {
					String firstName = contactPersonArray[0];
					String lastName = contactPersonArray[1];
					String address = contactPersonArray[2];
					String city = contactPersonArray[3];
					String state = contactPersonArray[4];
					String zip = contactPersonArray[5];
					String phone = contactPersonArray[6];
					String email = contactPersonArray[7];
					ContactPerson contact = new ContactPerson(firstName, lastName, address, city, state, zip, phone,
							email);
					contactPersonList.add(contact);
				}
				AddressBookMain addressBookMain = new AddressBookMain();
				addressBookMain.setContactPersonList(contactPersonList);
				addressBooks.put(fileName.substring(0, fileName.length() - 4), addressBookMain);
				csvReader.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		});
		return addressBooks;
	}
}
