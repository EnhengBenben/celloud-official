$(function() {
	$(".panel").css({"height":$(window).height()});
	$(".diary img").css({"height":$(window).height()-300});
	$.scrollify({
		section:".panel"
	});
	$(".scroll").click(function(e) {
		e.preventDefault();
		$.scrollify("move",$(this).attr("href"));
	});
	
	$.scrollify({
	    section : "section",
	    sectionName : "section-name",
	    easing: "easeOutExpo",
	    scrollSpeed: 500,
	    offset : 0,
	    scrollbars: true,
	    before:function() {},
	    after:function() {}
    });
});
$("img").lazyload();
$(document).ready( function() {
	$("#toTop").trigger("click");
    $('.subMenu').smint({
    	'scrollSpeed' : 1000
    });
});
function isScrollBottom(obj) {
	if (obj.scrollTop + obj.clientHeight === obj.scrollHeight) {
	    return true;
	} else {
		return false;
	}
}

//加载首页图
function animateIt(x,id) {
	var css = $(id).attr("class");
	$(id).removeClass().addClass('animated ' + x + ' '+css).one('webkitAnimationEnd mozAnimationEnd MSAnimationEnd oanimationend animationend', function(){
		$(this).removeClass().addClass(css);
	});
};
setTimeout(function(){$("#img_c").removeClass("none");animateIt('zoomIn fadeIn',"#img_c")},10);
setTimeout(function(){$("#img_l1").removeClass("none");animateIt('lightSpeedIn',"#img_l1")},500);
setTimeout(function(){$("#img_l2").removeClass("none");animateIt('bounceIn',"#img_l2")},900);
setTimeout(function(){$("#img_r1").removeClass("none");animateIt('rotateInDownLeft',"#img_r1")},500);
setTimeout(function(){$("#img_r2").removeClass("none");animateIt('bounceIn',"#img_r2")},900);
setTimeout(function(){$("#img_l3").removeClass("none");animateIt('rotateInUpLeft2d',"#img_l3")},1200);
setTimeout(function(){$("#img_l33").removeClass("none");animateIt('bounceIn',"#img_l33")},1400);
setTimeout(function(){$("#img_r3").removeClass("none");animateIt('rotateInUpLeft2d',"#img_r3")},1200);
setTimeout(function(){$("#img_r33").removeClass("none");animateIt('bounceIn',"#img_r33")},1400);
setTimeout(function(){$("#img_l4").removeClass("none");animateIt('rotateInUpLeft2d',"#img_l4")},1200);
setTimeout(function(){$("#img_l44").removeClass("none");animateIt('bounceIn',"#img_l44")},1300);
setTimeout(function(){$("#img_r4").removeClass("none");animateIt('rotateInUpLeft2d',"#img_r4")},1200);
setTimeout(function(){$("#img_r44").removeClass("none");animateIt('bounceIn',"#img_r44")},1300);
setTimeout(function(){$("#img_l5").removeClass("none");animateIt('rotateInUpLeft2d',"#img_l5")},1200);
setTimeout(function(){$("#img_l55").removeClass("none");animateIt('bounceIn',"#img_l55")},1500);
setTimeout(function(){$("#img_r5").removeClass("none");animateIt('rotateInUpLeft2d',"#img_r5")},1200);
setTimeout(function(){$("#img_r6").removeClass("none");animateIt('bounceIn',"#img_r6")},1700);
setTimeout(function(){$("#img_l6").removeClass("none");animateIt('rotateInUpLeft2d',"#img_l6")},1200);
setTimeout(function(){$("#img_l7").removeClass("none");animateIt('bounceIn',"#img_l7")},1800);
setTimeout(function(){$("#img_l8").removeClass("none");animateIt('rotateInUpLeft2d',"#img_l8")},2000);
setTimeout(function(){$("#img_l88").removeClass("none");animateIt('bounceIn',"#img_l88")},2300);
setTimeout(function(){$("#img_l9").removeClass("none");animateIt('rotateInUpLeft2d',"#img_l9")},2000);
setTimeout(function(){$("#img_l99").removeClass("none");animateIt('bounceIn',"#img_l99")},2200);
setTimeout(function(){$("#img_l10").removeClass("none");animateIt('rotateInUpLeft2d',"#img_l10")},2000);
setTimeout(function(){$("#img_l100").removeClass("none");animateIt('bounceIn',"#img_l100")},2300);
setTimeout(function(){$("#img_l11").removeClass("none");animateIt('rotateInUpLeft2d',"#img_l11")},2600);
setTimeout(function(){$("#img_l12").removeClass("none");animateIt('bounceIn',"#img_l112")},3000);
setTimeout(function(){$("#img_l13").removeClass("none");animateIt('rotateInUpLeft2d',"#img_l13")},3400);
setTimeout(function(){$("#img_l133").removeClass("none");animateIt('bounceIn',"#img_l133")},3700);
setTimeout(function(){$("#img_l14").removeClass("none");animateIt('rotateInUpLeft2d',"#img_l14")},3400);
setTimeout(function(){$("#img_l144").removeClass("none");animateIt('bounceIn',"#img_l144")},3900);
setTimeout(function(){$("#img_r71").removeClass("none");animateIt('rotateInUpLeft2d',"#img_r71")},2000);
setTimeout(function(){$("#img_r711").removeClass("none");animateIt('bounceIn',"#img_r711")},2400);
setTimeout(function(){$("#img_r72").removeClass("none");animateIt('rotateInUpLeft2d',"#img_r72")},2100);
setTimeout(function(){$("#img_r722").removeClass("none");animateIt('bounceIn',"#img_r722")},2500);
setTimeout(function(){$("#img_r73").removeClass("none");animateIt('rotateInUpLeft2d',"#img_r73")},2200);
setTimeout(function(){$("#img_r733").removeClass("none");animateIt('bounceIn',"#img_r733")},2600);
setTimeout(function(){$("#img_r74").removeClass("none");animateIt('rotateInUpLeft2d',"#img_r74")},2300);
setTimeout(function(){$("#img_r744").removeClass("none");animateIt('bounceIn',"#img_r744")},2700);
setTimeout(function(){$("#img_r75").removeClass("none");animateIt('rotateInUpLeft2d',"#img_r75")},2400);
setTimeout(function(){$("#img_r755").removeClass("none");animateIt('bounceIn',"#img_r755")},2800);

/***********************
 * 函数：判断滚轮滚动方向
 * 参数：event
 * 返回：滚轮方向
*************************/
var scrollFunc=function(e){
	setTimeout(function(){},1000);
    var direct=0;
    e=e || window.event;
   
    
    if(e.wheelDelta){//IE/Opera/Chrome 120向上  -120向下
		direct=e.wheelDelta;
    }else if(e.detail){//Firefox 3向下  -3向上
		direct=-e.detail;
    }
	if(direct<0){
		setTimeout(function(){animateIt('bounceInDown',"#result1")},100);
		if($("#s0").hasClass("active")){
			setTimeout(function(){animateIt('bounceInDown',"#result1")},100);
		}
		if($("#s1").hasClass("active")){
			setTimeout(function(){animateIt('zoomIn',"#result1")},100);
			setTimeout(function(){animateIt('zoomIn',"#rupdate")},100);
			setTimeout(function(){animateIt('zoomIn',"#rreport")},1500);
			setTimeout(function(){animateIt('zoomIn',"#ranalysis")},800);
		}
	}
}
/*注册事件*/
if(document.addEventListener){
	document.addEventListener('DOMMouseScroll',scrollFunc,false);
}//W3C
window.onmousewheel=document.onmousewheel=scrollFunc;//IE/Opera/Chrome/Safari