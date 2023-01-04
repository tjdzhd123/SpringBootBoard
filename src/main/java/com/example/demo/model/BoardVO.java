package com.example.demo.model;


import java.security.Timestamp;

import lombok.Data;

@Data
public class BoardVO {
	private int seq;
	private String title;
	private String content;
	private String id_frt;
	private Timestamp dt_frt;
	private String id_lst;
	private Timestamp dt_lst;
	private String file_path;
	private String file_string;
	private String nickname;

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public String getFile_path() {
		return file_path;
	}

	public void setFile_path(String file_path) {
		this.file_path = file_path;
	}

	public String getFile_string() {
		return file_string;
	}

	public void setFile_string(String file_string) {
		this.file_string = file_string;
	}

	public int getSeq() {
		return seq;
	}
	public void setSeq(int seq) {
		this.seq = seq;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getId_frt() {
		return id_frt;
	}
	public void setId_frt(String id_frt) {
		this.id_frt = id_frt;
	}
	public Timestamp getDt_frt() {
		return dt_frt;
	}
	public void setDt_frt(Timestamp dt_frt) {
		this.dt_frt = dt_frt;
	}
	public String getId_lst() {
		return id_lst;
	}
	public void setId_lst(String id_lst) {
		this.id_lst = id_lst;
	}
	public Timestamp getDt_lst() {
		return dt_lst;
	}
	public void setDt_lst(Timestamp dt_lst) {
		this.dt_lst = dt_lst;
	}


}
