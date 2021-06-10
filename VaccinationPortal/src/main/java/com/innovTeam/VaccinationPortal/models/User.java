package com.innovTeam.VaccinationPortal.models;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class User {

	private String name;
	private int age;
	@Id
	private String adharCard;
	private String password;
	private String gender;
	private String status;
	private String centerKey;
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getCenterKey() {
		return centerKey;
	}
	public void setCenterKey(String centerKey) {
		this.centerKey = centerKey;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public String getAdharCard() {
		return adharCard;
	}
	public void setAdharCard(String adharCard) {
		this.adharCard = adharCard;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
}
