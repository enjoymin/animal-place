package com.example.demo.controller.he;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.domain.he.AlarmDTO;
import com.example.demo.domain.he.Criteria;
import com.example.demo.domain.he.MBoardDTO;
import com.example.demo.domain.he.PageDTO;
import com.example.demo.model.pboard.PFileDTO;
import com.example.demo.service.he.AlarmService;
import com.example.demo.service.he.MyService;
import com.example.demo.service.he.UserService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/my/*")
public class MyController {
	@Autowired
	private MyService service;
	@Autowired
	private UserService uservice;
	@Autowired
	private AlarmService aservice;
	
	@GetMapping("profile")
	public void profile(HttpServletRequest req, Model model) {
		HttpSession session = req.getSession();
		String userid =  (String) session.getAttribute("loginUser");
		model.addAttribute("user", uservice.getUser(userid));
		model.addAttribute("myphoto", uservice.getprofile(userid));
	}
	
	@GetMapping("contents")
	public void contents(Criteria cri, HttpServletRequest req, Model model) {
		cri.setPagenum(1);
		cri.setAmount(5);
		HttpSession session = req.getSession();
		String userid =  (String) session.getAttribute("loginUser");
		model.addAttribute("mtotal", service.getMBtotal(userid));
		model.addAttribute("ptotal", service.getPBtotal(userid));
	}
	
	@ResponseBody
	@GetMapping("contentsMB")
	public Map<String, Object> contentsMB(HttpServletRequest req, Criteria cri) {
		HttpSession session = req.getSession();
		String userid =  (String) session.getAttribute("loginUser");
		List<MBoardDTO> list = service.getMBList(cri, userid);
		Map<String, Object> response = new HashMap<>();
		response.put("list", list);
		response.put("pageMaker",new PageDTO(service.getMBtotal(userid), cri));
		return response;
//		return new ResponseEntity<>(list,HttpStatus.OK);
	}
	@GetMapping("contentsMBprv")
	@ResponseBody
	public List<MBoardDTO> contentsMBprv(HttpServletRequest req, Criteria cri) {
		cri.setPagenum(1);
		cri.setAmount(5);
		HttpSession session = req.getSession();
		String userid =  (String) session.getAttribute("loginUser");
		List<MBoardDTO> list = service.getMBList(cri, userid);
		return list;
	}
	@GetMapping("contentsPBprv")
	@ResponseBody
	public List<PFileDTO> contentsPBprv(HttpServletRequest req, Criteria cri){
		cri.setPagenum(1);
		cri.setAmount(5);
		HttpSession session = req.getSession();
		String userid =  (String) session.getAttribute("loginUser");
		List<PFileDTO> list = service.getPFile(cri, userid);
		System.out.println(list);
		return list;
	}
	@ResponseBody
	@GetMapping("contentsPB")
	public Map<String, Object> contentsPB(HttpServletRequest req, int pagenum) {
		Criteria cri = new Criteria(pagenum, 15);
		HttpSession session = req.getSession();
		String userid =  (String) session.getAttribute("loginUser");
		List<PFileDTO> list = service.getPFile(cri, userid);
		Map<String, Object> response = new HashMap<>();
		response.put("list", list);
		response.put("pageMaker",new PageDTO(service.getPBtotal(userid), cri));
		return response;
	}
	@GetMapping("alarm")
	@ResponseBody
	public List<AlarmDTO> alarm(HttpServletRequest req) {
		HttpSession session = req.getSession();
		String userid =  (String) session.getAttribute("loginUser");
		List<AlarmDTO> alarm =  aservice.getAlarm(userid);
		return alarm;
	}
	
	@GetMapping("alarmcheck")
	@ResponseBody
	public boolean alarmcheck(HttpServletRequest req) {
		HttpSession session = req.getSession();
		String userid =  (String) session.getAttribute("loginUser");
		return aservice.extAlarm(userid);
	}
	@GetMapping("deleteAlarm")
	@ResponseBody
	public void deleteAlarm(long num) {
		aservice.deleteAlarm(num);
	}
	
	@PostMapping("insertAlarmByLike")
	@ResponseBody
	public void insertAlarmByLike(String ctuserid, String boardtitle, String contentpath) {
		String flag = "plike";
		aservice.insertAlarm(ctuserid, boardtitle, contentpath, flag);
	}
}
