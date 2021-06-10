package com.innovTeam.VaccinationPortal.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.innovTeam.VaccinationPortal.models.User;
import com.innovTeam.VaccinationPortal.repository.NonAdminRepository;

@Service
public class UserService { 
 
	@Autowired
	private NonAdminRepository nonAdminRepository;

	public Optional<User> getUserByAdhar(String adharCard)
	{
		Optional<User> nonAdmin = nonAdminRepository.findById(adharCard);
		return nonAdmin;
	}
	
	public Optional<User> getUserByAdhar(String adharCard,String password)
	{
		Optional<User> nonAdmin = nonAdminRepository.findById(adharCard);
		if(nonAdmin.isPresent() && nonAdmin.get().getPassword().equals(password))
			return nonAdmin;
		else
			return Optional.empty();
	}
	
	public void saveNonAdmin(User nonAdmin)
	{
		nonAdminRepository.save(nonAdmin);
	}
	
	public Iterable<User> getListOfUser()
	{
		return nonAdminRepository.findAll();
	}
}
