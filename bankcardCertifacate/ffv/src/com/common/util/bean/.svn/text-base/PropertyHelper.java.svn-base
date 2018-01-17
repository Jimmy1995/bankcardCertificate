package com.common.util.bean;

import java.beans.PropertyDescriptor;
import java.math.BigDecimal;
import java.util.Date;

import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;

import com.common.base.BaseException;

public class PropertyHelper {

	
	/**
	 * 自动转换类型设置参数
	 * @param bean
	 * @param field
	 * @param value
	 */
	public static void setProperty(final Object bean, final String field,
			Object value) {
		try {
			
			PropertyDescriptor dis = org.apache.commons.beanutils.PropertyUtils.getPropertyDescriptor(bean,
					field);

			if (dis == null) {
				return;
			}
			String nameType = dis.getPropertyType().getSimpleName();

			Object setValue = null;
			if (nameType.equalsIgnoreCase("BigDecimal")) {
				if (NumberUtils.isNumber(ObjectUtils.toString(value))) {
					setValue = stringToBigDecimal(ObjectUtils.toString(value));
					org.apache.commons.beanutils.PropertyUtils.setProperty(bean, field, setValue);
				}
			} else if (nameType.equalsIgnoreCase("String")) {
				setValue = ObjectUtils.toString(value);
				org.apache.commons.beanutils.PropertyUtils.setProperty(bean, field, setValue);
			} else if (nameType.equalsIgnoreCase("Integer")) {
				setValue = ObjectUtils.toString(value);

				if (StringUtils.isBlank((String) setValue)) {
					org.apache.commons.beanutils.PropertyUtils.setProperty(bean, field, null);
				} else {
					if (NumberUtils.isNumber(ObjectUtils.toString(value))) {
						setValue = Integer.parseInt((String) setValue);
						org.apache.commons.beanutils.PropertyUtils.setProperty(bean, field, setValue);
					}
				}
			} else if (nameType.equalsIgnoreCase("Boolean")) {
				org.apache.commons.beanutils.PropertyUtils.setProperty(bean, field,
						Boolean.valueOf(ObjectUtils.toString(value)));
			} else if (nameType.equalsIgnoreCase("Long")) {

				if (NumberUtils.isNumber(ObjectUtils.toString(value))) {
					org.apache.commons.beanutils.PropertyUtils.setProperty(bean, field,
							Long.valueOf(ObjectUtils.toString(value)));
				}

			} else if (nameType.equalsIgnoreCase("Float")) {
				if (NumberUtils.isNumber(ObjectUtils.toString(value))) {
					org.apache.commons.beanutils.PropertyUtils.setProperty(bean, field,
							Float.valueOf(ObjectUtils.toString(value)));
				}

			} else if (nameType.equalsIgnoreCase("Double")) {
				if (NumberUtils.isNumber(ObjectUtils.toString(value))) {
					org.apache.commons.beanutils.PropertyUtils.setProperty(bean, field,
							Double.valueOf(ObjectUtils.toString(value)));
				}
			} else if (nameType.equalsIgnoreCase("Short")) {

				if (NumberUtils.isNumber(ObjectUtils.toString(value))) {
					org.apache.commons.beanutils.PropertyUtils.setProperty(bean, field,
							Short.valueOf(ObjectUtils.toString(value)));
				}
			}  else if (nameType.equalsIgnoreCase("Date")) {
				if( value instanceof Date){
					org.apache.commons.beanutils.PropertyUtils.setProperty(bean, field,value);
				}
			} else {
				if (!"Class".equals(nameType)) {
					throw new BaseException("不支持的类型" + nameType);
				}
			}

		} catch (Exception e) {
			throw new BaseException(e);
		}
	}
	
	/** 比较2个object值，相等为true，不相等为false
	 * @param first
	 * @param last
	 * @return
	 */
	public static boolean compareObj(Object srcValue, Object changedValue) {
		if (srcValue == null) {
			if(changedValue != null&&StringUtils.isNotBlank(changedValue.toString())){
				return false;
			}else {
				return true;
			}
		}else {
			return srcValue.equals(changedValue);
		}
	}
	

	public static BigDecimal stringToBigDecimal(String bigStr) {
		if (StringUtils.isBlank(bigStr)) {
			return BigDecimal.ZERO;
		} else {
			return new BigDecimal(bigStr);
		}
	}
}
