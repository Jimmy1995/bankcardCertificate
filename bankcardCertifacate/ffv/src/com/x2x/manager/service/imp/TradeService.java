package com.x2x.manager.service.imp;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.common.base.DBHelper;
import com.x2x.manager.model.FfvTrade01;
import com.x2x.manager.service.ITradeService;
@Service
public class TradeService implements ITradeService{
	private Logger log = Logger.getLogger(TradeService.class);

	@Override
	public String callTradeNo() throws Exception {
		return DBHelper.getMaxId("tradeCode");
	}
	
	@Override
	public String callB01id() throws Exception {
		return DBHelper.getMaxId("b01id");
	}
	
	@Override
	public String callMchid() throws Exception {
		return DBHelper.getMaxId("busMchid");
	}

	@Override
	public void insertTrade01(FfvTrade01 record) {
		DBHelper.insert("ffv_trade01", record);
		
	}
}
