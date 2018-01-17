<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
<HTML>
	<HEAD>
		<TITLE>聊天窗口</TITLE>
		<META http-equiv=Content-Type content="text/html; charset=utf-8"><meta name="viewport" content="width=device-width, initial-scale=1.0">
		<script type="text/javascript" src="${pageContext.request.contextPath}/dwr/engine.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/dwr/util.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/dwr/interface/JavaChat.js"></script>
					 <%
		   	  if(session.getAttribute("userAdmin")==null){%><script type="text/javascript">
		   		location.href="<%=request.getContextPath()%>/manage/login.jsp"; </script>
		   	<%  }
		   %>
		<script type="text/javascript">
			function init() {//方法来启动该页面的ReverseAjax功能。这时候默认采用的将是comet的方式来完成页面内容的推送。
			    dwr.engine.setActiveReverseAjax(true);
			    JavaChat.init('${userAdmin.id}','${userAdmin.userName}',' ');//当页面开始时就将信息存入在线列表(初始化)
			}
			
		    //发送消息
			function sendMessage(){
				var receiver = dwr.util.getValue('receiver'); // 获得接受者userName
				var content = dwr.util.getValue('content'); // 获得消息内容
				JavaChat.sendMassage('${userAdmin.userName}',receiver,content);
			}
		    
		    //显示消息
			function showMessage(data) {
				if(data==null){
				return;
				}
	            var text = dwr.util.getValue("info");
	            if (!!text) {  
	               dwr.util.setValue("info", text + "\n" + data.sender+" "+data.time+"\n"+data.msg);
	            } else {
	                dwr.util.setValue("info", data.sender+" "+data.time+"\n"+data.msg);
	            }
	        } 
		    
		   //关闭窗口时清除session  
		  window.onbeforeunload=function (){
			 dwr.engine.setAsync(false);//改为同步的
			 JavaChat.closeSession('${userAdmin.userName}','javaChat.jsp');
		  }
	    </script>
	</HEAD>
	<BODY onLoad="init();" style="background-image:url(chat.jpg)">
	<center>
	<div>
	  <div  style="border : 1px solid #06c;width: 578px; padding:0px; margin:0px;background-image:url(chat1.jpg)">
      <textarea rows="20" cols="60" id="info" readonly="readonly" style="border : 1px solid #06c;background-image:url(chat1.jpg)"></textarea>
      <select name="receiver" id="receiver" size="20" style="border : 1px solid #06c;background-image:url(chat1.jpg)">
      	<option >所有人</option>
      </select>
     	 <textarea rows="5" cols="60" id="content" style="border : 1px solid #06c;background-image:url(chat1.jpg);"  onkeypress="dwr.util.onReturn(event, sendMessage)"></textarea>
      <input type="button" value=" 发送 " onClick="sendMessage();" style="height: 85px; width: 66px;"/>
      </div>
      <div class="right"></div>
      </center>
     </div>
	</BODY>
</HTML>
