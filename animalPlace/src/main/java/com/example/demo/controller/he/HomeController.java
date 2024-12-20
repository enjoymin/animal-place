package com.example.demo.controller.he;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.example.demo.service.mboard.M_BoardService;
import com.example.demo.service.pboard.PFileService;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;

@Controller
public class HomeController {
	
	@Autowired
	private PFileService pfservice;
	
	@Autowired
	private M_BoardService mservice;
	
	@GetMapping("/")
	public String Home(HttpServletRequest req, Model model) {
		// 다른 페이지에서 로그인으로 넘어온 경우 받아온 쿠키를 모델에 추가하여 해당 페이지로 이동하도록 설정
		Cookie[] cookies = req.getCookies();
		if(cookies != null) {
			for(Cookie cookie : cookies) {
				if(cookie.getName().equals("path")) {
					model.addAttribute("path",cookie.getValue());
					break;
				}
			}
		}
		
		model.addAttribute("mlist", mservice.getRecentList());
		
		model.addAttribute("flist",pfservice.getBestImage());
		return "index";
	}
}
