package com.innovTeam.VaccinationPortal.models;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Vaccination_Center 
{
	@Id
	private String licence_number;
	private String name;
	private String address;
	private String pinCode;
	private int allocatedUsers;
	
	public int getAllocatedUsers() {
		return allocatedUsers;
	}
	public void setAllocatedUsers(int allocatedUsers) {
		this.allocatedUsers = allocatedUsers;
	}
	public String getLicence_number() {
		return licence_number;
	}
	public void setLicence_number(String licence_number) {
		this.licence_number = licence_number;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getPinCode() {
		return pinCode;
	}
	public void setPinCode(String pincode) {
		this.pinCode = pincode;
	}
	
}
