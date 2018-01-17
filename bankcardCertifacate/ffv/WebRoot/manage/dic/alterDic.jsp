<%@ page language="java" pageEncoding="utf-8"%>
<%@ taglib uri="/WEB-INF/sys.tld" prefix="sys" %><%@ taglib uri="/WEB-INF/c.tld" prefix="c" %><%@ taglib uri="/WEB-INF/fmt.tld" prefix="fmt" %><%@ taglib uri="/WEB-INF/fn.tld" prefix="fn" %> 
<!DOCTYPE HTML>
<HTML>
	<HEAD>
		<sys:head />
		<TITLE>修改字典</TITLE>
		<%@ include file="../../commons/dtree.jsp"%>
		<script type="text/javascript">
	$(document).ready(function() {
		$.formValidator.initConfig( {
			formID : "form1",
			onError : function() {
			},
			onSuccess:function(){
			}
		});
		//点击获取脚本按钮，获取校验代码

		$("#codetype").formValidator( {
			onShow : "请输入键",
			onFocus : "键不能为空",
			onCorrect : "键合法"
		}).inputValidator( {
			min : 1,
			empty : {
				leftempty : false,
				rightempty : false,
				emptyError : "键两边不能有空符号"
			},
			onerror : "键不能为空,请确认"
		});
			$("#dicKey").formValidator( {
				onshow : "请输入键",
				onfocus : "键不能为空",
				oncorrect : "键合法"
			}).inputValidator( {
				min : 1,
				empty : {
					leftempty : false,
					rightempty : false,
					emptyerror : "键两边不能有空符号"
				},
				onerror : "键不能为空,请确认"
			});
			$("#dicValue").formValidator( {
				onshow : "请输入值",
				onfocus : "值不能为空",
				oncorrect : "值合法"
			}).inputValidator( {
				min : 1,
				empty : {
					leftempty : false,
					rightempty : false,
					emptyerror : "值两边不能有空符号"
				},
				onerror : "值不能为空,请确认"
			});
			$("#orders").formValidator( {
				onShow : "请输入排序",
				onfocus : "请输入排序值",
				oncorrect : "排序值合法"
			}).inputValidator( {
				type:"number",
				max:100,
				min : 1,
				onerror : "请输入整数，请确认"
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
		
						<form name="form1" id="form1" method="post"	action="${ctx }/manage/dic-manage!alter.action">
										<div class="col-3" style="overflow: auto;height: 200px"> 

											<p>
												<a href="javascript: d.openAll();">展开</a> |
												<a href="javascript: d.closeAll();">关闭</a>
											</p>

											<script type="text/javascript">
												<!--
												d = new dTree('d');
												d.add(0,-1,"<input type='radio' name='parentId' value='0' checked >系统角色");
												<c:forEach items="${roles}" var="role" varStatus="s">
												   d.add("${role.roleId}", "0", "<input type='checkBox' name='pojo.roles' value='${role.roleId}'" +
													   <c:forEach items="${obj.roles}" var="oRole" varStatus="os">
												   "<c:if test="${role.roleId==oRole.roleId}">checked</c:if>" +
												   	</c:forEach>
												   ">${role.roleName }");       	
												</c:forEach>
												document.write(d);
												//-->
											</script>

										</div>							
										<div class="col-9">
											<input type="hidden" id="dicId" name="obj.dicId" value="${obj.dicId }" />
											<sys:editorlayout cols="1,3,2">
												<sys:editorlayout-row>
													<sys:textfield name="obj.codetype" id="codetype" label="归类" showTip="true"/>
												</sys:editorlayout-row>
												<sys:editorlayout-row>
													<sys:textfield name="obj.dicKey" id="dicKey" label="代码" showTip="true"/>
												</sys:editorlayout-row>
												<sys:editorlayout-row>
													<sys:textfield name="obj.dicValue" id="dicValue" label="显示值" showTip="true"/>
												</sys:editorlayout-row>
												<sys:editorlayout-row>
													<sys:textfield name="obj.orders" id="orders" label="排序" showTip="true"/>
												</sys:editorlayout-row>
												<sys:editorlayout-row>
													<sys:textfield name="obj.description" id="description" label="描述" showTip="true"/>
												</sys:editorlayout-row>
												<sys:editorlayout-row>
													<sys:select list="#{0:'否',1:'是'}" name="obj.isOpen" showTip="true" label="所有用户"></sys:select>
												</sys:editorlayout-row>
												<sys:editorlayout-row>
													<td colspan="3" align="center">
														<input type="button" onclick="submitFun();" class="btn btn-info" name="button" id="button" value="提交" />
													</td>
												</sys:editorlayout-row>
											</sys:editorlayout>
										</div>
						</form>
					

	</BODY>
</HTML>
