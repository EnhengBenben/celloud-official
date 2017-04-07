(function () {
'use strict';

angular
    .module('ngDemo')
    .config(configHttpProvider);

/* @ngInject */
function configHttpProvider($httpProvider) {
  $httpProvider.interceptors.push('authInterceptor');
  $httpProvider.defaults.withCredentials = false;
  delete $httpProvider.defaults.headers.common['X-Requested-With'];
  $httpProvider.defaults.headers.get = {
    Accept: 'application/json'
  };
  //application/vnd.cma.v1+json
  $httpProvider.defaults.headers.post.Accept = 'application/json';
}
})();
