package com.common.frame.service.imp;

import java.util.Date;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import com.common.base.DBHelper;
import com.common.base.dao.ICommonDAO;
import com.common.base.service.CommonService;
import com.common.frame.model.GenNumber;
import com.common.frame.service.IGenNumberService;
import com.common.util.DateUtil;
@Service
public class GenNumberService extends CommonService<GenNumber> implements IGenNumberService {
	private static final long serialVersionUID = 7777296180996885563L;
	@Resource(name="genNumberDAO")
	private  ICommonDAO<GenNumber> genNumberDAO;
	@Override
	protected ICommonDAO<GenNumber> getDAO() {
		return genNumberDAO;
	}
	/**
	 * 根据一个字符串得到一个流水号;如果没有就从0开始,每查询一次都在原来的基础上加1
	 */
	public String applySerializeNumber(String gentype) {
		GenNumber genNumber=DBHelper.queryForBean("select * from sys_gennumber where genType=?", GenNumber.class,gentype);//genNumberDAO.findById(gentype);
		if(null==genNumber){
			genNumber=new GenNumber();
			genNumber.setGenType(gentype);
			genNumber.setEndNumber(1l);
			//genNumberDAO.save(genNumber);
			DBHelper.insert("sys_gennumber", genNumber);
			return "1";
		}else{
			Long endNumber=(genNumber.getEndNumber()+1);
			genNumber.setEndNumber(endNumber);
			//genNumberDAO.alter(genNumber);
			DBHelper.update("sys_gennumber", genNumber, new String[]{"genType"},new String[]{"endNumber"});
			String gf=genNumber.getGenFunction();
			if(null!=gf&&!"".equals(gf)){
				String ymdstr=DateUtil.dateToString(new Date(), "yyyyMMdd");
				int i= gf.indexOf("|");
				int gflength=gf.length();
				if(i>0){
					gf=StringUtils.substring(gf, 0, i+1)+StringUtils.leftPad(endNumber+"",gflength-i-1,"0");
				}else{
					gf=StringUtils.leftPad(endNumber+"",gflength,"0");
				}
				return gf.replace("|", "").replace("YYYYMMDD", ymdstr);
			}else{
				return endNumber+"";
			}
		}
	}

}
