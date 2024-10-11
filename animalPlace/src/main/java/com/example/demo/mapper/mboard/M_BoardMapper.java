package com.example.demo.mapper.mboard;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.example.demo.domain.mboard.Criteria;
import com.example.demo.domain.mboard.M_BoardDTO;

@Mapper
public interface M_BoardMapper {	
	List<M_BoardDTO> getList();
	
	int insertBoard(M_BoardDTO board);	

	int getTotal();

	int getLastNum(String userid);
	
	M_BoardDTO getBoardByBoardNum(int boardnum);
	
	int updateBoard(M_BoardDTO board);
	
	int deleteBoard(int boardnum);

	String getMembers(int mboardnum);
	
	int updateMember(M_BoardDTO mboard);

	List<M_BoardDTO> getListByTitle(String keyword);

	List<M_BoardDTO> getListByContent(String keyword);

	List<M_BoardDTO> getListByWriter(String keyword);

	List<M_BoardDTO> getListBySetdate(String keyword);

	List<M_BoardDTO> getListByTitle_Content(String keyword);

	void update_readcount(int boardnum, int readcount);
}
