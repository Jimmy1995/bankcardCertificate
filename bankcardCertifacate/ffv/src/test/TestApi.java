package test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.util.EntityUtils;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.springframework.beans.BeanUtils;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.common.base.BaseException;
import com.common.base.DBHelper;
import com.common.util.MD5;
import com.common.util.xml.XMLUtils;
import com.common.web.SpringContextHolder;
import com.x2x.api.ApiHelper;
import com.x2x.api.model.ReqHead;
import com.x2x.api.model.req.BankCardAuthRequest;
import com.x2x.api.model.req.EmptyRequest;
import com.x2x.api.utils.APISecurityUtils;
import com.x2x.api.utils.Base64Utils;
import com.x2x.api.utils.ConnectionManager;
import com.x2x.api.utils.Matchers;
import com.x2x.manager.service.ITradeService;

public class TestApi{
//	private static String prikey="MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBALYgMbrgI82Kl+M6F7O9glPiW7kPiKWB4S8oVOU2/XHnIcQ+0pj8E60lgRavquoq/r1q+1RAPlfMR/p/Dmjekobm80Yv3KPj9SMwXYLx+NowS0Q6w851biPNK3kD5bP7gAnURs/4KyutLgZvh8buzj9orlzcD15qXmrviSV0q24rAgMBAAECgYABy5Nx6h+wOPuCS+JL7URJm2OYEWUhbIRRuK4NjFs3MjYM/ymIRIPVIxeAp76hjEbyiwlrLS7wIp1bBMGZmwgKtB5TRp9LsJLi/TpV7KDidkFonaY9es6+qBfJkdjSOZPRnIy18F+Atcj3lS3r3HgfojNKTGQ1TJl6mt2NOyVU0QJBANiqiScHygiL65uPsFMkUeT9bXulBU79T5froHEKOlgv4oYUi7zLTK415IsSIhu6bynxgNztTAvtTEA0cQ4iW+UCQQDXMGiliKQDmoeV3Tev8jfK9YSxTq9farNhdRT0MEBqOxNYXz/W8HtqGBXyITgWc6xzeUsVH9EO4GUWTYiIZKDPAkEAnwn/BuFW0ZMzbMq6WS0t1/KWrM3i6apTBGb3LEKftR/hHR5zvC9WqAHzMooq80OUWatmNcURryeOcwqLeh6KaQJAGms5DcmeyUp5hu6n3ZQRQXuvFM2iPkatSzPCpNAfuGsUTu8yuULBBB984kMtzaPZ8jtb+nXzhq4J+xj9wDEKhQJBAMR8VTAcqqY70wNSEiE2DbvGb6n9wfjoyqI5JVXdkkM+GomeVnJ0BV6Tw7cG83pUkc9age00Y+h6wlbwBFwaoBs=";//商户私钥
//	private static String pubkey="MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDvfGaA47eDKQ9WD7JRvswq0W4ufLBi6x36xYSvR+pH5UQGa/kRmoRzRe0geNUDcc7R3DQFOGAPaTBsjYCn+r63cGPp95w0ZeLRNK4LjKCKz+xcFWd+aJZd2xpFlRP+I5e/29H6WycXfy4w/kk83V+dCku0l6bEvdC5kgVoV48PmwIDAQAB";//平台公钥
	
