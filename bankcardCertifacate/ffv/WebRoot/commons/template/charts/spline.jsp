<%@ page language="java" pageEncoding="gbk" %>
<%@page import="com.common.util.json.JsonUtils"%>
<%@page import="java.util.Map"%>
<script type="text/javascript" src="${ctx}/js/highcharts/highcharts.js"></script>
<!--[if IE]><script type="text/javascript" src="${ctx}/js/highcharts/excanvas.compiled.js"></script><![endif]-->
<%
	Map<String,String> param=(Map<String,String>)request.getAttribute("chartParamMap");
	String id=param.get("id");
	String title=param.get("title");
	String chartsType=param.get("chartsType");
	String unit=param.get("unit");
	String series=JsonUtils.toString(request.getAttribute(param.get("series")));
	String xAxis=JsonUtils.toString(request.getAttribute(param.get("xAxis")));
%>
<script type="text/javascript">
	$(function() {
		new Highcharts.Chart({
			chart : {
				renderTo : '<%=id%>',
				defaultSeriesType : '<%=chartsType%>',
				margin : [ 50, 150, 60, 80 ]
			},
			title : {
				text : '<%=title%>',
				style : {
					margin : '10px 100px 0 0' // center it
				}
			},
			xAxis : {
				categories: <%=xAxis%>
			},
			yAxis : {
				title : {
					text : '<%=unit%>'
				},
				plotLines : [ {
					value : 0,
					width : 1,
					color : '#808080'
				} ]
			},
			tooltip : {
				formatter : function() {
					return  '<b>'+ this.series.name +'</b><br/>'
							+ this.x+':'
							+ this.y + '<%=unit%>';
				}
			},
			legend : {
				layout : 'vertical',
				style : {
					left : 'auto',
					bottom : 'auto',
					right : '10px',
					top : '100px'
				}
			},
			series : <%=series%>
		});
	});
</script>
<div id="<%=id%>" style="width: 100%; margin: 0 auto"></div>