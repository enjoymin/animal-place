package com.example.demo.mapper.disease;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.example.demo.model.disease.DiseaseDTO;

@Mapper
public interface DiseaseMapper {
	List<DiseaseDTO> getDiseasesByAnimalAndBodyPart(@Param("animalName") String animalName, @Param("bodyPart") String bodyPart);
}
