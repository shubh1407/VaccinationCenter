package com.innovTeam.VaccinationPortal.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.innovTeam.VaccinationPortal.models.Admin;
import com.innovTeam.VaccinationPortal.models.Vaccination_Center;
import com.innovTeam.VaccinationPortal.repository.AdminRepository;
import com.innovTeam.VaccinationPortal.repository.VaccinationCenterRepository;

@Service
public class AdminServices {
	
	@Autowired
	private AdminRepository adminRepository;
	
	@Autowired
	private VaccinationCenterRepository vaccinationCenterRepository;
	
	public AdminServices() {
		System.out.println("Service");
	}

	public Optional<Admin> getAdminByAdhar(String adharCard,String password)
	{
		Optional<Admin> admin = adminRepository.findById(adharCard);
		if(admin.isPresent() && admin.get().getPassword().equals(password))
			return admin;
		else
			return Optional.empty();
	}
	
	public Optional<Admin> getAdminByAdhar(String adharCard)
	{
		Optional<Admin> admin = adminRepository.findById(adharCard);
		return admin;
	}
	
	public void saveAdmin(Admin admin)
	{
		adminRepository.save(admin);
	}
	
	public void saveCenter(Vaccination_Center vaccination_Centres)
	{
		vaccinationCenterRepository.save(vaccination_Centres);
	}
	
	public Iterable<Vaccination_Center> getVaccinationCenter()
	{
		return vaccinationCenterRepository.findAll();
	}
	
	public Optional<Vaccination_Center> getVaccinationCenterById(String centerKey)
	{
		if(centerKey!=null)
			return vaccinationCenterRepository.findById(centerKey);
		else
			return Optional.empty();
	}
}
