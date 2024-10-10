package com.example.demo.mapper.he;

import org.apache.ibatis.annotations.Mapper;

import com.example.demo.domain.he.ProfileDTO;

@Mapper
public interface FileMapper {
	boolean updateProfile(ProfileDTO pdto);
	boolean deleteProfile(String userid);
	ProfileDTO getProfile(String userid);
}
