package com.example.demo.domain.he;

import lombok.Data;

@Data
public class AlarmDTO {
	private int alarmnum;
	private String userid;
	private String boardtitle;
	private String contentpath;
	private boolean reply;
	private boolean meeting;
}
