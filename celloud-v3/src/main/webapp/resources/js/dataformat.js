/**
 * str:需要处理的字符串
 * step:切割标准
 * splitor:分隔符
 * 
 * 例如：
 * var eg = "12345678";
 * $.format(eg, 3, ',')
 * 
 * 结果：12,345,678
 */
;(function($){$.extend({format:function(str,step,splitor){str=str.toString();var len=str.length;if(len>step){var l1=len%step,l2=parseInt(len/step),arr=[],first=str.substr(0,l1);if(first!=''){arr.push(first);};for(var i=0;i<l2;i++){arr.push(str.substr(l1+i*step,step));};str=arr.join(splitor);};return str;}});})(jQuery);