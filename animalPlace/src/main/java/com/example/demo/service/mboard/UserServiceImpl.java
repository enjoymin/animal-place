package com.example.demo.service.mboard;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.domain.mboard.UserDTO;
import com.example.demo.mapper.mboard.UserMapper;

@Service
public class UserServiceImpl implements UserService{
	
	@Autowired
	private UserMapper umapper;

	@Override
	public boolean login(String userid, String userpw) {
		UserDTO user = umapper.getUserByUserid(userid);
		if(user != null) {
			if(user.getUserpw().equals(userpw)) {
				return true;
			}
		}
		return false;
	}
	
	// 참가하기
	
	@Override
	public boolean update_schedule(UserDTO user) {
		return umapper.update_schedule(user);		
	}

	@Override
	public UserDTO getUserSchedule(String userid) {
		return umapper.getUserByUserid(userid);
	}

}
