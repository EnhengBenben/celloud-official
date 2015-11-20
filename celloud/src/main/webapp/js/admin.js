$.ajaxSetup ({
	cache: false //关闭AJAX相应的缓存
});
/** 
* ctrl+f 搜索后台管理模块
 * 软件管理--> software start
 * 用户管理--> user start
 * 登录统计--> login start
 * 文件上传--> fileupload start
 * 数据导出--> dataOutput start
 * 公司管理--> company&dept start
 * 公告--> notice start
 * 客户端--> client start
 * 统计功能 --> count
 * 邮件群发 --> email start
 */
/**
 * ============menu start========
 */
	function initMenu(){
		document.onkeydown = function(event) {
			var e = event || window.event || arguments.callee.caller.arguments[0];
			if (e && e.keyCode == 13) { // enter
				userLogin();
			}
		};
		$("#loginForm").find(".control-group").removeClass("error");
		$("#loginForm").find(".help-inline").html("");
		//show之前加一个判断
		$.get("getAdminRole.action",{},function(role){
			if(role!=2&&role!=3){
				$("input").focus();
				$("#loginForm").find('input').val('');
				$("#loginModal").modal("show");
			}
		});

		$("#softCount").click(function(){
			$("#main").load("admin/softwareStatistic.jsp");
		});
		
		$("#dataMenu").click(function(){
			$("#main").load("admin/dataMenu.jsp");
		});
		
		$("#notice").click(function(){
			$.get("checkAdminSessionTimeOut.action",{},function(userName){
				if(userName==null||userName==""){
					window.location = "pages/index.jsp"; 
				}else{
					$("#main").load("admin/notice.jsp");
				}
			});
		});
		
		$("#email").click(function(){
			$("#main").load("admin/email.jsp");
		});
		
		//登录
		$("#login").click(function(){
			$("#loginForm").find(".control-group").removeClass("error");
			$("#loginForm").find(".help-inline").html("");
			$("#loginErrorDiv").css("display","none");
			$("#loginForm").find('input').val('');
			$("#loginModal").modal("show");
		});
		//软件管理
		$("#software").click(function(){
			$.get("checkAdminSessionTimeOut.action",{},function(userName){
				if(userName==null||userName==""){
					window.location = "pages/index.jsp"; 
				}else{
					$("#main").load("admin/software.jsp");
				}
			});
		});
		$("#data").click(function(){
			$("#main").load("admin/data.jsp");
		});
		$("#logo").click(function(){
			$("#main").load("admin/log.jsp");
		});
		
		$("#count").click(function(){
			$("#main").load("admin/count.jsp");
		});
		
		$("#client").click(function(){
			$("#main").load("admin/client.jsp");
		});
		//用户管理
		$("#user").click(function(){
			$.get("checkAdminSessionTimeOut.action",{},function(userName){
				if(userName==null||userName==""){
					window.location = "pages/index.jsp"; 
				}else{
					$("#main").load("admin/user.jsp");
				}
			});
		});
		//退出
		$("#logout").click(function(){
			$.get("logout.action",{},function(responseText){
				if(responseText){
					$("#main").html("");
					$("#menu").load("admin/menu.jsp");
					$("#loginErrorDiv").css("display","none");
					$("#loginError").html("");
				}else{
					jAlert("退出失败！");
				}
			});
		});
		//弹出修改密码框
		$("#resetPwd").click(function(){
			$("#resetPwdForm").find(".control-group").removeClass("error");
			$("#resetPwdForm").find(".help-inline").html("");
			$("#resetPwdForm").find('input').val('');
			$("#editPwdModal").modal("show");
		});
		//修改密码
		$("#saveNewPwd").click(function(){
			if((!validateResetPwdForm())||(!validate)){
				return;
			}else{
				var pwdNew = $.trim($("#pwdNew").val());
				$.get("updatePwd.action",{"password":pwdNew},function(responseText){
					if(responseText){
						jAlert("修改成功");
						$("#editPwdModal").modal("hide");
					}else{
						jAlert("修改失败");
					}
				});
			}
		});
		$("#company").click(function(){
			getHospital(1);
		});
		
		$(".subNavBtn").click(function(){
			$(".subNavBtn").find("a").removeClass("subNavBtnA");
			$(this).find("a").addClass("subNavBtnA");
		});
	}
/**
 * ============menu end========
 */
/**
 * =========login start=========
 */
	function validateLoginForm(){
		var isTrue = true;
		var username = $.trim($("#login_username").val());
		if(username==""){
			isTrue = false;
			$(".login[name='user.username']").parent().parent().addClass("error");
			$(".login[name='user.username']").parent().parent().find(".help-inline").html("请输入登录名称！");
		}else{
			$(".login[name='user.username']").parent().parent().removeClass("error");
			$(".login[name='user.username']").parent().parent().find(".help-inline").html("");
		}
		var password = $.trim($("#login_password").val());
		if(password==""){
			isTrue = false;
			$("#login_password").parent().parent().addClass("error");
			$("#login_password").parent().parent().find(".help-inline").html("请输入登录密码！");
		}else{
			$("#login_password").parent().parent().removeClass("error");
			$("#login_password").parent().parent().find(".help-inline").html("");
		}
		return isTrue;
	}
	//用户登录
	function userLogin(){
		if(!validateLoginForm()){
			return;
		}else{
			var password = $.trim($("#login_password").val());
			$("#time").val(new Date().getTime());
			$("#userPwdHidden").val(hex_md5(password));
			var params = $("#loginForm").serialize();
			$.get("userJson_adminLogin.action",params,function(responseText){
				if(responseText){
					$.get("getAdminRole.action",{},function(role){
						if(role==2||role==3){
							$("#menu").load("admin/menu.jsp");
							$("#loginModal").modal("hide");
							if(role==3){
								$("#main").load("admin/count.jsp");
							}
						}else{
							$("#loginErrorDiv").css("display","");
							$("#loginError").html("没有操作权限，登录失败！");
						}
					});
				}else{
					$("#loginErrorDiv").css("display","");
					$("#loginError").html("登录失败！登录名称或登录密码错误");
				}
			});
		}
	}
	var validate = true;
	function checkPwd(){
		var pwdOld = $.trim($("#pwdOld").val());
		//验证用户密码
		if(pwdOld!=""){
			$.get("checkPwd.action",{"password":pwdOld},function(responseText){
				if(!responseText){
					validate = false;
					$(".reset[name='user.password']").parent().parent().addClass("error");
					$(".reset[name='user.password']").parent().parent().find(".help-inline").html("原密码不正确！");
				}else{
					validate = true;
					$(".reset[name='user.password']").parent().parent().removeClass("error");
					$(".reset[name='user.password']").parent().parent().find(".help-inline").html("");
				}
			});
		}
	}
	function validateResetPwdForm(){
		var isTrue = true;
		var oldPwd = $("#pwdOld").val();
		var pwdNew = $.trim($("#pwdNew").val());
		var confirmPwd = $.trim($("#confirmPwd").val());
		if(oldPwd==""){
			isTrue = false;
			$(".reset[name='user.password']").parent().parent().addClass("error");
			$(".reset[name='user.password']").parent().parent().find(".help-inline").html("请输入原密码！");
		}else{
			$(".reset[name='user.password']").parent().parent().removeClass("error");
			$(".reset[name='user.password']").parent().parent().find(".help-inline").html("");
			if(pwdNew==""){
				isTrue = false;
				$(".reset[name='pwdNew']").parent().parent().addClass("error");
				$(".reset[name='pwdNew']").parent().parent().find(".help-inline").html("请输入新密码！");
			}else{
				$(".reset[name='pwdNew']").parent().parent().removeClass("error");
				$(".reset[name='pwdNew']").parent().parent().find(".help-inline").html("");
				if(confirmPwd==""){
					isTrue = false;
					$(".reset[name='confirmPwd']").parent().parent().addClass("error");
					$(".reset[name='confirmPwd']").parent().parent().find(".help-inline").html("请输入确认密码！");
				}else{
					if(confirmPwd!=pwdNew){
						isTrue = false;
						$(".reset[name='confirmPwd']").parent().parent().addClass("error");
						$(".reset[name='confirmPwd']").parent().parent().find(".help-inline").html("确认密码与新密码不一致！");
					}else{
						$(".reset[name='confirmPwd']").parent().parent().removeClass("error");
						$(".reset[name='confirmPwd']").parent().parent().find(".help-inline").html("");
					}
				}
			}
		}
		return isTrue;
	}
/**
 * ===========login end=========
 */
/**
 * ============software start========
 */
