package com.x2x.util.test;

import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.BeanUtils;

import com.x2x.util.BankCardAuthRequest;
import com.x2x.util.BaseException;
import com.x2x.util.ConnectionManager;
import com.x2x.util.EmptyRequest;
import com.x2x.util.MD5;
import com.x2x.util.Matchers;
import com.x2x.util.ReqHead;
import com.x2x.util.SignUtil;
import com.x2x.util.XMLUtils;

/**
 * 
 * @DESC 
 * @AUTHOR JIANGCW
 * @DATE 2018-1-12下午03:02:07
 */
public class AuthTest {
	public static void main(String[] args) throws Exception{
		//银行卡实名验证
		ReqHead head=new ReqHead();
		head.setAppId("1001");
		head.setVersion("1.0");
		head.setReqType("ssbankcard_request");
        head.setMchid("20171211008");//商户号
        head.setReqNo("test201801208");//请求流水号
        head.setSignType("RSA1");
        head.setBackURL("");//异步通知地址可不填
        BankCardAuthRequest.body data = new BankCardAuthRequest.body();
        data.setBankCard("");//银行卡号
        data.setRealName("");//开户人姓名
        data.setIdCard("");//开户人身份证号
        data.setBankPhone("");//银行预留手机号
        data.setServiceCode("");//302：银行卡三要素实名认证  303：银行卡四要素实名认证
        
		BankCardAuthRequest req =new BankCardAuthRequest();
		req.setHead(head);
		req.setData(data);
		
		EmptyRequest eres=signRequest(req,"RSA1","SS18011061");
		String reqxml=XMLUtils.toString(eres, EmptyRequest.class);
		System.out.println("reqxml:"+reqxml);
		HttpPost post = new HttpPost("http://127.0.0.1:8080/ffv/x2xpay/doRequest.action");//远程接口地址
		StringEntity se = new StringEntity(reqxml,"utf-8");
		post.setEntity(se);
		HttpResponse response = ConnectionManager.getHttpClient().execute(post);
		String res = EntityUtils.toString(response.getEntity(),"utf-8");
		System.out.println("resStr:"+res);
		String xml=verifyResponse(res,"RSA1");//验签、并返回解密后的请求内容
		System.out.println(xml);
		
		
	}
	
	//加签
	public static EmptyRequest signRequest(Object bean,String signType,String mchid)throws Exception{
		String xmlstr=XMLUtils.toString(bean, bean.getClass());
		//1、加签
		Map map=SignUtil.xml2map(xmlstr, false);
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
		String sign=SignUtil.sign(b.toString().getBytes("utf-8"), SignUtil.prikey);
		head.put("sign", sign);
		//2、加密
		String encodedata=SignUtil.encodeData(Matchers.match("<data>(.*)</data>", xmlstr.replace("\n", "").replace("\t", "")), mchid);
		EmptyRequest request=new EmptyRequest();
		BeanUtils.copyProperties(bean, request, new String[]{"data"});
		request.setData(encodedata);
		request.getHead().setSign(sign);
		/*Object s=bean.getClass().getMethod("getHead").invoke(bean);
		request.setData(encodedata);
		request.setHead((ReqHead)s);
		request.getHead().setSign(sign);*/
		return request;
	}
	
	public static String verifyResponse(String reqdata,String signType)throws Exception{
		//1、解密
		Map<String,Object> map=SignUtil.xml2map(reqdata, false);
		String noDecodeData=map.get("data")+"";
		String datastr=SignUtil.decodeData(noDecodeData);
		Map<String,Object> data=SignUtil.xml2map("<data>"+datastr+"</data>", false);
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
		String subMchPubKey=SignUtil.pubkey;//TODO:返回的签名用平台公钥验签
		
		boolean flag =false;
		if("MD5".equals(signType)){
			String md5sign=MD5.getInstance().createMD5(b.toString()+subMchPubKey);
			String sign=head.get("sign")+"";
			flag=sign.equals(md5sign);
		}else{
			flag=SignUtil.verify(b.toString().getBytes("utf-8"),subMchPubKey, head.get("sign")+"");
		}
		if(!flag)throw new BaseException("0011");//验签失败
		System.out.println("验签成功");
		return reqdata.replace(noDecodeData, datastr);
	}
	
	
}
