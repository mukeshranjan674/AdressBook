package com.capgemini.addressbook;

import java.io.File;
import java.io.FileWriter;
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

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

/**
 * UC15
 * 
 * @author LENOVO
 *
 */
public class AddressBookJsonFileIO {
	private String PATH = "C:\\Users\\LENOVO\\eclipse-workspace\\AddressBookProblem\\src\\AddressBooksData\\Json";
	private List<ContactPerson> contactPersonList;
	Map<String, AddressBookMain> addressBooks = new TreeMap<>();

	public void writeAddressBooks(Map<String, AddressBookMain> addressBooks) {

		addressBooks.forEach((name, addressBook) -> {
			String FILE_NAME = PATH + "\\" + name + ".json";
			Path filePath = Paths.get(FILE_NAME);
			if (!Files.exists(filePath)) {
				try {
					Files.createFile(filePath);
				} catch (IOException e) {
				}
			}
			try {
				List<ContactPerson> contactPersonList = addressBook.getContactPersonList();
				Writer writer = new FileWriter(FILE_NAME);
				new Gson().toJson(contactPersonList, writer);
				writer.close();
			} catch (Exception ex) {
				ex.printStackTrace();
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

				contactPersonList = new Gson().fromJson(reader, new TypeToken<List<ContactPerson>>() {
				}.getType());
				AddressBookMain addressBookMain = new AddressBookMain();
				addressBookMain.setContactPersonList(contactPersonList);
				addressBooks.put(fileName, addressBookMain);
				reader.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		});
		return addressBooks;
	}
}
