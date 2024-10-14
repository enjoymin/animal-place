package com.example.demo.mapper.adoption;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.example.demo.model.adoption.AdoptionFileDTO;

@Mapper
public interface AdoptionFileMapper {
	int insertFile(AdoptionFileDTO fdto);

	List<AdoptionFileDTO> getFiles(long adoptionnum);

	int deleteFileBySystemname(String systemname);

	AdoptionFileDTO getOneFileByAdoptionnum(long adoptionnum);

}
