package com.example.demo.model.pboard;

import lombok.Data;

@Data
public class PBoardDTO {
	private long boardnum;
	private String boardtitle;
	private String boardcontents;
	private long boardlikecnt;
	private String regdate;
	private String updatedate;
	private String userid;
	private String file;
}