	//private static String prikey="MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBAJ3wiuHbiDtegWPKXt/OzVfN26ZYaO/qKc7OR4QBIadFQ49H3P1EEw+MPqdAzl5T8iaLPjXW5Nf5W4POwUedqGot6NF8J2172NYDh85rywtFNfQS5Q5F5DmzrNhOraFWa5RQb5DM9ih3yEoHt9KYMTgPi3GbafHhwRdHA3lZy6ujAgMBAAECgYA3yjZk3dVbk2CFTYEB8d0bPP5v3h8DUGYARWGBwZyPp6nbvPjVBeztuw6Oqhe7vk91Sg5pmkUv/UdUYf5oO8q3WEvfRzYYROMyqG1bHXLAlYvC2NtZb9oAyaLvCKd2CMsukccmiDY+rG9GT6rGlK6abRnHxf79cmFFECQ37hwVwQJBAN3ybBKPmIMzCQHVE/YAw+98XCwt+iYURFs7zbcS1Wce0ZyIl3JzhSuHmhhkJqkbQ7CvGXaGIRDhZbyZcijbCpECQQC2LA5IeCUAIQSAXCWVjRTiLxKTSf8DACS5KSH/BD241dMJTDLMKLlXM3QIWdE6gzLPH1kY+5M6YmMIfKZFwGTzAkEAuAVB+fm5WD6+XxiOXk+wJSaVPcQ5Pc1cB9ED9aDV85Qn84OFHk4NwlPCPir7qwaCD0J++DipvddEhSUs9Bb5IQJAHV4Ky2n65QNtm1e/g1VkCXZ3MyFZp90tDmBwJ5FCdfqac8RB6CGkh0c3LGV2Uk12MmemUF22BVJ5DpQxpcr3OwJAVJoNf7KxeS0MH0Pehvv9O6J+0D4J/GzbJ0DR20EkcKx67uit0OeC7E6hcqkEqSNssCiTKOzwe3V4orAWLHtAXg==";//商户私钥
	//private static String pubkey="MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCd8Irh24g7XoFjyl7fzs1XzdumWGjv6inOzkeEASGnRUOPR9z9RBMPjD6nQM5eU/Imiz411uTX+VuDzsFHnahqLejRfCdte9jWA4fOa8sLRTX0EuUOReQ5s6zYTq2hVmuUUG+QzPYod8hKB7fSmDE4D4txm2nx4cEXRwN5WcurowIDAQAB";//平台公钥
	/*
	public static void main1(String[] args) throws Exception{
		//SubmitPayRequest.body data=new SubmitPayRequest.body();
		PrePayRequest.body data=new PrePayRequest.body();
		ReqHead head=new ReqHead();
	//	SubmitPayRequest req=new SubmitPayRequest();
		PrePayRequest req=new PrePayRequest();
		head.setAppId("DEFAULT");
		head.setVersion("1.0");
		head.setReqType("pre_pay_request");
		head.setMchid("20171211008");
		head.setReqNo("test2017141138");
		head.setSignType("RSA1");
		head.setBackURL("http://124.232.133.199/ccp/x2xpay/payCallBack.action");
		req.setData(data);
		req.setHead(head);
		
		EmptyRequest eres=signRequest(req,"RSA1","2017");
		String reqxml=XMLUtils.toString(eres, EmptyRequest.class);
		System.out.println(reqxml);
		HttpPost post = new HttpPost("http://124.232.133.199/ccp/x2xpay/doRequest.action");
		StringEntity se = new StringEntity(reqxml,"utf-8");
		post.setEntity(se);
		HttpResponse response = ConnectionManager.getHttpClient().execute(post);
		String res = EntityUtils.toString(response.getEntity(),"utf-8");
		System.out.println(res);
		String xml=verifyResponse(res,"RSA1");//验签、并返回解密后的请求内容
		System.out.println(xml);
	}
	*/
	
