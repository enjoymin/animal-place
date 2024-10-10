package com.example.demo.mapper.adoption;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.example.demo.model.adoption.AdoptionCriteria;
import com.example.demo.model.adoption.AdoptionDTO;

@Mapper
public interface AdoptionMapper {

	int insertAdoption(AdoptionDTO adoption);
	List<AdoptionDTO> getList(AdoptionCriteria adCri);
	long getTotal(AdoptionCriteria adCri);

}
