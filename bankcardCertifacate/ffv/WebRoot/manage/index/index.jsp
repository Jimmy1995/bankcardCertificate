<%@page import="com.common.frame.model.Role"%>
<%@page import="java.util.Set"%>
<%@ page language="java" pageEncoding="utf-8"%>
<%@page import="com.common.frame.model.UserInfo"%>
<%@ taglib uri="/WEB-INF/sys.tld" prefix="sys" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>
<%@ taglib uri="/WEB-INF/fmt.tld" prefix="fmt" %>
<%@ taglib uri="/WEB-INF/fn.tld" prefix="fn" %>
 
<!DOCTYPE HTML>
<HTML>
	<HEAD>
		<sys:head />
	 <%@ include file="../../commons/zTree.jsp"%>
	 <script language="text/javascript" type="text/javascript" src="${ctx}/js/jquery.cookie.js" charset="utf-8"></script>
	 <%
	 	UserInfo user = (UserInfo)session.getAttribute("userAdmin");
	 %>
	 <%if(user==null){%>
	 	<script type="text/javascript">location.href="<%=request.getContextPath()%>/manage/login.jsp"; </script>
	 <%}else if(null!=user.getRoles()&&user.getRoles().size()>0&&((Role)(user.getRoles().toArray())[0]).getWelcomePage()!=null){%><!-- 选择第一个角色中的欢迎界面 -->
	 	<script type="text/javascript">
	 		$(function(){
		 		$("[name='contentFrame']").attr("src",ctx+"<%=((Role)(user.getRoles().toArray())[0]).getWelcomePage() %>");
	 		});	
	 	</script>	
	 <%}%>
