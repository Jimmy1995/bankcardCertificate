package com.common.web.datasource;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

import org.apache.log4j.Logger;
import org.springframework.aop.AfterReturningAdvice;
import org.springframework.aop.ThrowsAdvice;
import org.springframework.util.StringUtils;

public class DataSourceAfterAdvice implements AfterReturningAdvice,ThrowsAdvice {
	private Logger log=Logger.getLogger(DataSourceAfterAdvice.class);
	/**
	 * 后拦截，清除无错误情况下的当前线程信息(回复默认DataSource)。
	 */
	public void afterReturning(Object returnObj, Method method, Object[] args,
			Object targetObj) throws Throwable {
		clear(method);
		log.info(new StringBuffer().append("**************<<<<退出方法{").append(targetObj.getClass()).append(".").append(method.getName()).append("(").append(StringUtils.arrayToCommaDelimitedString(args)).append(")}>>>>>********"));
	}

	/**
	 * 异常拦截，清除存在异常情况下的当前线程信息(回复默认DataSource)。
	 * 
	 * @param method
	 * @param args
	 * @param target
	 * @param throwable
	 */
	public void afterThrowing(Method method, Object[] args, Object target,
			RuntimeException throwable) {
		clear(method);
	}

	private void clear(Method method) {
		// 销毁当前线程数据源
		Annotation[] annotation = method.getAnnotations();
		for (Annotation tag : annotation) {
			if(tag instanceof DataSource){
				if (((DataSource) tag).name() != null)
					DataSourceHolder.clearCustomerType();
			}
		}
	}
}