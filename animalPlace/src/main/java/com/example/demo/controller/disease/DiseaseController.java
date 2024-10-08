package com.example.demo.controller.disease;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.demo.model.disease.DiseaseDTO;
import com.example.demo.service.disease.DiseaseService;

import java.util.List;

@Controller
@RequestMapping("/board/*")

public class DiseaseController {

    @Autowired
    private DiseaseService diseaseService;

    // 기본 페이지를 보여주는 메소드 (index.html)
    @GetMapping("/")
    public String showIndexPage() {
        return "board/index"; // src/main/resources/templates/board/index.html 파일을 렌더링
    }
    // 기본 페이지를 보여주는 메소드
    @GetMapping("disease")
    public String showDiseasePage() {
        return "disease/board/disease"; // src/main/resources/templates/disease.html 파일을 렌더링
    }

    // 동물 이름과 신체 부위에 따른 질병 정보를 반환하는 메소드
    @GetMapping("disease/{animalName}/{bodyPart}")
    @ResponseBody
    public List<DiseaseDTO> getDiseasesByAnimalAndBodyPart(@PathVariable String animalName, @PathVariable String bodyPart) {
        System.out.println("요청 들어옴 animal : "+animalName);
    	// 데이터베이스에서 질병 정보를 조회하는 로직
    	System.out.println("Animal: " + animalName + ", Body Part: " + bodyPart); // 로그 추가
    	System.out.println(diseaseService.getDiseasesByAnimalAndBodyPart(animalName, bodyPart));
        return diseaseService.getDiseasesByAnimalAndBodyPart(animalName, bodyPart); // DiseaseDTO를 반환해야 함
    }
}
