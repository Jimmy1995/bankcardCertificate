/**
 * Copyright (c) 2005-2009 springside.org.cn
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * 
 * $Id: ServletUtils.java 1048 2010-04-17 11:23:09Z calvinxiu $
 */
package com.common.util;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.StringTokenizer;
import java.util.TreeMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;


/**
 * Http与Servlet工具类.
 * 
 * @author calvin
 */
public class ServletUtils {

	//-- Content Type 定义 --//
	public static final String TEXT_TYPE = "text/plain";
	public static final String JSON_TYPE = "application/json";
	public static final String XML_TYPE = "text/xml";
	public static final String HTML_TYPE = "text/html";
	public static final String JS_TYPE = "text/javascript";
	public static final String EXCEL_TYPE = "application/vnd.ms-excel";

	//-- Header 定义 --//
	public static final String AUTHENTICATION_HEADER = "Authorization";

	//-- 常用数值定义 --//
	public static final long ONE_YEAR_SECONDS = 60 * 60 * 24 * 365;

	/**
	 * 设置客户端缓存过期时间 Header.
	 */
	public static void setExpiresHeader(HttpServletResponse response, long expiresSeconds) {
		//Http 1.0 header
		response.setDateHeader("Expires", System.currentTimeMillis() + expiresSeconds * 1000);
		//Http 1.1 header
		response.setHeader("Cache-Control", "private, max-age=" + expiresSeconds);
	}

	/**
	 * 设置客户端无缓存Header.
	 */
	public static void setNoCacheHeader(HttpServletResponse response) {
		//Http 1.0 header
		response.setDateHeader("Expires", 0);
		response.addHeader("Pragma", "no-cache");
		//Http 1.1 header
		response.setHeader("Cache-Control", "no-cache");
	}

	/**
	 * 设置LastModified Header.
	 */
	public static void setLastModifiedHeader(HttpServletResponse response, long lastModifiedDate) {
		response.setDateHeader("Last-Modified", lastModifiedDate);
	}

	/**
	 * 设置Etag Header.
	 */
	public static void setEtag(HttpServletResponse response, String etag) {
		response.setHeader("ETag", etag);
	}

	/**
	 * 根据浏览器If-Modified-Since Header, 计算文件是否已被修改.
	 * 
	 * 如果无修改, checkIfModify返回false ,设置304 not modify status.
	 * 
	 * @param lastModified 内容的最后修改时间.
	 */
	public static boolean checkIfModifiedSince(HttpServletRequest request, HttpServletResponse response,
			long lastModified) {
		long ifModifiedSince = request.getDateHeader("If-Modified-Since");
		if ((ifModifiedSince != -1) && (lastModified < ifModifiedSince + 1000)) {
			response.setStatus(HttpServletResponse.SC_NOT_MODIFIED);
			return false;
		}
		return true;
	}

	/**
	 * 根据浏览器 If-None-Match Header, 计算Etag是否已无效.
	 * 
	 * 如果Etag有效, checkIfNoneMatch返回false, 设置304 not modify status.
	 * 
	 * @param etag 内容的ETag.
	 */
	public static boolean checkIfNoneMatchEtag(HttpServletRequest request, HttpServletResponse response, String etag) {
		String headerValue = request.getHeader("If-None-Match");
		if (headerValue != null) {
			boolean conditionSatisfied = false;
			if (!"*".equals(headerValue)) {
				StringTokenizer commaTokenizer = new StringTokenizer(headerValue, ",");

				while (!conditionSatisfied && commaTokenizer.hasMoreTokens()) {
					String currentToken = commaTokenizer.nextToken();
					if (currentToken.trim().equals(etag)) {
						conditionSatisfied = true;
					}
				}
			} else {
				conditionSatisfied = true;
			}

			if (conditionSatisfied) {
				response.setStatus(HttpServletResponse.SC_NOT_MODIFIED);
				response.setHeader("ETag", etag);
				return false;
			}
		}
		return true;
	}

	/**
	 * 设置让浏览器弹出下载对话框的Header.
	 * 
	 * @param fileName 下载后的文件名.
	 */
	public static void setFileDownloadHeader(HttpServletResponse response, String fileName) {
		try {
			//中文文件名支持
			String encodedfileName = new String(fileName.getBytes(), "ISO8859-1");
			response.setHeader("Content-Disposition", "attachment; filename=\"" + encodedfileName + "\"");
		} catch (UnsupportedEncodingException e) {
		}
	}

