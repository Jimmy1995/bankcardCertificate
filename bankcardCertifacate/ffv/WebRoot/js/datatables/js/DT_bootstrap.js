/* Set the defaults for DataTables initialisation */
$.extend( true, $.fn.dataTable.defaults, {
	"sDom": "<'row'r>t<'row nomargin-right footbar'<'col-lg-4 col-sm-4 col-md-4 hidden-xs footbar-pagesize'il><'col-lg-8 col-sm-8 col-md-8 col-xs-12 nomargin-right footbar-pagebar'p>>",
	"sPaginationType": "bootstrap",
//	"oLanguage": {
//	    "sLengthMenu": " _MENU_"
//	},
	"fnInitComplete": function(oSettings, json) {
      //alert($('.dataTables_filter').html());//初始化表格完成后的函数
	  //fnupdatebindclick(oSettings);
		$(".dataTables_scrollHeadInner").removeAttr("style");//当grid高度固定时，宽度有bug
		$(".no-footer").removeAttr("style");//当grid高度固定时，宽度有bug
		if(!oSettings.showPagesize){//如果不显示左边当前页条数
			$(".footbar-pagesize").remove();//
			$(".footbar-pagebar").attr("class","col-lg-12 col-sm-12 col-md-12 col-xs-12 nomargin-right footbar-pagebar");//
		}
    }
} );



/* Default class modification */
$.extend( $.fn.dataTableExt.oStdClasses, {
	"sWrapper": "dataTables_wrapper form-inline"
} );

/*绑定单、多选*/
function fnupdatebindclick(oSettings){
	var tableid=oSettings.sTableId;
	var mutip=window[[tableid]+"_mutipleSelect"];
	var tableid=oSettings.sTableId;//是否支持多选
	$("#"+tableid+" tbody tr").click(function(e){
		if($(this).hasClass("selected")){
			$(this).removeClass("selected");
		}else{
			if(!mutip){
				$(this).siblings(".selected").removeClass("selected");//为保持单选，先把其它选中的移除
			}
			$(this).addClass("selected");
		}
		
	});
}
/*得到选择行的数据对象*/
function getSelectRowData(gridobj){
	return gridobj.rows('.selected').data();
}
/*得到当前页码*/
$.fn.getPageNO=function(gridobj){
	var tableSetings=gridobj.context[0];
	var paging_length=tableSetings._iDisplayLength;//当前每页显示多少  
	var page_start=tableSetings._iDisplayStart;//当前页开始  
	var n1 = Math.round(page_start); //四舍五入     
    var n2 = Math.round(paging_length); //四舍五入    
    var rslt = n1 / n2; //除    
    if (rslt >= 0) {  
        rslt = Math.floor(rslt); //返回小于等于原rslt的最大整数。     
    }  
    else {  
        rslt = Math.ceil(rslt); //返回大于等于原rslt的最小整数。     
    }
    return rslt;
};

/* API method to get paging information */
$.fn.dataTableExt.oApi.fnPagingInfo = function ( oSettings )
{
	return {
		"iStart":         oSettings._iDisplayStart,
		"iEnd":           oSettings.fnDisplayEnd(),
		"iLength":        oSettings._iDisplayLength,
		"iTotal":         oSettings.fnRecordsTotal(),
		"iFilteredTotal": oSettings.fnRecordsDisplay(),
		"iPage":          Math.ceil( oSettings._iDisplayStart / oSettings._iDisplayLength ),
		"iTotalPages":    Math.ceil( oSettings.fnRecordsDisplay() / oSettings._iDisplayLength )
	};
};


