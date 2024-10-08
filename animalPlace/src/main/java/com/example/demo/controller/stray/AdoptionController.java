package com.example.demo.controller.stray;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.demo.model.adoption.AdoptionDTO;
import com.example.demo.service.adoption.AdoptionService;

@Controller
@RequestMapping("/adoption/*")
public class AdoptionController {
	@Autowired
	private AdoptionService adservice;
	
	@GetMapping(value = {"list"})
	public void list() {}
	
	@GetMapping("write")
	public void write() {}
	
	@PostMapping("write")
	public void writeOk(AdoptionDTO adoption) {
		System.out.println(adoption);
		adservice.regist(adoption);
	}
}














