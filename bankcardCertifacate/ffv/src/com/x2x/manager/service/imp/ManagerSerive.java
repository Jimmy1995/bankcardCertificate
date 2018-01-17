package com.x2x.manager.service.imp;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;

import com.common.util.DateUtil;
import com.common.util.MD5;
import com.common.web.SpringContextHolder;
import com.common.web.quartz.QuartzManager;
import com.x2x.api.ApiConfig;
import com.x2x.api.InteConfig;
import com.x2x.api.MsgCode;
import com.x2x.manager.service.IManagerService;
@Service
public class ManagerSerive implements IManagerService{
	Log log=LogFactory.getLog(ManagerSerive.class);
	@Override
	public String refreshCache(HttpServletRequest request,HttpServletResponse response) {
		String appkey = InteConfig.getInteConfig().get("appKey");
        String sign = request.getParameter("sign");
        String ggg = MD5.getInstance().createMD5(appkey + DateUtil.dateToString(new Date(), "YYYYMMDDHHMM"));
        if (ggg.equals(sign)) {
            InteConfig.freshcount = 0;
            ApiConfig.freshcount=0;
            MsgCode.freshcount=0;
            SpringContextHolder.registerBean4DataBase();// 刷新bean
            QuartzManager.start4DBInit();// 刷新job
            log.info("系统缓存刷新成功！");
        } else {
            log.info("签名不正确刷新失败！");
        }
        return "go404";
	}
	
}
