package com.example.demo.model.map;

import com.example.demo.model.map.Criteria;

import lombok.Data;

@Data
public class MbPageDTO {
    private int startPage;  // 시작 페이지
    private int endPage;    // 끝 페이지
    private int realEnd;    // 실제 마지막 페이지
    private long total;      // 전체 게시글 수
    private boolean prev;    // 이전 페이지 존재 여부
    private boolean next;    // 다음 페이지 존재 여부
    private Criteria cri;    // 현재 페이지 기준 정보

    public MbPageDTO(long total, Criteria cri) {
        this.cri = cri;
        this.total = total;

        // 실제 마지막 페이지 계산
        this.realEnd = (int) Math.ceil(total / (double) cri.getPageSize());
        // 시작 페이지 및 끝 페이지 계산
        this.endPage = (int) Math.ceil(cri.getPageNum() / (double) cri.getPageSize()) * cri.getPageSize();
        this.startPage = this.endPage - (cri.getPageSize() - 1);

        // 시작 페이지와 끝 페이지 조정
        if (this.startPage < 1) {
            this.startPage = 1;
        }
        if (this.endPage > this.realEnd) {
            this.endPage = this.realEnd;
        }

        // 이전 및 다음 페이지 존재 여부
        this.prev = this.startPage > 1;
        this.next = this.endPage < this.realEnd;
    }
}
