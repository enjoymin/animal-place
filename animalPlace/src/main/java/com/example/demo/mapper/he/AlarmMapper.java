package com.example.demo.mapper.he;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.example.demo.domain.he.AlarmDTO;

@Mapper
public interface AlarmMapper {
	List<AlarmDTO> getAlarm(String userid);
	int deleteAlarm(long num);
}