/* Bootstrap style pagination control */
$.extend( $.fn.dataTableExt.oPagination, {
	"bootstrap": {
		"fnInit": function( oSettings, nPaging, fnDraw ) {
			var oLang = oSettings.oLanguage.oPaginate;
			var fnClickHandler = function ( e ) {
				e.preventDefault();
				if ( oSettings.oApi._fnPageChange(oSettings, e.data.action) ) {
					fnDraw( oSettings );
				}
			};

			$(nPaging).addClass('pagination').append(
				'<ul class="pagination pagination-sm" style="margin-left:0px;margin-right:0px;margin-top:0px;margin-bottom:0px">'+
					'<li class="first disabled"><a href="#">&laquo;<span class="hidden-480">'+oLang.sFirst+'</span></a></li>'+
					'<li class="prev disabled"><a href="#">&larr;<span class="hidden-480">'+oLang.sPrevious+'</span></a></li>'+
					'<li class="next disabled"><a href="#"><span class="hidden-480">'+oLang.sNext+'</span> &rarr; </a></li>'+
					'<li class="last disabled"><a href="#">&raquo;<span class="hidden-480">'+oLang.sLast+'</span></a></li>'+
				'</ul>'
			);
			var els = $('a', nPaging);
			$(els[0]).bind( 'click.DT', { action: "first" }, fnClickHandler );
			$(els[1]).bind( 'click.DT', { action: "previous" }, fnClickHandler );
			$(els[2]).bind( 'click.DT', { action: "next" }, fnClickHandler );
			$(els[3]).bind( 'click.DT', { action: "last" }, fnClickHandler );
		},

		"fnUpdate": function ( oSettings, fnDraw ) {
			var iListLength = 5;
			var oPaging = oSettings.oInstance.fnPagingInfo();
			var an = oSettings.aanFeatures.p;
			var i, j, sClass, iStart, iEnd, iHalf=Math.floor(iListLength/2);

			if ( oPaging.iTotalPages < iListLength) {
				iStart = 1;
				iEnd = oPaging.iTotalPages;
			}
			else if ( oPaging.iPage <= iHalf ) {
				iStart = 1;
				iEnd = iListLength;
			} else if ( oPaging.iPage >= (oPaging.iTotalPages-iHalf) ) {
				iStart = oPaging.iTotalPages - iListLength + 1;
				iEnd = oPaging.iTotalPages;
			} else {
				iStart = oPaging.iPage - iHalf + 1;
				iEnd = iStart + iListLength - 1;
			}

			for ( i=0, iLen=an.length ; i<iLen ; i++ ) {
				// Remove the middle elements
				$('li:gt(0)', an[i]).filter(':not(.last)').filter(':not(.prev)').filter(':not(.next)').remove();
				// Add the new list items and their event handlers
				for ( j=iStart ; j<=iEnd ; j++ ) {
					sClass = (j==oPaging.iPage+1) ? 'class="active"' : '';
					$('<li '+sClass+'><a href="#">'+j+'</a></li>')
						.insertBefore( $('li.next', an[i])[0] )
						.bind('click', function (e) {
							e.preventDefault();
							oSettings._iDisplayStart = (parseInt($('a', this).text(),10)-1) * oPaging.iLength;
							fnDraw( oSettings );
						} );
				}

				// Add / remove disabled classes from the static elements
				if ( oPaging.iPage === 0 ) {
					$('li:first', an[i]).addClass('disabled');
					$('li.prev', an[i]).addClass('disabled');
				} else {
					$('li:first', an[i]).removeClass('disabled');
					$('li.prev', an[i]).removeClass('disabled');
				}

				if ( oPaging.iPage === oPaging.iTotalPages-1 || oPaging.iTotalPages === 0 ) {
					$('li:last', an[i]).addClass('disabled');
					$('li.next', an[i]).addClass('disabled');
				} else {
					$('li:last', an[i]).removeClass('disabled');
					$('li.next', an[i]).removeClass('disabled');
				}
			}
			
			fnupdatebindclick(oSettings);//绑定单击选择一行
		}
	}
} );


/*
 * TableTools Bootstrap compatibility
 * Required TableTools 2.1+
 */
if ( $.fn.DataTable.TableTools ) {
	// Set the classes that TableTools uses to something suitable for Bootstrap
	$.extend( true, $.fn.DataTable.TableTools.classes, {
		"container": "DTTT btn-group",
		"buttons": {
			"normal": "btn",
			"disabled": "disabled"
		},
		"collection": {
			"container": "DTTT_dropdown dropdown-menu",
			"buttons": {
				"normal": "",
				"disabled": "disabled"
			}
		},
		"print": {
			"info": "DTTT_print_info modal"
		},
		"select": {
			"row": "active"
		}
	} );

	// Have the collection use a bootstrap compatible dropdown
	$.extend( true, $.fn.DataTable.TableTools.DEFAULTS.oTags, {
		"collection": {
			"container": "ul",
			"button": "li",
			"liner": "a"
		}
	} );
}


