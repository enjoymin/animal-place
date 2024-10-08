package com.example.demo.domain.mboard;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

import lombok.Data;

@Data
public class M_BoardDTO {
	private int boardnum;
	private String boardtitle;
	private String userid;
	private String setdate;
	private int dDay;
	private String place;
	private int mnum;
	private String boardcontent;
	private String boarddatetime;
	private boolean boardflag;
	private String member;
	
    public void make_dDay() {
        LocalDate targetDate = LocalDate.parse(this.setdate);
        LocalDate today = LocalDate.now();
        this.dDay = (int) ChronoUnit.DAYS.between(today, targetDate);
        System.out.println("dDay: " + dDay); // 확인용 출력
    }
	
}
