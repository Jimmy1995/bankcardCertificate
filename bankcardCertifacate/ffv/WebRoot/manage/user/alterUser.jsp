<%@ page language="java" pageEncoding="utf-8"%>
<%@ taglib uri="/WEB-INF/sys.tld" prefix="sys" %><%@ taglib uri="/WEB-INF/c.tld" prefix="c" %><%@ taglib uri="/WEB-INF/fmt.tld" prefix="fmt" %><%@ taglib uri="/WEB-INF/fn.tld" prefix="fn" %> 
<!DOCTYPE HTML>
<HTML>
	<HEAD>
		<sys:head />
<TITLE>修改用户</TITLE>
<STYLE type=text/css> 
{
	FONT-SIZE: 12px
}
#menuTree A {
	COLOR: #566984; TEXT-DECORATION: none
}
.STYLE2 {font-size: x-large}
</STYLE>

<style type="text/css" media="all"> 
body,div{font-size:12px;}
</style> 

<META content="MSHTML 6.00.2900.5848" name=GENERATOR>

<script type="text/javascript">
	$(document).ready(function(){
		$.formValidator.initConfig( {
			formID : "form1",
			onError : function() {
			},
			onSuccess:function(){
			}
		});
$("#password1").formValidator({onshow:"请输入密码",onfocus:"密码不能为空",oncorrect:"密码合法"}).inputValidator({min:1,empty:{leftempty:false,rightempty:false,emptyerror:"密码两边不能有空符号"},onError:"密码不能为空,请确认"});

$("#password2").formValidator({onshow:"请输入重复密码",onfocus:"两次密码必须一致哦",oncorrect:"密码一致"}).inputValidator({min:1,empty:{leftempty:false,rightEmpty:false,emptyError:"重复密码两边不能有空符号"},onError:"重复密码不能为空,请确认"}).compareValidator({desID:"password1",operateor:"=",onError:"2次密码不一致,请确认"});

$("input:radio[name='sex']").formValidator({tipid:"sexTip",onshow:"请选择你的性别",onfocus:"没有第三种性别了，你选一个吧",oncorrect:"输入正确"}).inputValidator({min:1,max:1,onerror:"性别忘记选了,请确认"});//.defaultPassed();

$("#email").formValidator({
onShow:"请输入邮箱",
onFocus:"邮箱6-100个字符",
onCorrect:"恭喜你,你输对了"}).inputValidator({
	min:6,max:100,onError:"你输入的邮箱长度非法,请确认"
	}).regexValidator({regExp:regexEnum.email,onError:"你输入的邮箱格式不正确"});

$("#name").formValidator({onshow:"请输入你的名字",onfocus:"描述至少要输入2个汉字",oncorrect:"恭喜你,你输对了"}).inputValidator({min:2,onerror:"你输入的描述长度不正确,请确认"});	


$("#qq").formValidator({empty:true,onempty:"你真的不想留下QQ号码吗？",onshow:"请输入你的QQ号码",onfocus:"请输入至少5位的QQ号码"}).inputValidator({min:5,max:12,onerror:"你输入的QQ号码长度错误"});	


});	

	
	function submitFun(){
		if(!$.formValidator.pageIsValid("1")){return ;}
		var formobj=$("#form1");
		$.post(formobj.attr("action"),formobj.serialize(),function(json){
			if(json.errorType=='0'){
				$("#button").hide();
				$.common.alert({text:"保存成功!"});
			}else{
				$.common.alert({text:json.message});
			}
		},"json");
	}	
</script>
	

</HEAD>
<BODY>
      <form name="form1" id="form1" method="post" action="${ctx }/manage/user-manage!alter.action">
      	
      	
      	 <input type="hidden" name="obj.userId" value="${obj.userId }"  />
      	 <sys:editorlayout cols="3">
      	   <sys:editorlayout-row>
      	   		<sys:textfield label="用户名" name="obj.userName" id="userName" disabled="true" showTip="true"/>
      	   </sys:editorlayout-row>
      	   <sys:editorlayout-row>
      	   		<sys:textfield label="新密码" name="obj.userPassword" id="password1" showTip="true" value=""/>
      	   </sys:editorlayout-row>
      	   <sys:editorlayout-row>
      	   		<sys:textfield label="重复密码" id="password2" showTip="true" value=""/>
      	   </sys:editorlayout-row>
      	   <sys:editorlayout-row>
      	   		<sys:textfield label="姓名" name="obj.name" id="name" showTip="true"/>
      	   </sys:editorlayout-row>
      	   <sys:editorlayout-row>
      	   		<td colspan="3" align="center">
      	   			<input type="button" onclick="submitFun();" class="btn btn-info" name="button" id="button" value="提交" />
      	   		</td>
      	   </sys:editorlayout-row>
        </sys:editorlayout>
      
      
      </form>
</BODY>
</HTML>
