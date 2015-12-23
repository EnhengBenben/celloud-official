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
