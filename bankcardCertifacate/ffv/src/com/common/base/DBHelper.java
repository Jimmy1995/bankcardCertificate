package com.common.base;

import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;

import org.apache.commons.lang3.ArrayUtils;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import com.common.SysConstants;
import com.common.base.handler.IHandler;
import com.common.frame.service.IGenNumberService;
import com.common.util.bean.BeanHelper;
import com.common.web.SpringContextHolder;
import com.common.web.page.Page;
import com.common.web.page.PageUtil;
/**
 * 
 * @author longzy
 *
 */
public class DBHelper {
	private static JdbcTemplate helper = SpringContextHolder.getBean("jdbcTemplate");
	private static IHandler handler=SpringContextHolder.getBean("springHandler");
	private static IGenNumberService gennumber=SpringContextHolder.getBean(IGenNumberService.class);
	public static int[] batchUpdate(String[] sql) {
		return helper.batchUpdate(sql);
	}
	public static void execute(String sql) {
		helper.execute(sql);
	}
	public static int queryForInt(String sql, Object... args) {
		return helper.queryForInt(sql, args);
	}
	public static int queryForInt(String sql ,Map param){
		List args=new ArrayList();
		sql=transfromMap(sql, param,args);
		return helper.queryForInt(sql, args.toArray());
	}
	public static int queryForInt(String sql, Object[] args, int[] argTypes) {
		return helper.queryForInt(sql, args, argTypes);
	}
	public static int queryForInt(String sql)  {
		return helper.queryForInt(sql);
	}
	public static <T> List<T> queryBeanList(String sql, Class<T> requiredType,Object... args)  {
		return helper.query(sql,new BeanPropertyRowMapper<T>(requiredType),args);
	}
	public static <T> List<T> queryBeanList(String sql, Class<T> requiredType,Map param)  {
		List args=new ArrayList();
		sql=transfromMap(sql, param,args);
		return helper.query(sql,new BeanPropertyRowMapper<T>(requiredType),args.toArray());
	}
	public static <T> List<T> queryBeanList(String sql, Class<T> requiredType) {
		return helper.query(sql,new BeanPropertyRowMapper<T>(requiredType));
	}
	public static List<Map<String, Object>> queryForList(String sql,Object... args) {
		return helper.queryForList(sql, args);
	}
	public static List<Map<String, Object>> queryForList(String sql,Map param) {
		List args=new ArrayList();
		sql=transfromMap(sql, param,args);
		return helper.queryForList(sql, args.toArray());
	}
	public static <T> List<T> queryBeanList(String sql, Object[] args,Class<T> requiredType) {
		return helper.query(sql,args,new BeanPropertyRowMapper<T>(requiredType));
	}
	public static <T> List<T> queryBeanList(String sql,Map param,Class<T> requiredType)  {
		List args=new ArrayList();
		sql=transfromMap(sql, param,args);
		return helper.query(sql,args.toArray(),new BeanPropertyRowMapper<T>(requiredType));
	}
	public static <T> List<T> queryForList(String sql, Object[] args,int[] argTypes, Class<T> requiredType)  {
		return helper.query(sql,args,new BeanPropertyRowMapper<T>(requiredType));
	}
	public static List<Map<String, Object>> queryForList(String sql,Object[] args, int[] argTypes)  {
		return helper.queryForList(sql, args, argTypes);
	}
	public static List<Map<String, Object>> queryForList(String sql) {
		return helper.queryForList(sql);
	}
	public static long queryForLong(String sql, Object... args) {
		return helper.queryForLong(sql, args);
	}
	public static long queryForLong(String sql, Map param) {
		List args=new ArrayList();
		sql=transfromMap(sql, param,args);
		return helper.queryForLong(sql, args.toArray());
	}
	public static long queryForLong(String sql, Object[] args, int[] argTypes) {
		return helper.queryForLong(sql, args, argTypes);
	}
	public static long queryForLong(String sql)  {
		return helper.queryForLong(sql);
	}
	public static Map<String, Object> queryForMap(String sql, Object... args) {
		Map<String, Object> map;
		try {
			map = helper.queryForMap(sql, args);
		} catch (EmptyResultDataAccessException e) {
			return null;
		} catch (IncorrectResultSizeDataAccessException e1) {
			throw e1;
		}
		return map;
	}
	public static Map<String, Object> queryForMap(String sql, Map param) {
		Map<String, Object> map;
		try {
			List args=new ArrayList();
			sql=transfromMap(sql, param,args);
			map = helper.queryForMap(sql, args.toArray());
		} catch (EmptyResultDataAccessException e) {
			return null;
		} catch (IncorrectResultSizeDataAccessException e1) {
			throw e1;
		}
		return map;
	}
	public static Map<String, Object> queryForMap(String sql, Object[] args,int[] argTypes)  {
		Map<String, Object> map;
		try {
			map = helper.queryForMap(sql, args, argTypes);
		} catch (EmptyResultDataAccessException e) {
			return null;
		} catch (IncorrectResultSizeDataAccessException e1) {
			throw e1;
		}
		return map;
	}
	public static Map<String, Object> queryForMap(String sql) {
		Map<String, Object> map;
		try {
			map = helper.queryForMap(sql);
		} catch (EmptyResultDataAccessException e) {
			return null;
		} catch (IncorrectResultSizeDataAccessException e1) {
			throw e1;
		}
		return map;
	}
	public static <T> T queryForSigleResult(String sql, Class<T> requiredType,Object... args)  {
		T t;
		try {
			t = helper.queryForObject(sql, requiredType, args);
		} catch (EmptyResultDataAccessException e) {
			return null;
		} catch (IncorrectResultSizeDataAccessException e1) {
			throw e1;
		}
		return t;
	}
	public static <T> T queryForSigleResult(String sql, Class<T> requiredType) {
		T t;
		try {
			t = helper.queryForObject(sql, requiredType);
		} catch (EmptyResultDataAccessException e) {
			return null;
		} catch (IncorrectResultSizeDataAccessException e1) {
			throw e1;
		}
		return t;
	}
	public static <T> T queryForSigleResult(String sql, Object[] args,Class<T> requiredType)  {
		T t;
		try {
			t = helper.queryForObject(sql, args, requiredType);
		} catch (EmptyResultDataAccessException e) {
			return null;
		} catch (IncorrectResultSizeDataAccessException e1) {
			throw e1;
		}
		return t;
	}
	public static <T> T queryForSigleResult(String sql, Map param,Class<T> requiredType)  {
		T t;
		try {
			List args=new ArrayList();
			sql=transfromMap(sql, param,args);
			t = helper.queryForObject(sql, args.toArray(), requiredType);
		} catch (EmptyResultDataAccessException e) {
			return null;
		} catch (IncorrectResultSizeDataAccessException e1) {
			throw e1;
		}
		return t;
	}
	public static <T> T queryForSigleResult(String sql, Object[] args,int[] argTypes, Class<T> requiredType)  {
		T t;
		try {
			t = helper.queryForObject(sql, args, argTypes, requiredType);
		} catch (EmptyResultDataAccessException e) {
			return null;
		} catch (IncorrectResultSizeDataAccessException e1) {
			throw e1;
		}
		return t;
	}
	public static <T> T queryForBean(String sql, Object[] args,int[] argTypes, Class<T> requiredType)  {
		T t;
		try {
			BeanPropertyRowMapper<T> rowMapper=new BeanPropertyRowMapper<T>(requiredType);
			t = helper.queryForObject(sql, args, argTypes, rowMapper);
		} catch (EmptyResultDataAccessException e) {
			return null;
		} catch (IncorrectResultSizeDataAccessException e1) {
			throw e1;
		}
		return t;
	}
	public static <T> T queryForBean(String sql, Object[] args, Class<T> requiredType)  {
		T t;
		try {
			BeanPropertyRowMapper<T> rowMapper=new BeanPropertyRowMapper<T>(requiredType);
			t = helper.queryForObject(sql, args, rowMapper);
		} catch (EmptyResultDataAccessException e) {
			return null;
		} catch (IncorrectResultSizeDataAccessException e1) {
			throw e1;
		}
		return t;
	}
	public static <T> T queryForBean(String sql, Class<T> requiredType,Object... args)  {
		T t;
		try {
			BeanPropertyRowMapper<T> rowMapper=new BeanPropertyRowMapper<T>(requiredType);
			t = helper.queryForObject(sql, rowMapper, args);
		} catch (EmptyResultDataAccessException e) {
			return null;
		} catch (IncorrectResultSizeDataAccessException e1) {
			throw e1;
		}
		return t;
	}

