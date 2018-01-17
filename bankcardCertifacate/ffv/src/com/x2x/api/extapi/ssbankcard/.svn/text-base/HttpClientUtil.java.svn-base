package com.x2x.api.extapi.ssbankcard;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import com.alibaba.fastjson.JSON;
import com.songshun.sdk.entity.RespEntity;
import com.songshun.sdk.http.HttpRequestClient;
import com.songshun.sdk.sign.SignatureUtil;

/**
 * 
 * @DESC 银行卡实名认证接口发起http请求工具类 
 * @AUTHOR JIANGCW
 * @DATE 2018-1-10下午04:30:49
 */
public class HttpClientUtil {
    /**
     * 发起http-get请求
     * @param map
     * @throws Exception
     */
    public static Map<String, Object> invoke(Map<String, Object> map) throws Exception{
        List<NameValuePair> parameters = new ArrayList<NameValuePair>();
        if (map.size() > 0) {
            for (Map.Entry<String, Object> entry : map.entrySet()) {
                String value=String.valueOf( entry.getValue()).trim();
                if(value!=null && value!="null") {
                    // post 提交参数
                    parameters.add(new BasicNameValuePair(entry.getKey(),value));
                }
            }
        }
        //请求地址
        String url = map.get("url")+"";
        //签名 
        String signKey = map.get("signKey")+"";
        String signature=SignatureUtil.sign("MD5", map, signKey, "MD5");
        System.out.println("reqMap:"+JSON.toJSONString(map));
        System.out.println("signature: "+signature);
        //拼接请求地址
        url= MessageFormat.format(url+"/{0}/{1}/{2}/{3}/{4}", map.get("version"),
                map.get("accCode"), map.get("accessKeyId"),signature,map.get("timestamp"));
        System.out.println( String.format("调用服务开始 ，url：%s",url) );
       
        /*String resStr = com.x2x.api.extapi.yuqian.HttpClientUtil.doPost(url,map);
		System.out.println("收到内容---"+resStr);
		Map<String,Object> resMap = JSON.parseObject(resStr,Map.class);
        return resMap;*/
        
        RespEntity respEntity =null;
        try {
            respEntity = HttpRequestClient.post_http(url+"", parameters, "UTF-8");
        } catch (Exception e) {
            e.printStackTrace();
        }
        //响应参数封装入Map
        String jsonStr = JSON.toJSONString(respEntity);
        Map<String,Object> resMap = JSON.parseObject(jsonStr);
        return resMap;
    }
    
}
