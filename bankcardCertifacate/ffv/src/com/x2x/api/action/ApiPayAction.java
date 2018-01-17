package com.x2x.api.action;
import java.io.PrintWriter;
import java.io.StringWriter;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;

import com.common.base.BaseAction;
import com.common.base.BaseException;
import com.common.base.DBHelper;
import com.common.util.WebUtils;
import com.common.util.xml.XMLUtils;
import com.common.web.SpringContextHolder;
import com.x2x.api.ApiHelper;
import com.x2x.api.MsgCode;
import com.x2x.api.model.res.EmptyResponse;
import com.x2x.api.service.IExecService;
import com.x2x.api.utils.LocalIP;
import com.x2x.api.utils.Matchers;
import com.x2x.api.model.ResHead;
/**
 * 统一对外支付服务接口
 * @author longzy
 */
@Namespace("/x2xpay")
public class ApiPayAction extends BaseAction<Object>{
	private static final long serialVersionUID = -5587864312649141869L;
	Log log=LogFactory.getLog(ApiPayAction.class);
	@Override
	public String list() throws Exception {return null;}
	/**
	 * 对外提供服务方法
	 */
	@Action(value="doRequest")
	public void doRequest(){
		String sigType="RSA1";
		String mchid="";
		try{
			String reqdata=WebUtils.inputStream2String(request.getInputStream());//抽取请求数据
			log.info(reqdata);
			String reqType = Matchers.match("<reqType>(.*)</reqType>", reqdata);
			sigType = Matchers.match("<signType>(.*)</signType>", reqdata);
			mchid = Matchers.match("<mchid>(.*)</mchid>", reqdata);
			if(StringUtils.isBlank(sigType))sigType="RSA1";
			String decodeReqData=ApiHelper.verifyRequest(reqdata,request,sigType);//验签、解密、检查流水是否重复
			if(null!=reqType){
				IExecService service=SpringContextHolder.getBean(reqType);
				if(null==service)throw new BaseException("0034");//无此交易
				LocalIP.setIp(WebUtils.getIP(request));//当前终端的访问IP
				Object o=service.exec(decodeReqData);
				if(StringUtils.isBlank(BeanUtils.getProperty(o, "head.respMsg"))){
					BeanUtils.setProperty(o, "head.respMsg", MsgCode.get(BeanUtils.getProperty(o, "head.respCd")));
				}
				BeanUtils.setProperty(o, "head.respNo", DBHelper.getMaxId("apires"));
				EmptyResponse empres=ApiHelper.signResponse(o,sigType,mchid);//加签
				String xml=XMLUtils.toString(empres,EmptyResponse.class);
				log.info(xml);
				response.setCharacterEncoding("utf-8");
				response.getWriter().write(xml);
			}
		}catch(org.dom4j.DocumentException de){//xml格式错误
			de.printStackTrace();
            StringWriter sw = new StringWriter();
            de.printStackTrace(new PrintWriter(sw, true));
            log.error(sw.toString());
            
			response.setCharacterEncoding("utf-8");
			ResHead head=new ResHead();
			head.setRespNo(DBHelper.getMaxId("apires"));
			head.setRespCd("0013");
			head.setRespMsg("报文格式错误");
			EmptyResponse res=new EmptyResponse(head, null);
			try {
				EmptyResponse empres=ApiHelper.signResponse(res,sigType,mchid);//加签
				String xml=XMLUtils.toString(empres,EmptyResponse.class);
				log.info(xml);
				response.getWriter().write(xml);
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}catch(Exception e){//其它错误
			e.printStackTrace();
			response.setCharacterEncoding("utf-8");
			String value=MsgCode.get(e.getMessage());
			ResHead head=new ResHead();
			head.setRespNo(DBHelper.getMaxId("apires"));
			if(StringUtils.isBlank(value)){
				head.setRespCd("0099");
				head.setRespMsg(e.getMessage());
			}else{
				head.setRespCd(e.getMessage());
				head.setRespMsg(value);
			}
			EmptyResponse res=new EmptyResponse(head, null);
			try {
				EmptyResponse empres=ApiHelper.signResponse(res,sigType,mchid);//加签
				String xml=XMLUtils.toString(empres,EmptyResponse.class);
				log.info(xml);
				response.getWriter().write(xml);
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
	}
}