<%@ page language="java" pageEncoding="utf-8"%>
<%@ taglib uri="/WEB-INF/sys.tld" prefix="sys" %><%@ taglib uri="/WEB-INF/c.tld" prefix="c" %><%@ taglib uri="/WEB-INF/fmt.tld" prefix="fmt" %><%@ taglib uri="/WEB-INF/fn.tld" prefix="fn" %> 
<!DOCTYPE HTML>
<HTML>
	<HEAD>
		<sys:head />
		<TITLE>设置权限</TITLE>
		<%@ include file="../../commons/dtree.jsp"%>
	</HEAD>
	<BODY>
				<form name="form1" id="form1" method="post"
							action="${ctx }/manage/role-manage!setActionByRole.action" target="mainFrame">
							<table width="591" border="0" align="center">
								<tr>
									<td height="45" colspan="2" align="right">
										<div align="left">
											<span class="STYLE3">权限名:${role.roleName}</span>
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
											分配权限：
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
		d.add(0,-1,"权限分配");
		<c:forEach items="${menuList}" var="menu" varStatus="s">
			
		   d.add("${menu.menuId}", 0, "<input name='pojo.menus' id='${menu.menuId}' type='checkbox'   onClick='checkStatus(this)'>${menu.menuName }");   
		   <c:forEach items="${menu.actions}" var="action" varStatus="sf">
					
			d.add("${action.actionId}","${menu.menuId}","<input name='pojo.actions' id='${menu.menuId}-${action.actionId}' type='checkbox' value='${action.actionId}' <c:forEach items="${role.actions}" var='actionInfo' varStatus='st'><c:if test='${action.actionId==actionInfo.actionId }'>checked</c:if></c:forEach>>${action.actionName}");		
												
			</c:forEach>    	
		</c:forEach>
	  	 
		document.write(d);

		//-->
function checkStatus(menuCheck){

	var id=menuCheck.id;
    var actions = document.getElementsByName('actions');

    for(var i=0;i<actions.length;i++){
  
    	 if(id==actions[i].actionId.split("-")[0]){
    	 	
    	   if(menuCheck.checked){   	 				
    	 		actions[i].checked=true;
    	 	}else{  	 		
    	 		actions[i].checked=false;
    	 	}
    	 }
    }
	
	
}
	</script>
</p>
</div>
									</td>
								</tr>

								<tr>
									<td width="148" height="30">&nbsp;
										
									</td>
									<td width="433">
										<input type="submit" name="submit" id="submit" onclick="submitF()" value="提交" />
									</td>
								</tr>
							</table>
							：
						</form>
	</BODY>
</HTML>
