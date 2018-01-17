<%@ page language="java" pageEncoding="utf-8"%>
<%@ taglib uri="/WEB-INF/sys.tld" prefix="sys" %><%@ taglib uri="/WEB-INF/c.tld" prefix="c" %><%@ taglib uri="/WEB-INF/fmt.tld" prefix="fmt" %><%@ taglib uri="/WEB-INF/fn.tld" prefix="fn" %> 
<!DOCTYPE HTML>
<HTML>
	<HEAD>
		<sys:head /> 
<TITLE>程序初始化安装</TITLE>
<META http-equiv=Content-Type content="text/html; charset=utf-8">
		<script type="text/javascript">
			function testConnection(urlStr){//异步
				$.ajax({
				  type: "POST",
				  url: urlStr,
				  data:"teststr="+$("#teststr").val()+"&connection.username="+$("#username").val()+"&connection.password="+$("#password").val()+"&connection.url="+$("#connectionUrl").val()
				  +"&jndiUrl="+$("#jndiUrl").val()+"&connection.driver_class="+$("#connectionDriver").val()+"&jndiClassName="+$("#jndiClassName").val(),
				  datatype : "json",
				  success : function(data){	
		            if( $.trim(data) == "1" ){		
		                alert("测试成功！");
					}
		            else{
		                alert("失败！请重新配置");
					}
				  },	
				  error: function(){alert("失败！请重新配置");}
				});
			}
			
			function selectDatabase(database){
				if(database=="mssql"){
					$("#dialect").val("org.hibernate.dialect.SQLServerDialect");
					$("#teststr").val("select 1");
					$("#connectionUrl").val("jdbc:sqlserver://localhost:1433;DatabaseName=role");
					$("#jndiUrl").val("jdbc:jtds:sqlserver://localhost:1433/role");
					$("#connectionDriver").val("com.microsoft.sqlserver.jdbc.SQLServerDriver");
					$("#jndiClassName").val("net.sourceforge.jtds.jdbc.Driver");
				}
				if(database=='orcl'){
					$("#teststr").val("select 1 from dual");
					$("#dialect").val("org.hibernate.dialect.Oracle10gDialect");
					$("#connectionUrl").val("jdbc:oracle:thin:@localhost:1521:orcl");
					$("#jndiUrl").val("jdbc:oracle:thin:@localhost:1521:orcl");
					$("#connectionDriver").val("oracle.jdbc.driver.OracleDriver");
					$("#jndiClassName").val("oracle.jdbc.driver.OracleDriver");
				}
			}
		</script>
	</head>
	<body>
		<center>
		<h1>程序初始化安装！</h1>
			<form action="${ctx }/servlet/setUp" method="post">
				<table>
					<tr>
						<td colspan="2" align="center">
							<input type="button" value="mssql连接" onclick="selectDatabase('mssql')"/>
							<input type="button" value="oracle连接" onclick="selectDatabase('orcl')"/>
						</td>
					</tr>
					<tr>
						<td align="right">测试sql语句：</td>
						<td><input id="teststr" name="teststr" value="select 1 from dual" size="50"/></td>
					</tr>
					<tr>
						<td align="right">用户名：</td>
						<td><input id="username" name="connection.username" value="role" size="50"/></td>
					</tr>
					<tr>
						<td align="right">密码：</td>
						<td><input id="password" name="connection.password" value="123" size="50"/></td>
					</tr>
					<tr>
						<td align="right">数据库方言：</td>
						<td>
						
						<!--<input id="dialect" name="dialect" value="org.hibernate.dialect.SQLServerDialect" size="50"/>
						-->
						
						<input id="dialect" name="dialect" value="org.hibernate.dialect.Oracle10gDialect" size="50"/>
						</td>
					</tr>
					<tr>
						<td align="right">(jDBC)数据库连接地址：</td>
						<td>
						<!--<input id="connectionUrl" name="connection.url" value="jdbc:sqlserver://localhost:1433;DatabaseName=role" size="50"/>
							--><input id="connectionUrl" name="connection.url"
							value="jdbc:oracle:thin:@localhost:1521:orcl" size="50"/>
							</td>
					</tr>
					<tr>
						<td align="right">(JNDI)数据库连接地址：</td>
						<td>
						<!--<input id="jndiUrl" name="jndiUrl" value="jdbc:jtds:sqlserver://localhost:1433/role" size="50"/>
						-->
						<input id="jndiUrl" name="jndiUrl" value="jdbc:oracle:thin:@localhost:1521:orcl" size="50"/>
						</td>
					</tr>
					<tr>
						<td align="right">(jDBC)数据库连接驱动：</td>
						<td>
						<!--<input id="connectionDriver" name="connection.driver_class" value="com.microsoft.sqlserver.jdbc.SQLServerDriver" size="50"/>
						-->
						<input id="connectionDriver" name="connection.driver_class" value="oracle.jdbc.driver.OracleDriver" size="50"/>
						</td>
					</tr>
					<tr>
						<td align="right">(JNDI)数据库连接驱动：</td>
						<td>
						<!--<input id="jndiClassName" name="jndiClassName" value="net.sourceforge.jtds.jdbc.Driver" size="50"/>
						-->
						
						<input id="jndiClassName" name="jndiClassName" value="oracle.jdbc.driver.OracleDriver" size="50"/>
						</td>
					</tr>
					<tr>
						<td colspan="2" align="center">
						<input type="button" value="测试jdbc连接" onclick="testConnection('${ctx }/setup/testJdbc.jsp')"/>
						<input type="button" value="测试JNDI连接" onclick="testConnection('${ctx }/setup/testJndi.jsp')"/>
						<input type="submit" value="马上安装"/>
						</td>
					</tr>
					<tr>
				</table>
			</form>
		</center>
	</body>
</html>
