/**
 * Created by yong on 2017/4/5.
 */
(function () {
  'use strict';

  angular.module('ngDemo')
    .controller('CaseListCtrl', Controller);

  /** @ngInject */

  function Controller($stateParams, $scope) {
    var vm = this;
    vm.status = $stateParams.status;
    $scope.$parent.status = $stateParams.status;
    return init();

    function init() {

    }
  }
})();
