package com.example.demo.service.he;

import java.util.List;

import com.example.demo.domain.he.Criteria;
import com.example.demo.domain.he.MBoardDTO;

public interface MyService {
	long getMBtotal(String userid);
	List<MBoardDTO> getMBList(Criteria cri, String userid);
}
