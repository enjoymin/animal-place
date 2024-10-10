package com.example.demo.mapper.he;

import org.apache.ibatis.annotations.Mapper;

import com.example.demo.domain.he.UserDTO;

@Mapper
public interface UserMapper {
	int insertUser(UserDTO user);
	UserDTO getUserByUserid(String userid);
	int updateUser(UserDTO user);
	boolean update_schedule(UserDTO user);
}
