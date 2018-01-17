<%@ page language="java" pageEncoding="utf-8"%>
<%@ taglib uri="/WEB-INF/sys.tld" prefix="sys" %><%@ taglib uri="/WEB-INF/c.tld" prefix="c" %><%@ taglib uri="/WEB-INF/fmt.tld" prefix="fmt" %><%@ taglib uri="/WEB-INF/fn.tld" prefix="fn" %> 
<!DOCTYPE HTML>
<HTML>
	<HEAD>
		<sys:head />
		<TITLE>修改菜单</TITLE>
		<%@ include file="../../commons/dtree.jsp"%>
		<META http-equiv=Content-Type content="text/html; charset=utf-8"><meta name="viewport" content="width=device-width, initial-scale=1.0">
		<STYLE type=text/css>
#menuTree A {
	COLOR: #566984;
	TEXT-DECORATION: none
}

.STYLE2 {
	font-size: x-large
}
</STYLE>

		<style type="text/css" media="all">
body,div {
	font-size: 12px;
}
</style>

		<META content="MSHTML 6.00.2900.5848" name=GENERATOR>

		<script type="text/javascript">
				$(document).ready(function(){
					$.formValidator.initConfig( {
						formID : "form1",
						onError : function() {
						},
						onSuccess:function(){
							
						}
					});
				
				$("#menuName").formValidator({
					onshow:"请输入",
					onfocus:"不能为空",
					oncorrect:"合法"}).inputValidator(
					{min:1,empty:{leftempty:false,rightempty:false,emptyerror:"菜单名两边不能有空符号"},
					onerror:"菜单名不能为空,请确认"});
				$("#orders").formValidator( {
					onShow : "格式:1,1.2",
					onfocus : "请输入",
					oncorrect : "排序值合法"
				}).inputValidator( {
					type:"number",
					max:100,
					min : 1,
					onerror : "请输入整数，请确认"
				});
				$("#menuUrl").formValidator({
					onshow:"请输入",
					oncorrect:"路径合法"});
				$("#imageUrl").formValidator({
					onshow:"请输入",
					oncorrect:"合法"});
			});
				
				
			/**function doSubmit(){
				$.ajax({
					  type: "post",
					  url: $("#form1").attr("action"),
					  cache: false,
					  data: $("#form1").serialize(),
					  dataType: "json",
					  async:false,
					  success: function(json){
						  $.common.alert({text:json.message});
						  parent.curMenuId=json.data;//
						  parent.queryTree();//刷新机构列表
					  }
				});
				return false;
			}**/
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
						<form name="form1" id="form1" method="post"
							action="${ctx }/manage/menu-manage!alter.action">
						<div class="col-4" style="overflow: auto;height: 300px">

											<p>
												<a href="javascript: d.openAll();">展开</a> |
												<a href="javascript: d.closeAll();">关闭</a>
											</p>

											<script type="text/javascript">
											<!--
									 		
											d = new dTree('d');
											d.add(0,-1,"<input type='radio' name='parentId' value='0' checked >系统菜单");
											<c:forEach items="${menuList}" var="menu" varStatus="s">
												
											   d.add("${menu.menuId}", "${menu.parentId}", "<input type='radio' name='parentId' value='${menu.menuId}'" +
											   "	<c:if test="${menu.menuId==menuInfo.parentId}">checked</c:if>" +
											   " 	<c:if test="${menu.menuId==menuInfo.menuId}">disabled=\"disabled\"</c:if>" +
											   " 	<c:if test="${menu.parentId==menuInfo.menuId}">disabled=\"disabled\"</c:if>" +
											   ">${menu.menuName }");       	
											</c:forEach>
										  	 
											document.write(d);
									
											//-->
									
										</script>

							</div>	
							<div class="col-8">	
								<input type="hidden" name="obj.menuId" value="${obj.menuId}"/>
								<sys:editorlayout cols="1,3,2">
									<sys:editorlayout-row>
										<sys:textfield name="obj.menuName" id="menuName" label="菜单名" showTip="true"/>
									</sys:editorlayout-row>
									<sys:editorlayout-row>
										<sys:textfield name="obj.orders" id="orders" label="排序" showTip="true"/>
									</sys:editorlayout-row>
									<sys:editorlayout-row>
										<sys:textfield name="obj.menuUrl" id="menuUrl" label="url" showTip="true"/>
									</sys:editorlayout-row>
									<sys:editorlayout-row>
										<sys:textfield name="obj.imageUrl" id="imageUrl" label="图片" showTip="true"/>
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
