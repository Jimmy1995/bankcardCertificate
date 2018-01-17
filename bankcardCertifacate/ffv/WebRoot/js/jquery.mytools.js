jQuery.common={
	openDialog:function(option){//option={url:"/ddd/sss.action",title:"标题",width:"",height:"",scroll:"yes"}
		var diaobj= top.dialog({
				title:option.title||"提示",
				content:option.content,
				url:option.url,
				width:option.width,
				height:option.height,
				onclose: function () {
					if(this.returnValue){//如果有返回值，返回值要是一个能执行的js
						eval(this.returnValue);//执行js
					}
					if(option.callback){
						option.callback();
					}
				}
		});
		diaobj.showModal();
		return diaobj;
	},
	alert:function(option){//option={text:"内容"}
		var diaobj=top.dialog({title:"提示",content: option.text});
		diaobj.showModal();
	},
	confirm:function(option){//option={text:"你确定吗？",yesCallBack:function(){},noCallBack:function(){}}
		var	diaobj=top.dialog({
			title:"消息",
			content:option.text, 
			okValue:"确定",
			ok:function () {
		    	if(option.yesCallBack)option.yesCallBack();
			}, 
			cancelValue:"取消",
			cancel:function () {
			    if(option.noCallBack)option.noCallBack();
			}
		});
		diaobj.showModal();
	},
	addTab:function(id,name,url){
		top.addTab(id,name,url);
	},
	mergeCell:function(tbid,col,row) {//合并单元格
    	row=row|0;
    	var tb=document.getElementById(tbid);
    	var lastValue=""; 
    	var value="";
    	var pos=1; 
    	for(var i=row;i<tb.rows.length;i++){
        	value = tb.rows[i].cells[col].innerText; 
        	if((lastValue == value)&&(i>row)){//如果“本单元格=上行单元格”，删除“本单元格”，将“上行单元格.rowSpan=上行单元格.rowSpan+1”
	        	tb.rows[i].deleteCell(col); 
	        	tb.rows[i-pos].cells[col].rowSpan = tb.rows[i-pos].cells[col].rowSpan+1; 
	        	pos++; 
        	}else{ 
	        	lastValue = value; 
	        	pos=1; 
        	} 
    	} 
    } 
};
		/**
		 * 自动对表单赋值
		 * @param dtoObj json对象,来自服务器的dto对象
		 * @param attrNameStr dto在action中的属性名
		 */
		function pushFormValue(dtoObj, attrNameStr, isArray) {
			for ( var attrName in dtoObj) {
				if (dtoObj[attrName] != null && $.isArray(dtoObj[attrName])) {
					if(attrNameStr){
						pushFormValue(dtoObj[attrName], attrNameStr + "." + attrName, true);
					}else{
						pushFormValue(dtoObj[attrName], attrName, true);
					}
				} else if (dtoObj[attrName] != null&& typeof dtoObj[attrName] == 'object') {
					if (isArray) {
						if(attrNameStr){
							pushFormValue(dtoObj[attrName], attrName);
						}else{
							pushFormValue(dtoObj[attrName], attrNameStr + "[" + attrName+ "]");
						}
						
					} else {
						if(attrNameStr){
							pushFormValue(dtoObj[attrName], attrNameStr + "." + attrName);
						}else{
							pushFormValue(dtoObj[attrName], attrName);
						}
					}
				} else {
					if (dtoObj[attrName] == null) {
						continue;
					}
					var name = attrNameStr + "." + attrName;
					if(!attrNameStr){
						name=attrName;
					}
					var inputs = $("[name='" + name + "']");
					for ( var index = 0; index < inputs.length; index++) {
						if (inputs[index].type == 'text'
								|| inputs[index].type == 'hidden'
								|| inputs[index].tagName == 'TEXTAREA') {
							inputs[index].value = dtoObj[attrName];
							continue;
						}
		
						if (inputs[index].type == 'checkBox'
								|| inputs[index].type == 'radio') {
							$(
									"input[name='" + inputs[index].name + "'][value='"
											+ dtoObj[attrName] + "']").attr("checked",
									"checked");
							continue;
						}
		
						if (inputs[index].tagName == 'SELECT') {
							$(
									"select[name='" + inputs[index].name
											+ "'] option[value='" + dtoObj[attrName]
											+ "']").attr("selected", "selected");
							continue;
						}
					}
				}
			}
		}
	function Map() {//初始化map_,给map_对象增加方法，使map_像Map  
         var map_ = new Object();  
         map_.put = function(key, value) {  
             map_[key+'_'] = value;  
         };  
         map_.get = function(key) {  
             return map_[key+'_'];  
         };  
         map_.remove = function(key) {  
             delete map_[key+'_'];  
         };  
         map_.removeAll = function() {  
             map_=new Object();  
         };
         map_.keyset = function() {  
             var ret = "";  
             for(var p in map_) {  
                 if(typeof p == 'string' && p.substring(p.length-1) == "_") {  
                     ret += ",";  
                     ret += p.substring(0,p.length-1);  
                 }  
             }  
             if(ret == "") {  
                 return ret.split(",");  
             } else {  
                 return ret.substring(1).split(",");  
             }  
         };  
         return map_;  
	}

