package com.innovTeam.VaccinationPortal.models;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Admin {

	private String name;
	private int age;
	@Id
	private String adharCard;
	private String password;
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
	public String getAdharCard() {
		return adharCard;
	}
	@Override
	public String toString() {
		return "Admin [name=" + name + ", age=" + age + ", adharCard=" + adharCard + ", password=" + password + "]";
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
