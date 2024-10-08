package com.example.demo.mapper.mboard;

import org.apache.ibatis.annotations.Mapper;

import com.example.demo.domain.mboard.UserDTO;

@Mapper
public interface UserMapper {

	UserDTO getUserByUserid(String userid);

	boolean updateUser(UserDTO user);

	boolean update_schedule(UserDTO user);
}








