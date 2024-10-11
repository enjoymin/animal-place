package com.example.demo.model.map;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class MapSearchHistoryDTO {
    private Long id;
    private String keyword;
    private LocalDateTime searchTime;
    private int searchCount; // 검색 횟수 추가

    public MapSearchHistoryDTO(Long id, String keyword, LocalDateTime searchTime, int searchCount) {
        this.id = id;
        this.keyword = keyword;
        this.searchTime = searchTime;
        this.searchCount = searchCount; // 생성자에 검색 횟수 추가
    }
}
