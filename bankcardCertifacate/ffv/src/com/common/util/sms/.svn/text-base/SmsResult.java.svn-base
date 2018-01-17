package com.common.util.sms;

import java.util.HashMap;
import java.util.Map;

public class SmsResult {
	private static Map<String, String> resultMap = new HashMap<String, String>();
	static {
		resultMap.put("000", "发送成功！");
		resultMap.put("0808191630319344", "发送成功！");
		resultMap.put("-01", "当前账号余额不足！");
		resultMap.put("-02", "当前用户ID错误！");
		resultMap.put("-03", "当前密码错误！");
		resultMap.put("-04", "参数不够或参数内容的类型错误！");
		resultMap.put("-05", "手机号码格式不对！");
		resultMap.put("-06", "短信内容编码不对！");
		resultMap.put("-07", "短信内容含有敏感字符！");
		resultMap.put("-08", "无接收数据");
		resultMap.put("-09", "系统维护中..");
		resultMap.put("-10", "手机号码数量超长！");
		resultMap.put("-11", "短信内容超长！");
		resultMap.put("-12", "其它错误！");
	}
	public static String getResult(String code) {
		return resultMap.get(code);
	}
	public static Boolean isSuccess(String code){
		if("000".equals(code)||"0808191630319344".equals(code))
			return true;
		else
			return false;
	}
}
