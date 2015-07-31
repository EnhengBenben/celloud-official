$.ajaxSetup ({
	cache: false //关闭AJAX相应的缓存
});
/**
 * @fileOverview
 * the script for Larger imgage 
 * @author liuqx
 * @version 2014-9-24
 */
function transitionEnd(){
	var e=document.createElement("bootstrap");
	var t={WebkitTransition:"webkitTransitionEnd",MozTransition:"transitionend",OTransition:"oTransitionEnd otransitionend",transition:"transitionend"};
	for(var n in t){
		if(e.style[n]!==undefined){
			return{end:t[n]}
		}
	}
	return false;
}
(function(e){
	e.fn.smartZoom=function(t){
		function r(e){}
		function s(e,t){
			var r=n.data("smartZoomData");
			if(r.currentWheelDelta*t<0)r.currentWheelDelta=0;r.currentWheelDelta+=t;
			i.zoom(r.mouseWheelDeltaFactor*r.currentWheelDelta,{x:e.pageX,y:e.pageY})
		}
		function o(e){e.preventDefault()}
		function u(){
			var e=n.data("smartZoomData");
			if(e.settings.mouseMoveEnabled!=true||e.settings.moveCursorEnabled!=true)return;
			var t=S();
			var r=t.width/e.originalSize.width;
			if(parseInt(r*100)>parseInt(e.adjustedPosInfos.scale*100))n.css({cursor:"move"});
			else n.css({cursor:"default"})
		}
		function a(e){m(e.pageX,e.pageY)}
		function f(t){
			t.preventDefault();
			e(document).on("mousemove.smartZoom",l);
			e(document).bind("mouseup.smartZoom",c);
			var r=n.data("smartZoomData");
			r.moveCurrentPosition=new A(t.pageX,t.pageY);
			r.moveLastPosition=new A(t.pageX,t.pageY)
		}
		function l(e){
			var t=n.data("smartZoomData");
			if(t.mouseMoveForPan||!t.mouseMoveForPan&&t.moveCurrentPosition.x!=e.pageX&&t.moveCurrentPosition.y!=e.pageY){
				t.mouseMoveForPan=true;
				v(e.pageX,e.pageY,0,false)
				
			}}
		function c(t){
			var r=n.data("smartZoomData");
			if(r.mouseMoveForPan){
				r.mouseMoveForPan=false;
				if(r.moveLastPosition.distance(r.moveCurrentPosition)>4){
					var i=r.moveLastPosition.interpolate(r.moveCurrentPosition,-4);
					v(i.x,i.y,500,true)
				}else{
					v(r.moveLastPosition.x,r.moveLastPosition.y,0,true)
				}
			}else if(r.settings.zoomOnSimpleClick){m(t.pageX,t.pageY)}
			e(document).unbind("mousemove.smartZoom");
			e(document).unbind("mouseup.smartZoom")
		}
		function h(t){
			t.preventDefault();
			e(document).unbind("touchmove.smartZoom");
			e(document).unbind("touchend.smartZoom");
			e(document).bind("touchmove.smartZoom",p);
			e(document).bind("touchend.smartZoom",d);
			var r=t.originalEvent.touches;
			var i=r[0];
			var s=n.data("smartZoomData");
			s.touch.touchMove=false;
			s.touch.touchPinch=false;
			s.moveCurrentPosition=new A(i.pageX,i.pageY);
			s.moveLastPosition=new A(i.pageX,i.pageY);
			s.touch.lastTouchPositionArr=new Array;
			var o;
			var u=r.length;
			for(var a=0;a<u;++a){
				o=r[a];s.touch.lastTouchPositionArr.push(new A(o.pageX,o.pageY))
			}
		}
		function p(e){
			e.preventDefault();
			var t=n.data("smartZoomData");
			var r=e.originalEvent.touches;
			var s=r.length;
			var o=r[0];
			if(s==1&&!t.touch.touchPinch&&t.settings.touchMoveEnabled==true){
				if(!t.touch.touchMove){
					var u=t.touch.lastTouchPositionArr[0];
					if(u.distance(new A(o.pageX,o.pageY))<3){return}
					else t.touch.touchMove=true
				}
				v(o.pageX,o.pageY,0,false)
			}else if(s==2&&!t.touch.touchMove&&t.settings.pinchEnabled==true){
				t.touch.touchPinch=true;
				var a=r[1];
				var f=t.touch.lastTouchPositionArr[0];
				var l=t.touch.lastTouchPositionArr[1];
				var c=new A(o.pageX,o.pageY);
				var h=new A(a.pageX,a.pageY);
				var p=c.distance(h);
				var d=f.distance(l);
				var m=p-d;if(Math.abs(m)<3)return;var g=new A((c.x+h.x)/2,(c.y+h.y)/2);var y=S();var b=t.originalSize;var w=y.width/b.width;var E=p/d;var x=y.width*E/b.width;i.zoom(x-w,g,0);t.touch.lastTouchPositionArr[0]=c;t.touch.lastTouchPositionArr[1]=h}}function d(t){t.preventDefault();var r=t.originalEvent.touches.length;if(r==0){e(document).unbind("touchmove.smartZoom");e(document).unbind("touchend.smartZoom")}var i=n.data("smartZoomData");if(i.touch.touchPinch)return;if(i.touch.touchMove){if(i.moveLastPosition.distance(i.moveCurrentPosition)>2){var s=i.moveLastPosition.interpolate(i.moveCurrentPosition,-4);v(s.x,s.y,500,true)}}else{if(i.settings.dblTapEnabled==true&&i.touch.lastTouchEndTime!=0&&(new Date).getTime()-i.touch.lastTouchEndTime<400){var o=i.touch.lastTouchPositionArr[0];m(o.x,o.y)}i.touch.lastTouchEndTime=(new Date).getTime()}}function v(e,t,i,s){g(r.PAN);var o=n.data("smartZoomData");o.moveLastPosition.x=o.moveCurrentPosition.x;o.moveLastPosition.y=o.moveCurrentPosition.y;var u=n.offset();var a=S();var f=u.left+(e-o.moveCurrentPosition.x);var l=u.top+(t-o.moveCurrentPosition.y);var c=y(f,l,a.width,a.height);x(r.PAN,r.START,false);E(n,c.x,c.y,a.width,a.height,i,s==true?function(){x(r.PAN,r.END,false)}:null);o.moveCurrentPosition.x=e;o.moveCurrentPosition.y=t}function m(e,t){var r=n.data("smartZoomData");var s=r.originalSize;var o=S();var u=o.width/s.width;var a=r.adjustedPosInfos.scale;var f=parseFloat(r.settings.dblClickMaxScale);var l;if(u.toFixed(2)>f.toFixed(2)||Math.abs(f-u)>Math.abs(u-a)){l=f-u}else{l=a-u}i.zoom(l,{x:e,y:t})}function g(t){var r=n.data("smartZoomData");if(r.transitionObject){if(r.transitionObject.cssAnimHandler)n.off(e.support.transition,r.transitionObject.cssAnimTimer);var i=r.originalSize;var s=S();var o=new Object;o[r.transitionObject.transition]="all 0s";if(r.transitionObject.css3dSupported){o[r.transitionObject.transform]="translate3d("+s.x+"px, "+s.y+"px, 0) scale3d("+s.width/i.width+","+s.height/i.height+", 1)"}else{o[r.transitionObject.transform]="translateX("+s.x+"px) translateY("+s.y+"px) scale("+s.width/i.width+","+s.height/i.height+")"}n.css(o)}else{n.stop()}u();if(t!=null)x(t,"",true)}function y(e,t,r,i){var s=n.data("smartZoomData");var o=Math.min(s.adjustedPosInfos.top,t);o+=Math.max(0,s.adjustedPosInfos.top+s.adjustedPosInfos.height-(o+i));var u=Math.min(s.adjustedPosInfos.left,e);u+=Math.max(0,s.adjustedPosInfos.left+s.adjustedPosInfos.width-(u+r));return new A(u.toFixed(2),o.toFixed(2))}function b(e){n.unbind("load.smartZoom");i.init.apply(n,[e.data.arguments])}function w(){var e=n.data("smartZoomData");var t=e.containerDiv;var r=e.originalSize;var i=t.parent().offset();
		var s=C(e.settings.left,i.left,t.parent().width());
		var o=C(e.settings.top,i.top,t.parent().height());
		t.offset({left:s,top:o});
		t.width(N(e.settings.width,t.parent().width(),s-i.left));
		t.height(N(e.settings.height,t.parent().height(),o-i.top));
		var a=L(t);
		var f=Math.min(Math.min(a.width/r.width,a.height/r.height),1).toFixed(2);
		var l=r.width*f;var c=r.height*f;
		e.adjustedPosInfos={left:(a.width-l)/2+i.left,top:(a.height-c)/2+i.top,width:l,height:c,scale:f};
		g();
		E(n,e.adjustedPosInfos.left,e.adjustedPosInfos.top,l,c,0,function(){n.css("visibility","visible")});
		u()}function E(t,r,i,s,o,u,a){var f=n.data("smartZoomData");var l=f.containerDiv.offset();
		var c=r-l.left;var h=i-l.top;
		if(f.transitionObject!=null){
			var p=f.originalSize;
			var d=new Object;
			d[f.transitionObject.transform+"-origin"]="0 0";
			d[f.transitionObject.transition]="all "+u/1e3+"s ease-out";
			if(f.transitionObject.css3dSupported)d[f.transitionObject.transform]="translate3d("+c+"px, "+h+"px, 0) scale3d("+s/p.width+","+o/p.height+", 1)";
			else d[f.transitionObject.transform]="translateX("+c+"px) translateY("+h+"px) scale("+s/p.width+","+o/p.height+")";
			if(a!=null){
				f.transitionObject.cssAnimHandler=a;
				
			}
			t.css(d)
		}else{
			t.animate({
				"margin-left":c,"margin-top":h,width:s,height:o
				},{
				duration:u,easing:f.settings.easing,complete:function(){if(a!=null)a()}
			})
		}
	}
	function S(e){var t=n.data("smartZoomData");var r=n.width();var i=n.height();var s=n.offset();var o=parseInt(s.left);var u=parseInt(s.top);var a=t.containerDiv.offset();if(e!=true){o=parseInt(o)-a.left;u=parseInt(u)-a.top}if(t.transitionObject!=null){var f=n.css(t.transitionObject.transform);if(f&&f!=""&&f.search("matrix")!=-1){var l;var c;if(f.search("matrix3d")!=-1){c=f.replace("matrix3d(","").replace(")","").split(",");l=c[0]}else{c=f.replace("matrix(","").replace(")","").split(",");l=c[3];o=parseFloat(c[4]);u=parseFloat(c[5]);if(e){o=parseFloat(o)+a.left;u=parseFloat(u)+a.top}}r=l*r;i=l*i}}return{x:o,y:u,width:r,height:i}}function x(e,t,i){var s=n.data("smartZoomData");var o="";if(i==true&&s.currentActionType!=e){o=s.currentActionType+"_"+r.END;s.currentActionType="";s.currentActionStep=""}else{if(s.currentActionType!=e||s.currentActionStep==r.END){s.currentActionType=e;s.currentActionStep=r.START;o=s.currentActionType+"_"+s.currentActionStep}else if(s.currentActionType==e&&t==r.END){s.currentActionStep=r.END;o=s.currentActionType+"_"+s.currentActionStep;s.currentActionType="";s.currentActionStep=""}}if(o!=""){var u=jQuery.Event(o);u.targetRect=S(true);u.scale=u.targetRect.width/s.originalSize.width;n.trigger(u)}}function T(){var t=document.body||document.documentElement;var n=t.style;var r=["transition","WebkitTransition","MozTransition","MsTransition","OTransition"];var i=["transition","-webkit-transition","-moz-transition","-ms-transition","-o-transition"];var s=["transform","-webkit-transform","-moz-transform","-ms-transform","-o-transform"];var o=r.length;var u;for(var a=0;a<o;a++){if(n[r[a]]!=null){transformStr=s[a];var f=e('<div style="position:absolute;">Translate3d Test</div>');e("body").append(f);u=new Object;u[s[a]]="translate3d(20px,0,0)";f.css(u);css3dSupported=f.offset().left-e("body").offset().left==20;f.empty().remove();if(css3dSupported){return{transition:i[a],transform:s[a],css3dSupported:css3dSupported}}}}return null}function N(e,t,n){if(e.search&&e.search("%")!=-1)return(t-n)*(parseInt(e)/100);else return parseInt(e)}function C(e,t,n){if(e.search&&e.search("%")!=-1)return t+n*(parseInt(e)/100);else return t+parseInt(e)}function k(){w()}function L(e){var t=e.offset();if(!t)return null;var n=t.left;var r=t.top;return{x:n,y:r,width:e.outerWidth(),height:e.outerHeight()}}function A(e,t){this.x=e;this.y=t;this.toString=function(){return"(x="+this.x+", y="+this.y+")"};this.interpolate=function(e,t){var n=t*this.x+(1-t)*e.x;var r=t*this.y+(1-t)*e.y;return new A(n,r)};this.distance=function(e){return Math.sqrt(Math.pow(e.y-this.y,2)+Math.pow(e.x-this.x,2))}}var n=this;r.ZOOM="SmartZoom_ZOOM";r.PAN="SmartZoom_PAN";r.START="START";r.END="END";r.DESTROYED="SmartZoom_DESTROYED";var i={init:function(t){if(n.data("smartZoomData"))return;settings=e.extend({top:"0",left:"0",width:"100%",height:"100%",easing:"smartZoomEasing",initCallback:null,maxScale:3,dblClickMaxScale:1.8,mouseEnabled:true,scrollEnabled:true,dblClickEnabled:true,mouseMoveEnabled:true,moveCursorEnabled:true,adjustOnResize:true,touchEnabled:true,dblTapEnabled:true,zoomOnSimpleClick:false,pinchEnabled:true,touchMoveEnabled:true,containerBackground:"#FFFFFF",containerClass:""},t);var r=n.attr("style");var i="smartZoomContainer"+(new Date).getTime();var u=e('<div id="'+i+'" class="'+settings.containerClass+'"></div>');n.before(u);n.remove();u=e("#"+i);u.css({overflow:"hidden"});if(settings.containerClass=="")u.css({"background-color":settings.containerBackground});u.append(n);var l=new Object;l.lastTouchEndTime=0;l.lastTouchPositionArr=null;l.touchMove=false;l.touchPinch=false;n.data("smartZoomData",{settings:settings,containerDiv:u,originalSize:{width:n.width(),height:n.height()},originalPosition:n.offset(),transitionObject:T(),touch:l,mouseWheelDeltaFactor:.15,currentWheelDelta:0,adjustedPosInfos:null,moveCurrentPosition:null,moveLastPosition:null,mouseMoveForPan:false,currentActionType:"",initialStyles:r,currentActionStep:""});w();if(settings.touchEnabled==true)n.bind("touchstart.smartZoom",h);if(settings.mouseEnabled==true){if(settings.mouseMoveEnabled==true)n.bind("mousedown.smartZoom",f);if(settings.scrollEnabled==true){u.bind("mousewheel.smartZoom",s);u.bind("mousewheel.smartZoom DOMMouseScroll.smartZoom",o)}if(settings.dblClickEnabled==true&&settings.zoomOnSimpleClick==false)u.bind("dblclick.smartZoom",a)}document.ondragstart=function(){return false};if(settings.adjustOnResize==true)e(window).bind("resize.smartZoom",k);if(settings.initCallback!=null)settings.initCallback.apply(this,n)},zoom:function(e,t,i){var s=n.data("smartZoomData");var o;var a;if(!t){var f=L(s.containerDiv);o=f.x+f.width/2;a=f.y+f.height/2}else{o=t.x;a=t.y}g(r.ZOOM);var l=S(true);var c=s.originalSize;var h=l.width/c.width+e;h=Math.max(s.adjustedPosInfos.scale,h);h=Math.min(s.settings.maxScale,h);var p=c.width*h;var d=c.height*h;var v=o-l.x;var m=a-l.y;var b=p/l.width;var w=l.x-(v*b-v);var T=l.y-(m*b-m);var N=y(w,T,p,d);if(i==null)i=700;x(r.ZOOM,r.START,false);E(n,N.x,N.y,p,d,i,function(){s.currentWheelDelta=0;u();x(r.ZOOM,r.END,false)})},pan:function(e,t,i){if(e==null||t==null)return;if(i==null)i=700;var s=n.offset();var o=S();var u=y(s.left+e,s.top+t,o.width,o.height);if(u.x!=s.left||u.y!=s.top){g(r.PAN);x(r.PAN,r.START,false);E(n,u.x,u.y,o.width,o.height,i,function(){x(r.PAN,r.END,false)})}},destroy:function(){var t=n.data("smartZoomData");if(!t)return;g();var i=t.containerDiv;n.unbind("mousedown.smartZoom");n.bind("touchstart.smartZoom");i.unbind("mousewheel.smartZoom");i.unbind("dblclick.smartZoom");i.unbind("mousewheel.smartZoom DOMMouseScroll.smartZoom");e(window).unbind("resize.smartZoom");e(document).unbind("mousemove.smartZoom");e(document).unbind("mouseup.smartZoom");e(document).unbind("touchmove.smartZoom");e(document).unbind("touchend.smartZoom");n.css({cursor:"default"});i.before(n);E(n,t.originalPosition.left,t.originalPosition.top,t.originalSize.width,t.originalSize.height,5);n.removeData("smartZoomData");i.remove();n.attr("style",t.initialStyles);n.trigger(r.DESTROYED)},isPluginActive:function(){return n.data("smartZoomData")!=undefined}};if(i[t]){return i[t].apply(this,Array.prototype.slice.call(arguments,1))}else if(typeof t==="object"||!t){if(n[0].tagName.toLowerCase()=="img"&&!n[0].complete){n.bind("load.smartZoom",{arguments:arguments[0]},b)}else{i.init.apply(n,[arguments[0]])}}else{e.error("Method "+t+" does not exist on e-smartzoom jquery plugin")}}})(jQuery);(function(e){e.extend(e.easing,{smartZoomEasing:function(t,n,r,i,s){return e.easing["smartZoomOutQuad"](t,n,r,i,s)},smartZoomOutQuad:function(e,t,n,r,i){return-r*(t/=i)*(t-2)+n}})})(jQuery);(function(e){function t(t){var n=t||window.event,r=[].slice.call(arguments,1),i=0,s=true,o=0,u=0;t=e.event.fix(n);t.type="mousewheel";if(n.wheelDelta){i=n.wheelDelta/120}if(n.detail){i=-n.detail/3}u=i;if(n.axis!==undefined&&n.axis===n.HORIZONTAL_AXIS){u=0;o=-1*i}if(n.wheelDeltaY!==undefined){u=n.wheelDeltaY/120}if(n.wheelDeltaX!==undefined){o=-1*n.wheelDeltaX/120}r.unshift(t,i,o,u);return(e.event.dispatch||e.event.handle).apply(this,r)}var n=["DOMMouseScroll","mousewheel"];if(e.event.fixHooks){for(var r=n.length;r;){e.event.fixHooks[n[--r]]=e.event.mouseHooks}}e.event.special.mousewheel={setup:function(){if(this.addEventListener){for(var e=n.length;e;){this.addEventListener(n[--e],t,false)}}else{this.onmousewheel=t}},teardown:function(){if(this.removeEventListener){for(var e=n.length;e;){this.removeEventListener(n[--e],t,false)}}else{this.onmousewheel=null}}};e.fn.extend({mousewheel:function(e){return e?this.bind("mousewheel",e):this.trigger("mousewheel")},unmousewheel:function(e){return this.unbind("mousewheel",e)}})})(jQuery);$.fn.emulateTransitionEnd=function(e){var t=false,n=this;$(this).one($.support.transition.end,function(){t=true});var r=function(){if(!t)$(n).trigger($.support.transition.end)};setTimeout(r,e);return this};$(function(){$.support.transition=transitionEnd()})