Array.prototype.contains = function(item){
    return RegExp(item).test(this);
};

/* sys辅助对象 */
if (!sys) {
    var sys = {};
    sys = {
        obj: function(name) {
            return document.getElementById(name);
        },

        val: function(name, value) {
            var o = this.obj(name);
            if (o == null) {
                return "";
            }
            if (value != undefined) {
                o.value = value;
            }
            return o.value;
        },

        focus: function(name) {
            var o = this.obj(name);
            if (o == null) {
                return;
            }
            window.setTimeout(function() {
                o.focus();
            },
            0);
        },

        show: function(name) {
            var o = this.obj(name);
            if (o == null) {
                return;
            }

            o.style.display = "";
        },

        hide: function(name) {
            var o = this.obj(name);
            if (o == null) {
                return;
            }

            o.style.display = "none";
        },

        check: function(name, type) {
            var o = this.obj(name);
            if (o == null) {
                return false;
            }

            var chkval = this.trim(o.value);
            var chktype = (type == undefined ? "str": type.toLowerCase());
            var chkresult = false;

            switch (chktype) {
            case "str":
                chkresult = !this.isnull(chkval);
                break;
            case "num":
                chkresult = this.isnum(chkval);
                break;
            case "int":
                chkresult = this.isint(chkval);
                break;
            case "dec":
                chkresult = this.isdec(chkval);
                break;
            case "date":
                chkresult = this.isdate(chkval);
                break;
            case "time":
                chkresult = this.istime(chkval);
                break;
            case "datetime":
                chkresult = this.isdatetime(chkval);
                break;
            default:
                chkresult = false;
            }

            return chkresult;
        },

        each: function(object, callback) {
            var name, i = 0,
            length = object.length;

            if (length == undefined) {
                for (name in object) if (callback.call(object[name], name, object[name]) === false) break;
            } else {
                for (var value = object[0]; i < length && callback.call(value, i, value) !== false; value = object[++i]) {}
            }

            return object;
        },

        extend: function() {
            // copy reference to target object
            var target = arguments[0] || {},
            i = 1,
            length = arguments.length,
            deep = false,
            options;

            // Handle a deep copy situation
            if (target.constructor == Boolean) {
                deep = target;
                target = arguments[1] || {};
                // skip the boolean and the target
                i = 2;
            }

            // Handle case when target is a string or something (possible in
            // deep copy)
            if (typeof target != "object" && typeof target != "function") target = {};

            // extend jQuery itself if only one argument is passed
            if (length == i) {
                target = this; --i;
            }

            for (; i < length; i++)
            // Only deal with non-null/undefined values
            if ((options = arguments[i]) != null)
            // Extend the base object
            for (var name in options) {
                var src = target[name],
                copy = options[name];

                // Prevent never-ending loop
                if (target === copy) continue;

                // Recurse if we're merging object values
                if (deep && copy && typeof copy == "object" && !copy.nodeType)
                // Never move original objects, clone them
                target[name] = powersi.extend(deep, src || (copy.length != null ? [] : {}), copy);

                // Don't bring in undefined values
                else if (copy !== undefined) target[name] = copy;

            }

            // Return the modified object
            return target;
        },
        isvalue: function(o) {
            return typeof(o) !== 'undefined' && o !== null ? true: false;
        },

        isarray: function(o) {
            return this.isvalue(o) ? Object.prototype.toString.apply(o) === '[object Array]': false;
        },

        ismap: function(o) {
            return this.isvalue(o) ? typeof(o) === 'object': false;
        },

        getarraysize: function(o) {
            return this.isarray(o) ? o.length: -1;
        },

        getarray: function(o) {
            return this.isarray(o) ? o: new Array();
        },

        getmap: function(o) {
            return this.ismap(o) ? o: new Object();
        },

        getstring: function(str) {
            return this.isvalue(str) ? String(str) : '';
        },

        tostring: function(o) {
            var str = "";
            if (this.isvalue(o)) {
                var t = Object.prototype.toString.apply(o);

                if (t === '[object Array]') {
                    var a = [];
                    for (var i = 0; i < o.length; i++) {
                        a.push(this.tostring(o[i]));
                    }
                    str = '[' + a.join(',') + ']';

                    a.length = 0;
                    a = null;
                } else if (t === '[object Date]') {
                    var y = o.getYear();
                    if (y < 1900) {
                        y += 1900;
                    }
                    var m = o.getMonth() + 1;
                    str = y + "-" + this.lpad(m, 2, '0') + "-" + this.lpad(o.getDate(), 2, '0') + " " + this.lpad(o.getHours(), 2, '0') + ":" + this.lpad(o.getMinutes(), 2, '0') + ":" + this.lpad(o.getSeconds(), 2, '0');
                } else if (t === '[object Object]') {
                    var a = [],
                    k;
                    for (k in o) {
                        var vt = Object.prototype.toString.apply(o[k]);
                        if (vt === '[object Array]' || vt === '[object Object]') {
                            a.push('"' + k + '":' + this.tostring(o[k]));
                        } else {
                            a.push('"' + k + '":"' + this.tostring(o[k]) + '"');
                        }
                    }
                    str = '{' + a.join(',') + '}';

                    a.length = 0;
                    a = null;
                } else {
                    str = String(o);
                }
            }

            return str;
        },

        tojson: function(str) {
            return eval("(" + str + ")");
        },

        isnull: function(str) {
            if (str == "") {
                return true;
            }
            var regu = "^[ ]+$";
            var re = new RegExp(regu);
            return re.test(str);
        },

        isnum: function(str) {
            var regu = /^(\d+)$/;
            return regu.test(str);
        },

        isint: function(str) {
            var regu = /^[-]{0,1}[0-9]{1,}$/;
            return regu.test(this.val(str));
        },

        isdec: function(str) {
            if (this.isint(v)) return true;
            var re = /^[-]{0,1}(\d+)[\.]+(\d+)$/;
            if (re.test(str)) {
                if (RegExp.$1 == 0 && RegExp.$2 == 0) return false;
                return true;
            } else {
                return false;
            }
        },

        getmaxday: function(year, month) {
            if (month == 4 || month == 6 || month == 9 || month == 11) return "30";
            if (month == 2) if (year % 4 == 0 && year % 100 != 0 || year % 400 == 0) return "29";
            else return "28";
            return "31";
        },

        isdate: function(str) {
            if (str.length != 10) return false;

            var dateList = str.split("-");
            if (dateList.length != 3) return false;

            var year = dateList[0];
            if (year > "2100" || year < "1900") return false;

            var month = dateList[1];
            if (month > "12" || month < "01") return false;

            var day = dateList[2];
            if (day > this.getmaxday(year, month) || day < "01") return false;

            return true;
        },

        istime: function(str) {
            if (str.length != 8) return false;
            var timeList = str.split(":");
            if (timeList.length < 1 || timeList.length > 3) return false;

            if (!this.isnum(timeList[0]) || !this.isnum(timeList[1]) || !this.isnum(timeList[2])) return false;

            var hour = parseInt(timeList[0]);
            if (hour >= 24 || hour < 0) return false;

            var minute = parseInt(timeList[1]);
            if (minute >= 60 || minute < 0) return false;

            var second = parseInt(timeList[2]);
            if (second >= 60 || second < 0) return false;

            return true;
        },

        isdatetime: function(str) {
            if (str.length != 19) return false;
            if (!this.isdate(str.substring(0, 10))) return false;
            if (str.charAt(10) != ' ') return false;
            return this.istime(str.substring(11));
        },

        /* table begin */
        istable: function(el) {
            return (el.nodeName == 'TABLE') ? true: false;
        },

        istbody: function(el) {
            return (el.nodeName == 'TBODY') ? true: false;
        },

        istfoot: function(el) {
            return (el.nodeName == 'TFOOT') ? true: false;
        },

        isthead: function(el) {
            return (el.nodeName == 'THEAD') ? true: false;
        },

        iscell: function(el) {
            return (el.nodeName == 'TD' || el.nodeName == 'TH') ? true: false;
        },

        isrow: function(el) {
            return (el.nodeName == 'TR') ? true: false;
        },
        /* table end */

        /* string begin */
        lpad: function(str, len, pad) {
            str = this.getstring(str);
            if (typeof(len) === "undefined") {
                var len = 0;
            }
            if (typeof(pad) === "undefined") {
                var pad = ' ';
            }

            if (len + 1 >= str.length) {
                str = Array(len + 1 - str.length).join(pad) + str;
            }

            return str;
        },

        rpad: function(str, len, pad) {
            str = this.getstring(str);
            if (typeof(len) === "undefined") {
                var len = 0;
            }
            if (typeof(pad) === "undefined") {
                var pad = ' ';
            }

            if (len + 1 >= str.length) {
                str = str + Array(len + 1 - str.length).join(pad);
            }

            return str;
        },

        trim: function(text) {
            return (text || "").replace(/^\s+|\s+$/g, "");
        },
        /* string end */

        /* debug begin */
        benchmark: function(s, d) {
            this.debug(s + "" + (new Date().getTime() - d.getTime()) + "ms");
        },

        debug: function(s) {
            if (typeof console != "undefined" && typeof console.debug != "undefined") {
                console.log(s);
            } else {
                try {
                    window.external.writeLog("", s);
                } catch(e) {
                    alert(s);
                }
            }
        },
        /* debug end */

        cache: {}
    };// end of  funciton
} // end of  var
/**
 * 给JS函数分命名空间：
 * 例：$.namespace("common.sys")放在页最前面
 * 后面就可以这样定义函数：common.sys.funA=function(){}
 */
$.namespace = function() {
    var a=arguments, o=null, i, j, d;
    for (i=0; i<a.length; i=i+1) {
        d=a[i].split(".");
        o=window;
        for (j=0; j<d.length; j=j+1) {
            o[d[j]]=o[d[j]] || {};
            o=o[d[j]];
        }
    }
    return o;
};