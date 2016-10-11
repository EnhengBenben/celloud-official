(function() {
	celloudApp.controller("noticeController", function($scope, $rootScope,
			noticeService, commonService) {
		var pages = {
			page : 1,
			pageSize : 10
		};
		$scope.checkedNotices = [];
		$scope.noticeRemoveState = false;
		$scope.noticeReadState = false;
		$scope.changePage = function(page, pageSize) {
			pages = {
				page : page,
				pageSize : pageSize
			};
			noticeService.listNotices({
				currentPage : page,
				pageSize : pageSize
			}, function(data) {
				$scope.notices = data;
				$scope.checkedNotices = [];
				changeNoticeState();
			});
		};
		$scope.changePage(pages.page,pages.pageSize);
		$scope.readNotices = function() {
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
		$scope.readAllNotices = function() {
			noticeService.readNotices(function() {
				reload(null, null);
			});
		};
		$scope.deleteNotice = function() {
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
		$scope.checkNoticeAll = function() {
			$scope.checkedNotices = $scope.checkNoticeAllState?$scope.notices.datas:[];
			changeNoticeState();
		}
		$scope.checkNoticeOne = function(notice) {
			var index = $scope.checkedNotices.indexOf(notice);
			if(index == -1){
				$scope.checkedNotices.push(notice);	
			}else{
				$scope.checkedNotices.splice(index, 1);
			}
			changeNoticeState();
		}
		var changeNoticeState = function() {
			$scope.removeState = $scope.checkedNotices.length > 0;
			var flag = false;
			for (var i in $scope.checkedNotices) {
				if ($scope.checkedNotices[i].readState == 0) {
					flag = true;
					break;
				}
			}
			$scope.readState = flag;
			$scope.checkNoticeAllState = $scope.notices.datas.length==$scope.checkedNotices.length;
		}
		var reload = function(page, pageSize) {
			noticeService.listNotices({
				currentPage : page || pages.page,
				pageSize : pageSize || pages.pageSize
			},function(data){
				$scope.notices = data;
				$scope.checkedNotices = [];
				changeNoticeState();
			});
			$rootScope.notices = commonService.notices.get();
		}
	});
})();