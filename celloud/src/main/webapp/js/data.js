$.ajaxSetup ({
	cache: false //关闭AJAX相应的缓存
});
//----------------------loading效果参数配置----------------------------
var opts = {
  lines: 7, // The number of lines to draw
  length: 10, // The length of each line
  width: 5, // The line thickness
  radius: 10, // The radius of the inner circle
  corners: 1, // Corner roundness (0..1)
  rotate: 0, // The rotation offset
  direction: 1, // 1: clockwise, -1: counterclockwise
  color: '#000', // #rgb or #rrggbb or array of colors
  speed: 1, // Rounds per second
  trail: 60, // Afterglow percentage
  shadow: false, // Whether to render a shadow
  hwaccel: false, // Whether to use hardware acceleration
  className: 'spinner', // The CSS class to assign to the spinner
  zIndex: 2e9, // The z-index (defaults to 2000000000)
  top: 'auto', // Top position relative to parent in px
  left: 'auto' // Left position relative to parent in px
};
var spinner;
//---------------------------------------------------------------------

//记录数据管理页面每页显示记录个数,默认是10
var dataPageDataNum = 10;
//记录数据列表当前页
var dataCurrentPageNumber = 1;
//保存用户已经选择的数据
var checkedDataIds = new Array();

//privateData排序
var sortType = 2;//默认按照时间进行排序
var fileNameSort = "asc";
var createDateSort = "desc";
//sharedData排序
var ssortType = 2;//默认按照时间进行排序
var sfileNameSort = "asc";
var screateDateSort = "desc";
var onclick = "";
function initData(){
	$(".table tr:odd").addClass("intro1");
	$(".table tr").mouseover(function(){
		$(this).addClass("change");
		});
	$(".table tr").mouseout(function(){
		$(this).removeClass("change");
		});
	$(".table tr:even").addClass("intro2");
	onclick = $("#proParamTab").attr("onclick");
	$("#windowMark").hide();
	
	$("#dataTagSearch").val("");
	getPrivateDataList();
	
	//数据搜索绑定回车事件
	$("#dataTagSearch").bind('keyup', function(event){
	   var event = event || window.event;
	   if (event.keyCode=="13"){
		   searchData(1);
		   return;
	   }
	});
}	

function keyDownToSearch(){
	var event = event || window.event;
	if(event.keyCode==13){return false;}
}

//获取我的数据
function getPrivateDataList(){
	//设置遮罩
	spinner = new Spinner(opts);
	var target = document.getElementById('selfDataDiv');
	spinner.spin(target);
	
	$("#pageRecordSel").val(dataPageDataNum);
	var dataTag = $.trim($("#dataTagSearch").val());
	
	var sortOrder = "asc";
	if(sortType==1){//当前按照文件名进行排序
		sortOrder = fileNameSort;
	}else if(sortType==2){//当前按照上传时间进行排序
		sortOrder = createDateSort;
	}
	$.get("data_getMyOwnData.action",{"page.pageSize":dataPageDataNum,"page.currentPage":1,"type":sortType,"sort":sortOrder,"dataTag":dataTag},function(responseText){
		spinner.stop();
		$("#selfDataDiv").attr("style","");
		$("#selfDataDiv").html(responseText);
		privateIcon();
//		getDataSharedToMeList();
	});
}
//获取共享给我的数据
function getDataSharedToMeList(){
	//设置遮罩
	spinner = new Spinner(opts);
	var target = document.getElementById('dataSharedToMeDiv');
	spinner.spin(target);
	$.get("data_getDataSharedToMe.action",{"type":2,"sort":"desc"},function(responseText){
		spinner.stop();
		$("#dataSharedToMeDiv").attr("style","");
		$("#dataSharedToMeDiv").html(responseText);
	});
}

//实现物种可输入可选择
function copyAddProStrainToInput(){
	var strain = $("#strainTxt").val();
	$("#addProInputStrainTxt").val(strain);
	
}
function copyProStrainToSel(){
	$("#strainTxt").focus();
	var strain = $.trim($("#addProInputStrainTxt" + n).val());
	if(strain != ""){
		$("#strainTxt").append("<option value='"+strain+"' selected>"+strain+"</option>");
	}
}

//数据运行app
function goToRunAppForData(softwareId,dataKey,reportId,userId,softwareName){
	//将运行按钮禁用
	$("#a"+reportId).attr("disabled",true);
	$("#a"+reportId).html("正在运行中，请耐心等待...");
	$.get("updateReportStateByReportId.action",{"reportId":reportId,"state":1},function(flag){
		if(flag==0){
			jAlert("修改报告状态失败");
			return;
		}else{
			//刷新App列表
			var fileId = $("#dataReportHidden").val();
			$.get("data_getSoftwareList.action",{"fileId":fileId},function(responseText){
				$("#dataReportBody").html(responseText);
			});
			//获取App对应的管理员邮箱
			$.get("getEmailBySessionUserId.action",{},function(email){
				//获取dataKeyList
				$.get("getFileNameByDataKey.action",{"dataKey":dataKey},function(fileName){
					var ext = fileName.substring(fileName.lastIndexOf("."));
					var dataList = dataKey + "," + dataKey + ext + "," + fileName + ";";
					var newPath = "?userId=" + userId + "&appId=" + softwareId + "&projectId=" + "&appName=" + softwareName +"&email=" + email + "&dataKeyList=" + dataList;
					$.get("runAppForData.action",{"requestUrl":newPath});
				});
			});
		}
	});
}

