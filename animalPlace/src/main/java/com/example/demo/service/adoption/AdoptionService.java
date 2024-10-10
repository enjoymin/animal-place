package com.example.demo.service.adoption;

import java.util.List;

import com.example.demo.model.adoption.AdoptionCriteria;
import com.example.demo.model.adoption.AdoptionDTO;

public interface AdoptionService {
    
	boolean regist(AdoptionDTO adoption);
	List<AdoptionDTO> getList(AdoptionCriteria adCri);
	long getTotal(AdoptionCriteria adCri);
}