/**
 * @fileOverview
 * the script for app layout
 * @author kuangyi
 * @version 2013-6-20
 */
 
 ;(function($){
 
    $.fn.appLayout = function(ops){
        var defaults = {
            "desktopContainer":"",
            "appBtnClass":"",
            "appWidth":150,
            "appHeight":100
        }
        var op = $.extend(defaults,ops);
        var $this = $(this);
        var wrapperHeight = $this.height();
        var $desktopContainer = op.desktopContainer;
        var appBtnClass = op.appBtnClass;
        var appWidth = op.appWidth;
        var appHeight = op.appHeight;

        $desktopContainer.each(function(){
            var $that = $(this);
            var $appBtns = $that.find("."+appBtnClass);
            var appNum = $appBtns.length;
            var lineNum = Math.floor(wrapperHeight/appHeight);
            $appBtns.each(function(index,domElem){
                var leftPos = Math.floor(index/lineNum) * appWidth;
                var topPos = (index%lineNum) * appHeight;
                $(domElem).css({"left":leftPos+"px","top":topPos+"px"});
            });
        });

    }
 
 })(jQuery);
 /**
  * @fileOverview
  * the script for app move in global view
  * @author kuangyi
  * @version 2013-7-5
  */
  
  ;(function($){
  
     $.fn.appMove = function(ops){
         var defaults = {
            "dragClass":"",
            "folderContainer":"",
            "folderItemWrapper":""
         }
         var op = $.extend(defaults,ops);
         var wrapperWidth = $(window).width()/5;
         var wrapperTop = op.folderContainer.offset().top;
         
         return this.each(function(){
             var $this = $(this);
             var isMouseDown = false;
             var $cloneApp;
             var eventX;
             var eventY;
             var distanceX;
             var distanceY;
             var left;
             var top;
             var initOrder;
             $this.live("mousedown",function(e){               
                 isMouseDown = true;
                 eventX = e.pageX;
                 eventY = e.pageY;
                 left = $this.offset().left;
                 top = $this.offset().top;
                 initOrder = $this.closest(op.folderItemWrapper).attr("_order");
                 $cloneApp = $this.clone();
                 $cloneApp.appendTo("body").css({"position":"absolute","left":left,"top":top});
                 $this.addClass(op.dragClass);
                 disableSelection(document.body);
             });
             $(document).live("mousemove",function(e){
                 if(isMouseDown){
                     distanceX = e.pageX - eventX;
                     distanceY = e.pageY - eventY;
                     $cloneApp.css({"left":left+distanceX,"top":top+distanceY});
                 }
             });
             $(document).live("mouseup",function(){
                 if(isMouseDown){
                     //应用整理
                     isMouseDown = false;
                     if($cloneApp.position().top >= wrapperTop && distanceX && distanceY){
                         var order = Math.floor($cloneApp.position().left/wrapperWidth);
                         if(order<0){
                             order = 0;
                         }
                         $this.appendTo($(op.folderItemWrapper).eq(order));
                         var appId = $this.attr("_appId");
                         var thisObj;
                         $.each(apps[initOrder],function(i,n){
                             if(n.appId == appId){
                                 thisObj = n;
                                 apps[initOrder].splice(i,1);
                                 return false;
                             }
                         });
                         apps[order].push(thisObj);                      
                     }
                     var userId = $("#userId").val();
                     $.get("app!updateDesk",{"userId":userId,"deskNo":order,"oldDeskNo":initOrder,"appId":appId},function(data){
                     	
                     });
                     $cloneApp.remove();
                     $this.removeClass(op.dragClass);
                     enableSelection(document.body);
                     
                     //打开应用
                     if(!distanceX && !distanceY){
                         if(!$this.attr("_isOpen")){
                             var appId = $this.attr("_appId");
                             var appName = $this.attr("_appName");
                             var iframeSrc = $this.attr("_iframeSrc");
                             var imgSrc = $this.attr("_appImg");
                             var $appWindow = $("<div class='popWindow appWindow' _appId='" + appId + "'><div class='popWindow-titleBar'><div class='popWindow-title'>" + appName + "</div><div class='popWindow-Btn'><a href='###' class='popWindow-Btn-close'></a><a href='###' class='popWindow-Btn-max'></a><a href='###' class='popWindow-Btn-min'></a></div></div><div class='popWindow-bg-container'></div><div class='popWindow-content'><iframe src='" + iframeSrc + "' scrolling='auto' frameborder='0'></iframe></div><div class='popWindow-resize-t'></div><div class='popWindow-resize-r'></div><div class='popWindow-resize-b'></div><div class='popWindow-resize-l'></div><div class='popWindow-resize-rt'></div><div class='popWindow-resize-rb'></div><div class='popWindow-resize-lb'></div><div class='popWindow-resize-lt'></div></div>");
                             var $appTaskBox = $("<div class='taskItem' _appId='" + appId + "'><img src='" + imgSrc + "' alt='" + appName + "' title='" + appName + "' width='32' height='32' /><span>" + appName + "</span></div>");     
                             $("#desktop").append($appWindow)
                             if($(".appWindow").length > 1){
                                 $(".appWindow").css("z-index",10);
                             	$(".popWindow[_appid='ztsz']").css("z-index", 10);
                                 $appWindow.css("z-index",11);
                             }
                             $(".taskBox").append($appTaskBox)
                             $(".taskItem").removeClass("taskItemActive");
                             $appTaskBox.addClass("taskItemActive");
                             $this.attr("_isOpen","1");
                             /* 窗口拖拽 */
                             $(".popWindow[_appId='" + appId + "'] .popWindow-titleBar").drag({
                                 "dragElem":$(".popWindow[_appId='" + appId + "']")
                             });
                             //resize应用窗口大小
                             $(".popWindow[_appId='" + appId + "']").elemResize({
                                 "topLine":".popWindow-resize-t",
                                 "rightLine":".popWindow-resize-r",
                                 "bottomLine":".popWindow-resize-b",
                                 "leftLine":".popWindow-resize-l",
                                 "rightTopLine":".popWindow-resize-rt",
                                 "rightBottomLine":".popWindow-resize-rb",
                                 "leftBottomLine":".popWindow-resize-lb",
                                 "leftTopLine":".popWindow-resize-lt"
                             });
                             
                             //退出全局视图
                             $(".folderItem").removeClass("folderItemTurn");
                             $("#appManagerPanel").hide();
                             $("#desktop").show();
                             loadApps();
                             layoutApp();                            
                         }                     
                     }
                 }
             });
         });
         
         //设置dom节点不可选
         function disableSelection(target){
             if (typeof target.style.WebkitUserSelect!="undefined") //Chrome route
                 target.style.WebkitUserSelect="none"; 
             else if (typeof target.style.MozUserSelect!="undefined") //Firefox route
                 target.style.MozUserSelect="none";
             else if (typeof target.onselectstart!="undefined") //IE route
                 target.onselectstart=function(){return false}
             else //All other route (ie: Opera)
                 target.onmousedown=function(){return false}
         }
         //恢复节点可选
         function enableSelection(target){
             if(typeof target.style.WebkitUserSelect!="undefined") //Chrome route
                 target.style.WebkitUserSelect=""; 
             else if (typeof target.style.MozUserSelect!="undefined") //Firefox route
                 target.style.MozUserSelect="";
             else if (typeof target.onselectstart!="undefined") //IE route
                 target.onselectstart=function(){return true}
             else //All other route (ie: Opera)
                 target.onmousedown=function(){return true}
         }

     }
  
  })(jQuery);
		  
  /**
   * @fileOverview
   * the script for drag
   * @author kuangyi
   * @version 2013-6-18
   */

  ;(function($){

      $.fn.drag = function(ops){
          var $this = $(this);
          var defaults = {
              "dragElem":$this
          };
          var op = $.extend(defaults,ops);
          var $dragElem = op.dragElem;
          $this.live("mousedown",function(e){
              $this.attr("_offsetLeft",$dragElem.position().left); 
              $this.attr("_offsetTop",$dragElem.position().top);
              $this.attr("_ePageX",e.pageX);
              $this.attr("_ePageY",e.pageY);
              $this.attr("_isDrag","1");
              $dragElem.find("div.popWindow-bg-container:first").css({zIndex:5});
              disableSelection(document.body);
          });
          $(document).live({
              mousemove:function(e){
                  if($this.attr("_isDrag")){
                      var left = parseInt($this.attr("_offsetLeft"))+(e.pageX-parseInt($this.attr("_ePageX")));
                      var top = parseInt($this.attr("_offsetTop"))+(e.pageY-parseInt($this.attr("_ePageY")));
                      if(top >= 0 && top < $(window).height()-28){
                          $dragElem.css({"top":top+"px"});
                      }
                      //if(left > 0 && left < $(window).width() - $this.width()){
                          $dragElem.css({"left":parseInt(left)+"px"});
                      //}
                  }            
              },
              mouseup:function(e){
                  $this.removeAttr("_isDrag");
                  $dragElem.find("div.popWindow-bg-container:first").css({zIndex:-1});
                  enableSelection(document.body);
              }
          });
          
          //判断鼠标是否移出浏览器或移到iframe
          document.onmouseout = function(e){
              var e = e||window.event;
              if(!(e.relatedTarget || e.toElement) || (e.relatedTarget || e.relatedTarget.tagName == "HTML") || (e.toElement || e.toElement.tagName == "HTML")  || (e.relatedTarget || e.relatedTarget.tagName == "IFRAME") || (e.toElement || e.toElement.tagName == "IFRAME")){
                  if($this.attr("_isDrag")){
                      $this.attr("_isDocMouseout","1");
                  }
              }
          }
          document.onmouseover = function(e){
              var e = e||window.event;
              if($this.attr("_isDrag")){
                  $this.removeAttr("_isDocMouseout");
              }            
          }

          //设置dom节点不可选
          function disableSelection(target){
              if (typeof target.style.WebkitUserSelect!="undefined") //Chrome route
                  target.style.WebkitUserSelect="none"; 
              else if (typeof target.style.MozUserSelect!="undefined") //Firefox route
                  target.style.MozUserSelect="none";
              else if (typeof target.onselectstart!="undefined") //IE route
                  target.onselectstart=function(){return false}
              else //All other route (ie: Opera)
                  target.onmousedown=function(){return false}
          }
          //恢复节点可选
          function enableSelection(target){
              if(typeof target.style.WebkitUserSelect!="undefined") //Chrome route
                  target.style.WebkitUserSelect=""; 
              else if (typeof target.style.MozUserSelect!="undefined") //Firefox route
                  target.style.MozUserSelect="";
              else if (typeof target.onselectstart!="undefined") //IE route
                  target.onselectstart=function(){return true}
              else //All other route (ie: Opera)
                  target.onmousedown=function(){return true}
          }
      }

  })(jQuery)
		  