//数据管理-搜索框活得焦点时提示内容消失
function hideSearchInputInfo(){
	$("#dataTagSearch").attr("placeholder","");
}
//数据管理-搜索框失去焦点时显示提示内容
function showSearchInputInfo(){
	$("#dataTagSearch").attr("placeholder","搜索文件名/数据标签/文件别名");
}
//批量删除数据
function deleteDatas(){
	jConfirm("确定删除选中的数据吗？", '确认提示框', function(r) {
		if(r){
			var dataIds = "";
		    //遍历得到每个checkbox的value值
		    for (var i=0;i<checkedDataIds.length;i++){
		         dataIds += checkedDataIds[i] + ",";
		    }
		    dataIds = dataIds.substring(0, dataIds.length-1);
		    $.get("dataJson_delDatas.action",{"dataIds":dataIds},function(flag){
	    		if(flag>0){
	    			getPrivateDataList();
	    			checkedDataIds = [];
	    			toNoUse();
	    		}else{
	    			jAlert("删除失败！");
	    		}
	    	});
		}
	});
}
//为数据选择App
function showAppsForData(){
	var runText = $("#appTextBtn").text();
	if(runText=="已经开始运行"){
		return;
	}
	var dataIds = "";
	if(checkedDataIds.length==0){
		jAlert("请选择至少一条数据");
		$("#appTextBtn").text("选择App");
		return;
	}
    //遍历得到每个checkbox的value值
    for (var i=0;i<checkedDataIds.length;i++){
         dataIds += checkedDataIds[i] + ",";
    }
    dataIds = dataIds.substring(0, dataIds.length-1);
    $.get("validateDataType.action",{"dataIds":dataIds},function(result){
	    if(result==0){
	    	jAlert("所选数据格式不统一！");
	    	$("#appTextBtn").text("选择App");
	    }else{
	    	$("#appsForDataUl").html("");
			$("#appsForDataUl").show();
			$("body").bind("click",function(){
				hideDataAppUl();
			});
		    $.get("dataJson_getFileFormatById.action",{"dataIds":dataIds},function(fileFormat){
			    $.get("getSoftListByFormat.action",{"fileFormat":fileFormat},function(softList){
			    	$("#appsForDataUl").html("");
			    	for(var i=0;i<softList.length;i++){
			    		var appName = softList[i].softwareName;
			    		var dataNum = softList[i].dataNum;
			    		var offLine = softList[i].offLine;
			    		if(dataNum<=checkedDataIds.length){
			    			if(offLine==0){
			    				if(appName=="VSP" ||appName=="CMP"||appName=="CMP_199"){
			    					if(checkedDataIds.length==2){
			    						var li = "<li value='"+appName+"' id='li"+appName+"' onclick=javascript:setAppName('"+appName+"');><a style='cursor:pointer;'>"+appName+"</a></li>";
				    					$("#appsForDataUl").append(li);
			    					}
			    				}else{
			    					var li = "<li value='"+appName+"' id='li"+appName+"' onclick=javascript:setAppName('"+appName+"');><a style='cursor:pointer;'>"+appName+"</a></li>";
			    					$("#appsForDataUl").append(li);
			    				}
			    			}
			    		}
			    	}
			    	$("#appsForDataUl").append("<hr/>");
			    	$("#appsForDataUl").append("<li onclick=javascript:hideDataAppUl();><a style='cursor:pointer;'>关闭</a></li>");
			    });
		    });
	    }
    });
}
//关闭数据选择App下拉框
function hideDataAppUl(){
	$("#appsForDataUl").hide();
	$("body").unbind("click");
}
//多条数据运行
function runMultiDataNew(){
	var runText = $("#appTextBtn").text();
	if(runText=="选择App"){
		showAppsForData();
		return;
	}else if(runText=="已经开始运行"){
		return;
	}
	var dataIds = "";
	var fileNames = new Array();
	if(checkedDataIds.length==0){
		jAlert("请选择至少一条数据！");
		return;
	}
    //遍历得到每个checkbox的value值
    for (var i=0;i<checkedDataIds.length;i++){
         dataIds += checkedDataIds[i] + ",";
         fileNames.push($("#fileName"+checkedDataIds[i]).val());
    }
    dataIds = dataIds.substring(0, dataIds.length-1);
	$.get("getSoftwareIdByName.action",{"softwareName":runText},function(softId){
		if(softId == 110||softId == 111||softId == 112){
			var cmp_temp = "";
			var length = fileNames.length;
			for (i=0;i < length; i++) {
				cmp_temp +=fileNames[i];
				if(fileNames[i].length<30 && i<length-1 && fileNames[i+1].length<30){
					cmp_temp+="&nbsp;&nbsp;"
				}else{
					cmp_temp+="<br>"
				}
				if((i+1)%2==0){
					cmp_temp+="<br>";
				}
			}
			jConfirm("<html><body>请确定以下数据都存在配对数据！<br><span style='font-size:10px;color:#ccc'>(配对格式:aaa<span style='font-weight:bolder;color:red'>1</span>.fastq&nbsp;&nbsp;&nbsp;aaa<span style='font-weight:bolder;color:red'>2</span>.fastq)</span><br><span style='font-size:9px;'>"+cmp_temp+"</span></body></html>", "判断CMP所选数据", function(r) { 
				if(r) { 
					$.get("checkDatasInProReportState.action",{"dataIds":dataIds,"softwareId":softId},function(fileNames){
			    		if(fileNames.length>0){
			    			jAlert("所勾选的数据中有的正在运行，请取消勾选后再次尝试，或者等待其运行完成后再尝试");
			    			return;
			    		}else{
			    			jAlert(runText+"已经开始运行");
			    			$("#appTextBtn").html("已经开始运行");
			    			setInterval(resetAppBtnText,10000);
			    			$("input[name='datachk']").attr("checked",false);
			    			$("#selAll").attr("checked",false);
			    			
			    			$.get("project!run", {"dataIds" : dataIds,"softwareId" : softId}, function(error) {
			    				if (error == 1) {
			    					jAlert("创建项目失败");
			    				} else if (error == 2) {
			    					jAlert("创建项目数据关系失败");
			    				}
			    				checkedDataIds = [];
			    				searchData(dataCurrentPageNumber);
			    				$("#appTextBtn").attr("disabled",false);
			    			});
			    		}
			    	});
                }else{
                	return;
                }
            }); 
            event.stopPropagation(); 
            event.preventDefault();
	    }else{
	    	$.get("checkDatasInProReportState.action",{"dataIds":dataIds,"softwareId":softId},function(fileNames){
	    		if(fileNames.length>0){
	    			jAlert("所勾选的数据中有的正在运行，请取消勾选后再次尝试，或者等待其运行完成后再尝试");
	    			return;
	    		}else{
	    			jAlert(runText+"已经开始运行");
	    			$("#appTextBtn").html("已经开始运行");
	    			setInterval(resetAppBtnText,10000);
	    			$("input[name='datachk']").attr("checked",false);
	    			$("#selAll").attr("checked",false);
	    			
	    			$.get("project!run", {"dataIds" : dataIds,"softwareId" : softId}, function(error) {
	    				if (error == 1) {
	    					jAlert("创建项目失败");
	    				} else if (error == 2) {
	    					jAlert("创建项目数据关系失败");
	    				}
	    				checkedDataIds = [];
	    				searchData(dataCurrentPageNumber);
	    				$("#appTextBtn").attr("disabled",false);
	    			});
	    		}
	    	});
	    }
	});
}	
function resetAppBtnText(){
	$("#appTextBtn").html("选择App");
}

