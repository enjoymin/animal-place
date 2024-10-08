package com.example.demo.service.he;

import java.io.File;

import org.springframework.web.multipart.MultipartFile;

import com.example.demo.domain.he.ProfileDTO;
import com.example.demo.domain.he.UserDTO;

public interface UserService {
	boolean join(UserDTO user);
	boolean login(String userid, String userpw);
	UserDTO getUser(String userid);
	boolean updateUser(UserDTO user, MultipartFile file);
	ProfileDTO getprofile(String userid);
	UserDTO getUserSchedule(String userid);
	void update_schedule(UserDTO scheduleInfo);
}
