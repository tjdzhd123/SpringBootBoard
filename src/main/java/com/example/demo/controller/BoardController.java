package com.example.demo.controller;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.FileSystem;
import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import com.example.demo.dao.BoardMapper;
import com.example.demo.manager.BoardManager;
import com.example.demo.model.BoardVO;

import javax.swing.filechooser.FileSystemView;

@RestController
@RequestMapping("/api")
public class BoardController {
	@Autowired
	BoardMapper boardMapper;
	
	@Autowired
	BoardManager boardManager;

	@GetMapping("/board/list")
	public  ArrayList<HashMap<String, Object>> listBoard() {
		
		return boardManager.boardList();
	}

	@PatchMapping("/board/{seq}")
	public Integer updateBoard(BoardVO boardVO) {

		return boardManager.updateBoard(boardVO);
	}

	@DeleteMapping("/board/{seq}")
	public Integer deleteBoard(BoardVO boardVO) {

		return boardManager.deleteBoard(boardVO);
	}



	@PostMapping("/board/new")
	public int createBoard(BoardVO boardVO) {
		UUID uuid = UUID.randomUUID();
		File file = new File(FileSystemView.getFileSystemView().getDefaultDirectory() + boardVO.getNickname() + uuid);
		System.out.println(file);
		//다시 디코딩 처리(원상태)
		Base64.Decoder decoder = Base64.getDecoder();
		byte[] decodeBytes = decoder.decode(boardVO.getFile_string().getBytes());
		try {
			//파일로 바이트 단위의 출력을 내보내는 클래스
			FileOutputStream fileOutputStream = new FileOutputStream(file);
			try {
				fileOutputStream.write(decodeBytes);
				fileOutputStream.close();
			}catch (IOException e) {
				e.printStackTrace();
			}
		}catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		//성공 실패 넘기기
			String file_path = file.toString();
			boardVO.setFile_path(file_path);
		boardManager.createBoard(boardVO);

		return boardVO.getSeq();
	}

}
