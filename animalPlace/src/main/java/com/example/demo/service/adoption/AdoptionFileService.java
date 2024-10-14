package com.example.demo.service.adoption;

import java.util.HashMap;

import com.example.demo.model.adoption.AdoptionFileDTO;

public interface AdoptionFileService {
	HashMap<String, Object> getTumbnailResource(String systemname) throws Exception;

	AdoptionFileDTO getOneFileByAdoptionnum(long adoptionnum);
}