/**
 * @fileOverview
 * the script for Element resize
 * @author kuangyi
 * @version 2013-6-18
 */

;(function($){

    $.fn.elemResize = function(ops){
        var $this = $(this);
        var defaults = {
            "topLine":"",
            "rightLine":"",
            "bottomLine":"",
            "leftLine":"",
            "rightTopLine":"",
            "rightBottomLine":"",
            "leftBottomLine":"",
            "leftTopLine":""
        }
        var op = $.extend(defaults,ops);
        var $topLine = $this.find(op.topLine);
        var $rightLine = $this.find(op.rightLine);
        var $bottomLine = $this.find(op.bottomLine);
        var $leftLine = $this.find(op.leftLine);
        var $rightTopLine = $this.find(op.rightTopLine);
        var $rightBottomLine = $this.find(op.rightBottomLine);
        var $leftBottomLine = $this.find(op.leftBottomLine);
        var $leftTopLine = $this.find(op.leftTopLine);
        
        //上
        $topLine.live({         
            mousedown:function(e){
                $topLine.attr("_top",$this.position().top);
                $topLine.attr("_height",$this.height());
                $topLine.attr("_ePageY",e.pageY);
                $topLine.attr("_isMouseDown","1"); 
                $this.find("div.popWindow-bg-container:first").css({zIndex:5});
            }
        });
        $(document).live({
            mousemove:function(e){
                if($topLine.attr("_isMouseDown")){
                    var distanceY = parseInt($topLine.attr("_ePageY") - e.pageY);
                    if(!((distanceY <= 0 && $this.height() <= 185) || (distanceY >= 0 && $this.position().top <= 5))){
                        $this.css({"top":(parseInt($topLine.attr("_top"))-distanceY)+"px","height":(parseInt($topLine.attr("_height"))+distanceY)+"px"});
                    }                    
                }            
            },
            mouseup:function(e){
                $topLine.removeAttr("_isMouseDown");
                $this.find("div.popWindow-bg-container:first").css({zIndex:-1});
            }           
        });
        
        //右
        $rightLine.live({         
            mousedown:function(e){
                $rightLine.attr("_width",$this.width());
                $rightLine.attr("_ePageX",e.pageX);
                $rightLine.attr("_isMouseDown","1"); 
                $this.find("div.popWindow-bg-container:first").css({zIndex:5});
            }
        });
        $(document).live({
            mousemove:function(e){
                if($rightLine.attr("_isMouseDown")){
                    var distanceX = e.pageX - parseInt($rightLine.attr("_ePageX"));
                    if(!((distanceX <= 0 && $this.width() <= 250) || (distanceX >= 0 && $this.width() >= ($(window).width() - $this.position().left - 10)))){
                        $this.css({"width":(parseInt($rightLine.attr("_width"))+distanceX)+"px"});   
                    }                  
                }            
            },
            mouseup:function(e){
                $rightLine.removeAttr("_isMouseDown");
                $this.find("div.popWindow-bg-container:first").css({zIndex:-1});
            }           
        }); 

        //下        
        $bottomLine.live({         
            mousedown:function(e){
                $bottomLine.attr("_height",$this.height());
                $bottomLine.attr("_ePageY",e.pageY);
                $bottomLine.attr("_isMouseDown","1"); 
                $this.find("div.popWindow-bg-container:first").css({zIndex:5});
            }
        });
        $(document).live({
            mousemove:function(e){
                if($bottomLine.attr("_isMouseDown")){
                    var distanceY = e.pageY - parseInt($bottomLine.attr("_ePageY"));
                    $this.css({"height":(parseInt($bottomLine.attr("_height"))+distanceY)+"px"});                  
                }            
            },
            mouseup:function(e){
                $bottomLine.removeAttr("_isMouseDown");
                $this.find("div.popWindow-bg-container:first").css({zIndex:-1});
            }           
        });   

        //左
        $leftLine.live({         
            mousedown:function(e){
                $leftLine.attr("_left",$this.position().left);
                $leftLine.attr("_width",$this.width());
                $leftLine.attr("_ePageX",e.pageX);
                $leftLine.attr("_isMouseDown","1"); 
                $this.find("div.popWindow-bg-container:first").css({zIndex:5});
            }
        });
        $(document).live({
            mousemove:function(e){
                if($leftLine.attr("_isMouseDown")){
                    var distanceX = parseInt($leftLine.attr("_ePageX")) - e.pageX;
                    if(!((distanceX <= 0 && $this.width() <= 250) || (distanceX >= 0 && $this.width() >= ($(window).width() - $this.position().left - 10)))){
                        $this.css({"left":$leftLine.attr("_left")-distanceX,"width":(parseInt($leftLine.attr("_width"))+distanceX)+"px"});   
                    }                       
                }            
            },
            mouseup:function(e){
                $leftLine.removeAttr("_isMouseDown");
                $this.find("div.popWindow-bg-container:first").css({zIndex:-1});
            }           
        }); 

        //右上
        $rightTopLine.live({         
            mousedown:function(e){
                $rightTopLine.attr("_left",$this.position().left);
                $rightTopLine.attr("_top",$this.position().top);
                $rightTopLine.attr("_width",$this.width());
                $rightTopLine.attr("_height",$this.height());
                $rightTopLine.attr("_ePageX",e.pageX);  
                $rightTopLine.attr("_ePageY",e.pageY);          
                $rightTopLine.attr("_isMouseDown","1"); 
                $this.find("div.popWindow-bg-container:first").css({zIndex:5});
            }
        });
        $(document).live({
            mousemove:function(e){
                if($rightTopLine.attr("_isMouseDown")){
                    var distanceX = e.pageX - parseInt($rightTopLine.attr("_ePageX"));
                    var distanceY = parseInt($rightTopLine.attr("_ePageY") - e.pageY);
                    if(!((distanceX <= 0 && $this.width() <= 250) || (distanceX >= 0 && $this.width() >= ($(window).width() - $this.position().left - 10)))){
                        $this.css({"width":(parseInt($rightTopLine.attr("_width"))+distanceX)+"px"});   
                    }                    
                    if(!((distanceY <= 0 && $this.height() <= 185) || (distanceY >= 0 && $this.position().top <= 5))){
                        $this.css({"top":(parseInt($rightTopLine.attr("_top"))-distanceY)+"px","height":(parseInt($rightTopLine.attr("_height"))+distanceY)+"px"});
                    }                    
                }            
            },
            mouseup:function(e){
                $rightTopLine.removeAttr("_isMouseDown");
                $this.find("div.popWindow-bg-container:first").css({zIndex:-1});
            }           
        });   

        //右下
        $rightBottomLine.live({         
            mousedown:function(e){
                $rightBottomLine.attr("_width",$this.width());
                $rightBottomLine.attr("_height",$this.height());
                $rightBottomLine.attr("_ePageX",e.pageX);                               
                $rightBottomLine.attr("_ePageY",e.pageY);
                $rightBottomLine.attr("_isMouseDown","1"); 
                $this.find("div.popWindow-bg-container:first").css({zIndex:5});
            }
        });
        $(document).live({
            mousemove:function(e){
                if($rightBottomLine.attr("_isMouseDown")){
                    var distanceX = e.pageX - parseInt($rightBottomLine.attr("_ePageX"));
                    var distanceY = e.pageY - parseInt($rightBottomLine.attr("_ePageY"));                    
                    if(!((distanceX <= 0 && $this.width() <= 250) || (distanceX >= 0 && $this.width() >= ($(window).width() - $this.position().left - 10)))){
                        $this.css({"width":(parseInt($rightBottomLine.attr("_width"))+distanceX)+"px"});   
                    }
                    $this.css({"height":(parseInt($rightBottomLine.attr("_height"))+distanceY)+"px"});                     
                }            
            },
            mouseup:function(e){
                $rightBottomLine.removeAttr("_isMouseDown");
                $this.find("div.popWindow-bg-container:first").css({zIndex:-1});
            }           
        });  

        //左下
        $leftBottomLine.live({         
            mousedown:function(e){
                $leftBottomLine.attr("_left",$this.position().left);
                $leftBottomLine.attr("_width",$this.width());
                $leftBottomLine.attr("_height",$this.height());
                $leftBottomLine.attr("_ePageX",e.pageX);
                $leftBottomLine.attr("_ePageY",e.pageY);
                $leftBottomLine.attr("_isMouseDown","1"); 
                $this.find("div.popWindow-bg-container:first").css({zIndex:5});
            }
        });
        $(document).live({
            mousemove:function(e){
                if($leftBottomLine.attr("_isMouseDown")){
                    var distanceX = parseInt($leftBottomLine.attr("_ePageX")) - e.pageX;
                    var distanceY = e.pageY - parseInt($leftBottomLine.attr("_ePageY"));
                    if(!((distanceX <= 0 && $this.width() <= 250) || (distanceX >= 0 && $this.width() >= ($(window).width() - $this.position().left - 10)))){
                        $this.css({"left":$leftBottomLine.attr("_left")-distanceX,"width":(parseInt($leftBottomLine.attr("_width"))+distanceX)+"px"});   
                    }
                    $this.css({"height":(parseInt($leftBottomLine.attr("_height"))+distanceY)+"px"});                    
                }            
            },
            mouseup:function(e){
                $leftBottomLine.removeAttr("_isMouseDown");
                $this.find("div.popWindow-bg-container:first").css({zIndex:-1});
            }           
        }); 
        
        //左上
        $leftTopLine.live({         
            mousedown:function(e){
                $leftTopLine.attr("_left",$this.position().left);
                $leftTopLine.attr("_top",$this.position().top);
                $leftTopLine.attr("_width",$this.width());
                $leftTopLine.attr("_height",$this.height());
                $leftTopLine.attr("_ePageX",e.pageX);
                $leftTopLine.attr("_ePageY",e.pageY);
                $leftTopLine.attr("_isMouseDown","1"); 
                $this.find("div.popWindow-bg-container:first").css({zIndex:5});
            }
        });
        $(document).live({
            mousemove:function(e){
                if($leftTopLine.attr("_isMouseDown")){
                    var distanceX = parseInt($leftTopLine.attr("_ePageX")) - e.pageX;
                    var distanceY = parseInt($leftTopLine.attr("_ePageY") - e.pageY);
                    if(!((distanceX <= 0 && $this.width() <= 250) || (distanceX >= 0 && $this.width() >= ($(window).width() - $this.position().left - 10)))){
                        $this.css({"left":$leftTopLine.attr("_left")-distanceX,"width":(parseInt($leftTopLine.attr("_width"))+distanceX)+"px"});   
                    }  
                    if(!((distanceY <= 0 && $this.height() <= 185) || (distanceY >= 0 && $this.position().top <= 5))){
                        $this.css({"top":(parseInt($leftTopLine.attr("_top"))-distanceY)+"px","height":(parseInt($leftTopLine.attr("_height"))+distanceY)+"px"});
                    }                      
                }            
            },
            mouseup:function(e){
                $leftTopLine.removeAttr("_isMouseDown");
                $this.find("div.popWindow-bg-container:first").css({zIndex:-1});
            }           
        });         
    }

})(jQuery);
function initCelloud(){
	layoutApp();
	$(window).resize(function() {
		layoutApp();
	});

	// 多屏切换
	var screenIndex = 2;
	$(".screenOrder a").eq(screenIndex).addClass(
			getScreenOrder(screenIndex) + "Selected");
	$(".screenOrder").on("click","a",function() {
		var curIndex = $(this).index();
		$(this).addClass(getScreenOrder(curIndex) + "Selected");
		$(".screenOrder a").eq(screenIndex).removeClass(
				getScreenOrder(screenIndex) + "Selected");
		if (curIndex > screenIndex) {
			$(".desktopContainer").eq(screenIndex).removeClass(
					"desktop-show-animation").addClass(
					"desktop-disappear-animation1");
		}
		if (curIndex < screenIndex) {
			$(".desktopContainer").eq(screenIndex).removeClass(
					"desktop-show-animation").addClass(
					"desktop-disappear-animation2");
		}
		$(".desktopContainer").eq(curIndex).removeClass(
				"desktop-disappear-animation1").removeClass(
				"desktop-disappear-animation2").addClass(
				"desktop-show-animation");
		screenIndex = curIndex;

		deskNo = screenIndex;
	});



	// 打开应用
	$(".leftApp").live("click",function(e) {
		var $this = $(this);
		var state = $this.attr("_state");
		if (state != 1) {
			var appName = $this.attr("_appName");
			var leftApp = $(".leftApp[_appId='" + $this.attr("_appId") + "']");
			var isOpen = false;
			for(var i=0; i<leftApp.length; i++){
				var w = leftApp[i];
				if($(w).attr("_isOpen")==1){
					isOpen=true;
				}
			}
			if (!isOpen || appName == "报告") {
				var appId;
				if (appName == "报告") {
					appId = $this.attr("_appId")
							+ new Date().getTime();
				} else {
					appId = $this.attr("_appId");
				}
				$("#softwareId").val(appId);
				$("#softwareName").val(appName);
				var iframeSrc = $this.attr("_iframeSrc");
				var imgSrc = $this.attr("_appImg");
				if (imgSrc.indexOf("/") < 0) {
					imgSrc = "images/app/" + imgSrc;
				}
				var $appWindow = $("<div class='popWindow appWindow' id='appWindowId' _appId='"
						+ appId
						+ "'><div class='popWindow-titleBar'><div class='popWindow-title'>"
						+ appName
						+ "</div><div class='popWindow-Btn'><a href='javascript:;' class='popWindow-Btn-close'></a><a href='javascript:;' class='popWindow-Btn-max'></a><a href='javascript:;' class='popWindow-Btn-min'></a></div></div><div class='popWindow-bg-container'></div><div class='popWindow-content'><iframe id='appInfoIframe"
						+ appId
						+ "' name="
						+ appId
						+ " src='"
						+ iframeSrc
						+ "' scrolling='auto' frameborder='0'></iframe></div><div class='popWindow-resize-t'></div><div class='popWindow-resize-r'></div><div class='popWindow-resize-b'></div><div class='popWindow-resize-l'></div><div class='popWindow-resize-rt'></div><div class='popWindow-resize-rb'></div><div class='popWindow-resize-lb'></div><div class='popWindow-resize-lt'></div></div>");
				var $appTaskBox = $("<div class='taskItem' _appId='"
						+ appId
						+ "'><img src='"
						+ imgSrc
						+ "' alt='"
						+ appName
						+ "' title='"
						+ appName
						+ "' width='32' height='32' /><span>"
						+ appName + "</span></div>");
				checkAppManagerPanel($this);
				$("#desktop").append($appWindow);
				if ($(".appWindow").length > 1) {
					$(".appWindow").css("z-index", 10);
					$appWindow.css("z-index", 11);
				}
				$(".taskBox").append($appTaskBox);
				$(".taskItem").removeClass("taskItemActive");
				$appTaskBox.addClass("taskItemActive");
				$this.attr("_isOpen", "1");
				setWinPosition(appId);
				/* 窗口拖拽 */
				$(".popWindow[_appId='" + appId+ "'] .popWindow-titleBar").drag({
					"dragElem" : $(".popWindow[_appId='"
							+ appId + "']")
				});
				// resize应用窗口大小
				$(".popWindow[_appId='" + appId + "']").elemResize({
					"topLine" : ".popWindow-resize-t",
					"rightLine" : ".popWindow-resize-r",
					"bottomLine" : ".popWindow-resize-b",
					"leftLine" : ".popWindow-resize-l",
					"rightTopLine" : ".popWindow-resize-rt",
					"rightBottomLine" : ".popWindow-resize-rb",
					"leftBottomLine" : ".popWindow-resize-lb",
					"leftTopLine" : ".popWindow-resize-lt"
				});
				$(".popWindow[_appId='" + appId + "']").live("click", function() {
					var $this = $(this);
					var appid = $this.attr("_appId");
					var $taskItem = $(".taskItem[_appId='" + appid + "']");
					var appWindows = $(".appWindow");
					for(var i=0;i<appWindows.length;i++){
						var w = appWindows[i];
						if($(w).attr("_appId") == appid){
							$(w).css("z-index", 11);
							//获取焦点时，删除这个半透明div层
							$(w).children(".popWindow-content").children(".popWindow-overlap").remove(); 
						}else{
							$(w).css("z-index", 10);
							var overlaps = $(w).children(".popWindow-content").children(".popWindow-overlap"); 
							if(overlaps.length == 0){
								//在失去焦点的窗口中，增加一个半透明div层，这样当用户点击此层后，本窗口获取焦点
								$(w).children(".popWindow-content").append("<div class=\"popWindow-overlap\"></div>");
							}
						}
					}
					$(".themeWindow").css("z-index", 10);
					$(".themeWindow").children(".popWindow-content").append("<div class=\"popWindow-overlap\"></div>");
					$(".taskItem").removeClass("taskItemActive");
					$taskItem.addClass("taskItemActive");
				});
				$(".themeWindow").css("z-index", 10);
				$(".themeWindow").children(".popWindow-content").append("<div class=\"popWindow-overlap\"></div>");
				if (appName == "报告") {
					$(".popWindow[_appId='"+ appId + "']").find(".popWindow-Btn-max").trigger("click");
				}
			} else {
				var appid = $this.attr("_appId");
				var appWindows = $(".appWindow");
				appWindows.css("z-index", 10);
				//获取焦点时，删除这个半透明div层
				appWindows.children(".popWindow-content").children(".popWindow-overlap").remove(); 
				$(".popWindow[_appId='"+ appid + "']").css("z-index", 11);
				var $taskItem = $(".taskItem[_appId='" + appid + "']");
				for(var i=0;i<appWindows.length;i++){
					var w = appWindows[i];
					if($(w).attr("_appId") != appid){
						var overlaps = $(w).children(".popWindow-content").children(".popWindow-overlap"); 
						if(overlaps.length == 0){
							//在失去焦点的窗口中，增加一个半透明div层，这样当用户点击此层后，本窗口获取焦点
							$(w).children(".popWindow-content").append("<div class=\"popWindow-overlap\"></div>");
						}
					}
				}
				$(".themeWindow").css("z-index", 10);
				$(".themeWindow").children(".popWindow-content").append("<div class=\"popWindow-overlap\"></div>");
				$(".taskItem").removeClass("taskItemActive");
				$taskItem.addClass("taskItemActive");
				checkAppManagerPanel($this);
				var $appWindow = $(".appWindow[_appId='" + $this.attr("_appId") + "']");
				if ($appWindow.is(":hidden")) {
					$appWindow.fadeIn().css("z-index", 11);
				} 
			}
		} else {
			// 这里是CS软件的调用
			$.get("selectIp.action", {
				"processName" : "Oligo 7"
			},
			function(data) {
				var height = window.screen.height;
				var width = window.screen.width;
				var username = array[0];
				var password = array[1];
				var entry = array[2];
				var remote = document
						.getElementById("remoteapp");
				remote.SetParam("Server", json);
				remote.SetParam("Username", "CDC\\"
						+ username);
				remote.SetParam("EncryptPassword",
						password);
				remote
						.SetParam("ExecutablePath",
								entry);
				remote.SetParam("WorkingDirectory",
						"z:\\" + username);
				remote.SetParam("DesktopWidth", width);
				remote
						.SetParam("DesktopHeight",
								height);
				remote.Connect();
			});
		}
	});
	
	$(".appBtn").live("click",function(e) {
		var $this = $(this);
		var state = $this.attr("_state");
		if (state != 1) {
			var appName = $this.attr("_appName");
			var leftApp = $(".appBtn[_appId='" + $this.attr("_appId") + "']");
			var isOpen = false;
			for(var i=0; i<leftApp.length; i++){
				var w = leftApp[i];
				if($(w).attr("_isOpen")==1){
					isOpen=true;
				}
			}
			if (!isOpen) {
				var appId;
				if (appName == "报告") {
					appId = $this.attr("_appId")
							+ new Date().getTime();
				} else {
					appId = $this.attr("_appId");
				}
				$("#softwareId").val(appId);
				$("#softwareName").val(appName);
				var iframeSrc = $this.attr("_iframeSrc");
				var imgSrc = $this.attr("_appImg");
				if (imgSrc.indexOf("/") < 0) {
					imgSrc = "images/app/" + imgSrc;
				}
				var $appWindow = $("<div class='popWindow appWindow' id='appWindowId' _appId='"
						+ appId
						+ "'><div class='popWindow-titleBar'><div class='popWindow-title'>"
						+ appName
						+ "</div><div class='popWindow-Btn'><a href='javascript:;' class='popWindow-Btn-close'></a><a href='javascript:;' class='popWindow-Btn-max'></a><a href='javascript:;' class='popWindow-Btn-min'></a></div></div><div class='popWindow-bg-container'></div><div class='popWindow-content'><iframe id='appInfoIframe"
						+ appId
						+ "' name="
						+ appId
						+ " src='"
						+ iframeSrc
						+ "' scrolling='auto' frameborder='0'></iframe></div><div class='popWindow-resize-t'></div><div class='popWindow-resize-r'></div><div class='popWindow-resize-b'></div><div class='popWindow-resize-l'></div><div class='popWindow-resize-rt'></div><div class='popWindow-resize-rb'></div><div class='popWindow-resize-lb'></div><div class='popWindow-resize-lt'></div></div>");
				var $appTaskBox = $("<div class='taskItem' _appId='"
						+ appId
						+ "'><img src='"
						+ imgSrc
						+ "' alt='"
						+ appName
						+ "' title='"
						+ appName
						+ "' width='32' height='32' /><span>"
						+ appName + "</span></div>");
				checkAppManagerPanel($this);
				$("#desktop").append($appWindow);
				if ($(".appWindow").length > 1) {
					$(".appWindow").css("z-index", 10);
					$appWindow.css("z-index", 11);
				}
				$(".taskBox").append($appTaskBox);
				$(".taskItem").removeClass("taskItemActive");
				$appTaskBox.addClass("taskItemActive");
				$this.attr("_isOpen", "1");
				setWinPosition(appId);
				/* 窗口拖拽 */
				$(".popWindow[_appId='" + appId+ "'] .popWindow-titleBar").drag({
					"dragElem" : $(".popWindow[_appId='"
							+ appId + "']")
				});
				// resize应用窗口大小
				$(".popWindow[_appId='" + appId + "']").elemResize({
					"topLine" : ".popWindow-resize-t",
					"rightLine" : ".popWindow-resize-r",
					"bottomLine" : ".popWindow-resize-b",
					"leftLine" : ".popWindow-resize-l",
					"rightTopLine" : ".popWindow-resize-rt",
					"rightBottomLine" : ".popWindow-resize-rb",
					"leftBottomLine" : ".popWindow-resize-lb",
					"leftTopLine" : ".popWindow-resize-lt"
				});
				$(".popWindow[_appId='" + appId + "']").live("click", function() {
					var $this = $(this);
					var appid = $this.attr("_appId");
					var $taskItem = $(".taskItem[_appId='" + appid + "']");
					var appWindows = $(".appWindow");
					for(var i=0;i<appWindows.length;i++){
						var w = appWindows[i];
						if($(w).attr("_appId") == appid){
							$(w).css("z-index", 11);
							//获取焦点时，删除这个半透明div层
							$(w).children(".popWindow-content").children(".popWindow-overlap").remove(); 
						}else{
							$(w).css("z-index", 10);
							var overlaps = $(w).children(".popWindow-content").children(".popWindow-overlap"); 
							if(overlaps.length == 0){
								//在失去焦点的窗口中，增加一个半透明div层，这样当用户点击此层后，本窗口获取焦点
								$(w).children(".popWindow-content").append("<div class=\"popWindow-overlap\"></div>");
							}
						}
					}
					$(".themeWindow").css("z-index", 10);
					$(".themeWindow").children(".popWindow-content").append("<div class=\"popWindow-overlap\"></div>");
					$(".taskItem").removeClass("taskItemActive");
					$taskItem.addClass("taskItemActive");
				});
			} else {
				var appid = $this.attr("_appId");
				var appWindows = $(".appWindow");
				appWindows.css("z-index", 10);
				//获取焦点时，删除这个半透明div层
				appWindows.children(".popWindow-content").children(".popWindow-overlap").remove(); 
				$(".popWindow[_appId='"+ appid + "']").css("z-index", 11);
				var $taskItem = $(".taskItem[_appId='" + appid + "']");
				for(var i=0;i<appWindows.length;i++){
					var w = appWindows[i];
					if($(w).attr("_appId") != appid){
						var overlaps = $(w).children(".popWindow-content").children(".popWindow-overlap"); 
						if(overlaps.length == 0){
							//在失去焦点的窗口中，增加一个半透明div层，这样当用户点击此层后，本窗口获取焦点
							$(w).children(".popWindow-content").append("<div class=\"popWindow-overlap\"></div>");
						}
					}
				}
				$(".themeWindow").css("z-index", 10);
				$(".themeWindow").children(".popWindow-content").append("<div class=\"popWindow-overlap\"></div>");
				$(".taskItem").removeClass("taskItemActive");
				$taskItem.addClass("taskItemActive");
				checkAppManagerPanel($this);
				var $appWindow = $(".appWindow[_appId='" + $this.attr("_appId") + "']");
				if ($appWindow.is(":hidden")) {
					$appWindow.fadeIn().css("z-index", 11);
				} 
			}
			if (appName == "报告") {
				$(".popWindow[_appId='"+ appid + "']").find(".popWindow-Btn-max").trigger("click");
			}
		} else {
			// 这里是CS软件的调用
			$.get("selectIp.action", {
				"processName" : "Oligo 7"
			},
			function(data) {
				var height = window.screen.height;
				var width = window.screen.width;
				var username = array[0];
				var password = array[1];
				var entry = array[2];
				var remote = document
						.getElementById("remoteapp");
				remote.SetParam("Server", json);
				remote.SetParam("Username", "CDC\\"
						+ username);
				remote.SetParam("EncryptPassword",
						password);
				remote
						.SetParam("ExecutablePath",
								entry);
				remote.SetParam("WorkingDirectory",
						"z:\\" + username);
				remote.SetParam("DesktopWidth", width);
				remote
						.SetParam("DesktopHeight",
								height);
				remote.Connect();
			});
		}
	});

	// 右键菜单
	$('.appBtn:not(".addAppBtn,.addAppBtn1")').live('contextmenu',function(e) {
		$(".rightMenu").remove();
		var $this = $(this);
		var appId = $this.attr("_appId");
		var order = $this.closest(".desktopContainer").attr(
				"_order");
		var $rightMenu = $("<div class='rightMenu'><ul><li><a href='javascript:deleteDeskApp("
				+ appId
				+ ","
				+ order
				+ ");' class='deleteApp'>删除应用</a></li></ul></div>");
		$("#desktop").append($rightMenu);
		$rightMenu.css({
			"left" : e.clientX,
			"top" : e.clientY
		});
		e.preventDefault();
		// 删除应用
		// $(".deleteApp").live("click",function(){
		// $this.remove();
		// layoutApp();
		// $.each(apps[order],function(i,n){
		// if(n.appId == appId){
		// apps[order].splice(i,1);
		// return false;
		// }
		// });
		// });
		$(document).click(function(e) {
			var e = e || window.event;
			var el = e.target || e.srcElement;
			if (!$(el).hasClass("appBtn") && e.which != 3) {
				$rightMenu.remove();
			}
		});
	});

	// 应用最小化
	$(".popWindow-Btn-min").live("click", function(e) {
		var $this = $(this);
		var $appWindow = $this.closest(".appWindow");
		$appWindow.hide();
		var $appWindow = $this.closest(".appWindow");
		$appWindow.prev().css("z-index", 11);
		$appWindow.prev().children(".popWindow-content").children(".popWindow-overlap").remove(); 
		var e=e||window.event;
		stopBubble(e);
	});

	// 显示被最小化的应用
	$(".taskItem").live("click", function() {
		var $this = $(this);
		var appid = $this.attr("_appId");
		var $appWindow = $(".appWindow[_appId='" + appid + "']");
		if ($appWindow.is(":hidden")) {
			$(".appWindow").css("z-index", 10);
			var appWindows = $(".appWindow");
			for(var i=0;i<appWindows.length;i++){
				var w = appWindows[i];
				if($(w).attr("_appId") != appid){
					$(w).css("z-index", 10);
					var overlaps = $(w).children(".popWindow-content").children(".popWindow-overlap"); 
					if(overlaps.length == 0){
						//在失去焦点的窗口中，增加一个半透明div层，这样当用户点击此层后，本窗口获取焦点
						$(w).children(".popWindow-content").append("<div class=\"popWindow-overlap\"></div>");
					}
				}
			}
			$(".themeWindow").children(".popWindow-content").append("<div class=\"popWindow-overlap\"></div>");
			$appWindow.fadeIn().css("z-index", 11);
			$appWindow.children(".popWindow-content").children(".popWindow-overlap").remove();
			$(".taskItem").removeClass("taskItemActive");
			$this.addClass("taskItemActive");
		} else {
			if ($appWindow.css("z-index") == 10 && $(".appWindow").length > 1) {
//				$(".appWindow").css("z-index", 10);
				var appWindows = $(".appWindow");
				for(var i=0;i<appWindows.length;i++){
					var w = appWindows[i];
					if($(w).attr("_appId") != appid){
						$(w).css("z-index", 10);
						var overlaps = $(w).children(".popWindow-content").children(".popWindow-overlap"); 
						if(overlaps.length == 0){
							//在失去焦点的窗口中，增加一个半透明div层，这样当用户点击此层后，本窗口获取焦点
							$(w).children(".popWindow-content").append("<div class=\"popWindow-overlap\"></div>");
						}
					}
				}
				$(".themeWindow").children(".popWindow-content").append("<div class=\"popWindow-overlap\"></div>");
				$appWindow.css("z-index", 11);
				$appWindow.children(".popWindow-content").children(".popWindow-overlap").remove();
				$(".taskItem").removeClass("taskItemActive");
				$this.addClass("taskItemActive");
			} else {
				$appWindow.fadeOut();
				$this.removeClass("taskItemActive");
			}
		}
	});
	$(".appWindow").live("click", function() {
		var $this = $(this);
		var appid = $this.attr("_appId");
		var $taskItem = $(".taskItem[_appId='" + appid + "']");
		$(".appWindow").css("z-index", 10);
		$this.css("z-index", 11);
		$(".taskItem").removeClass("taskItemActive");
		$taskItem.addClass("taskItemActive");
	});

	// 应用最大化
	$(".popWindow-Btn-max").live("click", function() {
		var $this = $(this);
		var $appWindow = $this.closest(".appWindow");
		var winWidth = 0;
		var winHeight = 0;
		var winLeft = 0;
		var winTop = 0;
		if (!$appWindow.attr("_isMax")) {
			$appWindow.attr("_width", $appWindow.width());
			$appWindow.attr("_height", $appWindow.height())
			$appWindow.attr("_left", $appWindow.position().left)
			$appWindow.attr("_top", $appWindow.position().top)
			$appWindow.width($(document).width() - 2);
			$appWindow.height($(document).height() + 15);
			$appWindow.css({
				"left" : 0,
				"top" : 0
			});
			$this.addClass("popWindow-Btn-max-restore");
			$appWindow.attr("_isMax", "1");
		} else {
			$appWindow.width($appWindow.attr("_width"));
			$appWindow.height($appWindow.attr("_height"));
			$appWindow.css({
				"left" : $appWindow.attr("_left") + "px",
				"top" : $appWindow.attr("_top") + "px"
			});
			$this.removeClass("popWindow-Btn-max-restore");
			$appWindow.removeAttr("_isMax");
		}
	});

	// 关闭应用
	$(".popWindow-Btn-close").live("click", function(e) {
		var $this = $(this);
		var $appWindow = $this.closest(".popWindow");
		var appId = $appWindow.attr("_appId");
		if(appId=="sjsc" && isUploading){
			if (confirm("关闭窗口将中断文件上传，确定关闭？")){
				$appWindow.prev().css("z-index", 11);
				$appWindow.prev().children(".popWindow-content").children(".popWindow-overlap").remove(); 
				
				var $taskItem = $(".taskItem[_appId='" + appId + "']");
				$appWindow.remove();
				$taskItem.remove();
				$(".appBtn[_appId='" + appId + "']").removeAttr("_isOpen");
				$(".leftApp[_appId='" + appId + "']").removeAttr("_isOpen");
			    return;
			}else{
				return;
			}
		}
		var $taskItem = $(".taskItem[_appId='" + appId + "']");
		if(appId=="ztsz"||!$appWindow.prev().hasClass("popWindow")){
			$appWindow.next().css("z-index", 11);
			$appWindow.next().children(".popWindow-content").children(".popWindow-overlap").remove(); 
		}else{
			$appWindow.prev().css("z-index", 11);
			$appWindow.prev().children(".popWindow-content").children(".popWindow-overlap").remove(); 
		}
		if(appId=="ztsz"){
			$appWindow.css("display","none");
		}else{
			$appWindow.remove();
			$taskItem.remove();
		}
		$(".appBtn[_appId='" + appId + "']").removeAttr("_isOpen");
		$(".leftApp[_appId='" + appId + "']").removeAttr("_isOpen");
		var e=e||window.event;
		stopBubble(e);
	});

	// 添加应用
	
	// addApp(2,"weather","天气","../img/ph/weather.png","");

	/*
	 * //设置最外层容器desktop宽度 function setDesktopSize(){
	 * $("#desktop").width($(document).width());
	 * $("#desktop").height($(document).height());
	 * $("#desktop").css("overflow","hidden"); } //setDesktopSize();
	 */

	// 主题设置弹窗
	$(".toolIcon-setting").live("click", function(e) {
		var $this = $(this);
		$(".themeWindow").show();
		$(".themeWindow .popWindow-titleBar").drag({
			"dragElem" : $(".themeWindow")
		});
		setWinPosition("ztsz");
		var appid = $this.attr("_appId");
		var appWindows = $(".appWindow");
		appWindows.css("z-index", 10);
		//获取焦点时，删除这个半透明div层
		$(".themeWindow").children(".popWindow-content").children(".popWindow-overlap").remove(); 
		$(".themeWindow").css("z-index", 11);
		for(var i=0;i<appWindows.length;i++){
			var w = appWindows[i];
			if($(w).attr("_appId") != appid){
				var overlaps = $(w).children(".popWindow-content").children(".popWindow-overlap"); 
				if(overlaps.length == 0){
					//在失去焦点的窗口中，增加一个半透明div层，这样当用户点击此层后，本窗口获取焦点
					$(w).children(".popWindow-content").append("<div class=\"popWindow-overlap\"></div>");
				}
			}
		}
	});
	$(".themeWindow").live("click", function(e) {
		var $this = $(this);
		$(".themeWindow").show();
		$(".themeWindow .popWindow-titleBar").drag({
			"dragElem" : $(".themeWindow")
		});
		setWinPosition("ztsz");
		var appid = $this.attr("_appId");
		var appWindows = $(".appWindow");
		appWindows.css("z-index", 10);
		//获取焦点时，删除这个半透明div层
		$(".themeWindow").children(".popWindow-content").children(".popWindow-overlap").remove(); 
		$(".themeWindow").css("z-index", 11);
		for(var i=0;i<appWindows.length;i++){
			var w = appWindows[i];
			if($(w).attr("_appId") != appid){
				var overlaps = $(w).children(".popWindow-content").children(".popWindow-overlap"); 
				if(overlaps.length == 0){
					//在失去焦点的窗口中，增加一个半透明div层，这样当用户点击此层后，本窗口获取焦点
					$(w).children(".popWindow-content").append("<div class=\"popWindow-overlap\"></div>");
				}
			}
		}
		checkAppManagerPanel($this);
	});
	// 关闭主题设置
//	$(".popWindow-Btn-close").live("click", function() {
//		var $this = $(this);
//		var $themeWindow = $this.closest(".themeWindow");
//		$themeWindow.hide();
//	});
	// 修改主题
	$(".theme").live("click",function() {
		var $this = $(this);
		var imgSrc = $this.attr("_src");
		// 图片预加载
		var img = new Image();
		img.src = imgSrc;
		if (img.complete) {
			$("body").css("background-image",
					"url(" + imgSrc + ")");
		} else {
			img.onload = function() {
				$("body").css("background-image",
						"url(" + imgSrc + ")");
				img.onload = null;
			};
		}
		;

		// 将个人主题保存到数据库中
		var imgName = imgSrc.substring(
				imgSrc.lastIndexOf("/") + 1, imgSrc.length);
		$.get("user!updateTheme", {
			"user.theme" : imgName
		}, function(data) {
			// 暂不做处理
		});
	});

	/* 打开全局视图 */
	$(".globalView").live("click", function() {
		$("#desktop").hide();
		$("#appManagerPanel").show();
		$(".folderItem").addClass("folderItemTurn");
		loadGlobalApps();
		/* 全局视图中的应用整理 */
		$(".aMgApp").appMove({
			"dragClass" : "aMgAppDrag",
			"folderContainer" : $(".aMgFolderContainer"),
			"folderItemWrapper" : ".folderItemInner"
		});
	});
	/* 退出全局视图 */
	$(".quit").live("click", function() {
		$(".folderItem").removeClass("folderItemTurn");
		$("#appManagerPanel").hide();
		$("#desktop").show();
		loadApps();
		layoutApp();
	});

	// 鼠标hover到时钟上，出现关闭按钮
	$(".widget-clock").live("mouseover", function() {
		var $this = $(this);
		var $widgetCloseBtn = $this.find(".widget-close");
		$widgetCloseBtn.show();
	});
	$(".widget-clock").live("mouseout", function() {
		var $this = $(this);
		var $widgetCloseBtn = $this.find(".widget-close");
		$widgetCloseBtn.hide();
	});
	// 关闭时钟
	$(".widget-close").live("click", function() {
		var $this = $(this);
		var $widgetClock = $this.closest(".widget-clock");
		$widgetClock.hide();
	});
	// 打开时钟
	$("#openClock").live("click", function() {
		$(".widget-clock").show();
	});

	// 打开搜索框
	$(".search").live("click", function() {
		$(".searchBar").toggle();
	});

	$.ajaxSetup ({
		cache: false //关闭AJAX相应的缓存
	});
	//初始化桌面app数据
	$.get("app!getApp",{"userId":userId},function(data){
		apps = data;
		//加载桌面应用
		loadApps();
		//设置布局
		layoutApp();
	});
    layoutApp();
    $(window).resize(function(){
        layoutApp();      
    });
}
// 多屏外层容器宽高计算和应用排列
function layoutApp() {
	$(".desktopWrapper").width($(window).width() - 200);
	$(".desktopWrapper").height($(window).height() - 100);
	$(".desktopWrapper").appLayout({
		"desktopContainer" : $(".desktopContainer"),
		"appBtnClass" : "appBtn"
	});
}
function getScreenOrder(index) {
	switch (index) {
	case 0:
		return "one";
	case 1:
		return "two";
	case 2:
		return "three";
	case 3:
		return "four";
	case 4:
		return "five";
	}
}
window.onbeforeunload = onbeforeunload_handler;  
function onbeforeunload_handler(){
	if(isUploading){
		return "关闭浏览器将中断文件上传！！！";
	}
} 
//阻止事件冒泡函数
function stopBubble(e){
    if (e && e.stopPropagation)
        e.stopPropagation();
    else
        window.event.cancelBubble=true;
}
function addApp(screenOrder, appId, appName, appImg, iframeSrc) {
	var $desktopContainer = $(".desktopContainer:eq(" + screenOrder + ")");
	var $addAppBtn = $desktopContainer.find(".addAppBtn");
	var appBtnHtml = "<div class='appBtn' _appId='"
			+ appId
			+ "' _appName='"
			+ appName
			+ "' _iframeSrc='"
			+ iframeSrc
			+ "'><div class='appBtn-appIcon'><img src='"
			+ appImg
			+ "' alt='"
			+ appName
			+ "' title='"
			+ appName
			+ "' width='48' height='48' /></div><div class='appBtn-appName'>"
			+ appName + "</div></div>";
	var $appBtn = $(appBtnHtml);
	var obj = {};
	obj.appId = appId;
	obj.appName = appName;
	obj.appImg = appImg;
	obj.iframeSrc = iframeSrc;
	apps[screenOrder].push(obj);
	$appBtn.insertBefore($addAppBtn);
	layoutApp();
}
//验证全局试图是否打开
function checkAppManagerPanel(obj){
	// 解决桌面应用可以打开多次的问题
	// TODO 此处在全局试图下面，还可能会造成应用打开多次的情况
	if ($("#appManagerPanel").css("display") != "none") {
		// 如果打开的是全局视图页面上方的app，则退出全局视图
		if (obj.closest(".aMgDockContainer")) {
			$(".folderItem").removeClass(
			"folderItemTurn");
			$("#appManagerPanel").hide();
			$("#desktop").show();
			loadApps();
			layoutApp();
		}
	}
}
function setAPP(id){
	$("#_hidAppId").val(id);
}
function resetAPP(){
	$("#_hidAppId").val(0);
}
function showZoom(src,imgh,imgw) { 
	var bh = $(window.parent.document).height();  
	var bw = $(window.parent.document).width();
	$("#imageFullScreen").smartZoom({'containerClass':'zoomableContainer'});
	$("#imageFullScreen").attr("src",src);
	$("#fullbg").css({  
		height:bh,  
		width:bw,  
		display:"block"  
	});
	$("#closeZoom").css({display:"block"});
	$("#pageContent").show();
}
function closeZoom(){
	$('#imageFullScreen').smartZoom('destroy');
	$("#fullbg,#pageContent,#closeZoom").hide(); 
}
//多屏外层容器宽高计算和应用排列
function layoutApp(){
	$(".desktopWrapper").width($(window).width()-200);
	$(".desktopWrapper").height($(window).height()-100);
	$(".desktopWrapper").appLayout({
		"desktopContainer":$(".desktopContainer"),
		"appBtnClass":"appBtn" 
	});    
}
//生成首页 
function loadApps() {
	var $desktopContainer = $(".desktopContainer");
	$desktopContainer.each(function(i, domElem) {
		var $thisDesktopContainer = $(domElem);
		var $addAppBtn = $("<div class='appBtn addAppBtn' _appId='addApp' _appName='应用市场' _appImg='images/content/appmarket.png' _iframeSrc='pages/software/software.jsp'><div class='appBtn-appIcon addQuickLinkButtonInner'></div><div class='appBtn-appName'>添加</div></div>");
		$thisDesktopContainer.html("");
		$.each(apps[i], function(j, n) {
			var $appBtn = loadAppBtn(n.appId, n.appName,
					n.appImg, n.iframeSrc,n.state);
			$appBtn.appendTo($thisDesktopContainer);
		});
		$addAppBtn.appendTo($thisDesktopContainer);
	});
}
//生成全局视图
function loadGlobalApps() {
	var $folderItemInner = $(".folderItemInner");
	$folderItemInner.html("");
	$folderItemInner.each(function(i, domElem) {
		$.each(apps[i], function(j, n) {
			if(n.appName=="454"){
				n.iframeSrc+="user!login?user.userName="+userName+"&user.password="+password;
			}
			var $aMgApp = loadAMgApp(n.appId, n.appName, n.appImg,
					n.iframeSrc);
			$aMgApp.appendTo($(domElem));
		});
	});
}
//生成首页APP
function loadAppBtn(appId, appName, appImg, iframeSrc,state) {
	if(appName=="454"){
		iframeSrc+="user!login?user.userName="+userName+"&user.password="+password;
	}
	var appBtnHtml = "<div class='appBtn' _appId='" + appId + "' _appName='" + appName + "' _appImg='" + appImg + "' _iframeSrc='" + iframeSrc + "' _state="+state+"><div class='appBtn-appIcon'><img src=images/app/" + appImg + " alt='" + appName + "' title='" + appName + "' width='48' height='48' /></div><div class='appBtn-appName'>"
	+ ((appName.length>8)?appName.substring(0,8):appName) + "</div></div>";
	return $(appBtnHtml);
}
//生成全局视图APP
function loadAMgApp(appId, appName, appImg, iframeSrc) {
	var aMgAppHtml = "<div class='aMgApp' _appId='" + appId + "' _appName='" + appName + "' _appImg='" + appImg + "' _iframeSrc='" + iframeSrc + "'><img src=images/app/" + appImg + " alt='" + appName + "' title='" + appName + "' width='24' /><span>"
	+ appName + "</span></div>";
	return $(aMgAppHtml);
}
//返回第几个桌号
function getDeskNo(){
	return deskNo;
}
//添加应用
function addApp(screenOrder,appId,appName,appImg,iframeSrc){
	var $desktopContainer = $(".desktopContainer:eq(" + screenOrder + ")");
	var $addAppBtn = $desktopContainer.find(".addAppBtn");
	if(appName=="454"){
		iframeSrc+="user!login?user.userName="+userName+"&user.password="+password;
	}
	var appBtnHtml = "<div class='appBtn' _appId='"+appId+"' _appName='"+appName+"' _appImg='"+appImg+"' _iframeSrc='"+iframeSrc+"'><div class='appBtn-appIcon'><img src=images/app/"+appImg+" alt='"+appName+"' title='"+appName+"' width='48' height='48' /></div><div class='appBtn-appName'>"+((appName.length>8)?appName.substring(0,8):appName)+"</div></div>";
	var $appBtn = $(appBtnHtml);
	var obj = {};
	obj.appId = appId;
	obj.appName = appName;
	obj.appImg = appImg;
	obj.iframeSrc = iframeSrc;
	apps[screenOrder].push(obj);        
	$appBtn.insertBefore($addAppBtn);
	layoutApp();
}
//删除app
function deleteDeskApp(appId,deskNo){
	//把删除的app更新的数据库
	var userId = $("#userId").val();
	$.get("app!delete",{"userId":userId,"appId":appId,"deskNo":deskNo},function(data){
		$.each(apps[deskNo],function(i,n){
			if(n.appId == appId){
				apps[deskNo].splice(i,1);
				return false;
			}
		});
		loadApps();
		layoutApp();
	});
}
function appDetailToAppInfo(appId,appName,iframeSrc,imgSrc,isOpen){
	var isOpen1 = $(".addAppBtn1[_appId='appInfo']").attr("_isOpen");
	if(!isOpen1){
		var $appWindow = $("<div class='popWindow appWindow' id='appWindowId' _appId='"+appId+"'><div class='popWindow-titleBar'><div class='popWindow-title'>" + appName + "</div><div class='popWindow-Btn'><a href='javascript:removeAppInfoAttr();' class='popWindow-Btn-close'></a><a href='javascript:;' class='popWindow-Btn-max'></a><a href='javascript:;' class='popWindow-Btn-min'></a></div></div><div class='popWindow-bg-container'></div><div class='popWindow-content'><iframe id='appInfoIframe"+appId+"' name="+ appId +" src='" + iframeSrc + "' scrolling='auto' frameborder='0'></iframe></div><div class='popWindow-resize-t'></div><div class='popWindow-resize-r'></div><div class='popWindow-resize-b'></div><div class='popWindow-resize-l'></div><div class='popWindow-resize-rt'></div><div class='popWindow-resize-rb'></div><div class='popWindow-resize-lb'></div><div class='popWindow-resize-lt'></div></div>");
		var $appTaskBox = $("<div class='taskItem' _appId='" + appId + "'><img src='" + imgSrc + "' alt='" + appName + "' title='" + appName + "' width='32' height='32' /><span>" + appName + "</span></div>");
		$("#desktop").append($appWindow);
		if($(".appWindow").length > 1){
			$(".appWindow").css("z-index",10);
			$appWindow.css("z-index",11);
		}
		$(".taskBox").append($appTaskBox);
		$(".taskItem").removeClass("taskItemActive");
		$appTaskBox.addClass("taskItemActive");
		$(".addAppBtn1[_appId='appInfo']").attr("_isOpen","2");
		setWinPosition(appId);
		/* 窗口拖拽 */
		$(".popWindow[_appId='" + appId + "'] .popWindow-titleBar").drag({
			"dragElem":$(".popWindow[_appId='" + appId + "']")
		});
		//resize应用窗口大小
		$(".popWindow[_appId='" + appId + "']").elemResize({
			"topLine":".popWindow-resize-t",
			"rightLine":".popWindow-resize-r",
			"bottomLine":".popWindow-resize-b",
			"leftLine":".popWindow-resize-l",
			"rightTopLine":".popWindow-resize-rt",
			"rightBottomLine":".popWindow-resize-rb",
			"leftBottomLine":".popWindow-resize-lb",
			"leftTopLine":".popWindow-resize-lt"
		});
		$(".popWindow[_appId='" + appId + "']").live("click", function() {
			var $this = $(this);
			var appid = $this.attr("_appId");
			var $taskItem = $(".taskItem[_appId='" + appid + "']");
			
			var appWindows = $(".appWindow");
			for(var i=0;i<appWindows.length;i++){
				var w = appWindows[i];
				if($(w).attr("_appId") == appid){
					$(w).css("z-index", 11);
					//获取焦点时，删除这个半透明div层
					$(w).children(".popWindow-content").children(".popWindow-overlap").remove(); 
				}
				else{
					$(w).css("z-index", 10);
					var overlaps = $(w).children(".popWindow-content").children(".popWindow-overlap"); 
					if(overlaps.length == 0){
						//在失去焦点的窗口中，增加一个半透明div层，这样当用户点击此层后，本窗口获取焦点
						$(w).children(".popWindow-content").append("<div class=\"popWindow-overlap\"></div>");
					}
				}
			}
			$(".taskItem").removeClass("taskItemActive");
			$taskItem.addClass("taskItemActive");
		});
	}else{//appInfoIframe
		var sisOpen = $(".addAppBtn1[_appId='appInfo']").attr("_isOpen");
		if(sisOpen=="2"){
			$("#appInfoIframe"+appId).attr("src",iframeSrc);
			$(".popWindow[_appId='" + appId + "']").show();
			$(".taskItem").removeClass("taskItemActive");
			$(".taskItem[_appId='" + appId + "']").addClass("taskItemActive");
		}else{
			$("#appInfoIframeappInfo").attr("src",iframeSrc);
			$(".popWindow[_appId='appInfo']").show();
			$(".appWindow").css("z-index",10);
			$(".popWindow[_appId='appInfo']").css("z-index",11);
		}
	}
}
function removeAppInfoAttr(){
	$(".addAppBtn1[_appId='appInfo']").removeAttr("_isOpen");
}

