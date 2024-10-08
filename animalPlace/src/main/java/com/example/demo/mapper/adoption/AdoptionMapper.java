package com.example.demo.mapper.adoption;

import org.apache.ibatis.annotations.Mapper;

import com.example.demo.model.adoption.AdoptionDTO;

@Mapper
public interface AdoptionMapper {

	int insertAdoption(AdoptionDTO adoption);

}
