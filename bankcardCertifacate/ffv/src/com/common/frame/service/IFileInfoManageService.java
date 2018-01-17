package com.common.frame.service;

import com.common.base.service.ICommonService;
import com.common.frame.model.FileInfo;
import com.common.web.page.Page;

public interface IFileInfoManageService extends ICommonService<FileInfo>{
	public Page query(FileInfo obj);
}
