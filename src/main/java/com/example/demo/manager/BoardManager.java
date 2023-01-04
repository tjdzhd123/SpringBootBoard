package com.example.demo.manager;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.dao.BoardMapper;
import com.example.demo.model.BoardVO;

import javax.swing.filechooser.FileSystemView;

@Service
public class BoardManager {

	@Autowired
	BoardMapper boardMapper;
	
	public  ArrayList<HashMap<String, Object>> boardList() {
		
		return boardMapper.boardList();
	}

	public BoardVO getBoard(long seq) {
		return boardMapper.getBoard(seq);
	}

	public int createBoard(BoardVO boardVO) {
		return boardMapper.createBoard(boardVO);
	}

	public int updateBoard(BoardVO boardVO) {
		return boardMapper.updateBoard(boardVO);
	}

	public int deleteBoard(BoardVO boardVO) {
		return boardMapper.deleteBoard(boardVO);
	}

}
