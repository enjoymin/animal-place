package com.example.demo.controller.stray;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.demo.domain.he.UserDTO;
import com.example.demo.model.adoption.AdoptionCriteria;
import com.example.demo.model.adoption.AdoptionDTO;
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
	public void list() {}
	
	@GetMapping("write")
	public void write() {}
	
	@PostMapping("write")
	public void writeOk(AdoptionDTO adoption) {
		System.out.println(adoption);
		adservice.regist(adoption);
	}
	
	@GetMapping("get")
	public String get(AdoptionCriteria adCri, long adoptionnum, HttpServletRequest req, HttpServletResponse resp, Model model) {
		//list 에서 보던 곳으로 돌아가기 위한 cri 세팅
		HttpSession session = req.getSession();
		//현재 로그인 된 사람의 아이디
		String loginUser = (String)session.getAttribute("loginUser");
		//넘겨진 adoptionnum에 해당하는 게시글 데이터 검색
		AdoptionDTO adoption = adservice.getDetail(adoptionnum);
		UserDTO user = adservice.getUserDetail(adoption.getUserid());
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
		
		adCri.setBreed(adoption.getBreed()); adCri.setType(adoption.getType());
		adCri.setRegion(adoption.getRegion()); adCri.setGender(adoption.getGender());
		adCri.setAge(adoption.getAge()); adCri.setCost(adoption.getCost());
		adCri.setAdoptionOk(adoption.getAdoptionOk());
		 
		
		model.addAttribute("user", user);
		model.addAttribute("adoption",adoption);
		model.addAttribute("adCri",adCri);
		return "/adoption/get";
	}
	
	@GetMapping("modify")
	public void modify(AdoptionCriteria adCri, long adoptionnum, Model model) {
		AdoptionDTO adoption = adservice.getDetail(adoptionnum);
		model.addAttribute("adoption", adoption);
		model.addAttribute("adCri",adCri);
	}
	
	@PostMapping("modify")
	public String modify(AdoptionDTO adoption, AdoptionCriteria adCri) throws Exception{
		System.out.println(adoption);
		System.out.println(adCri);
		if(adservice.modify(adoption)) {
			return "redirect:/adoption/get"+adCri.getListLink()+"&adoptionnum="+adoption.getAdoptionnum();			
		}
		return "redirect:/adoption/get"+adCri.getListLink()+"&adoptionnum="+adoption.getAdoptionnum();
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














