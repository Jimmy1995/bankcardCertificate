package com.x2x.api;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.common.base.DBHelper;

public class MsgCode {
	private static Map<String,String> map=new HashMap<String,String>();
	public static int freshcount=0;
	/*static{
		map.put("0000","成功");
		map.put("0001","交易失败");
		map.put("0002","系统未开放或暂时关闭，请稍后再试");
		map.put("0003","交易通讯超时，请发起查询交易");
		map.put("0004","交易状态未明，请稍后查询");
		map.put("0005","交易状态未明，请稍后查询");
		map.put("0010","报文格式错误");
		map.put("0011","验证失败");
		map.put("0012","重复交易");
		map.put("0013","报文交易要素缺失");
		map.put("0020","二维码已失效");
		map.put("0021","支付次数超限");
		map.put("0022","二维码状态错误");
		map.put("0023","无此二维码");
		map.put("0030","交易未通过");
		map.put("0031","商户状态不正确");
		map.put("0032","无此交易权限");
		map.put("0033","交易金额超限");
		map.put("0034","无此交易");
		map.put("0037","已超过最大查询次数或操作过于频繁");
		map.put("0038","风险受限");
		map.put("0039","交易不在受理时间范围内");
		map.put("0060","交易失败，详情请咨询您的发卡行");
		map.put("0061","输入的卡号无效，请确认后输入");
		map.put("0062","交易失败，发卡银行不支持该商户，请更换其他银行卡");
		map.put("0063","卡状态不正确");
		map.put("0064","卡上的余额不足");
		map.put("0065","输入的密码、有效期或CVN2有误，交易失败");
		map.put("0066","持卡人身份信息或手机号输入不正确，验证失败");
		map.put("0067","密码输入次数超限");
		map.put("0068","您的银行卡暂不支持该业务，请向您的银行咨询");
		map.put("0099","通用错误");
		map.put("00A6","有缺陷的成功");
		map.put("PR05","审核中");
		map.put("PR99","审核失败");
		map.put("0100", "等待交易");
		map.put("MJ01", "商户入驻已成功提交，请耐心等待审核!");
	}*/
	public static String get(String keyname){
		if(freshcount<1){
			init();
		}
		return map.get(keyname);
	}
	public static void init(){
		map.clear();
		List<Map<String ,Object>> sys_respcode=DBHelper.queryForList("select * from sys_respcode where yxbz='1'");
		if(null!=sys_respcode&&sys_respcode.size()>0){
			for(Map mobj:sys_respcode){
				MsgCode.map.put(mobj.get("respcode")+"", mobj.get("respmsg")+"");
			}
		}
		freshcount++;
	}
	/*public static void main(String[] args) {
		for(Map.Entry<String, String> mobj:map.entrySet()){
			System.out.println("INSERT INTO `sys_respcode` (`respcode`, `respmsg`) VALUES ('"+mobj.getKey()+"', '"+mobj.getValue()+"');");
		}
	}*/
}