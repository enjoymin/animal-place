package com.example.demo.service.map;

import java.util.List;

import com.example.demo.model.map.MapSearchHistoryDTO;

public interface MapSearchService {
    void recordSearch(MapSearchHistoryDTO searchHistoryDTO);
    List<MapSearchHistoryDTO> getSearchRankings();
}