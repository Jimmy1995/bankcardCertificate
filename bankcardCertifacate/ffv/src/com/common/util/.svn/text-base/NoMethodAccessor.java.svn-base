package com.common.util;

import java.util.Map;

import ognl.MethodAccessor;
import ognl.MethodFailedException;

public class NoMethodAccessor implements MethodAccessor {

	public NoMethodAccessor() {
	}

	public Object callStaticMethod(Map context, Class targetClass,
			String methodName, Object[] args) throws MethodFailedException {
		throw new MethodFailedException("do not run", "123");
	}

	public Object callMethod(Map context, Object target, String methodName,
			Object[] args) throws MethodFailedException {
		// TODO Auto-generated method stub
		throw new MethodFailedException("do not run", "123");
	}

}
