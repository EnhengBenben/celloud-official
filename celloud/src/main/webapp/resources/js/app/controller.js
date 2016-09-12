(function(){
	celloudApp.controller("toAppStore",function($scope,appService){
		$scope.toSclassifyApp = function(pid,pname){
			appService.toSclassifyApp(pid,pname).
			success(function(data){
				$("#secondClassifyName").parent().addClass("hide");
				$("#appClassifyUl li").removeClass("active");
				$("#classifypidLi"+pid).addClass("active");
				$("#sclassify").html(data);
				$("#defaultPid").val(pid);
				$("#defaultPname").val(pname);
			})
		}
		appService.toAppStore().
		success(function(data){
			$scope.pclassifys = data;
			appService.getMyApp().
			success(function(data){
				$("#myAppDiv").html(data);
				var pid = $("#defaultPid").val();
				var pname = $("#defaultPname").val();
				$scope.toSclassifyApp(pid,pname);
			});
		});
	});
})();