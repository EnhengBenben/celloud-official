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
 * 时间格式化
 *
 * @method formatDate
 * @static
 * @param {Number} size Size to format as string.
 * @return {String} Formatted size string.
 */
utils.formatDate =function(size) {
  var ss = parseInt(size);// 秒
  if (ss === 0){
    return "00:00:00";
  } 
  if (/\D/.test(ss)) {
    return 'N/A';
  }
  ss = parseInt(size)
  var radix = 60;
    var mm = 0;// 分
    var hh = 0;// 小时
    if(ss > radix) {
        mm = parseInt(ss/radix);
        ss = parseInt(ss%radix);
        if(mm > radix) {
            hh = parseInt(mm/radix);
            mm = parseInt(mm%radix);
        }
    }
    if(hh > 0 && hh <10) {
      result = "0"+parseInt(hh);
    }else if(hh>=10){
      result = parseInt(hh);
    }else if(hh<=0){
      result = "00";
    }
    result += ":";
    if(mm > 0 && mm <10) {
      result += "0"+parseInt(mm);
    }else if(mm>=10){
      result += parseInt(mm);
    }else if(mm<=0){
      result += "00";
    }
    result += ":";
    if(ss>0 && ss<10){
      result += "0"+parseInt(ss);
    }else if(ss>=10){
      result += parseInt(ss);
    }else if(ss<=0){
      result += "00";
    }
    return result;
},
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

/**
 * 根据视口和文档的宽高设置背景图片的尺寸
 */
utils.setDocSize = function(){
	var winWidth = $(window).width();
	var docWidth = $(document).width();
	var winHeight = $(window).height();
	var docHeight = $(document).height();
	//设置宽度
	if(winWidth >= docWidth){
		$(".bgContainer").width(winWidth);
		$(".longbackground").width(winWidth);
		$("#bg").width(winWidth);
		$(".page-layout").css("min-width",winWidth);
		$(".container").width(winWidth);
	}else{
		$(".bgContainer").width(docWidth);
		$(".longbackground").width(docWidth);
		$("#bg").width(docWidth);
		$(".page-layout").css("min-width",docWidth);
		$(".container").width(docWidth);
	}
	//设置高度
	if(winHeight >= docHeight){
		$(".bgContainer").height(winHeight);
		$(".longbackground").height(winHeight);
		$("#bg").height(winHeight);
		$(".page-layout").css("min-height",winHeight);
		$(".container").height(winHeight);
	}else{
		$(".bgContainer").height(docHeight);
		$(".longbackground").height(docHeight);
		$("#bg").height(docHeight);
		$(".page-layout").css("min-height",docHeight);
		$(".container").height(docHeight);
	}
	$(".bgContainer").css("overflow","hidden");
}


//判断浏览器是否支持 placeholder属性
utils.checkPlaceholder = function(){
  var input = document.createElement('input');
  var isPlaceholder = 'placeholder' in input;
  if (!isPlaceholder) {//不支持placeholder 用jquery来完成
    $(document).ready(function() {
        if(!isPlaceholder){
            $("input").not("input[type='password']").each(//把input绑定事件 排除password框
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
            if(pwdField.val()){
            	 pwdPlaceholder.hide();
                 pwdField.show();
            }else{
            	 pwdPlaceholder.show();
                 pwdField.hide();
            }
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
}

/**
 * title每隔n个字符插入指定字符
 */
/**
  @param data 原始字符串
  @param splitStr 要插入的字符串
  @param number 间隔的字符长度
  @example $.dataManager.splitDataByInfo("abcdefghijklmno","\r\n",10) 
     即表示在字符串"abcdefghijklmno"中每隔10个字符串插入"\r\n字符串"
*/
utils.splitDataByInfo = function(data,splitStr,number){
  var reStr = "(.{"+number+"}|.*)"; 
  var reg = new RegExp(reStr,"g"); 
  var dataArray = data.match(reg) ;
  dataArray.pop();
  var arrLength = dataArray.length+1; 
  for(var i=0;i<dataArray.length;i+=2){ 
    dataArray.splice(i+1,0,splitStr);
  } 
  dataArray.pop();
  var str = dataArray.join('');
  return str; 
}

/**
 * 加载百度统计js
 */
utils.loadBaiduTongji = function(){
  //获取域名
  var host = window.location.host;
  if(host.indexOf("www.celloud.cn")>=0){
    var _hmt = _hmt || [];
    (function() {
      var hm = document.createElement("script");
      hm.src = "//hm.baidu.com/hm.js?f6b04d06932c0fc1578e79be222599f4";
      hm.async = "async";
      hm.defer = "defer";
      var s = document.getElementsByTagName("script")[0]; 
      s.parentNode.insertBefore(hm, s);
    })();
  }
}
utils.loadBaiduTongji();
utils.setDocSize();
$(window).resize(function(){  
  utils.setDocSize();
});
utils.stopBubble = function(e) { 
  //如果提供了事件对象，则这是一个非IE浏览器 
  if ( e && e.stopPropagation ) 
      //因此它支持W3C的stopPropagation()方法 
      e.stopPropagation(); 
  else 
      //否则，我们需要使用IE的方式来取消事件冒泡 
      window.event.cancelBubble = true; 
}