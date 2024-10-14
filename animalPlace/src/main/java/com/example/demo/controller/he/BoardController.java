package com.example.demo.controller.he;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.demo.domain.he.Criteria;


@Controller
@RequestMapping("/board/*")
public class BoardController {
	
	@GetMapping("list")
	public void list(Criteria cri) {
		
	}
}