function setAppName(appName){
	hideDataAppUl();
	$("#appTextBtn").text(appName);
}

//打开批量管理数据页面
function tobatchManageModel(){
	$.get("dataJson_getAllDataStrainList.action",{},function(data){
		$("#batchManageDatasStrainSel").select2({
			placeholder: "请选择样本类型/物种",
		    allowClear: true, //必须与placeholder同时出现才有效
			createSearchChoice:function(term, data) {
				if ($(data).filter(function() {
					return this.text.localeCompare(term)===0; 
					}).length===0) {
					return {id:term, text:term};
					}
				},
			data:data
		});
	});
	$("#batchManageAnotherName").val("");
	$("#batchManageDatasTarg").val("");
	$("#batchManageDatasSample").val("");
	$("#batchManageDatasStrainSel").val("");
	$("#oneToOneManageDatasModal").modal("hide");
	$("#batchManageDatasModal").modal("show");
}

function saveBatchManageDatas(){
	//遍历得到每个checkbox的value值
	var dataIds = "";
    for (var i=0;i<checkedDataIds.length;i++){
         dataIds += checkedDataIds[i] + ",";
    }
    dataIds = dataIds.substring(0, dataIds.length-1);
    var strain = $("#batchManageDatasStrainSel").val();
	var dataTag = $("#batchManageDatasTarg").val();
	var sample = $("#batchManageDatasSample").val();
	var anotherName = $("#batchManageAnotherName").val();
    $.get("updateDataInfoListByFileId.action",{"data.strain":strain,"data.dataTags":dataTag,"data.sample":sample,"data.anotherName":anotherName,"dataIds":dataIds},function(flag){
    	if(flag>0){
    		searchData(dataCurrentPageNumber);
    		checkedDataIds = [];
    		$("#batchManageDatasModal").modal("hide");
    	}else {
    		jAlert("修改失败");
    	}
    });
}

