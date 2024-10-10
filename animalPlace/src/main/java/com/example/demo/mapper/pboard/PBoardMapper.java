package com.example.demo.mapper.pboard;

import java.util.ArrayList;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.example.demo.model.pboard.PBoardDTO;

@Mapper
public interface PBoardMapper {
	ArrayList<PBoardDTO> getList(@Param("lastBoardnum") Long lastBoardnum, @Param("limit") int limit);
	Long getBoardnum();
	boolean insertBoard(PBoardDTO pboard);
	PBoardDTO getBoardByBoardnum(Long boardnum);
	boolean updateBoard(PBoardDTO updateBoard);
	Long getNextBoardnum(Long lastBoardnum);
	boolean delete(Long boardnum);
}
