package com.common.ext.druid;

import com.alibaba.druid.filter.FilterChain;
import com.alibaba.druid.filter.FilterEventAdapter;
import com.alibaba.druid.filter.logging.LogFilterMBean;
import com.alibaba.druid.pool.DruidConnectionHolder;
import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.pool.DruidPooledConnection;
import com.alibaba.druid.proxy.jdbc.CallableStatementProxy;
import com.alibaba.druid.proxy.jdbc.ConnectionProxy;
import com.alibaba.druid.proxy.jdbc.DataSourceProxy;
import com.alibaba.druid.proxy.jdbc.JdbcParameter;
import com.alibaba.druid.proxy.jdbc.PreparedStatementProxy;
import com.alibaba.druid.proxy.jdbc.ResultSetProxy;
import com.alibaba.druid.proxy.jdbc.StatementProxy;
import com.alibaba.druid.sql.SQLUtils;
import com.alibaba.druid.util.JdbcUtils;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Savepoint;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;

public abstract class LogFilter extends FilterEventAdapter
  implements LogFilterMBean
{
  protected String dataSourceLoggerName = "druid.sql.DataSource";
  protected String connectionLoggerName = "druid.sql.Connection";
  protected String statementLoggerName = "druid.sql.Statement";
  protected String resultSetLoggerName = "druid.sql.ResultSet";

  private boolean connectionConnectBeforeLogEnable = true;
  private boolean connectionConnectAfterLogEnable = true;
  private boolean connectionCommitAfterLogEnable = true;
  private boolean connectionRollbackAfterLogEnable = true;
  private boolean connectionCloseAfterLogEnable = true;

  private boolean statementCreateAfterLogEnable = true;
  private boolean statementPrepareAfterLogEnable = true;
  private boolean statementPrepareCallAfterLogEnable = true;

  private boolean statementExecuteAfterLogEnable = true;
  private boolean statementExecuteQueryAfterLogEnable = true;
  private boolean statementExecuteUpdateAfterLogEnable = true;
  private boolean statementExecuteBatchAfterLogEnable = true;
  private boolean statementExecutableSqlLogEnable = false;

  private boolean statementCloseAfterLogEnable = true;

  private boolean statementParameterClearLogEnable = true;
  private boolean statementParameterSetLogEnable = true;

  private boolean resultSetNextAfterLogEnable = true;
  private boolean resultSetOpenAfterLogEnable = true;
  private boolean resultSetCloseAfterLogEnable = true;

  private boolean dataSourceLogEnabled = true;
  private boolean connectionLogEnabled = true;
  private boolean connectionLogErrorEnabled = true;
  private boolean statementLogEnabled = true;
  private boolean statementLogErrorEnabled = true;
  private boolean resultSetLogEnabled = true;
  private boolean resultSetLogErrorEnabled = true;
  protected DataSourceProxy dataSource;

  public LogFilter()
  {
    configFromProperties(System.getProperties());
  }

  public void configFromProperties(Properties properties)
  {
    String prop = properties.getProperty("druid.log.conn");
    if ("false".equals(prop))
      this.connectionLogEnabled = false;
    else if ("true".equals(prop)) {
      this.connectionLogEnabled = true;
    }

     prop = properties.getProperty("druid.log.stmt");
    if ("false".equals(prop))
      this.statementLogEnabled = false;
    else if ("true".equals(prop)) {
      this.statementLogEnabled = true;
    }

     prop = properties.getProperty("druid.log.rs");
    if ("false".equals(prop))
      this.resultSetLogEnabled = false;
    else if ("true".equals(prop)) {
      this.resultSetLogEnabled = true;
    }

     prop = properties.getProperty("druid.log.stmt.executableSql");
    if ("true".equals(prop))
      this.statementExecutableSqlLogEnable = true;
    else if ("false".equals(prop))
      this.statementExecutableSqlLogEnable = false;
  }

  public void init(DataSourceProxy dataSource)
  {
    this.dataSource = dataSource;
  }

  public boolean isConnectionLogErrorEnabled() {
    return this.connectionLogErrorEnabled;
  }

  public boolean isResultSetCloseAfterLogEnabled() {
    return (isResultSetLogEnabled()) && (this.resultSetCloseAfterLogEnable);
  }

  public void setResultSetCloseAfterLogEnabled(boolean resultSetCloseAfterLogEnable) {
    this.resultSetCloseAfterLogEnable = resultSetCloseAfterLogEnable;
  }

  public void setConnectionLogErrorEnabled(boolean connectionLogErrorEnabled) {
    this.connectionLogErrorEnabled = connectionLogErrorEnabled;
  }

  public boolean isResultSetLogErrorEnabled() {
    return this.resultSetLogErrorEnabled;
  }

  public void setResultSetLogErrorEnabled(boolean resultSetLogErrorEnabled) {
    this.resultSetLogErrorEnabled = resultSetLogErrorEnabled;
  }

  public boolean isConnectionConnectBeforeLogEnabled() {
    return (isConnectionLogEnabled()) && (this.connectionConnectBeforeLogEnable);
  }

  public void setConnectionConnectBeforeLogEnabled(boolean beforeConnectionConnectLogEnable) {
    this.connectionConnectBeforeLogEnable = beforeConnectionConnectLogEnable;
  }

  public boolean isConnectionCloseAfterLogEnabled() {
    return (isConnectionLogEnabled()) && (this.connectionCloseAfterLogEnable);
  }

  public boolean isConnectionRollbackAfterLogEnabled() {
    return (isConnectionLogEnabled()) && (this.connectionRollbackAfterLogEnable);
  }

  public void setConnectionRollbackAfterLogEnabled(boolean connectionRollbackAfterLogEnable) {
    this.connectionRollbackAfterLogEnable = connectionRollbackAfterLogEnable;
  }

  public void setConnectionCloseAfterLogEnabled(boolean afterConnectionCloseLogEnable) {
    this.connectionCloseAfterLogEnable = afterConnectionCloseLogEnable;
  }

  public boolean isConnectionCommitAfterLogEnabled() {
    return (isConnectionLogEnabled()) && (this.connectionCommitAfterLogEnable);
  }

  public void setConnectionCommitAfterLogEnabled(boolean afterConnectionCommitLogEnable) {
    this.connectionCommitAfterLogEnable = afterConnectionCommitLogEnable;
  }

  public boolean isConnectionConnectAfterLogEnabled() {
    return (isConnectionLogEnabled()) && (this.connectionConnectAfterLogEnable);
  }

  public void setConnectionConnectAfterLogEnabled(boolean afterConnectionConnectLogEnable) {
    this.connectionConnectAfterLogEnable = afterConnectionConnectLogEnable;
  }

  public boolean isResultSetNextAfterLogEnabled() {
    return (isResultSetLogEnabled()) && (this.resultSetNextAfterLogEnable);
  }

  public void setResultSetNextAfterLogEnabled(boolean afterResultSetNextLogEnable) {
    this.resultSetNextAfterLogEnable = afterResultSetNextLogEnable;
  }

  public boolean isResultSetOpenAfterLogEnabled() {
    return (isResultSetLogEnabled()) && (this.resultSetOpenAfterLogEnable);
  }

  public void setResultSetOpenAfterLogEnabled(boolean afterResultSetOpenLogEnable) {
    this.resultSetOpenAfterLogEnable = afterResultSetOpenLogEnable;
  }

  public boolean isStatementCloseAfterLogEnabled() {
    return (isStatementLogEnabled()) && (this.statementCloseAfterLogEnable);
  }

  public void setStatementCloseAfterLogEnabled(boolean afterStatementCloseLogEnable) {
    this.statementCloseAfterLogEnable = afterStatementCloseLogEnable;
  }

  public boolean isStatementCreateAfterLogEnabled() {
    return (isStatementLogEnabled()) && (this.statementCreateAfterLogEnable);
  }

  public void setStatementCreateAfterLogEnabled(boolean afterStatementCreateLogEnable) {
    this.statementCreateAfterLogEnable = afterStatementCreateLogEnable;
  }

  public boolean isStatementExecuteBatchAfterLogEnabled() {
    return (isStatementLogEnabled()) && (this.statementExecuteBatchAfterLogEnable);
  }

  public void setStatementExecuteBatchAfterLogEnabled(boolean afterStatementExecuteBatchLogEnable) {
    this.statementExecuteBatchAfterLogEnable = afterStatementExecuteBatchLogEnable;
  }

  public boolean isStatementExecuteAfterLogEnabled() {
    return (isStatementLogEnabled()) && (this.statementExecuteAfterLogEnable);
  }

  public void setStatementExecuteAfterLogEnabled(boolean afterStatementExecuteLogEnable) {
    this.statementExecuteAfterLogEnable = afterStatementExecuteLogEnable;
  }

  public boolean isStatementExecuteQueryAfterLogEnabled() {
    return (isStatementLogEnabled()) && (this.statementExecuteQueryAfterLogEnable);
  }

  public void setStatementExecuteQueryAfterLogEnabled(boolean afterStatementExecuteQueryLogEnable) {
    this.statementExecuteQueryAfterLogEnable = afterStatementExecuteQueryLogEnable;
  }

  public boolean isStatementExecuteUpdateAfterLogEnabled() {
    return (isStatementLogEnabled()) && (this.statementExecuteUpdateAfterLogEnable);
  }

  public void setStatementExecuteUpdateAfterLogEnabled(boolean afterStatementExecuteUpdateLogEnable) {
    this.statementExecuteUpdateAfterLogEnable = afterStatementExecuteUpdateLogEnable;
  }

  public boolean isStatementExecutableSqlLogEnable() {
    return this.statementExecutableSqlLogEnable;
  }

  public void setStatementExecutableSqlLogEnable(boolean statementExecutableSqlLogEnable) {
    this.statementExecutableSqlLogEnable = statementExecutableSqlLogEnable;
  }

  public boolean isStatementPrepareCallAfterLogEnabled() {
    return (isStatementLogEnabled()) && (this.statementPrepareCallAfterLogEnable);
  }

  public void setStatementPrepareCallAfterLogEnabled(boolean afterStatementPrepareCallLogEnable) {
    this.statementPrepareCallAfterLogEnable = afterStatementPrepareCallLogEnable;
  }

  public boolean isStatementPrepareAfterLogEnabled() {
    return (isStatementLogEnabled()) && (this.statementPrepareAfterLogEnable);
  }

  public void setStatementPrepareAfterLogEnabled(boolean afterStatementPrepareLogEnable) {
    this.statementPrepareAfterLogEnable = afterStatementPrepareLogEnable;
  }

  public boolean isDataSourceLogEnabled() {
    return this.dataSourceLogEnabled;
  }

  public void setDataSourceLogEnabled(boolean dataSourceLogEnabled) {
    this.dataSourceLogEnabled = dataSourceLogEnabled;
  }

  public boolean isConnectionLogEnabled() {
    return this.connectionLogEnabled;
  }

  public void setConnectionLogEnabled(boolean connectionLogEnabled) {
    this.connectionLogEnabled = connectionLogEnabled;
  }

  public boolean isStatementLogEnabled() {
    return this.statementLogEnabled;
  }

  public void setStatementLogEnabled(boolean statementLogEnabled) {
    this.statementLogEnabled = statementLogEnabled;
  }

  public boolean isStatementLogErrorEnabled() {
    return this.statementLogErrorEnabled;
  }

  public void setStatementLogErrorEnabled(boolean statementLogErrorEnabled) {
    this.statementLogErrorEnabled = statementLogErrorEnabled;
  }

  public boolean isResultSetLogEnabled() {
    return this.resultSetLogEnabled;
  }

  public void setResultSetLogEnabled(boolean resultSetLogEnabled) {
    this.resultSetLogEnabled = resultSetLogEnabled;
  }

  public boolean isStatementParameterSetLogEnabled() {
    return (isStatementLogEnabled()) && (this.statementParameterSetLogEnable);
  }

  public void setStatementParameterSetLogEnabled(boolean statementParameterSetLogEnable) {
    this.statementParameterSetLogEnable = statementParameterSetLogEnable;
  }

  public boolean isStatementParameterClearLogEnable() {
    return (isStatementLogEnabled()) && (this.statementParameterClearLogEnable);
  }

  public void setStatementParameterClearLogEnable(boolean statementParameterClearLogEnable) {
    this.statementParameterClearLogEnable = statementParameterClearLogEnable; } 
  protected abstract void connectionLog(String paramString);

  protected abstract void statementLog(String paramString);

  protected abstract void statementLogError(String paramString, Throwable paramThrowable);

  protected abstract void resultSetLog(String paramString);

  protected abstract void resultSetLogError(String paramString, Throwable paramThrowable);

  public void connection_connectAfter(ConnectionProxy connection) { if ((this.connectionConnectAfterLogEnable) && (isConnectionLogEnabled()))
      connectionLog("{conn-" + connection.getId() + "} connected");
  }

  public Savepoint connection_setSavepoint(FilterChain chain, ConnectionProxy connection)
    throws SQLException
  {
    Savepoint savepoint = chain.connection_setSavepoint(connection);

    if (isConnectionLogEnabled()) {
      connectionLog("{conn " + connection.getId() + "} setSavepoint-" + savepoint.getSavepointId());
    }

    return savepoint;
  }

  public Savepoint connection_setSavepoint(FilterChain chain, ConnectionProxy connection, String name)
    throws SQLException
  {
    Savepoint savepoint = chain.connection_setSavepoint(connection, name);

    if (isConnectionLogEnabled()) {
      connectionLog("{conn " + connection.getId() + "} setSavepoint-" + name);
    }

    return savepoint;
  }

  public void connection_rollback(FilterChain chain, ConnectionProxy connection) throws SQLException
  {
    super.connection_rollback(chain, connection);

    if ((this.connectionRollbackAfterLogEnable) && (isConnectionLogEnabled()))
      connectionLog("{conn " + connection.getId() + "} rollback");
  }

  public void connection_rollback(FilterChain chain, ConnectionProxy connection, Savepoint savePoint)
    throws SQLException
  {
    super.connection_rollback(chain, connection, savePoint);

    if ((this.connectionRollbackAfterLogEnable) && (isConnectionLogEnabled()))
      connectionLog("{conn " + connection.getId() + "} rollback -> " + savePoint.getSavepointId());
  }

  public void connection_commit(FilterChain chain, ConnectionProxy connection)
    throws SQLException
  {
    super.connection_commit(chain, connection);

    if ((this.connectionCommitAfterLogEnable) && (isConnectionLogEnabled()))
      connectionLog("{conn-" + connection.getId() + "} commited");
  }

  public void connection_setAutoCommit(FilterChain chain, ConnectionProxy connection, boolean autoCommit)
    throws SQLException
  {
    connectionLog("{conn-" + connection.getId() + "} setAutoCommit " + autoCommit);
    chain.connection_setAutoCommit(connection, autoCommit);
  }

  public void connection_close(FilterChain chain, ConnectionProxy connection) throws SQLException
  {
    super.connection_close(chain, connection);

    if ((this.connectionCloseAfterLogEnable) && (isConnectionLogEnabled()))
      connectionLog("{conn-" + connection.getId() + "} closed");
  }

  public void statement_close(FilterChain chain, StatementProxy statement)
    throws SQLException
  {
    super.statement_close(chain, statement);

    if ((this.statementCloseAfterLogEnable) && (isStatementLogEnabled()))
      statementLog("{conn-" + statement.getConnectionProxy().getId() + ", " + stmtId(statement) + "} closed");
  }

  protected void statementExecuteBefore(StatementProxy statement, String sql)
  {
    statement.setLastExecuteStartNano();
    if ((statement instanceof PreparedStatementProxy))
      logParameter((PreparedStatementProxy)statement);
  }

  protected void statementExecuteAfter(StatementProxy statement, String sql, boolean firstResult)
  {
    logExecutableSql(statement, sql);

    if ((this.statementExecuteAfterLogEnable) && (isStatementLogEnabled())) {
      statement.setLastExecuteTimeNano();
      double nanos = statement.getLastExecuteTimeNano();
      double millis = nanos / 1000000.0D;

      statementLog("{conn-" + statement.getConnectionProxy().getId() + ", " + stmtId(statement) + "} executed. " + millis + " millis." );
    }
  }

  protected void statementExecuteBatchBefore(StatementProxy statement)
  {
    statement.setLastExecuteStartNano();
  }

  protected void statementExecuteBatchAfter(StatementProxy statement, int[] result)
  {
    String sql;
    
    if ((statement instanceof PreparedStatementProxy))
      sql = ((PreparedStatementProxy)statement).getSql();
    else {
      sql = statement.getBatchSql();
    }

    logExecutableSql(statement, sql);

    if ((this.statementExecuteBatchAfterLogEnable) && (isStatementLogEnabled())) {
      statement.setLastExecuteTimeNano();
      double nanos = statement.getLastExecuteTimeNano();
      double millis = nanos / 1000000.0D;

      statementLog("{conn-" + statement.getConnectionProxy().getId() + ", " + stmtId(statement) + "} batch executed. " + millis + " millis." );
    }
  }

  protected void statementExecuteQueryBefore(StatementProxy statement, String sql)
  {
    statement.setLastExecuteStartNano();
    if ((statement instanceof PreparedStatementProxy))
      logParameter((PreparedStatementProxy)statement);
  }

  protected void statementExecuteQueryAfter(StatementProxy statement, String sql, ResultSetProxy resultSet)
  {
    logExecutableSql(statement, sql);

    if ((this.statementExecuteQueryAfterLogEnable) && (isStatementLogEnabled())) {
      statement.setLastExecuteTimeNano();
      double nanos = statement.getLastExecuteTimeNano();
      double millis = nanos / 1000000.0D;

      statementLog("{conn-" + statement.getConnectionProxy().getId() + ", " + stmtId(statement) + ", rs-" + resultSet.getId() + "} query executed. " + millis + " millis." );
    }
  }

  protected void statementExecuteUpdateBefore(StatementProxy statement, String sql)
  {
    statement.setLastExecuteStartNano();
    if ((statement instanceof PreparedStatementProxy))
      logParameter((PreparedStatementProxy)statement);
  }

  protected void statementExecuteUpdateAfter(StatementProxy statement, String sql, int updateCount)
  {
    logExecutableSql(statement, sql);

    if ((this.statementExecuteUpdateAfterLogEnable) && (isStatementLogEnabled())) {
      statement.setLastExecuteTimeNano();
      double nanos = statement.getLastExecuteTimeNano();
      double millis = nanos / 1000000.0D;

      statementLog("{conn-" + statement.getConnectionProxy().getId() + ", " + stmtId(statement) + "} update executed. effort " + updateCount + ". " + millis + " millis." );
    }
  }

  private void logExecutableSql(StatementProxy statement, String sql)
  {
    if (!isStatementExecutableSqlLogEnable()) {
      return;
    }

    int parametersSize = statement.getParametersSize();
    if (parametersSize == 0) {
      statementLog("{conn-" + statement.getConnectionProxy().getId() + ", " + stmtId(statement) + "} executed. \n" + SQLUtils.toSQLString(SQLUtils.toStatementList(sql, statement.getConnectionProxy().getDirectDataSource().getDbType()), statement.getConnectionProxy().getDirectDataSource().getDbType()));

      return;
    }

    List parameters = new ArrayList(parametersSize);
    for (int i = 0; i < parametersSize; i++) {
      JdbcParameter jdbcParam = statement.getParameter(i);
      parameters.add(jdbcParam.getValue());
    }

    String dbType = statement.getConnectionProxy().getDirectDataSource().getDbType();
    String formattedSql = SQLUtils.format(sql, dbType, parameters);
    statementLog("{conn-" + statement.getConnectionProxy().getId() + ", " + stmtId(statement) + "} executed. \n" + formattedSql);
  }

  public void resultSet_close(FilterChain chain, ResultSetProxy resultSet)
    throws SQLException
  {
    chain.resultSet_close(resultSet);

    StringBuffer buf = new StringBuffer();
    buf.append("{conn-");
    buf.append(resultSet.getStatementProxy().getConnectionProxy().getId());
    buf.append(", ");
    buf.append(stmtId(resultSet));
    buf.append(", rs-");
    buf.append(resultSet.getId());
    buf.append("} closed");

    if (isResultSetCloseAfterLogEnabled())
      resultSetLog(buf.toString());
  }

  public boolean resultSet_next(FilterChain chain, ResultSetProxy resultSet)
    throws SQLException
  {
    boolean moreRows = super.resultSet_next(chain, resultSet);

    if ((moreRows) && 
      (this.resultSetNextAfterLogEnable) && (isResultSetLogEnabled())) {
      try {
        StringBuffer buf = new StringBuffer();
        buf.append("{conn-");
        buf.append(resultSet.getStatementProxy().getConnectionProxy().getId());
        buf.append(", ");
        buf.append(stmtId(resultSet));
        buf.append(", rs-");
        buf.append(resultSet.getId());
        buf.append("}");
        buf.append(" Result: [");

        ResultSetMetaData meta = resultSet.getMetaData();
        int i = 0; for (int size = meta.getColumnCount(); i < size; i++) {
          if (i != 0) {
            buf.append(", ");
          }
          int columnIndex = i + 1;
          int type = meta.getColumnType(columnIndex);
          Object value;
          
          if (type == 93) {
            value = resultSet.getTimestamp(columnIndex);
          }
          else
          {
            if (type == 2004) {
              value = "<BLOB>";
            }
            else
            {
              if (type == 2005) {
                value = "<CLOB>";
              }
              else
              {
                if (type == 2011) {
                  value = "<NCLOB>";
                }
                else
                {
                  if (type == -2)
                    value = "<BINARY>";
                  else
                    value = resultSet.getObject(columnIndex); 
                }
              }
            }
          }
          buf.append(value);
        }

        buf.append("]");

        resultSetLog(buf.toString());
      } catch (SQLException ex) {
        resultSetLogError("logging error", ex);
      }

    }

    return moreRows;
  }

  public Object callableStatement_getObject(FilterChain chain, CallableStatementProxy statement, int parameterIndex)
    throws SQLException
  {
    Object obj = chain.callableStatement_getObject(statement, parameterIndex);

    if ((obj instanceof ResultSetProxy)) {
      resultSetOpenAfter((ResultSetProxy)obj);
    }

    return obj;
  }

  public Object callableStatement_getObject(FilterChain chain, CallableStatementProxy statement, int parameterIndex, Map<String, Class<?>> map)
    throws SQLException
  {
    Object obj = chain.callableStatement_getObject(statement, parameterIndex, map);

    if ((obj instanceof ResultSetProxy)) {
      resultSetOpenAfter((ResultSetProxy)obj);
    }

    return obj;
  }

  public Object callableStatement_getObject(FilterChain chain, CallableStatementProxy statement, String parameterName)
    throws SQLException
  {
    Object obj = chain.callableStatement_getObject(statement, parameterName);

    if ((obj instanceof ResultSetProxy)) {
      resultSetOpenAfter((ResultSetProxy)obj);
    }

    return obj;
  }

  public Object callableStatement_getObject(FilterChain chain, CallableStatementProxy statement, String parameterName, Map<String, Class<?>> map)
    throws SQLException
  {
    Object obj = chain.callableStatement_getObject(statement, parameterName, map);

    if ((obj instanceof ResultSetProxy)) {
      resultSetOpenAfter((ResultSetProxy)obj);
    }

    return obj;
  }

  protected void resultSetOpenAfter(ResultSetProxy resultSet)
  {
    if ((this.resultSetOpenAfterLogEnable) && (isResultSetLogEnabled()))
      try {
        StringBuffer buf = new StringBuffer();
        buf.append("{conn-");
        buf.append(resultSet.getStatementProxy().getConnectionProxy().getId());
        buf.append(", ");
        buf.append(stmtId(resultSet));
        buf.append(", rs-");
        buf.append(resultSet.getId());
        buf.append("}");

        String resultId = buf.toString();
        resultSetLog(resultId + " open");

        buf.append(" Header: [");

        ResultSetMetaData meta = resultSet.getMetaData();
        int i = 0; for (int size = meta.getColumnCount(); i < size; i++) {
          if (i != 0) {
            buf.append(", ");
          }
          buf.append(meta.getColumnName(i + 1));
        }
        buf.append("]");

        resultSetLog(buf.toString());
      } catch (SQLException ex) {
        resultSetLogError("logging error", ex);
      }
  }

  protected void statementCreateAfter(StatementProxy statement)
  {
    if ((this.statementCreateAfterLogEnable) && (isStatementLogEnabled()))
      statementLog("{conn-" + statement.getConnectionProxy().getId() + ", stmt-" + statement.getId() + "} created");
  }

  protected void statementPrepareAfter(PreparedStatementProxy statement)
  {
    if ((this.statementPrepareAfterLogEnable) && (isStatementLogEnabled()))
      statementLog("{conn-" + statement.getConnectionProxy().getId() + ", pstmt-" + statement.getId() + "} created. \n" + statement.getSql());
  }

  protected void statementPrepareCallAfter(CallableStatementProxy statement)
  {
    if ((this.statementPrepareCallAfterLogEnable) && (isStatementLogEnabled()))
      statementLog("{conn-" + statement.getConnectionProxy().getId() + ", cstmt-" + statement.getId() + "} created. \n" + statement.getSql());
  }

  protected void statement_executeErrorAfter(StatementProxy statement, String sql, Throwable error)
  {
    if (isStatementLogErrorEnabled())
      statementLogError("{conn-" + statement.getConnectionProxy().getId() + ", " + stmtId(statement) + "} execute error. \n" + sql, error);
  }

  private String stmtId(ResultSetProxy resultSet)
  {
    return stmtId(resultSet.getStatementProxy());
  }

  private String stmtId(StatementProxy statement) {
    StringBuffer buf = new StringBuffer();
    if ((statement instanceof CallableStatementProxy))
      buf.append("cstmt-");
    else if ((statement instanceof PreparedStatementProxy))
      buf.append("pstmt-");
    else {
      buf.append("stmt-");
    }
    buf.append(statement.getId());

    return buf.toString();
  }

  protected void logParameter(PreparedStatementProxy statement) {
    if (isStatementParameterSetLogEnabled())
    {
      StringBuffer buf = new StringBuffer();
      buf.append("{conn-");
      buf.append(statement.getConnectionProxy().getId());
      buf.append(", ");
      buf.append(stmtId(statement));
      buf.append("}");
      buf.append(" Parameters : [");

      int i = 0; for (int parametersSize = statement.getParametersSize(); i < parametersSize; i++) {
        JdbcParameter parameter = statement.getParameter(i);
        if (i != 0) {
          buf.append(", ");
        }
        if (parameter == null)
        {
          continue;
        }
        int sqlType = parameter.getSqlType();
        Object value = parameter.getValue();
        switch (sqlType) {
        case 0:
          buf.append("NULL");
          break;
        default:
          buf.append(String.valueOf(value));
        }
      }

      buf.append("]");
      statementLog(buf.toString());

      buf = new StringBuffer();
      buf.append("{conn-");
      buf.append(statement.getConnectionProxy().getId());
      buf.append(", ");
      buf.append(stmtId(statement));
      buf.append("}");
      buf.append(" Types : [");
      i = 0; for (int parametersSize = statement.getParametersSize(); i < parametersSize; i++) {
        JdbcParameter parameter = statement.getParameter(i);
        if (i != 0) {
          buf.append(", ");
        }
        if (parameter == null) {
          continue;
        }
        int sqlType = parameter.getSqlType();
        buf.append(JdbcUtils.getTypeName(sqlType));
      }
      buf.append("]");
      statementLog(buf.toString());
    }
  }

  public void dataSource_releaseConnection(FilterChain chain, DruidPooledConnection conn)
    throws SQLException
  {
    long connectionId = -1L;

    if (conn.getConnectionHolder() != null) {
      ConnectionProxy connection = (ConnectionProxy)conn.getConnectionHolder().getConnection();
      connectionId = connection.getId();
    }

    chain.dataSource_recycle(conn);

    if ((this.connectionCloseAfterLogEnable) && (isConnectionLogEnabled()))
      connectionLog("{conn-" + connectionId + "} pool-recycle \n");
  }

  public DruidPooledConnection dataSource_getConnection(FilterChain chain, DruidDataSource dataSource, long maxWaitMillis)
    throws SQLException
  {
    DruidPooledConnection conn = chain.dataSource_connect(dataSource, maxWaitMillis);

    ConnectionProxy connection = (ConnectionProxy)conn.getConnectionHolder().getConnection();

    if ((this.connectionConnectAfterLogEnable) && (isConnectionLogEnabled())) {
      connectionLog("{conn-" + connection.getId() + "} pool-connect");
    }

    return conn;
  }

  public void preparedStatement_clearParameters(FilterChain chain, PreparedStatementProxy statement)
    throws SQLException
  {
    if (isStatementParameterClearLogEnable()) {
      statementLog("{conn-" + statement.getConnectionProxy().getId() + ", pstmt-" + statement.getId() + "} clearParameters. ");
    }

    chain.preparedStatement_clearParameters(statement);
  }

  public boolean isWrapperFor(Class<?> iface)
  {
    return (iface == getClass()) || (iface == LogFilter.class);
  }

  public <T> T unwrap(Class<T> iface)
  {
    if ((iface == getClass()) || (iface == LogFilter.class)) {
      return (T)this;
    }
    return null;
  }
}