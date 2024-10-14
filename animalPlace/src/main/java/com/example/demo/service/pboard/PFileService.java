package com.example.demo.service.pboard;

import java.util.ArrayList;

import org.springframework.web.multipart.MultipartFile;

import com.example.demo.model.pboard.PBoardDTO;

public interface PFileService {

	ArrayList<String> getFilesByBoardnum(Long boardnum);

	ArrayList<String> getFileByBoardnum(ArrayList<PBoardDTO> list);

	boolean insert(Long boardnum, MultipartFile[] files) throws Exception;

	boolean delete(Long boardnum, String[] delete);
	
	ArrayList<String> getBestImage();
}
