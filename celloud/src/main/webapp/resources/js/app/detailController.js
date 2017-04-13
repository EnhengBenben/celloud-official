(function(){
  celloudApp.controller("appDetailCtrl",['$scope','$routeParams','AppService',function($scope, $routeParams, AppService){
    //分页检索
    $scope.getApp = getApp;
    $scope.pageQuery = pageQuery;
    $scope.updateScore = updateScore;
    $scope.updateComment = updateComment;
    $scope.appScreenShow = appScreenShow;
    return init();
    function init(){
      AppService.appDetail($routeParams.id).success(function(data) {
    	$(document).scrollTop(0);
        $scope.appInfos = data;
        $scope.countScore5 = data.countScore[5]==undefined?0:data.countScore[5].count;
        $scope.countScore4 = data.countScore[4]==undefined?0:data.countScore[4].count;
        $scope.countScore3 = data.countScore[3]==undefined?0:data.countScore[3].count;
        $scope.countScore2 = data.countScore[2]==undefined?0:data.countScore[2].count;
        $scope.countScore1 = data.countScore[1]==undefined?0:data.countScore[1].count;
        
        $scope.scoreStype5 = {"width":($scope.countScore5/data.totalCount)*100+"%"};
        $scope.scoreStype4 = {"width":($scope.countScore4/data.totalCount)*100+"%"};
        $scope.scoreStype3 = {"width":($scope.countScore3/data.totalCount)*100+"%"};
        $scope.scoreStype2 = {"width":($scope.countScore2/data.totalCount)*100+"%"};
        $scope.scoreStype1 = {"width":($scope.countScore1/data.totalCount)*100+"%"};
        $scope.screenShow = data.screens[0];
      });
      $scope.currentPage = 1;
      $scope.pageSize = 10;
      appComments();
    }
    function appComments(){
      AppService.appComments($routeParams.id,$scope.currentPage,$scope.pageSize).success(function(data) {
        $scope.appComments = data;
      });
    }
    function pageQuery(currentPage,pageSize){
      $scope.currentPage = currentPage;
      $scope.pageSize = pageSize;
      appComments();
    }
    function updateScore(score){
	  $(".user-click").attr("class","user-click score-icon score-gray-null");
      for(var i=1;i<=score;i++){
        $("#score-"+i).attr("class","user-click score-icon score-full");
      }
      $scope.userScore = score;
    }
    function updateComment(){
    	if(!$scope.userScore||!$scope.userComment){
    		$.tips("评分和评论均不能为空！");
    		return;
    	}
      AppService.updateComment($routeParams.id,$scope.userScore,$scope.userComment).success(function() {
        $.tips("评论成功");
        init();
      });
    }
    function appScreenShow(img,index){
      $scope.screenShow = img;
      $("#screens-ul .active").removeClass("active");
      $("#screen-"+index).addClass("active");
    }
    //获取APP授权
    function getApp(appId){
      AppService.updateAdd(appId)
      .then(
          function successCallback(res) {
            console.log("successCallback" + res.status +res.status==400);
            init();
            // 请求成功执行代码
          }, function errorCallback(res) {
            console.log("errorCallback" + res.status);
            if(res.status==400){
              $.tips("您还没有此应用的使用权限，请通过右侧“联系我们”获取!");
            }
          }
      );
    }
  }]);
})();