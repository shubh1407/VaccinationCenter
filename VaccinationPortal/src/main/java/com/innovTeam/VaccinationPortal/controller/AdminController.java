package com.innovTeam.VaccinationPortal.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.innovTeam.VaccinationPortal.models.Admin;
import com.innovTeam.VaccinationPortal.models.User;
import com.innovTeam.VaccinationPortal.models.Vaccination_Center;
import com.innovTeam.VaccinationPortal.service.AdminServices;
import com.innovTeam.VaccinationPortal.service.UserService;
import com.innovTeam.VaccinationPortal.util.Utility;

@Controller
@RequestMapping("/admin")
public class AdminController {

	@Autowired
	private AdminServices adminServices;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private Utility utility;
	
	
	@RequestMapping("/")
	public String adminIndexPage(Admin admin)
	{
		System.out.println("index page");
		return "adminIndex";
	}
	
	@PostMapping("/logout")
	public String adminLogout(Admin admin)
	{
		return "adminIndex";
	}
	
	@PostMapping("/register")
	public ModelAndView registerAdmin(@Valid Admin admin)
	{
		ModelAndView modelAndView = new ModelAndView();
		//admin.getName()!=null && !admin.getName().isEmpty() && admin.getAge()>0 && admin.getPassword()!=null && !admin.getPassword().isEmpty() && admin.getAdharCard()!=null && !admin.getAdharCard().isEmpty()
		
		if(!(admin.getName()!=null && !admin.getName().isEmpty() 
				&& admin.getAge()>0 && admin.getPassword()!=null && 
				!admin.getPassword().isEmpty() && admin.getAdharCard()!=null && 
				!admin.getAdharCard().isEmpty()))
		{
			modelAndView.setViewName("adminIndex");
			modelAndView.addObject("registermessage","Please enter all information carefully");
			return modelAndView;
		}
		if(! adminServices.getAdminByAdhar(admin.getAdharCard()).isPresent())
		{
			adminServices.saveAdmin(admin);
			modelAndView.setViewName("register");
			modelAndView.addObject("message","Admin register successful");
		}
		else
		{
			modelAndView.setViewName("register");
			modelAndView.addObject("message","Adhar card is already registered ");
		}
		return modelAndView;
	}
	
	@PostMapping("/login")
	public ModelAndView loginAdmin(Admin admin)
	{
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("adminIndex");
		modelAndView.addObject("loginmessage","Please enter all information carefully");
		if(admin.getPassword()!=null && !admin.getPassword().isEmpty() && admin.getAdharCard()!=null && !admin.getAdharCard().isEmpty())
		{
			Optional<Admin> adminOption=adminServices.getAdminByAdhar(admin.getAdharCard(),admin.getPassword());
			if(adminOption.isPresent())
			{
				modelAndView.setViewName("adminLogin");
				//modelAndView.addObject("admin",adminOption.get());
			}
		}
		return modelAndView;
	}
	
	
	@PostMapping("/updateAdmin")
	public ModelAndView updateDetails(Admin admin)
	{
		ModelAndView modelAndView = new ModelAndView();
		if(admin.getName()!=null && !admin.getName().isEmpty() && admin.getAge()>0
				&& admin.getPassword()!=null && !admin.getPassword().isEmpty() && admin.getAdharCard()!=null && !admin.getAdharCard().isEmpty()
				&& adminServices.getAdminByAdhar(admin.getAdharCard()).isPresent())
		{
			adminServices.saveAdmin(admin);
			modelAndView.setViewName("login");
			modelAndView.addObject("admin",admin);
		}
		else
		{
			modelAndView.setViewName("adminIndex");
			modelAndView.addObject("message","Information is wrong");
		}
		return modelAndView;
	}
	
	@PostMapping("/listUser")
	public ModelAndView UserLists()
	{
		ModelAndView modelAndView = new ModelAndView();
		List<User> users = new ArrayList<User>();
		Iterable<User> usersInDb= userService.getListOfUser();
		if(usersInDb!=null)
		{
			usersInDb.forEach(user -> users.add(user));
		}
		modelAndView.setViewName("UserList");
		modelAndView.addObject("users",users);
		return modelAndView;
	}
	
	
	@PostMapping("/center")
	public ModelAndView centerRegister(Vaccination_Center vaccination_Center)
	{
		ModelAndView modelAndView = new ModelAndView("center");
		return modelAndView;
	}
	
