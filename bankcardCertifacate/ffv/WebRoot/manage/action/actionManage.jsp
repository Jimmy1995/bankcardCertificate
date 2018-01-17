<%@ page language="java" pageEncoding="utf-8"%>
<%@ taglib uri="/WEB-INF/sys.tld" prefix="sys" %><%@ taglib uri="/WEB-INF/c.tld" prefix="c" %><%@ taglib uri="/WEB-INF/fmt.tld" prefix="fmt" %><%@ taglib uri="/WEB-INF/fn.tld" prefix="fn" %> 
<!DOCTYPE HTML>
<HTML>
	<HEAD>
		<sys:head />
		<TITLE>权限管理</TITLE>
		<script type="text/javascript">
			
		//	$(function(){//选中全部
		  //  	$("#selectAll").click(function(){
		//	  		$("input[name='pojo.actions']").each(function(){
		//	   			$(this).attr("checked",!this.checked);              
		//	    	});
		//	  	});
		 //   	
		  //  });
			function operationByAjax(urlStr,dataVal){//异步操作
				$.ajax({
				  type: "POST",
				  url: urlStr,
				  data:{"actionids":dataVal},
				  datatype : "json",
				  success : function(data){	
		            if( data == "1" ){		
		                $.common.alert({text:"操作成功！"});
		                window.setTimeout('query()',2000);
					}
		            else{
						$.common.alert({text:"操作失败！"});
					}
				  },	
				  error: function(){$.common.alert({text:"服务器没有返回数据，可能服务器忙，请重试"});}
				});
			}
			function del(){//删除
				var urlStr="${ctx }/manage/action-manage!delete.action";
				var chk_value =getSelect();
				if(chk_value.length==0){
					$.common.alert({text:"你还没有选择任何内容！"});
					return ;
				}
				$.common.confirm({text:"你确定删除吗？",yesCallBack:function(){
					operationByAjax(urlStr,chk_value.toLocaleString());
				}})
			}
			
			function update(){//修改
				var urlStr="${ctx }/manage/action-manage!toAlter.action";
				var chk_value =getSelect();   
				if(chk_value.length==0){
					$.common.alert({text:"你还没有选择任何内容！"});
					return ;
				}else if(chk_value.length>1){
					$.common.alert({text:"只能选择一条记录！"});
					return ;
				}
				$.common.openDialog({width:"560",height:"230",url:urlStr+"?actionId="+chk_value[0],title:"权限管理"});
			}
			function query(){
				actionform.draw();
			}
			function getSelect(){
				var chk_value =new Array();
				var data=getSelectRowData(actionform);
				$.each(data,function(i,n){     
	                chk_value.push(n.actionId);     
	            });
				return chk_value;
			}
			function add(){
				$.common.openDialog({
					width:'560',
					height:'230',
					url:'${ctx }/manage/action-manage!toSave.action?menuId=${menuId}',
					callback:function(){query();}
				});
			}
		</script>
	</HEAD>
	<BODY>
		<form id="listForm" name="listForm">
			<sys:editorlayout cols="4">
				<sys:editorlayout-row>
					<sys:textfield name="actionName" id="actionName" label="名称"></sys:textfield>
					<sys:select list="#request.menuList" name="menu.menuId" headerKey="" headerValue="选择模块" listKey="menuId" listValue="menuName"></sys:select>
					<td>
						<sys:dropdown hasPrveButton="true" onclick="javascript:query();" value="搜索">
							<sys:dropdownItem onclick="javascript:add();" value="增加"></sys:dropdownItem>
							<sys:dropdownItem onclick="update();" value="修改"></sys:dropdownItem>
							<sys:dropdownItem isDivider="true"></sys:dropdownItem>
							<sys:dropdownItem onclick="javascript:del()" value="删除"></sys:dropdownItem>
						</sys:dropdown>
					</td>
				</sys:editorlayout-row>
			</sys:editorlayout>
		</form>
		
		<sys:grid action="${ctx }/manage/action-manage!list.action" mutipleSelect="true" formId="listForm" id="actionform" cssClass="its">
			<sys:grid-column name="actionName" display="权限名"></sys:grid-column>
			<sys:grid-column name="actionPath" display="说明" cssClass="hidden-xs"></sys:grid-column>
		</sys:grid>
	</BODY>
</HTML>
