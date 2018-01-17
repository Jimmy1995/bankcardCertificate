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
					$("#listForm").attr("action","${ctx }/manage/dic-manage!setRoleByDics.action?isThisRole=${param.isThisRole}");
					$("#listForm").submit();
					$("#listForm").attr("${ctx }/manage/dic-manage!listDicsByRole.action?isThisRole=${param.isThisRole}");
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
			  		$("input[name='pojo.dics']").each(function(){
			   			$(this).attr("checked",!this.checked);              
			    	});
			  	});
		    	
		    });
		</script>
	</HEAD>
	<BODY>
		<form id="listForm" name="listForm" action="${ctx }/manage/dic-manage!listDicsByRole.action?isThisRole=${param.isThisRole}" method="post">
			<table>
				<tr>
					<td>
						<input type="hidden" name="roleid" id="roleid" value="${roleid}" />
					</td>
					<td height="23">
						键
					</td>
					<td>
						<input type="text" name="dicKey" id="dicKey"
							value="${dicKey }" />
					</td>
					<td>
						<input type="submit" class="btn btn-info" value="搜索" />
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
						其它字典（键）
					<%}else{%>
						当前角色字典（键）						
					<%} %>
					</TH>
					<TH  scope=col>
						值
					</TH>
				</TR>

				<c:forEach items="${page.result}" var="dic" varStatus="s">
					<TR>
						<TD  style="WIDTH: 50px">
							${(page.pageSize*(page.pageNo-1))+s.count}
							<input type="checkbox" name="pojo.dics" value="${dic.dicId}"/>
						</TD>
						<TD nowrap="nowrap">
						    ${dic.dicKey}
						</TD>
						<TD nowrap="nowrap">
						    ${dic.dicValue}
						</TD>
					</TR>
				</c:forEach>
			</TABLE>
			${pageTag }
		</form>
	</BODY>
</HTML>