	@PostMapping("/registerCenter")
	public ModelAndView centerRegisteration(Vaccination_Center vaccination_Center)
	{
		ModelAndView modelAndView = new ModelAndView("adminLogin");
		if(!(vaccination_Center.getName()!=null && !vaccination_Center.getName().isEmpty() && vaccination_Center.getAddress()!=null &&
				! vaccination_Center.getAddress().isEmpty() && vaccination_Center.getPinCode()!=null && 
				!vaccination_Center.getPinCode().isEmpty()))
		{
			modelAndView.setViewName("center");
			modelAndView.addObject("registermessage","Please enter all information carefully");
			return modelAndView;
		}
		else
		{
			vaccination_Center.setLicence_number(utility.getLicenceKey());
			adminServices.saveCenter(vaccination_Center);
			modelAndView.setViewName("center");
			modelAndView.addObject("registermessage","Vaccination Registered Successfully");
		}
		return modelAndView;
	}
	
	
	@PostMapping("/listCenter")
	public ModelAndView getListOfCenter()
	{
		ModelAndView modelAndView = new ModelAndView("CenterList");
		List<Vaccination_Center> centers = new ArrayList<Vaccination_Center>();
		Iterable<Vaccination_Center> iterableCenter = adminServices.getVaccinationCenter();
		if(iterableCenter!=null)
		{
			iterableCenter.forEach(center -> centers.add(center));
		}
		modelAndView.addObject("centers",centers);
		return modelAndView;
	}
	
	
	@PostMapping("/editStatus")
	public ModelAndView UserLists(@RequestParam String adharCard)
	{
		ModelAndView modelAndView = new ModelAndView();
		List<Vaccination_Center> centers = new ArrayList<Vaccination_Center>();
		Optional<User> userOptional=userService.getUserByAdhar(adharCard);
		if(userOptional.isPresent())
		{
			Optional<Vaccination_Center> center=adminServices.getVaccinationCenterById(userOptional.get().getCenterKey());
			Iterable<Vaccination_Center> centerList=adminServices.getVaccinationCenter();
			centerList.forEach(c->{
				if(c.getAllocatedUsers()<50) {
					centers.add(c);
				}
			});
			if(center.isPresent())
				modelAndView.addObject("center",center.get().getName()+"\n"+center.get().getAddress()+","+center.get().getPinCode());
			modelAndView.addObject("centers",centers);
		}
		modelAndView.setViewName("UserDetails");
		modelAndView.addObject("statusList",Arrays.asList(new String[] {"Waiting","Ready","Vaccinated"}));
		modelAndView.addObject("user",userOptional.get());
		return modelAndView;
	}
	
	
	@PostMapping("/updateUser")
	public ModelAndView updateUserStatus(@RequestParam("adharCard") String adharCard, @RequestParam("status") String status,@RequestParam("centerKey") String centerKey)
	{
		ModelAndView modelAndView = new ModelAndView();
		Optional<User> userOptional=userService.getUserByAdhar(adharCard);
			if(userOptional.isPresent())
			{
				userOptional.get().setStatus(status);
				userOptional.get().setCenterKey(centerKey);
				userService.saveNonAdmin(userOptional.get());
				Optional<Vaccination_Center> center=adminServices.getVaccinationCenterById(userOptional.get().getCenterKey());
				if(center.isPresent())
				{
					if(status.equals("Ready"))
						center.get().setAllocatedUsers(center.get().getAllocatedUsers()+1);
					if(status.equals("Vaccinated"))
						center.get().setAllocatedUsers(center.get().getAllocatedUsers()-1);
					adminServices.saveCenter(center.get());
				}
			}
			List<User> users = new ArrayList<User>();
			Iterable<User> usersInDb= userService.getListOfUser();
			if(usersInDb!=null)
			{
				usersInDb.forEach(user -> users.add(user));
			}
			modelAndView.setViewName("UserList");
			modelAndView.addObject("users",users);
		return modelAndView;
	}
	
	
	@PostMapping("/allocateCenter")
	public ModelAndView allocateCenter(@RequestParam ("adhar") String adhar,@RequestParam("status") String status)
	{
		ModelAndView modelAndView = new ModelAndView();
		if(adhar!=null && ! adhar.isEmpty() && status!=null && ! status.isEmpty())
		{
			Optional<User> userOptional=userService.getUserByAdhar(adhar);
			if(userOptional.isPresent())
			{
				User user = userOptional.get();
				user.setStatus(status);
				userService.saveNonAdmin(user);
			}
		}
		else
		{
			modelAndView.setViewName("error");
			modelAndView.addObject("message","Some Error occurs. Please retry again");
		}
		return modelAndView;
	}
}
