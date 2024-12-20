package com.example.demo.service.pboard;

import java.util.ArrayList;

import org.springframework.web.multipart.MultipartFile;

import com.example.demo.model.pboard.PBoardDTO;

public interface PBoardService {
	ArrayList<PBoardDTO> getList(Long lastBoardnum, int limit);
	Long getStartnum();
	boolean regist(PBoardDTO pboard,MultipartFile[] files) throws Exception;
	PBoardDTO getBoardByBoardnum(Long boardnum);
	boolean modify(PBoardDTO updateBoard);
	Long getNextBoardnum(Long lastBoardnum);
	boolean remove(Long boardnum, String[] files) throws Exception;
}