function showOnetoOneManageModel(){
	$("#batchManageDatasModal").modal("hide");
	var dataIds = "";
    for (var i=0;i<checkedDataIds.length;i++){
         dataIds += checkedDataIds[i] + ",";
    }
    dataIds = dataIds.substring(0, dataIds.length-1);
    var oneManageHtml = "";
    $.get("getStrainDataKeySampleById.action",{"dataIds":dataIds},function(fileList){
    	if(fileList.length>0){
			for(var i=0; i<fileList.length; i++){
				oneManageHtml += "<tr style='line-height: 40px;'>";
				var fileName = fileList[i].fileName.length>10?fileList[i].fileName.substring(0,9)+"...":fileList[i].fileName;
    			oneManageHtml += "<td style='border:1px solid #ccc;padding:0px 0px 0px 5px' class='text-right'><table><tr style='border-width:0px;'><td align='right' style='border-width:0px; padding:0px 0px 5px 5px'>文件：</td><td align='left' style='border-width:0px; padding:0px 0px 5px 5px'>"+fileName+"</td></tr>";
    			oneManageHtml += "<tr style='border-width:0px'><td align='right' style='border-width:0px; height:20px; padding:0px 0px 5px 5px'>文件别名：</td><td align='left' style='border-width:0px; padding:0px 0px 5px 5px'><span id='oneManageStrainSpan"+fileList[i].fileId+"'><input type='text' id='oneManageAnotherName"+fileList[i].fileId+"' style='width:153px;' onkeyup=\"value=value.replace(/[^\\u4E00-\\u9FA5\\w]/g,'')\" onbeforepaste=\"clipboardData.setData('text',clipboardData.getData('text').replace(/[^\\u4E00-\\u9FA5\\w]/g,\'\'))\" placeholder='请输入字母\\数字\\下划线\\汉字' title='请输入字母\\数字\\下划线\\汉字'/></span></td></tr>";
    			oneManageHtml += "<tr style='border-width:0px'><td align='right' style='border-width:0px; height:20px; padding:0px 0px 5px 5px'>样本类型/物种：</td><td align='left' style='border-width:0px; padding:0px 0px 5px 5px'><span id='oneManageStrainSpan"+fileList[i].fileId+"'><input name='selectForStrain' type='hidden' id='oneManageStrain"+fileList[i].fileId+"' style='width:164px;'/></span></td></tr>";
    			oneManageHtml += "<tr style='border-width:0px'><td align='right' style='border-width:0px; height:20px; padding:0px 0px 5px 5px'>数据标签：</td><td align='left' style='border-width:0px; padding:0px 0px 5px 5px'><input type='text' id='oneManageTarg"+fileList[i].fileId+"' style='width:153px;' value=''/></td></tr>";
    			oneManageHtml += "<tr style='border-width:0px'><td align='right' style='border-width:0px; height:20px; padding:0px 0px 5px 5px'>样本：</td><td align='left' style='border-width:0px; padding:0px 0px 5px 5px'><input type='text' id='oneManageSample"+fileList[i].fileId+"' style='width:153px;' maxlength='45' value=''</td></tr></table></td>";
    			i++;
    			if(i<fileList.length){
    				var fileName = fileList[i].fileName.length>10?fileList[i].fileName.substring(0,9)+"...":fileList[i].fileName;
	    			oneManageHtml += "<td style='border:1px solid #ccc;padding:0px 0px 0px 5px' class='text-right'><table><tr style='border-width:0px;'><td align='right' style='border-width:0px; padding:0px 0px 5px 5px'>文件：</td><td align='left' style='border-width:0px; padding:0px 0px 5px 5px'>"+fileName+"</td></tr>";
	    			oneManageHtml += "<tr style='border-width:0px'><td align='right' style='border-width:0px; height:20px; padding:0px 0px 5px 5px'>文件别名：</td><td align='left' style='border-width:0px; padding:0px 0px 5px 5px'><span id='oneManageStrainSpan"+fileList[i].fileId+"'><input type='text' id='oneManageAnotherName"+fileList[i].fileId+"' style='width:153px;' onkeyup=\"value=value.replace(/[^\\u4E00-\\u9FA5\\w]/g,'')\" onbeforepaste=\"clipboardData.setData('text',clipboardData.getData('text').replace(/[^\\u4E00-\\u9FA5\\w]/g,\'\'))\" placeholder='请输入字母\\数字\\下划线\\汉字' title='请输入字母\\数字\\下划线\\汉字'/></span></td></tr>";
	    			oneManageHtml += "<tr style='border-width:0px'><td align='right' style='border-width:0px; height:20px; padding:0px 0px 5px 5px'>样本类型/物种：</td><td align='left' style='border-width:0px; padding:0px 0px 5px 5px'><span id='oneManageStrainSpan"+fileList[i].fileId+"'><input name='selectForStrain' type='hidden' id='oneManageStrain"+fileList[i].fileId+"' style='width:164px;'/></span></td></tr>";
	    			oneManageHtml += "<tr style='border-width:0px'><td align='right' style='border-width:0px; height:20px; padding:0px 0px 5px 5px'>数据标签：</td><td align='left' style='border-width:0px; padding:0px 0px 5px 5px'><input type='text' id='oneManageTarg"+fileList[i].fileId+"' style='width:153px;' value=''/></td></tr>";
	    			oneManageHtml += "<tr style='border-width:0px'><td align='right' style='border-width:0px; height:20px; padding:0px 0px 5px 5px'>样本：</td><td align='left' style='border-width:0px; padding:0px 0px 5px 5px'><input type='text' id='oneManageSample"+fileList[i].fileId+"' style='width:153px;' maxlength='45' value=''</td></tr></table></td>";
    			}
    			oneManageHtml += "</tr>";
	    		$("#onetoOneManageList").html(oneManageHtml);
			}
			$.get("dataJson_getAllDataStrainList.action",{},function(data){
    			$("input[name='selectForStrain']").select2({
					placeholder: "请选择样本类型/物种",
				    allowClear: true, //必须与placeholder同时出现才有效
					createSearchChoice:function(term, data) {
						if ($(data).filter(function() {
							return this.text.localeCompare(term)===0; 
							}).length===0) {
							return {id:term, text:term};
							}
						},
					data:data
				});
			});
    	}
    });
	$("#oneToOneManageDatasModal").modal("show");
}

