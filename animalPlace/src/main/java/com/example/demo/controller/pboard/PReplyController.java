package com.example.demo.controller.pboard;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.demo.model.pboard.PReplyDTO;
import com.example.demo.service.he.AlarmService;
import com.example.demo.service.pboard.PReplyService;

@Controller
@RequestMapping("/preply/*")
public class PReplyController {

	@Autowired
	private PReplyService prservice;
	
	@Autowired
	private AlarmService alservice;

	@GetMapping("loadMore")
	@ResponseBody
	public List<PReplyDTO> loadMoreReply(@RequestParam(required = false) Long lastBoardnum,
			@RequestParam(required = false) Long replynum) {
		System.out.println(lastBoardnum + " : " + replynum);
		List<PReplyDTO> list = prservice.getMoreReply(lastBoardnum, replynum);
		System.out.println("댓글 list : " + list);
		return list;
	}

//	@PostMapping("regist")
//	public ResponseEntity<Map<String, PReplyDTO>> regist(@RequestBody Map<String, Object> hash) {
//		System.out.println("check");
//		
//		String replycontent = (String) hash.get("replycontent");
//		long boardnum = (long) hash.get("boardnum");
//		String replyuserid = (String) hash.get("replyuserid");
//
//		System.out.println(replycontent);
//		System.out.println(boardnum);
//		System.out.println(replyuserid);
//
//		Map<String, PReplyDTO> registReply = new HashMap<>();
//		System.out.println(replycontent + " " + boardnum + " " + replyuserid);
//		if (prservice.registReply(replycontent, boardnum, replyuserid)) {
//			PReplyDTO replydto = prservice.getFirstReply(boardnum, replyuserid);
//			if (replydto != null) {
//				registReply.put("reply", replydto);
//				return ResponseEntity.ok(registReply);
//			}
//		}
//		return ResponseEntity.ok(Map.of("message", null));
//
//		// 여유 될때 DTO로 넘겨서 되는지 수정
//	}
	@PostMapping("regist")
	public ResponseEntity<Map<String, PReplyDTO>> regist(@RequestParam String replycontent, @RequestParam Long boardnum,
			@RequestParam String replyuserid, @RequestParam String ctuserid, @RequestParam String boardtitle, @RequestParam String contentpath) {
		String flag = "reply";
		Map<String, PReplyDTO> registReply = new HashMap<>();
		System.out.println(replycontent + " " + boardnum + " " + replyuserid);
		if (prservice.registReply(replycontent, boardnum, replyuserid)) {
			PReplyDTO replydto = prservice.getFirstReply(boardnum, replyuserid);
			
			//알람 -한얼님

			if (replydto != null) {
				registReply.put("reply", replydto);
				if(!replyuserid.equals(ctuserid)) {
					System.out.println(replyuserid);
					System.out.println(ctuserid);
					alservice.insertAlarm(ctuserid, boardtitle, contentpath, flag);
				}
				return ResponseEntity.ok(registReply);
			}
		}
		
		return ResponseEntity.ok(Map.of("message", null));

		// 여유 될때 DTO로 넘겨서 되는지 수정
	}

	@PostMapping("modify")
	public ResponseEntity<Map<String, String>> modify(@RequestBody PReplyDTO replydto) {
		System.out.println("Received replydto: " + replydto);
		System.out.println("Reply Content: " + replydto.getReplycontent());
		Map<String, String> updateReply = new HashMap<>();
		if (prservice.modifyReply(replydto)) {
			updateReply.put("replycontent", replydto.getReplycontent());
			return ResponseEntity.ok(updateReply);
		}
		return ResponseEntity.ok(Map.of("message", "수정 실패"));
	}

	@PostMapping("remove")
	public ResponseEntity<Long> remove(@RequestParam Long replynum) {
		if (prservice.removeReply(replynum)) {
			return ResponseEntity.ok(replynum);
		}
		return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
	}
}
