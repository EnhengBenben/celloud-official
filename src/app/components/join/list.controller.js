/**
 * Created by yong on 2017/4/5.
 */
(function () {
  'use strict';

  angular.module('ngDemo')
    .controller('JoinListCtrl', Controller);

  /** @ngInject */

  function Controller($stateParams, $state, $scope) {
    var vm = this;
    $scope.$parent.$state = $state;
    vm.status = $stateParams.status;
    vm.item = $stateParams.item;
    return init();

    function init() {

    }
  }
})();
