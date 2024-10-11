package com.example.demo.mapper.map;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.example.demo.entity.map.MapSearchHistory;

@Mapper
public interface MapSearchMapper {
    void insertSearchHistory(MapSearchHistory searchHistory);
    List<MapSearchHistory> getTopRankings();
	void updateSearchCount(MapSearchHistory existingSearchHistory);
	MapSearchHistory getSearchHistoryByKeyword(String keyword);
}