function saveOneToOneManageDatas(){
	var dataIds = "";
    for (var i=0;i<checkedDataIds.length;i++){
         dataIds += checkedDataIds[i] + "," + $("#oneManageStrain" + checkedDataIds[i]).val() + ","+$("#oneManageTarg" + checkedDataIds[i]).val() + ","+$("#oneManageSample" + checkedDataIds[i]).val() + "," + removeAllSpace($("#oneManageAnotherName" + checkedDataIds[i]).val()) +";";
    }
    dataIds = dataIds.substring(0, dataIds.length-1);
    $.get("updateAllDataInfoByFileId.action",{"dataIds":dataIds},function(flag){
    	if(flag>0){
    		searchData(dataCurrentPageNumber);
    		checkedDataIds = [];
    		$("#oneToOneManageDatasModal").modal("hide");
    	}else {
    		jAlert("修改失败");
    	}
    });
}

/**
 * 禁用删除和批量操作
 */
function toNoUse(){
	$("#delDataBtn").attr("disabled",true);
	$("#batchManage").attr("disabled",true);
	$("#delDataBtn").addClass("disabled");
	$("#batchManage").addClass("disabled");
	$("#delDataBtn").removeClass("btn-blue");
	$("#batchManage").removeClass("btn-blue");
}
/**
 * 启用删除和批量操作
 */
function toUse(){
	if(checkedDataIds.length>0){
		$("#delDataBtn").attr("disabled",false);
		$("#batchManage").attr("disabled",false);
		$("#delDataBtn").removeClass("disabled");
		$("#batchManage").removeClass("disabled");
		$("#delDataBtn").addClass("btn-blue");
		$("#batchManage").addClass("btn-blue");
	}else{
		toNoUse();
	}
}

function initDataList(){
	$("#appTextBtn").text("选择App");
	$("#appsForDataUl").hide();
	//全选
	$("#selAll").click(function(){
		toNoUse();
		//清空checkedDataIds
		var checked = $(this).attr("checked");
		if(checked){
			$("input[name='datachk']").attr("checked",true);
			var arrChk=$("input[name='datachk']:checked");
			for(var i = 0;i<arrChk.length;i++){
				if($.inArray(arrChk[i].value,checkedDataIds)==-1){
					checkedDataIds.push(arrChk[i].value);
				}
			}
		}else{
			$("input[name='datachk']").attr("checked",false);
			var arrChk=$("input[name='datachk']");
			for(var i = 0;i<arrChk.length;i++){
				if($.inArray(arrChk[i].value,checkedDataIds)!=-1){
					checkedDataIds.splice($.inArray(arrChk[i].value,checkedDataIds),1);
				}
			}
		}
		toUse();
	});
	// 设置之前选过的数据为选中状态
	for(var j=0;j<checkedDataIds.length;j++){
		var fileId = checkedDataIds[j];
		$("#chk" + fileId).attr("checked","checked");
	}
	//回显页面当前记录个数
	var recordNum = $("#pageRecordNumHidden").val();
	$("#pageRecordSel").val(recordNum);
	
	var arrChk=$("input[name='datachk']:checked");
	var currentPageRecordNum = $("#currentPageRecordNum").val();
	if((arrChk.length==currentPageRecordNum)&&(arrChk.length!=0)){//若所有数据为选中状态，则全选复选框应设为选中状态
		$("#selAll").attr("checked","checked");
	}
}
//翻页
function searchData(page){
	//设置遮罩
	spinner = new Spinner(opts);
	var target = document.getElementById('selfDataDiv');
	spinner.spin(target);
	
	dataCurrentPageNumber = page;
	var sortOrder = "asc";
	if(sortType==1){//当前按照文件名进行排序
		sortOrder = fileNameSort;
	}else if(sortType==2){//当前按照上传时间进行排序
		sortOrder = createDateSort;
	}
	var dataTag = $.trim($("#dataTagSearch").val());
	$.get("data_getMyOwnData.action",{"dataTag":dataTag,"page.pageSize":dataPageDataNum,"page.currentPage":page,"type":sortType,"sort":sortOrder},function(responseText){
		$("#selfDataDiv").html(responseText);
		$("#pageRecordSel").val(dataPageDataNum);
		toUse();
		$("#fileDataBody").scrollTop(0);
		privateIcon();
		spinner.stop();
	});
}
function privateIcon(){
	if(sortType==1){
		if(fileNameSort=="asc"){
			$("#sortFileName").attr("src","../../images/publicIcon/ascending_b.png");
		}else if(fileNameSort=="desc"){
			$("#sortFileName").attr("src","../../images/publicIcon/descending_b.png");
		}
		if(createDateSort=="asc"){
			$("#sortCreateDate").attr("src","../../images/publicIcon/ascending.png");
		}else if(createDateSort=="desc"){
			$("#sortCreateDate").attr("src","../../images/publicIcon/descending.png");
		}
	}else if(sortType==2){
		if(createDateSort=="asc"){
			$("#sortCreateDate").attr("src","../../images/publicIcon/ascending_b.png");
		}else if(createDateSort=="desc"){
			$("#sortCreateDate").attr("src","../../images/publicIcon/descending_b.png");
		}
		if(fileNameSort=="asc"){
			$("#sortFileName").attr("src","../../images/publicIcon/ascending.png");
		}else if(fileNameSort=="desc"){
			$("#sortFileName").attr("src","../../images/publicIcon/descending.png");
		}
	}
}
//按文件名进行排序
function sortByFileName(){
	sortType = 1;
	if(fileNameSort=="asc"){
		fileNameSort = "desc";
	}else if(fileNameSort=="desc"){
		fileNameSort = "asc";
	}
	searchData(1);
}
//按照上传时间进行排序
function sortByCreateDate(){
	sortType = 2;
	if(createDateSort=="asc"){
		createDateSort = "desc";
	}else if(createDateSort=="desc"){
		createDateSort = "asc";
	}
	searchData(1);
}

