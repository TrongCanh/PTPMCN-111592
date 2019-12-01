package com.sapo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.Calendar;
import java.util.Date;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
public class HelloWorldController {
	@Autowired
	RestTemplate restTemplate;

	@RequestMapping({ "/home" })
	public String hello() {
		return "Cảnh đẹp trai";
	}

	

}