var editor;
var appFileFormatFlag = 1;
var appScreenShot = 1;
var appClassifyFlag = 1;
var currentPage = 1;
var pageSize = 10;
function initSoftware(){
	init(currentPage);
	$("#editSave").click(function(){
		var screenShotName = "";
		$(".fileName").each(function(){
			screenShotName += "@"+$(this).html();
		});
		$(".editSoftware[name='soft.screenshot']").val(screenShotName);
		alert($("#softEditForm").serialize());
		$.post("softwareJson_editSoft.action",$("#softEditForm").serialize(),function(flag){
			$("#editModal").modal("hide");
			$("#edit").html("");
			if(flag>0){
				init(currentPage);
			}else{
				jAlert("保存失败");
			}
		});
	});
}
//初始化
function init(pageNow){
	currentPage = pageNow;
	$.get("soft_getPageListSoft.action",{"excess":new Date().getTime(),"page.currentPage":currentPage,"page.pageSize":pageSize},function(responseText){
		$("#softwareList").html(responseText);
	});
}
//验证addForm
function validateSoftForm(){
	var isTrue = true;
	var softwareName = $.trim($("#softwareName").val());
	if(softwareName==""){
		isTrue = false;
		$(".addSoftware[name='soft.softwareName']").parent().parent().addClass("error");
		$(".addSoftware[name='soft.softwareName']").parent().parent().find(".help-inline").html("请输入软件名称！");
	}else{
		$(".addSoftware[name='soft.softwareName']").parent().parent().removeClass("error");
		$(".addSoftware[name='soft.softwareName']").parent().parent().find(".help-inline").html("");
	}
	var pictureName = $.trim($("#pictureName").val());
	if(pictureName==""){
		isTrue = false;
		$(".addSoftware[id='pictureName']").parent().parent().addClass("error");
		$(".addSoftware[id='pictureName']").parent().parent().find(".help-inline").html("请选择软件图片！");
	}else{
		$(".addSoftware[id='pictureName']").parent().parent().removeClass("error");
		$(".addSoftware[id='pictureName']").parent().parent().find(".help-inline").html("");
	}
	var classifyId = $.trim($("#appClassifyHidden").val());
	if(classifyId==""){
		isTrue = false;
		$(".addSoftware[name='soft.classify']").parent().parent().addClass("error");
		$(".addSoftware[name='soft.classify']").parent().parent().find(".help-inline").html("请选择软件分类！");
	}else{
		$(".addSoftware[name='soft.classify']").parent().parent().removeClass("error");
		$(".addSoftware[name='soft.classify']").parent().parent().find(".help-inline").html("");
	}
	var description = $.trim($("#description").val());
	if(description==""){
		isTrue = false;
		$(".addSoftware[name='soft.description']").parent().parent().addClass("error");
		$(".addSoftware[name='soft.description']").parent().parent().find(".help-inline").html("请输入软件描述！");
	}else{
		$(".addSoftware[name='soft.description']").parent().parent().removeClass("error");
		$(".addSoftware[name='soft.description']").parent().parent().find(".help-inline").html("");
	}
	var intro = $.trim($("#intro").val());
	if(intro==""){
		isTrue = false;
		$(".addSoftware[name='soft.intro']").parent().parent().addClass("error");
		$(".addSoftware[name='soft.intro']").parent().parent().find(".help-inline").html("请输入软件简介！");
	}else{
		$(".addSoftware[name='soft.intro']").parent().parent().removeClass("error");
		$(".addSoftware[name='soft.intro']").parent().parent().find(".help-inline").html("");
	}
	var dataNum = $.trim($("#dataNum").val());
	if(dataNum==""){
		isTrue = false;
		$(".addSoftware[name='soft.dataNum']").parent().parent().addClass("error");
		$(".addSoftware[name='soft.dataNum']").parent().parent().find(".help-inline").html("请输入运行数据条数！");
	}else{
		$(".addSoftware[name='soft.dataNum']").parent().parent().removeClass("error");
		$(".addSoftware[name='soft.dataNum']").parent().parent().find(".help-inline").html("");
	}
	var screenshot = $.trim($("#screenShotAllHidden").val());
	if(screenshot==""){
		isTrue = false;
		$(".addSoftware[name='soft.screenShot']").parent().parent().addClass("error");
		$(".addSoftware[name='soft.screenShot']").parent().parent().find(".help-inline").html("请选择软件截图！");
	}else{
		$(".addSoftware[name='soft.screenShot']").parent().parent().removeClass("error");
		$(".addSoftware[name='soft.screenShot']").parent().parent().find(".help-inline").html("");
	}
	var dataFormat = $.trim($("#fileFormatHidden").val());
	if(dataFormat==""){
		isTrue = false;
		$(".addSoftware[name='soft.formatDesc']").parent().parent().addClass("error");
		$(".addSoftware[name='soft.formatDesc']").parent().parent().find(".help-inline").html("请选择运行文件格式！");
	}else{
		$(".addSoftware[name='soft.formatDesc']").parent().parent().removeClass("error");
		$(".addSoftware[name='soft.formatDesc']").parent().parent().find(".help-inline").html("");
	}
	
	return isTrue;
}
function amchars(){
	var chart;
	var chartData;
	$.get("softwareJson_getSoftRunStatistic.action",{},function(data){
		chartData = data;
	    // SERIAL CHART
	    chart = new AmCharts.AmSerialChart();
	    chart.dataProvider = chartData;
	    chart.categoryField = "softwareName";
	    chart.marginRight = 0;
	    chart.marginTop = 20;    
	    chart.autoMarginOffset = 100;
	    // the following two lines makes chart 3D
	    chart.depth3D = 5;
	    chart.angle = 30;

	    // AXES
	    // category
	    var categoryAxis = chart.categoryAxis;
	    categoryAxis.tile = "软件名称";
	    categoryAxis.labelRotation = 30;
	    categoryAxis.dashLength = 5;
	    categoryAxis.gridPosition = "start";

	    // value
	    var valueAxis = new AmCharts.ValueAxis();
	    valueAxis.title = "运行次数";
	    valueAxis.dashLength = 5;
	    chart.addValueAxis(valueAxis);

	    // GRAPH            
	    var graph = new AmCharts.AmGraph();
	    graph.valueField = "runNum";
//		    graph.colorField = "color";
	    graph.balloonText = "[[category]]: [[value]]";
	    graph.type = "column";
	    graph.lineAlpha = 0;
	    graph.fillAlphas = 1;
	    chart.addGraph(graph);

	    // WRITE
	    chart.write("chartdiv");
	});
}
//改变项目数据类型选项
function changeProDataType(){
	var type = $("#proDataTypeSel").val();
	$("#proDataTypeHidden").val(type);
}
//删除软件
function deleteSoft(id){
    jConfirm("确定删除该app吗？", '确认提示框', function(r) {
		if(r){
			$.get("softwareJson_deleteSoftWare.action",{"excess":new Date().getTime(),"soft.softwareId":id},function(flag){
				if(flag>0){
					init(1);
				}else if(flag==-1){
					jAlert("该app正在运行数据，不能删除");
				}else if(flag==0){
					jAlert("删除失败");
				}
			});
		}
	});
}
//打开新增页面
function toAddSoft(){
	$.get("goToAddSoftPage.action","",function(responseText){
		$("#softwareList").html(responseText);
		$("#appManageInfo").html("新增软件");
		$("#editTypeHidden").val("0");
	});
}
//保存新增软件页面
function addSoft(){
	if(!validateSoftForm()){
		return;
	}else{
		var editType = $.trim($("#editTypeHidden").val());
		var type = "add";
		if(editType!="0"){
			type = "edit";
		}
		var softwareName = $.trim($("#softwareName").val());
		var softId = $.trim($("#softwareIdHidden").val());
		if(softId==""){
			softId="0";
		}
		//从上线的app中检查是否有重名的app
		$.get("getSoftIdByNameOffLine.action",{"softwareId":softId,"softwareName":softwareName,"editType":type},function(softId){
			if(softId!=0){
				$(".addSoftware[name='soft.softwareName']").parent().parent().addClass("error");
				$(".addSoftware[name='soft.softwareName']").parent().parent().find(".help-inline").html("该软件名称已经存在！");
			}else{
				$(".addSoftware[name='soft.softwareName']").parent().parent().removeClass("error");
				$(".addSoftware[name='soft.softwareName']").parent().parent().find(".help-inline").html("");
				var params = $("#softAddForm").serialize();
				$.post("softwareJson_createSoftware.action",params,function(responseText){
					$("#addSoftDivInfo").attr("style","");
					if(responseText>0){
						$("#addSoftDivInfo").html("保存成功！");
					}else{
						$("#addSoftDivInfo").html("保存失败！");
					}
					window.scrollTo(0,0);
					setInterval(clearInfo,5000);
				});
			}
		});
	}
}

