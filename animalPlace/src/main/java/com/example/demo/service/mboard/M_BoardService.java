package com.example.demo.service.mboard;

import java.util.List;

import com.example.demo.domain.mboard.M_BoardDTO;

public interface M_BoardService {

	// 글 긁어오기
	List<M_BoardDTO> getList();
	
	// 글 작성
	boolean regist(M_BoardDTO mboard);

	// 글 개수 받아오기
	int getTotal();

	// 글 마지막 번호 받아오기(글쓰기 후 이동시 필요)
	int getLastNum(String userid);

	// 글 전체 받아오기
	M_BoardDTO getDetail(int mboardnum);

	boolean remove(int mboardnum);

	boolean modify(M_BoardDTO mboard);

	String getMembers(int mboardnum);

	boolean updateMember(M_BoardDTO mboard);

	List<M_BoardDTO> this_m_list();

	List<Integer> this_m_list_memNum(List<M_BoardDTO> thisMonthList);

	List<M_BoardDTO> getMyList(String loginUser);

	boolean updateUser_modify(M_BoardDTO mboard, String oldDate);

	boolean updateUser_remove(M_BoardDTO mboard, String oldDate);

	List<M_BoardDTO> searchBoards(String type, String keyword);

	void increase_readcount(int mboardnum);

}
