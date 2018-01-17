<!--
function writeIframe1()
{
    var strIframe = "<html><head><meta http-equiv='Content-Type' content='text/html; charset=utf-8'><style>"+
    "*{font-size: 12px; font-family: 宋体}"+
    ".bg{  color: "+ WebCalendar.lightColor +"; cursor: default; background-color: "+ WebCalendar.darkColor +";}"+
    "table#tableMain{ width: 142; height: 180;}"+
    "table#tableWeek td{ color: "+ WebCalendar.lightColor +";}"+
    "table#tableDay  td{ font-weight: bold;}"+
    "td#meizzYearHead, td#meizzYearMonth{color: "+ WebCalendar.wordColor +"}"+
    ".out { text-align: center; border-top: 1px solid "+ WebCalendar.DarkBorder +"; border-left: 1px solid "+ WebCalendar.DarkBorder +";"+
    "border-right: 1px solid "+ WebCalendar.lightColor +"; border-bottom: 1px solid "+ WebCalendar.lightColor +";}"+
    ".over{ text-align: center; border-top: 1px solid #FFFFFF; border-left: 1px solid #FFFFFF;"+
    "border-bottom: 1px solid "+ WebCalendar.DarkBorder +"; border-right: 1px solid "+ WebCalendar.DarkBorder +"}"+
    "input{ border: 1px solid "+ WebCalendar.darkColor +"; padding-top: 1px; height: 18; cursor: hand;"+
    "       color:"+ WebCalendar.wordColor +"; background-color: "+ WebCalendar.btnBgColor +"}"+
    "</style></head><body style='margin: 0px' oncontextmenu='return false'><form name=meizz>";
//body: onselectstart='return false'
    if (WebCalendar.drag){ strIframe += "<script language=javascript>"+"var drag=false, cx=0, cy=0, o = parent.WebCalendar.calendar; function document.onmousemove(){"+
    "if(parent.WebCalendar.drag && drag){if(o.style.left=='')o.style.left=0; if(o.style.top=='')o.style.top=0;"+
    "o.style.left = parseInt(o.style.left) + window.event.clientX-cx;"+
    "o.style.top  = parseInt(o.style.top)  + window.event.clientY-cy;}}"+
    "function document.onkeydown(){ "+
	"switch(window.event.keyCode){  case 27 : parent.hiddenCalendar(); break;"+
    "case 37 : parent.prevM(); break; case 38 : parent.prevY(); break; case 39 : parent.nextM(); break; case 40 : parent.nextY(); break;"+
    "case 84 : document.forms[0].today.click(); break;case 13 : window.event.keyCode = 0; window.event.returnValue= false;}"+
	"}"+
    "function dragStart(){cx=window.event.clientX; cy=window.event.clientY; drag=true;}</script>"}

    strIframe += "<select name=tmpYearSelect  onblur='parent.hiddenSelect(this)' style='z-index:1;position:absolute;top:3;left:18;display:none'"+
    " onchange='parent.WebCalendar.thisYear =this.value; parent.hiddenSelect(this); parent.writeCalendar();'></select>"+
    "<select name=tmpMonthSelect onblur='parent.hiddenSelect(this)' style='z-index:1; position:absolute;top:3;left:74;display:none'"+
    " onchange='parent.WebCalendar.thisMonth=this.value; parent.hiddenSelect(this); parent.writeCalendar();'></select>"+

    "<table id=tableMain class=bg border=0 cellspacing=2 cellpadding=0>"+
    "<tr><td width=140 height=19 bgcolor='"+ WebCalendar.lightColor +"'>"+
    "    <table width=140 id=tableHead border=0 cellspacing=1 cellpadding=0><tr align=center>"+
    "    <td width=15 height=19 class=bg title='向前翻 1 月&#13;快捷键：←' style='cursor: hand' onclick='parent.prevM()'><b>&lt;</b></td>"+
    "    <td width=60 id=meizzYearHead  title='点击此处选择年份' onclick='parent.funYearSelect(parseInt(this.innerText, 10))'"+
    "        onmouseover='this.bgColor=parent.WebCalendar.darkColor; this.style.color=parent.WebCalendar.lightColor'"+
    "        onmouseout='this.bgColor=parent.WebCalendar.lightColor; this.style.color=parent.WebCalendar.wordColor'></td>"+
    "    <td width=50 id=meizzYearMonth title='点击此处选择月份' onclick='parent.funMonthSelect(parseInt(this.innerText, 10))'"+
    "        onmouseover='this.bgColor=parent.WebCalendar.darkColor; this.style.color=parent.WebCalendar.lightColor'"+
    "        onmouseout='this.bgColor=parent.WebCalendar.lightColor; this.style.color=parent.WebCalendar.wordColor'></td>"+
    "    <td width=15 class=bg title='向后翻 1 月&#13;快捷键：→' onclick='parent.nextM()' style='cursor: hand'><b>&gt;</b></td></tr></table>"+
    "</td></tr><tr><td height=20><table id=tableWeek border=1 width=140 cellpadding=0 cellspacing=0 ";
    if(WebCalendar.drag){strIframe += "onmousedown='dragStart()' onmouseup='drag=false' onmouseout='drag=false'";}
    strIframe += " borderColorLight='"+ WebCalendar.darkColor +"' borderColorDark='"+ WebCalendar.lightColor +"'>"+
    "    <tr align=center><td height=20>日</td><td>一</td><td>二</td><td>三</td><td>四</td><td>五</td><td>六</td></tr></table>"+
    "</td></tr><tr><td valign=top width=140 bgcolor='"+ WebCalendar.lightColor +"'>"+
    "    <table id=tableDay height=120 width=140 border=0 cellspacing=1 cellpadding=0>";
         for(var x=0; x<5; x++){ strIframe += "<tr>";
         for(var y=0; y<7; y++)  strIframe += "<td class=out id='meizzDay"+ (x*7+y) +"'></td>"; strIframe += "</tr>";}
         strIframe += "<tr>";
         for(var x=35; x<39; x++) strIframe += "<td class=out id='meizzDay"+ x +"'></td>";
         strIframe +="<td colspan=3 class=out title='"+ WebCalendar.regInfo +"'><input style=' background-color: "+
         WebCalendar.btnBgColor +";cursor: hand; padding-top: 4px; width: 100%; height: 100%; border: 0' onfocus='this.blur()'"+
         " type=button value='&nbsp; &nbsp; 关闭' onclick='parent.hiddenCalendar()'></td></tr></table>"+
    "</td></tr>";
	if(false){
		strIframe +="<tr><td height=20 width=140 bgcolor='"+ WebCalendar.lightColor +"'>"+
		"    <table border=0 cellpadding=1 cellspacing=0 width=140>"+
		"    <tr><td><input name=prevYear title='向前翻 1 年&#13;快捷键：↑' onclick='parent.prevY()' type=button value='&lt;&lt;'"+
		"    onfocus='this.blur()' style='meizz:expression(this.disabled=parent.WebCalendar.thisYear==1000)'><input"+
		"    onfocus='this.blur()' name=prevMonth title='向前翻 1 月&#13;快捷键：←' onclick='parent.prevM()' type=button value='&lt;&nbsp;'>"+
		"    </td><td align=center><input name=today type=button value='Today' onfocus='this.blur()' style='width: 50' title='当前日期&#13;快捷键：T'"+
		"    onclick=\"parent.returnDate1(new Date().getDate() +'/'+ (new Date().getMonth() +1) +'/'+ new Date().getFullYear())\">"+
		"    </td><td align=right><input title='向后翻 1 月&#13;快捷键：→' name=nextMonth onclick='parent.nextM()' type=button value='&nbsp;&gt;'"+
		"    onfocus='this.blur()'><input name=nextYear title='向后翻 1 年&#13;快捷键：↓' onclick='parent.nextY()' type=button value='&gt;&gt;'"+
		"    onfocus='this.blur()' style='meizz:expression(this.disabled=parent.WebCalendar.thisYear==9999)'></td></tr></table>"+
		"</td></tr>";
	}
	if(WebCalendar.timeShow){
		strIframe +="<tr><td height=20 width=140>Time:<input type='text' size='16' id='timeinput' value='"+ appendZero(new Date().getHours())  +":"+ appendZero(new Date().getMinutes()) +":"+ appendZero(new Date().getSeconds()) +"'></td></tr>";
	}
	strIframe +="</table></form></body></html>";
    with(WebCalendar.iframe)
    {
        document.writeln(strIframe); document.close();
        for(var i=0; i<39; i++)
        {
            WebCalendar.dayObj[i] = eval("meizzDay"+ i);
            WebCalendar.dayObj[i].onmouseover = dayMouseOver;
            WebCalendar.dayObj[i].onmouseout  = dayMouseOut;
            WebCalendar.dayObj[i].onclick     = returnDate1;
        }
    }
}
function calendar_month() //主调函数
{
    var e = window.event.srcElement;   
    var o = WebCalendar.calendar.style; WebCalendar.eventSrc = e;
	if (arguments.length == 0) WebCalendar.objExport = e;
    if (arguments.length == 1) WebCalendar.objExport = eval(arguments[0]);
	if (arguments.length == 2){
		WebCalendar.objExport = eval(arguments[0]);
		WebCalendar.timeShow = arguments[1];
	}
	writeIframe1();
    WebCalendar.iframe.tableWeek.style.cursor = WebCalendar.drag ? "move" : "default";
	var t = e.offsetTop,  h = e.clientHeight, l = e.offsetLeft, p = e.type;
	while (e = e.offsetParent){t += e.offsetTop; l += e.offsetLeft;}
    o.display = ""; WebCalendar.iframe.document.body.focus();
    var cw = WebCalendar.calendar.clientWidth, ch = WebCalendar.calendar.clientHeight;
    var dw = document.body.clientWidth, dl = document.body.scrollLeft, dt = document.body.scrollTop;
    
    if (document.body.clientHeight + dt - t - h >= ch) o.top = (p=="image")? t + h : t + h + 6;
    else o.top  = (t - dt < ch) ? ((p=="image")? t + h : t + h + 6) : t - ch;
    if (dw + dl - l >= cw) o.left = l; else o.left = (dw >= cw) ? dw - cw + dl : dl;

    if  (!WebCalendar.timeShow) WebCalendar.dateReg = /^(\d{1,4})(-|\/|.)(\d{1,2})\2(\d{1,2})$/;
    else WebCalendar.dateReg = /^(\d{1,4})(-|\/|.)(\d{1,2})\2(\d{1,2}) (\d{1,2}):(\d{1,2}):(\d{1,2})$/;

    try{
        if (WebCalendar.objExport.value.trim() != ""){
            WebCalendar.dateStyle = WebCalendar.objExport.value.trim().match(WebCalendar.dateReg);
            if (WebCalendar.dateStyle == null)
            {
                WebCalendar.thisYear   = new Date().getFullYear();
                WebCalendar.thisMonth  = new Date().getMonth()+ 1;
                WebCalendar.thisDay    = new Date().getDate();
                //alert("原文本框里的日期有错误！\n可能与你定义的显示时分秒有冲突！");
                writeCalendar(); return false;
            }
            else
            {
                WebCalendar.thisYear   = parseInt(WebCalendar.dateStyle[1], 10);
                WebCalendar.thisMonth  = parseInt(WebCalendar.dateStyle[3], 10);
                WebCalendar.thisDay    = parseInt(WebCalendar.dateStyle[4], 10);
                WebCalendar.inputDate  = parseInt(WebCalendar.thisDay, 10) +"/"+ parseInt(WebCalendar.thisMonth, 10) +"/"+ 
                parseInt(WebCalendar.thisYear, 10); writeCalendar();
            }
        }  else writeCalendar();
    }  catch(e){writeCalendar();}
}
function returnDate1() //根据日期格式等返回用户选定的日期