	public static <T> T queryForBean(String sql, Class<T> requiredType) {
		T t;
		try {
			BeanPropertyRowMapper<T> rowMapper=new BeanPropertyRowMapper<T>(requiredType);
			t = helper.queryForObject(sql, rowMapper);
		} catch (EmptyResultDataAccessException e) {
			return null;
		} catch (IncorrectResultSizeDataAccessException e1) {
			throw e1;
		}
		return t;
	}
	public static int update(String sql, Object... args) {
		return helper.update(sql, args);
	}
	public static int update(String sql, Map param) {
		List args=new ArrayList();
		sql=transfromMap(sql, param,args);
		return helper.update(sql, args.toArray());
	}
	public static int update(String sql, Object[] args, int[] argTypes) {
		return helper.update(sql, args, argTypes);
	}
	public static int update(String sql)  {
		return helper.update(sql);
	}
	public static int update(String tableName,Object bean,String[] wheres){
		return update(tableName,bean,wheres,null);
	}
	
	public static int update(String tableName,Object bean,String[] wheres,String[] updates){
		try {
			Map<String, Object> beanMap = BeanHelper.getBeanAttr(bean);
			Object[] obj = new Object[null==updates?beanMap.size():updates.length+wheres.length];
			int s = 0;
			StringBuffer updatestr = new StringBuffer();
			StringBuffer wherestr=new StringBuffer();
			for (Map.Entry<String, Object> set : beanMap.entrySet()) {
				//System.out.println(set.getKey());
				if(ArrayUtils.contains(wheres, set.getKey())){
					continue;
				}
				if(null!=updates&&!ArrayUtils.contains(updates, set.getKey())){
					continue;
				}
				if (s > 0) {
					updatestr.append(",");
				}
				updatestr.append(set.getKey());
				updatestr.append("=?");
				obj[s] = set.getValue();
				s++;
			}
			for(int i=0;i<wheres.length;i++){
				if(i>0){
					wherestr.append(" and ");
				}
				wherestr.append(wheres[i]);
				wherestr.append("=?");
				obj[s]=beanMap.get(wheres[i]);
				s++;
			}
			String updatesql = "update " + tableName + " set " + updatestr+" where "+wherestr;
			return helper.update(updatesql, obj);
		} catch (Exception e) {
			throw new BaseException(e);
		}
	}
	public static int insert(String tableName, Object bean) {
		Map<String, Object> beanMap;
		try {
			beanMap = BeanHelper.getBeanAttr(bean);
		} catch (Exception e) {
			throw new BaseException(e);
		}
		StringBuffer name = new StringBuffer();
		StringBuffer value = new StringBuffer();
		Object[] obj = new Object[beanMap.size()];
		int s = 0;
		for (Map.Entry<String, Object> set : beanMap.entrySet()) {
			if (s > 0) {
				name.append(",");
				value.append(",");
			}
			name.append(set.getKey());
			value.append("?");
			obj[s] = set.getValue();
			s++;
		}
		String insertsql = "insert into " + tableName + "(" + name
				+ ") values(" + value + ")";
		return helper.update(insertsql, obj);
	}
	public static int[] insert(String tableName, List beanlist) {
		if (beanlist == null || beanlist.size() < 1) {
			throw new BaseException("参数出错，beanlist不能为空");
		}
		String[] sqlstr = new String[beanlist.size()];
		List paramlist = new ArrayList();
		int x = 0;
		for (Object bean : beanlist) {
			Map<String, Object> beanMap;
			try {
				beanMap = BeanHelper.getBeanAttr(bean);
			} catch (Exception e) {
				throw new BaseException(e);
			}
			StringBuffer name = new StringBuffer();
			StringBuffer value = new StringBuffer();
			Object[] obj = new Object[beanMap.size()];
			int s = 0;
			for (Map.Entry<String, Object> set : beanMap.entrySet()) {
				if (s > 0) {
					name.append(",");
					value.append(",");
				}
				name.append(set.getKey());
				value.append("?");
				obj[s] = set.getValue();
				s++;
			}
			sqlstr[x] = "insert into " + tableName + "(" + name + ") values("
					+ value + ")";
			paramlist.add(obj);
			x++;
		}
		final List<Object[]> datalist = paramlist;
		BatchPreparedStatementSetter setter = new BatchPreparedStatementSetter() {
			public int getBatchSize() {
				return datalist.size();
			}
			public void setValues(PreparedStatement ps, int i) {
				Object[] obj = datalist.get(i);
				try {
					for (int ii = 0; ii < obj.length; ii++) {
						ps.setObject(ii+1, obj[ii]);
					}
				} catch (Exception e) {
					e.printStackTrace();
					throw new BaseException(e);
				}
			}
		};
		return helper.batchUpdate(sqlstr[0],setter);
	}
	public static Page queryPage(String sql,Class type,Object[] params){
		Page page=PageUtil.getPage();
		String orderStr = page.getOrder();
		if (orderStr != null && !orderStr.equals("")) {// 在这儿设置排序规则,页面传过来的排序格式：field:asc
//			String[] order = orderStr.split(":");
//			if (order[1].equals("asc"))
//				sql="select * from ("+sql+") lzy order by lzy."+order[0]+" asc";
//			else
//				sql="select * from ("+sql+") lzy order by lzy."+order[0]+" desc";
			sql="select * from ("+sql+") lzy order by "+orderStr.replace(":"," ");
		}
		return handler.queryPageBySQL(sql, page.getPageSize(), page.getPageNo(),type,params);
	}
	public static Page queryPage(String sql,Class type){
		return queryPage(sql,type,null);
	}
	public static Page queryPage(String sql,Class type,Object params){
		Page page=PageUtil.getPage();
		String orderStr = page.getOrder();
		if (orderStr != null && !orderStr.equals("")) {// 在这儿设置排序规则,页面传过来的排序格式：field:asc
			sql="select * from ("+sql+") lzy order by "+orderStr.replace(":"," ");
		}
		List lst=new ArrayList();
		try {
			sql=transfromMap(sql, BeanHelper.getBeanAttr(params), lst);
		} catch (Exception e) {
			e.printStackTrace();
			throw new BaseException(e);
		}
		return handler.queryPageBySQL(sql, page.getPageSize(), page.getPageNo(),type,lst.toArray());
	}
	public static String getMaxId(String tablename){
		synchronized (tablename) {
			return gennumber.applySerializeNumber(tablename);
		}
	}
	private static String transfromMap(String sql,Map param,List args){
		Matcher matcher = SysConstants.paramPattern.matcher(sql);
		matcher.matches();
		while(matcher.find()){
			args.add(param.get(matcher.group(2)));//获取param的顺序保存
			sql=sql.replaceFirst(matcher.group(1), "?");//替换:param为?
		}
		return sql;
	}
}