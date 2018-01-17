<%
	//System.gc();
	//response.sendRedirect("memory.jsp");
	double totle = (Runtime.getRuntime().totalMemory()) /(1024*1024);
	double surplus = totle-(Runtime.getRuntime().freeMemory()) /(1024*1024);
	out.print(totle+"-"+surplus);
%>