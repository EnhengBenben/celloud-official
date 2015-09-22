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

//记录数据管理页面每页显示记录个数,默认是50
var dataPageDataNum = 50;
//记录数据列表当前页
var dataCurrentPageNumber = 1;
//保存用户已经选择的数据
var checkedDataIds = new Array();

//privateData排序
var sortType = 0;//默认按照时间进行排序
var fileNameSort = "asc";
var createDateSort = "desc";
var onclick = "";
function initData(){
	$("#dataTagSearch").val("");
	getAllDataList();
	
	//数据搜索绑定回车事件
	$("#dataTagSearch").bind('keyup', function(event){
	   var event = event || window.event;
	   if (event.keyCode=="13"){
		   getDataByCondition(1);
		   return;
	   }
	});
}	

//-------v 3.0版本
//获取我的数据
var addedApps = new Array();
function getAllDataList(){
	//设置遮罩
	spinner = new Spinner(opts);
	var target = document.getElementById('selfDataDiv');
	spinner.spin(target);
	$.get("data3!getAllData",{},function(responseText){
		spinner.stop();
		$("#selfDataDiv").html(responseText);
		$("#pageRecordSel").val(dataPageDataNum);
	});
}
function getDataByCondition(pageNum){
	//设置遮罩
	spinner = new Spinner(opts);
	var target = document.getElementById('selfDataDiv');
	spinner.spin(target);
	dataCurrentPageNumber = pageNum;
	var condition = $.trim($("#dataTagSearch").val());
	$.get("data3!getDataByCondition",{"condition":condition,"page.pageSize":dataPageDataNum,"page.currentPage":pageNum,"conditionInt":sortType,"sortByName":fileNameSort,"sortByDate":createDateSort},function(responseText){
		$("#selfDataDiv").html(responseText);
		$("#pageRecordSel").val(dataPageDataNum);
		toUse();
		$("#fileDataBody").scrollTop(0);
		spinner.stop();
		privateIcon();
	});
}
function sortByFileName(){//按文件名进行排序
	sortType = 1;
	if(fileNameSort=="asc"){
		fileNameSort = "desc";
	}else if(fileNameSort=="desc"){
		fileNameSort = "asc";
	}
	getDataByCondition(1);
}
function sortByCreateDate(){//按照上传时间进行排序
	sortType = 2;
	if(createDateSort=="asc"){
		createDateSort = "desc";
	}else if(createDateSort=="desc"){
		createDateSort = "asc";
	}
	getDataByCondition(1);
}
function privateIcon(){
	if(fileNameSort=="asc"){
		$("#sortFileName").removeClass("fa-sort-amount-desc").addClass("fa-sort-amount-asc");
	}else if(fileNameSort=="desc"){
		$("#sortFileName").removeClass("fa-sort-amount-asc").addClass("fa-sort-amount-desc");
	}
	if(createDateSort=="asc"){
		$("#sortCreateDate").removeClass("fa-sort-amount-desc").addClass("fa-sort-amount-asc");
	}else if(createDateSort=="desc"){
		$("#sortCreateDate").removeClass("fa-sort-amount-asc").addClass("fa-sort-amount-desc");
	}
}
function showRunApp(){
	$("#appsForDataUl").html("");
	$("#addedDataUl").html("");
	var dataIds = "";
	addedApps=[];
	if(checkedDataIds.length==0){
		$("#warningText").html("请选择至少一条数据！");
		$("#warningModal").modal('show');
		return;
	}
    //遍历得到每个checkbox的value值
	var dataLi = "";
    for (var i=0;i<checkedDataIds.length;i++){
         dataIds += checkedDataIds[i] + ",";
         dataLi += "<li class='types-options data-select' id='dataLi"+checkedDataIds[i]+"' title='点击删除' onclick=\"removetoRunData("+checkedDataIds[i]+")\">"+$("#fileName"+checkedDataIds[i]).val()+"</li>";
    }
    dataIds = dataIds.substring(0, dataIds.length-1);
    var dataLength = checkedDataIds.length;
    $.get("data3!getSoftListByFormat",{"dataIds":dataIds},function(result){
    	if(result == "所选数据格式不统一！"){
    		$("#warningText").html(result);
			$("#warningModal").modal("show");
    	}else{
    		var li = "";
    		for(var i=0;i<result.length;i++){
    			var appId = result[i].softwareId;
	    		var appName = result[i].softwareName;
	    		var dataNum = result[i].dataNum;
	    		var offLine = result[i].offLine;
	    		if(dataNum<=dataLength){
	    			if((appName=="VSP" ||appName=="CMP"||appName=="CMP_199"||appName=="GDD")&&dataNum<dataLength){
	    			}else{
	    				li += "<li class='types-options' id='runAppli"+appId+"' title='点击选中' onclick=\"addRunApp("+appId+",'"+appName+"','"+dataIds+"')\">"+appName+"</li>";
	    			}
	    		}
	    	}
    		$("#appsForDataUl").append(li);
    		$("#addedDataUl").append(dataLi);
    		$("#runApp").modal("show");
    	}
    });
}
function addRunApp(appId,appName,dataIds){
	if($("#runAppli"+appId).hasClass("selected")){
		addedApps.splice($.inArray(appId,addedApps),1);
		$("#runAppli"+appId).removeClass("selected");
	}else{
		//判断为包含CMP/CMP_199/GDD则提示检查所选数据
		if(appId==110 ||appId==111|| appId==112){
			$("#runErrorTitle").html("请确定以上数据为配对数据！<input type='hidden' id='appIdHide' value='"+appId+"'>");
			$("#runError").html("(配对格式:aaa<span class='text-red'>1</span>.fastq&nbsp;&nbsp;&nbsp;aaa<span class='text-red'>2</span>.fastq)");
			$("#runErrorDiv").removeClass("hide");
		}else{
			$.get("data3!checkDataRunningSoft",{"dataIds":dataIds,"conditionInt":appId},function(intList){
				if(intList.length>0){
					var dataName = "";
					for(var i=0;i<intList.length;i++){
						var dataId = intList[i];
						dataName+=$("#fileName"+dataId).val() + "<br>";
					}
					$("#runErrorTitle").html("以下数据正在运行APP："+appName);
					$("#runError").html(dataName+"<br>请选择其他APP或删除选中数据");
					$("#runErrorDiv").removeClass("hide");
				}else{
					$("#runAppli"+appId).addClass("selected");
					addedApps.push(appId);
				}
			})
		}
	}
}
function removetoRunData(id){
	checkedDataIds.splice($.inArray(id,checkedDataIds),1);
	$("#chk"+id).attr("checked",false);
	$("#dataLi"+id).remove();
}
function okToRun(type){
	if(type == 1){
		var appId = $("#appIdHide").val();
		$("#runAppli" +appId).addClass("selected");
		addedApps.push(appId);
	}
	$("#runErrorDiv").addClass("hide");
}
function toRunApp(){
	var dataIds = "";
	for (var i=0;i<checkedDataIds.length;i++){
		dataIds += checkedDataIds[i] + ",";
	}
	$("#runError").html("");
	for (var i=0;i<addedApps.length;i++){
		softId = addedApps[i];
		$.get("project!run", {"dataIds":dataIds,"softwareId" : softId}, function(error) {
			if (error > 0) {
				$("#runErrorTitle").html("以下APP运行失败：");
				
				$("#runError").append($("#runAppli" +softId).html() + "  ");
				$("#runErrorDiv").removeClass("hide");
			}else{
				if(i==addedApps.length-1){
					checkedDataIds = [];
					$("input[type='checkbox']").prop("checked",false);
					$("#runApp").modal("hide");
					$("#runErrorDiv").addClass("hide");
				}
			}
		});
	}
}
function deleteData(){
	$("#warningText").html("确定要删除选中数据吗？");
	$("#warningModal").modal("show");
	$("#checkTrue").one("click",function(){
		var dataIds = "";
	    for (var i=0;i<checkedDataIds.length;i++){
	         dataIds += checkedDataIds[i] + ",";
	    }
	    dataIds = dataIds.substring(0, dataIds.length-1);
	    $.get("data3!deleteData",{"dataIds":dataIds},function(result){
    		if(result>0){
    			getDataByCondition(dataCurrentPageNumber);
    			checkedDataIds = [];
    			toNoUse();
    		}
    	});
	});
}
function toMoreDataInfoModel(id){
	$.get("data3!getMoreData",{"conditionInt":id},function(responseText){
		$("#moreDatasForm").html(responseText);
		var strain = $("#strainListHide").html();
		setSelect2Info("dataStrainHide",eval(strain));
		$("#dataMoreInfoModal").modal("show");
	});
}
//给select2添加下拉选项
function setSelect2Info(objId,data){
	$("#"+objId).select2({
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
}
//打开批量管理数据页面
function toManageDatasModel(){
	$.get("dataJson_getAllDataStrainList.action",{},function(data){
		$("#manageDatasStrainSel").select2({
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
	$("#manageAnotherName").val("");
	$("#manageDatasTarg").val("");
	$("#manageDatasSample").val("");
	$("#manageDatasStrainSel").val("");
	$("#oneToOneManageDatasModal").modal("hide");
	$("#manageDatasModal").modal("show");
}
function saveManageDatas(){
	var dataIds = "";
    for (var i=0;i<checkedDataIds.length;i++){
         dataIds += checkedDataIds[i] + ",";
    }
    dataIds = dataIds.substring(0, dataIds.length-1);
    var strain = $("#manageDatasStrainSel").val();
	var dataTag = $("#manageDatasTarg").val();
	var sample = $("#manageDatasSample").val();
	var anotherName = $("#manageAnotherName").val();
    $.get("updateDataInfoListByFileId.action",{"data.strain":strain,"data.dataTags":dataTag,"data.sample":sample,"data.anotherName":anotherName,"dataIds":dataIds},function(flag){
    	if(flag>0){
    		getDataByCondition(dataCurrentPageNumber);
    		checkedDataIds = [];
    	}else {
    		$("#runErrorTitle").html("修改失败");
			$("#runErrorDiv").removeClass("hide");
    	}
    	$("#manageDatasModal").modal("hide");
    });
}
//----------
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

//数据管理-搜索框活得焦点时提示内容消失
function hideSearchInputInfo(){
	$("#dataTagSearch").attr("placeholder","");
}
//数据管理-搜索框失去焦点时显示提示内容
function showSearchInputInfo(){
	$("#dataTagSearch").attr("placeholder","搜索文件名/数据标签/文件别名");
}

function showOnetoOneManageModel(){
	$("#manageDatasModal").modal("hide");
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
    		getDataByCondition(dataCurrentPageNumber);
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
		$("#delDataBtn").removeAttr("disabled");
		$("#batchManage").removeAttr("disabled");
		$("#delDataBtn").removeClass("disabled");
		$("#batchManage").removeClass("disabled");
	}else{
		toNoUse();
	}
}

function initDataList(){
	//全选
	$("#selAll").click(function(){
		toNoUse();
		//清空checkedDataIds
		var checked = $(this).prop("checked");
		if(checked){
			$("input[name='datachk']").prop("checked",true);
			var arrChk=$("input[name='datachk']:checked");
			for(var i = 0;i<arrChk.length;i++){
				if($.inArray(arrChk[i].value,checkedDataIds)==-1){
					checkedDataIds.push(arrChk[i].value);
				}
			}
		}else{
			$("input[name='datachk']").prop("checked",false);
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
		$("#chk" + fileId).prop("checked",true);
	}
	//回显页面当前记录个数
	var recordNum = $("#pageRecordNumHidden").val();
	$("#pageRecordSel").val(recordNum);
	
	var arrChk=$("input[name='datachk']:checked");
	var currentPageRecordNum = $("#currentPageRecordNum").val();
	if((arrChk.length==currentPageRecordNum)&&(arrChk.length!=0)){//若所有数据为选中状态，则全选复选框应设为选中状态
		$("#selAll").prop("checked",true);
	}
}

//复选框点击事件
function chkOnChange(obj){
	var checked = $(obj).prop("checked");//jquery1.11获取属性
	if(checked){
		if($.inArray($(obj).val(),checkedDataIds)==-1){
			checkedDataIds.push($(obj).val());
		}
	}else{
		$("#selAll").prop("checked",false);			
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
	getDataByCondition(1);
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
//	$("#dataMoreInfoStrainHidden").attr("style","width:353px;background-color: #fff;");
//	$("#dataMoreInfoSampleHidden").attr("style","width:353px;background-color: #fff;");
//	$("#anotherNameHidden").attr("style","width:353px;background-color: #fff;");
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
//	$("#anotherNameHidden").attr("style","width:353px;background-color: #eee;");
	$("#dataMoreInfoStrainHidden").attr("readonly","readonly");
//	$("#dataMoreInfoStrainHidden").attr("style","width:353px;background-color: #eee;");
	$("#dataMoreInfoSampleHidden").attr("readonly","readonly");
//	$("#dataMoreInfoSampleHidden").attr("style","width:353px;background-color: #eee;");
	
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
				getDataByCondition(dataCurrentPageNumber);
			}else{
				$("#saveMoreInfoDiv").html("保存失败！");
			}
		});
	}else{
		$("#dataMoreInfoModal").modal("hide");
	}
}

function removeAllSpace(str) {
  return str.replace(/\s+/g, "");
}
