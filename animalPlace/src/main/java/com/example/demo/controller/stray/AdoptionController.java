package com.example.demo.controller.stray;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.demo.domain.he.UserDTO;
import com.example.demo.model.adoption.AdoptionCriteria;
import com.example.demo.model.adoption.AdoptionDTO;
import com.example.demo.model.adoption.AdoptionPageDTO;
import com.example.demo.service.adoption.AdoptionService;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

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
	
	@GetMapping("get")
	public String get(AdoptionCriteria adCri, long adoptionnum, HttpServletRequest req, HttpServletResponse resp, Model model) {
		//list 에서 보던 곳으로 돌아가기 위한 cri 세팅
		model.addAttribute("adCri",adCri);
		HttpSession session = req.getSession();
		//현재 로그인 된 사람의 아이디
		String loginUser = (String)session.getAttribute("loginUser");
		//넘겨진 adoptionnum에 해당하는 게시글 데이터 검색
		AdoptionDTO adoption = adservice.getDetail(adoptionnum);
		UserDTO user = adservice.getUserDetail(adoption.getUserid());
		System.out.println(adoption);
		
		Cookie[] cookies = req.getCookies();
		if(cookies != null) {
			for(Cookie cookie : cookies) {
				//get으로 넘어온 때가 작성 완료 후 넘어왔다면, 알럿을 띄워주기 위한 w 쿠키 검사 후 모델에 추가
				if(cookie.getName().equals("w")) {
					model.addAttribute("w",cookie.getValue());
					break;
				}
			}
		}
		
		model.addAttribute("user", user);
		model.addAttribute("adoption",adoption);
		return "/adoption/get";
	}
	
	@GetMapping("modify")
	public void modify(AdoptionCriteria adCri, long adoptionnum, Model model) {
		AdoptionDTO adoption = adservice.getDetail(adoptionnum);
		model.addAttribute("adoption", adoption);
		model.addAttribute("adCri",adCri);
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	@GetMapping("remove")
	public String remove(AdoptionCriteria adCri, long adoptionnum, HttpServletRequest req) {
		String loginUser = (String)req.getSession().getAttribute("loginUser");
		AdoptionDTO adoption = adservice.getDetail(adoptionnum);
		if(adoption.getUserid().equals(loginUser)) {
			if(adservice.remove(adoptionnum)) {
				return "redirect:/adoption/list"+adCri.getListLink();
			}
		}
		return "redirect:/adoption/get"+adCri.getListLink()+"&adoptionnum="+adoptionnum;
	}
}














