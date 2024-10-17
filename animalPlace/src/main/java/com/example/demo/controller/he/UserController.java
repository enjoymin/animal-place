package com.example.demo.controller.he;

import java.io.File;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.domain.he.UserDTO;
import com.example.demo.service.he.UserService;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/user/*")
public class UserController {

	@Autowired
	private UserService service;

	@GetMapping("join")
	public void replace(String path, Model model) {
		model.addAttribute("path", path);
	}

	@PostMapping("join")
	public String join(UserDTO user, @RequestParam String path) {
		if (service.join(user)) {
		} else {
		}
		return "redirect:"+path;
	}
	@PostMapping("moveJoin")
	public String moveJoin(String jpath, Model model) {
		model.addAttribute("path", jpath);
		return "/user/join";
	}
	
	@GetMapping("checkId")
	@ResponseBody
	public boolean checkId(String userid) {
		UserDTO user = service.getUser(userid);
		if(user == null) {
			return true;
		}
		return false;
	}

	@PostMapping("login")
	public String login(String path, String userid, String userpw, HttpServletRequest req) {
		HttpSession session = req.getSession();
		if (service.login(userid, userpw)) {
			session.setAttribute("loginUser", userid);
			if (!path.equals("") && !path.equals("/user/join")) {
				return "redirect:" + path;
			} 
			else {
				return "redirect:/";
			}
		} else
			return "redirect:/";
	}

	
	@GetMapping("isUser")
	@ResponseBody
	public boolean isUser(String userid, String userpw) {
		return service.login(userid, userpw);
	}

//	@GetMapping("login")
//	public String login(String path, HttpServletResponse resp) {
//		// 로그인 시도시 로그인 후 현재 페이지로 이동을 위해 쿠키 추가
//		Cookie cookie = new Cookie("path", path);
//		cookie.setPath("/");
//		cookie.setMaxAge(180);
//		resp.addCookie(cookie);
//		return "redirect:/";
//	}

	@PostMapping("logout")
	public String logout(String path, HttpServletRequest req) {
		req.getSession().invalidate();
		return "redirect:" + path;
	}

	@PostMapping("modify")
	public String modify(UserDTO user, MultipartFile myphoto) {
		if (myphoto == null || myphoto.isEmpty()) {
		}
		if (service.updateUser(user, myphoto)) {
		} else {
		}
		return "redirect:/my/profile";
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
