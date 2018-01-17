package com.x2x.api.extapi.ssbankcard;

import java.util.Random;

import javax.servlet.http.HttpServletRequest;
/**
 * 
 * @DESC 
 * @AUTHOR JIANGCW
 * @DATE 2018-1-11下午02:26:22
 */
public class WxHelper {
	/**
	 * 生成随机字符串
	 * @param length
	 * @return
	 */
	public static String getRandomStringByLength(int length) {  
        String base = "abcdefghijklmnopqrstuvwxyz0123456789";  
        Random random = new Random();  
        StringBuffer sb = new StringBuffer();  
        for (int i = 0; i < length; i++) {  
            int number = random.nextInt(base.length());  
            sb.append(base.charAt(number));  
        }  
        return sb.toString();  
    }  
	public static boolean isWxPay(HttpServletRequest request){
		boolean validation=false;
		String ua = request.getHeader("user-agent")  
		        .toLowerCase();  
		if (ua.indexOf("micromessenger") > 0) {// 是微信浏览器  
		    validation = true;  
		}  
		return validation;
	}
	public static boolean isAliPay(HttpServletRequest request){
		boolean validation=false;
		String ua = request.getHeader("user-agent")  
		        .toLowerCase();  
		if (ua.indexOf("alipayclient") > 0) {// 是支付宝浏览器  
		    validation = true;  
		}  
		return validation;
	}
}
