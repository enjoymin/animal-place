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
	public long insertNote(NoteDTO note) {
		mmapper.insertNote(note);
		long noteNum = mmapper.getnoteNum(note.getReceiveuser());
		return noteNum;
	}

	@Override
	public List<NoteDTO> getNote(String receiveuser,Criteria cri) {
		List<NoteDTO> list =  mmapper.getNote(receiveuser,cri);
		return list;
	}

	@Override
	public long getNtotal(String user) {
		long total = mmapper.getNtotal(user);
		return total;
	}

	@Override
	public NoteDTO getNoteCT(long noteNum) {
		NoteDTO note = mmapper.getNoteCT(noteNum);
		return note;
	}

	@Override
	public boolean updateNote(long noteNum) {
		return mmapper.updateNote(noteNum);
	}

	@Override
	public boolean delelteNote(long noteNum) {
		
		return mmapper.deleteNote(noteNum);
	}
}