<title>华普代付云管理系统</title>
<script type="text/javascript" src="${ctx }/js/slide/jquery.slide.js"></script>
<script type="text/javascript" src="${ctx}/js/contextMenu/src/Plugins/jquery.contextmenu.js?v=ddd"></script><!-- //手动修改了jquery.contextmenu.js，给菜单加上了name -->
<link rel="stylesheet" href="${ctx }/js/contextMenu/css/contextmenu.css?v=123" type="text/css">
<script type="text/javascript">
	var curMenu = null, zTree_Menu = null;var zNodes=new Array();
		var setting = {
			view: {
				showLine: false,
				showIcon: false,
				selectedMulti: false,
				dblClickExpand: false,
				addDiyDom: addDiyDom
			},
			data: {
				simpleData: {
					enable: true
				}
			},
			callback: {
				onNodeCreated: this.onNodeCreated,
				beforeClick: this.beforeClick,
				onRightClick: this.onRightClick
			}
		};
		
		function beforeClick(treeId, node) {
			if (node.isParent) {
				if (node.level === 0) {
					var pNode = curMenu;
					while (pNode && pNode.level !==0) {
						pNode = pNode.getParentNode();
					}
					if (pNode !== node) {
						var a = $("#" + pNode.tId + "_a");
						a.removeClass("cur");
						zTree_Menu.expandNode(pNode, false);
					}
					a = $("#" + node.tId + "_a");
					a.addClass("cur");
 
					var isOpen = false;
					for (var i=0,l=node.children.length; i<l; i++) {
						if(node.children[i].open) {
							isOpen = true;
							break;
						}
					}
					if (isOpen) {
						zTree_Menu.expandNode(node);
						curMenu = node;
					} else {
						zTree_Menu.expandNode(node.children[0].isParent?node.children[0]:node);
						curMenu = node.children[0];
					}
				} else {
					zTree_Menu.expandNode(node);
				}
			}
			//if(node.isParent){
			//	a.siblings("ul").css({height: "300px","overflow-y": "scroll",display: "block"});
			//}
			return !node.isParent;
		}


     $(function(){
    	var $menuObj=$("#menuTreeDiv");
    	$menuObj.height(document.documentElement.clientHeight-$menuObj.offset().top);//初始iframe的高度//初始化菜单栏的高度，总高减170
		$('#navTab a:first').tab('show'); // Select first tab
		/* 用按钮控制图片左右滚动 */
		$("#body .JQ-slide").Slide({
			effect:"scroolLoop",
			autoPlay:false,
			speed:"normal",
			timer:3000,
			steps:1
		});
		
		$("#searchMenu").keydown(function(e){//回车查询菜单
			if(e.keyCode ==13){searchMenu($(this).val());}
		})
		$("#searchMenuButton").click(function(){//点击查询菜单
			searchMenu($("#searchMenu").val());
		})
			<c:forEach items="${userMenuMap}" var="entity" varStatus="s"> 
			   <c:choose>
			    <c:when test="${(entity.value.menuUrl!='')&&(entity.value.menuUrl!=null)}">
			    	zNodes.push({id:"${entity.value.menuId }",pId:"${entity.value.parentId }",name:"${entity.value.menuName }",click:"addTab('${entity.value.menuId}Tab','${entity.value.menuName}','${ctx}${entity.value.menuUrl}')"
			    		<c:if test="${(entity.value.imageUrl!='')&&(entity.value.imageUrl!=null)}">
			    		,icon:"${ctx}${entity.value.imageUrl}"
			    		</c:if>
			    	});
			    </c:when>
			    <c:otherwise>
					zNodes.push({id:"${entity.value.menuId }",pId:"${entity.value.parentId }",name:"${entity.value.menuName }"
					<c:if test="${(entity.value.imageUrl!='')&&(entity.value.imageUrl!=null)}">
			    		,icon:"${ctx}${entity.value.imageUrl}"
			    	</c:if>
					});		    	
			    </c:otherwise>
			  </c:choose>
			</c:forEach>
			var treeObj = $("#treeDemo");
			$.fn.zTree.init(treeObj, setting, zNodes);
			zTree_Menu = $.fn.zTree.getZTreeObj("treeDemo");
			curMenu = zTree_Menu.getNodes()[0].children[0];
			zTree_Menu.selectNode(null);
			var a = $("#" + zTree_Menu.getNodes()[0].tId + "_a");
			a.addClass("cur");
			
			treeObj.hover(function () {
				if (!treeObj.hasClass("showIcon")) {
					treeObj.addClass("showIcon");
				}
			}, function() {
				treeObj.removeClass("showIcon");
			});
			
			
		showFavorite();//显示收藏的菜单	
	 })
	 
	 /**
	  * 根据菜单名称查询并定位
	  * @param {Object} menuName
	  * @return {TypeName} 
	  */
	  function searchMenu(menuName){
		  if(!menuName)return;
			var nodes = zTree_Menu.getNodesByParamFuzzy("name", menuName, null);
			if(nodes.length==0)$.common.alert({text:"没有找到菜单！"});
			$.each(nodes,function(i,node){
				if(i==0)zTree_Menu.selectNode(node);
				zTree_Menu.expandNode(node, true, true, true);
			});
	  }
	  /**
	   * 增加标签页
	   * @param {Object} id
	   * @param {Object} name
	   * @param {Object} url
	   * @memberOf {TypeName} 
	   * @return {TypeName} 
	   */
	  function addTab(id,name,url){
		   hiddenMenu();//如果菜单全屏就隐藏菜单显示标签页面  
		   
		   
    	 $('#navTab li.active').attr("class","");
    	 $("[class='tab-pane active']").attr("class",'tab-pane');
    	 var obj=$("#"+id);
    	 if(obj.attr("id")){
    		 obj.attr("class","active");
    		 $("#"+id+"Tab").attr("class","tab-pane active");
    		 //refreshTab(id);
    		 return;
    	 }
    	 $('#navTab').append("<li id='"+id+"' class='active'><a href=\"#"+id+"Tab\" data-toggle=\"tab\" style='padding-right:0px'><i class=\"glyphicon glyphicon-hand-right\"></i>"+name+"<span class=\"close\" onClick=\"delTab('"+id+"')\" style='line-height: 9px;padding-left: 9px;padding-right:2px;float:none'>&times;</span></a></li>");//增加tab选项卡
    	 //$('.tab-content').append("<div class=\"tab-pane active\" id=\""+id+"Tab\"><div class='l-tab-loading' style='display:block;'></div><iframe name='contentFrame' src=\""+url+"\" frameborder=\"0\" scrolling=\"auto\" frameBorder=0 scrolling=no width=\"100%\" height=\"100%\"></iframe></div>");//增加tab对应的内容页
    	 var contentitem=$("<div class=\"tab-pane active\" id=\""+id+"Tab\"><div class='l-tab-loading' style='display:block;'></div><iframe name='contentFrame' frameborder=\"0\" scrolling=\"auto\" frameBorder=0 scrolling=no width=\"100%\" height=\"100%\"></iframe></div>");
    	 var iframeloading = $("div:first", contentitem);
         var iframe = $("iframe:first", contentitem);
         iframe.attr("src", url).bind('load.tab', function (){
                     iframeloading.hide();
                 });
         $('.tab-content').append(contentitem);
    	 $("#"+id).contextmenu({ width: 110,items: [
                { text: "刷新", icon: ctx+"/images/refresh.png",alias:"referCurrent"},
                { text: "收藏", icon: ctx+"/images/add.gif",alias:"addFavorite"},
                { type: "splitLine"},
				{ text: "关闭标签", icon: ctx+"/images/cross.gif",alias:"closeCurrent"},
                { text: "关闭其它",icon: ctx+"/images/dtree/delete.png",alias:"closeOther"},
                { text: "关闭所有",icon: ctx+"/images/dtree/trash.gif",alias:"closeAll"}
                ],
                onShow: function(){
    		 		var thisId=this.id;
    		 		var f1=$("[name='closeCurrent']");//手动修改了jquery.contextmenu.js，加上了name
    		 	    var f10=$("[name='addFavorite']");
    		 		var f2=$("[name='closeOther']");
    		 		var f3=$("[name='closeAll']");
    		 		var f4=$("[name='referCurrent']");
    		 		if(typeof(fun1)!='undefined'){f1.unbind("click",fun1);}
    		 		if(typeof(fun10)!='undefined'){f10.unbind("click",fun10);}
    		 		if(typeof(fun2)!='undefined'){f2.unbind("click",fun2);}
    		 		if(typeof(fun3)!='undefined'){f3.unbind("click",fun3);}
    		 		if(typeof(fun4)!='undefined'){f4.unbind("click",fun4);}
    		 		f1.one("click",fun1 =function(){delTab(thisId);});
    		 		f10.one("click",fun10 =function(){addFavorite(thisId);});
    		 		f2.one('click',fun2 =function(){closeOther(thisId);});
    		 		f3.one('click',fun3 =function(){closeAll();});
    		 		f4.one('click',fun4 =function(){refreshTab(thisId);});
    		 	}
        });
	 }
     /**
      * 关闭其它标签页
      * @param {Object} id
      */
     function closeOther(id){
    	 $("#home").siblings().not($("#"+id)).remove();
		 $("#homeTab").siblings().not($("#"+id+"Tab")).remove();
		 $("#"+id).attr("class","active");
		 $("#"+id+"Tab").attr("class","tab-pane active");
     }
      /**
       * 收藏菜单
       * @param {Object} id
       */
     function addFavorite(id){
    	 var favorite=$.cookie("favorite");//"id,id1,id2,id3"
    	 if(favorite){
    		 var fs=favorite.split(",");
    		 if(!fs.contains(id)){
    			 favorite+=","+id;
    		 }
    	 }else{
    		 favorite=id;
    	 }
    	 $.cookie("favorite",favorite,{ expires: 365 },{path:"/"});
    	 showFavorite();
     }
      /**
       * 删除收藏
       * @param {Object} id
       */
     function deleteFavorite(id){
    	 var favorite=$.cookie("favorite");//"id,id1,id2,id3"
    	 if(favorite){
    		 if(favorite.split(",").length==1){
    			 favorite=null;
    		 }else{
	    		 favorite=favorite.replace(id+"Tab,",'');
	    		 favorite=favorite.replace(","+id+"Tab",'');
    		 }
    	 }
    	 $.cookie("favorite",favorite,{ expires: 365 },{path:"/"});
    	 showFavorite();
     }
     /**
      *显示收藏的菜单,可清除不能显示的菜单cookies(比如没有权限或已经删除的)
      */
     function showFavorite(){
    	 var favorite=$.cookie("favorite");
    	 var favorite1="";
    	 var fsize=0;//cookie中的收藏条数
    	 var fsize1=0;//实际能显示的条数
    	 if(favorite){var fs=favorite.split(",");fsize=fs.length;}
    	 var favoriteListHtmlObj=$("#favoriteList");
    	 favoriteListHtmlObj.html(null);
    	 $.each(zNodes,function(i,n){
    		 if(favorite){
    			 if(fs.contains(n.id)){
    				 if(i==1){
    					 favorite1=n.id+"Tab";
    				 }else if(i>1){
    					 favorite1+=","+n.id+"Tab";
    				 }
    				 favoriteListHtmlObj.append("<li><a style='width:92%;padding-left: 20px' onclick=\""+n.click+"\">"+n.name+"</a><a class='close' style='width:8%;font-size:large;font-weight:bold;' onclick=\"javascript:deleteFavorite('"+n.id+"')\">&times;</a></li>");
    				 fsize1++;
    			 }
    		 }
    	 });
    	 $("#favoriteSize").html(fsize1);
    	 if(fsize1!=fsize){
    		 $.cookie("favorite",favorite1,{ expires: 365 },{path:"/"});
    	 }
     }  
      /**
       * 关闭所有标签页
       */
     function closeAll(){
    	 $("#home").siblings().remove();
		 $("#homeTab").siblings().remove();
		 $("#home").attr("class","active");
		 $("#homeTab").attr("class","tab-pane active");
     }
      /**
       * 删除标签页
       * @param {Object} id
       */
     function delTab(id){
    	 var tabObj=$("#"+id);
    	 if(tabObj.attr("class")=="active"){
    		 var prevObj=tabObj.prev();
    		 if(prevObj.size()<1){//没有前一个就选择后一个
    			 prevObj=tabObj.next();
    		 }
    		 prevObj.attr("class","active");
    		 $("#"+prevObj.attr("id")+"Tab").attr("class","tab-pane active");
    	 }
		 tabObj.remove();
		 $("#"+id+"Tab").remove();
     }
     /**
      * 刷新标签页
      * @param {Object} id
      */
     function refreshTab(id){
    	 $('#navTab li.active').attr("class","");
    	 $("[class='tab-pane active']").attr("class",'tab-pane');
    	 $("#"+id).attr("class","active");
    	 $("#"+id+"Tab").attr("class","tab-pane active");//.find("[name='contentFrame']")[0].location.reload();
    	 var contentObj=$("#"+id+"Tab [name='contentFrame']");
    	 var src=contentObj.attr("src");
    	 contentObj.attr("src",src);
     }
     
     function addDiyDom(treeId, treeNode) {
			var spaceWidth = 5;
			var switchObj = $("#" + treeNode.tId + "_switch"),
			icoObj = $("#" + treeNode.tId + "_ico");
			switchObj.remove();
			icoObj.before(switchObj);
 
			if (treeNode.level > 0) {
				var spaceStr = "<span style='display: inline-block;width:" + (spaceWidth * treeNode.level)+ "px'></span>";
				switchObj.before(spaceStr);
			}
	 }
     
     /**
      * 呼叫或者隐藏菜单
      * @param {Object} id
      */
     function toggleShow(){
    	 if($("#navMenu").hasClass("hidden-xs")){
    		 $("#navMenu").removeAttr("class","hidden-xs");
    	 	 $("#navMenu").removeAttr("class","hidden-ms");
    	 	 $("#bodypane").css("top","50px");
    	 	 $("#toppane").removeAttr("class");
    	 }else{
    		 $("#navMenu").attr("class","col-lg-2 col-md-2 hidden-sm hidden-xs");
    		 $("#toppane").attr("class","hidden-sm hidden-xs");
    		 $("#bodypane").css("top","0px");
    	 }
     }
     function hiddenMenu(){
    	 if(!$("#navMenu").hasClass("hidden-xs")){//如果是菜单全屏模式
    		 $("#navMenu").attr("class","col-lg-2 col-md-2 hidden-sm hidden-xs");
    		 $("#toppane").attr("class","hidden-sm hidden-xs");
    		 $("#bodypane").css("top","0px");
    	 }
     } 
  </script>
  <style type="text/css">
		.tab-pane{height:100%;}
		<%--左侧菜单样式--%>
		.ztree{padding: 0px 0px;border: 0px 0px;}
		.ztree * {font-size: 10pt;font-family:"Microsoft Yahei",Verdana,Simsun,"Segoe UI Web Light","Segoe UI Light","Segoe UI Web Regular","Segoe UI","Segoe UI Symbol","Helvetica Neue",Arial}
		.ztree li ul{ margin:0; padding:0;border-left: #e5e5e5 1px solid;border-bottom: #e5e5e5 1px solid;}
		.ztree li {line-height:38px;}
		.ztree li a {width:100%;height:38px;padding-top: 1px;border-bottom:#e5e5e5 1px solid;border-left:#e5e5e5 1px solid }
		.ztree li a:hover {text-decoration:none; background-color: #E7E7E7;}
		<%--.ztree li a span.button.switch {visibility:hidden}--%>
		.ztree.showIcon li a span.button.switch {visibility:visible}
		.ztree li a.curSelectedNode {background-color:#E7E7E7;border:0;height:38px;}
		.ztree li span {line-height:38px;}
		.ztree li span.button {margin-top: -7px;}
		.ztree li span.button.switch {width: 16px;height: 16px;}
		.ztree li a.level0 span {font-size: 100%;font-weight: bold;}
		.ztree li span.button {background-image:url("<%=request.getContextPath()%>/images/left_menuForOutLook.png"); *background-image:url("<%=request.getContextPath()%>/images/left_menuForOutLook.gif")}
		.ztree li span.button.switch.level0 {width: 20px; height:20px}
		.ztree li span.button.switch.level1 {width: 20px; height:20px}
		.ztree li span.button.noline_open {background-position: 0 0;}
		.ztree li span.button.noline_close {background-position: -18px 0;}
		.ztree li span.button.noline_open.level0 {background-position: 0 -18px;}
		.ztree li span.button.noline_close.level0 {background-position: -18px -18px;}
	
		<%--选项卡样式--%>
		#navTab li A{border-left: #e5e5e5 1px solid;border-right: #e5e5e5 1px solid;border-top: #e5e5e5 1px solid;}
 	</style>
</head>
<body>
	    <div id="toppane" style="BACKGROUND-COLOR: #438EB9; height:50px; overflow:hidden; z-index:1">
	    	<h7>欢迎使用华普代付云系统<h7/>
	    	<div style="background-color: #B74635;float: right;width: 5%;border-left:#e5e5e5 1px solid;height: 100%;">
	    		<A href="${ctx}/manage/user-manage!loginOut.action">
				          		<img id="loginoutimg" border="0" src="${ctx }/images/login/loginout.gif" />
						    </A>
						    
	    	</div>
	    	<div style="background-color: #892E65;float: right;width: 5%;border-left:#e5e5e5 1px solid;height: 100%">锁定系统</div>
	    	<div style="background-color: #62A8D1;float: right;width: 10%;border-left:#e5e5e5 1px solid;height: 100%">欢迎,<%=((UserInfo)session.getAttribute("userAdmin")).getName()%></div>
	    	<div style="background-color: #2E8965;float: right;width: 5%;border-left:#e5e5e5 1px solid;height: 100%">消息</div>
	    </div>
		<div id="bodypane" class="container" style="bottom:0px; right:0; position:fixed; left:0; top:50px; z-index:0;width: 100%">
		 <div class="row" style="HEIGHT:100%;">
            <div id="navMenu" class="col-lg-2 col-md-2 hidden-sm hidden-xs" style="padding: 0 0px; height: 100%">
            	
            	
            	<div class="panel panel-success" style="HEIGHT:100%;">
				  <div class="panel-heading" style="padding: 2px 2px">
				    <div class="panel-title">
				    <div class="input-group">
					  <input type="text" class="form-control" placeholder="菜单名--查询" id="searchMenu">
					  <span id="searchMenuButton" class="input-group-addon"><i style='cursor:pointer' class="glyphicon glyphicon-search"></i></span>
					</div>
				    </div>
				  </div>
				  <div class="panel-body" style="HEIGHT:100%;padding: 0px 0px;margin: 0px 0px;">
							    
							  
            				
            				<div class="panel-group" id="accordion">
								  <div class="panel panel-success">
								    <div class="panel-heading">
										<h4 class="panel-title">
									        <a data-toggle="collapse" data-toggle="collapse" data-parent="#accordion" href="#collapseOne">
									          	<b>收藏夹</b>
									          	<span class="badge" id="favoriteSize">0</span>
									        </a>
									     </h4>
								    </div>
								    <div id="collapseOne" class="panel-collapse collapse" style="margin-top: 0px;">
								      <div class="panel-body" style="padding: 0px 0px;border: 0px 0px">
								      	<div class="ztree">
										  <ul id="favoriteList">
										  	
										  </ul>
										</div> 
								      </div>
								    </div>
								  </div>
								  <div class="panel panel-success" style="margin-top: 0px;">
								    <div class="panel-heading">
										<h4 class="panel-title">
									        <a data-toggle="collapse" data-toggle="collapse" data-parent="#accordion" href="#collapseTwo" id="menu">
									          	<b>系统菜单</b>
									          	<span class="badge">${fn:length(userMenuMap)}</span>
									        </a>
									     </h4>
								    </div>
								    <div id="collapseTwo" class="panel-collapse collapse in">
								      <div class="panel-body" id="menuTreeDiv" style="padding: 0px 0px;border: 0px 0px;overflow: auto">
										<ul id="treeDemo" class="ztree"></ul>
								      </div>
								    </div>
								  </div>
						    </div>
						    
						    
					</div>
				</div>
				
				
			</div>
            <div id="body" class="col-lg-10 col-md-10 col-ms-12 col-xs-12" style="padding: 0 0px;HEIGHT:100%;">
				<div class="JQ-slide"> 
					 <div class="JQ-slide-nav" style="float:left;width:1%;line-height: 30px">
							 <a class="next" href="javascript:void(0);">
							 	<i title="呼叫菜单" class="glyphicon glyphicon-th-large hidden-lg hidden-md" style="z-index:9999;font-size: large;" onclick="toggleShow();"></i>
							 	<i class="hidden-xs hidden-ms glyphicon glyphicon-chevron-left"></i>
							 </a>
					 </div>
						  <!-- Nav tabs -->
					 <div  style="float:left;width:98%;overflow: hidden;height:38px">
							<ul class="nav nav-tabs JQ-slide-content"  id="navTab">
							  <li id="home"><a href="#homeTab" data-toggle="tab"><i class="glyphicon glyphicon-home"></i>主页</a></li>
							</ul>
					  </div>
					  <div class="JQ-slide-nav" style="float:left;width:1%;">
							<a class="prev" href="javascript:void(0);">
								<i class="glyphicon glyphicon-chevron-right" style="z-index: 999;float: right"></i>
							</a>
					  </div>
				 </div>
				  		 <!-- Tab panes -->
				  <div class="tab-content" style="overflow:visible;height:100%;padding-top: 36px">
					  <div class="tab-pane active" id="homeTab">
						<iframe name="contentFrame" src="" frameborder="0" scrolling="auto" frameBorder=0 scrolling=no width="100%" height="100%"></iframe>
					  </div>
				  </div>
			</div>
          </div>
         </div>
     </html>