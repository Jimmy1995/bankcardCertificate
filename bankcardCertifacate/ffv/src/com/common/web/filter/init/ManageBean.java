package com.common.web.filter.init;
public class ManageBean {
	private String[] manageKeyWords;
	private String[] disManageKeyWords;
	private String[] noLogin;


	/**
	 * @return the noLogin
	 */
	public String[] getNoLogin() {
		return noLogin;
	}



	/**
	 * @param noLogin the noLogin to set
	 */
	public void setNoLogin(String[] noLogin) {
		this.noLogin = noLogin;
	}



	/**
	 * @return the manageKeyWords
	 */
	public String[] getManageKeyWords() {
		return manageKeyWords;
	}



	/**
	 * @param manageKeyWords the manageKeyWords to set
	 */
	public void setManageKeyWords(String[] manageKeyWords) {
		this.manageKeyWords = manageKeyWords;
	}



	/**
	 * @return the disManageKeyWords
	 */
	public String[] getDisManageKeyWords() {
		return disManageKeyWords;
	}



	/**
	 * @param disManageKeyWords the disManageKeyWords to set
	 */
	public void setDisManageKeyWords(String[] disManageKeyWords) {
		this.disManageKeyWords = disManageKeyWords;
	}
}