//添加数据标签
function showAddDataTagModal(fileId,dataTags){
	if(dataTags==""){
		$("#dataTagHeaderMsg").html("添加数据标签");
	}else{
		$("#dataTagHeaderMsg").html("修改数据标签");
	}
	$("#dataTagHidden").val(fileId);
	$.get("getFileNameById.action",{"fileId":fileId},function(responseText){
		$("#addTagSpan").html("文件名称：" + responseText);
		$("#dataTagInfo").html("");
		$("#dataTagTxt").val(dataTags);
		$("#addTagModal").modal("show");
	});
}

//保存标签
function saveDataTag(){
	var fileId = $("#dataTagHidden").val();
	var tag = $.trim($("#dataTagTxt").val());
	$.get("saveDataTag.action",{"fileId":fileId,"dataTag":tag},function(responseText){
		if(responseText>0){
			$("#addTagModal").modal("hide");
			searchData(dataCurrentPageNumber);
		}else{
			$("#dataTagInfo").html("保存失败！");
		}
	});
}
//数据共享
function showShareDataModal(fileId,owner){
	$("#dataShareOwnerHidden").val(owner);
	//获取共享人getUsersMapByFileId
	$.get("getUsersMap.action",{},function(data){
		//获取以前的共享用户
		var newShareArr = new Array();
		$.get("getUsersMapByFileId.action",{"fileId":fileId},function(data){
			$.each(data,function(index,item){
				if(item.text!=sessionUserName){
					newShareArr.push(item);
				}
			});
			if(newShareArr.length>0){
				$("#userSel").select2("data", newShareArr);
			}
		});
		$("#userSel").select2({
			minimumInputLength: 1,
			data:data.tags,
			query: function (query) {
				var data = {results: [{id: query.term, text: query.term}]};
				query.callback(data);
		 	},
			multiple: true
		});
	});
	
	$("#dataShareHidden").val(fileId);
	$.get("getFileNameById.action",{"fileId":fileId},function(responseText){
		var ext = responseText.substring(responseText.lastIndexOf(".")-1);
		if(responseText.length-ext.length>20){
			var newFileName = responseText.substring(0,20)+"..." + ext;
			$("#shareDataSpan").html("文件名称："+newFileName);
		}else{
			$("#shareDataSpan").html("文件名称："+responseText);
		}
	});
	$("#userSel").val('');
	$("#dataShareSpanInfo").html("");
	$("#shareDataModal").modal("show");
}

//保存共享数据
function saveShareData(){
	var owner = $("#dataShareOwnerHidden").val();
	var fileId = $("#dataShareHidden").val();
	var userNames = "";
	var data = $("#userSel").select2("data");
	var empty = false;
	$.each(data,function(index,item){
		var id = $.trim(item.id);
		if(id==""){
			empty = true;
		}
	});
	if(empty){
		$("#dataShareSpanInfo").html("用户名不能有空值！");
		return;
	}
	$.each(data, function(index, item) {
		userNames += item.text + ",";
	});
	//在共享类表里进行共享，不能共享给数据或项目的所有人
	if(owner!=""){
		if(userNames.indexOf(owner, 0)!=-1){
			$("#dataShareSpanInfo").html("数据不能共享给数据的所有人！");
			return;
		}
	}
	if(userNames!=""&&(userNames.substring(0, userNames.length-1)==sessionUserName||userNames.indexOf(sessionUserName, 0)!=-1)){
		$("#dataShareSpanInfo").html("数据不能共享给自己！");
		return;
	}else{
		if(userNames!=""){
			userNames = userNames.substring(0, userNames.length-1);
		}
		$.get("shareData.action",{"fileId":fileId,"userNames":userNames},function(responseText){
			if(responseText=="1"){//保存成功
				$("#shareDataModal").modal("hide");
				//发送邮件
				$.get("sendEmail.action",{"userNames":userNames,"fileId":fileId},function(flag){
					if(flag==0){
						jAlert("邮件发送失败！");
					}else{
						checkedDataIds = [];
						searchData(dataCurrentPageNumber);
					}
				});
			}else if(responseText=="0"){
				$("#shareDataModal").modal("hide");
				searchData(dataCurrentPageNumber);
			}else{
				$("#dataShareSpanInfo").html(responseText);
			}
		});
	}
}