{   WebCalendar.format="yyyy-mm"
    if(WebCalendar.objExport)
    {
        var returnValue;
        var a = (arguments.length==0) ? WebCalendar.day[this.id.substr(8)].split("/") : arguments[0].split("/");
        var d = WebCalendar.format.match(/^(\w{4})(-|\/|.|)(\w{1,2})$/);
        if(d==null){alert("你设定的日期输出格式不对！\r\n\r\n请重新定义 WebCalendar.format ！"); return false;}
        var flag = d[3].length==2 || d[4].length==2; //判断返回的日期格式是否要补零
        returnValue = flag ? a[2] +d[2]+ appendZero(a[1])  : a[2] +d[2]+ a[1] ;
        if(WebCalendar.timeShow)
        {
            //var h = new Date().getHours(), m = new Date().getMinutes(), s = new Date().getSeconds();
			with(WebCalendar.iframe)
			{
				var objtime;
				objtime=eval("document.all.timeinput");
				if(isTime(objtime.value)){
					var a = objtime.value.match(/^(\d{1,2})(:)?(\d{1,2})\2(\d{1,2})$/);
					if (a == null) {alert('输入的参数不是时间格式'); return false;}
            		returnValue += " "+ appendZero(a[1]) +":"+ appendZero(a[3]) +":"+ appendZero(a[4]);
				}else{
					return false;
				}
			}

            
        }
        WebCalendar.objExport.value = returnValue;
        hiddenCalendar();
    }
}
//-->