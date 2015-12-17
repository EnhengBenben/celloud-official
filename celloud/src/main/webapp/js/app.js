//默认打开按功能分类
var classifyPid = 1;
var classifySid = 0;
var introNext = 0;
function initApp(){
	getMyApp();
	var pid = $("#defaultPid").val();
	var pname = $("#defaultPname").val();
	if(hasNavi == 1 && intro != null){
		intro.exit();
		intro = null;
		intro = introJs();
		intro.setOption('tooltipPosition', 'bottom');
		intro.setOption('showStepNumbers', false);
		intro.setOption('showButtons', false);
		intro.start();
		intro.goToStep(2);
	}
	toSclassifyApp(pid,pname);
}
function toSclassifyApp(pid,pname){
	$("#secondClassifyName").parent().addClass("hide");
	$("#appClassifyUl li").removeClass("active");
	$("#classifypidLi"+pid).addClass("active");
	$.get("app3!toSclassifyApp",{"paramId":pid},function(responseText){
		$("#sclassify").html(responseText);
		if(hasNavi == 1 && intro != null && introNext==1){
			intro.exit();
			intro = null;
			intro = introJs();
			intro.setOption('tooltipPosition', 'left');
			intro.setOption('showStepNumbers', false);
			intro.setOption('showButtons', false);
			intro.start();
			intro.goToStep(3);
		}
		introNext = 1;
	});
	$("#defaultPid").val(pid);
	$("#defaultPname").val(pname);
}
function toMoreApp(pid,sid,pageNum,isParent){
	currentPage = pageNum;
	$("#rootClassifyName").unbind("click");
	$("#secondClassifyName").unbind("click");
	$.get("app3!toMoreAppList",{"classifyId":sid,"classifyPid":pid,"classifyFloor":isParent,"page.pageSize":pageAppNum,"page.currentPage":currentPage,"condition":sortFiled,"type":sortType},function(responseText){
		$("#rootClassifyName").html($("#pid"+pid).html());
		$("#secondClassifyName").html($("#sid"+sid).html());
		$("#appMain").html(responseText);
		$("#rootClassifyName").bind("click",function(){toMoreApp(pid,0,1,0);});
		if($("#sid"+sid).html()=="全部"){
			$("#secondClassifyName").bind("click",function(){alert("--1--"); toMoreApp(pid,0,1,0);});
		}else{
			$("#secondClassifyName").bind("click",function(){alert("--2--"); toMoreApp(pid,sid,pageNum,isParent);});
		}
		$("#secondClassifyName").parent().removeClass("hide");
		$("#sortByCreateDate").bind("click",function(){
            sortFiled = "s.create_date";
            toMoreApp(pid,sid,1,isParent);
		});
		sortApp();
		sortFiled = "";
		if(hasNavi == 1 && intro != null){
			intro.exit();
			intro = null;
			intro = introJs();
			intro.setOption('tooltipPosition', 'auto');
			intro.setOption('positionPrecedence', ['left', 'right', 'bottom', 'top']);
			intro.setOption('showStepNumbers', false);
			intro.setOption('showButtons', false);
			intro.start();
			intro.goToStep(2);
		}
	});
}
function sortApp(){
	$("#sort-listUl li").removeClass("current");
	if(sortFiled == "s.create_date"){
		$("#sortByCreateDate").addClass("current");
		if(sortType == "desc"){
			$("#sortByCreateDate i").removeClass("up").addClass("down");
			sortType = "asc";
		}else{
			$("#sortByCreateDate i").removeClass("down").addClass("up");
			sortType = "desc";
		}
	}else{
		$("#defaultSort").addClass("current");
	}
}
function toAppDetail(id){
	$.get("app3!getAppById",{"paramId":id},function(responseText){
		$("#appMain").html(responseText);
		if(hasNavi == 1 && intro != null){
			intro.exit();
			intro = null;
			intro = introJs();
			intro.setOption('tooltipPosition', 'auto');
			intro.setOption('positionPrecedence', ['left', 'right', 'bottom', 'top']);
			intro.setOption('showStepNumbers', false);
			intro.setOption('showButtons', false);
			intro.start();
			intro.goToStep(2);
		}
	});
}
function getMyApp(){
	$.get("app3!getMyAppList",{},function(responseText){
		$("#myAppDiv").html(responseText);
	});
}
function addApp(id){
	$.get("app3!userAddApp",{"paramId":id},function(responseText){
		if(responseText.length>0){
			$("#toAddApp").attr("onclick","removeApp("+id+")");
			$("#toAddApp").html("<i class=\"fa fa-minus\"></i>&nbsp;取消添加");
			$("#toAddApp").removeClass("btn-celloud-success").addClass("btn-celloud-close");
			$("#myAppDiv").html(responseText);
			if(hasNavi == 1 && intro != null){
				$("#toUploadMenu").attr("data-step",3);
				intro.exit();
				intro = null;
				intro = introJs();
				intro.setOption('tooltipPosition', 'auto');
				intro.setOption('positionPrecedence', ['left', 'right', 'bottom', 'top']);
				intro.setOption('showStepNumbers', false);
				intro.setOption('showButtons', false);
				intro.start();
				intro.goToStep(3);
			}
		}
	});
}
function removeApp(id){
	$.get("app3!userRemoveApp",{"paramId":id},function(responseText){
		if(responseText.length>0){
			$("#toAddApp").attr("onclick","removeApp("+id+")");
			$("#toAddApp").html("<i class=\"fa fa-minus\"></i>&nbsp;取消添加");
			$("#toAddApp").removeClass("btn-celloud-success").addClass("btn-celloud-close");
			$("#myAppDiv").html(responseText);
			if(hasNavi == 1 && intro != null){
				$("#toUploadMenu").attr("data-step",3);
				intro.exit();
				intro = null;
				intro = introJs();
				intro.setOption('tooltipPosition', 'auto');
				intro.setOption('positionPrecedence', ['left', 'right', 'bottom', 'top']);
				intro.setOption('showStepNumbers', false);
				intro.setOption('showButtons', false);
				intro.start();
				intro.goToStep(3);
			}
		}
	});
}