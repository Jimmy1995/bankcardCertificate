<%@ page language="java" pageEncoding="utf-8"%>
<%@ taglib uri="/WEB-INF/sys.tld" prefix="sys" %><%@ taglib uri="/WEB-INF/c.tld" prefix="c" %><%@ taglib uri="/WEB-INF/fmt.tld" prefix="fmt" %><%@ taglib uri="/WEB-INF/fn.tld" prefix="fn" %> 
<!DOCTYPE HTML>
<HTML>
	<HEAD>
		<sys:head />
		<TITLE>人员管理</TITLE>
		<script type="text/javascript">
			//function offLine(sessionId){
			//	$("#sessionId").val(sessionId);
			//	$("form").attr("action","${ctx }/manage/user-manage!offLine.action");
		//		$("form").submit();
		//		$("#sessionId").val("");
		//		$("from").attr("action","${ctx }/manage/user-manage!list.action");
		//	}
		//	$(function(){//选中全部
		 //   	$("#selectAll").click(function(){
		//	  		$("input[name='pojo.userIds']").each(function(){
		//	   			$(this).attr("checked",!this.checked);              
		//	    	});
		//	  	});
		 //   	
		   // });
			
			function operationByAjax(urlStr,dataVal){//异步操作用户
				$.ajax({
				  type: "POST",
				  url: urlStr,
				  data:{"userids":dataVal},
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
			
			function del(){//删除
				var urlStr="${ctx }/manage/user-manage!delete.action";
				var chk_value =getSelect();     
				if(chk_value.length==0){
					$.common.alert({text:"你还没有选择任何内容！"});
					return ;
				}
				$.common.confirm({text:"你确定删除吗？",yesCallBack:function(){
					operationByAjax(urlStr,chk_value.toLocaleString());
				}})
			}
			
			function update(){//修改
				var urlStr="${ctx }/manage/user-manage!toAlter.action";
				var chk_value =getSelect();     
				if(chk_value.length==0){
					$.common.alert({text:"你还没有选择任何内容！"});
					return ;
				}else if(chk_value.length>1){
					$.common.alert({text:"只能选择一条记录！"});
					return ;
				}
				$.common.openDialog({url:urlStr+"?userId="+chk_value[0],title:"用户管理",callback:function(){query();}});
			}
			
			function setRole(){//分配角色
				var urlStr="${ctx }/manage/user-manage!toSetRoleByUser.action";
				var chk_value =getSelect();     
				if(chk_value.length==0){
					$.common.alert({text:"你还没有选择任何内容！"});
					return ;
				}else if(chk_value.length>1){
					$.common.alert({text:"只能选择一条记录！"});
					return ;
				}
				$.common.openDialog({url:urlStr+"?userId="+chk_value[0],title:"用户管理"});
			}
			
			
			function forbid(){//禁用
				var urlStr="${ctx }/manage/user-manage!forbid.action?flag=0";
				var chk_value =getSelect();     
				if(chk_value.length==0){
					$.common.alert({text:"你还没有选择任何内容！"});
					return ;
				}
				operationByAjax(urlStr,chk_value.toLocaleString());
			}
			
			function enable(){//启用
				var urlStr="${ctx }/manage/user-manage!forbid.action?flag=1";
				var chk_value =getSelect();     
				if(chk_value.length==0){
					$.common.alert({text:"你还没有选择任何内容！"});
					return ;
				}
				operationByAjax(urlStr,chk_value.toLocaleString());
			}
			function query(){
				userform.draw();
			}
			function getSelect(){
				var chk_value =new Array();
				var data=getSelectRowData(userform);
				$.each(data,function(i,n){     
	                chk_value.push(n.userId);     
	            });
				return chk_value;
			}
			function add(){
				$.common.openDialog({url:'${ctx }/manage/user/addUser.jsp',callback:function(){query();}});
			}
		</script>
		
	</HEAD>
	<BODY>
		<form id="listForm" name="listForm" >
			<sys:editorlayout cols="5">
				<sys:editorlayout-row>
					<input type="hidden" name="sessionId" id="sessionId"/>
					<sys:textfield name="userName" id="userName" label="用户名"></sys:textfield>
					<sys:select list="#request.roleList" name="roleId" headerKey="" headerValue="选择角色" listKey="roleId" listValue="roleName"></sys:select>
					<td class="hidden-xs">
						<label>
							<input type="checkbox" name="onlyOnline" value="1" ${onlyOnline eq '1' ?'checked':''}/>在线
						</label>							
					</td>	
					<td nowrap="nowrap">
						<sys:dropdown hasPrveButton="true" onclick="javascript:query();" value="搜索">
							<sys:dropdownItem onclick="javascript:add();" value="增加"></sys:dropdownItem>
							<sys:dropdownItem onclick="javascript:del()" value="删除"></sys:dropdownItem>
							<sys:dropdownItem onclick="update();" value="修改"></sys:dropdownItem>
							<sys:dropdownItem onclick="setRole();" value="分配角色"></sys:dropdownItem>
							<sys:dropdownItem isDivider="true"></sys:dropdownItem>
							<sys:dropdownItem onclick="forbid();" value="禁用"></sys:dropdownItem>
							<sys:dropdownItem onclick="enable();" value="启用"></sys:dropdownItem>
						</sys:dropdown>
					</td>	
				</sys:editorlayout-row>
			</sys:editorlayout>
			</form>
			<sys:grid action="${ctx }/manage/user-manage!list.action" formId="listForm" id="userform" cssClass="its" mutipleSelect="true">
				<sys:grid-column name="name" display="姓名" orderable="false"></sys:grid-column>
				<sys:grid-column name="userName" display="用户名"></sys:grid-column>
				<sys:grid-column name="createTime" display="创建时间" order="desc" cssClass="hidden-xs"></sys:grid-column>
			</sys:grid>
	</BODY>
</HTML>
