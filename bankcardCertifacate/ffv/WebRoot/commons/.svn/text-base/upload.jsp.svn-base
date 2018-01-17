<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page language="java" import="java.net.URLDecoder"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<% String path =request.getContextPath() ;%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<script type="text/javascript" src="<%=path%>/js/upload/jquery-1.3.2.min.js"></script>
<script type="text/javascript" src="<%=path%>/js/upload/swfobject.js"></script>
<script type="text/javascript" src="<%=path%>/js/upload/jquery.uploadify.v2.1.0.min.js"></script>

<title>附件上传</title>
<script language="JavaScript" type="text/javascript">
jQuery.noConflict(); 
jQuery(function(){	
	var i=0;
	var str=",";
	var val="";
	var html="";
	jQuery("#uploadify").uploadify({
		'uploader'       : '<%=path%>/js/upload/uploadify.swf',
		'script'         : '<%=path%>/manage/file-manage!upload.action',
		'cancelImg'      : '<%=path%>/js/upload/img/cancel.png',
		'scriptData'	 : {'fileName':'test', 'mark':'test'},//上传时自已要带的数据
		'fileDataName'   : 'uploadify',//和input的name属性值保持一致就好，Struts2就能处理了
		'queueID'        : 'fileQueue',
		'queueSizeLimit' : '<% if(null==request.getParameter("fileCount")){%>1<%}else out.print(request.getParameter("fileCount"));%>', //一次最多选择多少个文件上传
		'buttonText'     :'',
		'buttonImg'		 :'<%=path%>/js/upload/img/upload_bt1.jpg',
		'auto'           : false,//是否选取后自动上传
		'multi'          : true,//是否支持多文件上传
		'wmode'          :'transparent',//按钮图片背景透明
		'displayData'    : 'speed',//有speed和percentage两种，一个显示速度，一个显示完成百分比 
        'fileDesc'       : '<% if(null==request.getParameter("fileDesc")){%>支持格式:doc<%}else out.print(request.getParameter("fileDesc"));%>', //如果配置了以下的'fileExt'属性，那么这个属性是必须的 
        'fileExt'        : '<% if(null==request.getParameter("fileExt")){%>*.doc;*.docx;<%}else out.print(request.getParameter("fileExt"));%>',//允许的格式
        'sizeLimit'    : <% if(null==request.getParameter("fileSize")){%>100000<%}else out.print(request.getParameter("fileSize"));%>*1024 ,// 单个文件的最大尺寸（字节为单位）
		'onSelect'		 :function(){
			jQuery('img').show();
		},
		'onComplete':function(event,queueID,fileObj,response,data){
			response=eval("("+response+")");
			if(response.errorType=='0'){//上传成功
				jQuery("#fileInfoId").val(response.data);
				jQuery("*[name='fileInfoId']").val(response.data);
			}else{
				alert("上传出错！"+response.message);
			}
		},
		'onAllComplete':function(event,data){//filesUploaded所有文件数,errors错误数,allBytesLoaded文件总大小,speed速度
			var success=data.filesUploaded-data.errors;
			alert('成功文件：'+success+'\n错误文件：'+data.errors+'\n文件总大小为:'+data.allBytesLoaded/1024+'KB\n上传速度为:'+data.speed/1024+'kb/s');
		}
	});
	jQuery('img').hide();
	var leftt=jQuery('img').offset().left;
	var topt=jQuery('img').offset().top;
	jQuery('#fileQueue').offset({ top:topt , left: leftt });
	
});
</script>
<style type="text/css"> 
.uploadifyQueueItem {
	font: 11px Verdana, Geneva, sans-serif;
	border: 2px solid #E5E5E5;
	background-color: #F5F5F5;
	margin-top: 5px;
	padding: 10px;
	width: 350px;
}
.uploadifyError {
	border: 2px solid #FBCBBC !important;
	background-color: #FDE5DD !important;
}
.uploadifyQueueItem .cancel {
	float: right;
}
.uploadifyProgress {
	background-color: #FFFFFF;
	border-top: 1px solid #808080;
	border-left: 1px solid #808080;
	border-right: 1px solid #C5C5C5;
	border-bottom: 1px solid #C5C5C5;
	margin-top: 10px;
	width: 100%;
}
#uploadifyUploader{
    width: 50px;
    height: 24px;
    background: url('<%=path%>/js/upload/img/upload_bt1.jpg')no-repeat;
}
#fileQueue{
	position:absolute;
	z-index:1000;
}
</style>

</head>

<body>
<table>
<tr>
<td>
	<input type="file" name="uploadify" id="uploadify" />
</td>
<td>	
	<img style="border: 0px;padding: 0px;padding-left: 0px;padding-right: 0px;" src="<%=path%>/js/upload/img/upload_sub_bt.jpg" alt="" height="24" width="50" name="sub" onclick="jQuery('#uploadify').uploadifyUpload()" /></br>
</td>	
</table>	
	<div id="fileQueue"></div>
</body>
</html>