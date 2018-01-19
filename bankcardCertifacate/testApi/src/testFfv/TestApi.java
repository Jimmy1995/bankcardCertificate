package testFfv;
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

import utils.APISecurityUtils;
import utils.Base64Utils;
import utils.ConnectionManager;
import utils.MD5;
import utils.Matchers;
import utils.XMLUtils;

public class TestApi{
	private static String prikey="";//商户私钥
	private static String pubkey="";//平台公钥
	private static String jkURL="";//接口地址
	public static void main(String[] args) throws Exception{
		//银行卡实名验证
		String reqNo = System.currentTimeMillis()+"";
		ReqHead head=new ReqHead();
		head.setAppId("1001");
		head.setVersion("1.0");
		head.setReqType("ssbankcard_request");
        head.setMchid("S668000000000016");//商户号 //S668000000000016
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
		System.out.println(reqxml);
		HttpPost post = new HttpPost(jkURL);
		StringEntity se = new StringEntity(reqxml,"utf-8");
		post.setEntity(se);
		HttpResponse response = ConnectionManager.getHttpClient().execute(post);
		String res = EntityUtils.toString(response.getEntity(),"utf-8");
		System.out.println(res);
		String xml=verifyResponse(res,"RSA1");//验签、并返回解密后的请求内容
		System.out.println(xml);
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
		String subMchPubKey=pubkey;
		
		boolean flag =false;
		if("MD5".equals(signType)){
			String md5sign=MD5.getInstance().createMD5(b.toString()+subMchPubKey);
			String sign=head.get("sign")+"";
			flag=sign.equals(md5sign);
		}else{
			flag=APISecurityUtils.verify(b.toString().getBytes("utf-8"),subMchPubKey, head.get("sign")+"");
		}
		if(!flag){
			System.out.println("验签失败");//验签失败
			return null;
		}
		System.out.println("验签成功");
		return reqdata.replace(noDecodeData, datastr);
	}
	//加签
	public static EmptyRequest signRequest(Object bean,String signType,String mchid)throws Exception{
		String xmlstr=XMLUtils.toString(bean, bean.getClass());
		//1、加签
		Map map=xml2map(xmlstr, false);
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
		Object s=bean.getClass().getMethod("getHead").invoke(bean);
		request.setData(encodedata);
		request.setHead((ReqHead)s);
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
			System.out.println("解密失败");
		}
		return null;
	}
	/**
	 * 加密
	 * @param data
	 * @param mchid
	 * @return
	 */
	public static String encodeData(String dataxml,String mchid){
		if(StringUtils.isBlank(dataxml))return "";
		String subMchPubKey=pubkey;
		try {
			byte[] datastr = APISecurityUtils.encryptByPublicKey(dataxml.toString().getBytes("UTF-8"), subMchPubKey);
			return new String(Base64Utils.encode(datastr),"UTF-8");
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("解密失败");
		}
		return null;
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
}