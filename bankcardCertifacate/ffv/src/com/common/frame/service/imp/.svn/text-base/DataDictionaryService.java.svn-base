package com.common.frame.service.imp;

import java.util.Map;

import javax.annotation.Resource;
import javax.persistence.criteria.JoinType;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Property;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Service;

import com.common.base.dao.ICommonDAO;
import com.common.base.service.CommonService;
import com.common.frame.model.DataDictionary;
import com.common.frame.service.IDataDictionaryService;
import com.common.web.page.Page;
/**
 * 
 * @author longzy
 * 数据字典
 */
@Service
public class DataDictionaryService extends CommonService<DataDictionary> implements IDataDictionaryService{
	private static final long serialVersionUID = -257447098833868592L;
	@Resource(name="dataDictionaryDAO")
	private  ICommonDAO<DataDictionary> dataDictionaryDAO;
	@Override
	protected ICommonDAO<DataDictionary> getDAO() {
		return dataDictionaryDAO;
	}
	public Page findDataDictionarysByPage(DataDictionary obj, Map param) {
		DetachedCriteria query=DetachedCriteria.forClass(DataDictionary.class,"dic");
		if(obj!=null){
			if(obj.getDicKey()!=null&&!obj.getDicKey().equals("")){
				query.add(Restrictions.like("dicKey",obj.getDicKey(),MatchMode.ANYWHERE));
			}if(obj.getDescription()!=null&&!obj.getDescription().equals("")){
				query.add(Restrictions.like("description",obj.getDescription(),MatchMode.ANYWHERE));
			}/**if(obj.getIsOpen()!=null&&!obj.getIsOpen().equals("")){
				query.add(Restrictions.eq("isOpen",obj.getIsOpen()));
			}**/if(obj.getEct1()!=null&&!obj.getEct1().equals("")){
				query.add(Restrictions.like("ect1",obj.getEct1(),MatchMode.ANYWHERE));
			}if(obj.getDicValue()!=null&&!obj.getDicValue().equals("")){
				query.add(Restrictions.like("dicValue",obj.getDicValue(),MatchMode.ANYWHERE));
			}if(obj.getOrders()!=null&&!obj.getOrders().equals("")){
				query.add(Restrictions.eq("orders", obj.getOrders()));
			}
		}if(param!=null&&param.size()>0){//参数不为空
			String roleid=(param.get("roleid")==null?null:(String)param.get("roleid"));//角色ID
			String isThisRole=(param.get("isThisRole")==null?null:(String)param.get("isThisRole"));//是否只查询这个角色的用户;0表示只查询不是这个角色的，1表示只查询这个角色的
			if("0".equals(isThisRole)){//只查询不是这个角色的
				DetachedCriteria subQuery40=DetachedCriteria.forClass(DataDictionary.class,"dic");//子查询
				subQuery40.setProjection(Property.forName("dicId"));//与一面这句配合相当于select id from dic
			    subQuery40.createAlias("roles", "roles",JoinType.LEFT.ordinal());
				subQuery40.add(Restrictions.eq("roles.roleId", roleid));
				query.add(Property.forName("dicId").notIn(subQuery40));
			}else if("1".equals(isThisRole)){//只查询是这个角色的
				query.createAlias("roles", "roles").add( Restrictions.like("roles.roleId", roleid));
			}
		}
		return dataDictionaryDAO.queryPage(query);
	}

}