	/**
	 * 取得带相同前缀的Request Parameters.
	 * 
	 * 返回的结果Parameter名已去除前缀.
	 */
	@SuppressWarnings("unchecked")
	public static Map getParametersStartingWith(HttpServletRequest request, String prefix) {
		Enumeration paramNames = request.getParameterNames();
		Map params = new TreeMap();
		if (prefix == null) {
			prefix = "";
		}
		while (paramNames != null && paramNames.hasMoreElements()) {
			String paramName = (String) paramNames.nextElement();
			if ("".equals(prefix) || paramName.startsWith(prefix)) {
				String unprefixed = paramName.substring(prefix.length());
				String[] values = request.getParameterValues(paramName);
				if (values == null || values.length == 0) {//NOSONAR
					// Do nothing, no values found at all.
				} else if (values.length > 1) {
					params.put(unprefixed, values);
				} else {
					params.put(unprefixed, values[0]);
				}
			}
		}
		return params;
	}
	/**
	 * 将从request（request.getParamMap()）中传过来的参数变了一个分层次对象
	 * 相当于struts2自动属性注入功能
	 * （得到MAP后，可以用JSONUTIL转换为json然后再转换为相应的bean对象）
	 * @param map(request.getParamMap())
	 * @param prefix(参数的前缀)
	 * @param separator(分隔符，默认为null,当此符号有值时，就把内容当成数组分隔)
	 * @param separatorTargets 要被当成数组分隔的字段名集合（短名，不是从头点到尾那种，当为null时，所有字段都分隔）
	 * @return
	 */
	public static Map paramMapTransform(Map<String,Object> map,String prefix,String separator,String[] separatorTargets){
		Map<String,Object> prefixMap=new HashMap<String,Object>();
		Iterator<Entry<String, Object>>  mit=map.entrySet().iterator();
		while(mit.hasNext()){//填充MAP
			Entry<String, Object> e=mit.next();
			if(!e.getKey().startsWith(prefix)){//删除不是以prefix开头的
				mit.remove();continue;
			}
			
			String sx=e.getKey();
			if(!StringUtils.isEmpty(prefix)){//不为空就执行替换
				String repstr=prefix.replace("[","\\[").replace("]", "\\]");//注意转义的问题比如".","],"["等
				sx=e.getKey().replaceFirst(repstr+"\\.", "");
				if(e.getKey().equals(sx)){//如果没有替换到，那就表示没有“.”，这时候只要替换前缀就行了
					sx=sx.replaceFirst(repstr, "");
				}
			}
			if(sx.indexOf(".")!=-1){//存在下一层StringUtils.isEmpty(prefix)||
				String nextPrefix=prefix+"."+sx.split("\\.")[0];//默认取前缀加“.”后的第一个
				if(StringUtils.isEmpty(prefix)){//不为空就取“.”前第一个
					nextPrefix=sx.split("\\.")[0];
				}
				prefixMap.put(sx.split("\\.")[0], paramMapTransform((Map)ObjUtlis.clone(map),nextPrefix,separator,separatorTargets));
			}else{
				Object v=e.getValue();
				if(v.getClass().isArray()){//request中的参数是数组类型传递过来的
					 v=((String[])v)[0];
				}
				prefixMap.put(e.getKey().split("\\.")[e.getKey().split("\\.").length-1], v);
			}
		}
		
		Iterator<Entry<String, Object>>  listIt=prefixMap.entrySet().iterator();
		Map<String,Object> midMap=new HashMap<String,Object>();
		while(listIt.hasNext()){//合成数组类型，判断替换后缀（【0】、【1】）后，是否相同。相同的就变成一个数组
			Entry<String, Object> e=listIt.next();
			String key=e.getKey().replaceAll("\\[[0-9]+\\]", "");
			if(!key.equals(e.getKey())){//如果有后缀（【0】、【1】），就添加为list类型，并且把当前的key/valeu从MAP中删除
				List<Object> list=new ArrayList<Object>();
				if(midMap.containsKey(key)){
					list=(List)midMap.get(key);
				}
				list.add(e.getValue());
				midMap.put(key, list);
				listIt.remove();
			}else if(separator!=null&&(null==separatorTargets||ArrayUtils.contains(separatorTargets, key))){//用分隔符分隔
				String[] vs=(e.getValue()+"").split(separator);
				midMap.put(key, vs);
				listIt.remove();
			}
		}
		prefixMap.putAll(midMap);
		return prefixMap;
	}
	
//	public static void main(String[] args){
//		Map map=new HashMap();
//		map.put("news.article[1].url", 2);
//		map.put("news.article[10].url", 24);
//		map.put("news.article[0].title", "ti");
//		map.put("news.article[0].ss", "ti");
//		map.put("news.article[1].xx[0].dd", 2);
//		map.put("news.article[1].xx[0].cc", "cc0");
//		map.put("news.article[1].xx[1].dd", "dc1");
//		map.put("news.article[1].xx[1].cc", "cc1,33");
//		map.put("news.news.cc", "newsnewscc");
//		map.put("cid", "cid");
//		Map result=paramMapTransform(map, "news",null,null);
//		Map result1=paramMapTransform(map, "news",",",new String[]{"cc"});
//		System.out.println(JsonUtils.toString(result));
//		//System.out.println(JsonUtils.toString(result1));
//	}
}
