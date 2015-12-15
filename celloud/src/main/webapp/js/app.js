//默认打开按功能分类
var classifyPid = 1;
var classifySid = 0;
function initApp(){
	getMyApp();
	var pid = $("#defaultPid").val();
	var pname = $("#defaultPname").val();
	toSclassifyApp(pid,pname);
	$("#appSubtitle").bind("click",function(){
		toSclassifyApp($("#defaultPid").val(),$("#defaultPname").val());
	});
}
function toSclassifyApp(pid,pname){
	$("#secondClassifyName").parent().addClass("hide");
	$.get("app3!toSclassifyApp",{"paramId":pid},function(responseText){
		$("#sclassify").html(responseText);
	});
	$("#defaultPid").val(pid);
	$("#defaultPname").val(pname);
}
function toMoreApp(pid,sid,pageNum,isParent){
	currentPage = pageNum;
	$.get("app3!toMoreAppList",{"classifyId":sid,"classifyPid":pid,"classifyFloor":isParent,"page.pageSize":pageAppNum,"page.currentPage":currentPage,"condition":sortFiled,"type":sortType},function(responseText){
		$("#appMain").html(responseText);
		$("#rootClassifyName").html($("#pid"+pid).html());
		$("#rootClassifyName").bind("click",function(){ toMoreApp(pid,0,1,0);});
		$("#secondClassifyName").html($("#sid"+sid).html());
		$("#secondClassifyName").bind("click",function(){ toMoreApp(pid,sid,pageNum,isParent);});
		$("#secondClassifyName").parent().removeClass("hide");
		$("#sortByCreateDate").bind("click",function(){
            sortFiled = "s.create_date";
            toMoreApp(pid,sid,1,isParent);
		});
		sortApp();
		sortFiled = "";
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
		}
	});
}