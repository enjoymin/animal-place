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
        return "board/index"; 
    }
    // 기본 페이지를 보여주는 메소드
    @GetMapping("disease")
    public String showDiseasePage() {
        return "disease/board/disease"; 
    }

  
    @GetMapping("disease/{animalName}/{bodyPart}")
    @ResponseBody
    public List<DiseaseDTO> getDiseasesByAnimalAndBodyPart(@PathVariable String animalName, @PathVariable String bodyPart) {
        return diseaseService.getDiseasesByAnimalAndBodyPart(animalName, bodyPart); 
    }
}
