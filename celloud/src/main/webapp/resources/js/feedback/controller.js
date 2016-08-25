(function() {
	celloudApp.controller("feedbackController", function($scope,
			feedbackService) {
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
				$scope.current = feedbackService.get({
					id : id
				});
			});
		}
	});
})();