function clearInfo(){
	$("#addSoftDivInfo").html("");
	$("#addSoftDivInfo").attr("style","display:none;");
}

//打开编辑页面
function toEditSoft(id){
	$.get("goToAddSoftPage.action","",function(responseText){
		$("#softwareList").html(responseText);
		//文件格式
		appFileFormatFlag = 1;
		//截图
		appScreenShot = 1;
		//分类
		appClassifyFlag = 1;
		$("#appManageInfo").html("编辑软件");
		$("#editTypeHidden").val("1");
		$.get("soft_toEditSoft.action",{"excess":new Date().getTime(),"soft.softwareId":id},function(soft){
			//id
			$("#softwareIdHidden").val(soft.softwareId);
			//name
			$("#softwareName").val(soft.softwareName);
			//englishName
			$("#englishName").val(soft.englishName);
			//softwareEntry
			$("#softwareEntry").val(soft.softwareEntry);
			//processName
			$("#processName").val(soft.processName);
			//host
			$("#host").val(soft.host);
			//pictureImg
			$("#pictureImg").attr("src","/celloud/images/app/"+soft.pictureName);
			$("#pictureName").val(soft.pictureName);
			//软件分类
			var classify = soft.classify;
			if(classify!=null&&classify!=""){
				classify = classify.substring(0,classify.length-1);
				$.each(classify.split(";"),function(index,item){
					if(index==0){
						$("#classifySel1 option").each(function(){ 
							if($(this).text()==item){
								$("#classifySel1").val($(this).val());
							}
						});
					}else{
						addClassify();
						$("#classifySel"+appClassifyFlag+" option").each(function(){ 
							if($(this).text()==item){
								$("#classifySel"+appClassifyFlag).val($(this).val());
							}
						});
					}
					changeClassify();
				});
			}
			//description
			$("#description").val(soft.description);
			//intro
			$("#intro").val(soft.intro);
			//email
			$("#email").val(soft.email);
			//type
			$("#typeSel").val(soft.type);
			//runData
			var runData = soft.runData;
			$("input[name='soft.runData'][value='"+runData+"']").attr("checked",true);
			//dataNum
			$("#dataNum").val(soft.dataNum);
			//param
			var param = soft.param;
			$("input[name='soft.param'][value='"+param+"']").attr("checked",true);
			//ScreenShot
			var screenShot = soft.screenShot;
			if(screenShot!=null&&screenShot!=""){
				screenShot = screenShot.substring(0,screenShot.length-1);
				$.each(screenShot.split(";"),function(index,item){
					if(index==0){
						$("#screenImg1").attr("src","/celloud/images/screenshot/"+item);
						$("#screenNameInfo1").html(item);
					}else{
						addAppFile();
						$("#screenImg"+appScreenShot).attr("src","/celloud/images/screenshot/"+item);
						$("#screenNameInfo"+appScreenShot).html(item);
					}
					changeScreen();
				});
			}
			//format
			var format = soft.formatDesc;
			if(format!=null&&format!=""){
				format = format.substring(0,format.length-1);
				$.each(format.split(";"),function(index,item){
					if(index==0){
						$("#fileFormat1 option").each(function(){ 
							if($(this).text()==item){
								$("#fileFormat1").val($(this).val());
							}
						});
					}else{
						addFileFormat();
						$("#fileFormat"+appFileFormatFlag+" option").each(function(){ 
							if($(this).text()==item){
								$("#fileFormat"+appFileFormatFlag).val($(this).val());
							}
						});
					}
					changeFormat();
				});
			}
			//dataType
			$("#proDataTypeSel").val(soft.proDataType);
			//appDoc
			editor.setContent(soft.appDoc);
		});
	});
}
//打开软件详细信息页面
function toDetailSoft(id){
	$.get("soft_toDetailSoft.action",{"excess":new Date().getTime(),"soft.softwareId":id},function(responseText){
		$("#softwareList").html(responseText);
	});
}
//改变分类选择
function changeClassify(){
	var selVal = "";
	$("#appClassifyDiv select").each(function(){
		var oneVal = $(this).val();
		if(oneVal!=""){
			selVal+=oneVal+";";
		}
	});
	$("#appClassifyHidden").val(selVal);
}
//改变运行文件格式选择
function changeFormat(){
	var selVal = "";
	$("#formatDiv select").each(function(){
		var oneVal = $(this).val();
		if(oneVal!=""){
			selVal+=oneVal+";";
		}
	});
	$("#fileFormatHidden").val(selVal);
}
//新增运行文件格式
function addFileFormat(){
	appFileFormatFlag++;
	var div = $("#formatDiv");
	var sel = "<select id='fileFormat"+appFileFormatFlag+"' onchange='javascript:changeFormat();'></select>";
	var del = $("<a style='cursor: pointer;' id='delFormatBtn"+appFileFormatFlag+"'><i class='icon-minus'></i></a>");
	var br = $("<br id='fbr"+appFileFormatFlag+"'/>");
	div.append(sel);
	$("#fileFormat1 option").each(function(){ 
		$("#fileFormat"+appFileFormatFlag).append("<option value='"+$(this).val()+"'>"+$(this).text()+"</option>");
	});
	div.append(del);
	div.append(br);
	$("#delFormatBtn"+appFileFormatFlag).click(function(){
		$("#fileFormat"+appFileFormatFlag).remove();
		$("#delFormatBtn"+appFileFormatFlag).remove();
		$("#fbr"+appFileFormatFlag).remove();
		appFileFormatFlag--;
		changeFormat();
	});
}
//新增app分类
function addClassify(){
	appClassifyFlag++;
	var div = $("#appClassifyDiv");
	var sel = $("<select id='classifySel"+appClassifyFlag+"' class='addSoftware' onchange='javascript:changeClassify();'></select>");
	var del = $("<a style='cursor: pointer;' id='delClassifyBtn"+appClassifyFlag+"'><i class='icon-minus'></i></a>");
	var br = $("<br id='cbr"+appClassifyFlag+"'/>");
	div.append(sel);
	$("#classifySel1 option").each(function(){ 
		$("#classifySel"+appClassifyFlag).append("<option value='"+$(this).val()+"'>"+$(this).text()+"</option>");
	});
	div.append(del);
	div.append(br);
	$("#delClassifyBtn"+appClassifyFlag).click(function(){
		$("#classifySel"+appClassifyFlag).remove();
		$("#delClassifyBtn"+appClassifyFlag).remove();
		$("#cbr"+appClassifyFlag).remove();
		appClassifyFlag--;
		changeClassify();
	});
}
//新增app截图
function addAppFile() {
	appScreenShot++;
	var div = $("#divScreenShot");
	var img = "<img alt='软件截图' id='screenImg"+appScreenShot+"' src='' style='width: 100px;height: 100px;'>";
	var input = $("<input type='file' name='file' id='uploadScreenshot"+appScreenShot+"' onchange='upload(this,1)'/>");
	var span = $("<span id='screenNameInfo"+appScreenShot+"' class='screenSpan' style='display:none;'></span>");
	var del = $("<a style='cursor: pointer;' id='delSceenBtn"+appScreenShot+"'>&nbsp;<i class='icon-minus'></i></a>");
	var hidden = $("<input type='hidden' id='screenHidden"+appScreenShot+"'/>");
	var br = $("<br id='br"+appScreenShot+"'/>");
	div.append(img);
	div.append(input);
	div.append(span);
	div.append(del);
	div.append(hidden);
	div.append(br);
	$("#delSceenBtn"+appScreenShot).click(function() {
		$("#uploadScreenshot"+appScreenShot).remove();
		$("#delSceenBtn"+appScreenShot).remove();
		$("#screenNameInfo"+appScreenShot).remove();
		$("#screenHidden"+appScreenShot).remove();
		$("#screenImg"+appScreenShot).remove();
		$("#br"+appScreenShot).remove();
		appScreenShot--;
		changeScreen();
	});
}
function changeScreen(){
	var hm = "";
	$(".screenSpan").each(function(){
		var spanHtml = $.trim($(this).html());
		if(spanHtml!=""){
			hm += spanHtml+";";
		}
	});
	$("#screenShotAllHidden").val(hm);
}
function showDetail(){
	if($("select[name='soft.state']").val()==1){
		$("#detail").css("display","");
	}else{
		$("#detail").css("display","none");
		$("input[name='soft.softwareEntry']").val("");
		$("input[name='soft.processName']").val("");
		$("input[name='soft.host']").val("");
	}
}
//推荐
function recommend(id){
	$.get("softwareRecommend.action",{"access":new Date().getTime(),"soft.softwareId":id},function(flag){
		if(flag){
			init(currentPage);
		}else{
			jAlert("推荐失败");
		}
	});	
}
//取消推荐
function noRecommend(id){
	$.get("softwareNoRecommend.action",{"access":new Date().getTime(),"soft.softwareId":id},function(flag){
		if(flag>0){
			init(currentPage);
		}else{
			jAlert("取消推荐失败");
		}
	});
}
//app下线
function softOffLine(softwareId,softwareName,flag){
	if(flag==0){//上线的时候判断该名称的app是否已经存在
		$.get("getSoftIdByNameOffLine.action",{"softwareName":softwareName,"editType":"edit"},function(softId){
			if(softId!=0){
				jAlert("软件" + softwareName + "已存在！");
			}else{
				$.get("offLineSoft.action",{"soft.softwareId":softwareId,"soft.offLine":flag},function(flag){
					if(flag>0){
						init(currentPage);
					}else{
						jAlert("操作失败");
					}
				});
			}
		});
	}else{
		$.get("offLineSoft.action",{"soft.softwareId":softwareId,"soft.offLine":flag},function(flag){
			if(flag>0){
				init(currentPage);
			}else{
				jAlert("操作失败");
			}
		});
	}
}
function initAddSoft(){
	getAllSubClassify();
	getFileFormatList();
	getProDataTypeList();
	$("#addSoftDivInfo").attr("style","display:none;");
	$("#addSoftDivInfo").html("");
}
//获取所有项目数据类型
function getProDataTypeList(){
	$("#proDataTypeSel").html("<option value=''>请选择数据类型</option>");
	$.get("dataType!getAllDataTypeList",{},function(typeList){
		$.each(typeList,function(index,item){
			$("#proDataTypeSel").append("<option value='"+item.typeId+"'>"+item.typeDesc+"</option>");
		});
	});
}
//获取所有文件格式
function getFileFormatList(){
	$("#fileFormat1").html("<option value=''>请选择文件格式</option>");
	$.get("getAllDataType.action",{},function(formatList){
		$.each(formatList,function(index,item){
			$("#fileFormat1").append("<option value='"+item.formatId+"'>"+item.formatDesc+"</option>");
		});
	});
}
//获取软件分类
function getAllSubClassify(){
	$("#classifySel1").html("<option value=''>请选择软件分类</option>");
	$.get("getAllSubClassifyList.action",{},function(classifyList){
		$.each(classifyList,function(index,item){
			$("#classifySel1").append("<option value='"+item.classifyId+"'>"+item.classifyName+"</option>");
		});
	});
}
function toSoftList(){
	init(currentPage);
}
function initEditSoft(){
	var classify = $("#classifyIdHidden").val();
	$("#classifyId").find("option").each(function(){
		if($(this).val()==classify){
			$(this).attr("selected","selected");
		}
	});
	var softwareType = $("#softwareTypeHidden").val();
	$("#softwareType").find("option").each(function(){
		if($(this).val()==classify){
			$(this).attr("selected","selected");
		}
	});
}
function toSoftList(){
	init(1);
}
/**
 * ============software end========
 */
 /**
  * ============user start========
  */
