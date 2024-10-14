package com.example.demo.service.mboard;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.domain.mboard.Criteria;
import com.example.demo.domain.mboard.M_BoardDTO;
import com.example.demo.domain.he.UserDTO;
import com.example.demo.mapper.mboard.M_BoardMapper;
import com.example.demo.mapper.he.UserMapper;

@Service
public class M_BoardServiceImpl  implements M_BoardService{

	@Autowired
	private M_BoardMapper bmapper;
	
	@Autowired
	private UserMapper umapper;
	
	@Override
	public List<M_BoardDTO> getList() {
		return bmapper.getList();
	}
	
	@Override
	public List<M_BoardDTO> getList(Criteria cri) {
		return bmapper.getList(cri);
	}

	@Override
	public boolean regist(M_BoardDTO mboard) {
		return bmapper.insertBoard(mboard)==1;
	}

	@Override
	public int getTotal() {
		return bmapper.getTotal();
	}

	@Override
	public int getLastNum(String userid) {
		return bmapper.getLastNum(userid);
	}

	@Override
	public M_BoardDTO getDetail(int mboardnum) {
		return bmapper.getBoardByBoardNum(mboardnum);
	}

	@Override
	public boolean remove(int mboardnum) {
		return bmapper.deleteBoard(mboardnum)==1;
	}

	@Override
	public boolean modify(M_BoardDTO mboard) {
		return bmapper.updateBoard(mboard)==1;
	}

	@Override
	public String getMembers(int mboardnum) {
		return bmapper.getMembers(mboardnum);
	}
	
	@Override
	public boolean updateMember(M_BoardDTO mboard) {
		return bmapper.updateMember(mboard)==1;
	}

	@Override
	public List<M_BoardDTO> this_m_list() {
		List<M_BoardDTO> allList = bmapper.getList();
		List<M_BoardDTO> this_m_list = new ArrayList<>();
		
		LocalDate today = LocalDate.now();
		int this_year = today.getYear();
		int this_month = today.getMonthValue();
		
		for(M_BoardDTO board : allList) {
			LocalDate b_today = LocalDate.parse(board.getSetdate());
			if(b_today.getYear()==this_year && b_today.getMonthValue()==this_month) {
				this_m_list.add(board);
			}
		}
		return this_m_list;
	}

	@Override
	public List<Integer> this_m_list_memNum(List<M_BoardDTO> thisMonthList) {
		List<Integer> memberCounts = new ArrayList<>();
		
		for(M_BoardDTO board : thisMonthList) {
			String memberStr = bmapper.getMembers(board.getBoardnum());
			// 멤버에 공백을 제거한 값이 아무것도 없을때도 추가해주어야함
			// 그러지 않으면 1로 반환되기 때문에
			if (memberStr != null && !memberStr.trim().isEmpty()) {
				// 역슬래시 떼고 개수 세기
	            int memberCount = memberStr.split("\\\\").length;
	            memberCounts.add(memberCount);
	        } else {
	        	// 멤버가 아무도 없으면 0 추가
	            memberCounts.add(0);
	        }
		}
		return memberCounts;
	}

	@Override
	public List<M_BoardDTO> getMyList(String loginUser) {
	    List<M_BoardDTO> allList = bmapper.getList();
	    List<M_BoardDTO> getMyList = new ArrayList<>();

	    for (M_BoardDTO board : allList) {
	        String membersStr = board.getMember();
	        if (membersStr != null && !membersStr.trim().isEmpty()) { // Null 및 빈 문자열 체크
	            String[] members = membersStr.split("\\\\"); // membersStr로 수정
	            for (String mems : members) {
	                if (mems.equals(loginUser)) {
	                    getMyList.add(board);
	                    break; // 해당 사용자가 이미 추가됐으니 다음 보드로 넘어감
	                }
	            }
	        }
	    }
	    return getMyList;
	}

