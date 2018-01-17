<%@ page language="java" pageEncoding="utf-8"%>
<%@ taglib uri="/WEB-INF/sys.tld" prefix="sys" %><%@ taglib uri="/WEB-INF/c.tld" prefix="c" %><%@ taglib uri="/WEB-INF/fmt.tld" prefix="fmt" %><%@ taglib uri="/WEB-INF/fn.tld" prefix="fn" %> 
<!DOCTYPE HTML>
<HTML>
	<HEAD>
		<sys:head />
		<TITLE>根据部门显示用户</TITLE>
		<%@ include file="../../commons/zTree.jsp"%>
		<script type="text/javascript">
			function query(){
				userform.draw();
			}		
			function outGroup(){
				var data=getSelectRowData(userform);
				if(data.length==0){
					$.common.alert({text:"你还没有选择任何内容！"});
					return ;
				}
				var urlStr="${ctx }/manage/user-manage!outOfGroup.action?userId="+data[0].userId+"&groupid=${param.groupid }";
				$.common.confirm({text:"你确定退出吗？",yesCallBack:function(){
					operationByAjax(urlStr);
				}});
			}
			function operationByAjax(urlStr){//异步操作
				$.ajax({
				  type: "POST",
				  url: urlStr,
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
			function addUser(){
				var url='${ctx }/manage/user-manage!listNoThisGroup.action?groupid=${param.groupid}';
				$.common.openDialog({url:url,title:"移动用户到部门",callback:function(){query();}});
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
								<sys:dropdownItem onclick="javascript:addUser()" value="移动用户"></sys:dropdownItem>
								<sys:dropdownItem onclick="javascript:outGroup()" value="退出部门"></sys:dropdownItem>
							</sys:dropdown>
						</td>
					</sys:editorlayout-row>				
				</sys:editorlayout>
			</form>
			<sys:grid action="${ctx }/manage/user-manage!listUserByGroup.action" formId="listForm" id="userform" cssClass="its">
				<sys:grid-column name="name" display="姓名"></sys:grid-column>
				<sys:grid-column name="userName" display="用户名"></sys:grid-column>
			</sys:grid>
		</div>
	</BODY>
	 
</HTML>