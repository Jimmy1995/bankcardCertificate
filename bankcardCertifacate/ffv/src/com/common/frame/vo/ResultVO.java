package com.common.frame.vo;

public class ResultVO {
	public ResultVO() {
		super();
		// TODO Auto-generated constructor stub
	}

	public ResultVO(String login, String error) {
		super();
		this.login = login;
		this.error = error;
	}

	private String login;
	private String error;

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getError() {
		return error;
	}

	public void setError(String error) {
		this.error = error;
	}
}
