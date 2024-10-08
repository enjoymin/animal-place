package com.example.demo.service.mboard;

import com.example.demo.domain.mboard.UserDTO;


public interface UserService {
	boolean login(String userid, String userpw);
	
	// 참가하기 로직들
	
	boolean update_schedule(UserDTO user);

	UserDTO getUserSchedule(String userid);

}
