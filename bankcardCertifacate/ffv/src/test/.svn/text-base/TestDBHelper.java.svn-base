package test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;

import com.common.base.DBHelper;
import com.x2x.api.ApiConfig;
import com.x2x.api.ApiHelper;

import org.junit.Test;

import com.common.web.SpringContextHolder;
import com.x2x.api.service.IExecService;

public class TestDBHelper extends BaseTestTemplate {
	@Test
	public void test(){
		/*Map<String,List<String>> map=new HashMap<String,List<String>>();
		List<Map<String,Object>> mlist=DBHelper.queryForList("select * from sys_api_appauth");
		if(null==mlist)return;
		for(Map<String,Object> m:mlist){
			List<String> xlist=map.get(m.get("appId"));
			if(null==xlist){
				xlist=new ArrayList<String>();
			}
			xlist.add(m.get("reqType")+"");
			map.put(m.get("appId")+"", xlist);
		}
=======
	 private IExecService getService(){ return SpringContextHolder.getBean("yopen_card_request"); }
	
	@Test
	public void test(){
		//-----------------------------测试商户入驻接口-------------------------------------------
//		String str = "<xml><head><appId>DEFAULT</appId><version>1.0</version><reqType>ydojoin_request</reqType>" +
//					 "<mchid></mchid>" +
//					 "<reqNo>20180102001</reqNo>" +
//					 "<backURL></backURL>" +
//					 "<signType></signType>" +
//				 	 "<sign>abc</sign></head>" +
//				 	 "<data>" +
//				 	//开通商户信息
//					 "<busName>甜蜜蛋糕屋</busName>" +
//					 "<busShortName>甜蜜屋</busShortName>" +
//					 "<proxy01id>2</proxy01id>" +
//					 "<chid>2</chid>" +
//					 "<rateCode>1000010</rateCode>" +
//					 "<payMinLimitPerTrans>100000</payMinLimitPerTrans>" +
//					 "<payMaxLimitPerTrans>2000000</payMaxLimitPerTrans>" +
//					 "<payMinLimitPerDay>0</payMinLimitPerDay>" +
//					 "<payMaxLimitPerDay>5000000</payMaxLimitPerDay>" +
//					//身份证信息
//					 "<cardId>430703199402231652</cardId>" +
//					 "<idName>胡文</idName>" +
//					 "<mobileNumber>13787784135</mobileNumber>" +
//					 "<cardStartTime>20101010</cardStartTime>" +
//					 "<cardEndTime>20191009</cardEndTime>" +
//					//储蓄卡银行卡信息保存
//					 "<creditBankcode>308</creditBankcode>" +
//					 "<creditBankname>招商银行</creditBankname>" +
//					 "<creditCardType01>10</creditCardType01>" +
//					 "<creditCardType02>20</creditCardType02>" +
//					 "<creditAccountNo>6214833220523073</creditAccountNo>" +
//					 "<creditPhoneNo>13787784135</creditPhoneNo>" +
//					 "<creditCardBusType>20</creditCardBusType>" +
//					 "<creditSubBranchName>长沙岳麓支行</creditSubBranchName>" +
//					 "<creditBankCountry>430100</creditBankCountry>" +
//					 "<unionNumber>308584000013</unionNumber>" +
//					//手续费信息
//					 "<isPayProcessType>10</isPayProcessType>" +
//					 "<payProcess>60</payProcess>" +
//					 "<isDrawPorcessType>10</isDrawPorcessType>" +
//					 "<drawPorcess>1</drawPorcess>" +
//					 "<drawPorcessFee>200</drawPorcessFee>" +
//					//扩展字段
//					 "<extend1></extend1>" +
//					 "<extend2></extend2>" +
//					 "<extend3></extend3>" +
//					 "<extend4></extend4>" +
//					 "<extend5></extend5></data></xml>";
//		try {
//			getService().exec(str);
//			
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
>>>>>>> .r222
		
<<<<<<< .mine
		List<String> reqTypes=map.get("1001");
		System.out.println(reqTypes.contains("open_card_request"));*/
		System.out.println(ApiConfig.veriyAppIds("RSA1_"+"20171211008", "1001"));
		ApiHelper.veriyAppAuth("1003", "open_card_request");
		
		
		
		
		//-----------------------------测试侧板卡接口-------------------------------------------
		String str = "<xml><head><appId>DEFAULT</appId><version>1.0</version><reqType>yopen_card_request</reqType>" +
		 "<mchid>484584045119381</mchid>" +
		 "<reqNo>20180102002</reqNo>" +
		 "<backURL></backURL>" +
		 "<signType></signType>" +
	 	 "<sign>abc</sign></head>" +
	 	 "<data>" +
		//储蓄卡银行卡信息保存
		 "<debitBankcode>308</debitBankcode>" +
		 "<debitBankname>招商银行</debitBankname>" +
		 "<debitCardType01>20</debitCardType01>" +
		 "<debitCardType02>20</debitCardType02>" +
		 "<debitAccountNo>4392260029812342</debitAccountNo>" +
		 "<debitAccountName>胡文</debitAccountName>" +
		 "<debitCertId>430703199402231652</debitCertId>" +
		 "<debitPhoneNo>13787784135</debitPhoneNo>" +
		 "<debitCardBusType>10</debitCardBusType>" +
		 "<debitCvv2>516</debitCvv2>" +
		 "<debitExpired>0720</debitExpired>" +
		 "</data></xml>";
				try {
					//getService().exec(str);
				} catch (Exception e) {
					e.printStackTrace();
				}
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		//-----------------------------测试消费接口-------------------------------------------
//		String str = "<xml><head><appId>DEFAULT</appId><version>1.0</version><reqType>ysubmit_pay_request</reqType>" +
//					 "<mchid></mchid>" +
//					 "<reqNo>20171229001</reqNo>" +
//					 "<backURL></backURL>" +
//					 "<signType></signType>" +
//				 	 "<sign>abc</sign></head>" +
//				 	 "<data>" +
//				 	 
//					 "<money>200</money>" +
//					 "<accountNo>4392260029812342</accountNo>" +
//					 
//					 "</data></xml>";
//		try {
//			getService().exec(str);
//		
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
		
	}
		
}
