<%@ page language="java" pageEncoding="utf-8"%>
<%@ taglib uri="/WEB-INF/sys.tld" prefix="sys" %><%@ taglib uri="/WEB-INF/c.tld" prefix="c" %><%@ taglib uri="/WEB-INF/fmt.tld" prefix="fmt" %><%@ taglib uri="/WEB-INF/fn.tld" prefix="fn" %> 
<!DOCTYPE HTML>
<HTML>
	<HEAD>
		<sys:head />
<TITLE>新增用户</TITLE>
<STYLE type=text/css> 
{
	FONT-SIZE: 12px
}
#menuTree A {
	COLOR: #566984; TEXT-DECORATION: none
}
.STYLE2 {font-size: x-large}
</STYLE>

<style type="text/css" media="all"> 
body,div{font-size:12px;}
</style> 

<META content="MSHTML 6.00.2900.5848" name=GENERATOR>

<script type="text/javascript">
function doSubmit(){
	$.ajax({
		  type: "post",
		  url: $("#form1").attr("action"),
		  cache: false,
		  data: $("#form1").serialize(),
		  dataType: "json",
		  async:false,
		  success: function(json){
			  $.common.alert({text:json.message});
		  }
	});
	return false;
}
</script>
	

</HEAD>
<BODY>
      <form name="form1" id="form1" method="post" action="${ctx }/manage/user-manage!setRoleByUser.action" onsubmit="return doSubmit();">
      	<sys:editorlayout cols="1,2">
      		<sys:editorlayout-row>
      			<input type="hidden" name="userId" value="${obj.userId }"/>
      			<sys:textfield label="用户名" name="obj.name" id="name"></sys:textfield>
      		</sys:editorlayout-row>
      		<sys:editorlayout-row>
      			<td class="tdLabel">
      				分配角色
      			</td>
      			<td>
      				<ul>
      				<c:forEach items="${roleList}" var="role" varStatus="s">
      				   <li>
			   	        <input name="pojo.roles" type="checkbox" id="${role.roleId}" value="${role.roleId}"  
				   	        <c:forEach items="${obj.roles}" var="roleInfo" varStatus="st">
					   	            <c:if test="${role.roleId==roleInfo.roleId }">
					   	       		 checked
					   	       		 </c:if>
				   	        </c:forEach>
			   	        ><label for='${role.roleId}' style="cursor: pointer">${role.roleName}</label>
					   </li>	
		   	        </c:forEach>
		   	        </ul>
      			</td>
      		</sys:editorlayout-row>
      		<sys:editorlayout-row>
      			<td colspan="2" align="center"><input class="btn btn-info" type="submit" name="button" id="button" value="提交" /></td>
      		</sys:editorlayout-row>
      	</sys:editorlayout>
      </form>
     </BODY>
</HTML>