function initUser(){
	//获取用户列表
	$.get("user_getAllUserPageList.action",{},function(responseText){
		$("#userList").html(responseText);
	});
	$.get("company!getAll",function(response){
		var x = "<option value='0'>--请选择--</option>";
		for(var i=0;i<response.length;i++){
			x+="<option value="+response[i].companyId+">"+response[i].companyName+"</option>";
		}
		$("#cselect").html(x);
		$("#cselect1").html(x);
		$("#cselect2").html(x);
		$("#cselect3").html(x);
	});
	
	//保存用户
	$("#save").click(function(){
		if(!validateAddForm()){
			return;
		}else{
			//验证用户名重复问题
			var username = $.trim($("#add_username").val());
			$.get("user!checkUsername",{"username":username},function(flag){
				if(flag){
					$(".addUser[name='user.username']").parent().parent().addClass("error");
					$(".addUser[name='user.username']").parent().parent().find(".help-inline").html("该用户名已经存在！");
				}else{
					$(".addUser[name='user.username']").parent().parent().removeClass("error");
					$(".addUser[name='user.username']").parent().parent().find(".help-inline").html("");
					//验证邮箱
					var email = $.trim($("#add_email").val());
					if(email!=""){
						$.get("user!checkUserEmail",{"user.email":email},function(flag){
							if(flag){
								$(".addUser[name='user.email']").parent().parent().addClass("error");
								$(".addUser[name='user.email']").parent().parent().find(".help-inline").html("该邮箱已存在！");
							}else{
								$(".addUser[name='user.email']").parent().parent().removeClass("error");
								$(".addUser[name='user.email']").parent().parent().find(".help-inline").html("");
								var params = $("#userForm").serialize();
								$.post("userJson_addUser.action",params,function(responseText){
									if(responseText){
										$.get("user_getAllUserPageList.action",{},function(responseText){
											$("#addModal").modal("hide");
											$("#userList").html(responseText);
										});
									}else{
										jAlert("保存失败！");
									}
								});
							}
						});
					}else{
						var params = $("#userForm").serialize();
						$.post("userJson_addUser.action",params,function(responseText){
							if(responseText){
								$.get("user_getAllUserPageList.action",{},function(responseText){
									$("#addModal").modal("hide");
									$("#userList").html(responseText);
								});
							}else{
								jAlert("保存失败！");
							}
						});
					}
				}
			});
		}
	});
	
	$("#cancle").click(function(){
		$("#addModal").modal("hide");
		$("#sendEmail").modal("hide");
	});
	
	$("#editCancle").click(function(){
		$("#editModal").modal("hide");
	});
	
	//编辑用户
	$("#editSave").click(function(){
		if(!validateEditForm()){
			return;
		}else{
			var userId = $("#editUserIdHidden").val();
			var username = $.trim($("#edit_username").val());
			$.get("user!checkUsernameByUserId",{"user.userId":userId,"user.username":username},function(flag){
				if(flag){
					$(".editUser[name='user.username']").parent().parent().addClass("error");
					$(".editUser[name='user.username']").parent().parent().find(".help-inline").html("该用户名已经存在！");
				}else{
					$(".editUser[name='user.username']").parent().parent().removeClass("error");
					$(".editUser[name='user.username']").parent().parent().find(".help-inline").html("");
					//验证邮箱
					var email = $.trim($("#edit_email").val());
					if(email!=""){
						$.get("user!checkUserEmailByUserId",{"user.userId":userId,"user.email":email},function(flag){
							if(flag){
								$(".editUser[name='user.email']").parent().parent().addClass("error");
								$(".editUser[name='user.email']").parent().parent().find(".help-inline").html("该邮箱已存在！");
							}else{
								$(".editUser[name='user.email']").parent().parent().removeClass("error");
								$(".editUser[name='user.email']").parent().parent().find(".help-inline").html("");
								var params = $("#editForm").serialize();
								$.post("userJson_updateUser.action",params,function(responseText){
									if(responseText){
										$("#editModal").modal("hide");
										$.get("user_getAllUserPageList.action",{},function(responseText){
											$("#userList").html(responseText);
										});
									}else{
										jAlert("修改失败！");
									}
								});
							}
						});
					}else{
						var params = $("#editForm").serialize();
						$.post("userJson_updateUser.action",params,function(responseText){
							if(responseText){
								$("#editModal").modal("hide");
								$.get("user_getAllUserPageList.action",{},function(responseText){
									$("#userList").html(responseText);
								});
							}else{
								jAlert("修改失败！");
							}
						});
					}
				}
			});
		}
	});
	$("#send").click(function(){
		var isPass = true;
		$("#emailForm").find("input").each(function(){
			var email = $(this).val();
			if(checkEmail(email)){
				$(this).parent().parent().removeClass("error");
				$(this).parent().parent().find(".help-inline").html("");
			}else{
				isPass = false;
				$(this).parent().parent().addClass("error");
				$(this).parent().parent().find(".help-inline").html("邮箱格式不正确！");
			}
		});
		if(isPass){
			var params = $("#emailForm").serialize(); 
			$.get("user!checkEmail",params,function(flag){
				if(flag.indexOf("1")>=0){
					var error = flag.split(",");
					for ( var i = 0; i < error.length; i++) {
						if(error[i]==1){
							$("#email"+i).addClass("error");
							$("#email"+i).find(".help-inline").html("此邮箱已经添加！");
						}else{
							$("#email"+i).removeClass("error");
							$("#email"+i).find(".help-inline").html("");
						}
					}
				}else{
					alert("邮件发送成功");
					$("#sendEmail").modal("hide");
					$.get("user!sendEmail",params);
				}
			});
		}
	});
}
//编辑用户
function editUser(userId){
	$.get("user_getUserById.action",{"userId":userId},function(responseText){
		$("#edit").html(responseText);
		$("#editDselect").val($("#deptIdHidden").val());
		$("#editCselect").val($("#companyIdHidden").val());
		$("#editCAselect").val($("#CAIdHidden").val());
		$("#editModal").modal("show");
	});
}
//删除用户
function delUser(userId){
    jConfirm("删除该用户所有关于该用户信息将会被删除，确定删除该用户吗？", '确认提示框', function(r) {
		if(r){
			$.get("userJson_deleteUserByUserId.action",{"userId":userId},function(responseText){
				if(responseText){
					$.get("user_getAllUserPageList.action",{"page.currentPage":1,"page.pageSize":10},function(responseText){
						$("#userList").html(responseText);
					});
				}else{
					jAlert("删除失败！");
				}
			});
		}
	});
}
//翻页
function searchUser(currentPage){
	$.get("user_getAllUserPageList.action",{"page.currentPage":currentPage,"page.pageSize":10},function(responseText){
		$("#userList").html(responseText);
	});
}
//密码重置
function resetPwd(userId){
    jConfirm("密码将被还原为：celloud，确定重置密码吗？", '确认提示框', function(r) {
		if(r){
			$.get("userJson_userResetPwd.action",{"user.userId":userId,"password":"celloud"},function(responseText){
				if(responseText){
					jAlert("重置成功！");
				}else{
					jAlert("重置失败！");
				}
			});
		}
	});
}
function detailUser(remark){
	$("#userDetailDiv").html(remark);
	$("#detailModal").modal("show");
}

