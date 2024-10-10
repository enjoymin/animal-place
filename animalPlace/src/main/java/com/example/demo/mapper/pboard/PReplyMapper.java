package com.example.demo.mapper.pboard;

import java.util.ArrayList;

import org.apache.ibatis.annotations.Mapper;

import com.example.demo.model.pboard.PReplyDTO;

@Mapper
public interface PReplyMapper {

	ArrayList<PReplyDTO> getReply(Long boardnum);

	ArrayList<PReplyDTO> getMoreReply(Long boardnum, Long replynum);

	boolean modifyReply(PReplyDTO replydto);

	boolean removeReply(Long replynum);

	boolean registReply(String replycontent, Long boardnum, String replyuserid);

	PReplyDTO getFirstReply(Long boardnum, String replyuserid);
}

