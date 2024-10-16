package com.example.demo.service.disease;

import com.example.demo.mapper.disease.DiseaseMapper;
import com.example.demo.model.disease.DiseaseDTO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DiseaseServiceImpl implements DiseaseService {

    @Autowired
    private DiseaseMapper diseaseMapper;
    @Override
    public List<DiseaseDTO> getDiseasesByAnimalAndBodyPart(String animalName, String bodyPart) {
        return diseaseMapper.getDiseasesByAnimalAndBodyPart(animalName, bodyPart);
    }
}
