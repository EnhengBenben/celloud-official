//默认打开按功能分类
var classifyPid = 1;
var classifySid = 0;
function initApp(){
	getAppClassify(classifyPid);
}
function getAppClassify(pid){
	$("#toAllApp").addClass("bg-green-active");
	$("#subtitle").html("全部APP");
	$("#toMyApp").removeClass("bg-green-active");
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
	$("#toMyApp").addClass("bg-green-active");
	$("#subtitle").html("已添加APP");
	$("#toAllApp").removeClass("bg-green-active");
	getMyApp();
}
function getMyApp(){
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
function addApp(id){
	$.get("app3!userAddApp",{"paramId":id},function(resultInt){
		if(resultInt>0){
			$("#toAddApp").attr("onclick","removeApp("+id+")");
			$("#toAddApp").html("<i class=\"fa fa-minus\"></i>&nbsp;取消添加");
			$("#toAddApp").removeClass("btn-celloud-success").addClass("btn-celloud-close");
			if($("#toMyApp").hasClass("bg-green-active")){
				getMyApp();
			}
		}
	});
}
function removeApp(id){
	$.get("app3!userRemoveApp",{"paramId":id},function(resultInt){
		if(resultInt>0){
			if($("#toMyApp").hasClass("bg-green-active")){
				$("#appDetailModel").modal('hide');
				getMyApp();
			}else{
				$("#toAddApp").attr("onclick","addApp("+id+")");
				$("#toAddApp").html("<i class=\"fa fa-plus\"></i>&nbsp;添加");
				$("#toAddApp").removeClass("btn-celloud-close").addClass("btn-celloud-success");
			}
		}
	});
}