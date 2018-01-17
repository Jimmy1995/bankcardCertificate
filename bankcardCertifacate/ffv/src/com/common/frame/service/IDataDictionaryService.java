package com.common.frame.service;

import java.util.Map;

import com.common.base.service.ICommonService;
import com.common.frame.model.DataDictionary;
import com.common.web.page.Page;

public interface IDataDictionaryService extends ICommonService<DataDictionary>{
	/**
	 * 分页查询
	 * @param 
	 * @param 
	 * @return page
	 */
	public Page findDataDictionarysByPage(DataDictionary dic,Map param);
}