	//---银行三要素、四要素实名认证对外接口全流程测试-start 20180112 by jiangcw---------------------------------------
	//商户私钥（用于请求数据签名、响应数据解密）   
	final static String prikey="MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBAMRzrGTTKdIoUrtFA8sZje0+AUBrgoLd1MN31uVoIjVSP7i0ekIEWkpEwCYM+Uz61yfQKmHNQPKlGsmpzhIfIwtlqb24/LZIx+Mo4gEbzC9JyPZigObPhkPLF0tmOgECBe396SwaHAFlfAnJy/eG1e65Fwaj/4c0h3bZxzoOUc6rAgMBAAECgYBvO9/9JuOjL1nI14cSVOHb1yP2SemYF+sE0rJHKvNTpcKW3vnqr3RAYTU7VlVClTWGQYP95K5Ftd65GQCpmh/Isk2FqWNbd8Ts1NufmicNedjYf7Z4wgAkStn2ZQA89GTdRSWPeAIWGF6a/gYHfV2FYI0CN626x+lqaQEmqwaooQJBAOD+Jeag0hgQeunw2Qjar6NzfRzotOaUizH/L2nAUy0EjB1yW0nXpAoOLFjTSByUFlajFCUGjbgFXxC+9crEjC0CQQDfhpaWY0DcWvyq8jrFcBuOVnNyB57vXiBj5x6PSnNFVSZQCg8cziH0mtl2mAJQpFdHwIEvhsvqXO8Q1ny/+BU3AkAKZhSwXRx4ukJED7qoOxtFDbBppqO9yH3KeMXjOF1fxkcHkWKAvjO5tz/7dwBtObbymCT1NSFVsQHcz0ai8YSdAkB7WAVYz0RHVF4A5tHPiWFrVgE2d0YYyFQPTMXsRCT/qVEO8b4NIyJRm/FMI+2DGmyfR3cCBtwXY1j4baNBO2BZAkEAt4wFZA/ZozOPOmgHqGJMnohJy8lmbEwWLBncUnTQR0FHh8ZN9YQQNr8jWouBAAkD0Rruvvgr0OEatJgpnzsTBQ==";
	//平台公钥(平台和商户互相交换了公钥，用于请求数据加密，响应数据验签)
	final static String pubkey = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDgg9VQS2M4Ztg04QmhAqoZfIDzMwU3tiAqGnVAPQEc4WwJ7z41hKqXvHaHSt8E2Rx/a1qWwId7DpdPFWBhYFnAraqNC+tnSsCeO+W0aZv/NXR912SsfKJMiWkdBn7jIddbd23kuUjzKNKV7HgTbDKiehU1f9z/vKZB/1ELbf6oaQIDAQAB";
	public static void main(String[] args) throws Exception{
		//商户私钥（用于请求数据签名、响应数据解密）
		//final static String prikey="MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBAMRzrGTTKdIoUrtFA8sZje0+AUBrgoLd1MN31uVoIjVSP7i0ekIEWkpEwCYM+Uz61yfQKmHNQPKlGsmpzhIfIwtlqb24/LZIx+Mo4gEbzC9JyPZigObPhkPLF0tmOgECBe396SwaHAFlfAnJy/eG1e65Fwaj/4c0h3bZxzoOUc6rAgMBAAECgYBvO9/9JuOjL1nI14cSVOHb1yP2SemYF+sE0rJHKvNTpcKW3vnqr3RAYTU7VlVClTWGQYP95K5Ftd65GQCpmh/Isk2FqWNbd8Ts1NufmicNedjYf7Z4wgAkStn2ZQA89GTdRSWPeAIWGF6a/gYHfV2FYI0CN626x+lqaQEmqwaooQJBAOD+Jeag0hgQeunw2Qjar6NzfRzotOaUizH/L2nAUy0EjB1yW0nXpAoOLFjTSByUFlajFCUGjbgFXxC+9crEjC0CQQDfhpaWY0DcWvyq8jrFcBuOVnNyB57vXiBj5x6PSnNFVSZQCg8cziH0mtl2mAJQpFdHwIEvhsvqXO8Q1ny/+BU3AkAKZhSwXRx4ukJED7qoOxtFDbBppqO9yH3KeMXjOF1fxkcHkWKAvjO5tz/7dwBtObbymCT1NSFVsQHcz0ai8YSdAkB7WAVYz0RHVF4A5tHPiWFrVgE2d0YYyFQPTMXsRCT/qVEO8b4NIyJRm/FMI+2DGmyfR3cCBtwXY1j4baNBO2BZAkEAt4wFZA/ZozOPOmgHqGJMnohJy8lmbEwWLBncUnTQR0FHh8ZN9YQQNr8jWouBAAkD0Rruvvgr0OEatJgpnzsTBQ==";
		//平台公钥(平台和商户互相交换了公钥，用于请求数据加密，响应数据验签)
		//final static String pubkey = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDgg9VQS2M4Ztg04QmhAqoZfIDzMwU3tiAqGnVAPQEc4WwJ7z41hKqXvHaHSt8E2Rx/a1qWwId7DpdPFWBhYFnAraqNC+tnSsCeO+W0aZv/NXR912SsfKJMiWkdBn7jIddbd23kuUjzKNKV7HgTbDKiehU1f9z/vKZB/1ELbf6oaQIDAQAB";
		
		//平台私钥（用于解密请求的数据，响应数据加签）
		//MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBAOCD1VBLYzhm2DThCaECqhl8gPMzBTe2ICoadUA9ARzhbAnvPjWEqpe8dodK3wTZHH9rWpbAh3sOl08VYGFgWcCtqo0L62dKwJ475bRpm/81dH3XZKx8okyJaR0GfuMh11t3beS5SPMo0pXseBNsMqJ6FTV/3P+8pkH/UQtt/qhpAgMBAAECgYBqeDpljIquCZ+IXBu73k6aLX/dVRmpVNYmAUwPPZ7UelZI08rViGd586bl++yNGQS5rEhWa1EUW1QH7eQ56X5FzIlwv+MzA6uLODdXvwCeLvPJgriC/GuCztVy/c8QlOkG80h5A3Yqcx14PlZbODpniMsycTXkyhsmzrYEVsznAQJBAP6mWFX3pjvy4OJOv8mFHy/2vAZM6+qC0FQysCgJWniSGzrSJ1yvVDlYYivv9Ehd2Wc3bwhiXKwv+CUcnq0mdyECQQDhtJWAfFmvApkNGHemTitFSaELpmNHPFRcv5lDwhgeSAco1f1ls1iQVv5zmcs3Qd73WTSzOl8PHJZkcvO4ILBJAkALG+Cgp3QWC2tqE/tj5C3PHtlnAPYNvfUupyNuK7JJrj4H2+EhtvhapBNsTUR/37A7Q3zRhdD7JgCSReRaM2JhAkEAmfxCIy6zYRs8oBqZRvT9wH/DR9d01Bzs0XpGblv1aHNrrwiPoNsDCTbQ0r2ST1i5bbeGpOCmCgFO+akTbgOfQQJBAJCt2jOFkg8PJPZdNXk0oOSVWoHqXsDe1kQIBtC4ryJx1r1Q7zHkWmGh5O9UN+4hXwX2hwJ8HisVM/VFJC9qyLg=
		//商户公钥（请求数据验签，响应数据加密）
		//MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDEc6xk0ynSKFK7RQPLGY3tPgFAa4KC3dTDd9blaCI1Uj+4tHpCBFpKRMAmDPlM+tcn0CphzUDypRrJqc4SHyMLZam9uPy2SMfjKOIBG8wvScj2YoDmz4ZDyxdLZjoBAgXt/eksGhwBZXwJycv3htXuuRcGo/+HNId22cc6DlHOqwIDAQAB
		
		//银行卡实名验证
		String reqNo = System.currentTimeMillis()+"";
		ReqHead head=new ReqHead();
		head.setAppId("1001");
		head.setVersion("1.0");
		head.setReqType("ssbankcard_request");
        head.setMchid("S668000000000016");//下游商户号 //S668000000000016
        head.setReqNo(reqNo);//下游请求流水
        head.setSignType("RSA1");
        head.setBackURL("");
        BankCardAuthRequest.body data = new BankCardAuthRequest.body();
        data.setBankCard("");//银行卡号
        data.setRealName("");//开户人姓名
        data.setIdCard("");//开户人身份证号
        data.setBankPhone("");//银行预留手机号
        data.setServiceCode("302");//302：银行卡三要素实名认证  303：银行卡四要素实名认证
        
		BankCardAuthRequest req =new BankCardAuthRequest();
		req.setHead(head);
		req.setData(data);
		
		EmptyRequest eres=signRequest(req,"RSA1","20171211008");
		String reqxml=XMLUtils.toString(eres, EmptyRequest.class);
		System.out.println("reqxml:"+reqxml);
		HttpPost post = new HttpPost("http://127.0.0.1:8080/ffv/x2xpay/doRequest.action");
		StringEntity se = new StringEntity(reqxml,"utf-8");
		post.setEntity(se);
		
		HttpResponse response = ConnectionManager.getHttpClient().execute(post);
		String res = EntityUtils.toString(response.getEntity(),"utf-8");
		System.out.println("resStr:"+res);
		String xml=verifyResponse(res,"RSA1");//验签、并返回解密后的请求内容
		System.out.println(xml);
		
		
		/*
		ApplicationContext ct=new ClassPathXmlApplicationContext("classpath:config/spring/applicationContext.xml");
		ITradeService bean = ct.getBean(ITradeService.class);
		//下游商户id产生
		String b01id = bean.callB01id();
		System.out.println("b01id:"+b01id);//6000000000052
		//下游商户号产生
		//String busMchid = bean.callMchid();
		//System.out.println("busMchid:"+busMchid);
		//最后需配置进ffv_business01表中
		*/
		//---银行三要素、四要素实名认证对外接口全流程测试-end 20180112 by jiangcw---------------------------------------
		
		
		
		
//		OpenCardRequest.body data=new OpenCardRequest.body();
//		ReqHead head=new ReqHead();
//		OpenCardRequest req=new OpenCardRequest();
//		head.setAppId("DEFAULT");
//		head.setVersion("1.0");
//		head.setReqType("open_card_request");
//		head.setMchid("20171211008");
//		head.setReqNo("test2017121101");
//		head.setSignType("RSA1");
//		head.setBackURL("http://ccp.x2x.cn/ccp/x2xpay/payCallBack.action");
//		data.setBankCard("4392260029812342");
//		data.setBankPhone("13787784135");
//		data.setRealName("胡文");
//		data.setIdCard("430703199402231652");
//		//
//		req.setData(data);
//		req.setHead(head);
//		EmptyRequest eres=signRequest(req,"RSA1","2017");
//		String reqxml=XMLUtils.toString(eres, EmptyRequest.class);
//		System.out.println(reqxml);
//		HttpPost post = new HttpPost("http://ccp.x2x.cn/ccp/x2xpay/doRequest.action");
//		StringEntity se = new StringEntity(reqxml,"utf-8");
//		post.setEntity(se);
//		HttpResponse response = ConnectionManager.getHttpClient().execute(post);
//		String res = EntityUtils.toString(response.getEntity(),"utf-8");
//		System.out.println(res);
//		String xml=verifyResponse(res,"RSA1");//验签、并返回解密后的请求内容
//		System.out.println(xml);
		
		
		
//		PrePayRequest.body data=new PrePayRequest.body();
//        ReqHead head=new ReqHead();
//        PrePayRequest req=new PrePayRequest();
//        head.setAppId("DEFAULT");
//        head.setVersion("1.0");
//        head.setReqType("pre_pay_request");
//        head.setMchid("20171211008");
//        head.setReqNo("test2017121136");
//        head.setSignType("RSA1");
//        head.setBackURL("http://124.232.133.199/ccp/x2xpay/payCallBack.action");
//        req.setData(data);
//        req.setHead(head);
//        data.setOriReqNo("test2017121135");
//        data.setSmsCode("148802");
        /*data.setMoney(1500);// setBankCard("4392260029812342");
        data.setSrcBankCard("4392260029812342");//setBankPhone("13787784135");
        data.setSrcCvv2("516");//setRealName("胡文");
        data.setSrcExpired("0720");//setIdCard("430703199402231652");
        data.setSrcIdCard("430703199402231652");
        data.setSrcRealName("胡文");
        data.setSrcBankPhone("13787784135");
        data.setTargetBankCard("6214833220523073");
        data.setTargetBankName("招商银行");
        data.setTargetIdCard("430703199402231652");
        data.setTargetRealName("胡文");
        data.setTargetBankPhone("13787784135");
        data.setSubject("化妆品");*/
//        EmptyRequest eres=signRequest(req,"RSA1","2017");
//        String reqxml=XMLUtils.toString(eres, EmptyRequest.class);
//        System.out.println(reqxml);
//        HttpPost post = new HttpPost("http://124.232.133.199/ccp/x2xpay/doRequest.action");
//        StringEntity se = new StringEntity(reqxml,"utf-8");
//        post.setEntity(se);
//        HttpResponse response = ConnectionManager.getHttpClient().execute(post);
//        String res = EntityUtils.toString(response.getEntity(),"utf-8");
//        System.out.println(res);
//        String xml=verifyResponse(res,"RSA1");//验签、并返回解密后的请求内容
//        System.out.println(xml);
		

		//		SubmitPayRequest.body data=new SubmitPayRequest.body();
//        ReqHead head=new ReqHead();
//        SubmitPayRequest req=new SubmitPayRequest();
//        head.setAppId("DEFAULT");
//        head.setVersion("1.0");
//        head.setReqType("submit_pay_request");
//        head.setMchid("20171211008");
//        head.setReqNo("test2017121136");
//        head.setSignType("RSA1");
//        head.setBackURL("http://124.232.133.199/ccp/x2xpay/payCallBack.action");
//        req.setData(data);
//        req.setHead(head);
//        data.setOriReqNo("test2017121135");
//        data.setSmsCode("148802");
//        /*data.setMoney(1500);// setBankCard("4392260029812342");
//        data.setSrcBankCard("4392260029812342");//setBankPhone("13787784135");
//        data.setSrcCvv2("516");//setRealName("胡文");
//        data.setSrcExpired("0720");//setIdCard("430703199402231652");
//        data.setSrcIdCard("430703199402231652");
//        data.setSrcRealName("胡文");
//        data.setSrcBankPhone("13787784135");
//        data.setTargetBankCard("6214833220523073");
//        data.setTargetBankName("招商银行");
//        data.setTargetIdCard("430703199402231652");
//        data.setTargetRealName("胡文");
//        data.setTargetBankPhone("13787784135");
//        data.setSubject("化妆品");*/
//        EmptyRequest eres=signRequest(req,"RSA1","2017");
//        String reqxml=XMLUtils.toString(eres, EmptyRequest.class);
//        System.out.println(reqxml);
//        HttpPost post = new HttpPost("http://124.232.133.199/ccp/x2xpay/doRequest.action");
//        StringEntity se = new StringEntity(reqxml,"utf-8");
//        post.setEntity(se);
//        HttpResponse response = ConnectionManager.getHttpClient().execute(post);
//        String res = EntityUtils.toString(response.getEntity(),"utf-8");
//        System.out.println(res);
//        String xml=verifyResponse(res,"RSA1");//验签、并返回解密后的请求内容
//        System.out.println(xml);

		/*
		String string = StringHelper.encode("6217002920116221418");
		System.out.println(string);
		String bs = StringHelper.decode(string);
		System.out.println(bs);//6217002920102115780
		*/
		
	}
	
