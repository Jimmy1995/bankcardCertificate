<%@ page language="java" pageEncoding="utf-8"%>
<%@ taglib uri="/WEB-INF/sys.tld" prefix="sys" %><%@ taglib uri="/WEB-INF/c.tld" prefix="c" %><%@ taglib uri="/WEB-INF/fmt.tld" prefix="fmt" %><%@ taglib uri="/WEB-INF/fn.tld" prefix="fn" %> 
<!DOCTYPE HTML>
<HTML>
	<HEAD>
		<sys:head />
		<TITLE>新增权限</TITLE>
		<script type="text/javascript">
		$(document).ready(function() {
			$.formValidator.initConfig( {
				formID : "form1",
				onError : function() {
				},
				onSuccess:function(){
					
				}
			});

			$("#actionName").formValidator( {
				onFhow : "请输入权限描述",
				onFocus : "权限描述不能为空",
				onCorrect : "权限描述合法"
			}).inputValidator( {
				min : 1,
				empty : {
					leftempty : false,
					rightempty : false,
					emptyerror : "权限描述两边不能有空符号"
				},
				onError : "权限描述不能为空,请确认"
			});
			$("#actionPath").formValidator( {
				onShow : "请输入权限路径",
				onFocus : "权限路径不能为空",
				onCorrect : "权限路径合法"
			}).inputValidator( {
				min : 1,
				empty : {
					leftempty : false,
					rightempty : false,
					emptyerror : "权限路径两边不能有空符号"
				},
				onError : "权限路径不能为空,请确认"
			});
			$("#orders").formValidator( {
				onShow : "输入排序 ",
				onFocus : "请输入排序值",
				onCorrect : "排序值合法"
			}).inputValidator( {
				type:"number",
				max:100,
				min : 1,
				onError : "请输入整数，请确认"
			});
		});
	
	
	function submitFun(){
		if(!$.formValidator.pageIsValid("1")){return ;}
		var formobj=$("#form1");
		$.post(formobj.attr("action"),formobj.serialize(),function(json){
			if(json.errorType=='0'){
				$("#button").hide();
				$.common.alert({text:"保存成功!"});
			}else{
				$.common.alert({text:json.message});
			}
		},"json");
}	
</script>


	</HEAD>
	<BODY>
	
						<form name="form1" id="form1" method="post"	action="${ctx }/manage/action-manage!save.action" >
							 
							<sys:editorlayout cols="1,3,2">
								<sys:editorlayout-row>
									<sys:textfield name="actionName" id="actionName" showTip="true" label="权限描述"></sys:textfield>
								</sys:editorlayout-row>
								<sys:editorlayout-row>
									<sys:textfield name="orders" id="orders" showTip="true" label="排序"></sys:textfield>
								</sys:editorlayout-row>
								<sys:editorlayout-row>
									<sys:select list="#request.menuList" name="menuId" label="所属模块" listKey="menuId" listValue="menuName" showTip="true" value="#request.menuId"></sys:select>
								</sys:editorlayout-row>
									<sys:textarea name="actionPath" id="actionPath" showTip="true" label="权限路径"></sys:textarea>
								<sys:editorlayout-row>
									<td colspan="3" align="center">
										<input type="button" onclick="submitFun();" class="btn btn-info" name="button" id="button" value="提交" />
									</td>
								</sys:editorlayout-row>
							</sys:editorlayout>
						</form>
					
	</BODY>
</HTML>
