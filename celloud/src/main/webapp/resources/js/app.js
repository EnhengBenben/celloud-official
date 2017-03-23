var appStore=(function(appStore){
	var self=appStore||{};
	
	//默认打开按功能分类
	//self.classifyPid = 1;
	//self.classifySid = 0;
	self.introNext = 0;
	self.sortFiled = null;
	self.sortType=null;
	self.getMyApp=function(){
		$.get("app/myApps",{},function(responseText){
			$("#myAppDiv").html(responseText);
		});
	};
	
	self.toSclassifyApp=function(pid,pname){
		$("#secondClassifyName").parent().addClass("hide");
		$("#appClassifyUl li").removeClass("active");
		$("#classifypidLi"+pid).addClass("active");
		$.get("app/toSclassifyApp",{"paramId":pid},function(responseText){
			$("#sclassify").html(responseText);
//			if(hasNavi == 1 && intro != null && self.introNext==0){
//				intro.exit();
//				intro = null;
//				intro = introJs();
//				intro.setOption('tooltipPosition', 'bottom');
//				intro.setOption('showStepNumbers', false);
//				intro.setOption('showButtons', false);
//				intro.start();
//				intro.goToStep(2);
//			}
//			if(hasNavi == 1 && intro != null && self.introNext==1){
//				intro.exit();
//				intro = null;
//				intro = introJs();
//				intro.setOption('tooltipPosition', 'left');
//				intro.setOption('showStepNumbers', false);
//				intro.setOption('showButtons', false);
//				intro.start();
//				intro.goToStep(3);
//			}
			self.introNext = 1;
		});
		$("#defaultPid").val(pid);
		$("#defaultPname").val(pname);
	};
	
	self.initApp=function(){
		self.getMyApp();
		var pid = $("#defaultPid").val();
		var pname = $("#defaultPname").val();
		self.toSclassifyApp(pid,pname);
	};
	
	self.sortApp=function(){
		$("#sort-listUl li").removeClass("current");
		if(self.sortFiled == "a.create_date"){
			$("#sortByCreateDate").addClass("current");
			if(self.sortType == "desc"){
				$("#sortByCreateDate i").removeClass("up").addClass("down");
			}else{
				$("#sortByCreateDate i").removeClass("down").addClass("up");
			}
		}else{
			$("#defaultSort").addClass("current");
		}
	};
	
	self.toMoreApp=function(pid,sid,pageNum,isParent){
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
//			if(hasNavi == 1 && intro != null){
//				intro.exit();
//				intro = null;
//				intro = introJs();
//				intro.setOption('tooltipPosition', 'auto');
//				intro.setOption('positionPrecedence', ['left', 'right', 'bottom', 'top']);
//				intro.setOption('showStepNumbers', false);
//				intro.setOption('showButtons', false);
//				intro.start();
//				intro.goToStep(2);
//			}
		});
	};
	
	self.toAppDetail=function (id){
		$.get("app/appDetail",{"paramId":id},function(responseText){
			$("#appMain").html(responseText);
			window.scrollTo(0,0);//滚动条回到顶部
//			if(hasNavi == 1 && intro != null){
//				intro.exit();
//				intro = null;
//				intro = introJs();
//				intro.setOption('tooltipPosition', 'right');
//				intro.setOption('showStepNumbers', false);
//				intro.setOption('showButtons', false);
//				intro.start();
//				intro.goToStep(2);
//				
//				$("#manageAppBtns .btn").removeAttr("href");
//				$("#manageAppBtns .btn").removeAttr("onclick");
//				$("#manageAppBtns").bind("click",function(){
//					if(hasNavi == 1 && intro != null){
//						$("#toUploadMenu").attr("data-step",3);
//						intro.exit();
//						intro = null;
//						intro = introJs();
//						intro.setOption('tooltipPosition', 'auto');
//						intro.setOption('positionPrecedence', ['left', 'right', 'bottom', 'top']);
//						intro.setOption('showStepNumbers', false);
//						intro.setOption('showButtons', false);
//						intro.start();
//						intro.goToStep(3);
//					}
//					$("#manageAppBtns").unbind("click");
//				});
//			}
		});
	};
	
	self.toAppMoreDetail=function(id){
		$("#toAppMoreDetailUl li").removeClass("select");
		$("#"+id).addClass("select");
	};

	self.addApp=function(id){
		$.get("app/addApp",{"paramId":id},function(responseText){
			if(responseText>0){
				$("#toAddApp").attr("onclick","appStore.removeApp("+id+")");
				$("#toAddApp").html("<i class=\"fa fa-minus\"></i>&nbsp;取消添加");
				$("#toAddApp").removeClass("btn-celloud-success").addClass("btn-celloud-close");
				self.getMyApp();
				self.refreshProduct();
			}
		});
	};
	
	self.removeApp=function(id){
		$.get("app/removeApp",{"paramId":id},function(responseText){
			if(responseText>0){
				var appId=$("#app-detail-appId").val();
				if(appId==id){
					$("#toAddApp").attr("onclick","appStore.addApp("+id+")");
					$("#toAddApp").html("<i class=\"fa fa-plus\"></i>&nbsp;添加");
					$("#toAddApp").removeClass("btn-celloud-close").addClass("btn-celloud-success");
				}
				self.getMyApp();
				self.refreshProduct();
			}
		});
	};
	
	//调用angularjs中的方法刷新产品
	self.refreshProduct = function(){
	  //通过controller来获取Angular应用
	  var appElement = document.querySelector('[ng-controller=sidebarController]')
	  var element = angular.element(appElement);
	  //获取$scope
	  var $scope = element.scope();
	  //调用$scope中的方法
	  $scope.refreshUserProduct();
	}
	return self;
})(appStore);




