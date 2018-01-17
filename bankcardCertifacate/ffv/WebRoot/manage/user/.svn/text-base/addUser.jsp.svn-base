<%@ page language="java" pageEncoding="utf-8"%>
<%@ taglib uri="/WEB-INF/sys.tld" prefix="sys" %><%@ taglib uri="/WEB-INF/c.tld" prefix="c" %><%@ taglib uri="/WEB-INF/fmt.tld" prefix="fmt" %><%@ taglib uri="/WEB-INF/fn.tld" prefix="fn" %> 
<!DOCTYPE HTML>
<HTML>
	<HEAD>
		<sys:head />
<TITLE>新增用户</TITLE>
<script type="text/javascript">
	$(document).ready(function(){
		$.formValidator.initConfig( {
			formID : "form1",
			onError : function() {
			},
			onSuccess:function(){
			}
		});
$("#userName").formValidator({onshow:"请输入用户名",onfocus:"用户名不能为空",oncorrect:"用户名合法"}).inputValidator({min:1,empty:{leftempty:false,rightempty:false,emptyerror:"用户名两边不能有空符号"},onerror:"用户名不能为空,请确认"}).ajaxValidator({
	    type : "get",
		url : "${ctx}/manage/user-manage!checkUserName.action",
		datatype : "json",
		success : function(data){	
            if( data == "0" )
			{		
                return true;
			}
            else
			{
                return false;
			}
		},	
		error: function(){$.common.alert({text:"服务器没有返回数据，可能服务器忙，请重试"});},
		onerror : "该用户名不可用，请更换用户名",
		onwait : "正在对用户名进行合法性校验，请稍候..."
	});

$("#password1").formValidator({onShow:"请输入密码",onFocus:"密码不能为空",onCorrect:"密码合法"}).inputValidator({min:1,empty:{leftempty:false,rightempty:false,emptyerror:"密码两边不能有空符号"},onerror:"密码不能为空,请确认"});

$("#password2").formValidator({
onshow:"请输入重复密码",
onFocus:"两次密码必须一致哦",
onCorrect:"密码一致"}).inputValidator({
min:1,
empty:{leftempty:false,rightempty:false,emptyerror:"重复密码两边不能有空符号"},
onError:"重复密码不能为空,请确认"}).compareValidator({
desID:"password1",operateor:"=",onError:"2次密码不一致,请确认"});

$("input:radio[name='sex']").formValidator({tipid:"sexTip",onshow:"请选择你的性别",onfocus:"没有第三种性别了，你选一个吧",oncorrect:"输入正确",defaultvalue:[1]}).inputValidator({min:1,max:1,onerror:"性别忘记选了,请确认"});//.defaultPassed();

$("#email").formValidator({
onShow:"请输入邮箱",
onFocus:"邮箱6-100个字符",
onCorrect:"恭喜你,你输对了",
defaultValue:"@"}).inputValidator({
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
      <form name="form1" id="form1" method="post" action="${ctx }/manage/user-manage!save.action" >
       
      <sys:editorlayout cols="3">
      	   <sys:editorlayout-row>
      	   		<sys:textfield label="用户名" name="userName" id="userName" showTip="true"/>
      	   </sys:editorlayout-row>
      	   <sys:editorlayout-row>
      	   		<sys:textfield label="密码" name="userPassword" id="password1" showTip="true"/>
      	   </sys:editorlayout-row>
      	   <sys:editorlayout-row>
      	   		<sys:textfield label="重复密码" id="password2" showTip="true"/>
      	   </sys:editorlayout-row>
      	   <sys:editorlayout-row>
      	   		<sys:textfield label="姓名" name="name" id="name" showTip="true"/>
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
