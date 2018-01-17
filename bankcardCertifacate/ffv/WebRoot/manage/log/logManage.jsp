<%@ page language="java" pageEncoding="utf-8"%>
<%@ taglib uri="/WEB-INF/sys.tld" prefix="sys" %><%@ taglib uri="/WEB-INF/c.tld" prefix="c" %><%@ taglib uri="/WEB-INF/fmt.tld" prefix="fmt" %><%@ taglib uri="/WEB-INF/fn.tld" prefix="fn" %> 
<!DOCTYPE HTML>
<HTML>
	<HEAD>
		<sys:head />
<TITLE>系统操作日志</TITLE>
</HEAD>
<BODY>
	<form id="logForm" >
		<sys:editorlayout cols="5" >
			<sys:editorlayout-row>
				<sys:textfield label="账号" name='userName'/>
				<sys:textfield label="信息" name='message'/>
				<td><sys:btn id="search" value="查询"></sys:btn></td>
			</sys:editorlayout-row>
		</sys:editorlayout>
	</form>
	<sys:grid id="logs" formId="logForm" cssClass="its" action="${ctx}/manage/log-manage!list.action" width="100%">
		<sys:grid-column name='logLevel' display="级别" />
		<sys:grid-column name="" display="账号信息"  cssClass="hidden-xs">
			<sys:grid-column name='userName' display="账号" />
			<sys:grid-column name='name' display="名" orderable="false"/>
			<sys:grid-column name='ip' display="ip"/>
		</sys:grid-column>
		<sys:grid-column name='logDate' display="日期" cssClass="hidden-xs" order="desc"/>
		<sys:grid-column name='' display="操作" orderable="false"><a onclick="view(this)" style="cursor: pointer;">详细</a></sys:grid-column>
	</sys:grid>
	<script type="text/javascript">	
        function format(data, type, row){
        	return data.substring(0,10);
        }
        $(function(){
        	$("#search").click(function(){
        		logs.draw();
        	});
        });
        function view(obj) {
            var data = logs.row($(obj).parents('tr')).data();
            $.common.openDialog({content:"<xmp>"+data.message+"</xmp>"});
            //$.common.mergeCell("logs",1,2);
            //$.common.mergeCell("logs",0,2);
        }
    </script>
	</BODY>
</HTML>
