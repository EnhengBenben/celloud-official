(function() {
	celloudApp.service("feedbackService", function($resource) {
		var actions = {
			listAll : {
				method : 'get'
			},
			reply : {
				url : 'feedback/reply/:id',
				method : 'put',
				params : {
					id : '@id',
					content : '@content'
				}
			},
			solve : {
				url : 'feedback/solve/:id',
				method : 'post',
				params : {
					id : '@id'
				}
			},
			list : {
				url : 'feedback/list',
				method : 'get'
			},
			save : {
				url : "feedback/create",
				method : 'put',
				params : {
					title : '@title',
					content : '@content',
					attachList : '@attachments'
				}
			},
			deleteAttach : {
				url : "feedback/attach/delete",
				method : 'post',
				params : {
					name : '@name',
					_method:'delete',
					attachId : '@attachId'
				}
			}
		};
		return $resource("feedback/:id", {
			id : '@id'
		}, actions);
	});
})();