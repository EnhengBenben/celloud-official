/**
 * Created by yong on 2017/4/5.
 */
(function () {
  'use strict';

  angular.module('ngDemo')
    .controller('JoinLayoutCtrl', Controller);

  /** @ngInject */

  function Controller($stateParams) {
    var vm = this;
      vm.status = $stateParams.status;
    return init();

    function init() {

    }
  }
})();