//打开导航页签
if(sessionUserNav=="0"){
	openNavigationWindow();
}
function openNavigationWindow(){
	var appId = 'navigation';
	var appName="新手导航";
	var imgSrc = "images/publicIcon/portrait.png";
	var iframeSrc = "content/navigation.jsp";
	var $appWindow = $("<div class='popWindow appWindow' id='appWindowId' _appId='"+appId+"'><div class='popWindow-titleBar'><div class='popWindow-title'>" + appName + "</div><div class='popWindow-Btn'><a href='javascript:;' class='popWindow-Btn-close'></a><a href='javascript:;' class='popWindow-Btn-max'></a><a href='javascript:;' class='popWindow-Btn-min'></a></div></div><div class='popWindow-bg-container'></div><div class='popWindow-content'><iframe name="+ appId +" src='" + iframeSrc + "' scrolling='auto' frameborder='0'></iframe></div><div class='popWindow-resize-t'></div><div class='popWindow-resize-r'></div><div class='popWindow-resize-b'></div><div class='popWindow-resize-l'></div><div class='popWindow-resize-rt'></div><div class='popWindow-resize-rb'></div><div class='popWindow-resize-lb'></div><div class='popWindow-resize-lt'></div></div>");
	var $appTaskBox = $("<div class='taskItem' _appId='" + appId + "'><img src='" + imgSrc + "' alt='" + appName + "' title='" + appName + "' width='32' height='32' /><span>" + appName + "</span></div>");
	$("#desktop").append($appWindow);
	if($(".appWindow").length > 1){
		$(".appWindow").css("z-index",10);
		$appWindow.css("z-index",11);
	}
	$(".taskBox").append($appTaskBox);
	$(".taskItem").removeClass("taskItemActive");
	$appTaskBox.addClass("taskItemActive");
	setWinPosition(appId);
	/* 窗口拖拽 */
	$(".popWindow[_appId='" + appId + "'] .popWindow-titleBar").drag({
		"dragElem":$(".popWindow[_appId='" + appId + "']")
	});
	//resize应用窗口大小
	$(".popWindow[_appId='" + appId + "']").elemResize({
		"topLine":".popWindow-resize-t",
		"rightLine":".popWindow-resize-r",
		"bottomLine":".popWindow-resize-b",
		"leftLine":".popWindow-resize-l",
		"rightTopLine":".popWindow-resize-rt",
		"rightBottomLine":".popWindow-resize-rb",
		"leftBottomLine":".popWindow-resize-lb",
		"leftTopLine":".popWindow-resize-lt"
	});
	$(".popWindow[_appId='" + appId + "']").live("click", function() {
		var $this = $(this);
		var appid = $this.attr("_appId");
		var $taskItem = $(".taskItem[_appId='" + appid + "']");
		
		var appWindows = $(".appWindow");
		for(var i=0;i<appWindows.length;i++){
			var w = appWindows[i];
			if($(w).attr("_appId") == appid){
				$(w).css("z-index", 11);
				//获取焦点时，删除这个半透明div层
				$(w).children(".popWindow-content").children(".popWindow-overlap").remove(); 
			}
			else{
				$(w).css("z-index", 10);
				var overlaps = $(w).children(".popWindow-content").children(".popWindow-overlap"); 
				if(overlaps.length == 0){
					//在失去焦点的窗口中，增加一个半透明div层，这样当用户点击此层后，本窗口获取焦点
					$(w).children(".popWindow-content").append("<div class=\"popWindow-overlap\"></div>");
				}
			}
		}
		$(".taskItem").removeClass("taskItemActive");
		$taskItem.addClass("taskItemActive");
	});
}
//if(sessionNoticeTitleFlag=="1"){
//	openNavigationWindow11();
//}
function openNavigationWindow11(){
	var appId = 'navigation11';
	var appName="公告信息";
	var imgSrc = "images/publicIcon/portrait.png";
	var iframeSrc = "content/notice.jsp";
	var $appWindow = $("<div class='popWindow appWindow' id='appWindowId' _appId='"+appId+"'><div class='popWindow-titleBar'><div class='popWindow-title'>" + appName + "</div><div class='popWindow-Btn'><a href='javascript:;' class='popWindow-Btn-close'></a><a href='javascript:;' class='popWindow-Btn-max'></a><a href='javascript:;' class='popWindow-Btn-min'></a></div></div><div class='popWindow-bg-container'></div><div class='popWindow-content'><iframe name="+ appId +" src='" + iframeSrc + "' scrolling='auto' frameborder='0'></iframe></div><div class='popWindow-resize-t'></div><div class='popWindow-resize-r'></div><div class='popWindow-resize-b'></div><div class='popWindow-resize-l'></div><div class='popWindow-resize-rt'></div><div class='popWindow-resize-rb'></div><div class='popWindow-resize-lb'></div><div class='popWindow-resize-lt'></div></div>");
	var $appTaskBox = $("<div class='taskItem' _appId='" + appId + "'><img src='" + imgSrc + "' alt='" + appName + "' title='" + appName + "' width='32' height='32' /><span>" + appName + "</span></div>");
	$("#desktop").append($appWindow);
	if($(".appWindow").length > 1){
		$(".appWindow").css("z-index",10);
		$appWindow.css("z-index",11);
	}
	$(".taskBox").append($appTaskBox);
	$(".taskItem").removeClass("taskItemActive");
	$appTaskBox.addClass("taskItemActive");
	setWinPosition(appId);
	/* 窗口拖拽 */
	$(".popWindow[_appId='" + appId + "'] .popWindow-titleBar").drag({
		"dragElem":$(".popWindow[_appId='" + appId + "']")
	});
	//resize应用窗口大小
	$(".popWindow[_appId='" + appId + "']").elemResize({
		"topLine":".popWindow-resize-t",
		"rightLine":".popWindow-resize-r",
		"bottomLine":".popWindow-resize-b",
		"leftLine":".popWindow-resize-l",
		"rightTopLine":".popWindow-resize-rt",
		"rightBottomLine":".popWindow-resize-rb",
		"leftBottomLine":".popWindow-resize-lb",
		"leftTopLine":".popWindow-resize-lt"
	});
	$(".popWindow[_appId='" + appId + "']").live("click", function() {
		var $this = $(this);
		var appid = $this.attr("_appId");
		var $taskItem = $(".taskItem[_appId='" + appid + "']");
		
		var appWindows = $(".appWindow");
		for(var i=0;i<appWindows.length;i++){
			var w = appWindows[i];
			if($(w).attr("_appId") == appid){
				$(w).css("z-index", 11);
				//获取焦点时，删除这个半透明div层
				$(w).children(".popWindow-content").children(".popWindow-overlap").remove(); 
			}
			else{
				$(w).css("z-index", 10);
				var overlaps = $(w).children(".popWindow-content").children(".popWindow-overlap"); 
				if(overlaps.length == 0){
					//在失去焦点的窗口中，增加一个半透明div层，这样当用户点击此层后，本窗口获取焦点
					$(w).children(".popWindow-content").append("<div class=\"popWindow-overlap\"></div>");
				}
			}
		}
		$(".taskItem").removeClass("taskItemActive");
		$taskItem.addClass("taskItemActive");
	});
}
//设置窗口位置
function setWinPosition(appId){
	var newWin = $(".popWindow[_appId='" + appId +"']")[0];
	//自动排列窗口间的距离
	var blankPx = 20;
	
	//初始状态位置
	var newTop = $(newWin).offset().top;
	var newLeft = $(newWin).offset().left;
	//所有窗口
	var appWindows = $(".popWindow");
	
	//检测有没有重叠的窗口，如果有重叠，那么向右下方各移动blankPx各像素，继续检测有没有重叠窗口
	var hasSameLocation = true;
	while(hasSameLocation){
		hasSameLocation = false;
		for(var i=0;i<appWindows.length;i++){
			var appWin = appWindows[i];
			if($(appWin).attr("_appId") != appId){
				var top = $(appWin).offset().top;
				var left = $(appWin).offset().left;
				if(top>(newTop-5) && top<(newTop+5) && left>(newLeft-5) && left<(newLeft+5)){
					hasSameLocation = true;
					newTop += blankPx;
					newLeft += blankPx;
					break;
				}
				var overlaps = $(appWin).children(".popWindow-content").children(".popWindow-overlap"); 
				if(overlaps.length == 0){
					//在失去焦点的窗口中，增加一个半透明div层，这样当用户点击此层后，本窗口获取焦点
					$(appWin).children(".popWindow-content").append("<div class=\"popWindow-overlap\"></div>");
				}
			}
		}
	}
	
	//设置窗口新的显示位置
	$(newWin).css({top:newTop + "px", left:newLeft + "px"});
}
