/**
 * Created by yong on 2017/4/5.
 */
(function () {
  'use strict';

  angular.module('ngDemo')
    .controller('CaseLayoutCtrl', Controller);

  /** @ngInject */

  function Controller($stateParams, $scope) {
    var vm = this;
      vm.status = $stateParams.status;
      console.log(vm.status);
    return init();

    function init() {

    }
  }
})();
