package com.nt.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.nt.domain.UnlockAccount;
import com.nt.domain.UserAccount;
import com.nt.service.UserService;


@Controller
public class UnlockAccCEmailController {

	@Autowired
	private UserService service;

		@GetMapping("/unlockAcc")
	public String displayUnlockForm(@RequestParam("email") String email,Model model) {
		model.addAttribute("email", email);
		
		UnlockAccount unlockAcc=new  UnlockAccount();
		unlockAcc.setEmail(email);																//two binding 
		model.addAttribute("unlockAcc",unlockAcc);
		
		return "UnlockAccForm";
	}
	
	@PostMapping("/unlockUserAcc")
	public String unlockUserAcc(@ModelAttribute("unlockAcc") UnlockAccount unlockAcc, Model model) {

		UserAccount userAcc=service.getUserAccByTempPwd(unlockAcc.getTempPwd(),unlockAcc.getEmail());
		if(userAcc!=null) {
			//update acc_Status and password
			userAcc.setStatus("Un-Locked");
			userAcc.setPassword(unlockAcc.getNewPwd());
			boolean isUpdate=service.updateUserAccount(userAcc);
			if(isUpdate)
			return "UnlockAccSuccess";
		}
			//dispaly error msg on the same page
		model.addAttribute("errMsg","Please enter Correct Password");
		
		return "UnlockAccForm";
	}
	
}
