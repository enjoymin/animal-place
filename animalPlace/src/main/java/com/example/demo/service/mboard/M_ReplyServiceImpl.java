package com.example.demo.service.mboard;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.domain.mboard.M_ReplyDTO;
import com.example.demo.mapper.mboard.M_ReplyMapper;
import com.example.demo.mapper.he.UserMapper;

@Service
public class M_ReplyServiceImpl implements M_ReplyService{
	
	@Autowired
	private M_ReplyMapper rmapper;

	@Override
	public boolean put_reply(M_ReplyDTO replyDTO) {
		return rmapper.put_reply(replyDTO)==1;
	}

	@Override
	public List<M_ReplyDTO> get_reply_list() {
		return rmapper.get_reply_list();
	}

	@Override
	public boolean delete_reply(M_ReplyDTO replyDTO) {
		return rmapper.delete_reply(replyDTO)==1;
	}

	@Override
	public boolean modify_reply(M_ReplyDTO replyDTO) {
		return rmapper.modify_reply(replyDTO)==1;
	}

}
