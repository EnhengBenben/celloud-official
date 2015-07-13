$.ajaxSetup ({
	cache: false //关闭AJAX相应的缓存
});
// 当前 appId
var appId = window.parent.document.getElementById("softwareId").value;
var softwareName = window.parent.document.getElementById("softwareName").value;
// 当前时间
var nowDate = new Date().getTime();
//选中的 dataId 集合
var checkedDataIds = [];

/**
 * 复选框点击事件
 * 
 * @param obj
 */
function chkOnChange(obj) {
    var z = $(obj).val();
    if(appId == 73){
    	checkedDataIds = [z];
    }else{
    	var pos = $.inArray(z, checkedDataIds);
    	if ($(obj).is(':checked') && pos == -1) {
    		checkedDataIds.push(z);
    	} else if (pos != -1) {
    		checkedDataIds.splice(pos, 1);
    	}
    }
}

/**
 * 获取该app可以运行的数据
 * 
 * @param flag
 */
function getData(flag) {
	$.get("data!getAppDataList",{"softwareId" : appId,"page.pageSize":20,"page.currentPage":flag},function(pageList) {
		if(pageList.datas.length==0){
			return;
		}
		var list = pageList.datas;
		var page = pageList.page;
		var html = "<ul>";
		for ( var i = 0; i < list.length; i++) {
			var fileName = list[i].fileName;
			if(list[i].fileName.length>20){
				fileName = list[i].fileName.substring(0,20) + "...";
			}
			var imgSrc = "";
			if(list[i].isRunned>0){
				if(appId==1){
					imgSrc ="../../../images/publicIcon/icon-runned.png";
				}else{
					imgSrc = "../../images/publicIcon/icon-runned.png";
				}
			}else if(list[i].isRunning>0){
				if(appId==1){
					imgSrc = "../../../images/publicIcon/icon-running.png";
				}else{
					imgSrc = "../../images/publicIcon/icon-running.png";
				}
			}
			if(appId == 73 || appId == 1){
				html += "<li>"
					+ "<input type='radio' onclick='javascript:chkOnChange(this);' name='data' value='"
					+ list[i].fileId + "'> " + list[i].dataKey
					+ "（" + fileName + "）" + "<img src='"+imgSrc+"'></li>";
				
			}else {
				html += "<li>"
					+ "<input type='checkbox' onclick='javascript:chkOnChange(this);' name='data' value='"
					+ list[i].fileId + "'> " + list[i].dataKey
					+ "（" + fileName + "）" + "<img src='"+imgSrc+"'></li>";
			}
		}
		
		html +="</ul><div></div>";
		var pageHtml = "<div class='pagination pagination-small'><ul>";
		var currentPage = page.currentPage;
		if(page.hasPrev){
			currentPage = page.currentPage-1;
			pageHtml += "<li><a href='javascript:getData(" + currentPage + ")'>&lt;</a></li>";
		}else{
			pageHtml += "<li><a href='#'>&lt;</a></li>";
		}
		if(page.currentPage==1){
			pageHtml += "<li class='active'><a href='#'>1</a></li>";
		}else{
			pageHtml += "<li><a href='javascript:getData(1)'>1</a></li>";
		}
		if(page.currentPage>4&&page.totalPage>10){
			pageHtml += "<li>...</li>";
		}
		if(page.totalPage-page.currentPage>=7){
			if(page.currentPage==3){
				currentPage = page.currentPage-1;
				pageHtml += "<li><a href='javascript:getData(" + currentPage+")'>"+currentPage+"</a></li>";
			}
			if(page.currentPage==4){
				currentPage = page.currentPage-2;
				pageHtml += "<li><a href='javascript:getData(" + currentPage+")'>"+currentPage+"</a></li>";
			}
			if(page.currentPage>=3){
				currentPage = page.currentPage-1;
				pageHtml += "<li><a href='javascript:getData(" + currentPage+")'>"+currentPage+"</a></li>";
			}
			
			if(page.currentPage>1&&page.currentPage<page.totalPage){
				pageHtml += "<li class='active'><a href='#'>"+page.currentPage+"</a></li>";
			}
			if(page.totalPage-page.currentPage>1){
				currentPage = page.currentPage+1;
				pageHtml += "<li><a href='javascript:getData(" + currentPage+")'>"+currentPage+"</a></li>";
			}
			if(page.totalPage-page.currentPage>2){
				currentPage = page.currentPage+2;
				pageHtml += "<li><a href='javascript:getData(" + currentPage+")'>"+currentPage+"</a></li>";
			}
			if(page.totalPage-page.currentPage>3){
				currentPage = page.currentPage+3;
				pageHtml += "<li><a href='javascript:getData(" + currentPage+")'>"+currentPage+"</a></li>";
			}
			if(page.totalPage-page.currentPage>4){
				currentPage = page.currentPage+4;
				pageHtml += "<li><a href='javascript:getData(" + currentPage+")'>"+currentPage+"</a></li>";
			}
			if(page.totalPage-page.currentPage>5){
				currentPage = page.currentPage+5;
				pageHtml += "<li><a href='javascript:getData(" + currentPage+")'>"+currentPage+"</a></li>";
			}
			if(page.currentPage<4){
				if(page.totalPage-page.currentPage>6){
					currentPage = page.currentPage+6;
					pageHtml += "<li><a href='javascript:getData(" + currentPage+")'>"+currentPage+"</a></li>";
				}
			}
			if(page.currentPage==1){
				if(page.totalPage-page.currentPage>7){
					currentPage = page.currentPage+7;
					pageHtml += "<li><a href='javascript:getData(" + currentPage+")'>"+currentPage+"</a></li>";
				}
				if(page.totalPage-page.currentPage>8){
					currentPage = page.currentPage+8;
					pageHtml += "<li><a href='javascript:getData(" + currentPage+")'>"+currentPage+"</a></li>";
				}
			}else if(page.currentPage==2){
				if(page.totalPage-page.currentPage>7){
					currentPage = page.currentPage+7;
					pageHtml += "<li><a href='javascript:getData(" + currentPage+")'>"+currentPage+"</a></li>";
				}
			}else if(page.currentPage>4){
				if(page.totalPage-page.currentPage>6){
					currentPage = page.currentPage+6;
					pageHtml += "<li><a href='javascript:getData(" + currentPage+")'>"+currentPage+"</a></li>";
				}
			} 
		}else {
			if(page.totalPage-8>0){
				for(var z=page.totalPage-8; z<page.totalPage; z++){
					if(z == page.currentPage){
						pageHtml += "<li class='active'><a href='#'>"+z+"</a></li>";
					}else {
						pageHtml += "<li><a href='javascript:getData("+z+")'>"+z+"</a></li>";
					}
				}
			}else{
				for(var z=2; z<page.totalPage; z++){
					if(z == page.currentPage){
						pageHtml += "<li class='active'><a href='#'>"+z+"</a></li>";
					}else {
						pageHtml += "<li><a href='javascript:getData("+z+")'>"+z+"</a></li>";
					}
				}
			}
		}
		if(page.totalPage-page.currentPage>=8&&page.totalPage>10){
			pageHtml += "<li>...</li>";
		}
		if(page.currentPage==page.totalPage&&page.totalPage>1){
			pageHtml += "<li class='active'><a href='#'>"+page.totalPage+"</a></li>";
		}else if(page.totalPage>1){
			pageHtml += "<li><a href='javascript:getData("+page.totalPage+")'>"+page.totalPage+"</a></li>";
		}
		if(page.hasNext){
			var currentPage = page.currentPage+1;
			pageHtml += "<li><a href='javascript:getData("+currentPage+")'>&gt;</a></li>";
		}else{
			pageHtml += "<li><a href='#'>&gt;</a></li>";
		}
		pageHtml += "<li>共"+page.totalPage+"页&nbsp;|&nbsp;合计"+page.rowCount+"条</li></ul></div>";			
		$("#dataDiv").html(html);
		$("#pageDiv").html(pageHtml);
	});
}

