package com.example.demo.entity.map;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import jakarta.transaction.Transactional;

@Repository
@Transactional
public interface MapSearchHistoryRepository extends JpaRepository<MapSearchHistory, Long> {
    List<MapSearchHistory> findTop10ByOrderBySearchCountDesc();
    MapSearchHistory findByKeyword(String keyword);
}
