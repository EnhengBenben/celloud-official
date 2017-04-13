(function(){
  celloudApp.controller("appClassifyCtrl",['$rootScope','AppService',function($rootScope, AppService){
    if($rootScope.classifys == undefined){
      //APP分类
      AppService.classifys().success(function(data) {
        $rootScope.classifys = data;
      });
    }
  }]);
  celloudApp.controller("appMainCtrl",['$scope','AppService',function($scope, AppService){
    //获取APP
    $scope.getApp = getApp;
    $scope.appSwiperPre = appSwiperPre;
    $scope.appSwiperNext = appSwiperNext;
    return init();
    var viewSwiper;
    var previewSwiper;
    function init(){
      classicApps();
      recommendApps();
      classifysApps();
      
      viewSwiper = new Swiper('.view .swiper-container', {
        initialSlide: 0,
        observer: true,//修改swiper自己或子元素时，自动初始化swiper
        observeParents: true //修改swiper的父元素时，自动初始化swiper
      });
      previewSwiper = new Swiper('.preview .swiper-container', {
        direction : 'vertical',
        visibilityFullFit: true,
        slidesPerView: 'auto',
        onlyExternal: true,
        observer: true,//修改swiper自己或子元素时，自动初始化swiper
        observeParents: true, //修改swiper的父元素时，自动初始化swiper
        slideToClickedSlide:true,
        onClick: function() {
            viewSwiper.slideTo(previewSwiper.clickedIndex)
        }
      })
    }
    
    function appSwiperPre(){
      if (viewSwiper.activeIndex == 0) {
        viewSwiper.slideTo(viewSwiper.slides.length - 1, 1000);
        return;
      }
      viewSwiper.slidePrev();
    }
    function appSwiperNext(){
      if (viewSwiper.activeIndex == viewSwiper.slides.length - 1) {
        viewSwiper.slideTo(0, 1000);
        return
      }
      viewSwiper.slideNext();
    }
    //获取精选APP
    function classicApps(){
      AppService.classic().success(function(data) {
        $scope.classicApps = data;
      });
    }
    //获取推荐APP
    function recommendApps(){
      AppService.recommend().success(function(data) {
        $scope.recommendApps = data;
      });
    }
    //获取分类APP列表
    function classifysApps(){
      AppService.classifyApps().success(function(data) {
        $scope.classifysApps = data;
      });
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