/**
 * 序列标准化处理流程
 * 
 */
function sequenceJob(){
	clearWarn();
	$("#submit").click(function() {
		$("#runAlertInput").html("");
		var seq = $("#sequence").val();
		$.post("../../data!saveTextToFileAndRun", {
			"sequence" : seq,"softwareId":softwareId,"appName":appId
		});
	});
	$("#reset").click(function() {
		$("#runAlertInput").html("");
		$("#sequence").val("");
	});
	$("#random").click(function() {
		$("#runAlertInput").html("");
		$.post("sample.fa", {}, function(responseText) {
			$("#sequence").val(responseText);
		});
	});
}

/**
 * 清理警示信息
 */
function clearWarn(){
	$("#warning").html("");
	$("#warning").css("display","none");
}

/**
 * 清理复选框
 */
function resetCheckbox(){
	$("input[type='checkbox'][name='data']").attr("checked", false);
	$("input[type='radio'][name='data']").attr("checked", false);
	checkedDataIds=[];
}

/**
 * 将 id 的样式由 remove 切换为 add
 * 
 * @param id
 * @param remove
 * @param add
 */
function changeCss(id,remove,add){
	$("#"+id).removeClass(remove);
    $("#"+id).addClass(add);
}

$(document).ready(function(){
	getData(1);
	clearWarn();
	$("#submit").click(function() {
		$("#runAlertInput").html("");
		var seq = $("#sequence").val();
		if(seq == ""){
			$("#runAlertInput").removeClass("run_success");
	        $("#runAlertInput").addClass("run_error");
			$("#runAlertInput").html("请输入序列");
			return;
		}
		$("#runAlertInput").removeClass("run_error");
        $("#runAlertInput").addClass("run_success");
        $("#runAlertInput").html("您提交的数据已经开始运行，可进入<a href='javascript:toReportJsp();'>报告模块</a>查看运行状态...");
        $.get("data!saveTextToFileAndRun", {"sequence" : seq,"softwareId":appId,"appName":softwareName},function(data){
                if(data!=1){
                	$("#runAlertInput").removeClass("run_success");
                	$("#runAlertInput").addClass("run_error");
                	$("#runAlertInput").html("数据没有正确运行！");
                }
        });
	});
	$("#reset").click(function() {
		$("#runAlertInput").html("");
		$("#sequence").val("");
	});
	$("#random").click(function() {
		$("#runAlertInput").html("");
		$.post("test.fasta", {}, function(responseText) {
			$("#sequence").val(responseText);
		});
	});
	//切换至输入数据
	$("#toInputData").click(function(){
		$("#checkDataDiv").css("display","none");
		$("#inputDataDiv").css("display","");
	});
	//切换至选择数据
	$("#toSelData").click(function(){
		$("#inputDataDiv").css("display","none");
		$("#checkDataDiv").css("display","");
	});
	
	//重置 textarea
	$("#resetTextarea").click(function(){
		clearWarn();
		$("#show").css("display","none");
		$("textarea[name='sequence']").val("");
		$("#result").html("");
	});
	//重置 checkbox
	$("#resetCheckbox").click(function(){
		resetCheckbox();
	});
	
	//打开报告模块
	$(".openReport").click(function(){
		toReportJsp();
	});
	
	//打开文件上传模块
	$(".openUpload").click(function(){
	    var iframes = $(".leftApp", window.parent.document);
	    window.parent.setAPP(appId);
	    iframes[0].click();
	});
	
	//运行 checkbox
	$("#runCheckBox").click(function(){
	    var dataIds = "";
	    if (checkedDataIds.length == 0) {
	        changeCss("runAlert","run_success","run_error");
	        $("#runAlert").html("请选择至少一条数据！");
	        return;
	    }
	    if(appId == 11 && checkedDataIds.length<2){
	    	changeCss("runAlert","run_success","run_error");
	    	$("#runAlert").html("请选择至少两条数据！");
	    	return;
	    }
	    for ( var i = 0; i < checkedDataIds.length; i++) {
	        dataIds += checkedDataIds[i] + ",";
	    }
	    dataIds = dataIds.substring(0, dataIds.length - 1);
	    $.get("checkDatasInProReportState.action", {"dataIds" : dataIds}, function(fileNames) {
	        if (fileNames.length > 0) {
	            changeCss("runAlert","run_success","run_error");
	            $("#runAlert").html("所勾选的数据中有的正在运行，请取消勾选后再次尝试，或者等待其运行完成后再尝试");
	            return;
	        } else {
	        	resetCheckbox();
	            changeCss("runAlert","run_error","run_success");
	            $("#runAlert").html("您提交的数据已经开始运行，可进入<a href='javascript:toReportJsp();'>报告模块</a>查看运行状态...");
	            $.get("project!run", {"dataIds" : dataIds,"softwareId" : appId}, function(error) {
	                if (error == 1) {
	                	changeCss("runAlert","run_success","run_error");
	                    $("#runAlert").html("创建项目失败");
	                } else if (error == 2) {
	                	changeCss("runAlert","run_success","run_error");
	                    $("#runAlert").html("创建项目数据关系失败");
	                }
	            });
	        }
	    });
	});
	//运行 checkbox
	$("#runCheckBox1").click(function(){
	    var dataIds = "";
	    var fileId;
	    if (checkedDataIds.length != 1) {
	        changeCss("runAlert","run_success","run_error");
	        $("#runAlert").html("请选择一条数据！");
	        return;
	    }
	    for ( var i = 0; i < checkedDataIds.length; i++) {
	        dataIds += checkedDataIds[i] + ",";
	        fileId= checkedDataIds[i];
	     
	    }
	    dataIds = dataIds.substring(0, dataIds.length - 1);
	    $.get("checkDatasInProReportState.action", {"dataIds" : dataIds}, function(fileNames) {
	    	
	        if (fileNames.length > 0) {
	        
	            changeCss("runAlert","run_success","run_error");
	            $("#runAlert").html("所勾选的数据中有的正在运行，请取消勾选后再次尝试，或者等待其运行完成后再尝试");
	            return;
	        } else {
	        	resetCheckbox();
	            changeCss("runAlert","run_error","run_success");
	            $("#runAlert").html("您提交的数据已经开始运行，可进入<a href='javascript:toReportJsp();'>报告模块</a>查看运行状态...");
	            $.get("project!run", {"dataIds" : dataIds,"softwareId" : appId}, function(error) {
	                if (error == 1) {
	                	changeCss("runAlert","run_success","run_error");
	                    $("#runAlert").html("创建项目失败");
	                } else if (error == 2) {
	                	changeCss("runAlert","run_success","run_error");
	                    $("#runAlert").html("创建项目数据关系失败");
	                }
	            });
	        }
	    });
	    $("#fileId").val(fileId);
		$("form").submit();
	    
	});
});
function toReportJsp(){
	var iframes = $(".leftApp", window.parent.document);
    window.parent.setAPP(appId);
    iframes[2].click();
}