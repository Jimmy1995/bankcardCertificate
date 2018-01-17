package com.common.frame.service.imp;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import com.common.base.DAOHelper;
import com.common.base.dao.ICommonDAO;
import com.common.base.service.CommonService;
import com.common.frame.model.FileInfo;
import com.common.frame.service.IFileInfoManageService;
import com.common.web.page.Page;
@Service
public class FileInfoManageService extends CommonService<FileInfo> implements IFileInfoManageService{
	private static final long serialVersionUID = -7405306270811051083L;
	@Resource(name="fileInfoDAO")
	private  ICommonDAO<FileInfo> fileInfoDAO;
	@Override
	protected ICommonDAO<FileInfo> getDAO() {
		return fileInfoDAO;
	}
	public Page query(FileInfo obj) {
		String hql="select f from FileInfo f where 1=1";
		if(obj!=null){
			if(!StringUtils.isEmpty(obj.getDescription())){
				hql+=" and f.description like '%"+obj.getDescription()+"%'";
			}
			if(!StringUtils.isEmpty(obj.getFileName())){
				hql+=" and f.fileName like '%"+obj.getFileName()+"%'";
			}
			if(!StringUtils.isEmpty(obj.getEct1())){
				if(obj.getEct1().equals("image")){//微信中，image和thumb用的是同一图片
					hql+=" and (f.ect1 = '"+obj.getEct1()+"' or f.ect1='thumb')";
				}else {
					hql+=" and f.ect1 = '"+obj.getEct1()+"'";
				}
			}
			hql+=" order by f.createTime desc";
		}
		return DAOHelper.queryPageByHQL(hql);
	}
}