(function($) {
	$.fn.dataTableGrid = function(option){
		var ss=this;
		var id=option.id;
		var action=option.action;
		var json=option.json;
		var formId=option.formId;
		var order=option.order;
		var disorderTarget=option.disorderTarget;
		var showPagesize=option.showPagesize;//是否显示
		return $('#'+id).DataTable({
            	"oLanguage": {//语言国际化
                   // "sUrl": ctx+"/js/datatables/js/jquery.dataTable.cn.txt"
            			"sProcessing": "<div class='l-grid-loading' style='display: block;'>加载中...</div>",  
            			"sLengthMenu": "_MENU_",  
            			"sZeroRecords": "没有您要搜索的内容",  
            			"sInfo": "第_START_-_END_,总计:<span class='badge'>_TOTAL_</span>",  
            			"sInfoEmpty": "记录数为<span class='badge'>0</span>",  
            			"sInfoFiltered": "(全部记录数 _MAX_  条)",  
            			"sInfoPostFix": "",  
            			"sSearch": "搜索",  
            			"sUrl": "",  
            			"oPaginate": {  
            				"sFirst":    "",  
            				"sPrevious": "",  
            				"sNext":     "",  
            				"sLast":     ""  
            				}  
                },
                "bServerSide": true,
                "sAjaxSource": action,
                "order":order,
                "sAjaxDataProp":"result",
				"fnServerData": function ( sSource, aoData, fnCallback, oSettings ) {
					 if(null==showPagesize){
						 oSettings.showPagesize=true; 
					 }else{
						 oSettings.showPagesize=showPagesize;
					 }
					 
                	 aoData=new Array();
                	 var sortName=json[(oSettings.aaSorting)[0][0]].orderName;
                	 var order=(oSettings.aaSorting)[0][1];
                	 aoData.push( { "name": "order","value":sortName+":"+order});
					 aoData.push( { "name": "pageSize", "value": oSettings._iDisplayLength} );
					 aoData.push( { "name": "pageNo", "value": Math.ceil((oSettings._iDisplayStart+oSettings._iDisplayLength)/oSettings._iDisplayLength) } );
					 aoData=aoData.concat($("#"+formId).find("select,input,textarea").serializeArray());//oSettings.searchParams查询参数;var oSettings = logs.fnSettings();oSettings.searchParams=[{"name": "mOContent11122","value": "mOContent11122"}];
		             oSettings.jqXHR = $.ajax( {
		             "dataType": 'json', 
		             "type": "POST", 
		             "url": sSource, 
		             "data": aoData,
		             "error":function(XMLHttpRequest, textStatus, errorThrown){
		            	 $.common.alert({"text":errorThrown+""});
		             },
		             "success": function(json){
		            	 if(json.errorType!='0'){//出错
		            		 $.common.alert({"text":json.message+""});
		            		 $(".dataTables_processing").hide();
		            		 return ;
		            	 }
		            	 json.data.iTotalRecords=json.data.totalCount;
		            	 json.data.iTotalDisplayRecords=json.data.totalCount;
		            	 fnCallback(json.data);
		              }
		           } );
		         },
				"sServerMethod":"POST",
                'bPaginate': true,  //是否分页。  
                "bProcessing":true, //当datatable获取数据时候是否显示正在处理提示信息。
                'bFilter': false,  //是否使用内置的过滤功能
                'bLengthChange': true, //是否允许自定义每页显示条数.
                "sPaginationType": "bootstrap", //分页样式   full_numbers
                "aoColumns": json,
                "columnDefs":[{"orderable":false,"targets":disorderTarget}],//禁用哪些字段的排序
                "sScrollY":option.scrollY,
                "scrollCollapse": true,
                "lengthMenu": eval(option.lengthMenu),
                "stateSave": false,//保存状态，关闭页面后，再打开显示上次的页码及每页显示条数
                "iDisplayLength":(option.pageSize>0?option.pageSize:10)
            });
	} 
})(jQuery);