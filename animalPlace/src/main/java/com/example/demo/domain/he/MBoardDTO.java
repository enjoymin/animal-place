package com.example.demo.domain.he;

import lombok.Data;

@Data
public class MBoardDTO {
	private int boardnum;
	private String boardtitle;
	private String boardcontent;
	private String userid;
	private String boarddatetime;
	private int mNum;
	private String mMember;
	private String setdate;
	private String place;
	private int boardflag;
}
