<%@ page language="java" pageEncoding="utf-8" %>
<!DOCTYPE HTML>
<html>
<HEAD>
 <base target="_self">
      <a id="reload" href="<%=request.getContextPath() %>/manage/role/mid.jsp" style="display:none"></a>
		<TITLE>为角色设置多个人员</TITLE>
		<META http-equiv=Content-Type content="text/html; charset=utf-8"><meta name="viewport" content="width=device-width, initial-scale=1.0">
<script type="text/javascript">
 //var leftq=window.parent.leftFrame1;//contentWindow
 //var rightq=window.parent.rightFrame1;
 var leftq=self.parent.frames["leftFrame1"];
 var rightq=self.parent.frames["rightFrame1"];
 
 function submitLeft(){
	 leftq.setUsers();
	 isLoad(window.parent.document.getElementById('leftFrame1'),rightq);
 }
 function submitRight(){
	 rightq.setUsers();
	 isLoad(window.parent.document.getElementById('rightFrame1'),leftq);
 }
 
 function isLoad(iframe,ob){
	if (iframe.attachEvent) {//IE
	//if(document.all){
		iframe.attachEvent("onload", function() {
	          //以下操作必须在iframe加载完后才可进行
	          ob.queryPage();
	          //location.reload();
	           reload.click();//reload为A中隐藏a标签的id,当然可以换成其它名称 
		});
	} 
	else {//非IE
		iframe.onload = function() {
	          //以下操作必须在iframe加载完后才可进行
	          ob.queryPage();
	          iframe.onload=null;
		};
 	}
 }
</script>
</HEAD>
<body>
<center>
<div style="vertical-align:middle;   
    display:table-cell;   
   _position:absolute;   
   _top:20%;   
">
	<div><A href="javascript:void(0)" onclick="submitLeft()"><img style="border: 0" src="../../images/left.jpg"/></A></div>
	</br>
	</br>
	</br>
	<div><A href="javascript:void(0)" onclick="submitRight()"><img style="border: 0" src="../../images/right.jpg"/></A></div>
</div>
</center>
</body>
</html>