//修改数据物种
function showAddDataStrainModal(fileId,dataStrain){
	//获取文件名
	$.get("getFileNameById.action",{"fileId":fileId},function(responseText){
		$("#dataStrainFileNameSpan").html("文件名称："+responseText);
	});
	if(dataStrain==""){
		$("#dataStrainSpanInfo").html("添加");
	}else{
		$("#dataStrainSpanInfo").html("修改");
	}
	$.get("dataJson_getAllDataStrainList.action",{},function(data){
		$("#dataStrainSel").select2({
			placeholder: "请选择样本类型/物种",
		    allowClear: true, //必须与placeholder同时出现才有效
			createSearchChoice:function(term, data) {
				if ($(data).filter(function() {
					return this.text.localeCompare(term)===0; 
					}).length===0) {
					return {id:term, text:term};
					}
				},
			data:data
		});
		if(dataStrain == null||dataStrain==""){

		}else{
			$("#dataStrainSel").select2("data", {id: dataStrain, text: dataStrain});
		}
	});
	$("#dataStrainHidden").val(fileId);
	$("#addStrainModal").modal("show");
}
function saveDataStrain(){
	var fileId = $("#dataStrainHidden").val();
	var strain = $("#dataStrainSel").val();
	$.get("saveDataStrain.action",{"fileId":fileId,"strain":strain},function(responseText){
		if(responseText>0){
			$("#addStrainModal").modal("hide");
			searchData(dataCurrentPageNumber);
		}else{
			$("#").html("标签更新失败！");
		}
	});
}
//实现物种可输入可选择
function dataCopyStrainToInput(){
	var strain = $("#dataStrainSel").val();
	$("#dataStrainTxt").val(strain);
}
function dataCopyStrainToSel(){
	var strain = $.trim($("#dataStrainTxt").val());
	if(strain != ""){
		$("#dataStrainSel").append("<option value='"+strain+"' selected>"+strain+"</option>");
	}
}
//复选框点击事件
function chkOnChange(obj){
	$("#appTextBtn").text("选择App");
	$("#appsForDataUl").hide();
	var checked = $(obj).attr("checked");
	if(checked){
		if($.inArray($(obj).val(),checkedDataIds)==-1){
			checkedDataIds.push($(obj).val());
		}
	}else{
		$("#selAll").attr("checked",false);			
		if($.inArray($(obj).val(),checkedDataIds)!=-1){
			checkedDataIds.splice($.inArray($(obj).val(),checkedDataIds),1);
		}
	}
	toUse();
}

//判断某个数据id是否已经选择过
function ifExistsInArray(value){
	if($.inArray(value,checkedDataIds)!=-1){
		return true;
	}
	return false;
}

