package com.example.demo.controller.stray;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.demo.model.adoption.AdoptionCriteria;
import com.example.demo.model.adoption.AdoptionDTO;
import com.example.demo.model.adoption.AdoptionPageDTO;
import com.example.demo.service.adoption.AdoptionService;

@Controller
@RequestMapping("/adoption/*")
public class AdoptionController {
	@Autowired
	private AdoptionService adservice;
	
	@GetMapping(value = {"list"})
	public void list(AdoptionCriteria adCri, Model model) {
		System.out.println(adCri);
		List<AdoptionDTO> list = adservice.getList(adCri);
		model.addAttribute("pageMaker", new AdoptionPageDTO(adservice.getTotal(adCri), adCri));
		model.addAttribute("list",list);
	}
	
	@GetMapping("write")
	public void write() {}
	
	@PostMapping("write")
	public String write(AdoptionDTO adoption) {
		System.out.println(adoption);
		adservice.regist(adoption);
		return "redirect:/adoption/list";
	}
}














