package com.example.demo.service.map;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.model.map.MapSearchHistoryDTO;
import com.example.demo.entity.map.MapSearchHistory;
import com.example.demo.entity.map.MapSearchHistoryRepository;

import jakarta.transaction.Transactional;

@Service
public class MapSearchService {

	@Autowired
	private MapSearchHistoryRepository searchRepository;
	
	@Transactional
    public void recordSearch(MapSearchHistoryDTO searchHistoryDTO) {
        MapSearchHistory searchHistory = searchRepository.findByKeyword(searchHistoryDTO.getKeyword());
        if (searchHistory != null) {
            searchHistory.setSearchCount(searchHistory.getSearchCount() + 1);
            searchRepository.save(searchHistory);
        } else {
            searchHistory = new MapSearchHistory();
            searchHistory.setKeyword(searchHistoryDTO.getKeyword());
            searchHistory.setSearchCount(1);
            searchHistory.setSearchTime(LocalDateTime.now());
            searchRepository.save(searchHistory);
        }
	}
	public List<MapSearchHistoryDTO> getSearchRankings() {
	    List<MapSearchHistory> rankings = searchRepository.findTop10ByOrderBySearchCountDesc();
	    
	    return rankings.stream()
	        .map(history -> new MapSearchHistoryDTO(
	            history.getId(), 
	            history.getKeyword(), 
	            history.getSearchTime(), 
	            history.getSearchCount())) // 검색 횟수 추가
	        .collect(Collectors.toList());
	}
}