//选择页面显示记录个数
function changePageRecordNum(){
	dataPageDataNum = $("#pageRecordSel").val();
	searchData(1);
}
var editFlag = 0;
//显示数据更多信息
function showDataMoreInfoModal(fileId,fileName,strain,dataTags,sample,anotherName,fileFormat){
	$("#saveMoreInfoDiv").html("");
	editFlag = 0;
	$("#dataMoreInfoHidden").val(fileId);
	
	var ext = fileName.substring(fileName.lastIndexOf(".")-1);
	if(fileName.length-ext.length>20){
		var newFileName = fileName.substring(0,20)+"..." + ext;
		$("#dataMoreNameInfoSpan").html("文件名称："+newFileName);
	}else{
		$("#dataMoreNameInfoSpan").html("文件名称："+fileName);
	}
	
	$.get("dataJson_getAllDataStrainList.action",{},function(data){
		$("#dataMoreInfoStrainSel").select2({
			placeholder: "请选择样本类型/物种",
		    allowClear: true, //必须与placeholder同时出现才有效
			createSearchChoice:function(term, data) {
				if ($(data).filter(function() {
					return this.text.localeCompare(term)===0; 
					}).length===0) {
					return {id:term, text:term};
					}
				},
			data:data
		});
	});
	$("#dataMoreInfoStrainSel").val(strain);
	$("#dataMoreInfoStrainHidden").val(dataTags);
	$("#dataMoreInfoSampleHidden").val(sample);
	$("#anotherNameHidden").val(anotherName);
	$("#fileFormatHidden").val(fileFormat);
	cancelEditMoreInfo();
	$("#dataMoreInfoModal").modal("show");
}
//编辑更多信息
function showDataMoreInfoEdit(){
	$("#saveMoreInfoDiv").html("");
	editFlag = 1;
	$("#dataMoreInfoStrainHidden").attr("style","width:353px;background-color: #fff;");
	$("#dataMoreInfoSampleHidden").attr("style","width:353px;background-color: #fff;");
	$("#anotherNameHidden").attr("style","width:353px;background-color: #fff;");
	$("#anotherNameHidden").removeAttr("readonly");
	$("#dataMoreInfoStrainHidden").removeAttr("readonly");
	$("#dataMoreInfoSampleHidden").removeAttr("readonly");
	$("#dataMoreInfoStrainSel").removeAttr("disabled");
	$("#dataTag").children(".popWindow-overlap").remove(); 
	$(".select2-container").removeClass("select2-container-disabled");
}
//取消编辑更多信息
function cancelEditMoreInfo(){
	$("#saveMoreInfoDiv").html("");
	editFlag = 0;
	$("#dataMoreInfoStrainSel").attr("disabled","disabled");
	$("#anotherNameHidden").attr("readonly","readonly");
	$("#anotherNameHidden").attr("style","width:353px;background-color: #eee;");
	$("#dataMoreInfoStrainHidden").attr("readonly","readonly");
	$("#dataMoreInfoStrainHidden").attr("style","width:353px;background-color: #eee;");
	$("#dataMoreInfoSampleHidden").attr("readonly","readonly");
	$("#dataMoreInfoSampleHidden").attr("style","width:353px;background-color: #eee;");
	
	$(".select2-focusser").attr("disabled","disabled");
	$(".select2-container").addClass("select2-container-disabled");
	$("#dataTag").append("<div class=\"popWindow-overlap\"></div>");
}
//保存更多信息编辑
function saveDataMoreInfo(){
	if(editFlag==1){
		var fileId = $("#dataMoreInfoHidden").val();
		var strain = $("#dataMoreInfoStrainSel").val();
		var dataTag = $("#dataMoreInfoStrainHidden").val();
		var sample = $("#dataMoreInfoSampleHidden").val();
		var anotherName = $("#anotherNameHidden").val();
		$.get("updateDataInfoByFileId.action",{"data.fileId":fileId,"data.strain":strain,"data.dataTags":dataTag,"data.sample":sample,"data.anotherName":removeAllSpace(anotherName)},function(flag){
			if(flag>0){
				$("#dataMoreInfoModal").modal("hide");
				searchData(dataCurrentPageNumber);
			}else{
				$("#saveMoreInfoDiv").html("保存失败！");
			}
		});
	}else{
		$("#dataMoreInfoModal").modal("hide");
	}
}
//分页
function searchSharedData(page){
	spinner = new Spinner(opts);
	var target = document.getElementById('dataSharedToMeDiv');
	spinner.spin(target);
	var sortOrder = "asc";
	if(ssortType==1){//当前按照文件名进行排序
		sortOrder = sfileNameSort;
	}else if(ssortType==2){//当前按照上传时间进行排序
		sortOrder = screateDateSort;
	}
	$.get("data_getDataSharedToMe.action",{"page.pageSize":10,"page.currentPage":page,"type":ssortType,"sort":sortOrder},function(responseText){
		$("#dataSharedToMeDiv").html(responseText);
		spinner.stop();
		if(ssortType==1){
			if(sfileNameSort=="asc"){
				$("#sFileNameSortType").attr("src","../../images/publicIcon/ascending_b.png");
			}else if(sfileNameSort=="desc"){
				$("#sFileNameSortType").attr("src","../../images/publicIcon/descending_b.png");
			}
			if(screateDateSort=="asc"){
				$("#sCreateDateSortType").attr("src","../../images/publicIcon/ascending.png");
			}else if(screateDateSort=="desc"){
				$("#sCreateDateSortType").attr("src","../../images/publicIcon/descending.png");
			}
		}else if(ssortType==2){
			if(screateDateSort=="asc"){
				$("#sCreateDateSortType").attr("src","../../images/publicIcon/ascending_b.png");
			}else if(screateDateSort=="desc"){
				$("#sCreateDateSortType").attr("src","../../images/publicIcon/descending_b.png");
			}
			if(sfileNameSort=="asc"){
				$("#sFileNameSortType").attr("src","../../images/publicIcon/ascending.png");
			}else if(sfileNameSort=="desc"){
				$("#sFileNameSortType").attr("src","../../images/publicIcon/descending.png");
			}
		}
	});
}
//根据文件名进行排序
function ssortByFileName(){
	ssortType = 1;
	if(sfileNameSort=="asc"){
		sfileNameSort = "desc";
	}else if(sfileNameSort=="desc"){
		sfileNameSort = "asc";
	}
	searchSharedData(1);
}
//根据上传时间进行排序
function ssortByCreateDate(){
	ssortType = 2;
	if(screateDateSort=="asc"){
		screateDateSort = "desc";
	}else if(screateDateSort=="desc"){
		screateDateSort = "asc";
	}
	searchSharedData(1);
}

//显示更多信息
function showSharedDataMoreInfoModal(strain,dataTag){
	$("#sharedDataStrainSpan").html(strain);
	$("#sharedDataDataTagSpan").html(dataTag);
	$("#sharedDataMoreInfoModal").modal("show");
}

function removeAllSpace(str) {
  return str.replace(/\s+/g, "");
}
