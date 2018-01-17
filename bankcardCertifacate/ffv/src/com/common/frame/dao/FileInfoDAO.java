package com.common.frame.dao;

import java.io.File;

import org.springframework.stereotype.Repository;

import com.common.base.dao.CommonDAO;
import com.common.frame.model.FileInfo;
import com.common.util.FileUtils;
import com.common.web.filter.init.InitManage;
@Repository
public class FileInfoDAO extends CommonDAO<FileInfo>{
	@Override
	public boolean delete(FileInfo obj) {
		boolean flag = this.handler.deleteObj(obj);
		if(flag){
			FileUtils.delete(InitManage.getProperty("uploadRoot")+File.separator+obj.getFilePath());
		}
		return flag;
	}
}
