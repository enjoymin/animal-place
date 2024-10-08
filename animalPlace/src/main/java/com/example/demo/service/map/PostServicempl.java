package com.example.demo.service.map;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.demo.model.map.Criteria;
import com.example.demo.model.map.MapDTO;

@Service
public class PostServicempl implements PostService {

	@Override
	public void savePost(MapDTO post) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<MapDTO> getAllPost() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public MapDTO getPostById(Long boardnum) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void updatePost(MapDTO post) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deletePost(Long boardnum) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<MapDTO> getPostsByCriteria(Criteria criteria) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getTotalCount(Criteria criteria) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public List<MapDTO> getAllPost(Criteria criteria) {
		// TODO Auto-generated method stub
		return null;
	}

}
