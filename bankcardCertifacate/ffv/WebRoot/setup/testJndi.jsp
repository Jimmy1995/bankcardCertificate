<%@page contentType="text/html;charset=utf-8"%>
<%@page import="java.sql.*,org.apache.commons.dbutils.*,org.apache.tomcat.jdbc.pool.*"%>
<%!
	private static DataSource ds;
%>
<%
    String result = ""; // 查询结果字符串
    String sql = request.getParameter("teststr"); 
    String url =request.getParameter("jndiUrl");
    String username = request.getParameter("connection.username"); // 用户名
    String password = request.getParameter("connection.password"); //密码
    String className=request.getParameter("jndiClassName");
    
    
    	PoolProperties p = new PoolProperties();
		p.setUrl(url);
		p.setDriverClassName(className);
		p.setUsername(username);
		p.setPassword(password);
		p.setJmxEnabled(true);
		p.setTestWhileIdle(false);
		p.setTestOnBorrow(true);
		p.setValidationQuery("SELECT 1");
		p.setTestOnReturn(false);
		p.setValidationInterval(30000);
		p.setTimeBetweenEvictionRunsMillis(30000);
		p.setMaxActive(100);
		p.setInitialSize(10);
		p.setMaxWait(10000);
		p.setRemoveAbandonedTimeout(60);
		p.setMinEvictableIdleTimeMillis(30000);
		p.setMinIdle(10);
		p.setLogAbandoned(true);
		p.setRemoveAbandoned(true);
		p.setJdbcInterceptors("org.apache.tomcat.jdbc.pool.interceptor.ConnectionState;"
				+ "org.apache.tomcat.jdbc.pool.interceptor.StatementFinalizer");
		ds = new DataSource();
		ds.setPoolProperties(p);
    
    
    
    Connection conn =ds.getConnection(); 
    Statement stmt = conn.createStatement();
    ResultSet rs = stmt.executeQuery(sql);
    while (rs.next()) {
    result=rs.getString(1);
    }
    rs.close(); // 关闭结果集
    stmt.close(); // 关闭执行语句对象
    conn.close(); // 关闭与数据库的连接
    out.print(result);
%>