package com.example.demo.mapper.pboard;

import java.util.ArrayList;

import org.apache.ibatis.annotations.Mapper;

import com.example.demo.model.pboard.PBoardDTO;
import com.example.demo.model.pboard.PFileDTO;

@Mapper
public interface PFileMapper {
	int insertFile(PFileDTO pfdto);

	ArrayList<String> getFilesByBoardnum(Long boardnum);

	ArrayList<String> getFileByBoardnum(ArrayList<PBoardDTO> list);

	boolean deleteFile(Long boardnum, String systemname);

	String getImg(long boardnum);

	ArrayList<String> getBestImage();
}
