package com.common.web.log;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.UUID;

import org.apache.log4j.jdbc.JDBCAppender;

import com.common.util.ConnectionUtil;

/**
 * @author 龙家小哥
 * @version 1.x
 * 本程序可自动查找系统中的JNDI用的将系统日志存入数据库
 */
public class JDBCPoolAppender extends JDBCAppender {
	private String jndiName;// java:comp/env/jdbc/role
	private static ConnectionUtil connectionUtil=new ConnectionUtil();
	@Override
	protected void execute(String arg0) throws SQLException {
		Connection con = null;
		Statement stmt = null;
		String sql = arg0.replace("{Primarykey}", UUID.randomUUID() + "");// 在这儿主要是把主键替换成UUID
		try {
			con = getConnection();
			stmt = con.createStatement();
			stmt.executeUpdate(sql);
		} catch (SQLException e) {
			throw e;
		}finally{
			if (stmt != null)
				stmt.close();
			if(con!=null&&!con.isClosed()){
				con.close();
			}
		}
	}

	@Override
	protected Connection getConnection() throws SQLException {
		ConnectionUtil.setJndiName(jndiName);
		Connection con=connectionUtil.getConnection();
		setJndiName(ConnectionUtil.getJndiName());
		return con;
	}

	public String getJndiName() {
		return jndiName;
	}

	public void setJndiName(String jndiName) {
		this.jndiName = jndiName;
	}

}