	@Override
	public boolean updateUser_modify(M_BoardDTO mboard, String oldDate) {
		String membersStr = mboard.getMember();
		
		// 유저 테이블을 수정하기 위한 유저아이디 받아오고 유저의 스케줄을 받아오기
		if (membersStr != null && !membersStr.trim().isEmpty()) { // Null 및 빈 문자열 체크
            String[] members = membersStr.split("\\\\"); // membersStr로 수정
            for (String mems : members) {
            	// 받아온 멤버들을 하나씩 유저DTO에 넣어서 개인의 정보 가져오기
            	UserDTO user = umapper.getUserByUserid(mems);        
                // 해당 유저의 스케줄 문자열 가져오기
                String pre_schedules = user.getSchedule();
                
                // 새로 만들어줄 유저의 스케줄
                StringBuilder new_schedules = new StringBuilder();
                
                String[] scheduleArray = pre_schedules.split("\\\\");
                for(int i=0; i<scheduleArray.length; i++) {
                	String pre_schedule = scheduleArray[i].trim();
                	// 날짜를 분리해서 받아오고, 해당 날짜가 변경 전 날짜랑 같다==바꿔야할 날짜다
                	if(pre_schedule.trim().equals(oldDate.trim())) {
                		// 현재 받아온 날짜로 바꿔서 추가해
                		pre_schedule = mboard.getSetdate();
                	}
                	
                	// 다를경우 그냥 추가해
                	new_schedules.append(pre_schedule);
                	
                	// 마지막 요소가 아니라면 \\빼고 추가해
                	if (i < scheduleArray.length - 1) {
                		new_schedules.append("\\");
                    }
                }                
                user.setSchedule(new_schedules.toString());
                umapper.update_schedule(user);
            }
        }
		
		System.out.println("성공적으로 유저 스케줄 변경!");
		return true;		
	}

	@Override
	public boolean updateUser_remove(M_BoardDTO mboard, String oldDate) {
		String membersStr = mboard.getMember();
		
		// 유저 테이블을 수정하기 위한 유저아이디 받아오고 유저의 스케줄을 받아오기
		if (membersStr != null && !membersStr.trim().isEmpty()) { // Null 및 빈 문자열 체크
            String[] members = membersStr.split("\\\\"); // membersStr로 수정
            for (String mems : members) {
            	// 받아온 멤버들을 하나씩 유저DTO에 넣어서 개인의 정보 가져오기
            	UserDTO user = umapper.getUserByUserid(mems);        
                // 해당 유저의 스케줄 문자열 가져오기
                String pre_schedules = user.getSchedule();
                
                // 새로 만들어줄 유저의 스케줄
                StringBuilder new_schedules = new StringBuilder();
                
                String[] scheduleArray = pre_schedules.split("\\\\");
                for(int i=0; i<scheduleArray.length; i++) {
                	String pre_schedule = scheduleArray[i].trim();
                	// 날짜를 분리해서 받아오고, 해당 날짜가 변경 전 날짜랑 같지 않다==추가해야할 날짜다
                	if(!(pre_schedule.trim().equals(oldDate.trim()))) {
                		new_schedules.append(pre_schedule);        	
                	
                	// 마지막 요소가 아니라면 \\빼고 추가해
                	if (i < scheduleArray.length - 1) {
                		 new_schedules.append("\\");
                    }
                	}    	
                }                
                user.setSchedule(new_schedules.toString());
                umapper.update_schedule(user);
            }
        }
		
		System.out.println("성공적으로 유저 스케줄 삭제!");
		return true;
	}

	@Override
	public void increase_readcount(int mboardnum) {
		M_BoardDTO mboard = bmapper.getBoardByBoardNum(mboardnum);
		bmapper.update_readcount(mboardnum, mboard.getReadcount()+1);
	}

	@Override
	public List<M_BoardDTO> getListByViewType(String view_type) {
	    List<M_BoardDTO> boardList = bmapper.getListByViewType(view_type);
	    return boardList;
	}

}
