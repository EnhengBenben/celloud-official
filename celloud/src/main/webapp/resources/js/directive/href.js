(function(){
	celloudApp.directive('diHref', ['$location', '$route', function($location, $route) {
		return function(scope, element, attrs) {
			scope.$watch('diHref', function() {
				if(attrs.diHref) {
					element.attr('href', attrs.diHref);
					element.bind('click', function(event) {
						scope.$apply(function(){
							if($location.path() == attrs.diHref.substr(attrs.diHref.indexOf("#/")+1)){
								$route.reload();
							}
						});
					});
				}
			});
		}
	}]);
})()