package com.sapo.dto;

public class UserRegisterDTO {
	private String name;
	private String phone;
	private String email;
	private String username;
	private String password;
	private String passwordConfirm;
	
	public String getPasswordConfirm() {
		return passwordConfirm;
	}
	public void setPasswordConfirm(String passwordConfirm) {
		this.passwordConfirm = passwordConfirm;
	}
	public String getPhone() {
		return phone;
	}
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
}
