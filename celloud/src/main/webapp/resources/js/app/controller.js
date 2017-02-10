(function(){
	celloudApp.controller("toAppStore",function($route, $scope, appService){
		$scope.toSclassifyApp = function(pid,pname){
			appService.toSclassifyApp(pid,pname).
			success(function(data){
//				$("#secondClassifyName").parent().addClass("hide");
				$("#appClassifyUl li").removeClass("active");
				$("#classifypidLi"+pid).addClass("active");
				$scope.classifyAppMap = data['classifyAppMap'];
				$scope.sclassifys = data['sclassifys'];
//				$("#sclassify").html(data);
//				$("#defaultPid").val(pid);
//				$("#defaultPname").val(pname);
			})
		}
		
		$scope.toAppDetail = function(id){
			$.get("app/appDetail",{"paramId":id},function(responseText){
				$("#appMain").html(responseText);
				window.scrollTo(0,0);//滚动条回到顶部
				if(hasNavi == 1 && intro != null){
					intro.exit();
					intro = null;
					intro = introJs();
					intro.setOption('tooltipPosition', 'right');
					intro.setOption('showStepNumbers', false);
					intro.setOption('showButtons', false);
					intro.start();
					intro.goToStep(2);
					
					$("#manageAppBtns .btn").removeAttr("href");
					$("#manageAppBtns .btn").removeAttr("onclick");
					$("#manageAppBtns").bind("click",function(){
						if(hasNavi == 1 && intro != null){
							$("#toUploadMenu").attr("data-step",3);
							intro.exit();
							intro = null;
							intro = introJs();
							intro.setOption('tooltipPosition', 'auto');
							intro.setOption('positionPrecedence', ['left', 'right', 'bottom', 'top']);
							intro.setOption('showStepNumbers', false);
							intro.setOption('showButtons', false);
							intro.start();
							intro.goToStep(3);
						}
						$("#manageAppBtns").unbind("click");
					});
				}
			});
		};
		
		$scope.toMoreApp = function(pid,sid,pageNum,isParent){
			currentPage = pageNum;
			$("#rootClassifyName").unbind("click");
			$("#secondClassifyName").unbind("click");
			$.get("app/toMoreAppList",{"classifyId":sid,"classifyPid":pid,"classifyFloor":isParent,"size":pageAppNum,"page":currentPage,"condition":self.sortFiled,"type":self.sortType},function(responseText){
				$("#appMain").html(responseText);
				if(pid==0){//针对无二级分类的特殊处理
					$("#rootClassifyName").html($("#pid"+sid).html());
				}else{
					$("#rootClassifyName").html($("#pid"+pid).html());
				}
				if(sid==0||pid==0){
					$("#secondClassifyName").html("全部");
				}else{
					$("#secondClassifyName").html($("#sid"+sid).html());
				}
				$("#rootClassifyName").bind("click",function(){self.toMoreApp(pid,0,1,0);});
				if($("#secondClassifyName").html()=="全部"){
					$("#secondClassifyName").bind("click",function(){self.toMoreApp(pid,0,1,0);});
				}else{
					$("#secondClassifyName").bind("click",function(){self.toMoreApp(pid,sid,pageNum,isParent);});
				}
				$("#secondClassifyName").parent().removeClass("hide");
				$("#sortByCreateDate").bind("click",function(){
		            self.sortFiled = "a.create_date";
		            if(self.sortType == "desc"){
		            	self.sortType="asc";
					}else{
						self.sortType="desc";
					}
		            self.toMoreApp(pid,sid,1,isParent);
				});
				$("#defaultSort").bind("click",function(){
		            self.sortFiled = null;
		            self.sortType=null;
				});
				self.sortApp();
				if(hasNavi == 1 && intro != null){
					intro.exit();
					intro = null;
					intro = introJs();
					intro.setOption('tooltipPosition', 'auto');
					intro.setOption('positionPrecedence', ['left', 'right', 'bottom', 'top']);
					intro.setOption('showStepNumbers', false);
					intro.setOption('showButtons', false);
					intro.start();
					intro.goToStep(2);
				}
			});
		};
		
		$scope.removeApp = function(id){
			$.get("app/removeApp",{"paramId":id},function(responseText){
				if(responseText == -1){
					alert("测试账号不允许修改App");
				}
				if(responseText>0){
					appService.getMyApp().
					success(function(data){
						$scope.appList = data;
						$scope.refreshProduct();
					});
				}
			});
		};
		
		$scope.refreshProduct = function(){
			//通过controller来获取Angular应用
		    var appElement = document.querySelector('[ng-controller=sidebarController]')
		    var element = angular.element(appElement);
		    //获取$scope
		    var $scope = element.scope();
		    //调用$scope中的方法
		    $scope.refreshUserProduct();
		}
		
		appService.toAppStore().
		success(function(data){
			$scope.pclassifys = data;
			appService.getMyApp().
			success(function(data){
				$scope.appList = data;
				var pid = $scope.pclassifys[0].classifyId;
				var pname = $scope.pclassifys[0].classifyName;
				$scope.toSclassifyApp(pid,pname);
			});
		});
	});
	
	
	celloudApp.controller("toAppDetail",function($routeParams, $route, $scope, appService){
		appService.getAppDetail($routeParams.pid).
		success(function(data){
			$scope.app = data.app;
			$scope.screenList = data.screenList;
			window.scrollTo(0,0);//滚动条回到顶部
			appService.getMyApp().
			success(function(data){
				$scope.appList = data;
			});
		});
		
		$scope.refreshProduct = function(){
			//通过controller来获取Angular应用
		    var appElement = document.querySelector('[ng-controller=sidebarController]')
		    var element = angular.element(appElement);
		    //获取$scope
		    var $scope = element.scope();
		    //调用$scope中的方法
		    $scope.refreshUserProduct();
		}
		
		$scope.toAppMoreDetail = function(id){
			$("#toAppMoreDetailUl li").removeClass("select");
			$("#"+id).addClass("select");
		};
		
		$scope.addApp = function(id){
			$.get("app/addApp",{"paramId":id},function(responseText){
				if(responseText == -1){
					alert("测试账号不允许修改App");
				}
				if(responseText>0){
//					$("#toAddApp").attr("onclick","appStore.removeApp("+id+")");
//					$("#toAddApp").html("<i class=\"fa fa-minus\"></i>&nbsp;取消添加");
//					$("#toAddApp").removeClass("btn-celloud-success").addClass("btn-celloud-close");
					$scope.app.isAdded = 1;
					appService.getMyApp().
					success(function(data){
						$scope.appList = data;
						$scope.refreshProduct();
					});
				}
			});
		};
		
		$scope.removeApp = function(id){
			$.get("app/removeApp",{"paramId":id},function(responseText){
				if(responseText == -1){
					alert("测试账号不允许修改App");
				}
				if(responseText>0){
//					var appId=$("#app-detail-appId").val();
//					if(appId==id){
//						$("#toAddApp").attr("onclick","appStore.addApp("+id+")");
//						$("#toAddApp").html("<i class=\"fa fa-plus\"></i>&nbsp;添加");
//						$("#toAddApp").removeClass("btn-celloud-close").addClass("btn-celloud-success");
//					}
					if($scope.app.appId == id){
						$scope.app.isAdded = 0;
					}
					appService.getMyApp().
					success(function(data){
						$scope.appList = data;
						$scope.refreshProduct();
					});
				}
			});
		};
		
	});
	
	
})();