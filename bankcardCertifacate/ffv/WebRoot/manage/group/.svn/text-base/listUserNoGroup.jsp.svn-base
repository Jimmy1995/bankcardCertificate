<%@ page language="java" pageEncoding="utf-8"%>
<%@ taglib uri="/WEB-INF/sys.tld" prefix="sys" %><%@ taglib uri="/WEB-INF/c.tld" prefix="c" %><%@ taglib uri="/WEB-INF/fmt.tld" prefix="fmt" %><%@ taglib uri="/WEB-INF/fn.tld" prefix="fn" %> 
<!DOCTYPE HTML>
<HTML>
	<HEAD>
		<sys:head />
		<TITLE>显示不在本部门的用户（给部门分配用户）</TITLE>
		<link href="${ctx }/images/login/main.css" rel="stylesheet" type="text/css" />
		<%@ include file="../../commons/zTree.jsp"%>
		<script type="text/javascript">
			function setUsers(){
				var urlStr="${ctx }/manage/user-manage!setGroupByUsers.action?groupid=${param.groupid}";
				var chk_value =getSelect();
				if(chk_value.length==0){
					$.common.alert({text:"你还没有选择任何内容！"});
					return ;
				}
				operationByAjax(urlStr,chk_value.toLocaleString());
			}
			function operationByAjax(urlStr,dataVal){//异步操作
				$.ajax({
				  type: "POST",
				  url: urlStr,
				  data:{"pojo.userIds":dataVal},
				  datatype : "json",
				  success : function(data){	
		            if( data.errorType == "0" ){		
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
			function getSelect(){
				var chk_value =new Array();
				var data=getSelectRowData(userform);
				$.each(data,function(i,n){     
	                chk_value.push(n.userId);     
	            });
				return chk_value;
			}
			function query(){
				userform.draw();
			}	
		</script>
	</HEAD>
	<BODY>
		<div style="float: left;width: 100%">
			<form id="listForm" name="listForm">
				<sys:editorlayout cols="3">
					<sys:editorlayout-row>
						<input type="hidden" id="groupid" name="groupid" value="${param.groupid}" />
						<sys:textfield name="userName" id="userName" label="用户名"></sys:textfield>
						<td>
							<sys:dropdown hasPrveButton="true" onclick="javascript:query();" value="搜索">
								<sys:dropdownItem onclick="setUsers();" value="添加到部门"></sys:dropdownItem>
							</sys:dropdown>
						</td>
					</sys:editorlayout-row>				
				</sys:editorlayout>
			</form>
			<sys:grid action="${ctx }/manage/user-manage!listNoThisGroup.action" formId="listForm" id="userform" cssClass="its" mutipleSelect="true">
				<sys:grid-column name="name" display="姓名"></sys:grid-column>
				<sys:grid-column name="userName" display="用户名"></sys:grid-column>
			</sys:grid>
		
		</div>
	</BODY>
</HTML>