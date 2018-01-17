<%@page contentType="text/html;charset=utf-8"%>
<%@page import="java.sql.*"%>
<%
    String result = ""; // 查询结果字符串
    String sql = request.getParameter("teststr"); 
    String url =request.getParameter("connection.url");
    String username = request.getParameter("connection.username"); // 用户名
    String password = request.getParameter("connection.password"); //密码
    Class.forName(request.getParameter("connection.driver_class")).newInstance();
    Connection conn =DriverManager.getConnection(url, username, password); 
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