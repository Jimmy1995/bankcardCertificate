<%@ page language="java" pageEncoding="utf-8"%>
<%@ taglib uri="/WEB-INF/sys.tld" prefix="sys" %><%@ taglib uri="/WEB-INF/c.tld" prefix="c" %><%@ taglib uri="/WEB-INF/fmt.tld" prefix="fmt" %><%@ taglib uri="/WEB-INF/fn.tld" prefix="fn" %> 
<!DOCTYPE HTML>
<HTML>
	<HEAD>
		<sys:head />
		<TITLE>机构部门管理</TITLE>
		
		<%@ include file="../../commons/zTree.jsp"%>
		<link href="${ctx }/images/login/main.css" rel="stylesheet" type="text/css" />
		<script type="text/javascript" src="${ctx}/js/contextMenu/src/Plugins/jquery.contextmenu.js"></script><!-- //手动修改了jquery.contextmenu.js，给菜单加上了name -->
		<link rel="stylesheet" href="${ctx }/js/contextMenu/css/contextmenu.css" type="text/css">
		<script type="text/javascript">
		function doSomeThing(event, treeId, treeNode){
			
		}
		function onNodeCreated(event, treeId, treeNode){
			$("li[id='"+treeNode.tId+"']").contextmenu({ width: 90,items: [
				{ text: "添加", icon: "${ctx }/images/dtree/add.png",alias:"add"},
				{ type: "splitLine" },
                { text: "修改",icon: "${ctx }/images/dtree/edit.gif",alias:"edit"},
                { text: "删除",icon: "${ctx }/images/dtree/delete.png",alias:"delete"}
                ],
                onShow: function(){
    		 		var thisId=this.id;
    		 		var f1=$("[name='add']");//手动修改了jquery.contextmenu.js，加上了name
    		 		var f2=$("[name='edit']");
    		 		var f3=$("[name='delete']");
    		 		if(typeof(fun1)!='undefined'){f1.unbind("click",fun1);}
    		 		if(typeof(fun2)!='undefined'){f2.unbind("click",fun2);}
    		 		if(typeof(fun3)!='undefined'){f3.unbind("click",fun3);}
    		 		f1.one("click",fun1 =function(){
    		 			$.common.openDialog({url:"${ctx}/manage/group/addGroup.jsp?pid="+treeNode.id,title:"增加机构",height:"255"});
    		 		});
    		 		f2.one('click',fun2 =function(){
    		 			$.common.openDialog({url:"${ctx}/manage/group-manage!toAlter.action?groupId="+treeNode.id,title:"修改机构",height:"255"});
    		 		});
    		 		f3.one('click',fun3 =function(){
    		 			$.common.confirm({text:"你确定删除吗？",yesCallBack:function(){
							deleteTree(treeNode.id);
						}})
    		 		});
    		 	}
        	});
		}
		</script>
		<script type='text/javascript' src='${ctx}/js/createTree.js'></script>
		<SCRIPT type="text/javascript">
			var curMenuId="0";
			function queryTree(){
				var zNodes=new Array();
				$.ajax({
					  type: "post",
					  url: "${ctx}/manage/group-manage!queryAllGroup.action",
					  cache: false,
					  dataType: "text",
					  success: function(json){
					   // zNodes.push({id:"0",pId:"",name:"机构管理",url:"${ctx}/manage/user-manage!listUserByGroup.action?groupid=0",target:"right"});
						eval(json);
						$.fn.zTree.init($("#treeDemo"), setting, zNodes);
						selectMenu(curMenuId,$.fn.zTree.getZTreeObj("treeDemo"));
					  }
				});
			}
			function deleteTree(id){
				$.ajax({
					  type: "post",
					  url: "${ctx}/manage/group-manage!delete.action",
					  cache: false,
					  data: "groupId="+id,
					  dataType: "json",
					  success: function(json){
						  var zTree_Menu1 = $.fn.zTree.getZTreeObj("treeDemo");
						  curMenuId=zTree_Menu1.getNodeByParam("id", id, null).getParentNode().id;
						  queryTree();//刷新机构列表
					  }
				});
			}
			queryTree();
	    </SCRIPT>
	</HEAD>
	<BODY style="height: 520px">
	<table width="100%" height="100%" border="0" cellpadding="0" cellspacing="0">
	<tr valign="top">
	<td width="10%" class="hidden-xs">
				<div class="zTreeDemoBackground left">
					<ul id="treeDemo" class="ztree"></ul>
				</div>
	</td>
	<td width="90%" height="100%">
			<iframe name="right" src="${ctx}/manage/user-manage!listUserByGroup.action?groupid=0" scrolling="no" width="100%" height="100%" frameborder="0">
			</iframe>
	</td>
	</tr>
	</table>
    </BODY>
</HTML>