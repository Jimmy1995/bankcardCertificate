package com.common.frame.action;

import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.fastjson.serializer.SerializeFilter;
import com.alibaba.fastjson.serializer.SimplePropertyPreFilter;
import com.common.base.BaseAction;
import com.common.frame.model.DataDictionary;
import com.common.frame.model.Role;
import com.common.frame.service.IDataDictionaryService;
import com.common.frame.service.IRoleManageService;
import com.common.frame.vo.OtherPojo;
import com.common.util.Struts2Utils;
import com.common.util.WebUtils;
import com.common.util.json.JsonUtils;
@Namespace("/manage")
@Results({ 
	@Result(name = "success", location = "/manage/dic/dicManage.jsp"),
	@Result(name = BaseAction.RELOAD, location = "/manage/dic-manage.action", type = "redirect"),
	@Result(name = "alterDic", location = "/manage/dic/alterDic.jsp"),
	@Result(name = "toSave", location = "/manage/dic/addDic.jsp"),
	@Result(name = "listDicsByRole",location = "/manage/role/setDic.jsp"),
	@Result(name = "error", location = "/manage/login.jsp") })
public class DicManageAction extends BaseAction<DataDictionary>{
	private static final long serialVersionUID = 2802973310555216833L;
	private OtherPojo pojo;
	@Autowired
	protected IDataDictionaryService dataDictionaryService;
	@Autowired
	protected IRoleManageService roleManageService;
	@Override
	public String list() throws Exception {
		if(!WebUtils.isAJAXRequest(getRequest())){
			return SUCCESS;
		}
		SerializeFilter filter=new SimplePropertyPreFilter(DataDictionary.class,"codetype","dicId","dicKey","dicValue","orders","description");
		JsonUtils.setPropertyFilter(filter);
		writeJsonReturn(null,dataDictionaryService.findDataDictionarysByPage(obj, null));
		return NONE;
	}
	
	public String toSave() throws Exception {
		obj=dataDictionaryService.findById(obj.getDicId());
		setRequestAttribute("roles", roleManageService.findAllRoles());
		return "toSave";
	}
	@SuppressWarnings("unchecked")
	public String save() throws Exception {
		String[] rolesId=this.getPojo().getRoles();
		Set roleSet=new HashSet();
		if(rolesId!=null){
			for(String roleId:rolesId){
			    Role role=new Role();
			    role.setRoleId(roleId);
			    roleSet.add(role);
			}
		}
		obj.setCreateTime(new Date());
		obj.setRoles(roleSet);
		if(null==obj.getParentDic()||"".equals(obj.getParentDic().getDicId())){
			obj.setParentDic(null);
		}
		dataDictionaryService.save(obj);
//		if(rolesId!=null){
//			for(String roleId:rolesId){
//			    roleManageService.refreshRoleCache(roleId,osCacheManage);//刷新缓存
//			}
//		}
//		CacheUtil.refreshDictionaryCache(osCacheManage);//把数据字典全记录在内存当中
		writeJsonReturn("保存成功！",null);
		return NONE;
	}
	public String delete() throws Exception {
		String dics=getParameter("dics");
			if(dics!=null){
				String[] us=dics.split(",");
				for(String id:us){
				this.dataDictionaryService.deleteById(id.trim());
			}
		}
		Struts2Utils.renderText("1");
		return null;
	}
	public String toAlter() throws Exception {
		obj=dataDictionaryService.findById(obj.getDicId());
		setRequestAttribute("roles", roleManageService.findAllRoles());
		return "alterDic";
	}
	@SuppressWarnings("unchecked")
	public String alter() throws Exception {
		DataDictionary obj1=dataDictionaryService.findById(obj.getDicId());
		String[] rolesId=this.getPojo().getRoles();
		Set roleSet=new HashSet();
		if(rolesId!=null){
			for(String roleId:rolesId){
			    Role role=new Role();
			    role.setRoleId(roleId);
			    roleSet.add(role);
			}
		}
		obj1.setRoles(roleSet);//角色
		obj1.setDicKey(obj.getDicKey());
		obj1.setDicValue(obj.getDicValue());
		obj1.setDescription(obj.getDescription());
		obj1.setOrders(obj.getOrders());
		obj1.setIsOpen(obj.getIsOpen());
		obj1.setEct1(obj.getEct1());
		obj1.setEct2(obj.getEct2());
		obj1.setCodetype(obj.getCodetype());
		dataDictionaryService.save(obj1);
		/*if(rolesId!=null){
			for(String roleId:rolesId){
			    roleManageService.refreshRoleCache(roleId,osCacheManage);//刷新缓存
			}
		}
		osCacheManage.setCache("openDic", dataDictionaryService.findList("from DataDictionary where isOpen='1'"));//把数据字典全记录在内存当中
*/		writeJsonReturn("保存成功！",null);
		return NONE;
	}
	/**
	 * 去为角色分配字典
	 */
	@SuppressWarnings("unchecked")
	public String listDicsByRole()throws Exception{
		Map param=new HashMap();
		String roleid=request.getParameter("roleid");
		String isThisRole=request.getParameter("isThisRole");
		param.put("roleid", roleid);
		param.put("isThisRole", isThisRole);
		Set roles=new HashSet();
		roles.add(roleid);
		setPage(dataDictionaryService.findDataDictionarysByPage(obj,param));
		request.setAttribute("roleid", roleid);
		return "listDicsByRole";
	}
	/**
	 * 为角色分配多个字典
	 */
	public String setRoleByDics() throws Exception{
		Set<DataDictionary> dics=new HashSet<DataDictionary>(); 
		String roleid=request.getParameter("roleid");
		String isThisRole=request.getParameter("isThisRole");//如果这个为1表示要把这些用户从当前角色退出，为0表示要把这些用户添加到当前角色
		Role role=roleManageService.findById(roleid);
		if(getPojo().getDics()!=null){
			for(String dicid:getPojo().getDics()){
				dics.add(new DataDictionary(dicid));
			}
			if("0".equals(isThisRole)){//添加到角色
				role.getDataDictionarys().addAll(dics);
			}else if("1".equals(isThisRole)){//退出角色
				role.getDataDictionarys().removeAll(dics);
			}
			
		}
		roleManageService.alter(role);
		//roleManageService.refreshRoleCache(roleid,osCacheManage);//刷新缓存
		return listDicsByRole();
	}
	/**
	 * @return the pojo
	 */
	public OtherPojo getPojo() {
		if(pojo==null){
			pojo=new OtherPojo();
		}
		return pojo;
	}
	/**
	 * @param pojo the pojo to set
	 */
	public void setPojo(OtherPojo pojo) {
		this.pojo = pojo;
	}
}
