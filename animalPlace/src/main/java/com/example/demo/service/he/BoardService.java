package com.example.demo.service.he;

import java.util.List;

import com.example.demo.domain.he.MBoardDTO;

public interface BoardService {
	List<MBoardDTO> getList(String userid);
}
