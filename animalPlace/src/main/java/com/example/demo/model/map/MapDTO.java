package com.example.demo.model.map;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class MapDTO {
	private Long boardnum;
	private String boardtitle;
	private String boardcontent;
	private String placeData;
	private LocalDateTime regdate;
	private LocalDateTime updatedate;
	private int readcount;
	private String userid;
	
	public MapDTO() {}
	
	public MapDTO(Long boardnum, String boardtitle, String boardcontent, String placeData, String userid) {
		this.boardnum = boardnum;
		this.boardtitle = boardtitle;
		this.boardcontent = boardcontent;
		this.placeData = placeData;
		this.userid = userid;
	}
}
