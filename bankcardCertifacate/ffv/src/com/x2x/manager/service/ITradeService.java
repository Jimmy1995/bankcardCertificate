package com.x2x.manager.service;

import com.x2x.manager.model.FfvTrade01;


public interface ITradeService {
	public String callTradeNo()throws Exception; 
	
	public String callB01id()throws Exception; 
	
	public String callMchid()throws Exception; 
	
	public void insertTrade01(FfvTrade01 record);

}
