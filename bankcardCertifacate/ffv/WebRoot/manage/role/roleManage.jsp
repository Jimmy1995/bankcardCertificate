<%@ page language="java" pageEncoding="utf-8"%>
<%@ taglib uri="/WEB-INF/sys.tld" prefix="sys" %><%@ taglib uri="/WEB-INF/c.tld" prefix="c" %><%@ taglib uri="/WEB-INF/fmt.tld" prefix="fmt" %><%@ taglib uri="/WEB-INF/fn.tld" prefix="fn" %> 
<!DOCTYPE HTML>
<HTML>
	<HEAD>
		<sys:head />
<TITLE>角色管理</TITLE>
<script type="text/javascript">
			function openByM(url){
				window.showModalDialog(url,window,"dialogWidth=850px;dialogHeight=550px");
			}
			//$(function(){//选中全部
		    //	$("#selectAll").click(function(){
			 // 		$("input[name='pojo.roles']").each(function(){
			 //  			$(this).attr("checked",!this.checked);              
			  //  	});
			  //	});
		    	
		   // });
			function operationByAjax(urlStr,dataVal){//异步操作
				$.ajax({
				  type: "POST",
				  url: urlStr,
				  data:{"roleids":dataVal},
				  datatype : "json",
				  success : function(data){	
		            if( data == "1" ){		
		                $.common.alert({text:"操作成功！"});
		                window.setTimeout('query()',2000);
					}
		            else{
						$.common.alert({text:"操作失败！"});
					}
				  },	
				  error: function(){$.common.alert({text:"服务器没有返回数据，可能服务器忙，请重试"});}
				});
			}
			function refreshRoleCache(){//异步刷新cache中的角色
				var urlStr="${ctx }/manage/role-manage!updateCacheByRole.action";
				var chk_value =getSelect();     
				if(chk_value.length==0){
					$.common.alert({text:"你还没有选择任何内容！"});
					return ;
				}
				operationByAjax(urlStr,chk_value.toLocaleString());
			}
			
			
			function del(){//删除
				var urlStr="${ctx }/manage/role-manage!delete.action";
				var chk_value =getSelect();     
				if(chk_value.length==0){
					$.common.alert({text:"你还没有选择任何内容！"});
					return ;
				}
				$.common.confirm({text:"你确定删除吗？",yesCallBack:function(){
					operationByAjax(urlStr,chk_value.toLocaleString());
				}})
			}
			
			function setMenu(){//分配菜单与权限
				var urlStr="${ctx }/manage/role-manage!toSetMenuByRole.action";
				var chk_value =getSelect();     
				if(chk_value.length==0){
					$.common.alert({text:"你还没有选择任何内容！"});
					return ;
				}else if(chk_value.length>1){
					$.common.alert({text:"只能选择一条记录！"});
					return ;
				}
				$.common.openDialog({scroll:'yes',url:urlStr+"?roleId="+chk_value[0]});
			}
			function setUser(){//分配用户
				var urlStr="${ctx }/manage/role/index.jsp";//?roleid=${role.id}
				var chk_value =getSelect();     
				if(chk_value.length==0){
					$.common.alert({text:"你还没有选择任何内容！"});
					return ;
				}else if(chk_value.length>1){
					$.common.alert({text:"只能选择一条记录！"});
					return ;
				}
				$.common.openDialog({url:urlStr+"?roleid="+chk_value[0],width:900,height:400});
			}
			function setDIC(){//分配字典
				var urlStr="${ctx }/manage/role/indexForDic.jsp";
				var chk_value =getSelect();     
				if(chk_value.length==0){
					$.common.alert({text:"你还没有选择任何内容！"});
					return ;
				}else if(chk_value.length>1){
					$.common.alert({text:"只能选择一条记录！"});
					return ;
				}
				$.common.openDialog({url:urlStr+"?roleid="+chk_value[0],width:900,height:400});
			}
			
			function update(){//修改角色
				var urlStr="${ctx }/manage/role-manage!toAlter.action";
				var chk_value =getSelect();     
				if(chk_value.length==0){
					$.common.alert({text:"你还没有选择任何内容！"});
					return ;
				}else if(chk_value.length>1){
					$.common.alert({text:"只能选择一条记录！"});
					return ;
				}
				$.common.openDialog({url:urlStr+"?roleId="+chk_value[0],callback:function(){query();}});
			}
			
			function query(){
				roleform.draw();
			}
			function getSelect(){
				var chk_value =new Array();
				var data=getSelectRowData(roleform);
				$.each(data,function(i,n){     
	                chk_value.push(n.roleId);     
	            });
				return chk_value;
			}
			function add(){
				$.common.openDialog({url:'${ctx }/manage/role-manage!toSave.action',callback:function(){query();}});
			}
</script>
</HEAD>
<BODY>
     <form name="listForm">
		 <sys:editorlayout cols="3">
		 	<sys:editorlayout-row>
		 		<sys:textfield name="roleName" id="roleName" label="角色名"></sys:textfield>
		 		<td>
						<sys:dropdown hasPrveButton="true" onclick="javascript:query();" value="搜索">
							<sys:dropdownItem onclick="javascript:add();" value="增加"></sys:dropdownItem>
							<sys:dropdownItem onclick="refreshRoleCache()" value="刷新缓存"></sys:dropdownItem>
							<sys:dropdownItem onclick="javascript:del();" value="删除"></sys:dropdownItem>
							<sys:dropdownItem onclick="update();" value="修改"></sys:dropdownItem>
							<sys:dropdownItem onclick="setMenu();" value="菜单权限"></sys:dropdownItem>
							<sys:dropdownItem isDivider="true"></sys:dropdownItem>
							<sys:dropdownItem onclick="setUser();" value="分配用户"></sys:dropdownItem>
							<sys:dropdownItem onclick="setDIC();" value="分配字典"></sys:dropdownItem>
						</sys:dropdown>				
				</td>
		 	</sys:editorlayout-row>
		 </sys:editorlayout>
     </form>
     
			<sys:grid action="${ctx }/manage/role-manage!list.action" formId="listForm" id="roleform" cssClass="its" mutipleSelect="true">
				<sys:grid-column name="roleName" display="角色名"></sys:grid-column>
				<sys:grid-column name="roleInfo" display="说明" cssClass="hidden-xs"></sys:grid-column>
				<sys:grid-column name="orders" display="排序" cssClass="hidden-xs"></sys:grid-column>
				<sys:grid-column name="createTime" display="创建时间" cssClass="hidden-xs"></sys:grid-column>
			</sys:grid>     
</BODY>
</HTML>