	public static String verifyResponse(String reqdata,String signType)throws Exception{
		//1、解密
		Map<String,Object> map=xml2map(reqdata, false);
		String noDecodeData=map.get("data")+"";
		String datastr=decodeData(noDecodeData);
		Map<String,Object> data=xml2map("<data>"+datastr+"</data>", false);
		Map<String,Object> head=(Map)map.get("head");
		map.put("data", data);
		//2、验签
		StringBuffer b=new StringBuffer();
		SortedMap<String, String> sortMap=new TreeMap<String, String>();
		for(Map.Entry<String,Object> e: data.entrySet()){
			if("sign".equals(e.getKey())||null==e.getValue())continue;
			sortMap.put(e.getKey(), e.getValue()+"");
		}
		for(Map.Entry<String,Object> e: head.entrySet()){
			if("sign".equals(e.getKey())||null==e.getValue())continue;
			sortMap.put(e.getKey(), e.getValue()+"");
		}
		for(Map.Entry<String,String> e: sortMap.entrySet()){
			b.append(e.getValue());
		}
		String subMchPubKey=pubkey;//TODO:返回的签名用平台公钥验签
		
		boolean flag =false;
		if("MD5".equals(signType)){
			String md5sign=MD5.getInstance().createMD5(b.toString()+subMchPubKey);
			String sign=head.get("sign")+"";
			flag=sign.equals(md5sign);
		}else{
			flag=APISecurityUtils.verify(b.toString().getBytes("utf-8"),subMchPubKey, head.get("sign")+"");
		}
		if(!flag)throw new BaseException("0011");//验签失败
		System.out.println("验签成功");
		return reqdata.replace(noDecodeData, datastr);
	}
	//加签
	public static EmptyRequest signRequest(Object bean,String signType,String mchid)throws Exception{
		String xmlstr=XMLUtils.toString(bean, bean.getClass());
		//1、加签
		Map<String, Object> map=ApiHelper.xml2map(xmlstr, false);
		Map<String,Object> head=(Map)map.get("head");
		Map<String,Object> data=(Map)map.get("data");
		StringBuffer b=new StringBuffer();
		SortedMap<String, String> sortMap=new TreeMap<String, String>();
		if(null!=data){
			for(Map.Entry<String,Object> e: data.entrySet()){
				if("sign".equals(e.getKey())||null==e.getValue())continue;
				sortMap.put(e.getKey(), e.getValue()+"");
			}
		}
		for(Map.Entry<String,Object> e: head.entrySet()){
			if("sign".equals(e.getKey())||null==e.getValue())continue;
			sortMap.put(e.getKey(), e.getValue()+"");
		}
		for(Map.Entry<String,String> e: sortMap.entrySet()){
			b.append(e.getValue());
		}
		String sign=APISecurityUtils.sign(b.toString().getBytes("utf-8"), prikey);
		head.put("sign", sign);
		//2、加密
		String encodedata=encodeData(Matchers.match("<data>(.*)</data>", xmlstr.replace("\n", "").replace("\t", "")), mchid);
		EmptyRequest request=new EmptyRequest();
		BeanUtils.copyProperties(bean, request, new String[]{"data"});
		request.setData(encodedata);
		request.getHead().setSign(sign);
		return request;
	}
	/**
	 *	解密 
	 * @return
	 */
	public static String decodeData(String dataxml){
		if(StringUtils.isBlank(dataxml))return "";
		try {
			byte[] data=APISecurityUtils.decryptByPrivateKey(Base64Utils.decode(dataxml.getBytes("utf-8")), prikey);
			return new String(data,"utf-8");
		} catch (Exception e) {
			throw new BaseException("解密失败");
		}
	}
	/**
	 * 加密
	 * @param data
	 * @param mchid
	 * @return
	 */
	public static String encodeData(String dataxml,String mchid){
		if(StringUtils.isBlank(dataxml))return "";
		String subMchPubKey=pubkey;//TODO:商户用平台公钥加密数据
		try {
			byte[] datastr = APISecurityUtils.encryptByPublicKey(dataxml.toString().getBytes("UTF-8"), subMchPubKey);
			return new String(Base64Utils.encode(datastr),"UTF-8");
		} catch (Exception e) {
			e.printStackTrace();
			throw new BaseException("加密失败");
		}
	}
	/** 
     * xml转map 不带属性 
     * @param xmlStr 
     * @param needRootKey 是否需要在返回的map里加根节点键 
     * @return 
     * @throws DocumentException 
     */  
	public static Map xml2map(String xmlStr, boolean needRootKey)
			throws DocumentException {
		Document doc = DocumentHelper.parseText(xmlStr);
		Element root = doc.getRootElement();
		Map<String, Object> map = (Map<String, Object>) xml2map(root);
		if (root.elements().size() == 0 && root.attributes().size() == 0) {
			return map;
		}
		if (needRootKey) {
			// 在返回的map里加根节点键（如果需要）
			Map<String, Object> rootMap = new HashMap<String, Object>();
			rootMap.put(root.getName(), map);
			return rootMap;
		}
		return map;
	}
	/** 
     * xml转map 不带属性 
     * @param e 
     * @return 
     */ 
	private static Map xml2map(Element e) {
		Map map = new LinkedHashMap();
		List list = e.elements();
		if (list.size() > 0) {
			for (int i = 0; i < list.size(); i++) {
				Element iter = (Element) list.get(i);
				List mapList = new ArrayList();

				if (iter.elements().size() > 0) {
					Map m = xml2map(iter);
					if (map.get(iter.getName()) != null) {
						Object obj = map.get(iter.getName());
						if (!(obj instanceof List)) {
							mapList = new ArrayList();
							mapList.add(obj);
							mapList.add(m);
						}
						if (obj instanceof List) {
							mapList = (List) obj;
							mapList.add(m);
						}
						map.put(iter.getName(), mapList);
					} else
						map.put(iter.getName(), m);
				} else {
					if (map.get(iter.getName()) != null) {
						Object obj = map.get(iter.getName());
						if (!(obj instanceof List)) {
							mapList = new ArrayList();
							mapList.add(obj);
							mapList.add(iter.getText());
						}
						if (obj instanceof List) {
							mapList = (List) obj;
							mapList.add(iter.getText());
						}
						map.put(iter.getName(), mapList);
					} else
						map.put(iter.getName(), iter.getText());
				}
			}
		} else
			map.put(e.getName(), e.getText());
		return map;
	}
	
