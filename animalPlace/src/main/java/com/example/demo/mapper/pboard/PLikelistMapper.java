package com.example.demo.mapper.pboard;

import org.apache.ibatis.annotations.Mapper;

import com.example.demo.model.pboard.PLikelistDTO;

@Mapper
public interface PLikelistMapper {

	PLikelistDTO getLikelist(String loginUser, long boardnum);

	Long countlike(long boardnum);

	boolean insertLike(Long boardnum, String loginUser);

	boolean deleteLike(Long boardnum, String loginUser);

}
