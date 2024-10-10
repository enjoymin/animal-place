package com.example.demo.model.adoption;

import org.springframework.web.util.UriComponentsBuilder;

import lombok.Data;


@Data
public class AdoptionCriteria {
	private int pageNum; // 현재 페이지 번호
    private int pageSize;   // 한 페이지에 표시할 항목 수
    private String breed;
    private String type;
    private String region;
    private String gender;
    private int startRow; // 시작 행
    private int endRow;   // 끝 행
	
	public AdoptionCriteria() {
        this(1, 20); // 기본 생성자: 페이지 1, 항목 수 20
    }

    public AdoptionCriteria(int pagenum, int pageSize) {
        this.pageNum = pagenum;
        this.pageSize = pageSize;
        calculateRows(); // 시작 행과 끝 행 계산
    }

    // 시작 행과 끝 행 계산
    private void calculateRows() {
        this.startRow = (this.pageNum - 1) * this.pageSize;
        this.endRow = this.startRow + this.pageSize;
    }

    // pagenum이 바뀔 때마다 startrow와 endRow가 바뀌어야 함
    public void setPagenum(int pageNum) {
        this.pageNum = pageNum;
        calculateRows(); // 페이지 번호 변경 시 행 계산
    }

    // URI 생성 메서드
    public String getListLink() {
        UriComponentsBuilder builder = UriComponentsBuilder.fromPath("")
                .queryParam("pagenum", this.pageNum)
                .queryParam("amount", this.pageSize)
                .queryParam("breed", this.breed)
                .queryParam("type", this.type)
                .queryParam("region", this.region)
        		.queryParam("gender", this.gender);
        return builder.toUriString(); // ?pagenum=4&amount=10&... 형식
    }

    // MyBatis에서 #{typeArr} 사용 가능
    public String[] getTypeArr() {
        return type == null ? new String[] {} : type.split("");
    }
}


