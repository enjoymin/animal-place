package com.example.demo.service.adoption;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.mapper.adoption.AdoptionMapper;
import com.example.demo.model.adoption.AdoptionCriteria;
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

	@Override
	public List<AdoptionDTO> getList(AdoptionCriteria adCri) {
		List<AdoptionDTO> list = amapper.getList(adCri);
		return list;
	}

	@Override
	public long getTotal(AdoptionCriteria adCri) {
		return amapper.getTotal(adCri);
	}

	@Override
	public AdoptionDTO getDetail(long adoptionnum) {
		return amapper.getAdoptionByAdoptinnum(adoptionnum);
	}


    
}
