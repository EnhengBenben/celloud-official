(function() {
	celloudApp.controller("noticeController", function($scope, $rootScope,
			noticeService, commonService) {
		$scope.notices = noticeService.listNotices();
		$scope.noticeRemoveState = false;
		$scope.noticeReadState = false;
		$scope.changePage = function(page, pageSize) {
			$scope.notices = noticeService.listNotices({
				currentPage : page,
				pageSize : pageSize
			});
		}
		var checkedNotices = [];
		$scope.readNotices = function() {
			var noticeIds = [];
			for (i in checkedNotices) {
				if (checkedNotices[i].readState == 0) {
					noticeIds.push(checkedNotices[i].noticeId);
				}
			}
			noticeService.read({
				noticeIds : noticeIds
			}, reload);
		};
		$scope.readAllNotices = function() {
			noticeService.readNotices(reload);
		};
		$scope.deleteNotice = function() {
			var noticeIds = [];
			for (i in checkedNotices) {
				noticeIds.push(checkedNotices[i].noticeId);
			}
			noticeService.deleteNotice({
				noticeIds : noticeIds
			}, reload);
		};
		$scope.checkNoticeAll = function(state) {
			if (state) {
				$scope.chkall = true;
				checkedNotices = $scope.notices.datas;
			} else {
				$scope.chkall = false;
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
		var reload = function() {
			$scope.notices = noticeService.listNotices();
			checkedNotices = [];
			$rootScope.notices = commonService.notices.get();
			changeNoticeState();
		}
	});
})();