(function() {
  'use strict';

  angular
    .module('ngDemo')
    .controller('MainController', MainController);

  /** @ngInject */
  function MainController($scope, $state, $localStorage) {
    var app = this;
    app.logout = logout;
    $scope.$state = $state;

    return init();

    function init() {

    }

    function logout() {
        delete $localStorage.token;
        $state.go('login');
    }
  }
})();
