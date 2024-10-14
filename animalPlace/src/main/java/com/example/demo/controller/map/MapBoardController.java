package com.example.demo.controller.map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/map/*")
public class MapBoardController {


	@GetMapping(value = { "map"})
	public void replace() {

	}

}