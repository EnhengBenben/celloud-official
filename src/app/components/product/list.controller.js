/**
 * Created by yong on 2017/4/7.
 */
(function () {
  'use strict';

  angular.module('ngDemo')
    .controller('ProductListCtrl', Controller);

  /** @ngInject */
  function Controller($stateParams) {
    var vm = this;
    vm.stateParams = $stateParams;
    vm.params1 = params1;
    vm.params2 = params2;
    return init();

    function init() {
      vm.clinical = 'f1';
      vm.healthy = 'h1';
     // ui-sref="app.product.list({clinical: 'f5',healthy: vm.stateParams.healthy})"
    }

    function params1(params) {
      vm.clinical = params;
      console.log(vm.clinical);
    }

    function params2(params) {
      vm.healthy = params;
      console.log(vm.healthy);
    }
  }
})();