function closeDetailModal(){
	$("#detailModal").modal("hide");
}
function cchange(sid,rid){
	$.get("dept!getByComId",{"companyId":$("#"+sid).val()},function(response){
		var x = "<option value='0'>--请选择--</option>";
		for(var i=0;i<response.length;i++){
			x+="<option value="+response[i].deptId+">"+response[i].deptName+"</option>";
		}
		$("#"+rid).html(x);
	});
}
function addFile(divId) {
	var id = parseInt($("#emailForm").children().last().attr("id").replace("email",""))+1;
	var group = $("#"+divId);
	var div = $("<div class=\"control-group\" id=\"email"+id+"\"></div>");
	var label = $("<label class=\"control-label\">邮箱<font color=\"red\">*</font></label>");
	var controls = $("<div class=\"controls\"></div>");
	var input = $("<input type=\"text\" name=\"emailArray\"/>");
	var span = $("<span class=\"help-inline\"></span>");
	var spanEmpty = $("<span>&nbsp;&nbsp;</span>");
	var del = $("<a style='cursor: pointer;'><i class='icon-minus'></i></a>");
	
	group.append(div);
	div.append(label);
	div.append(controls);
	controls.append(input);
	controls.append(span);
	controls.append(spanEmpty);
	controls.append(del);
	del.bind('click',function(){
		$(this).parent().parent().remove();
	});
}
function clearClass(){
	$("#emailForm").find(".control-group").removeClass("error");
	$("#emailForm").find(".help-inline").html("");
	$("input[name='email']").val("");
	$("#userForm").find(".control-group").removeClass("error");
	$("#userForm").find(".help-inline").html("");
	$(".addUser").val("");
}
//验证addForm
function validateAddForm(){
	var isTrue = true;
	var username = $.trim($("#add_username").val());
	if(username==""){
		isTrue = false;
		$(".addUser[name='user.username']").parent().parent().addClass("error");
		$(".addUser[name='user.username']").parent().parent().find(".help-inline").html("请输入用户名！");
	}else{
		$(".addUser[name='user.username']").parent().parent().removeClass("error");
		$(".addUser[name='user.username']").parent().parent().find(".help-inline").html("");
	}
	var password = $.trim($("#add_password").val());
	if(password==""){
		isTrue = false;
		$(".addUser[name='user.password']").parent().parent().addClass("error");
		$(".addUser[name='user.password']").parent().parent().find(".help-inline").html("请输入密码！");
	}else{
		var numberRegex = /^[\d+]{6,16}$/;
		if(numberRegex.test(password)){
			isTrue = false;
			$(".addUser[name='user.password']").parent().parent().addClass("error");
			$(".addUser[name='user.password']").parent().parent().find(".help-inline").html("密码不能全为数字！");
		}else{
			var combineRegex = /^[a-zA-Z0-9_]{6,16}$/;
			if(!combineRegex.test(password)){
				isTrue = false;
				$(".addUser[name='user.password']").parent().parent().addClass("error");
				$(".addUser[name='user.password']").parent().parent().find(".help-inline").html("密码为6-16位的字母数字组合，也可以包含下划线_！");
			}else{
				$(".addUser[name='user.password']").parent().parent().removeClass("error");
				$(".addUser[name='user.password']").parent().parent().find(".help-inline").html("");
			}
		}
	}
	//确认密码
	var confirmPwd = $.trim($("#add_confirmPwd").val());
	if(confirmPwd==""){
		isTrue = false;
		$(".addUser[name='confirmPwd']").parent().parent().addClass("error");
		$(".addUser[name='confirmPwd']").parent().parent().find(".help-inline").html("请输入确认密码！");
	}else{
		if(confirmPwd!=password){
			isTrue = false;
			$(".addUser[name='confirmPwd']").parent().parent().addClass("error");
			$(".addUser[name='confirmPwd']").parent().parent().find(".help-inline").html("确认密码与密码不同！");
		}else{
			$(".addUser[name='confirmPwd']").parent().parent().removeClass("error");
			$(".addUser[name='confirmPwd']").parent().parent().find(".help-inline").html("");
		}
	}
	var email = $.trim($("#add_email").val());
	if(email!=""){
		if(!email.match(/^\w+((-\w+)|(\.\w+))*\@[A-Za-z0-9]+((\.|-)[A-Za-z0-9]+)*\.[A-Za-z0-9]+$/)){
			isTrue = false;
			$(".addUser[name='user.email']").parent().parent().addClass("error");
			$(".addUser[name='user.email']").parent().parent().find(".help-inline").html("邮箱格式不正确！");
		}else{
			$(".addUser[name='user.email']").parent().parent().removeClass("error");
			$(".addUser[name='user.email']").parent().parent().find(".help-inline").html("");
		}
	}else{
		$(".addUser[name='user.email']").parent().parent().removeClass("error");
		$(".addUser[name='user.email']").parent().parent().find(".help-inline").html("");
	}
	//手机号
	var cellPhone = $.trim($("#add_cellphone").val());
	if(cellPhone!=""){
		var mobileregex = /^[\d+]{11}$/;
		  if(!mobileregex.test(cellPhone)){
			  isTrue = false;
			  $(".addUser[name='user.cellPhone']").parent().parent().addClass("error");
			  $(".addUser[name='user.cellPhone']").parent().parent().find(".help-inline").html("手机号格式不正确！");
		  }else{
			  $(".addUser[name='user.cellPhone']").parent().parent().removeClass("error");
			  $(".addUser[name='user.cellPhone']").parent().parent().find(".help-inline").html("");
		  }
	}else{
		$(".addUser[name='user.cellPhone']").parent().parent().removeClass("error");
		$(".addUser[name='user.cellPhone']").parent().parent().find(".help-inline").html("");
	}
	
	//座机号
	var companyTel = $.trim($("#add_companyTel").val());
	if(companyTel!=""){
		if(companyTel.length<7||companyTel.length>12){
			isTrue = false;
			  $(".addUser[name='user.companyTel']").parent().parent().addClass("error");
			  $(".addUser[name='user.companyTel']").parent().parent().find(".help-inline").html("单位电话格式不正确！");
		}else{
			$(".addUser[name='user.companyTel']").parent().parent().removeClass("error");
			$(".addUser[name='user.companyTel']").parent().parent().find(".help-inline").html("");
		}
	}else{
		$(".addUser[name='user.companyTel']").parent().parent().removeClass("error");
		$(".addUser[name='user.companyTel']").parent().parent().find(".help-inline").html("");
	}	
	
	//角色
	var role = $.trim($("#add_role").val());
	if(role==""){
		isTrue = false;
		  $(".addUser[name='user.role']").parent().parent().addClass("error");
		  $(".addUser[name='user.role']").parent().parent().find(".help-inline").html("请选择用户角色！");
	}else{
		$(".addUser[name='user.role']").parent().parent().removeClass("error");
		$(".addUser[name='user.role']").parent().parent().find(".help-inline").html("");
	}
	return isTrue;
}
//验证editForm
function validateEditForm(){
	var isTrue = true;
	var username = $.trim($("#edit_username").val());
	if(username==""){
		isTrue = false;
		$(".editUser[name='user.username']").parent().parent().addClass("error");
		$(".editUser[name='user.username']").parent().parent().find(".help-inline").html("用户名不能为空！");
	}else{
		$(".editUser[name='user.username']").parent().parent().removeClass("error");
		$(".editUser[name='user.username']").parent().parent().find(".help-inline").html("");
	}
	var email = $.trim($("#edit_email").val());
	if(email!=""){
		if(!email.match(/^\w+((-\w+)|(\.\w+))*\@[A-Za-z0-9]+((\.|-)[A-Za-z0-9]+)*\.[A-Za-z0-9]+$/)){
			isTrue = false;
			$(".editUser[name='user.email']").parent().parent().addClass("error");
			$(".editUser[name='user.email']").parent().parent().find(".help-inline").html("邮箱格式不正确！");
		}else{
			$(".editUser[name='user.email']").parent().parent().removeClass("error");
			$(".editUser[name='user.email']").parent().parent().find(".help-inline").html("");
		}
	}
	//手机号
	var cellPhone = $.trim($("#edit_cellphone").val());
	if(cellPhone!=""){
		var mobileregex = /^[\d+]{11}$/;
		  if(!mobileregex.test(cellPhone)){
			  isTrue = false;
			  $(".editUser[name='user.cellPhone']").parent().parent().addClass("error");
			  $(".editUser[name='user.cellPhone']").parent().parent().find(".help-inline").html("手机号格式不正确！");
		  }else{
			  $(".editUser[name='user.cellPhone']").parent().parent().removeClass("error");
			  $(".editUser[name='user.cellPhone']").parent().parent().find(".help-inline").html("");
		  }
	}else{
		$(".editUser[name='user.cellPhone']").parent().parent().removeClass("error");
		  $(".editUser[name='user.cellPhone']").parent().parent().find(".help-inline").html("");
	}	
	
	//座机号
	var companyTel = $.trim($("#edit_companyTel").val());
	if(companyTel!=""){
		if(companyTel.length<7||companyTel.length>12){
			isTrue = false;
			  $(".editUser[name='user.companyTel']").parent().parent().addClass("error");
			  $(".editUser[name='user.companyTel']").parent().parent().find(".help-inline").html("单位电话格式不正确！");
		}else{
			$(".editUser[name='user.companyTel']").parent().parent().removeClass("error");
			$(".editUser[name='user.companyTel']").parent().parent().find(".help-inline").html("");
		}
	}else{
		$(".editUser[name='user.companyTel']").parent().parent().removeClass("error");
		$(".editUser[name='user.companyTel']").parent().parent().find(".help-inline").html("");
	}	
	//角色
	var role = $.trim($("#edit_role").val());
	if(role==""){
		isTrue = false;
		  $(".editUser[name='user.role']").parent().parent().addClass("error");
		  $(".editUser[name='user.role']").parent().parent().find(".help-inline").html("请选择用户角色！");
	}else{
		$(".editUser[name='user.role']").parent().parent().removeClass("error");
		$(".editUser[name='user.role']").parent().parent().find(".help-inline").html("");
	}
	return isTrue;
}
function checkEmail(email){
	if(email){
		if(!email.match(/^\w+((-\w+)|(\.\w+))*\@[A-Za-z0-9]+((\.|-)[A-Za-z0-9]+)*\.[A-Za-z0-9]+$/)){
			return false;
		}else{
			return true;
		}
	}
}
function searchUserByName(){
	var username = $.trim($("#search_username").val());
	$.get("user_getAllUserPageList.action",{"username":username},function(responseText){
		$("#userList").html(responseText);
	});
}	
 /**
  * ============user end========
  */
 /**
  * ============client start========
  */
	function getAllClient(){
		$.get("client!getAll",{},function(responseText){
			$("#clientList").html(responseText);
		});
	}
	function uploadClient(){
		$.ajaxFileUpload({
			type:"post",
			url:"file!addClient",
			secureuri:false,
			fileElementId: "file" ,//文件选择框的id属性
			dataType: 'json',        //服务器返回的格式，可以是json
			data : {"access":new Date().getTime(),"client.version":$("#version").val()},
			success: function (data,status){
				$.get("client!getAll",{},function(responseText){
					$("#clientList").html(responseText);
				});
			},
			error:function(data,status,e){
				$.get("client!getAll",{},function(responseText){
					$("#clientList").html(responseText);
				});
			}
		});
	}
 /**
  * ============client end========
  */
