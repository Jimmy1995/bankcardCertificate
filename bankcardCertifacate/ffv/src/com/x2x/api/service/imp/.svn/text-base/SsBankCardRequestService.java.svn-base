package com.x2x.api.service.imp;

import java.util.Map;

import org.springframework.stereotype.Service;

import com.common.util.xml.XMLUtils;
import com.common.web.SpringContextHolder;
import com.x2x.api.model.ResHead;
import com.x2x.api.model.req.BankCardAuthRequest;
import com.x2x.api.model.res.BankCardAuthResponse;
import com.x2x.api.service.IChannelApiService;
import com.x2x.api.service.IExecService;
import com.x2x.api.utils.StringHelper;
import com.x2x.api.vo.ssbankcard.SsCommonRequest;
import com.x2x.api.vo.ssbankcard.SsCommonResponse;
/**
 * 
 * @DESC 银行卡实名认证对外接口（暂时只有银行卡三、四要素实名认证）
 * @AUTHOR JIANGCW
 * @DATE 2018-1-11下午05:57:25
 */
@Service(value="ssbankcard_request")
public class SsBankCardRequestService implements IExecService{
	private IChannelApiService getSsBankCardApiService() {return SpringContextHolder.getBean("ssBankCardApiService");}
	
	@Override
	public Object exec(String requestxml) throws Exception {
		
		BankCardAuthRequest req=XMLUtils.parserString(requestxml, BankCardAuthRequest.class);
		//301 302 303 312 313(暂时只有 302：银行卡三要素实名认证 303：银行卡四要素实名认证 )
		String serviceCode = req.getData().getServiceCode();
		String bankCard = req.getData().getBankCard();
		String realName = req.getData().getRealName();
		String idCard = req.getData().getIdCard();
		String bankPhone = req.getData().getBankPhone();
		String downTransNo = req.getHead().getReqNo();
		String mChild = req.getHead().getMchid();
    	SsCommonRequest sreq = new SsCommonRequest();
    	sreq.setBankCard(bankCard);
    	sreq.setName(realName);
    	sreq.setIdNumber(idCard);
    	sreq.setMobile(bankPhone);
    	sreq.setServiceCode(serviceCode);
    	sreq.setDownTransNo(downTransNo);
    	sreq.setmChild(mChild);
		SsCommonResponse response = getSsBankCardApiService().verify(sreq);
		Map<String, Object> resMap = StringHelper.convertBean(response);
		ResHead rh=new ResHead();
		rh.setRespCd(resMap.get("key")+"");
		rh.setRespMsg(resMap.get("msg")+"");
		rh.setReqNo(req.getHead().getReqNo());
		BankCardAuthResponse.body body = new BankCardAuthResponse.body();
		body.setmChid(req.getHead().getMchid());
		BankCardAuthResponse res = new BankCardAuthResponse(rh,body);
		return res;
	}

}
