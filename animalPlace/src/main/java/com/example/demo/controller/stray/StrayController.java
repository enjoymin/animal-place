package com.example.demo.controller.stray;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.net.URLEncoder;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/stray/*")
public class StrayController {
	final static String serviceKey = "pmF9%2BcvRwKXr%2B8J4llELzWtgxz8zqVTR7VWXuJiRRLMc7ifXlCqRf%2B%2BQeiVrQbNKMezOthWu%2FhmzU7bDNYmGKA%3D%3D";
	
	@GetMapping(value = {"list"})
	public void list() {}

	@GetMapping(value = "get", produces = "application/json;charset=utf-8")
	@ResponseBody
	public String get(int pageNo) throws Exception {
		String url = "http://apis.data.go.kr/1543061/abandonmentPublicSrvc/abandonmentPublic?serviceKey="+serviceKey;
		url += "&numOfRows=1000";
		url += "&_type=json";
		url += "&pageNo="+pageNo;
		
		//단순한 문자열로 정의한 url 변수를 자바에서 네트워킹 할 때 활용할 수 있는 객체로 변환
		URL requestURL = new URI(url).toURL();
		
		//목적지로 향하는 다리 건설
		HttpURLConnection conn = (HttpURLConnection)requestURL.openConnection();
		conn.setRequestMethod("GET");
		
		//conn 다리가 건설되어 있는 목적지로부터 데이터를 읽어오기 위한 IS
		InputStream is = conn.getInputStream();
		//열려있는 IS 통로를 통해 들어오는 데이터를 읽기 위한 리더기
		InputStreamReader isr = new InputStreamReader(is);
		//쉽게 활용하기 위해 BR로 변환
		BufferedReader br = new BufferedReader(isr);
		
		String result = "";
		String line = "";
		while(true) {
			line = br.readLine();
			if(line == null) { break; }
			result += line;
		}
		//System.out.println(result);
		return result;
	}
	
	
}














