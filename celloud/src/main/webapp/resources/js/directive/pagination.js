(function() {
	celloudApp.directive("pagination", function() {
		return {
			restrict : 'E',
			replace : true,
			scope : {
				page : '=',
				change : '&'
			},
			templateUrl : 'pages/partial/_partial_pagination_template.jsp',
			controller : function($scope) {
				$scope.pageArray = function(totalPage) {
					var p = [];
					for (i = 1; i <= totalPage; i++) {
						p[i - 1] = i;
					}
					return p;
				}
			}
		}
	});
})();