/**
 * ============dataOutput start========
 */
	function getAllUser(){
		$.get("userJson_getAllUserList",function(data){
			var json = "[";
			var len = data.length;
			for ( var i = 0; i < len; i++) {
				json += "{id:"+data[i].userId+",text:'"+data[i].username+"'}";
				if(i<len-1){
					json += ",";
				}
			}
			json += "]";
			$("#userChoose").select2({
				placeholder: "请选择用户",
				multiple:true,
				data:eval(json)
			});
		});
	}
	function outPutSubmit(){
		var userId = $("#userChoose").val();
		var start = $("#_searchDate").val();
		var end = $("#_endDate").val();
		$.get("dataJson_outPutData",{"userIds":userId,"start":start,"end":end},function(text){
			$("#downLink").css("display","");
			$("#downLink").attr("href","../file!download?downName="+text);
		});
	}
/**
 * ============dataOutput end========
 */
/**
 * ============fileUpload start========
 */
	var globalFileNum = 1;
	function clearPage(){
		$("#fileNumber").val("");
		if(globalFileNum>1){
			$("#fileDetailTbody").html("");
			globalFileNum = 1;
		}
		$("#dataKeyTable").attr("style","display:none;");
		$("#fileDetailTable").attr("style","display:none;");
	}
	//获取用户列表
	function getUserList(){
		$("#userNameSel1").html("<option value='0'>请选择所属用户</option>");
		$.get("userJson_getAllUserList.action",{},function(data){
			$.each(data,function(index,item){
				$("#userNameSel1").append("<option value='"+item.userId+"'>"+item.username+"</option>");
			});
		});
	}
	//第一步：提交文件个数
	function submitFileNum(){
		$("#fileDetailTbody").html("");
		globalFileNum = 1;
		var fileNum = $.trim($("#fileNumber").val());
		if(fileNum==""){
			$("#fileNumWarn").html("请输入文件个数！");
		}else{
			$("#fileNumWarn").html("");
			//验证是否为数字
			if((/^(\+|-)?\d+$/.test(fileNum)) && fileNum>0){
				$("#fileNumWarn").html("");
				$("#dataKeyTable").attr("style","");
				$("#fileDetailTable").attr("style","");
				if(fileNum>1){
					for(var i=0;i<fileNum-1;i++){
						globalFileNum++;
						$("#fileDetailTbody").append("<tr id='tr"+globalFileNum+"'>");
						$("#fileDetailTbody").append("</tr>");
						$("#tr"+globalFileNum).append("<td>原文件名<font color='red'>*</font></td>");
						$("#tr"+globalFileNum).append("<td><input type='text' id='bigFileName"+globalFileNum+"' maxlength='100' placeholder='最长100个字符'><span id='fileNameWarn"+globalFileNum+"' style='color: red;'></span></td>");
						$("#tr"+globalFileNum).append("<td>所属用户<font color='red'>*</font></td>");
						$("#tr"+globalFileNum).append("<td><select id='userNameSel"+globalFileNum+"'></select><span id='userNameWarn"+globalFileNum+"' style='color: red;'></span></td>");
						$("#userNameSel1 option").each(function(){ 
							$("#userNameSel"+globalFileNum).append("<option value='"+$(this).val()+"'>"+$(this).text()+"</option>");
						});
					}
				}
			}else{
				$("#fileNumWarn").html("请输入正整数！");
			}
		}
	}
	
	//添加大数据信息
	function addBigData(){
		var dataList = "";
		for(var i=1;i<=globalFileNum;i++){
			var fileName = $("#bigFileName"+i).val();
			var userId = $("#userNameSel"+i).val();
			dataList += fileName + "," + userId + ";"
		}
		$.get("file!readBigFile",{"dataList":dataList},function(data){
			$("#addBigDataPromptTr").attr("style","");
			if(data){
				$("#addBigDataPrompt").html("<font color='green'>"+data+"文件不存在，其余文件上传成功！</font>");
			}else{
				$("#addBigDataPrompt").html("<font color='green'>保存成功！</font>");
			}
			setInterval(clearInfo,5000);
		});
	}
	function clearInfo(){
		$("#addBigDataPromptTr").attr("style","display:none;");
		$("#addBigDataPrompt").html("");
	}
