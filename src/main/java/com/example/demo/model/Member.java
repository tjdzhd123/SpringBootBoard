package com.example.demo.model;

import lombok.Data;

@Data
public class Member {
	
	private long id;
	private String name;
	private String token;
	
	public Member(String name, String token ) {
		this.name = name;
		this.token = token;
	}

	public long getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public String getToken() {
		return token;
	}

	public void setId(long id) {
		this.id = id;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setToken(String token) {
		this.token = token;
	}
	

}
