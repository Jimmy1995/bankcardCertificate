package com.common.base.handler.imp;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;

import javax.annotation.Resource;

import org.hibernate.Criteria;
import org.hibernate.Hibernate;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.transform.Transformers;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.stereotype.Repository;

import com.common.SysConstants;
import com.common.base.BaseException;
import com.common.base.handler.IHandler;
import com.common.util.EntityPersisterUtils;
import com.common.web.page.Page;

/**
 * @className:CommonDAO.java
 * @classDescription:spring持久层
 * @author:longzy
 * @createTime:2012-10-7
 */
@SuppressWarnings("unchecked")
@Repository("springHandler")
public class SpringHandler extends HibernateDaoSupport implements IHandler {
	boolean flag = false;

	/**
	 * 为父类HibernateDaoSupport注入sessionFactory的值
	 * 
	 * @param sessionFactory
	 */
	@Resource(name = "sessionFactory")
	public void setSuperSessionFactory(SessionFactory sessionFactory) {
		super.setSessionFactory(sessionFactory);
	}

	/**
	 * 保存对象
	 * 
	 * @param obj
	 *            要保存的对象
	 * @return 布尔值
	 */
	public boolean saveObj(Object obj) {
		try {
			this.getHibernateTemplate().save(obj);
			flag = true;
		} catch (Exception e) {
			e.printStackTrace();
			throw new BaseException(e.getMessage());
		}
		return flag;
	}

	/**
	 * 保存或更新对象
	 * 
	 * @param obj
	 *            要保存的对象
	 * @return 布尔值
	 */
	public boolean saveOrUpdate(Object obj) {
		try {
			this.getHibernateTemplate().saveOrUpdate(obj);
			flag = true;
		} catch (Exception e) {
			e.printStackTrace();
			throw new BaseException(e.getMessage());
		}
		return flag;
	}
	
	public void saveOrUpdateAll(List list){
		this.getHibernateTemplate().saveOrUpdateAll(list);
	}
	/**
	 * 删除对象
	 * 
	 * @param objQ
	 *            要删除的对象
	 * @return 布尔值
	 */
	public boolean deleteObj(Object obj) {
		if(null==obj){
			return true;
		}
		try {
			this.getHibernateTemplate().delete(obj);
			flag = true;
		} catch (Exception e) {
			flag = false;
			throw new BaseException("删除失败！");
		}
		return flag;
	}

	/**
	 * 修改对象
	 * 
	 * @param obj
	 *            要修改的对象
	 * @return 布尔值
	 */
	public boolean alterObj(Object obj) {
		try {
			this.getHibernateTemplate().saveOrUpdate(obj);
			flag = true;
		} catch (Exception e) {
			e.printStackTrace();
			flag = false;
			throw new BaseException("修改出错："+e.getMessage());
		}
		return flag;
	}

	/**
	 * 修改对象
	 * 
	 * @descript merge和saveOrUpdate方法区别在于： merge方法是把我们提供的对象转变为托管状态的对象；
	 *           而saveOrUpdate则是把我们提供的对象变成一个持久化对象；
	 *           说的通俗一点就是：saveOrUpdate后的对象会纳入session的管理
	 *           ，对象的状态会跟数据库同步，再次查询该对象会直接从session中取; merge后的对
	 *           象不会纳入session的管理，再次查询该对象还是会从数据库中取
	 * @param obj
	 *            要修改的对象
	 * @return 布尔值
	 */
	public boolean merge(Object obj) {
		try {
			this.getHibernateTemplate().merge(obj);
			flag = true;
		} catch (Exception e) {
			e.printStackTrace();
			flag = false;
			throw new BaseException("保存出错");
		}
		return flag;
	}

	/**
	 * 根据id查询对象
	 * 
	 * @param className
	 *            类名
	 * @param id
	 *            类id
	 * @return 对象
	 */
	public Object findObjById(Class className, Serializable id) {
		if (null == id || "".equals(id)) {
			//throw new BaseException("查询出错：主键为空");
			 return null;
		} else {
			return this.getHibernateTemplate().get(className, id);
		}
	}

