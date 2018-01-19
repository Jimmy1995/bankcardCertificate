package utils;


import java.io.Writer;
import java.util.List;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.serializer.SerializeFilter;
import com.alibaba.fastjson.serializer.SerializerFeature;


public class JsonUtils {
	private static ThreadLocal<SerializeFilter> propertyFilter = new ThreadLocal<SerializeFilter>();
	/** * 工具类需要的保护构造方法. */
	protected JsonUtils() {
	}
	/** 
	 * 把bean转换成json字符串，并写入writer.
	 * @param bean 实例
	 * @param writer 输出流
	 * @throws Exception 可能抛出任何异常
	 */
	public static void write(Object bean, Writer writer) throws Exception {
		String text=toString(bean);
		writer.write(text);
	}
	public static String toString(Object obj){
		SerializeFilter filter=propertyFilter.get();
		return JSON.toJSONString(obj,filter,SerializerFeature.WriteMapNullValue,SerializerFeature.WriteDateUseDateFormat);
	}
	public static <T> T deserialize(String json,Class<T> clazz){
		T obj=null;
		try {
			obj=(T)JSON.parseObject(json, clazz);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return obj;
	}
	public static <T> List<T> deserializeArray(String json,Class<T> clazz){
		List<T> obj=null;
		try {
			obj=JSON.parseArray(json, clazz);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return obj;
	}
	public static void setPropertyFilter(SerializeFilter propertyFilter) {
		JsonUtils.propertyFilter.set(propertyFilter);
	}
}