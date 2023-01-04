package com.example.demo.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.example.demo.model.BoardVO;

@Mapper
public interface BoardMapper {
	 ArrayList<HashMap<String, Object>> boardList();
	BoardVO getBoard(long seq);
	 int createBoard(BoardVO boardVO);
	 int updateBoard(BoardVO boardVO);
	 int deleteBoard(BoardVO boardVO);


}
