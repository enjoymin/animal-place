package com.example.demo.service.he;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.domain.he.Criteria;
import com.example.demo.domain.he.MBoardDTO;
import com.example.demo.domain.he.NoteDTO;
import com.example.demo.mapper.he.MyMapper;
import com.example.demo.model.pboard.PFileDTO;

@Service
public class MyServiceImpl implements MyService {

	@Autowired
	private MyMapper mmapper;
	
	@Override
	public long getMBtotal(String userid) {
		long total = mmapper.getMBtotal(userid);
		return total;
	}

	@Override
	public List<MBoardDTO> getMBList(Criteria cri, String userid) {
		List<MBoardDTO> list = mmapper.getMBList(cri, userid);
		return list;
	}

	@Override
	public List<PFileDTO> getPFile(Criteria cri, String userid) {
		List<PFileDTO> list = mmapper.getPFileByUserid(cri, userid);
		return list;
	}

	@Override
	public long getPBtotal(String userid) {
		long total = mmapper.getPBtotal(userid);
		return total;
	}

	@Override
	public boolean insertNote(NoteDTO note) {
		return mmapper.insertNote(note);
	}
}
