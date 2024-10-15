package com.example.demo.domain.he;

import lombok.Data;

@Data
public class NoteDTO {
	private int noteNum;
	private String senduser;
	private String receiveuser;
	private String title;
	private String contents;
	private String regdate;
	private int readcnt;
}
