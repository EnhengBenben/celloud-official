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

/**
 * zopIm 聊天插件
 */
window.$zopim || (function(d, s) {
	var z = $zopim = function(c) {
		z._.push(c)
	}, $ = z.s = d.createElement(s), e = d.getElementsByTagName(s)[0];
	z.set = function(o) {
		z.set._.push(o)
	};
	z._ = [];
	z.set._ = [];
	$.async = !0;
	$.setAttribute('charset', 'utf-8');
	$.src = '//v2.zopim.com/?2Dh23fvDcnb1ijOwG7PsEZCcrp8sutR1';
	z.t = +new Date;
	$.type = 'text/javascript';
	e.parentNode.insertBefore($, e)
})(document, 'script');
