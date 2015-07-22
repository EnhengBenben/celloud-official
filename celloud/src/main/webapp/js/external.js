function CreateScript(file){  
	var new_element;  
	new_element=document.createElement("script");  
	new_element.setAttribute("type","text/javascript");  
	new_element.setAttribute("src",file);  
	document.body.appendChild(new_element);
} 
/**
 * 百度统计
 */
CreateScript("plugins/baidu.js");

/**
 * 检测浏览器版本号
 */
$(document).ready(function(){
	if($.browser.msie) { 
		var version = $.browser.version;
		if(version=="6.0"||version=="7.0"){
			$(".alert").attr("style","");
		}
	}
});
function closeVserionDiv(){
	$(".alert").attr("style","display:none;");
}
