package com.example.demo.service.adoption;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.example.demo.domain.he.UserDTO;
import com.example.demo.model.adoption.AdoptionCriteria;
import com.example.demo.model.adoption.AdoptionDTO;
import com.example.demo.model.adoption.AdoptionFileDTO;

public interface AdoptionService {
    
	boolean regist(AdoptionDTO adoption, MultipartFile[] files) throws Exception;
	List<AdoptionDTO> getList(AdoptionCriteria adCri);
	long getTotal(AdoptionCriteria adCri);
	AdoptionDTO getDetail(long adoptionnum);
	UserDTO getUserDetail(String userid);
	boolean remove(long adoptionnum);
	boolean modify(AdoptionDTO adoption, MultipartFile[] files, String updateCnt) throws Exception;
	long getLastNum(String userid);
	List<AdoptionFileDTO> getFiles(long adoptionnum);
}
