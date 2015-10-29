//默认打开按功能分类
var classifyPid = 1;
var classifySid = 0;
function initApp(){
	getAppClassify(classifyPid);
}
function getAppClassify(pid){
	$("#toAllApp").removeClass("bg-green-active");
	$("#toMyApp").addClass("bg-green-active");
	classifyPid = pid;
	$.get("app3!getAppClassify",{"conditionInt":pid},function(responseText){
		$("#appClassify").html(responseText);
		classifySid = 0;
		getAppList(classifyPid,0);
		$("#sclassify0").click(function(){
			$("li[name='sclassify']").removeClass("selected");
			$("#sclassify0").addClass("selected");
			$.get("app3!getAppListPage",{"paramId":pid,"conditionInt":0},function(responseText){
				$("#appDetail").html(responseText);
			});
		});
	});
}
function getAppList(pid,isparent){
	if(isparent == 0){
		classifyPid = pid;
		$("li[name='pclassify']").removeClass("selected");
		$("#pclassify"+classifyPid.toString()).addClass("selected");
	}else{
		classifySid = pid;
	}
	$("li[name='sclassify']").removeClass("selected");
	$("#sclassify"+classifySid.toString()).addClass("selected");
	$.get("app3!getAppListPage",{"paramId":pid,"conditionInt":isparent},function(responseText){
		$("#appDetail").html(responseText);
	});
}
function toMyAppList(){
	$("#toMyApp").removeClass("bg-green-active");
	$("#toAllApp").addClass("bg-green-active");
	$.get("app3!getMyApp",{},function(responseText){
		$("#appClassify").html("");
		$("#appDetail").html(responseText);
	});
}
function toAppDetail(id){
	$.get("app3!getAppById",{"paramId":id},function(responseText){
		$("#modelAppDetail").html(responseText);
		$("#appDetailModel").modal('show');
	});
}