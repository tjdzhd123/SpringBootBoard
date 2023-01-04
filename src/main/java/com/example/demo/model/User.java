package com.example.demo.model;

import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;

@SuppressWarnings("serial")
@Data
public class User implements UserDetails  { 
	
	private int id;
	private String username;
	private String password;
	private String email;
	private String role; //ROLE_USER
	private String birth;
	private Timestamp createDate;
	
	private User user;
	
	public int getId() {
		return id;
	}

	public String getEmail() {
		return email;
	}

	public String getRole() {
		return role;
	}

	public String getBirth() {
		return birth;
	}

	public Timestamp getCreateDate() {
		return createDate;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public void setBirth(String birth) {
		this.birth = birth;
	}

	public void setCreateDate(Timestamp createDate) {
		this.createDate = createDate;
	}

	// 권한 (기본 권한 셋팅)
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		Collection<GrantedAuthority> collect = new ArrayList<>();
		collect.add(new GrantedAuthority() {
			@Override
			public String getAuthority() {
				return user.getRole();
			}
		});
		return collect;
	}

	@Override
	public String getPassword() {
		return this.password;
	}

	@Override
	public String getUsername() {
		return this.username;
	}

	// 계정 만료
	@Override
	public boolean isAccountNonExpired() {

		return true;
	}

	// 잠긴 계정
	@Override
	public boolean isAccountNonLocked() {

		return true;
	}

	// 패스워드 만료
	@Override
	public boolean isCredentialsNonExpired() {

		return true;
	}

	@Override
	public boolean isEnabled() {
		if(isCredentialsNonExpired() && isAccountNonExpired() && isAccountNonLocked()){
			return true;
		}
		return true;
	}
	@Override
	public String toString() {
		return "User [id=" + id + ", username=" + username + ", password=" + password + ", email=" + email + ", role="
				+ role + ", birth=" + birth + ", createDate=" + createDate + "]";
	}




}