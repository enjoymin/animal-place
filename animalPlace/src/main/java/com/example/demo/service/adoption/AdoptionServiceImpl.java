package com.example.demo.service.adoption;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.mapper.adoption.AdoptionMapper;
import com.example.demo.model.adoption.AdoptionDTO;

@Service
public class AdoptionServiceImpl implements AdoptionService {
	
	@Autowired
	private AdoptionMapper amapper;
	
	@Override
	public boolean regist(AdoptionDTO adoption) {
		if(amapper.insertAdoption(adoption) != 1) {
			return false;
		}
		return true;
	}


    
}
