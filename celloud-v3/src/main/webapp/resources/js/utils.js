var utils = {};
/**
 * Data Format
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

utils._stopDefault = function(e){
  if(e && e.preventDefault) {
    e.preventDefault();  
  } else {
    window.event.returnValue = false;   
  }  
  return false;  
}
/**
 * 获取文件后缀
 */
utils.getExt = function(fileName){
  var result =/\.[^\.]+/.exec(fileName);
  return result;
};

/**
 * 判断配置文件
 */
utils.isConfigure = function(fileName){
  if(utils.getExt(fileName)==".txt" || utils.getExt(fileName)==".lis"){
    return true;
  }else {
    return false;
  }
};

//根据视口和文档的宽高设置背景图片的尺寸
function setDocSize(){
	var winWidth = $(window).width();
	var docWidth = $(document).width();
	var winHeight = $(window).height();
	var docHeight = $(document).height();
	//设置宽度
	if(winWidth >= docWidth){
		$(".bgContainer").width(winWidth);
		$("#bg").width(winWidth);
		$(".wrapper").width(winWidth);
	}else{
		$(".bgContainer").width(docWidth);
		$("#bg").width(docWidth);
		$(".wrapper").width(docWidth);
	}
	//设置高度
	if(winHeight >= docHeight){
		$(".bgContainer").height(winHeight);
		$("#bg").height(winHeight);
		$(".wrapper").height(winHeight);
	}else{
		$(".bgContainer").height(docHeight);
		$("#bg").height(docHeight);
		$(".wrapper").height(docHeight);
	}
	$(".bgContainer").css("overflow","hidden");
}

//判断浏览器是否支持 placeholder属性
function isPlaceholder(){
    var input = document.createElement('input');
    return 'placeholder' in input;
}
if (!isPlaceholder()) {//不支持placeholder 用jquery来完成
    $(document).ready(function() {
        if(!isPlaceholder()){
            $("input").not("input[name='password']").each(//把input绑定事件 排除password框
                function(){
                    if($(this).val()=="" && $(this).attr("placeholder")!=""){
                        $(this).val($(this).attr("placeholder"));
                        $(this).addClass("placeholder");
                        $(this).focus(function(){
                            if($(this).val()==$(this).attr("placeholder")) $(this).val("");
                        });
                        $(this).blur(function(){
                            if($(this).val()=="") $(this).val($(this).attr("placeholder"));
                        });
                    }
            });
            //对password框的特殊处理1.创建一个text框 2获取焦点和失去焦点的时候切换
            var pwdField    = $("input[type=password]");
            var pwdVal      = pwdField.attr('placeholder');
            pwdField.after('<input id="pwdPlaceholder" type="text" class="pwd" value='+pwdVal+' autocomplete="off" />');
            var pwdPlaceholder = $('#pwdPlaceholder');
            pwdPlaceholder.show();
            pwdField.hide();
            
            pwdPlaceholder.focus(function(){
                pwdPlaceholder.hide();
                pwdField.show();
                pwdField.focus();
            });
            
            pwdField.blur(function(){
                if(pwdField.val() == '') {
                    pwdPlaceholder.show();
                    pwdField.hide();
                }
            });
        }
    });
}