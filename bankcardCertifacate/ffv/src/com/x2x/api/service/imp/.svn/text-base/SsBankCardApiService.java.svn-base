package com.x2x.api.service.imp;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.common.base.DBHelper;
import com.common.web.SpringContextHolder;
import com.x2x.api.InteConfig;
import com.x2x.api.extapi.ssbankcard.HttpClientUtil;
import com.x2x.api.service.IChannelApiService;
import com.x2x.api.utils.StringHelper;
import com.x2x.api.vo.BasicReq;
import com.x2x.api.vo.BasicRes;
import com.x2x.api.vo.ssbankcard.SsCommonRequest;
import com.x2x.api.vo.ssbankcard.SsCommonResponse;
import com.x2x.manager.model.FfvBusiness01;
import com.x2x.manager.model.FfvTrade01;
import com.x2x.manager.service.ITradeService;
/**
 * 
 * @DESC 长沙松顺银行卡实名认证API
 * @AUTHOR JIANGCW
 * @DATE 2018-1-10下午02:33:43
 */
@Service
public class SsBankCardApiService implements IChannelApiService{
	private Logger log = Logger.getLogger(SsBankCardApiService.class);
	private ITradeService getITradeService() {return SpringContextHolder.getBean(ITradeService.class);};

	/**
	 * 
	 * @DESC 银行卡实名认证调用方法(暂时只有银行卡三、四要素及接口开通)
	 * @AUTHOR JIANGCW
	 * @DATE 2018-1-11下午02:31:02
	 */
	@Override
	public <T extends BasicRes> T verify(BasicReq<T> req) throws Exception {
		if(req instanceof SsCommonRequest){
			SsCommonRequest sreq = (SsCommonRequest) req;
			SsCommonResponse sres = new SsCommonResponse();
			float allAmt = 200f;//调用接口的费用
			String downTransNo = sreq.getDownTransNo();
			String mChil = sreq.getmChild();//下游商户号
			//商家账户余额不充足返回错误信息，充足则才去调用上游的认证接口
			FfvBusiness01 business01 = DBHelper.queryForBean("select * from ffv_business01 where busMchid = ?", FfvBusiness01.class, mChil);
			if(business01.getBalance() < allAmt ){
				sres.setKey("0070");
				sres.setMsg("商家账户余额不足");
				return (T) sres;
			}
			float balance = business01.getBalance()-allAmt;//商户扣除费用后的余额
			String serviceCode = sreq.getServiceCode();
			log.info("serviceCode:"+serviceCode);
			//准备请求参数
			String version = "1.0";//版本号
			String accCode = InteConfig.getInteConfig().get("songshun.accCode");//SS001 调用方注册的账户ID(应用编码)
			String accessKeyId = InteConfig.getInteConfig().get("songshun.accessKeyId");//"2e034f473c3740e8ae8b879b69d811b3"; 用户申请的密钥的索引 
			String merchantId = InteConfig.getInteConfig().get("songshun.merchantId");//"SS18011061";商户号 
			String requestId = getITradeService().callTradeNo();
			String timeStamp = System.currentTimeMillis()+"";
			String url = InteConfig.getInteConfig().get("songshun.requestUrl");//"http://39.108.115.111/test/SMRZ_SRV";
			String signKey = InteConfig.getInteConfig().get("songshun.signKey");//签名秘钥
			//银行卡四要素
			String bankCard = sreq.getBankCard();//银行卡卡号
			String name = sreq.getName();//开户人姓名
			String idNumber = sreq.getIdNumber();//开户人身份证号
			String mobile = sreq.getMobile();//银行预留手机号
			//封装共有请求参数
			HashMap<String, Object> reqMap = new HashMap<String, Object>();
	        reqMap.put("version", version);
	        reqMap.put("accCode", accCode);
	        reqMap.put("accessKeyId", accessKeyId);
	        reqMap.put("timestamp", timeStamp);
	        reqMap.put("requestId", requestId);
	        reqMap.put("merchantId", merchantId);
	        
	        reqMap.put("url", url);
	        reqMap.put("signKey", signKey);
	        reqMap.put("serviceCode",serviceCode);
	        FfvTrade01 record = new FfvTrade01(Long.valueOf(requestId.substring(1)), requestId,downTransNo, "0", new Date(), mChil);
	        record.setServiceCode(serviceCode);
	        if(null != business01){
	        	record.setB01id(business01.getB01id());//下游商户ID需存入数据表
	        }
			if("301".equals(serviceCode)){//银行卡二要素认证
		        reqMap.put("bankCard", bankCard);
		        reqMap.put("name", name);
		        record.setAllAmt(allAmt);
			}else if("302".equals(serviceCode)){//银行卡三要素认证
		        reqMap.put("bankCard", bankCard);
		        reqMap.put("name", name);
		        reqMap.put("idNumber", idNumber);
		        record.setAllAmt(allAmt);
			}else if("303".equals(serviceCode)){//银行卡四要素认证
				//封装个体参数
		        reqMap.put("bankCard", bankCard);
		        reqMap.put("name", name);
		        reqMap.put("idNumber", idNumber);
		        reqMap.put("mobile", mobile);
		        record.setAllAmt(allAmt);
			}else if("312".equals(serviceCode)){//银行卡三要素精准错误返回实名认证
		        reqMap.put("bankCard", bankCard);
		        reqMap.put("name", name);
		        reqMap.put("idNumber", idNumber);
		        record.setAllAmt(allAmt);
			}else if("313".equals(serviceCode)){//银行卡四要素精准错误返回实名认证
		        reqMap.put("bankCard", bankCard);
		        reqMap.put("name", name);
		        reqMap.put("idNumber", idNumber);
		        reqMap.put("mobile", mobile);
		        record.setAllAmt(allAmt);
			}
			//保存数据到表
			record.setAccountNo(StringHelper.encode(bankCard));
			record.setAccountName(name);
			record.setCardNo(idNumber);
			record.setPhoneNo(mobile);
			getITradeService().insertTrade01(record );
			//调用接口
	        Map<String, Object> resMap = HttpClientUtil.invoke(reqMap);
	        //{"ext":{},"key":"0000","msg":"认证通过","requestId":"bp00000000000063212"}
	        log.info("API...resMap:"+JSON.toJSONString(resMap));
	        if("0000".equals(resMap.get("key"))){//认证通过
	        	FfvTrade01 trade01 = new FfvTrade01();
	        	trade01.setT01id(Long.valueOf(requestId.substring(1)));
	        	trade01.settStatus("1");
	        	trade01.setFinishTime(new Date());
	        	DBHelper.update("ffv_trade01", trade01,new String[]{"t01id"}, new String[]{"tStatus","finishTime"});
	        }else{//失败或错误
	        	FfvTrade01 trade01 = new FfvTrade01();
	        	trade01.setT01id(Long.valueOf(requestId.substring(1)));
	        	trade01.settStatus("2");
	        	trade01.setFinishTime(new Date());
	        	trade01.settMsg(resMap.get("msg")+"");
	        	DBHelper.update("ffv_trade01", trade01,new String[]{"t01id"}, new String[]{"tStatus","finishTime","tMsg"});
	        }
	        BeanUtils.populate(sres, resMap);
	        //修改下游商家账户余额ffv_business01(balance)
	        FfvBusiness01 busi = new FfvBusiness01();
	        busi.setBusMchid(business01.getBusMchid());
	        busi.setBalance(Long.valueOf((balance+"").substring(0,(balance+"").indexOf("."))));
	        busi.setUpdateTime(new Date());
	        DBHelper.update("ffv_business01",busi, new String[]{"busMchid"}, new String[]{"balance","updateTime"});
	        
	        return (T) sres;
		}
		return null;
	}

}
