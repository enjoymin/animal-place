package com.example.demo.service.map;

import java.util.List;

import com.example.demo.model.map.Criteria;
import com.example.demo.model.map.MapDTO;

public interface PostService {
	void savePost(MapDTO post);
	List<MapDTO> getAllPost();
	MapDTO getPostById(Long boardnum);
	void updatePost(MapDTO post);
	void deletePost(Long boardnum);
	List<MapDTO> getPostsByCriteria(Criteria criteria);
	int getTotalCount(Criteria criteria);
	List<MapDTO> getAllPost(Criteria criteria);
}