/**
 * ============fileUpload end========
 */
	
/**
 * ============log start========
 */
	function getData(){
		$.get("behavior!getBehaviorList",function(data){
			xyCharts(eval(data));
		});
	}
	function xyCharts(data){
		// XY Chart
	   	var chart = new AmCharts.AmXYChart();
	    chart.dataProvider = data;
	    chart.startDuration = 1.5;
	    chart.marginLeft = 10;
	    chart.marginTop = 10;

	    // AXES
	    // X
	    var xAxis = new AmCharts.ValueAxis();
	    xAxis.title = "登录次数";
	    xAxis.position = "left";
	    xAxis.autoGridCount = true;
	    chart.addValueAxis(xAxis);

	    // Y
	    var yAxis = new AmCharts.ValueAxis();
	    yAxis.title = "时刻";
	    yAxis.position = "bottom";
	    yAxis.autoGridCount = true;
	    chart.addValueAxis(yAxis);

	    // GRAPH
	    var graph = new AmCharts.AmGraph();
	    graph.valueField = 80;
	    graph.xField = "address";
	    graph.yField = "userName";
	    graph.lineAlpha = 0;
	    graph.bullet = "bubble";
	    graph.balloonText = "[[time]]: [[userName]]";
	    chart.addGraph(graph);

	    // WRITE                                
	    chart.write("showPic");
	}
/**
 * ============log end========
 */
/**
 * ============company&dept start========
 */
	var CID = null;
	function getDeptAll(cid){
		CID = cid;
		$.get("dept!getByComId",{"companyId":cid},function(response){
			var x = "";
			for(var i=0;i<response.length;i++){
				x+="<tr><td>"+response[i].deptId+"</td><td>"+response[i].deptName+"</td><td><a href='javascript:void(0)' onclick='updateDeptState("+response[i].deptId+",1)'>删除</a> |<a href='javascript:void(0)' onclick='toEditDept("+response[i].deptId+")'>编辑</a></td></tr>";
			}
			$("#deptBody").html(x);
			$("#deptBody").parents().css("display","");
		});
	}
	function toEditDept(did){
		$("#editDeptForm").find("input").val("");
		$("#editDIcon").html("");
		$("#editDLogo").attr("src","");
		$.get("dept!getOne",{"deptId":did},function(dept){
			$("#editDeptForm").find("input[name='dept.deptId']").val(dept.deptId);
			$("#editDeptForm").find("input[name='dept.deptName']").val(dept.deptName);
			$("#editDeptForm").find("input[name='dept.englishName']").val(dept.englishName);
			$("#editDeptForm").find("input[name='dept.tel']").val(dept.tel);
			$("#editDeptForm").find("input[name='dept.deptIcon']").val(dept.deptIcon);
			$("#editDIcon").html(dept.deptIcon);
			$("#editDLogo").attr("src","../images/deptIcon/"+dept.deptIcon);
			$("#editDeptModal").modal("show");
		});
	}
	
	//修改部门
	function editDept(){
		$.get("dept!update",$("#editDeptForm").serialize(),function(flag){
			if(flag==1){
				alert("成功");
				$("#editDeptModal").modal("hide");
				getDeptAll(CID);
			}else{
				$("#hospitalErrorDiv").css("display","");
				$("#hospitalError").html("失败");
			}
		});
	}
	
	//编辑信息前的查询
	function toEdit(cid){
		$.get("company!getOne",{"company.companyId":cid},function(flag){
			$("#editHospital").html(flag);
			$("#editHospitalModal").modal("show");
		});
	}
	function updateDeptState(did,state){
		$.get("dept!updateState",{"dept.deptId":did,"dept.state":state},function(flag){
			if(flag==1){
				getDeptAll(CID);
			}else{
				alert("修改状态失败");
			}
		});
	}
	//删除及恢复公司状态
	function updateState(cid,state){
		$.get("company!updateState",{"company.companyId":cid,"company.state":state},function(flag){
			if(flag==1){
				getHospital(1);
			}else{
				alert("修改状态失败");
			}
		});
	}
	//修改公司信息
	function editHospital(){
		$.get("company!updateCompany",$("#editHospitalForm").serialize(),function(flag){
			if(flag==1){
				getHospital(1);
				$("#editHospitalModal").modal("hide");
			}else{
				alert("修改信息失败");
			}
		});
	}
	//新增公司信息
	function addHospital(){
		$.get("company!addCompany",$("#hospitalForm").serialize(),function(flag){
			if(flag==1){
				getHospital(1);
				$("#hospitalModal").modal("hide");
			}else{
				$("#hospitalErrorDiv").css("display","");
				$("#hospitalError").html("新增医院失败");
			}
		});
	}
	//新增部门
	function addDept(){
		$.get("dept!add",$("#deptForm").serialize(),function(flag){
			if(flag==1){
				alert("成功");
				$("#deptModal").modal("hide");
			}else{
				$("#hospitalErrorDiv").css("display","");
				$("#hospitalError").html("失败");
			}
		});
	}
	//上传图片
	function uploadImg(obj,type,isEdit){
		var id = obj.id;
		$.ajaxFileUpload({
			type:"post",
			url:"file!hospitalImage",
			secureuri:false,
			fileElementId: id ,//文件选择框的id属性
			dataType: 'json',        //服务器返回的格式，可以是json
			data : {"access":new Date().getTime(),"flag":type},
			success: function (data,status){
				if(isEdit==1){
					if(type==0){//医院 logo
						$("#editHIcon").html(data);
						$("input[name='company.companyIcon']").val(data);
						$("#editHLogo").attr("src","/celloud/images/hospitalIcon/"+data);
					}else{//科室logo
						$("input[name='dept.deptIcon']").val(data);
						$("#editDIcon").html(data);
						$("#editDLogo").attr("src","/celloud/images/deptIcon/"+data);
					}
				}else{
					if(type==0){//医院 logo
						$("input[name='company.companyIcon']").val(data);
						$("#hospitalLogo").attr("src","/celloud/images/hospitalIcon/"+data);
					}else{//科室logo
						$("input[name='dept.deptIcon']").val(data);
						$("#descriptionLogo").attr("src","/celloud/images/deptIcon/"+data);
					}
				}
			},
			error:function(data,status,e){
				alert("<font color='red'>此文件上传失败</font>");
			}
		});
	}
	function upload(obj,type){
		var id = obj.id;
		$.ajaxFileUpload({
			type:"post",
			url:"file!imageUpload",
			secureuri:false,
			fileElementId: id ,//文件选择框的id属性
			dataType: 'json',        //服务器返回的格式，可以是json
			data : {"access":new Date().getTime(),"flag":type},
			success: function (data,status){
				if(type==0){//app图标上传
					$("#"+id).prev().html(data);
					$("#pictureName").val(data);
					$("#pictureImg").attr("src","/celloud/images/app/"+data);
				}else{//app截图上传
					var num = id.substring(id.length-1);
					$("#screenNameInfo"+num).html(data);
					$("#screenHidden"+num).val(data);
					$("#screenImg"+num).attr("src","/celloud/images/screenshot/"+data);
					changeScreen();
				}
			},
			error:function(data,status,e){
				$("#"+id).prev().html("<font color='red'>此文件上传失败</font>");
			}
		});
	}
	function toAddHospital(){
		$("#hospitalModal").find("input").val("");
		$("#hospitalModal").modal("show");
	}
	function toAddDept(){
		$("#deptModal").find("select").val(0);
		$("#deptModal").find("input").val("");
		$("#deptModal").find("select[name='dept.companyId']").val(CID);
		$("#deptModal").modal("show");
	}
	function getHospital(page){
		$.get("company!getPageListCompany",{"page.currentPage":page,"page.pageSize":10},function(responseText){
			$("#main").html(responseText);
		});
	}
/**
 * ============company&dept end========
 */
