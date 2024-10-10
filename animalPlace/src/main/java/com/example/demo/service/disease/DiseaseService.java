package com.example.demo.service.disease;

import java.util.List;

import com.example.demo.model.disease.DiseaseDTO;

public interface DiseaseService {
    List<DiseaseDTO> getDiseasesByAnimalAndBodyPart(String animal, String bodyPart);

	/* List<DiseaseDTO> getDiseasesByBodyPart(String bodyPart); */
}
