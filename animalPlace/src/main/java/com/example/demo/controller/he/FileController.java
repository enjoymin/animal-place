package com.example.demo.controller.he;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.demo.service.he.FileService;

@Controller
@RequestMapping("/file/*")
public class FileController {
	
	@Autowired
	private FileService service;
	
	@GetMapping("thumbnail")
	public ResponseEntity<Resource> tumbnail(String systemname){
		HashMap<String, Object> datas = service.getTumbnailResource(systemname);
		String contentType = (String)datas.get("contentType");
		Resource resource = (Resource) datas.get("resource");
		
		HttpHeaders headers = new HttpHeaders();
		headers.add(HttpHeaders.CONTENT_TYPE, contentType);
		return new ResponseEntity<Resource>(resource, headers, HttpStatus.OK);
	}
}
