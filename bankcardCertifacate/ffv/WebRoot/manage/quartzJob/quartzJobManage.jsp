<%@ page language="java" pageEncoding="utf-8"%>
<%@ taglib uri="/WEB-INF/sys.tld" prefix="sys" %><%@ taglib uri="/WEB-INF/c.tld" prefix="c" %><%@ taglib uri="/WEB-INF/fmt.tld" prefix="fmt" %><%@ taglib uri="/WEB-INF/fn.tld" prefix="fn" %> 
<!DOCTYPE HTML>
<HTML>
	<HEAD>
		<sys:head />
		<TITLE>管理</TITLE>
		<script type="text/javascript">
		//	$(function(){//选中全部
		 //   	$("#selectAll").click(function(){
		//	  		$("input[name='jobs']").each(function(){
		//	   			$(this).attr("checked",!this.checked);              
		//	    	});
		//	  	});
		 //   	
		  //  });
			function runAjax(urlStr,id){//异步
				$.ajax({
				  type: "GET",
				  url: urlStr,
				  datatype : "json",
				  success : function(data){
					$.common.alert({text:data.message});  
					query();
				  },	
				  error: function(){$.common.alert({text:"服务器没有返回数据，可能服务器忙，请重试"});}
				});
			}
			
			function del(){//删除
				var urlStr="${ctx }/manage/quartz-job-manage!delete.action";
				var chk_value =getSelect();
				if(chk_value.length==0){
					$.common.alert({text:"你还没有选择任何内容！"});
					return ;
				}
				$.common.confirm({text:"你确定删除吗？",yesCallBack:function(){
					$.ajax({
						  type: "GET",
						  url: urlStr,
						  data:{"jobs":chk_value.toLocaleString()},
						  datatype : "json",
						  success : function(data){
							  if(data.errorType=="0"){
								  $.common.alert({text:"删除成功"});
								  query();
							  }else{
								  $.common.alert({text:data.message});
							  }
						  },	
						  error: function(){$.common.alert({text:"服务器没有返回数据，可能服务器忙，请重试"});}
						});
				}})
			}
			
			function update(){//修改
				var urlStr="${ctx }/manage/quartz-job-manage!toAlter.action";
				var chk_value =getSelect();   
				if(chk_value.length==0){
					$.common.alert({text:"你还没有选择任何内容！"});
					return ;
				}else if(chk_value.length>1){
					$.common.alert({text:"只能选择一条记录！"});
					return ;
				}
				$.common.openDialog({width:"560",height:"230",url:urlStr+"?id="+chk_value[0]});
			}
			function format(data, type, row){
				if(data=="1"){
					return "已停止";
				}else{
					return "已启动";
				}
	        }
			function getSelect(){
				var chk_value =new Array();
				var data=getSelectRowData(quartzform);
				$.each(data,function(i,n){     
	                chk_value.push(n.id);     
	            });
				return chk_value;
			}
			function query(){
				quartzform.draw();
			}
			function start(){
				if(getSelect()&&getSelect().length>0){
					var ids=getSelect();
					var id=ids[0];
					runAjax("${ctx }/manage/quartz-job-manage!start.action?id="+id,""+id+"");	
				}else{
					$.common.alert({text:"请先选择数据"});
				}
				
			}
			function runOnce(){
				if(getSelect()&&getSelect().length>0){
					var ids=getSelect();
					var id=ids[0];
					runAjax("${ctx }/manage/quartz-job-manage!runOnce.action?id="+id,""+id+"");
				}else{
					$.common.alert({text:"请先选择数据"});
				}
			}
			function stop(){
				if(getSelect()&&getSelect().length>0){
					var ids=getSelect();
					var id=ids[0];
					runAjax("${ctx }/manage/quartz-job-manage!stop.action?id="+id,""+id+"");	
				}else{
					$.common.alert({text:"请先选择数据"});
				}
			}
			function add(){
				$.common.openDialog({width:"560",height:"230",url:'${ctx }/manage/quartzJob/addQuartzJob.jsp',callback:function(){query();}});
			}
		</script>
	</HEAD>
	<BODY>
		<form id="listForm" name="listForm">
			<sys:editorlayout cols="3">
				<sys:editorlayout-row>
					<sys:textfield name="jobName" id="jobName" label="任务名"></sys:textfield>
					<td>
						<sys:dropdown hasPrveButton="true" onclick="javascript:query();" value="搜索">
								<sys:dropdownItem onclick="javascript:add();" value="新增"></sys:dropdownItem>
								<sys:dropdownItem onclick="update();" value="修改"></sys:dropdownItem>
								<sys:dropdownItem onclick="javascript:del()" value="删除"></sys:dropdownItem>
								<sys:dropdownItem isDivider="true"></sys:dropdownItem>
								<sys:dropdownItem onclick="javascript:start()" value="启动"></sys:dropdownItem>
								<sys:dropdownItem onclick="javascript:stop()" value="停止"></sys:dropdownItem>
								<sys:dropdownItem onclick="javascript:runOnce()" value="立即执行一次"></sys:dropdownItem>
						</sys:dropdown>
					</td>
				</sys:editorlayout-row>
			</sys:editorlayout>
		</form>			
		<sys:grid action="${ctx }/manage/quartz-job-manage!list.action" formId="listForm" id="quartzform" cssClass="its">
			<sys:grid-column name="jobName" display="任务名"></sys:grid-column>
			<sys:grid-column name="description" display="说明" cssClass="hidden-xs"></sys:grid-column>
			<sys:grid-column name="cronExpression" display="表达式" cssClass="hidden-xs"></sys:grid-column><%--
			<sys:grid-column name="stateFulljobExecuteClass" display="业务类"></sys:grid-column>
			--%><sys:grid-column name="jobStatus" display="状态" fnRender="format"></sys:grid-column>
		</sys:grid>
	</BODY>
</HTML>
