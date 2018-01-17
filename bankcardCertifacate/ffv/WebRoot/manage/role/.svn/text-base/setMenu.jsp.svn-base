<%@ page language="java" pageEncoding="utf-8"%>
<%@ taglib uri="/WEB-INF/sys.tld" prefix="sys" %><%@ taglib uri="/WEB-INF/c.tld" prefix="c" %><%@ taglib uri="/WEB-INF/fmt.tld" prefix="fmt" %><%@ taglib uri="/WEB-INF/fn.tld" prefix="fn" %> 
<!DOCTYPE HTML>
<HTML>
	<HEAD>
		<sys:head />
		<TITLE>设置菜单</TITLE>
		<%@ include file="../../commons/dtree.jsp"%>
	</HEAD>
	<BODY>
						<form name="form1" id="form1" method="post" action="${ctx }/manage/role-manage!setMenuByRole.action" target="_parent">
							<table width="591"  border="0" align="center">
								<tr>
									<td height="45" colspan="2" align="right">
										<div align="left">
											<span class="STYLE3">角色:${role.roleName}</span>
											<input type="hidden" name="roleId" value="${role.roleId}" />
										</div>
									</td>
								</tr>
								<tr>
									<td height="48" colspan="2" align="right">
										<div align="left" class="STYLE3">
											添加时间:${role.createTime}
										</div>
									</td>
								</tr>
								<tr>
									<td height="23" colspan="2" align="right" valign="top">
										<div align="left" class="STYLE3">
											分配菜单：
										</div>
									</td>
								</tr>
								<tr>
									<td height="173" colspan="2" align="right" valign="top">
										<div class="dtree" align="left">
										
										<p align="left">
											<a href="javascript: d.openAll();">展开</a> |
											<a href="javascript: d.closeAll();">关闭</a>
										
										<script type="text/javascript">
												<!--
										 		
												d = new dTree('d');
												d.add(0,-1,"<input type='checkbox' name='parentId' value='0' onClick='checkStatus(this,0)' >系统菜单");
												<c:forEach items="${menuList}" var="menu" varStatus="s">			
												   d.add("${menu.menuId}", "${menu.parentId}", "<input id='${menu.menuId}' onClick=\"checkStatus(this,'${menu.menuId}')\" class='${menu.parentId}' type='checkbox' name='pojo.menus' value='${menu.menuId}'<c:forEach items="${role.menus}" var="menuInfo" varStatus="s"><c:if test="${menu.menuId==menuInfo.menuId}">checked</c:if></c:forEach>>${menu.menuName }");       	
														<c:forEach items="${menu.actions}" var="action" varStatus="sf">
															d.add("${action.actionId}","${menu.menuId}","<input name='pojo.actions' id='${menu.menuId}-${action.actionId}' type='checkbox' class='${menu.menuId}' value='${action.actionId}' <c:forEach items="${role.actions}" var='actionInfo' varStatus='st'><c:if test='${action.actionId==actionInfo.actionId }'>checked</c:if></c:forEach>><b>${action.actionName}</b><font color='red'>权限</font>");		
														</c:forEach>
												</c:forEach>
											  	 
												document.write(d);
										
												//-->
										
											
									function checkStatus(chkBox,no){   
								     // checkPar(chkBox);//当子目录选中时,父目录也选中  
								      var chks = $('input[type=checkbox]');//得到所有input
								      for(var i=0;i <chks.length;i++){
								          if(chks[i].className == no){//ID等于传进父目录ID时   
								            chks[i].checked = chkBox.checked;//保持选中状态和父目录一致   
								            checkStatus(chks[i],chks[i].value);//递归保持所有的子目录选中   
								          }   
								      }   
								    }   
								      
								    /**function checkPar(chkBox) {   
								      if(chkBox.checked && chkBox.className != 0) {//判断本单击为选中,并且不是根目录,则选中父目录   
								        var chkObject = document.getElementById(chkBox.className);//得到父目录对象 
								        if(chkObject!=null){
								        chkObject.checked=true;  
								        checkPar(chkObject); 
								       }
								      }   
								    }**/
											
											
										
											</script>
										</p>
										</div>
									</td>
								</tr>

								<tr>
									<td width="148" height="30">&nbsp;
										
									</td>
									<td width="433">
										<input type="button" onclick="submitFun()" name="button" id="button" value="提交" />
									</td>
								</tr>
							</table>
						</form>
						
						<script type="text/javascript">
						 function submitFun(){
								var formobj=$("#form1");
								$.post(formobj.attr("action"),formobj.serialize(),function(json){
									if(json.errorType=='0'){
										//$("#button").hide();
										$.common.alert({text:"保存成功!"});
									}else{
										$.common.alert({text:json.message});
									}
								},"json");
					 	 }		
						</script>
	</BODY>
</HTML>
