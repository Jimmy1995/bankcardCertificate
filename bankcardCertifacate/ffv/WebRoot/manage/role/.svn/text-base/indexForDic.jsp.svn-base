<%@ page language="java" pageEncoding="utf-8"%>
<%@ taglib uri="/WEB-INF/sys.tld" prefix="sys" %><%@ taglib uri="/WEB-INF/c.tld" prefix="c" %><%@ taglib uri="/WEB-INF/fmt.tld" prefix="fmt" %><%@ taglib uri="/WEB-INF/fn.tld" prefix="fn" %> 
<!DOCTYPE HTML>
<HTML>
	<HEAD>
		<sys:head />
	</head>
	<frameset rows="*" cols="*,40,*" framespacing="0" frameborder="no"
		border="0" name="frameSetIndex">
		<frame
			src="${ctx }/manage/dic-manage!listDicsByRole.action?pageSize=14&roleid=${param.roleid}&isThisRole=0"
			name="leftFrame1" scrolling="No" noresize="noresize" id="leftFrame1" />
		<frame src="mid.jsp" name="mid" noresize="noresize" scrolling="No"
			id="mid" />
		<frame
			src="${ctx }/manage/dic-manage!listDicsByRole.action?pageSize=14&roleid=${param.roleid}&isThisRole=1"
			name="rightFrame1" scrolling="No" noresize="noresize"
			id="rightFrame1" />
	</frameset>
</html>
