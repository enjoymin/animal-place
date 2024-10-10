package com.example.demo.domain.mboard;

import lombok.Data;

@Data
public class M_ReplyDTO {
	private long replynum;
	private String replycontent;
	private String replyuserid;
	private String replydatetime;
	private int boardnum;
	private String ctuserid;
	private String contentpath;
	private String boardtitle;
}
