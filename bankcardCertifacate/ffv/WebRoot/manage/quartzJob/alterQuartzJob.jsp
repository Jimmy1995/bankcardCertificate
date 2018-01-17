<%@ page language="java" pageEncoding="utf-8"%>
<%@ taglib uri="/WEB-INF/sys.tld" prefix="sys" %><%@ taglib uri="/WEB-INF/c.tld" prefix="c" %><%@ taglib uri="/WEB-INF/fmt.tld" prefix="fmt" %><%@ taglib uri="/WEB-INF/fn.tld" prefix="fn" %> 
<!DOCTYPE HTML>
<HTML>
	<HEAD>
		<sys:head />
		<TITLE>修改任务</TITLE>
		<script type="text/javascript">
	$(document).ready(function() {
		$.formValidator.initConfig( {
			formID : "form1",
			onError : function() {
			},
			onSuccess:function(){
			}
		});
			$("#jobName").formValidator( {
				onShow : "请输入",
				onFocus : "不能为空",
				onCorrect : "合法"
			}).inputValidator( {
				min : 1,
				empty : {
					leftEmpty : false,
					rightEmpty : false,
					emptyError : "两边不能有空符号"
				},
				onError : "不能为空,请确认"
			});
			
			$("#jobGroup").formValidator( {
				onShow : "请输入",
				onFocus : "不能为空",
				onCorrect : "合法"
			}).inputValidator( {
				min : 1,
				empty : {
					leftEmpty : false,
					rightEmpty : false,
					emptyError : "两边不能有空符号"
				},
				onError : "不能为空,请确认"
			});
			
			$("#description").formValidator( {
				onShow : "请输入",
				onFocus : "不能为空",
				onCorrect : "合法"
			}).inputValidator( {
				min : 1,
				empty : {
					leftEmpty : false,
					rightEmpty : false,
					emptyError : "两边不能有空符号"
				},
				onError : "不能为空,请确认"
			});
			
			$("#cronExpression").formValidator( {
				onShow : "请输入",
				onFocus : "不能为空",
				onCorrect : "合法"
			}).inputValidator( {
				min : 1,
				empty : {
					leftEmpty : false,
					rightEmpty : false,
					emptyError : "两边不能有空符号"
				},
				onError : "不能为空,请确认"
			});
			
			$("#stateFulljobExecuteClass").formValidator( {
				onShow : "请输入",
				onFocus : "不能为空",
				onCorrect : "合法"
			}).inputValidator( {
				min : 1,
				empty : {
					leftEmpty : false,
					rightEmpty : false,
					emptyError : "两边不能有空符号"
				},
				onError : "不能为空,请确认"
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
						<form name="form1" id="form1" method="post"	action="${ctx }/manage/quartz-job-manage!alter.action" >
							 
							<input type="hidden" id="id" name="obj.id" value="${obj.id}" />
							<sys:editorlayout cols="1,3,2">
								<sys:editorlayout-row>
									<sys:textfield name="obj.jobName" id="jobName" label="任务名称" showTip="true"/>
								</sys:editorlayout-row>
								<sys:editorlayout-row>
									<sys:textfield name="obj.jobGroup" id="jobGroup" label="任务组名" showTip="true"/>
								</sys:editorlayout-row>
								<sys:editorlayout-row>
									<sys:textfield name="obj.description" id="description" label="任务描述" showTip="true"/>
								</sys:editorlayout-row>
								<sys:editorlayout-row>
									<sys:textfield name="obj.cronExpression" id="cronExpression" label="表达式" showTip="true"/>
								</sys:editorlayout-row>
								<sys:editorlayout-row>
									<sys:textfield name="obj.stateFulljobExecuteClass" id="stateFulljobExecuteClass" label="运行类" showTip="true"/>
								</sys:editorlayout-row>
								<sys:editorlayout-row>
									<td nowrap="nowrap" colspan="3" align="center">
										<input type="button" onclick="submitFun();" class="btn btn-info" name="button" id="button" value="提交" />
									</td>
								</sys:editorlayout-row>
							</sys:editorlayout>
							<%--<ul> 
						     <li>"0 0 12 * * ?" 每天中午12点触发</li> 
						     <li>"0 15 10 ? * *" 每天上午10:15触发</li> 
						     <li>"0 15 10 * * ?" 每天上午10:15触发 </li> 
						     <li>"0 15 10 * * ? *" 每天上午10:15触发</li>
						     <li>"0 15 10 * * ? 2005" 2005年的每天上午10:15触发 </li>
						     <li>"0 * 14 * * ?" 在每天下午2点到下午2:59期间的每1分钟触发</li>
						     <li>"0 0/5 14 * * ?" 在每天下午2点到下午2:55期间的每5分钟触发</li>
						     <li>"0 0/5 14,18 * * ?" 在每天下午2点到2:55期间和下午6点到6:55期间的每5分钟触发</li>
						     <li>"0 0-5 14 * * ?" 在每天下午2点到下午2:05期间的每1分钟触发</li>
						     <li>"0 10,44 14 ? 3 WED" 每年三月的星期三的下午2:10和2:44触发</li>
						     <li>"0 15 10 ? * MON-FRI" 周一至周五的上午10:15触发</li>
						     <li>"0 15 10 15 * ?" 每月15日上午10:15触发</li>
						     <li>"0 15 10 L * ?" 每月最后一日的上午10:15触发</li> 
						     <li>"0 15 10 ? * 6L" 每月的最后一个星期五上午10:15触发</li>
						     <li>"0 15 10 ? * 6L 2002-2005" 2002年至2005年的每月的最后一个星期五上午10:15触发</li>
						     <li>"0 15 10 ? * 6#3" 每月的第三个星期五上午10:15触发 </li>
						   </ul> 
						--%></form>
					
	</BODY>
</HTML>
