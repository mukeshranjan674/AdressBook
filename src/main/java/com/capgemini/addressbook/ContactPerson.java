package com.capgemini.addressbook;

/**
 * UC2
 * 
 * @author LENOVO
 *
 */
class ContactPerson {

	private String firstName;
	private String lastName;
	private String address;
	private String city;
	private String state;
	private String zip;
	private String phone;
	private String email;

	public ContactPerson(String firstName, String lastName, String address, String city, String state, String zip,
			String phone, String email) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.address = address;
		this.city = city;
		this.state = state;
		this.zip = zip;
		this.phone = phone;
		this.email = email;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getZip() {
		return zip;
	}

	public void setZip(String zip) {
		this.zip = zip;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public boolean equals(Object o) {
		ContactPerson c = (ContactPerson) o;
		return c.firstName.equals(this.firstName);
	}

	public String toString() {
		return "\n Name " + firstName + " " + lastName + "\n Address " + address + "\n city " + city + "\n state "
				+ state + "\n zip " + zip + "\n Phone " + phone + "\n email " + email;
	}
}
