(function() {
  var intro;
	celloudApp.controller("sidebarController", function($scope,
			$location, $rootScope, $timeout, commonService) {
		
		$('#imageFullScreen').smartZoom({'containerClass':'zoomableContainer'});
		var revise = $(document).width()-$('.sidebar-menu').width();
		$("#pageContent").css("width",revise+"px");
		$('.sidebar-menu').resize(function(){
			revise = $(document).width()-$(this).width();
			$("#pageContent").css("width",revise+"px");
		});
		$rootScope.showZoom = function(src) {
			var bh = $("body").height();  
			var bw = $("body").width();
			$("#imageFullScreen").smartZoom({'containerClass':'zoomableContainer'});
			$("#imageFullScreen").attr("src",src);
			$("#fullbg").css({  
				height:bh,  
				width:bw,  
				display:"block"  
			});
			$("#closeZoom").css("display","block");
			$("#pageContent").show();
		}
		$rootScope.closeZoom = function(){
			$('#imageFullScreen').smartZoom('destroy');
			$("#fullbg,#pageContent,#closeZoom").hide(); 
		}
		/**
	     * HBV峰图放大
	     * @param src
	     */
	    $rootScope.bigFigure = function(src){
	        $("img[id='imageFullScreen']").css("width",960);
	        $("img[id='imageFullScreen']").css("height",144);
	        $rootScope.showZoom(src);
	    }
	    /**
	     * 原始峰图放大
	     * @param src
	     * @param id
	     */
	    $rootScope.bigOrigin = function(src,id) { 
	        var width = $("#" + id).width();
	        var height = $("#" + id).height();
	        $("img[id='imageFullScreen']").css("width",width*1.5);
	        $("img[id='imageFullScreen']").css("height",height*1.5);
	        $rootScope.showZoom(src);
	    }
	    /**
	     * 有路径替换的
	     * @param src
	     */
	    $rootScope.bigReplace = function(src){
	        $("img[id='imageFullScreen']").css("width",1050);
	        $("img[id='imageFullScreen']").css("height",157.5);
	        $rootScope.showZoom(src);
	    }
	    
	    /**
	     * 用于记录数据报告中哪个数据被点击过的json数组
	     * proId:项目id
	     * dataKeys:该项目下的dataKey
	     * [{proId:proId,dataKeys:[dataKey1,dataKey2,...]},{proId:proId,dataKeys:[dataKey1,dataKey2,...]},...]
	     */
	    window.rememberDataReport = new Array();
		
		$scope.isActive = function(viewLocation) {
			if (viewLocation != "/") {
				return $location.path().indexOf(viewLocation) >= 0;
			}
			return viewLocation === $location.path();
		};
		$rootScope.collapsed = false;
		$scope.toggleCollapse = function() {
			if ($scope.collapsed) {
				$scope.collapsed = false;
			} else {
				$scope.collapsed = true;
			}
		};
		$rootScope.errorInfo = "";
		$scope.refreshUserProduct = function(){
		  $rootScope.userProduct = commonService.getProduct().get();
		}
		$rootScope.userProduct = commonService.getProduct().get();
		$rootScope.userInfo = commonService.getUserInfo().get();
		$rootScope.messages = commonService.messages.get();
		$rootScope.notices = commonService.notices.get();
		$rootScope.tipsModal = function(info) {
			$rootScope.errorInfo = info;
			$("#tips-modal").modal("show");
		}
		/**
		 * 修改更多按钮
		 */
		$rootScope.changeChevronType = function(chevronType){
		  if(chevronType){
		    chevronType = false;
		  }else{
		    chevronType = true;
		  }
		  return chevronType;
		}
		$rootScope.pageArray = function(totalPage){
		  var p = [];
		  for(i=1;i<=totalPage;i++){
		    p[i-1]=i;
		  }
		  return p;
		}
		
		
		/**
		 * 监听userMessage频道，有新消息时，刷新右上角提醒
		 */
		messageUtils.subscribe("userMessage", function(data) {
			$rootScope.messages = commonService.messages.get();
			console.log("监听userMessage");
			messageUtils.notify(data.noticeTitle, data.noticeContext, {}, {
				"onclick" : function() {
					var notification = this;
					notification.close();
				}
			});
		});
		/**
		 * 监听userNotice频道，有新消息时，刷新右上角提醒
		 */
		messageUtils.subscribe("userNotice", function(data) {
			$rootScope.notices = commonService.notices.get();
			console.log("监听userNotice");
			messageUtils.notify(data.noticeTitle, data.noticeContext, {}, {
				"onclick" : function() {
					var notification = this;
					notification.close();
				}
			});
		});
		$timeout(function () {
		  if(window.navigation==1 && !$rootScope.userProduct.onlyBSI){
	      intro = introJs();
	      intro.setOption('tooltipPosition', 'bottom');
	      intro.setOption('positionPrecedence', ['left', 'right', 'bottom', 'top']);
	      intro.setOption('showStepNumbers', false);
	      intro.setOption('showButtons', true);
	      intro.start();
	    }
    }, 1000);
	});
})();
