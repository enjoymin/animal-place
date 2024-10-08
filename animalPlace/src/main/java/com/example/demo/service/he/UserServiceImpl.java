package com.example.demo.service.he;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.domain.he.ProfileDTO;
import com.example.demo.domain.he.UserDTO;
import com.example.demo.mapper.he.FileMapper;
import com.example.demo.mapper.he.UserMapper;

@Service
public class UserServiceImpl implements UserService {

	@Value("${file.dir}")
	private String saveFolder;

	@Autowired
	private UserMapper umapper;

	@Autowired
	private FileMapper fmapper;

	@Override
	public boolean join(UserDTO user) {
		return umapper.insertUser(user) == 1;
	}

	@Override
	public boolean login(String userid, String userpw) {
		UserDTO user = umapper.getUserByUserid(userid);
		if (user != null) {
			if (user.getUserpw().equals(userpw)) {
				return true;
			}
		}
		return false;
	}

	@Override
	public UserDTO getUser(String userid) {
		UserDTO user = umapper.getUserByUserid(userid);
		return user;
	}

	@Override
	public boolean updateUser(UserDTO user, MultipartFile file) {
		if (file == null || file.isEmpty()) {
			System.out.println("true");
			return umapper.updateUser(user) == 1;
		} else {
			if (fmapper.getProfile(user.getUserid()) != null) {
				ProfileDTO oldprofile = fmapper.getProfile(user.getUserid());
				File oldfile = new File(saveFolder, oldprofile.getSystemname());
				oldfile.delete();
				fmapper.deleteProfile(user.getUserid());
			}
			
			int lastIdx = file.getOriginalFilename().lastIndexOf(".");
			String ext = file.getOriginalFilename().substring(lastIdx);

			LocalDateTime now = LocalDateTime.now();
			String time = now.format(DateTimeFormatter.ofPattern("yyyyMMddHHmmssSSS"));
			String systemname = time + UUID.randomUUID().toString() + ext;
			String path = saveFolder + systemname;

			ProfileDTO pdto = new ProfileDTO();
			pdto.setSystemname(systemname);
			pdto.setUserid(user.getUserid());
			fmapper.updateProfile(pdto);

			try {
				file.transferTo(new File(path));
			} catch (IllegalStateException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			return umapper.updateUser(user) == 1;
		}
	}

	@Override
	public ProfileDTO getprofile(String userid) {
		return fmapper.getProfile(userid);
	}

}
