package com.common.ext.druid;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import com.alibaba.druid.filter.logging.Log4jFilterMBean;
import com.common.web.log.LogHelper;

public class Log4jFilter extends LogFilter
  implements Log4jFilterMBean
{
  private Logger dataSourceLogger = Logger.getLogger(this.dataSourceLoggerName);
  private Logger connectionLogger = Logger.getLogger(this.connectionLoggerName);
  private Logger statementLogger = Logger.getLogger(this.statementLoggerName);
  private Logger resultSetLogger = Logger.getLogger(this.resultSetLoggerName);

  public String getDataSourceLoggerName()
  {
    return this.dataSourceLoggerName;
  }

  public void setDataSourceLoggerName(String dataSourceLoggerName)
  {
    this.dataSourceLoggerName = dataSourceLoggerName;
    this.dataSourceLogger = Logger.getLogger(dataSourceLoggerName);
  }

  public void setDataSourceLogger(Logger dataSourceLogger) {
    this.dataSourceLogger = dataSourceLogger;
    this.dataSourceLoggerName = dataSourceLogger.getName();
  }

  public String getConnectionLoggerName()
  {
    return this.connectionLoggerName;
  }

  public void setConnectionLoggerName(String connectionLoggerName)
  {
    this.connectionLoggerName = connectionLoggerName;
    this.connectionLogger = Logger.getLogger(connectionLoggerName);
  }

  public void setConnectionLogger(Logger connectionLogger) {
    this.connectionLogger = connectionLogger;
    this.connectionLoggerName = connectionLogger.getName();
  }

  public String getStatementLoggerName()
  {
    return this.statementLoggerName;
  }

  public void setStatementLoggerName(String statementLoggerName)
  {
    this.statementLoggerName = statementLoggerName;
    this.statementLogger = Logger.getLogger(statementLoggerName);
  }

  public void setStatementLogger(Logger statementLogger) {
    this.statementLogger = statementLogger;
    this.statementLoggerName = statementLogger.getName();
  }

  public String getResultSetLoggerName()
  {
    return this.resultSetLoggerName;
  }

  public void setResultSetLoggerName(String resultSetLoggerName)
  {
    this.resultSetLoggerName = resultSetLoggerName;
    this.resultSetLogger = Logger.getLogger(resultSetLoggerName);
  }

  public void setResultSetLogger(Logger resultSetLogger) {
    this.resultSetLogger = resultSetLogger;
    this.resultSetLoggerName = resultSetLogger.getName();
  }

  public boolean isConnectionLogErrorEnabled()
  {
    return (this.connectionLogger.isEnabledFor(Level.ERROR)) && (super.isConnectionLogErrorEnabled());
  }

  public boolean isDataSourceLogEnabled()
  {
    return (this.dataSourceLogger.isDebugEnabled()) && (super.isDataSourceLogEnabled());
  }

  public boolean isConnectionLogEnabled()
  {
    return (this.connectionLogger.isDebugEnabled()) && (super.isConnectionLogEnabled());
  }

  public boolean isStatementLogEnabled()
  {
    return (this.statementLogger.isDebugEnabled()) && (super.isStatementLogEnabled());
  }

  public boolean isResultSetLogEnabled()
  {
    return (this.resultSetLogger.isDebugEnabled()) && (super.isResultSetLogEnabled());
  }

  public boolean isResultSetLogErrorEnabled()
  {
    return (this.resultSetLogger.isEnabledFor(Level.ERROR)) && (super.isResultSetLogErrorEnabled());
  }

  public boolean isStatementLogErrorEnabled()
  {
    return (this.statementLogger.isEnabledFor(Level.ERROR)) && (super.isStatementLogErrorEnabled());
  }

  protected void connectionLog(String message)
  {
	  if(LogHelper.getDoLog())
    this.connectionLogger.debug(message);
  }

  protected void statementLog(String message)
  {
	  if(LogHelper.getDoLog())
    this.statementLogger.debug(message);
  }

  protected void resultSetLog(String message)
  {
	  if(LogHelper.getDoLog())
    this.resultSetLogger.debug(message);
  }
  

  protected void resultSetLogError(String message, Throwable error)
  {
    this.resultSetLogger.error(message, error);
  }

  protected void statementLogError(String message, Throwable error)
  {
    this.statementLogger.error(message, error);
  }
}