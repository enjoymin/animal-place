package com.example.demo.controller.map;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.map.MapSearchHistoryDTO;
import com.example.demo.service.map.MapSearchService;

@RestController
@RequestMapping("/search")
public class MapSearchController {
	
	@Autowired
	private MapSearchService searchService;
	
	@PostMapping("/record")
	public void recordSearch(@RequestBody MapSearchHistoryDTO keywordDTO) {
	    System.out.println("Received keyword: " + keywordDTO.getKeyword()); // 키워드 확인
	    searchService.recordSearch(keywordDTO);
	}

    
    @GetMapping("/rankings")
    public List<MapSearchHistoryDTO> getSearchRankings() {
        return searchService.getSearchRankings();
    }
}
