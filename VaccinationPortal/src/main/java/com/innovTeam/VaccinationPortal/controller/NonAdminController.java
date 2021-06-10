package com.innovTeam.VaccinationPortal.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.innovTeam.VaccinationPortal.models.User;
import com.innovTeam.VaccinationPortal.models.Vaccination_Center;
import com.innovTeam.VaccinationPortal.service.AdminServices;
import com.innovTeam.VaccinationPortal.service.UserService;

@Controller
@RequestMapping("/user")
public class NonAdminController {

	@Autowired
	private UserService userService;
	
	@Autowired
	private AdminServices adminServices;
	
	@GetMapping("/")
	public String adminIndexPage(User user)
	{
		return "userIndex";
	}
	
	@GetMapping("/logout")
	public String adminLogout(User user)
	{
		return "index";
	}
	
	@PostMapping("/register")
	public ModelAndView registerAdmin(User user)
	{
		ModelAndView modelAndView = new ModelAndView();
		if (!(user.getName()!=null && !user.getName().isEmpty() && user.getAge()>0
				&& user.getPassword()!=null && !user.getPassword().isEmpty() && user.getAdharCard()!=null && !user.getAdharCard().isEmpty()))
		{
			modelAndView.setViewName("userIndex");
			modelAndView.addObject("registermessage","Please enter all information carefully");
			return modelAndView;
		}
		if(! userService.getUserByAdhar(user.getAdharCard()).isPresent())
		{
			user.setStatus("Waiting");
			userService.saveNonAdmin(user);
			modelAndView.setViewName("register");
			modelAndView.addObject("message","User register successful");
			return modelAndView;
		}
		else
		{
			modelAndView.setViewName("register");
			modelAndView.addObject("message","Adhar card is already registered ");
		}
		return modelAndView;
	}
	
	@PostMapping("/login")
	public ModelAndView loginAdmin(User user)
	{
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("userIndex");
		modelAndView.addObject("loginmessage","Please enter all information carefully");
		if(user.getPassword()!=null && !user.getPassword().isEmpty() && user.getAdharCard()!=null && !user.getAdharCard().isEmpty())
		{
			Optional<User> adminOption=userService.getUserByAdhar(user.getAdharCard(),user.getPassword());
			if(adminOption.isPresent())
			{
				Optional<Vaccination_Center> center=adminServices.getVaccinationCenterById(adminOption.get().getCenterKey());
				if(center.isPresent()) {
					modelAndView.addObject("center",center.get().getName()+"\n"+center.get().getAddress()+","+center.get().getPinCode());
				}
				modelAndView.setViewName("userLogin");
				modelAndView.addObject("user",adminOption.get());
			}
		}
		return modelAndView;
	}
}
