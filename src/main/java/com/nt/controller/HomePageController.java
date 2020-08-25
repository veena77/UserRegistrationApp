package com.nt.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class HomePageController {

	 @RequestMapping(value = "/login", method = RequestMethod.GET)
	    public String loginPage(Model model) {
	 
	        return "Home";
	    }
}
