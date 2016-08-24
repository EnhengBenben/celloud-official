(function() {
	celloudApp.controller("messageController", function($scope, $rootScope,
			noticeService, commonService) {
		$scope.notices = noticeService.listNotices();
		$scope.noticeRemoveState = false;
		$scope.noticeReadState = false;
		$scope.messageRemoveState = false;
		$scope.messageReadState = false;
		var checkedNotices = [];
		$scope.readNotices = function() {
			var noticeIds = [];
			for (i in checkedNotices) {
				if (checkedNotices[i].readState == 0) {
					noticeIds.push(checkedNotices[i].noticeId);
				}
			}
			noticeService.readNotices({noticeIds:noticeIds},reload);
		};
		$scope.readAllNotices = function() {
			noticeService.readNotices(reload);
		};
		$scope.readMessage = function() {
			noticeService.readMessage(reload);
		};
		$scope.deleteNotice = function() {
			var noticeIds = [];
			for (i in checkedNotices) {
				noticeIds.push(checkedNotices[i].noticeId);
			}
			noticeService.deleteNotice({noticeIds:noticeIds},reload);
		};
		$scope.checkNoticeAll = function(state) {
			if (state) {
				$scope.chk = true;
				checkedNotices = $scope.notices.datas;
			}else{
				$scope.chk = false;
				checkedNotices = [];
			}
			changeNoticeState();
		}
		$scope.checkNoticeOne = function(notice, state) {
			if (state) {
				checkedNotices.push(notice);
			} else {
				checkedNotices = removeNotice(checkedNotices, notice);
			}
			changeNoticeState();
		}
		var changeNoticeState = function() {
			$scope.noticeRemoveState = checkedNotices.length > 0;
			var flag = false;
			for (i in checkedNotices) {
				if (checkedNotices[i].readState == 0) {
					flag = true;
					break;
				}
			}
			$scope.noticeReadState = flag;
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
			$scope.notices = noticeService.listNotices();
			checkedNotices = [];
			$rootScope.messages = commonService.messages.get();
			$rootScope.notices = commonService.notices.get();
			changeNoticeState();
		}
	});
})();