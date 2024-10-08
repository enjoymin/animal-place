package com.example.demo.domain.mboard;

import lombok.Data;

@Data
public class UserDTO {
	private String userid;
	private String userpw;
	private String username;
	private String userphone;
	private String useremail;
	private String useraddr;
	private boolean userpet;
	private String schedule;
}
