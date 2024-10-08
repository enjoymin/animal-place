package com.example.demo.controller.map;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.model.map.Criteria;
import com.example.demo.model.map.MapDTO;
import com.example.demo.model.map.MbPageDTO;
import com.example.demo.service.map.PostService;

import jakarta.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/map/*")
public class MapBoardController {


	@GetMapping(value = { "map", "maptest", "header", "footer" , "mblist"})
	public void replace() {

	}

	@Autowired
	private PostService postService;

	@PostMapping("/mbwrite")
	public ResponseEntity<String> savePost(@RequestParam("placeData") String placeData,
			@RequestParam("postTitle") String postTitle, @RequestParam("postContent") String postContent,
			@RequestParam("userid") String userid) {
		MapDTO postDTO = new MapDTO();
		postDTO.setPlaceData(placeData);
		postDTO.setBoardtitle(postTitle);
		postDTO.setBoardcontent(postContent);
		postDTO.setUserid(userid);

		postService.savePost(postDTO);
		return ResponseEntity.ok("게시글 저장 완료");

	}

	

	@GetMapping("posts")
	public void getAllPosts(Criteria cri, HttpServletRequest req, Model model) {
		List<MapDTO> posts = postService.getAllPost(cri);
		model.addAttribute("posts",posts);
		model.addAttribute("pageMaker", new MbPageDTO(postService.getTotalCount(cri), cri));
		System.out.println(new MbPageDTO(postService.getTotalCount(cri), cri));

	}
	/*
	 * @GetMapping("mblist") public void mblist(Criteria cri, HttpServletRequest
	 * req, Model model) { List<MapDTO> mblist = postService.getAllPost(cri);
	 * model.addAttribute("[posts",mblist); model.addAttribute("pageMaker", new
	 * MbPageDTO(postService.getTotalCount(cri), cri));
	 * 
	 * }
	 */
	 
	@PutMapping("/posts/{boardnum}")
	public ResponseEntity<String> updatePost(@RequestBody MapDTO post) {
		postService.updatePost(post);
		return ResponseEntity.ok("게시글 수정 완료");
	}

	@DeleteMapping("/posts/{boardnum}")
	public ResponseEntity<String> deletePost(@PathVariable Long boardnum) {
		postService.deletePost(boardnum);
		return ResponseEntity.ok("게시글 삭제 완료");
	}

}
