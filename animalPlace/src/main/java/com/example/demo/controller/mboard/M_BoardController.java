package com.example.demo.controller.mboard;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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

import com.example.demo.domain.mboard.M_BoardDTO;
import com.example.demo.domain.mboard.M_ReplyDTO;
import com.example.demo.service.mboard.M_BoardService;
import com.example.demo.service.mboard.M_ReplyService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/mboard/*")
public class M_BoardController {
	
	@Autowired
	private M_BoardService service;
	
	@Autowired
	private M_ReplyService re_service;
	
	@GetMapping("m_board")
	public void m_board(Model model, HttpServletRequest req, HttpSession session) {
		String loginUser = (String) session.getAttribute("loginUser");
		Cookie[] cookies = req.getCookies();
		if(cookies != null) {
			for(Cookie cookie : cookies) {
				if(cookie.getName().equals("d")) {
					model.addAttribute("d",cookie.getValue());
					break;
				}
			}
		}
		// 모든 게시글 추가해주기
		List<M_BoardDTO> m_list = service.getList();
		model.addAttribute("m_list", m_list);
		// 글 개수 추가해주기
		model.addAttribute("list_cnt",service.getTotal());
		// 이 달의 게시글만 추가해주기
		List<M_BoardDTO> this_m_list = service.this_m_list();
	    model.addAttribute("this_m_list", this_m_list);
		// 이 달의 게시글의 인원수 추가해주기
	    List<Integer> member_cnt = service.this_m_list_memNum(this_m_list);
	    model.addAttribute("member_cnt", member_cnt);
	    // 나의 스케줄 받아오기
	    List<M_BoardDTO> my_list = service.getMyList(loginUser);
	    model.addAttribute("my_list", my_list);
	}

	@GetMapping("m_write")
	public void m_write() {
	}
	
	// 글 작성
	@PostMapping("m_write")
	public String m_write(M_BoardDTO mboard, HttpServletResponse resp) {
		mboard.make_dDay();
		if(service.regist(mboard)) {
			// w, t 는 등록 알럿 쿠키
			Cookie cookie = new Cookie("w", "t");
			cookie.setPath("/");
			cookie.setMaxAge(5);
			resp.addCookie(cookie);
			
			int mboardnum = service.getLastNum(mboard.getUserid());
			return "redirect:/mboard/m_get?mboardnum="+mboardnum;
		}
		return "redirect:/mboard/m_board";
	}
	
	@GetMapping("m_get")
	public String m_get(int mboardnum, Model model, HttpServletRequest req, HttpServletResponse resp) {
		Cookie[] cookies = req.getCookies();
		if(cookies != null) {
			for(Cookie cookie : cookies) {
				if(cookie.getName().equals("w")) {
					// 쿠키가 w 면 수정 쿠키를 get 으로 넘어갈때 추가해주기
					model.addAttribute("w",cookie.getValue());
					break;
				}
				if(cookie.getName().equals("s")) {
					// 쿠키가 s 면 수정 쿠키를 get 으로 넘어갈때 추가해주기
					model.addAttribute("s",cookie.getValue());
					break;
				}
			}
		}
		M_BoardDTO mboard = service.getDetail(mboardnum);
		model.addAttribute("m_board",mboard);		
		// 댓글 받아오기
	    List<M_ReplyDTO> reply_list = re_service.get_reply_list();
	    
	    Map<Integer, List<M_ReplyDTO>> repliesByBoardNum = reply_list.stream()
	            .collect(Collectors.groupingBy(M_ReplyDTO::getBoardnum));
	        
	    model.addAttribute("repliesByBoardNum", repliesByBoardNum);

		return "/mboard/m_get";
	}
	
	@GetMapping("m_remove")
	public String m_remove(int mboardnum, HttpServletResponse resp) {
		M_BoardDTO mboard = service.getDetail(mboardnum);
		String oldDate = mboard.getSetdate();
		
		// 댓글이 있다면 댓글 먼저 삭제
		List<M_ReplyDTO> reply_list = re_service.get_reply_list();		
		if (reply_list != null && !reply_list.isEmpty()) {
		    for (M_ReplyDTO reply : reply_list) {
		    	// 이 게시물에 댓글이 있으면
		        if(reply.getBoardnum()==mboardnum) {
		        	re_service.delete_reply(reply);
		        }
		    }
		}		
		
		if(service.remove(mboardnum)) {
			// 유저의 스케줄도 삭제
			service.updateUser_remove(mboard, oldDate);
			
			// d, n 은 삭제 알럿 쿠키
			Cookie cookie = new Cookie("d", "n");
			cookie.setPath("/");
			cookie.setMaxAge(5);
			resp.addCookie(cookie);
			return "redirect:/mboard/m_board";
		}
		return "redirect:/mboard/m_board";
		
	}
	
	@GetMapping("m_modify")
	public void m_modify(int mboardnum, Model model) {
		model.addAttribute("mboard",service.getDetail(mboardnum));
	}
	
	@PostMapping("m_modify")
	public String modify(M_BoardDTO mboard, String oldDate, HttpServletResponse resp) {
		mboard.make_dDay(); 
		if(service.modify(mboard)) {
			// 유저의 스케줄도 수정
			service.updateUser_modify(mboard, oldDate);
			
			// s, y 는 수정 알럿 쿠키
			Cookie cookie = new Cookie("s", "y");
			cookie.setPath("/");
			cookie.setMaxAge(5);
			resp.addCookie(cookie);
		}
		return "redirect:/mboard/m_get?mboardnum="+mboard.getBoardnum();
	}
	
	@GetMapping("get_members")
	@ResponseBody
	public String get_members(@RequestParam("boardnum") int mboardnum) {
	    String membersstr = service.getMembers(mboardnum);
	    if (membersstr == null) {
	        return "[]"; // 빈 배열 반환
	    }

	    // List<String>을 생성하고 빈 문자열 제거
	    List<String> members = Arrays.stream(membersstr.split("\\\\"))
	                                 .filter(member -> !member.isEmpty()) // 빈 문자열 제거
	                                 .collect(Collectors.toList());

	    // List<String>을 JSON 문자열로 변환
	    ObjectMapper objectMapper = new ObjectMapper();
	    try {
	        return objectMapper.writeValueAsString(members);
	    } catch (JsonProcessingException e) {
	        return "[]"; // 빈 배열 반환
	    }
	}
	
	@PostMapping("put_member")
	@ResponseBody
	public void put_member(@RequestBody Map<String, Object> memInfo) {
		// memInfo에서 member랑 boardnum문자열 추출
		String member = (String) memInfo.get("member");
	    String boardnumStr = (String)memInfo.get("boardnum"); // 문자열 숫자로
	    int boardnum = Integer.parseInt(boardnumStr);
	    
		M_BoardDTO mboard = new M_BoardDTO();
		mboard.setMember(member);
		mboard.setBoardnum(boardnum);
		service.updateMember(mboard);
	}
	
	@GetMapping("get_my_schedule")
	@ResponseBody
	public List<M_BoardDTO> getMySchedule(@RequestParam String userid) {
		List<M_BoardDTO> my_list = service.getMyList(userid);
	    return my_list;
	}
	
	@PostMapping("put_reply")
	public ResponseEntity<String> put_reply(@RequestBody M_ReplyDTO replyDTO) {
		if(re_service.put_reply(replyDTO)) {
			return ResponseEntity.ok("댓글 추가 완료!");
	    } else {
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("댓글 추가 실패");
	    }
	}
	
	@PostMapping("modify_reply")
	public ResponseEntity<String> modify_reply(@RequestBody M_ReplyDTO replyDTO) {
		if(re_service.modify_reply(replyDTO)) {
			return ResponseEntity.ok("댓글 수정 완료!");
		}
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("댓글 수정 실패");
	}
	
	@PostMapping("delete_reply")
	public ResponseEntity<String> delete_reply(@RequestBody M_ReplyDTO replyDTO) {
		if(re_service.delete_reply(replyDTO)) {
			return ResponseEntity.ok("댓글 삭제 완료!");
	    } else {
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("댓글 삭제 실패");
	    }
	}
	
	@GetMapping("m_board_search")
	public void m_board_search(
			@RequestParam("type") String type,
			@RequestParam("keyword") String keyword,
	        Model model,
	        HttpSession session) {
		
		List<M_BoardDTO> searchResults = service.searchBoards(type, keyword);
	    model.addAttribute("m_list", searchResults);	
		String loginUser = (String) session.getAttribute("loginUser");
		
		// 이 달의 게시글만 추가해주기
		List<M_BoardDTO> this_m_list = service.this_m_list();
		model.addAttribute("this_m_list", this_m_list);
		// 이 달의 게시글의 인원수 추가해주기
		List<Integer> member_cnt = service.this_m_list_memNum(this_m_list);
		model.addAttribute("member_cnt", member_cnt);
		// 나의 스케줄 받아오기
		List<M_BoardDTO> my_list = service.getMyList(loginUser);
		model.addAttribute("my_list", my_list);		
	}

}
