(function() {
	celloudApp.controller("messageController", function($scope, $rootScope,
			noticeService, commonService) {
		$scope.messages = noticeService.listMessage();
		$scope.removeState = false;
		$scope.readState = false;
		var checkedNotices = [];
		$scope.read = function() {
			var noticeIds = [];
			for (i in checkedNotices) {
				if (checkedNotices[i].readState == 0) {
					noticeIds.push(checkedNotices[i].noticeId);
				}
			}
			noticeService.read({noticeIds:noticeIds},reload);
		};
		$scope.readAll = function() {
			noticeService.readMessage(reload);
		};
		$scope.readMessage = function() {
			noticeService.readMessage(reload);
		};
		$scope.remove = function() {
			var noticeIds = [];
			for (i in checkedNotices) {
				noticeIds.push(checkedNotices[i].noticeId);
			}
			noticeService.deleteNotice({noticeIds:noticeIds},reload);
		};
		$scope.checkAll = function(state) {
			if (state) {
				$scope.chkall = true;
				checkedNotices = $scope.messages.datas;
			}else{
				$scope.chkall = false;
				checkedNotices = [];
			}
			changeState();
		}
		$scope.checkOne = function(notice, state) {
			if (state) {
				checkedNotices.push(notice);
			} else {
				checkedNotices = removeNotice(checkedNotices, notice);
			}
			changeState();
		}
		var changeState = function() {
			$scope.removeState = checkedNotices.length > 0;
			var flag = false;
			for (i in checkedNotices) {
				if (checkedNotices[i].readState == 0) {
					flag = true;
					break;
				}
			}
			$scope.readState = flag;
		}
		var removeNotice = function(notices, notice) {
			var index = -1;
			for (i in notices) {
				if (notices[i].noticeId == notice.noticeId) {
					index = i;
					break;
				}
			}
			if (index != -1) {
				notices.splice(index, 1);
			}
			return notices;
		}
		var reload = function(){
			$scope.messages = noticeService.listMessage();
			checkedNotices = [];
			$rootScope.messages = commonService.messages.get();
			changeState();
		}
	});
})();