package com.example.demo.model.adoption;

import lombok.Data;

@Data
public class AdoptionDTO {
	
	private long adoptionnum;
	private String title;
	private String contents;
	private String breed;
	private String type;
	private String region;
	private String gender;
	private String age;
	private String regdate;
	private String cost;
	private String adoptionOk;
	private String userid;
	
}
