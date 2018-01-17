<%@ page language="java" import="java.util.*,java.util.Map.Entry" pageEncoding="GBK" %>
<%@ taglib uri="/WEB-INF/sys.tld" prefix="sys" %><%@ taglib uri="/WEB-INF/c.tld" prefix="c" %><%@ taglib uri="/WEB-INF/fmt.tld" prefix="fmt" %><%@ taglib uri="/WEB-INF/fn.tld" prefix="fn" %> 
<!DOCTYPE HTML>
<HTML>
	<HEAD>
		<sys:head title="系统性能监控"/>
<script type="text/javascript" src="${ctx}/js/highcharts/highcharts.js"></script>
<!--[if IE]><script type="text/javascript" src="${ctx}/js/highcharts/excanvas.compiled.js"></script><![endif]-->
<script type="text/javascript">
		$(document).ready(function() {
			Highcharts.setOptions({ //设置时间;highcharts 中默认开启了UTC（世界标准时间），由于中国所在时区为+8，所以经过highcharts的处理后会减去8个小时。 
				global: {  
					useUTC: false  
				}  
			}); 
			
			 new Highcharts.Chart({
				chart: {
					renderTo: 'container',
					defaultSeriesType: 'spline',
					margin: [50, 150, 60, 80],
					events: {
						load: function() {
							// set up the updating of the chart each second
							var series = this.series[0];
							var series1 = this.series[1];
							setInterval(function() {
								var x = (new Date()).getTime(), // current time
									y = parseFloat(ajaxTotleMemory());
									y1=parseFloat(ajaxSurplusMemory());
								series.addPoint([x, y], true, true);
								series1.addPoint([x, y1], true, true);
							}, 3000);
						}
					}
				},
				title: {
					text: '系统监控',
					style: {
						margin: '10px 100px 0 0' // center it
					}
				},
				xAxis: {
					type: 'datetime',
					tickPixelInterval: 150
				},
				yAxis: {
					title: {
						text: 'M'
					},
					plotLines: [{
						value: 0,
						width: 1,
						color: '#808080'
					}]
				},
				tooltip: {
					formatter: function() {
			                return '<b>'+ this.series.name +'</b><br/>'+
							Highcharts.dateFormat('%Y-%m-%d %H:%M:%S', this.x) +': '+ this.y +'M';
					}
				},
				legend: {
					layout: 'vertical',
					style: {
						left: 'auto',
						bottom: 'auto',
						right: '10px',
						top: '100px'
					}
				},
				series: [{
					name: '分配内存(单位M)',
					data: (function() {
						// generate an array of random data
						var data = [],
							time = (new Date()).getTime(),
							i;
						for (i = -20; i <= 0; i++) {
							data.push({
								x: time + i* 1000,
								y: (function(){return 0;})()
							});
						}
						return data;
					})()
				},{
					name: '使用内存(单位M)',
					data: (function() {
						// generate an array of random data
						var data = [],
							time = (new Date()).getTime(),
							i;
						for (i = -20; i <= 0; i++) {
							data.push({
								x: time + i* 1000,
								y: (function(){return 0;})()
							});
						}
						return data;
					})()
				}]
			});
			
			
		});
		
		
	    var t;
	    var s;
		function ajaxTotleMemory(){
			var x=$.ajax({url: "${ctx}/manage/runInfo/util.jsp",async: false}).responseText;
			t=x.split("-")[0];
			s=x.split("-")[1];			
			return t;
		}
		function ajaxSurplusMemory(){
			return s;
		}
		
</script>
</head>
<body>
	<div id="container" style="width: 100%; height: 400px; margin: 0 auto"></div>
</body>
</html>