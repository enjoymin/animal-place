package com.example.demo.controller.he;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.SystemEnvironmentPropertySource;
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
import com.example.demo.domain.he.NoteDTO;
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
	public void insertAlarmByLike(HttpServletRequest req, String ctuserid, String boardtitle, String contentpath) {
		HttpSession session = req.getSession();
		String user =  (String) session.getAttribute("loginUser");
		if(!user.equals(ctuserid)) {
			String flag = "plike";
			aservice.insertAlarm(ctuserid, boardtitle, contentpath, flag);
		}
	}
	@GetMapping("userprofile")
	public void userprofile(HttpServletRequest req, Model model, String userid) {
		model.addAttribute("user", uservice.getUser(userid));
		model.addAttribute("myphoto", uservice.getprofile(userid));
	}
	@GetMapping("writeNote")
	public void writeNote(HttpServletRequest req,Model model, String userid) {
		HttpSession session = req.getSession();
		String senduser =  (String) session.getAttribute("loginUser");
		model.addAttribute("receiveuser", userid);
		model.addAttribute("senduser", senduser);
		
	}
	@PostMapping("writeNote")
	@ResponseBody
	public void writeNotesend(NoteDTO note) {
		long noteNum = service.insertNote(note);
		String flag = "note";
		aservice.insertAlarm(note.getReceiveuser(), note.getTitle(), "/my/getNote?noteNum="+noteNum, flag);
	}
	@GetMapping("note")
	public void note(int page, Model model) {
		model.addAttribute("pageN", page);
	}
	
	@GetMapping("noteList")
	@ResponseBody
	public Map<String, Object> noteList(HttpServletRequest req, Model model, Criteria cri) {
		HttpSession session = req.getSession();
		String user =  (String) session.getAttribute("loginUser");
		List<NoteDTO> list = service.getNote(user, cri);
		Map<String, Object> response = new HashMap<>();
		response.put("list", list);
		response.put("pageMaker",new PageDTO(service.getNtotal(user), cri));
		return response;
	}
	@GetMapping("getNote")
	public void getNote(HttpServletRequest req, Model model, long noteNum, int page) {
		HttpSession session = req.getSession();
		String user =  (String) session.getAttribute("loginUser");
		NoteDTO note = service.getNoteCT(noteNum);
		model.addAttribute("note", note);
		model.addAttribute("pageN", page);
		aservice.deleteAlarmByPath(user, "/my/getNote?noteNum="+noteNum);
		service.updateNote(noteNum);
	}
	@GetMapping("deleteNote")
	public String deleteNote(Model model, long noteNum, int page) {
		service.delelteNote(noteNum);
		return "redirect:/my/note?page="+page;
	}
}
