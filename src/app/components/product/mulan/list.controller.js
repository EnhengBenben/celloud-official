/**
 * Created by yong on 2017/4/11.
 */
(function () {
  'use strict';

  angular.module('ngDemo')
    .controller('ProductMulanListCtrl', Controller);

  /** @ngInject */
  function Controller($stateParams) {
    var vm = this;
    vm.status = $stateParams.status;
    return init();

    function init() {
    }
  }
})();
