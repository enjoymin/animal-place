package com.example.demo.controller.stray;

import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.demo.model.adoption.AdoptionFileDTO;
import com.example.demo.service.adoption.AdoptionFileService;

@RequestMapping("/adfile/*")
@Controller
public class AdoptionFileController {
	@Autowired
	private AdoptionFileService service;
	
	@GetMapping("thumbnail")
	public ResponseEntity<Resource> thumbnail(String systemname) throws Exception{
		HashMap<String, Object> datas = service.getTumbnailResource(systemname);
		String contentType = (String)datas.get("contentType");
		Resource resource = (Resource)datas.get("resource");
		//응답 헤더 생성
		HttpHeaders headers = new HttpHeaders();
		headers.add(HttpHeaders.CONTENT_TYPE, contentType);
		return new ResponseEntity<Resource>(resource,headers,HttpStatus.OK);
	}
	
	@GetMapping("onethumbnail")
	public ResponseEntity<Resource> onethumbnail(long adoptionnum) throws Exception{
		AdoptionFileDTO file = service.getOneFileByAdoptionnum(adoptionnum);
		HashMap<String, Object> datas = service.getTumbnailResource(file.getSystemname());
		String contentType = (String)datas.get("contentType");
		Resource resource = (Resource)datas.get("resource");
		//응답 헤더 생성
		HttpHeaders headers = new HttpHeaders();
		headers.add(HttpHeaders.CONTENT_TYPE, contentType);
		return new ResponseEntity<Resource>(resource,headers,HttpStatus.OK);
	}
}
