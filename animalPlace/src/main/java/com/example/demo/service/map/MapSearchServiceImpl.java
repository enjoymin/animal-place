package com.example.demo.service.map;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.entity.map.MapSearchHistory;
import com.example.demo.mapper.map.MapSearchMapper;
import com.example.demo.model.map.MapSearchHistoryDTO;

@Service
public class MapSearchServiceImpl implements MapSearchService {

    @Autowired
    private MapSearchMapper searchMapper;
    @Override
    public void recordSearch(MapSearchHistoryDTO searchHistoryDTO) {
        // 기존의 검색 기록 조회
        MapSearchHistory existingSearchHistory = searchMapper.getSearchHistoryByKeyword(searchHistoryDTO.getKeyword());

        if (existingSearchHistory != null) {
            // 기존 검색 기록이 있을 경우 카운트 증가
            existingSearchHistory.setSearchCount(existingSearchHistory.getSearchCount() + 1);
            searchMapper.updateSearchCount(existingSearchHistory);
        } else {
            // 새로운 검색 기록 생성
            MapSearchHistory newSearchHistory = new MapSearchHistory();
            newSearchHistory.setKeyword(searchHistoryDTO.getKeyword());
            newSearchHistory.setSearchCount(1); // 최초 검색이므로 1로 설정
            newSearchHistory.setSearchTime(LocalDateTime.now());

            searchMapper.insertSearchHistory(newSearchHistory);
        }
    }

	@Override
	public List<MapSearchHistoryDTO> getSearchRankings() {
List<MapSearchHistory> rankings = searchMapper.getTopRankings();
        
        return rankings.stream()
            .map(history -> new MapSearchHistoryDTO(
                history.getId(), 
                history.getKeyword(), 
                history.getSearchTime(), 
                history.getSearchCount()))
            .collect(Collectors.toList());
	}

}
