package com.example.demo.controller.mboard;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HHomeController {
	@GetMapping("/")
	public String home() {
		return "index";
	}
}
