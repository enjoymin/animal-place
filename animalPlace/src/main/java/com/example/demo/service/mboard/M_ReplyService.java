package com.example.demo.service.mboard;

import java.util.List;

import com.example.demo.domain.mboard.M_ReplyDTO;

public interface M_ReplyService {

	boolean put_reply(M_ReplyDTO replyDTO);

	List<M_ReplyDTO> get_reply_list();

	boolean delete_reply(M_ReplyDTO replyDTO);

}
