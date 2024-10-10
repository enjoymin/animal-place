package com.example.demo.mapper.mboard;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.example.demo.domain.mboard.M_ReplyDTO;

@Mapper
public interface M_ReplyMapper {

	int put_reply(M_ReplyDTO replyDTO);

	List<M_ReplyDTO> get_reply_list();

	int delete_reply(M_ReplyDTO replyDTO);

	int modify_reply(M_ReplyDTO replyDTO);
	
}
