package com.x2x.api.service;

import com.x2x.api.vo.BasicReq;
import com.x2x.api.vo.BasicRes;

/**
 * 支付通道API接口类：所有通道都继承此接口
 * @author JIANGCHONGWEN
 */
public interface IChannelApiService{
	/**
	 * 银行卡实名认证
	 * @param req
	 * @return 
	 */
	public <T extends BasicRes> T verify(BasicReq<T> req)throws Exception;

}