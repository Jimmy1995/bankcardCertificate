package com.common.util.bean;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.collections.map.LinkedMap;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class BeanHelper {
	private static Log log=LogFactory.getLog(BeanHelper.class);
	/**
	 * 拷贝属性<br/>
	 * 有如下2个优势<br/>
	 * 1. 不会像apache的BeanUtils那样,给Long这种封装类型在为null时设值为0<br/>
	 * 2. 不会像spring的BeanHelper那样不会自动转换类型<br/>
	 * 
	 * @param source
	 *            源
	 * @param target
	 *            目标
	 */
	public static void copyProperties(Object source, Object target) {
		PropertyDescriptor[] sourceProperties = PropertyUtils.getPropertyDescriptors(source);
		for (int i = 0; i < sourceProperties.length; i++) {
			try {
				String sourcefieldName = sourceProperties[i].getName();
				Object sourcefieldValue = PropertyUtils.getProperty(source,
						sourcefieldName);
				PropertyHelper.setProperty(target, sourcefieldName,
						sourcefieldValue);
			} catch (Exception e) {
				log.debug(e.getMessage());
			}
		}
	}
	/**
	 * 
	 * @param source 源
	 * @param target 目标
	 * @param ignoreProperties 忽略的属性
	 */
	public static void copyProperties(Object source, Object target, String[] ignoreProperties){
		PropertyDescriptor[] sourceProperties = PropertyUtils.getPropertyDescriptors(source);
		boolean f=false;
		for (int i = 0; i < sourceProperties.length; i++) {
			try {
				String sourcefieldName = sourceProperties[i].getName();
				f=false;
				if(ignoreProperties!=null&&ignoreProperties.length>0){
					for(String s:ignoreProperties){
						if(sourcefieldName.equals(s)){f=true; break;}
					}
					if(f)continue;
				}
				Object sourcefieldValue = PropertyUtils.getProperty(source,
						sourcefieldName);
				PropertyHelper.setProperty(target, sourcefieldName,
						sourcefieldValue);
			} catch (Exception e) {
				log.debug(e.getMessage());
			}
		}
	}
	/**
	 * 直接在两个List之间拷贝属性
	 * 
	 * @param source
	 *            源
	 * @param target
	 *            目标
	 * @param targetGenricType
	 *            target参数中的对象类型V.class
	 */
	public static <K, V> void copyPropertiesForList(List<K> source,
			List<V> target, Class targetGenricType) {
		boolean flag = false;
		if (source != null) {
			if (target == null) {
				target = new ArrayList<V>();
			} else if (target.size() >= source.size()) {
				flag = true;
				Iterator iter = target.iterator();
				while (iter.hasNext()) {
					if (beanPropertitesIsEmpty(iter.next())) {
						iter.remove();
					}
				}
			}
			if (source.size() > 0) {
				int i = 0;
				for (K sourceobj : source) {
					if (sourceobj != null) {
						try {
							if (!beanPropertitesIsEmpty(sourceobj)) {
								if (flag) {
									copyProperties(sourceobj, target.get(i));
								} else {
									V obj = (V) targetGenricType.newInstance();
									copyProperties(sourceobj, obj);
									target.add(obj);
								}
							}
						} catch (InstantiationException e) {
							e.printStackTrace();
						} catch (IllegalAccessException e) {
							e.printStackTrace();
						}
					}
					i++;
				}
			}
		}
	}

	/**
	 * 判断bean中的属性是否全为空
	 * 
	 * @param bean
	 * @return
	 */
	public static boolean beanPropertitesIsEmpty(Object bean) {
		Method[] methods = bean.getClass().getMethods();
		if (methods != null && methods.length > 0) {
			for (Method method : methods) {
				if (method.getName().startsWith("get")
						&& !method.getName().equals("getClass")) {
					try {
						if (method.invoke(bean) != null)
							return false;
					} catch (IllegalArgumentException e) {
						e.printStackTrace();
						log.debug(e.getMessage());
					} catch (IllegalAccessException e) {
						e.printStackTrace();
						log.debug(e.getMessage());
					} catch (InvocationTargetException e) {
						e.printStackTrace();
						log.debug(e.getMessage());
					}
				}
			}
		}
		return true;
	}

	// 用来遍历对象属性和属性值
	public static Map<String, Object> getBeanAttr(Object bean)throws Exception {
		if(bean instanceof Map){
			return (Map)bean;
		}
		if(bean instanceof Object[]){
			Map map= ArrayUtils.toMap((Object[])bean);
			return map;
		}
		if(bean instanceof Collection){
			Map map= ArrayUtils.toMap(((Collection)bean).toArray());
			return map;
		}
		Field[] fields = bean.getClass().getDeclaredFields();
		Map<String, Object> map = new HashMap<String, Object>();
		for (Field field : fields) {
			String m=Modifier.toString(field.getModifiers());
			if(m.contains("static")||m.contains("final")) continue;
			field.setAccessible(true);
			map.put(field.getName(), field.get(bean));
		}
		return map;
	}
	public static LinkedMap getBeanAttrToLinkedMap(Object bean)throws Exception {
		if(bean instanceof Map){
			return (LinkedMap)bean;
		}
		if(bean instanceof Object[]){
			LinkedMap map= (LinkedMap)ArrayUtils.toMap((Object[])bean);
			return map;
		}
		if(bean instanceof Collection){
			LinkedMap map= (LinkedMap)ArrayUtils.toMap(((Collection)bean).toArray());
			return map;
		}
		Field[] fields = bean.getClass().getDeclaredFields();
		LinkedMap map = new LinkedMap();
		for (Field field : fields) {
			String m=Modifier.toString(field.getModifiers());
			if(m.contains("static")||m.contains("final")) continue;
			field.setAccessible(true);
			map.put(field.getName(), field.get(bean));
		}
		return map;
	}
}
