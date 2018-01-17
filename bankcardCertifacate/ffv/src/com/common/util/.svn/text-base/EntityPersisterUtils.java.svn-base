package com.common.util;

import java.util.Map;

import org.hibernate.Session;
import org.hibernate.persister.entity.AbstractEntityPersister;

/**
 * 
 * @author Administrator
 *
 */
@SuppressWarnings("unchecked")
public class EntityPersisterUtils {
	/**
	 * 得到实体属性对应的数据库表字段
	 * @param session
	 * @param type
	 * @param PropertyName
	 * @return
	 */
	public static String getColumnNames(Session session, Class type,String propertyName) {
		AbstractEntityPersister cm = (AbstractEntityPersister) session
				.getSessionFactory().getClassMetadata(type);
		String[] strs = cm.getSubclassPropertyColumnNames(propertyName);
		if (strs != null && strs.length > 0)
			return strs[0];
		else {
			return null;
		}
	}
	/**
	 * 得到实体对应的数据库表的 主键
	 * @param session
	 * @param type
	 * @return
	 */
	public static String getIdentifierColumnNames(Session session,Class type){
		AbstractEntityPersister cm = (AbstractEntityPersister) session
		.getSessionFactory().getClassMetadata(type);
		String[] strs = cm.getIdentifierColumnNames();
		if (strs != null && strs.length > 0)
			return strs[0];
		else {
			return null;
		}
	}
	/**
	 * 得到实体对应的数据库表
	 * @param session
	 * @param type
	 * @return
	 */
	public static String getTableName(Session session,Class type){
		Map<String, AbstractEntityPersister> m =session.getSessionFactory().getAllClassMetadata();
        AbstractEntityPersister abstractEntityPersister = m.get(type.getName());
		return abstractEntityPersister.getTableName();
	}
	/**
	 * 得到实体的主键
	 * @param session
	 * @param type
	 * @return
	 */
	public static String getIdentifierPropertyName(Session session,Class type){
		AbstractEntityPersister cm = (AbstractEntityPersister) session
		.getSessionFactory().getClassMetadata(type);
		return cm.getIdentifierPropertyName();
	}
}
