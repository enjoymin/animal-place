package com.example.demo.controller.stray;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/adoption/*")
public class AdoptionController {
	final static String serviceKey = "pmF9%2BcvRwKXr%2B8J4llELzWtgxz8zqVTR7VWXuJiRRLMc7ifXlCqRf%2B%2BQeiVrQbNKMezOthWu%2FhmzU7bDNYmGKA%3D%3D";
	
	@GetMapping(value = {"list"})
	public void list() {}
	
	@GetMapping("write")
	public void write() {}
}














