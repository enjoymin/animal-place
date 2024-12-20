package com.example.demo.service.he;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.domain.he.AlarmDTO;
import com.example.demo.mapper.he.AlarmMapper;

@Service
public class AlarmServiceImpl implements AlarmService {
	@Autowired
	private AlarmMapper amapper;
	
	@Override
	public List<AlarmDTO> getAlarm(String userid) {
		return amapper.getAlarm(userid);
	}

	@Override
	public boolean extAlarm(String userid) {
		if(amapper.getAlarm(userid).size() != 0) {
			return true;
		}
		else {
			return false;
		}
	}

	@Override
	public boolean deleteAlarm(long num) {
		if(amapper.deleteAlarm(num) !=0) {
			return true;
		}
		else {
			return false;
		}
	}

	@Override
	public void insertAlarm(String userid, String boardtitle, String contentpath, String flag) {
		if(flag.equals("reply")) {
			amapper.insertAlarmReply(userid, boardtitle, contentpath, flag);
		}
		else if(flag.equals("ameeting")) {
			amapper.insertAlarmAmeeting(userid, boardtitle, contentpath);
		}
		else if(flag.equals("dmeeting")) {
			amapper.insertAlarmDmeeting(userid, boardtitle, contentpath);
		}
		else if(flag.equals("plike")) {
			amapper.insertAlarmPlike(userid, boardtitle, contentpath);
		}
		else if(flag.equals("note")) {
			amapper.insertAlarmNote(userid, boardtitle, contentpath);
		}
	}

	@Override
	public boolean deleteAlarmByPath(String userid, String path) {
		if(amapper.deleteAlarmByPath(userid, path) !=0) {
			return true;
		}
		else {
			return false;
		}
	}
}
