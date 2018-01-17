<%@ page language="java" pageEncoding="utf-8"%>
<%@ taglib uri="/WEB-INF/sys.tld" prefix="sys" %><%@ taglib uri="/WEB-INF/c.tld" prefix="c" %><%@ taglib uri="/WEB-INF/fmt.tld" prefix="fmt" %><%@ taglib uri="/WEB-INF/fn.tld" prefix="fn" %> 
<!DOCTYPE HTML>
<HTML>
	<HEAD>
		<sys:head />
<TITLE>新增机构</TITLE>
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
	$.formValidator.initConfig({
		formID:"form1",
		onError:function(){
		},
		onSuccess:function(){
		}
	});
	$("#groupName").formValidator({onshow:"请输入机构名",onfocus:"机构名不能为空",oncorrect:"机构名合法"}).inputValidator({min:1,empty:{leftempty:false,rightempty:false,emptyerror:"机构名两边不能有空符号"},onerror:"机构名不能为空,请确认"});
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

      <form name="form1" id="form1" method="post" action="${ctx }/manage/group-manage!save.action">
         
        <input type="hidden" id="pid" name="pid" value="${param.pid}"/>
        <sys:editorlayout cols="3">
			<sys:editorlayout-row>
				<sys:textfield name="groupName" id="groupName" label="机构名" showTip="true"/>
			</sys:editorlayout-row>  
			<sys:editorlayout-row>
				<sys:textfield name="groupCode" id="groupCode" label="机构代码" showTip="true"/>
			</sys:editorlayout-row>
			<sys:editorlayout-row>
				<sys:textfield name="phone" id="phone" label="机构电话" showTip="true"/>
			</sys:editorlayout-row>
			<sys:editorlayout-row>
				<sys:textfield name="contact" id="contact" label="联系方式" showTip="true"/>
			</sys:editorlayout-row>
			<sys:editorlayout-row>
				<sys:textfield name="address" id="address" label="联系地址" showTip="true"/>
			</sys:editorlayout-row>
			<%--<sys:editorlayout-row>
				<sys:textfield name="zipCode" id="zipCode" label="机构邮编" showTip="true"/>
			</sys:editorlayout-row>
			--%><sys:editorlayout-row>
				<sys:textfield name="type" id="type" label="机构类型" showTip="true"/>
			</sys:editorlayout-row>      
			<sys:editorlayout-row>
				<td colspan="3" align="center">
					<input type="button" class="btn btn-info" onclick="submitFun()" name="button" id="button" value="提交" />
				</td>
			</sys:editorlayout-row>    
        </sys:editorlayout>
        </form>
</BODY>
</HTML>