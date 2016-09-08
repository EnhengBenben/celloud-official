(function() {
	celloudApp.controller("sidebarController", function($scope,
			$location, $rootScope,commonService) {
		
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
				$(".view-container").css("margin-left","170px");
			} else {
				$scope.collapsed = true;
				$(".view-container").css("margin-left","50px");
			}
		};
		$rootScope.errorInfo = "";
		$rootScope.userInfo = commonService.getUserInfo().get();
		$rootScope.messages = commonService.messages.get();
		$rootScope.notices = commonService.notices.get();
		$rootScope.tipsModal = function(info) {
			$rootScope.errorInfo = info;
			$("#tips-modal").modal("show");
		}
		
		/**
		 * 监听userMessage频道，有新消息时，刷新右上角提醒
		 */
		messageUtils.subscribe("userMessage", function(data) {
			$rootScope.messages = commonService.messages.get();
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
			messageUtils.notify(data.noticeTitle, data.noticeContext, {}, {
				"onclick" : function() {
					var notification = this;
					notification.close();
				}
			});
		});
		
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
	});
})();
