package com.nt.controller;

import java.text.SimpleDateFormat;

import java.util.Date;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.nt.domain.UserAccount;
import com.nt.service.UserService;

@Controller
public class UserController {
	
	@Autowired
	private UserService service;

	@InitBinder
	public void myBinder(ServletRequestDataBinder binder) {
	SimpleDateFormat sdf=null;
	sdf=new SimpleDateFormat("dd-MM-yyyy");
	binder.registerCustomEditor(Date.class, new CustomDateEditor(sdf,false));
	}

	@RequestMapping("/loadForm")
	public String loadForm(Model model,@ModelAttribute("user") UserAccount userAcc ) {
		
		 userAcc =new UserAccount();
		model.addAttribute("userAcc",userAcc);

		Map<Integer,String> countryMap=service.getAllCountries();
		model.addAttribute("countryMap", countryMap);
		return "addUserForm";
	}
	@PostMapping("/userAccReg")
	public String userAccRegistration(@ModelAttribute("user") UserAccount userAcc,Model model) {
		
		service.saveUserAcc(userAcc);
	return "userRegFormSuccess";
	}
	
	@RequestMapping("/getStates")
	@ResponseBody
	public Map<Integer,String>  getStatesByCountryId(@RequestParam("cid") Integer countryId){
		return service.getStateByCountryId(countryId);
	}
	
	@RequestMapping("/getCities")
	@ResponseBody
	public Map<Integer,String> getCitiesByStateId(@RequestParam("sid") Integer stateId){
		return service.getCityByStateId(stateId);
	}
}

