package com.example.demo.mapper.he;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.example.demo.domain.he.Criteria;
import com.example.demo.domain.he.MBoardDTO;
import com.example.demo.domain.he.NoteDTO;
import com.example.demo.model.pboard.PFileDTO;

@Mapper
public interface MyMapper {
	long getMBtotal(String userid);
	List<MBoardDTO> getMBList(Criteria cri, String userid);
	List<PFileDTO> getPFileByUserid(Criteria cri, String userid);
	long getPBtotal(String userid);
	boolean insertNote(NoteDTO note);
	List<NoteDTO> getNote(String receiveuser, Criteria cri);
	long getNtotal(String user);
	NoteDTO getNoteCT(long noteNum);
}
