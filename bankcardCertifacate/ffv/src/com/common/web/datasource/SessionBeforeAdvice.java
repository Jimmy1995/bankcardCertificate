package com.common.web.datasource;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

import org.apache.log4j.Logger;
import org.springframework.aop.MethodBeforeAdvice;
import org.springframework.util.StringUtils;
public class SessionBeforeAdvice implements MethodBeforeAdvice {
	private Logger log=Logger.getLogger(SessionBeforeAdvice.class);
	public void before(Method method, Object[] args, Object targetObj)throws Throwable {
		/**
		 * 根据注释参数设置当前DataSource
		 */
		log.info(new StringBuffer().append("**************<<<<进入方法{").append(targetObj.getClass()).append("."+method.getName()).append("(").append(StringUtils.arrayToCommaDelimitedString(args)).append(")}>>>>>********"));
		Annotation[] annotation = method.getAnnotations();
		for (Annotation tag : annotation) {
			if(tag instanceof DataSource){
				String temp = ((DataSource) tag).name();
				if (temp != null) {
					DataSourceHolder.setCustomerType(temp);
				}
			}
		}
	}
}