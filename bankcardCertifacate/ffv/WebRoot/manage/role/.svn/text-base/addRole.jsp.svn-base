<%@page import="com.common.frame.enums.RoleType"%>
<%@ page language="java" pageEncoding="utf-8"%>
<%@ taglib uri="/WEB-INF/sys.tld" prefix="sys" %><%@ taglib uri="/WEB-INF/c.tld" prefix="c" %><%@ taglib uri="/WEB-INF/fmt.tld" prefix="fmt" %><%@ taglib uri="/WEB-INF/fn.tld" prefix="fn" %>
<!DOCTYPE HTML>
<HTML>
	<HEAD>
		<sys:head />
<TITLE>新增角色</TITLE>
<script type="text/javascript">
$(document).ready(function(){
		$.formValidator.initConfig( {
			formID : "form1",
			onError : function() {
			},
			onSuccess:function(){
			}
		});

$("#roleName").formValidator({onshow:"请输入角色名",onfocus:"角色名不能为空",oncorrect:"角色名合法"}).inputValidator({min:1,empty:{leftempty:false,rightempty:false,emptyerror:"角色名两边不能有空符号"},onerror:"角色名不能为空,请确认"});
$("#orders").formValidator({
					onShow:"排序",
					onfocus:"排序不能为空",
					oncorrect:"排序名合法"}).inputValidator(
					{type:"number",min:1,empty:{leftempty:false,rightempty:false},
					onerror:"排序不能为空,请确认"});

$("#roleInfo").formValidator({onshow:"请输入角色描述",onfocus:"描述至少要输入2个汉字",oncorrect:"恭喜你,你输对了",defaultvalue:"这家伙很懒，什么都没有留下。"}).inputValidator({min:2,onerror:"你输入的描述长度不正确,请确认"});	


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

      <form name="form1" id="form1" method="post" action="${ctx }/manage/role-manage!save.action" >
        
       	<sys:editorlayout cols="3">
       		<sys:editorlayout-row>
       			<sys:textfield name="roleName" id="roleName" label="角色名" showTip="true"></sys:textfield>
       		</sys:editorlayout-row>
       		<sys:editorlayout-row>
       			<sys:textfield name="orders" id="orders" label="排序" showTip="true"></sys:textfield>
       		</sys:editorlayout-row>
       		<sys:editorlayout-row>
       			<td class="tdLabel" align="right">类型:</td> 
	     	 	<td class="tdInput">
		     	 	<select id="type" name="type"  class="form-control">
		     	 		   <option value="">请选择类型</option>
		     	 		<%for (RoleType s : RoleType.values()) {%>
			     	 	   <option value="<%=s.name()%>">
								<%=s%>
						   </option>
						<%} %>	
		     	 	</select>
	     	 	</td> 
	     		<td ><div id="typeTip" ></div></td> 
       		</sys:editorlayout-row>
       		<sys:editorlayout-row>
       			<sys:textarea name="roleInfo" id="roleInfo" label="描述" showTip="true" />
       		</sys:editorlayout-row>
       		<sys:editorlayout-row>
       			<sys:textarea name="welcomePage" id="welcomePage" label="欢迎界面" colspan="2"/>
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