/**
 * ============notice start========
 */
	function initNotice(){
		$("#save").click(function(){
			var params = $("#noticeForm").serialize();
			$.post("notice!addNotice",params,function(responseText){
				if(responseText){
					$.get("notice!getAllNotice",{},function(responseText){
						$("#noticeList").html(responseText);
						$("#addNotice").css("display","none");
						$("#noticemain").css("display","");
					});
				}
			});
		});
		$("#cancleNotice").click(function(){
			$("#addNotice").css("display","none");
			$("#noticemain").css("display","");
		});
		
		$("#editCancleNotice").click(function(){
			$("#editModal").css("display","none");
			$("#noticemain").css("display","");
		});
		
		$.get("notice!getAllNotice",{},function(responseText){
			$("#noticeList").html(responseText);
		});
		
		$("#editSave").click(function(){
			var params = $("#editForm").serialize();
			$.post("notice!editNotice",params,function(responseText){
				$.get("notice!getAllNotice",{},function(responseText){
					$("#noticeList").html(responseText);
					$("#editModal").css("display","none");
					$("#noticemain").css("display","");
				});
			});
		});
	}
	function addNotice(){
		$("#addNotice").css("display","");
		$("#noticemain").css("display","none");
	}
	function editNotice(noticeId){
		$.get("notice!getNoticeById",{"noticeId":noticeId},function(responseText){
			$("#edit").html(responseText);
			editor = new UE.ui.Editor({initialFrameWidth:900,initialFrameHeight:300});
			editor.render("editNotice");
			$("#editModal").css("display","");
			$("#noticemain").css("display","none");
		});
	}
/**
 * ============notice end========
 */
/**
 * ============report end========
 */
	function initReport(){
		$.get("softwareJson_getAllSoft.action",{},function(softList){
			for ( var i = 0; i < softList.length; i++) {
				$("#dataSoftSel").append("<option value='"+softList[i].softwareId+"'>"+softList[i].softwareName+"</option>");
				$("#proSoftSel").append("<option value='"+softList[i].softwareId+"'>"+softList[i].softwareName+"</option>");
			}
		});
	}
	function hideProInfo(){
		$("#proAlertInfo").attr("style","display: none;");	
	}
	function hideDataInfo(){
		$("#dataAlertInfo").attr("style","display: none;");	
	}
	//修改项目状态
	function saveProReport(){
		var proId = $("#proId").val();
		var softId = $("#proSoftSel").val();
		var state = $("input[name='pst']:checked").val();
		if(proId==""||softId==""){
			return;
		}
		//根据softId获取softName
		$.get("getSoftwareNameById.action",{"softwareId":softId},function(softName){
			if(softName=="FastQC"){
				$.get("getPath.action",{},function(responseText){
					var toolsOutPath = responseText.split(",")[2] + "Procedure!updataState";
					$.get("getUserIdByProjectId.action",{"projectId":proId},function(userId){
						$.get("getDataInfoListByProjectId.action",{"projectId":proId},function(fileList){
							var dataKeyList = "";
							$.each(fileList,function(index,item){
								var ext = item.fileName.substring(item.fileName.indexOf("."));
								var data = item.dataKey + "," + item.dataKey + ext + "," + item.fileName + ";";
								dataKeyList += data;
							});
							$.get("getSoftwareIdByName.action",{"softwareName":softName},function(appId){
								$.get(toolsOutPath,{"userId":userId,"appId":appId,"projectId":proId,"dataKeyList":dataKeyList},function(responseText){
									if(responseText=="success"){
										$.get("updateReportByDataKeyProjectId.action",{"softwareId":softId,"state":state,"projectId":proId},function(flag){
											$("#proAlertInfo").attr("style","");
											if(flag>0){
												$("#proAlertInfo").html("保存成功！");
											}else{
												$("#proAlertInfo").html("保存失败！");
											}
											setTimeout("hideProInfo()",2000);
										});
									}else{
										jBox.alert("FastQC项目报告创建失败！");
									}
								});
							});
						});
					});
				});
			}else{
				$.get("updateReportByProSoftId.action",{"projectId":proId,"softwareIds":softId,"state":state},function(flag){
					$("#proAlertInfo").attr("style","");	
					if(flag>0){
						$("#proAlertInfo").html("保存成功！");
					}else{
						$("#proAlertInfo").html("保存失败！");
					}
					setTimeout("hideProInfo()",2000);
				});
			}
		});
	}
	//修改数据状态
	function saveDataReport(){
		var dataKey = $("#dataKey").val();
		var softId = $("#dataSoftSel").val();
		var state = $("input[name='dst']:checked").val();
		if(dataKey==""||softId==""){
			return;
		}
		$.get("getSoftwareNameById.action",{"softwareId":softId},function(softName){
			if(softName=="FastQC"){
				$.get("getPath.action",{},function(responseText){
					var toolsOutPath = responseText.split(",")[2] + "Procedure!updataState";
					$.get("getFileNameByDataKey.action",{"dataKey":dataKey},function(fileName){
						var ext = fileName.substring(fileName.indexOf("."));
						var dataKeyList = dataKey + "," + dataKey + ext + "," + fileName + ";";
						$.get("getUserIdBySoftIdDataKey.action",{"softwareId":softId,"dataKeys":dataKey},function(userId){
							$.get(toolsOutPath,{"appId":softId,"dataKeyList":dataKeyList,"userId":userId,"projectId":""},function(responseText){
								if(responseText=="success"){
									$.get("updateReportByDataKeySoftId.action",{"dataKeys":dataKey,"softwareId":softId,"state":state},function(flag){
										$("#dataAlertInfo").attr("style","");	
										if(flag>0){
											$("#dataAlertInfo").html("保存成功！");
										}else{
											$("#dataAlertInfo").html("保存失败！");
										}
										setTimeout("hideDataInfo()",2000);
									});
								}
							});
						});
					});
				});
			}else{
				$.get("updateReportByDataKeySoftId.action",{"dataKeys":dataKey,"softwareId":softId,"state":state},function(flag){
					$("#dataAlertInfo").attr("style","");	
					if(flag>0){
						$("#dataAlertInfo").html("保存成功！");
					}else{
						$("#dataAlertInfo").html("保存失败！");
					}
					setTimeout("hideDataInfo()",2000);
				});
			}
		});
	}
/**
 * ============report end========
 */
/**
*===================  count start ==========================
*/
	function initAnalysis(){
		$.get("analysis!getUserList",function(response){
			clearShow();
			$("#userDataList").html(response);
			$("#userDataList").css("display","");
			$.get("analysis!getUserDataList",function(result){
				$("#countUserAll").html(result);
				$("#countUserAll").css("display","");
			});
		});
	}
	
	function getUserData(userId){
		$.get("analysis!getUserData",{"userId":userId},function(response){
			clearShow();
			$("#everyUser").html(response);
			$("#everyUser").css("display","");
		});
	}
	function getUserMounthData(userId,mounth){
		$.get("analysis!getDataList",{"userId":userId,"mounth":mounth},function(response){
			clearShow();
			$("#everyMounth").html(response);
			$("#everyMounth").css("display","");
		});
	}
	function clearShow(){
		$("#userDataList").css("display","none");
		$("#countUserAll").css("display","none");
		$("#everyUser").css("display","none");
		$("#everyMounth").css("display","none");
	}
	
	function toBack(flag){
		if(flag == 0){
			$("#everyUser").css("display","none");
			$("#userDataList").css("display","");
			$("#countUserAll").css("display","");
		}else{
			$("#everyMounth").css("display","none");
			$("#everyUser").css("display","");
		}
	}
	
	/**
	*===================  count end ==========================
	*/
	/**
	 * ====================email start===========================
	 */
	function initEmail(){
		$.get("userJson_getAllUserList",function(response){
			var x = "<table><tr>";
			var mod = 0;
			for(var i=0;i<response.length;i++){
				if(i%5==mod){
					x+="</tr><tr>";
				}
				u = response[i];
				if(u.email==null||$.trim(u.email)==""){
					mod ++;
				}else{
					x+="<td><input type=\"checkbox\" name=\"emailArray\" value=\""+u.email+"\" />"+u.username+"</td>";
				}
			}
			x+="</tr></table>";
			$("#emailList").html(x);
		});
	}
	function selectAll(){
		$("input[name='emailArray']").prop('checked',true);
	}
	function selectNone(){
		$("input[name='emailArray']").prop('checked',false);
	}
	function selectOthers(){
		$("input[name='emailArray']").each(function(){
			$(this).prop("checked", !$(this).prop("checked"));  
		});	
	}
	function sendEmail(){
		$.post("../user!sendEmailToUser",$("#emailForm").serialize(),function(flag){
			alert("邮件发送结束");
		});
	}