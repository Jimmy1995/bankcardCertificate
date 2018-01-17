<%@ page language="java" pageEncoding="utf-8"%>
<%@ taglib uri="/WEB-INF/sys.tld" prefix="sys" %><%@ taglib uri="/WEB-INF/c.tld" prefix="c" %><%@ taglib uri="/WEB-INF/fmt.tld" prefix="fmt" %><%@ taglib uri="/WEB-INF/fn.tld" prefix="fn" %> 
<!DOCTYPE HTML>
<HTML>
	<HEAD>
		<sys:head />
		<TITLE>为角色设置多个人员</TITLE>
		<script type="text/javascript">
			function setUsers(){
				//if($("input[name='pojo.userIds'][checked]").length>0){
					$("#listForm").attr("action","${ctx }/manage/user-manage!setRoleByUsers.action?isThisRole=${param.isThisRole}");
					$("#listForm").submit();
					$("#listForm").attr("${ctx }/manage/user-manage!listUsersByRole.action?isThisRole=${param.isThisRole}");
				//}else{
					//$.common.alert({text:"请先选择用户"});
				//}
			}
			function queryPage(){
				$("#listForm").submit();
			}
		    $(function(){
		    	$("td[name='pageLeft']").css("display","none");
		    	$("#selectAll").click(function(){
			  		$("input[name='pojo.userIds']").each(function(){
			   			$(this).attr("checked",!this.checked);              
			    	});
			  	});
		    });
		</script>
	</HEAD>
	<BODY>
		<form id="listForm" name="listForm" action="${ctx }/manage/user-manage!listUsersByRole.action?isThisRole=${param.isThisRole}" method="post">
			<table>
				<tr>
					<td>
						<input type="hidden" name="roleid" id="roleid" value="${roleid}" />
					</td>
					<td height="23">
						用户名
						<input type="hidden" name="sessionId" id="sessionId"/>
					</td>
					<td>
						<input type="text" name="userName" id="userName" value="${userName }" />
					</td>
					<!-- <td>
						开始时间
					</td>
					<td>
						<input type="text" onClick="WdatePicker()" name="startDate"
							id="startDate" value="${startDate }">
					</td>
					<td>
						结束时间
					</td>
					<td>
						<input type="text" onClick="WdatePicker()" name="endDate"
							id="endDate" value="${endDate }">
					</td> -->
					<td>
						<input type="submit" class="btn btn-info" value="搜索" />
						<!--<input type="button" onclick="setUsers()" class="lo" value="更新角色" />-->
					</td>
				</tr>
			</table>
			<TABLE class=gridView id=ctl00_ContentPlaceHolder2_GridView1 >
				<TR>
					<TH>
						序 <input type="checkbox" id="selectAll" name="selectAll"/>
					</TH>
					<TH  scope=col>
					<%if(request.getParameter("isThisRole").equals("0")){ %>
						其它用户
					<%}else{%>
						当前角色用户						
					<%} %>
					</TH>
					<!-- <TH  scope=col>
						密码
					</TH>
					<TH  scope=col>
						时间
						<a href="javascript:order('createTime:asc')">升序</a>
						<a href="javascript:order('createTime:desc')">降序</a>
					</TH> -->
				</TR>

				<c:forEach items="${page.result}" var="user" varStatus="s">
					<TR>
						<TD  style="WIDTH: 50px">
							${(page.pageSize*(page.pageNo-1))+s.count}
							<!--<input type="checkbox" name="pojo.userIds" value="${user.userId}"
								<c:forEach var="entry" items="${user.roles}">
								    <c:if test="${entry.roleId eq roleid}" >
								    	checked
								    </c:if>
								</c:forEach>
							 />-->
							<input type="checkbox" name="pojo.userIds" value="${user.userId}"/>
						</TD>
						<TD nowrap="nowrap">
						    ${user.userName}
						</TD>
						<!--<TD >
							${user.userPassword}
						</TD>
						<TD >
							${user.createTime}
							</A>
						</TD> -->
					</TR>
				</c:forEach>
			</TABLE>
			${pageTag }
		</form>
	</BODY>
</HTML>
