(function() {
  'use strict';

  angular
    .module('ngDemo')
    .config(routerConfig);

  /** @ngInject */
  function routerConfig($stateProvider, $urlRouterProvider) {
    $stateProvider
      .state('app', {
        url: '/app',
        templateUrl: 'app/main/main.html',
        controller: 'MainController as app'
      })
        .state('app.dashboard', {
            url: '/dashboard',
          abstract: true,
            templateUrl: 'app/components/dashboard/layout.html'
        })
      .state('app.dashboard.list', {
        url: '/list',
        templateUrl: 'app/components/dashboard/list.html',
        controller: 'DashboardListCtrl as vm'
      })
      .state('app.join', {
        url: '/join',
        abstract: true,
        templateUrl: 'app/components/join/layout.html',
        controller: 'JoinLayoutCtrl as vm'
      })
      .state('app.join.list', {
        url: '/list?status&item',
        templateUrl: 'app/components/join/list.html',
        controller: 'JoinListCtrl as vm'
      })
      .state('app.case', {
        url: '/case',
        abstract: true,
        templateUrl: 'app/components/cases/layout.html',
        controller: 'CaseLayoutCtrl as vm'
      })
      .state('app.case.list', {
        url: '/list?status',
        templateUrl: 'app/components/cases/list.html',
        controller: 'CaseListCtrl as vm'
      })
      .state('app.product', {
        url: '/product',
        abstract: true,
        templateUrl: 'app/components/product/layout.html',
      })
      .state('app.product.list', {
        url: '/list?clinical&healthy',
        templateUrl: 'app/components/product/list.html',
        controller: 'ProductListCtrl as vm'
      })
      .state('app.product.mulan', {
        url: '/mulan',
        abstract: true,
        templateUrl: 'app/components/product/mulan/layout.html',
      })
      .state('app.product.mulan.list', {
        url: '/list?status',
        templateUrl: 'app/components/product/mulan/list.html',
        controller: 'ProductMulanListCtrl as vm'
      })
      .state('app.product.bacteria', {
        url: '/bacteria',
        abstract: true,
        templateUrl: 'app/components/product/bacteria/layout.html',
      })
      .state('app.product.bacteria.list', {
        url: '/bacteria?status',
        templateUrl: 'app/components/product/bacteria/list.html',
        controller: 'ProductBacteriaListCtrl as vm'
      })
      .state('app.about', {
        url: '/about',
        abstract: true,
        templateUrl: 'app/components/about/layout.html'
      })
      .state('app.about.list', {
        url: '/list',
        templateUrl: 'app/components/about/list.html'
      });
    $urlRouterProvider.otherwise('/app/dashboard/list');
  }

})();
