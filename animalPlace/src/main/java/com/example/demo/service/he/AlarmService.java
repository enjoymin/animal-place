package com.example.demo.service.he;

import java.util.List;

import com.example.demo.domain.he.AlarmDTO;

public interface AlarmService {
	List<AlarmDTO> getAlarm(String userid);
	boolean extAlarm(String userid);
	boolean deleteAlarm(long num);
}
