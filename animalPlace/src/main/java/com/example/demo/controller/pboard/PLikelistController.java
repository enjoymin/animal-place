package com.example.demo.controller.pboard;


import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;


import com.example.demo.model.pboard.PLikelistDTO;
import com.example.demo.service.pboard.PBoardService;
import com.example.demo.service.pboard.PLikelistService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/plikelist/*")
public class PLikelistController {

    @Autowired
    private PLikelistService plservice;
    
    @Autowired
    private PBoardService pbservice;

    @GetMapping("checklike")
    public ResponseEntity<PLikelistDTO> checkLike(@RequestParam Long boardnum, HttpServletRequest req) {
        HttpSession session = req.getSession();
        String loginUser = (String) session.getAttribute("loginUser");
        PLikelistDTO result = plservice.checklike(boardnum, loginUser);
        
        if (result == null) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null); // 204 No Content
        }
        return ResponseEntity.ok(result);
    }

    @PostMapping("insert")
    public ResponseEntity<Map<String, Object>> insert(@RequestParam Long boardnum, HttpServletRequest req) {
        HttpSession session = req.getSession();
        String loginUser = (String) session.getAttribute("loginUser");
        boolean success = plservice.insertLike(boardnum, loginUser);
        Map<String, Object> result = new HashMap<>();
        result.put("alarm", pbservice.getBoardByBoardnum(boardnum));
        return success ? ResponseEntity.ok(result) : ResponseEntity.status(400).body(Map.of("message", "좋아요 실패"));
    }

    @GetMapping("delete")
    public ResponseEntity<String> delete(@RequestParam Long boardnum, HttpServletRequest req) {

        HttpSession session = req.getSession();
        String loginUser = (String) session.getAttribute("loginUser");
        boolean success = plservice.deleteLike(boardnum, loginUser);
        return success ? ResponseEntity.ok("Like removed") : ResponseEntity.status(400).body("Failed to remove like");
    }
    
}
