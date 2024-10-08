package com.example.demo.controller.mboard;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.domain.mboard.UserDTO;
import com.example.demo.service.mboard.UserService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/user/*")
public class UserController {
	
	@Autowired
	private UserService service;
	
	@GetMapping("login")
	public String login() {
		return "redirect:/";
	}
	
	@GetMapping("logout")
	public String logout(HttpServletRequest req) {
		req.getSession().invalidate();
		return "redirect:/";
	}
	
	@PostMapping("login")
	public String login(String userid,String userpw,HttpServletRequest req) {
		HttpSession session = req.getSession();
		if(service.login(userid, userpw)) {
			session.setAttribute("loginUser", userid);
			System.out.println("세션의 loginUser: " + session.getAttribute("loginUser"));
			return "redirect:/board/m_board"; //보드 가지고 긁어가기
		}
		return "redirect:/"; //로그인 실패시 메인으로
	}
	
	
	// 참가하기
	
	@GetMapping("get_schedule")
	public ResponseEntity<String> get_schedule(@RequestParam String userid) {
		String schedule = service.getUserSchedule(userid).getSchedule();
		return ResponseEntity.ok(schedule != null ? schedule : "");
	}
	
	@PostMapping("update_schedule")
	public ResponseEntity<String> update_schedule(@RequestBody UserDTO scheduleInfo) {
	    service.update_schedule(scheduleInfo);
	    return ResponseEntity.ok("일정 업데이트 완료");
	}
}
