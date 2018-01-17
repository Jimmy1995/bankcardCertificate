<%@ page language="java" pageEncoding="utf-8"%>
<%@ taglib uri="/WEB-INF/sys.tld" prefix="sys" %><%@ taglib uri="/WEB-INF/c.tld" prefix="c" %><%@ taglib uri="/WEB-INF/fmt.tld" prefix="fmt" %><%@ taglib uri="/WEB-INF/fn.tld" prefix="fn" %> 
<!DOCTYPE HTML>
<HTML>
	<HEAD>
		<sys:head />
		<TITLE>数据字典管理</TITLE>
		<script type="text/javascript">
		//	$(function(){//选中全部
		  //  	$("#selectAll").click(function(){
		//	  		$("input[name='pojo.dics']").each(function(){
		//	   			$(this).attr("checked",!this.checked);              
		//	    	});
		//	  	});
		 //   	
		  //  });
			function operationByAjax(urlStr,dataVal){//异步操作
				$.ajax({
				  type: "POST",
				  url: urlStr,
				  data:{"dics":dataVal},
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
				var urlStr="${ctx }/manage/dic-manage!delete.action";
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
				var urlStr="${ctx }/manage/dic-manage!toAlter.action";
				var chk_value =getSelect();
				if(chk_value.length==0){
					$.common.alert({text:"你还没有选择任何内容！"});
					return ;
				}else if(chk_value.length>1){
					$.common.alert({text:"只能选择一条记录！"});
					return ;
				}
				$.common.openDialog({url:urlStr+"?dicId="+chk_value[0],title:"数据字典管理",width:"550",height:"250"});
			}
			
			function addChild(){
				var urlStr="${ctx }/manage/dic-manage!toSave.action";
				var chk_value =getSelect();
				if(chk_value.length==0){
					$.common.alert({text:"你还没有选择任何内容！"});
					return ;
				}else if(chk_value.length>1){
					$.common.alert({text:"只能选择一条记录！"});
					return ;
				}
				$.common.openDialog({url:urlStr+"?parentDic.dicId="+chk_value[0],title:"数据字典管理",width:"550",height:"250"});
			}
			
			function query(){
				dicform.draw();
			}
			function getSelect(){
				var chk_value =new Array();
				var data=getSelectRowData(dicform);
				$.each(data,function(i,n){     
	                chk_value.push(n.dicId);     
	            });
				return chk_value;
			}
			function add(){
				$.common.openDialog({
					width:'550',
					height:'250',
					url:'${ctx }/manage/dic-manage!toSave.action',title:'数据字典管理',
					callback:function(){query();}
				});
			}
		</script>
	</HEAD>
	<BODY>
		<form id="listForm" name="listForm">
			<sys:editorlayout cols="3">
				<sys:editorlayout-row>
					<sys:textfield id="dicKey" name="dicKey" label="名字"></sys:textfield>
					<td>
						<sys:dropdown hasPrveButton="true" onclick="javascript:query();" value="搜索">
								<sys:dropdownItem onclick="javascript:add()" value="增加"></sys:dropdownItem>
								<sys:dropdownItem onclick="javascript:del()" value="删除"></sys:dropdownItem>
								<sys:dropdownItem onclick="update();" value="修改"></sys:dropdownItem>
						</sys:dropdown>
					</td>
				</sys:editorlayout-row>	
			</sys:editorlayout>
		</form>
		
		<sys:grid action="${ctx }/manage/dic-manage!list.action" formId="listForm" id="dicform" cssClass="its">
			<sys:grid-column name="codetype" display="归类" width="20%"></sys:grid-column>
			<sys:grid-column name="dicKey" display="代码" width="20%"></sys:grid-column>
			<sys:grid-column name="dicValue" display="显示值" width="20%"></sys:grid-column>
			<sys:grid-column name="description" display="描述" cssClass="hidden-xs"></sys:grid-column>
			<sys:grid-column name="orders" display="排序" cssClass="hidden-xs"></sys:grid-column>
		</sys:grid>
	</BODY>
</HTML>

