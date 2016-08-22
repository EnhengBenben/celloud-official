(function(){
	celloudApp.controller("toAppStore",function($scope,appService){
		$scope.toSclassifyApp = function(pid,pname){
			appService.toSclassifyApp(pid,pname).
			success(function(data){
				$("#secondClassifyName").parent().addClass("hide");
				$("#appClassifyUl li").removeClass("active");
				$("#classifypidLi"+pid).addClass("active");
				$("#sclassify").html(data);
				if(hasNavi == 1 && intro != null && self.introNext==0){
					intro.exit();
					intro = null;
					intro = introJs();
					intro.setOption('tooltipPosition', 'bottom');
					intro.setOption('showStepNumbers', false);
					intro.setOption('showButtons', false);
					intro.start();
					intro.goToStep(2);
				}
				if(hasNavi == 1 && intro != null && self.introNext==1){
					intro.exit();
					intro = null;
					intro = introJs();
					intro.setOption('tooltipPosition', 'left');
					intro.setOption('showStepNumbers', false);
					intro.setOption('showButtons', false);
					intro.start();
					intro.goToStep(3);
				}
				self.introNext = 1;
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