package com.common.util;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import javax.naming.Binding;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.sql.DataSource;

import org.apache.log4j.Logger;
/**
 * 获取数据库连接
 * @author longzy
 *
 */
public class ConnectionUtil {
	Logger log = Logger.getLogger(ConnectionUtil.class);
	private static String jndiName;// java:comp/env/jdbc/role
	private static String initName="java:comp/env";//初始JNDI名
	private Connection connection=null;
	private static Context initCtx;
	private static DataSource dataSource;
	
	public ConnectionUtil() {
		try {
			if (initCtx == null) {
			initCtx = new InitialContext();
			}
		} catch (NamingException e) {
			e.printStackTrace();
		}
	}
	/**
	 * 执行sql语句
	 * @param sql
	 * @throws SQLException
	 */
	public void execute(String sql) throws SQLException {
		Connection con = null;
		Statement stmt = null;
		try {
			con = getConnection();
			stmt = con.createStatement();
			stmt.executeUpdate(sql);
		} catch (SQLException e) {
			if (stmt != null)
				stmt.close();
			throw e;
		}
		stmt.close();
		close();
	}
	/**
	 * 得到数据库连接
	 * @return connection
	 * @throws SQLException
	 */
	public Connection getConnection() throws SQLException {
		if (connection == null||connection.isClosed()) {//如果connection为空则重新查找。正常来说，connection都是被关掉了的。。。不过这儿做一个判断也好
			try {
				if (dataSource == null&&jndiName == null) {
					initJndi(initCtx,initName);
					log.info("===JNDI从系统中查找成功："+jndiName);
				}
				if (dataSource == null) {
					dataSource = (DataSource) initCtx.lookup(jndiName);
				}
			} catch (NamingException e) {// 如果得到dataSource有误（jndiname有错，那就再到系统中找一个jndi试试）
				log.info("===jndiName无效，重新查找系统中的jndiName");
				try {
					if (dataSource == null) {
						initJndi(initCtx,initName);
						dataSource = (DataSource) initCtx.lookup(jndiName);
						log.info("===成功找到系统中jndi为" + jndiName);
					}
				} catch (NamingException e1) {
					log.error("配置出错(jndiName有误)，或者是系统中根本没有配置JNDI。请重新配置===");
				}
			}
			connection=dataSource.getConnection();
		}
		return connection;
	}
	/**
	 * 关闭连接
	 */
	public void close(){
		try {
			if(connection!=null&&!connection.isClosed()){
				connection.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	/**
	 * 初始化jndiName,从系统中自动寻找，比如jdbc/role
	 * 
	 * @param initCtx
	 * @throws NamingException
	 */
	private void initJndi(Context initCtx ,String nameString) throws NamingException {
		setJndiName(nameString);
		NamingEnumeration <Binding> dataS = (NamingEnumeration <Binding>) initCtx.listBindings(jndiName);
		while (dataS.hasMore()) {
			Binding bd = (Binding)dataS.next();
			if(bd.getObject() instanceof DataSource){
				setJndiName(getJndiName() + "/" + bd.getName());
				return;
			}
			initJndi(initCtx,getJndiName() + "/" + bd.getName());
		} 
	}
	
	

	/**
	 * @return the jndiName
	 */
	public static String getJndiName() {
		return jndiName;
	}
	/**
	 * @param jndiName the jndiName to set
	 */
	public static void setJndiName(String jndiName) {
		ConnectionUtil.jndiName = jndiName;
	}
	/**
	 * @return the initName
	 */
	public static String getInitName() {
		return initName;
	}
	/**
	 * @param initName the initName to set
	 */
	public static void setInitName(String initName) {
		ConnectionUtil.initName = initName;
	}
	/**
	 * @return the initCtx
	 */
	public static Context getInitCtx() {
		return initCtx;
	}
	/**
	 * @param initCtx the initCtx to set
	 */
	public static void setInitCtx(Context initCtx) {
		ConnectionUtil.initCtx = initCtx;
	}
	/**
	 * @return the dataSource
	 */
	public static DataSource getDataSource() {
		return dataSource;
	}
	/**
	 * @param dataSource the dataSource to set
	 */
	public void setDataSource(DataSource dataSource) {
		ConnectionUtil.dataSource = dataSource;
	}
	/**
	 * @param connection the connection to set
	 */
	public void setConnection(Connection connection) {
		this.connection = connection;
	}
}
