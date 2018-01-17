package com.x2x.api.utils;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import com.sun.org.apache.xerces.internal.impl.dv.util.Base64;
import com.x2x.api.extapi.ssbankcard.WxHelper;


public class StringHelper {
	/**
	 * 
	 * @param str
	 * @return
	 */
	public static String decode(String str){
		if(str.length()<26||StringUtils.isBlank(str))return str;
		str=new String(Base64.decode(str));
		str=str.substring(0, 4)+str.substring(12,17)+str.substring(25,str.length());
		return StringUtils.reverse(str);
	}
	
	public static String encode(String str){
		if(str.length()<10||StringUtils.isBlank(str))return str;
		str=StringUtils.reverse(str);
		str=str.substring(0, 4)+WxHelper.getRandomStringByLength(8)+str.substring(4,9)+WxHelper.getRandomStringByLength(8)+str.substring(9,str.length());
		return Base64.encode(str.getBytes());
	}
	
	/**
	 * 
	 * @DESC Bean 转换成map
	 * @AUTHOR JIANGCW
	 * @DATE 2018-1-11下午05:06:32
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static Map<String, Object> convertBean(Object bean) throws IntrospectionException,
			IllegalAccessException, InvocationTargetException {
		Class type = bean.getClass();
		Map returnMap = new HashMap();
		BeanInfo beanInfo = Introspector.getBeanInfo(type);

		PropertyDescriptor[] propertyDescriptors = beanInfo
				.getPropertyDescriptors();
		for (int i = 0; i < propertyDescriptors.length; i++) {
			PropertyDescriptor descriptor = propertyDescriptors[i];
			String propertyName = descriptor.getName();
			if (!propertyName.equals("class")) {
				Method readMethod = descriptor.getReadMethod();
				Object result = readMethod.invoke(bean, new Object[0]);
				if (result != null) {
					returnMap.put(propertyName, result);
				} else {
					// returnMap.put(propertyName, "");
				}
			}
		}
		returnMap.remove("empty");
		return returnMap;
	}
}