package com.conatus.spring.rest_login;

public class UserDTO {
	private String username;
	private String password;
	private String accessToken;
	//private String token;

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

	public void setAccessToken(String accessToken)
	{
		this.accessToken = accessToken;
	}
/*
	public void setToken(String token) {
		this.token = token;
	}

	*/
	public String getAccessToken() {
		return accessToken;
	}
/*
	public String getToken() {
		return token;
	}
	*/
}
