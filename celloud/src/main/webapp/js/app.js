//默认打开按功能分类
var classifyPid = 1;
function initApp(){
	$.get("app3!toAppHome",{"conditionInt":classifyPid},function(responseText){
		$("#appDetail").html(responseText);
		$("#pclassify"+classifyPid).addClass("selected");
	})
}