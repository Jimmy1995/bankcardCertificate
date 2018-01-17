package com.x2x.manager.model;


/**
 * 
 * @DESC 
 * @AUTHOR JIANGCW
 * @DATE 2018-1-11下午02:08:32
 */
public class BpInteConfig implements java.io.Serializable {
	private static final long serialVersionUID = 5635833813323149176L;
	private String inteCode;
	private String inteValue;
	private String inteName;

	// Constructors

	/** default constructor */
	public BpInteConfig() {
	}

	/** minimal constructor */
	public BpInteConfig(String inteCode) {
		this.inteCode = inteCode;
	}

	/** full constructor */
	public BpInteConfig(String inteCode, String inteValue, String inteName) {
		this.inteCode = inteCode;
		this.inteValue = inteValue;
		this.inteName = inteName;
	}

	public String getInteCode() {
		return inteCode;
	}

	public void setInteCode(String inteCode) {
		this.inteCode = inteCode;
	}

	public String getInteValue() {
		return inteValue;
	}

	public void setInteValue(String inteValue) {
		this.inteValue = inteValue;
	}

	public String getInteName() {
		return inteName;
	}

	public void setInteName(String inteName) {
		this.inteName = inteName;
	}

	
}