	/**
	 * 根据id查询延时对象
	 * 
	 * @param className
	 *            类名
	 * @param id
	 *            类id
	 * @return 对象
	 */
	public Object findObjByIdLoad(Class className, Serializable id) {
		if (null == id || "".equals(id)) {
			throw new BaseException("查询出错：主键为空");
			// return null;
		} else {
			return this.getHibernateTemplate().load(className, id);
		}
	}

	/**
	 * 查询对象
	 * 
	 * @param hql
	 *            查找语句
	 * @return 对象
	 */
	public Object findObj(final String hql) {
		if (null == hql || "".equals(hql)) {
			throw new BaseException("查询出错：HQL语句为空");
			// return null;
		}
		return this.getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session session)
					throws HibernateException, SQLException {
				Query query = session.createQuery(hql);
				query.setFirstResult(0);
				query.setMaxResults(1);
				Object obj = query.uniqueResult();
				return obj;
			}
		});
	}

	/**
	 * 查询对象(防止依赖注入)
	 * 
	 * @param hql
	 *            查找语句
	 * @param map
	 *            参数
	 * @return 对象
	 */
	public Object findObj(final String hql,
			final Map<Serializable, Serializable> map) {
		if (null == hql || "".equals(hql)) {
			//throw new BaseException("查询出错：HQL语句为空");
			 return null;
		}
		return this.getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session session)
					throws HibernateException, SQLException {
				Query query = session.createQuery(hql);
				List<String> keys=matcherParamters(hql);
				if(keys!=null&&keys.size()>0)
				for (String key : keys) {
					if(!map.containsKey(key)){
						throw new BaseException("HQL出错,参数名："+key+"没有对应的匹配数据");
					}
					query.setParameter(key, map.get(key));
				}
				query.setFirstResult(0);
				query.setMaxResults(1);
				Object obj = query.uniqueResult();
				return obj;
			}
		});
	}

	/**
	 * 查询集合
	 * 
	 * @param sql
	 *            查询语句
	 * @return 集合
	 */
	public List findListOfObj(final String hql) {
		if (null == hql || "".equals(hql)) {
			//throw new BaseException("查询出错：HQL语句为空");
			 return null;
		}
		return this.getHibernateTemplate().executeFind(new HibernateCallback() {
			public Object doInHibernate(Session session)
					throws HibernateException, SQLException {
				Query query = session.createQuery(hql);
				return query.list();
			}
		});
	}

	/**
	 * 执行HQL语句
	 * 
	 * @param hql
	 * @return
	 */
	public int executeHQL(final String hql) {
		int i = 0;
		try {
			i = getHibernateTemplate().execute(new HibernateCallback() {
				public Object doInHibernate(Session session)
						throws HibernateException, SQLException {
					Query query = session.createQuery(hql);
					return query.executeUpdate();
				}
			});
		} catch (Exception e) {
			e.printStackTrace();
			throw new BaseException("HQL语句执行出错:"+e.getMessage());
		}
		return i;
	}

	/**
	 * 主要用于防止sql依赖注入
	 * 
	 * @param hql
	 *            hql语句（例如" from UserInfo where id=:id and
	 *            password=:password")query.setParameter("id",userForm.getId())
	 * @param map
	 * @return List集合
	 */
	public List findListOfObj(final String hql,
			final Map<Serializable, Serializable> map) {
		if (null == hql || "".equals(hql)) {
			//throw new BaseException("查询出错：HQL语句为空");
			return null;
		}
		return this.getHibernateTemplate().executeFind(new HibernateCallback() {
			public Object doInHibernate(Session session)
					throws HibernateException, SQLException {
				Query query = session.createQuery(hql);
				List<String> keys=matcherParamters(hql);
				if(keys!=null&&keys.size()>0)
				for (String key : keys) {
					if(!map.containsKey(key)){
						throw new BaseException("HQL出错,参数名："+key+"没有对应的匹配数据");
					}
					Object o=map.get(key);
					if (o instanceof Collection) {
						query.setParameterList(key, (Collection)o);
					} else {
						query.setParameter(key, o);
					}
				}
				List list = query.list();
				if (list.size() == 0) {
					return null;
				} else {
					return list;
				}
			}
		});
	}

	// -------------------sql语句-----------------------//
	/**
	 * 根据sql查询语句返回List对象
	 * 
	 * @param sql
	 *            sql查询语句，
	 * @return List
	 */
	public List findListBySql(String sql) {
		Query query = getSession().createSQLQuery(sql);
		return query.list();
	}

	/**
	 * 根据sql查询语句返回List对象
	 * 
	 * @param sql
	 *            sql查询语句，
	 * @return List
	 */
	public List findListBySql(String sql, Class type) {
		Query query = getSession().createSQLQuery(sql);
		if (type != null) {// 如果有类型传入就将结果转为这种类型
			if (java.util.List.class.isAssignableFrom(type)) {
				query = query.setResultTransformer(Transformers.TO_LIST);
			} else if (java.util.Map.class.isAssignableFrom(type)) {
				query = query
						.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
			} else {
				query = query.setResultTransformer(Transformers
						.aliasToBean(type));
			}
		}
		return query.list();
	}

	/**
	 * sql語句操作數據庫
	 * 
	 * @param sql
	 * @return boolean
	 */
	public boolean executeSql(final String sql) {
		boolean result = true;
		try {
			HibernateCallback cb = new HibernateCallback() {
				public Object doInHibernate(Session session) {
					return session.createSQLQuery(sql).executeUpdate();
				} // end function
			};// end callback

			this.getHibernateTemplate().execute(cb);
		} catch (Exception e) {
			result = false;
			throw new BaseException("sql执行出错,这个方法只执行更新操作");
		}

		return result;
	}

	/**
	 * 分页查询功能
	 * 
	 * @param detachedCriteria
	 * @param pageSize
	 * @param startIndex
	 * @param orderStr
	 *            //格式：order:asc，如果没有就写null
	 * @return page
	 */
	public Page findPageByCriteria(final DetachedCriteria detachedCriteria,
			final int pageSize, final int pageNo, final String orderStr) {
		return (Page) getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session session)
					throws HibernateException {
				int startIndex = pageSize * (pageNo - 1);
				Criteria criteria = detachedCriteria
						.getExecutableCriteria(session);
				int totalCount = ((Long) criteria.setProjection(
				// Projections.countDistinct(Projections.id().toString())).uniqueResult()).intValue();//distinct(id)
						Projections.rowCount()).uniqueResult()).intValue();
				criteria.setProjection(null);
				criteria
						.setResultTransformer(CriteriaSpecification.ROOT_ENTITY);// 使用了这个后，就算是多对多查询，得出的对象也是一个
				// criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);//用了这个后就可以消除重复记录(当使用关联查询时)
				if (orderStr != null && !orderStr.equals("")) {// 在这儿设置排序规则,页面传过来的排序格式：field:asc
					String [] o=orderStr.split(",");
					for(String ostr:o){
						String[] order = ostr.split(":");
						if (order[1].equals("asc"))
							criteria.addOrder(Order.asc(order[0]));
						else
							criteria.addOrder(Order.desc(order[0]));
					}
				}
				int pageNoNew = pageNo;
				if (startIndex > totalCount) {// 如果总数不够分页，那么，直接跳到最后一页
					pageNoNew = totalCount / pageSize;
					startIndex = pageSize * pageNoNew;
					pageNoNew++;// 其实是要加一页17/10得一，其实是有两页
				}
				List result = criteria.setFirstResult(startIndex)
						.setMaxResults(pageSize).list();
				Page page = new Page(pageSize, result, totalCount, startIndex,
						pageNoNew);
				page.setOrder(orderStr);
				return page;
			}
		});

	}

	public List findAllObject(Class className) {
		return this.getHibernateTemplate().loadAll(className);
	}

	public Page queryPageBySQL(final String sql,final int pageSize,final int pageNo,final Class type,final Object[] params){
		return (Page)this.getHibernateTemplate().execute(new HibernateCallback<Page>() {
			public Page doInHibernate(Session session)
					throws HibernateException {
				int startIndex = pageSize * (pageNo - 1);
				int pageNoNew = pageNo;
				String countSQL = "select COUNT(*) count from (" + sql + ")longzy";
				SQLQuery countquery=session.createSQLQuery(countSQL);
				if(params!=null){//是否有参数，有则注入参数
					for(int i=0;i<params.length;i++){
						countquery.setParameter(i, params[i]);
					}
				}
				int totalCount = (Integer) countquery.addScalar("count", Hibernate.INTEGER).uniqueResult();
				Query query = getSession().createSQLQuery(sql);
				if (type != null) {// 如果有类型传入就将结果转为这种类型
					if (java.util.List.class.isAssignableFrom(type)) {
						query = query.setResultTransformer(Transformers.TO_LIST);
					} else if (java.util.Map.class.isAssignableFrom(type)) {
						query = query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
					} else {
						query = query.setResultTransformer(Transformers.aliasToBean(type));
					}
				}
				if(params!=null){//是否有参数，有则注入参数
					for(int i=0;i<params.length;i++){
						query.setParameter(i, params[i]);
					}
				}
				if (startIndex > totalCount) {// 如果总数不够分页，那么，直接跳到最后一页
					pageNoNew = totalCount / pageSize;
					startIndex = pageSize * pageNoNew;
					pageNoNew++;// 其实是要加一页17/10得一，其实是有两页
				}
				List result = query.setFirstResult(startIndex).setMaxResults(pageSize)
						.list();
				Page page = new Page(pageSize, result, totalCount, startIndex,
						pageNoNew);
				return page;
			}

		});
		
		
		
	}
	
	/**
	 * sql分页查询
	 * 
	 * @param type为bean
	 *            /map/list
	 */
	public Page queryPageBySQL(String sql, int pageSize, int pageNo, Class type) {
		return queryPageBySQL(sql, pageSize, pageNo, type,null);
	}

	/**
	 * 根據查詢語句以及输入页数，以及显示条数查询对象（分页）
	 * 
	 * @param hql
	 *            hql语句
	 * @param pageNo
	 *            显示的页数
	 * @param pageSize
	 *            显示的条数
	 * @return 集合对象
	 */
	public Page queryPageByHQL(final String hql, final int pageSize,
			final int pageNo, final String orderStr) {
		List pageList = this.getHibernateTemplate().executeFind(
				new HibernateCallback() {
					public Object doInHibernate(Session session)
							throws HibernateException, SQLException {
						if (null == hql || "".equals(hql)) {
							return null;
						}
						Query query=null;
						if (orderStr != null && !orderStr.equals("")) {// 在这儿设置排序规则,页面传过来的排序格式：field:asc
//							String[] order = orderStr.split(":");
//							if (order[1].equals("asc"))
//								query = session.createQuery(hql+" order by "+order[0]+" asc");
//							else
//								query = session.createQuery(hql+" order by "+order[0]+" desc");
							query=session.createQuery(hql+" order by "+orderStr.replace(":"," "));
						}else{
							query = session.createQuery(hql);
						}
						

						String totalHql = "select count(*) as totals "
								+ removeOrders(removeSelect(hql));
						int totalCount = Integer.parseInt(session.createQuery(
								totalHql).list().get(0)
								+ "");

						int index = 0;
						int tempPageCount = 0;
						// 如果pageNo为负数,将pageNo设置为1
						if (pageNo < 1) {
							tempPageCount = 1;
						} else {
							tempPageCount = pageNo;
						}
						// 如果为第一页,索引为0
						if (tempPageCount == 1) {
							// 但前页数索引
							index = pageSize;
						} else {
							index = tempPageCount * pageSize;
						}
						// 设置取出的第一条索引
						query.setFirstResult(index - pageSize);
						// 设置取出得最大的索引
						query.setMaxResults(pageSize);
						List list = new ArrayList();
						list.add(new Page(pageSize, query.list(), totalCount,
								index, tempPageCount));
						return list;
					}
				});

		return (Page) pageList.get(0);
	}

	public List findList(final DetachedCriteria detachedCriteria) {
		return (List) getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session session)
					throws HibernateException {
				Criteria criteria = detachedCriteria
						.getExecutableCriteria(session);
				List result = criteria.list();
				return result;
			}
		});
	}
	/**
	 * @param dataMap 要更新的字段与where语句的数据都在这儿，不能为空
	 * @param type Hibernate实体类,不能为空
	 * @param updates 类型：String[]或List   可为空,默认更新主建及wheres中包含的键之外的
	 * @param wheres 类型：String[]或List 可为空，默认为主键
	 * @return
	 */
	public int update(Map<String, Serializable> dataMap, Class type,Object updates,Object wheres) {
		int num=0;
		if(dataMap!=null&&dataMap.size()>0){
			Session session = getSession();
			String setStr="";
			String whereStr="";
			int msize=dataMap.size();//map大小
			int fi=1;//临时参数
			List<String> wheresL=new ArrayList();
			List<String> updatesL=new ArrayList();
			
			if(null!=wheres){
				if(wheres instanceof String[]){
					wheresL=Arrays.asList((String[])wheres);
				}else if(wheres instanceof List){
					wheresL=(List)wheres;
				}else{
					throw new BaseException("传入的wheres必须为数组或List");
				}
			}
			if(null!=updates){
				if(updates instanceof String[]){
					updatesL=Arrays.asList((String[])updates);
				}else if(updates instanceof List){
					updatesL=(List)updates;
				}else{
					throw new BaseException("传入的updates必须为数组或List");
				}
			}
			boolean hasWheres=false;
			if(wheresL.size()>0){
				hasWheres=true;
			}
			String id=EntityPersisterUtils.getIdentifierPropertyName(session, type);
			if(!hasWheres){
				if(null==dataMap.get(id)){
					throw new BaseException("更新出错：主键不能为空");
				}
			}
			
			int ulsize=updatesL.size();
			if(ulsize<1){
				for (String key : dataMap.keySet()) {
					if(!key.equals(id)&&!wheresL.contains(key)){
						setStr=setStr+key+"=:"+key;
						if(fi<msize){
							setStr+=",";
						}
					}
					fi++;
				}
			}else{
				for(String update:updatesL){
					setStr=setStr+update+"=:"+update;
					if(fi<ulsize){
						setStr+=",";
					}
					fi++;
				}
			}
			for(String where:wheresL){
				if(hasWheres){
					if(whereStr.length()<1){
						whereStr=whereStr+where+"=:"+where;
					}else{
						whereStr=whereStr+" and "+where+"=:"+where;
					}
				}
			}
			
			if(!hasWheres){
				whereStr=id+"=:"+id;
			}
			Query query = session
					.createQuery("update "
							+ type.getSimpleName()+" set "
							+setStr+" where "
							+whereStr);
			query.setProperties(dataMap);
			try{
				num=query.executeUpdate();
			}catch (Exception e) {
					e.printStackTrace();
					throw new BaseException("更新出错,请检查字段名是否正确,主键不能为空,字段类型要和实体一致");
			}
		}
		return num;
	}

	
	
	
	
	
	
	/**
	 * 去除hql的select 子句
	 * 
	 * @param hql
	 * @return hql
	 */
	private static String removeSelect(String hql) {
		int beginPos = hql.toLowerCase().indexOf("from");
		return hql.substring(beginPos);
	}

	/**
	 * 去除hql的orderby 子句
	 * 
	 * @param hql
	 * @return hql
	 */
	private  String removeOrders(String hql) {
		Matcher matcher = SysConstants.orderbyPattern.matcher(hql);
		StringBuffer buf = new StringBuffer();
		while (matcher.find()) {
			matcher.appendReplacement(buf, "");
		}
		matcher.appendTail(buf);
		return buf.toString();
	}
	/**
	 * 匹配出字符串(hql或者sql)中的 ":param"格式的参数，返回"param"参数列表
	 * @param str
	 * @return
	 */
	private  List<String> matcherParamters(String str){
		List<String> args=new ArrayList<String>();
		Matcher matcher = SysConstants.paramPattern.matcher(str);
		matcher.matches();
		while(matcher.find()){
			args.add(matcher.group(2));//获取param
		}
		return args;
	}
}
