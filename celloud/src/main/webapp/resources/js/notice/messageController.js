(function() {
	celloudApp.controller("messageController", function($scope, $rootScope,
			noticeService, commonService) {
		var pages = {
			page : 1,
			pageSize : 10
		};
		$scope.checkedNotices = [];
		$scope.removeState = false;
		$scope.readState = false;
		$scope.read = function() {
			var noticeIds = [];
			for (var i in $scope.checkedNotices) {
				if ($scope.checkedNotices[i].readState == 0) {
					noticeIds.push($scope.checkedNotices[i].noticeId);
				}
			}
			noticeService.read({
				noticeIds : noticeIds
			}, function() {
				reload(null, null);
			});
		};
		$scope.changePage = function(page, pageSize) {
			pages = {
				page : page,
				pageSize : pageSize
			};
			noticeService.listMessage({
				currentPage : page,
				pageSize : pageSize
			}, function(data) {
				$scope.messages = data;
				$scope.checkedNotices = [];
				changeState();
			});
		};
		$scope.changePage(pages.page, pages.pageSize);
		$scope.readAll = function() {
			noticeService.readMessage(function() {
				reload(null, null);
			});
		};
		$scope.readMessage = function() {
			noticeService.readMessage(function() {
				reload(null, null);
			});
		};
		$scope.remove = function() {
			var noticeIds = [];
			for (var i in $scope.checkedNotices) {
				noticeIds.push($scope.checkedNotices[i].noticeId);
			}
			noticeService.deleteNotice({
				noticeIds : noticeIds
			}, function() {
				reload(1, null);
			});
		};
		$scope.checkAll = function() {
			$scope.checkedNotices = $scope.checkAllState?$scope.messages.datas:[];
			changeState();
		}
		$scope.checkOne = function(notice) {
			var index = $scope.checkedNotices.indexOf(notice);
			if(index == -1){
				$scope.checkedNotices.push(notice);	
			}else{
				$scope.checkedNotices.splice(index, 1);
			}
			changeState();
		}
		var changeState = function() {
			$scope.removeState = $scope.checkedNotices.length > 0;
			var flag = false;
			for (var i in $scope.checkedNotices) {
				if ($scope.checkedNotices[i].readState == 0) {
					flag = true;
					break;
				}
			}
			$scope.readState = flag;
			$scope.checkAllState = $scope.messages.datas.length==$scope.checkedNotices.length;
		}
		var reload = function(page, pageSize) {
			$scope.messages = noticeService.listMessage({
				currentPage : page || pages.page,
				pageSize : pageSize || pages.pageSize
			});
			checkedNotices = [];
			$rootScope.messages = commonService.messages.get();
			changeState();
		}
	});
	celloudApp
			.controller(
					"settingController",
					function($scope, messageService) {
						$scope.userMessageCategoryList = messageService
								.getUserSetting().query();
						$scope.updateMessageCategory = function(flag, mcId,
								targetName, targetVal) {
							var index = this.$index;
							if (flag == 0) { // 第一次更改消息设置, 需要插入到关系表中
								messageService
										.insertUserSetting(targetName,
												targetVal, mcId)
										.success(
												function(data) {
													$scope.userMessageCategoryList[index]['flag'] = 1;
													$scope.userMessageCategoryList[index][targetName
															.toLocaleLowerCase()] = targetVal;
												});
							} else { // 已经更改过消息设置, 更新自己的消息设置
								messageService
										.updateUserSetting(targetName,
												targetVal, mcId)
										.success(
												function(data) {
													$scope.userMessageCategoryList[index][targetName
															.toLocaleLowerCase()] = targetVal;
												});
							}
						}
					});
})();