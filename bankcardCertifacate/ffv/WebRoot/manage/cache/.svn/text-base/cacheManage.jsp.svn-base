<%@ page language="java" pageEncoding="utf-8"%>
<%@ page import="java.io.*" %>
<%@ page import="java.net.*" %>
<%@ page import="java.util.*" %>
<%@ page import="java.text.*" %>
<%@ page import="javax.naming.*" %>
<%@ taglib uri="/WEB-INF/sys.tld" prefix="sys" %><%@ taglib uri="/WEB-INF/c.tld" prefix="c" %><%@ taglib uri="/WEB-INF/fmt.tld" prefix="fmt" %><%@ taglib uri="/WEB-INF/fn.tld" prefix="fn" %> 
<!DOCTYPE HTML>
<HTML>
	<HEAD>
		<sys:head />
<link href="<%=request.getContextPath()%>/css/common.css" rel="stylesheet" type="text/css" >
<title>刷新系统缓存</title>
<script type="text/javascript">
			function refreshRoleCache(urlStr){//异步刷新cache中的角色
				$.ajax({
				  type: "GET",
				  url: urlStr,
				  datatype : "json",
				  success : function(data){
					if(data.message){
		            	$.common.alert({text:data.message});
		            }
		            if(data.errorType=='0'){
			            $("#cacheMap").html("<thead><TR><TH>名称</TH><TH  scope=col>信息</TH></TR></thead>");
			            $.each(data.data,function(i,n){
			            	$("#cacheMap").append("<tr><td>"+i+"</td><td>"+n+"</td></tr");
			            });
		            }
				  },	
				  error: function(){$.common.alert({text:"服务器没有返回数据，可能服务器忙，请重试"});}
				});
			}
			refreshRoleCache('${ctx }/manage/refresh-cache!list.action');
</script>
</head>
<body style="margin-left:50px;margin-right:50px;font-size: 14px">
<hr>
<center>
<a href="javaScript:void(0)" onclick="refreshRoleCache('${ctx }/manage/refresh-cache!refresh.action');">刷新系统缓存</a>
</center>
<hr>
<table id="cacheMap" class="its">
<thead>
	<TR>
		<TH>名称</TH>
		<TH  scope=col>信息</TH>
	</TR>
</thead>	
</table>
<table id="memory" class=its>
<thead>
	<TR>
		<TH>内存总量</TH>
		<TH  scope=col>最大内存</TH>
		<TH  scope=col>空闲内存</TH>
	</TR>
</thead>
<tbody>	
	<tr>
		<td><%=Runtime.getRuntime().totalMemory()/1024/1024%>MB</td>
		<td><%=Runtime.getRuntime().maxMemory()/1024/1024%>MB</td>
		<td><%=Runtime.getRuntime().freeMemory()/1024/1024%>MB</td>
	</tr>
</tbody>	
</table>
</body>

</html>


