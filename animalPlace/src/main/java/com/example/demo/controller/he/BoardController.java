package com.example.demo.controller.he;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.demo.domain.he.Criteria;
import com.example.demo.service.pboard.PFileService;

@Controller
@RequestMapping("/board/*")
public class BoardController {
	
	@Autowired
	private PFileService pfservice;
	
	
	@GetMapping("list")
	public void list(Criteria cri,Model model) {
		model.addAttribute("flist",pfservice.getBestImage());
	}
}
