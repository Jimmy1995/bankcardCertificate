/**
 * 
 */
package com.common.web.quartz;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.MethodInvokingJobDetailFactoryBean.StatefulMethodInvokingJob;

import com.common.frame.dao.FileInfoDAO;
import com.common.frame.model.FileInfo;
import com.common.util.DateUtil;
import com.common.web.SpringContextHolder;
/**
 * @author 龙志勇
 * 清理附件表中的垃圾（只清理当天的）
 * 加油加油加油
 */
public class QuartzJobOne extends StatefulMethodInvokingJob {
	private static FileInfoDAO fileInfoDAO= SpringContextHolder.getBean("fileInfoDAO");
	@Override
	protected void executeInternal(JobExecutionContext context)throws JobExecutionException {
		DetachedCriteria query=DetachedCriteria.forClass(FileInfo.class,"fileInfo");
		query.add(Restrictions.ge("createTime",DateUtil.stringToDate(DateUtil.datetimeByString().substring(0, 10),"yyyy-MM-dd")));
		List<FileInfo> fileInfos=fileInfoDAO.findList(query);
		for(FileInfo f:fileInfos){
			fileInfoDAO.delete(f);
		}
	}
}