	/*
	public static void main(String[] args) throws Exception{
		byte[] x = APISecurityUtils.encryptByPublicKey("1212312312312312312312312312312312312312312312312312312312312312312312312312312312312312312312312312312312312312312312312312312312312312312312312312312331212312312312312312312312312312312312312312312312312312312312312312312312312312312312312312312312312312312312312312312312312312312312312312312312312312331212312312312312312312312312312312312312312312312312312312312312312312312312312312312312312312312312312312312312312312312312312312312312312312312312312331212312312312312312312312312312312312312312312312312312312312312312312312312312312312312312312312312312312312312312312312312312312312312312312312312312331212312312312312312312312312312312312312312312312312312312312312312312312312312312312312312312312312312312312312312312312312312312312312312312312312312331212312312312312312312312312312312312312312312312312312312312312312312312312312312312312312312312312312312312312312312312312312312312312312312312312312331212312312312312312312312312312312312312312312312312312312312312312312312312312312312312312312312312312312312312312312312312312312312312312312312312312331212312312312312312312312312312312312312312312312312312312312312312312312312312312312312312312312312312312312312312312312312312312312312312312312312312331212312312312312312312312312312312312312312312312312312312312312312312312312312312312312312312312312312312312312312312312312312312312312312312312312312331212312312312312312312312312312312312312312312312312312312312312312312312312312312312312312312312312312312312312312312312312312312312312312312312312312331212312312312312312312312312312312312312312312312312312312312312312312312312312312312312312312312312312312312312312312312312312312312312312312312312312331212312312312312312312312312312312312312312312312312312312312312312312312312312312312312312312312312312312312312312312312312312312312312312312312312312331212312312312312312312312312312312312312312312312312312312312312312312312312312312312312312312312312312312312312312312312312312312312312312312312312312331212312312312312312312312312312312312312312312312312312312312312312312312312312312312312312312312312312312312312312312312312312312312312312312312312312331212312312312312312312312312312312312312312312312312312312312312312312312312312312312312312312312312312312312312312312312312312312312312312312312312312331212312312312312312312312312312312312312312312312312312312312312312312312312312312312312312312312312312312312312312312312312312312312312312312312312312331212312312312312312312312312312312312312312312312312312312312312312312312312312312312312312312312312312312312312312312312312312312312312312312312312312331212312312312312312312312312312312312312312312312312312312312312312312312312312312312312312312312312312312312312312312312312312312312312312312312312312331212312312312312312312312312312312312312312312312312312312312312312312312312312312312312312312312312312312312312312312312312312312312312312312312312312331212312312312312312312312312312312312312312312312312312312312312312312312312312312312312312312312312312312312312312312312312312312312312312312312312312339J中文结尾9J中文结尾9J中文结尾9J中文结尾9J中文结尾9J中文结尾9J中文结尾9J中文结尾9J中文结尾9J中文结尾9J中文结尾9J中文结尾9J中文结尾9J中文结尾9J中文结尾9J中文结尾9J中文结尾9J中文结尾9J中文结尾".getBytes("UTF-8"), "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQC2IDG64CPNipfjOhezvYJT4lu5D4ilgeEvKFTlNv1x5yHEPtKY/BOtJYEWr6rqKv69avtUQD5XzEf6fw5o3pKG5vNGL9yj4/UjMF2C8fjaMEtEOsPOdW4jzSt5A+Wz+4AJ1EbP+CsrrS4Gb4fG7s4/aK5c3A9eal5q74kldKtuKwIDAQAB");
		System.out.println(new String(Base64Utils.encode(x),"UTF-8"));
		byte [] s=APISecurityUtils.decryptByPrivateKey(x, "MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBALYgMbrgI82Kl+M6F7O9glPiW7kPiKWB4S8oVOU2/XHnIcQ+0pj8E60lgRavquoq/r1q+1RAPlfMR/p/Dmjekobm80Yv3KPj9SMwXYLx+NowS0Q6w851biPNK3kD5bP7gAnURs/4KyutLgZvh8buzj9orlzcD15qXmrviSV0q24rAgMBAAECgYABy5Nx6h+wOPuCS+JL7URJm2OYEWUhbIRRuK4NjFs3MjYM/ymIRIPVIxeAp76hjEbyiwlrLS7wIp1bBMGZmwgKtB5TRp9LsJLi/TpV7KDidkFonaY9es6+qBfJkdjSOZPRnIy18F+Atcj3lS3r3HgfojNKTGQ1TJl6mt2NOyVU0QJBANiqiScHygiL65uPsFMkUeT9bXulBU79T5froHEKOlgv4oYUi7zLTK415IsSIhu6bynxgNztTAvtTEA0cQ4iW+UCQQDXMGiliKQDmoeV3Tev8jfK9YSxTq9farNhdRT0MEBqOxNYXz/W8HtqGBXyITgWc6xzeUsVH9EO4GUWTYiIZKDPAkEAnwn/BuFW0ZMzbMq6WS0t1/KWrM3i6apTBGb3LEKftR/hHR5zvC9WqAHzMooq80OUWatmNcURryeOcwqLeh6KaQJAGms5DcmeyUp5hu6n3ZQRQXuvFM2iPkatSzPCpNAfuGsUTu8yuULBBB984kMtzaPZ8jtb+nXzhq4J+xj9wDEKhQJBAMR8VTAcqqY70wNSEiE2DbvGb6n9wfjoyqI5JVXdkkM+GomeVnJ0BV6Tw7cG83pUkc9age00Y+h6wlbwBFwaoBs=");
		System.out.println(new String(s));
		
	}
	*/
	
}