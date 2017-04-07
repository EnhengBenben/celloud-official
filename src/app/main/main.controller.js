(function() {
  'use strict';

  angular
    .module('ngDemo')
    .controller('MainController', MainController);

  /** @ngInject */
  function MainController($state, $localStorage) {
    var app = this;
    app.logout = logout;
    app.$state = $state;
    return init();

    function init() {

    }

    function logout() {
        delete $localStorage.token;
        $state.go('login');
    }
  }
})();
