package com.example.demo.service.he;

import java.util.List;

import com.example.demo.domain.he.Criteria;
import com.example.demo.domain.he.MBoardDTO;
import com.example.demo.model.pboard.PFileDTO;

public interface MyService {
	long getMBtotal(String userid);
	List<MBoardDTO> getMBList(Criteria cri, String userid);
	long getPBtotal(String userid);
	List<PFileDTO> getPFile(Criteria cri, String userid);
}
