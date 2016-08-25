(function() {
	celloudApp.controller("feedbackController", function($scope,
			feedbackService) {
		$scope.replyContent='';
		$scope.feedbacks = feedbackService.list(function(data) {
			if (data.datas.length > 0) {
				$scope.change(data.datas[0].id);
			}
		});
		$scope.change = function(id) {
			$scope.current = feedbackService.get({
				id : id
			});
		};
		$scope.solve = function(id) {
			feedbackService.solve({
				id : id
			}, function() {
				$scope.change(id);
			});
		}
		$scope.reply = function(id, content) {
			if(!content){
				return;
			}
			feedbackService.reply({
				id : id,
				content : content
			}, function() {
				$scope.change(id);
				$scope.replyContent = '';
			});
		}
		$scope.replyKeyUp = function($event) {
			if ($event.keyCode == 13) {
				$scope.reply($scope.current.id, $scope.replyContent);
			}
		}
	});
})();