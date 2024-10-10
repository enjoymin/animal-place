package com.example.demo.mapper.map;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.example.demo.model.map.Criteria;
import com.example.demo.model.map.MapDTO;

@Mapper
public interface MapMapper {
	void insertPost(MapDTO post);
	List<MapDTO> selectAllPosts();
	MapDTO selectPosts();
	void updatePost(MapDTO post);
	void deletePost(Long boardnum);
	List<MapDTO> selectPostByCriteria(Criteria criteria);
	int getTotalCount(Criteria criteria);
}
