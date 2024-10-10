package com.example.demo.mapper.he;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.example.demo.domain.he.AlarmDTO;

@Mapper
public interface AlarmMapper {
	List<AlarmDTO> getAlarm(String userid);
	int deleteAlarm(long num);
	void insertAlarmReply(String userid, String boardtitle, String contentpath, String flag);
	void insertAlarmAmeeting(String userid, String boardtitle, String contentpath);
	void insertAlarmDmeeting(String userid, String boardtitle, String contentpath);
	int deleteAlarmByPath(String path);
}
