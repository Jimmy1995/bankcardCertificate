package com.common.base.dao;

import javax.annotation.Resource;

import com.common.base.handler.IHandler;

public class BaseDAO implements IBaseDAO{
	@Resource(name="springHandler")
	protected IHandler handler;
	public IHandler getHandler() {
		return handler;
	}
	public void setHandler(IHandler handler) {
		this.handler = handler;
	}
}
