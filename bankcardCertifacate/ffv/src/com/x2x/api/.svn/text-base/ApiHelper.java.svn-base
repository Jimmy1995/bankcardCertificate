package com.x2x.api;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.springframework.beans.BeanUtils;

import com.common.base.BaseException;
import com.common.base.DBHelper;
import com.common.util.MD5;
import com.common.util.WebUtils;
import com.common.util.xml.XMLUtils;
import com.x2x.api.action.ApiPayAction;
import com.x2x.api.model.res.EmptyResponse;
import com.x2x.api.utils.APISecurityUtils;
import com.x2x.api.utils.Base64Utils;
import com.x2x.api.utils.Matchers;
import com.x2x.manager.model.CcpTradeCallBack;

public class ApiHelper {
	static Log log=LogFactory.getLog(ApiHelper.class);
	static final Pattern p = Pattern.compile("_[a-z]");
	public static String verifyRequest(String reqdata,HttpServletRequest request,String signType)throws Exception{
		//String decodeDataXml="";
		//1、解密
		Map<String,Object> map=xml2map(reqdata, false);
		String noDecodeData=map.get("data")+"";
		String datastr=decodeData(noDecodeData);
		Map<String,Object> data=xml2map("<data>"+datastr+"</data>", false);
		Map<String,Object> head=(Map)map.get("head");
		map.put("data", data);
		log.info("解密成功,明文如下:"+reqdata.replace(noDecodeData, datastr));
		//2、验签
		Object mchid=head.get("mchid");
		Object reqNo=head.get("reqNo");
		Object appId=head.get("appId");
		Object version=head.get("version");
		String reqType = Matchers.match("<reqType>(.*)</reqType>", reqdata);
		if(null==mchid||null==reqNo||null==appId||null==version)throw new BaseException("0013");//缺乏要素
		if(StringUtils.isBlank(appId+"")||StringUtils.isBlank(version+"")||StringUtils.isBlank(mchid+"")||StringUtils.isBlank(reqNo+""))throw new BaseException("0013");//缺乏要素
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
		String subMchPubKey=ApiConfig.getSubMchPubKey(signType+"_"+head.get("mchid"));
		String standip=ApiConfig.getSafeIp(signType+"_"+head.get("mchid"));
		if(!StringUtils.isBlank(standip)){
			String ip=WebUtils.getIP((HttpServletRequest)request);
			if(!standip.equals(ip))throw new BaseException("ip 地址受限");
		}
		
		boolean flag =false;
		if("MD5".equals(signType)){
			String md5sign=MD5.getInstance().createMD5(b.toString()+subMchPubKey);
			String sign=head.get("sign")+"";
			flag=sign.equals(md5sign);
		}else{
			flag=APISecurityUtils.verify(b.toString().getBytes("utf-8"),subMchPubKey, head.get("sign")+"");
		}
		if(!flag)throw new BaseException("0011");//验签失败
		log.info("验签成功");
		int i=DBHelper.queryForInt("select count(1) from ffv_trade01 a where a.busMchid=? and a.downTransNo=?",mchid,reqNo);
		if(i>0)throw new BaseException("0012");//重复交易（该商户请求流水号不唯一）
		//保存异步通知地址
		Object backurl = head.get("backURL");
		if(null!=backurl&&!StringUtils.isBlank(backurl+"")&&!reqType.contains("query_")&&!reqType.contains("submit_pay_request")){
			CcpTradeCallBack back=new CcpTradeCallBack();
			back.setBackurl(backurl+"");
			back.setRuncount(0);
			back.setReqNo(reqNo+"");
			back.setBcode(mchid+"");
			back.setStatus("0");
			back.setStartdate(new Date());
			try{
				DBHelper.insert("ffv_trade_callback", back);
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		//3、验appid对应的reqType权限
		boolean isv=ApiConfig.veriyAppIds(signType+"_"+head.get("mchid"), appId+"");
		if(!isv)throw new BaseException("此商户无appid权限");
		veriyAppAuth(appId+"",reqType);
		//4、返回解密后的完整报文
		return reqdata.replace(noDecodeData, datastr);
	}
	//加签
	public static EmptyResponse signResponse(Object bean,String signType,String mchid)throws Exception{
		String xmlstr=XMLUtils.toString(bean, bean.getClass());
		//1、加签
		Map map=ApiHelper.xml2map(xmlstr, false);
		Map<String,Object> head=(Map)map.get("head");
		StringBuffer b=new StringBuffer();
		SortedMap<String, String> sortMap=new TreeMap<String, String>();
		if(bean instanceof EmptyResponse){//空报体不用加签，只能头加签
			
		}else{
			Map<String,Object> data=(Map)map.get("data");
			if(null!=data){
				for(Map.Entry<String,Object> e: data.entrySet()){
					if("sign".equals(e.getKey())||null==e.getValue())continue;
					sortMap.put(e.getKey(), e.getValue()+"");
				}
			}
		}
		for(Map.Entry<String,Object> e: head.entrySet()){
			if("sign".equals(e.getKey())||null==e.getValue())continue;
			sortMap.put(e.getKey(), e.getValue()+"");
		}
		for(Map.Entry<String,String> e: sortMap.entrySet()){
			b.append(e.getValue());
		}
		String sign="";
		if("MD5".equals(signType)){
			String key=ApiConfig.getSubMchPubKey(signType+"_"+mchid);
			sign=MD5.getInstance().createMD5(b.toString()+key);
		}else{
			sign=APISecurityUtils.sign(b.toString().getBytes("utf-8"), ApiConfig.getSubMchPrivateKey());
		}
		head.put("sign", sign);
		//2、加密
		String encodedata=encodeData(Matchers.match("<data>(.*)</data>", xmlstr.replace("\n", "").replace("\t", "")), mchid);
		EmptyResponse response=new EmptyResponse();
		BeanUtils.copyProperties(bean, response, new String[]{"data"});
		response.setData(encodedata);
		response.getHead().setSign(sign);
		return response;
	}
	/**
	 *	解密 
	 * @return
	 */
	public static String decodeData(String dataxml){
		if(StringUtils.isBlank(dataxml))return "";
		try {
			byte[] data=APISecurityUtils.decryptByPrivateKey(Base64Utils.decode(dataxml.getBytes("utf-8")), ApiConfig.getSubMchPrivateKey());
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
		String subMchPubKey=ApiConfig.getSubMchPubKey("RSA1_"+mchid);
		try {
			byte[] datastr = APISecurityUtils.encryptByPublicKey(dataxml.toString().getBytes("utf-8"), subMchPubKey);
			return new String(Base64Utils.encode(datastr),"UTF-8");
		} catch (Exception e) {
			throw new BaseException("加密失败");
		}
	}
	public static void veriyAppAuth(String appId,String reqType){
		String[] appids=appId.split(",");
		boolean f=false;
		for(String aid:appids){
			List<String> reqtypelist=ApiConfig.getReqTypeList(aid);
			if(null!=reqtypelist&&reqtypelist.contains(reqType)){
				f=true;
				break;
			}
		}
		if(!f){
			throw new BaseException("appId无对应的访问的权限");
		}
	}
	/** 
     * xml转map 不带属性 
     * @param xmlStr 
     * @param needRootKey 是否需要在返回的map里加根节点键 
     * @return 
     * @throws DocumentException 
     */  
	public static Map<String, Object> xml2map(String xmlStr, boolean needRootKey)
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
	private static Map<String,Object> xml2map(Element e) {
		Map<String,Object> map = new LinkedHashMap<String,Object>();
		List<Object> list = e.elements();
		if (list.size() > 0) {
			for (int i = 0; i < list.size(); i++) {
				Element iter = (Element) list.get(i);
				List<Object> mapList = new ArrayList<Object>();

				if (iter.elements().size() > 0) {
					Map<String,Object> m = xml2map(iter);
					if (map.get(iter.getName()) != null) {
						Object obj = map.get(iter.getName());
						if (!(obj instanceof List)) {
							mapList = new ArrayList<Object>();
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
							mapList = new ArrayList<Object>();
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