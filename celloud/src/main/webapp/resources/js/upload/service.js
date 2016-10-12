(function() {
	celloudApp.service("uploadService", function($resource) {
		this.getProductTags = function(){
			return $resource("uploadFile/getProductTag",{cache: false});
		}
	});
})();
