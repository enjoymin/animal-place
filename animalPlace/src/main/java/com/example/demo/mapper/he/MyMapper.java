package com.example.demo.mapper.he;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.example.demo.domain.he.Criteria;
import com.example.demo.domain.he.MBoardDTO;

@Mapper
public interface MyMapper {
	long getMBtotal(String userid);
	List<MBoardDTO> getMBList(Criteria cri, String userid);
}
