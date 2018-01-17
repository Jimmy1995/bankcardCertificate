<!DOCTYPE HTML>
<%@ page language="java" pageEncoding="utf-8"%>
<%@ page language="java" import="org.apache.commons.logging.Log"%>
<%@ page language="java" import="org.apache.commons.logging.LogFactory"%>
<%@ taglib uri="/WEB-INF/sys.tld" prefix="sys" %><%@ taglib uri="/WEB-INF/c.tld" prefix="c" %><%@ taglib uri="/WEB-INF/fmt.tld" prefix="fmt" %><%@ taglib uri="/WEB-INF/fn.tld" prefix="fn" %> 
<%
	Log log = LogFactory.getLog("login.jsp");
	if (!session.isNew()) {
		session.invalidate();
		//log.info("session不是最新的，注销掉并重新生成新的session。防止浏览器未关闭同时登录多个账号时出来的session混共享问题");
	}
%>
<HTML xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<sys:head />
		<title>华普代付云系统</title>
		<script type="text/javascript" src="${ctx}/js/jQuery.md5.js"></script>
		<script type="text/javascript">
	$(document).ready(function(){
	//初始化信息
	$.formValidator.initConfig({formid:"form1",alertmessage:true,onerror:function(msg){alert(msg)}});
		//校验脚本
		$("#userName").formValidator().inputValidator({min:1,onerror:"用户名不能为空,请确认"});
		
		$("#password").formValidator().inputValidator({min:1,onerror:"密码不能为空,请确认"});
		
		
		
		
		
		  $(".login-text").focus(function ()
            {
                $(this).addClass("login-text-focus");
            }).blur(function ()
            {
                $(this).removeClass("login-text-focus");
            });
 
	});
	
	function doSubmit(){
		var os=$("#password").val();
		var ns=$.md5(os);
		$("#userPassword").val(ns);
		$("#password").val("");
		var userName=$("#userName").val();
		return true;
	}
	</script>
		<STYLE type=text/css><!--
* {
	PADDING-BOTTOM: 0px;
	MARGIN: 0px;
	PADDING-LEFT: 0px;
	PADDING-RIGHT: 0px;
	PADDING-TOP: 0px
}

BODY {
	TEXT-ALIGN: center;
	BACKGROUND: #4974a4
}

#login {
	MARGIN: 0px auto;
	WIDTH: 740px;
	FONT-SIZE: 12px
}

#loginlogo {
	MARGIN-TOP: 50px;
	WIDTH: 700px;
	BACKGROUND: url(${ctx }/images/login/logo.jpg) no-repeat;
	HEIGHT: 100px;
	OVERFLOW: hidden
}

#loginpanel {
	POSITION: relative;
	WIDTH: 729px;
	HEIGHT: 300px
}

.panel-h {
	Z-INDEX: 3;
	POSITION: absolute;
	WIDTH: 729px;
	BACKGROUND: url(${ctx }/images/login/panel-h.gif) no-repeat;
	HEIGHT: 20px;
	TOP: 0px;
	LEFT: 0px
}

.panel-f {
	Z-INDEX: 3;
	POSITION: absolute;
	WIDTH: 729px;
	BOTTOM: 0px;
	BACKGROUND: url(${ctx }/images/login/panel-f.gif) no-repeat;
	HEIGHT: 13px;
	LEFT: 0px
}

.panel-c {
	Z-INDEX: 2;
	WIDTH: 729px;
	BACKGROUND: url(${ctx }/images/login/panel-c.gif) repeat-y;
	HEIGHT: 300px
}

.panel-c-l {
	POSITION: absolute;
	TOP: 15px;
	LEFT: 60px
}

.panel-c-r {
	POSITION: absolute;
	TEXT-ALIGN: left;
	LINE-HEIGHT: 200%;
	WIDTH: 222px;
	TOP: 50px;
	RIGHT: 20px
}

.panel-c-l H3 {
	MARGIN-BOTTOM: 10px;
	COLOR: #556a85
}

.panel-c-l TD {
	PADDING-BOTTOM: 7px;
	PADDING-LEFT: 7px;
	PADDING-RIGHT: 7px;
	PADDING-TOP: 7px
}

.login-text {
	BORDER-BOTTOM: #e9e9e9 1px solid;
	BORDER-LEFT: #e9e9e9 1px solid;
	BACKGROUND: #f9f9f9;
	HEIGHT: 24px;
	BORDER-TOP: #e9e9e9 1px solid;
	BORDER-RIGHT: #e9e9e9 1px solid;
	LEFT: 24px
}

.login-text-focus {
	BORDER-BOTTOM: #e6bf73 1px solid;
	BORDER-LEFT: #e6bf73 1px solid;
	BORDER-TOP: #e6bf73 1px solid;
	BORDER-RIGHT: #e6bf73 1px solid
}

.login-btn {
	BORDER-BOTTOM: medium none;
	BORDER-LEFT: medium none;
	LINE-HEIGHT: 29px;
	WIDTH: 114px;
	BACKGROUND: url(${ctx }/images/login/login-btn.gif) no-repeat;
	HEIGHT: 29px;
	COLOR: #e9ffff;
	OVERFLOW: hidden;
	BORDER-TOP: medium none;
	CURSOR: pointer;
	BORDER-RIGHT: medium none
}

#txtUsername {
	WIDTH: 191px
}

#txtPassword {
	WIDTH: 191px
}

#logincopyright {
	TEXT-ALIGN: center;
	MARGIN-TOP: 50px;
	COLOR: white
}
--></STYLE>
	<META name=GENERATOR content="MSHTML 8.00.7600.17006"></HEAD>
<BODY 
style="PADDING-BOTTOM: 10px; PADDING-LEFT: 10px; PADDING-RIGHT: 10px; PADDING-TOP: 10px">
 <form action="${ctx }/manage/user-manage!login.action" method="post" name="form1" id="form1" onsubmit="return doSubmit();">
<DIV id=login>
<DIV id=loginlogo></DIV>
<DIV id=loginpanel>
<DIV class=panel-h></DIV>
<DIV class=panel-c>
<DIV class=panel-c-l>
<TABLE cellSpacing=0 cellPadding=0>
  <TBODY>
  <TR>
    <TD colSpan=2 align=left>
      <H3>欢迎使用华普代付云系统</H3></TD></TR>
  <TR>
    <TD align=right><H4>账号：</H4></TD>
    <TD align=left>
    <INPUT id=userName name=userName value='' type=text class="form-control"></TD><td width="50%" height="25" style="color:red">${message}</td></TR>
  <TR>
    <TD align=right><H4>密码：</H4></TD>
    <TD align=left><INPUT id=password  value= ''
      type=password name=password class="form-control">
      <INPUT id=userPassword type=hidden name=userPassword />
      </TD>
  </TR>
  <input type="hidden" name ="userkind" value="0"/>
<TR>
    <TD colSpan=2 align=middle valign="top"><INPUT id='btnLogin' class='btn btn-info' value='登陆' type='submit'/> 
  </TD></TR></TBODY></TABLE></DIV>
<DIV class=panel-c-r>
<P>请从左侧输入登录账号和密码登录</P>
<P>如果登录失败请联系管理员将帐号解禁</P>
<P>遇到系统问题，请联系网络管理员。</P>
</DIV>
</DIV>
<DIV class=panel-f></DIV>
</DIV>
<DIV id=logincopyright> 版权所有 © 湖南华普信息技术有限公司  </DIV></DIV>
</form>
</BODY></HTML>