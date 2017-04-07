(function () {

  'use strict';

  angular.module('ngDemo')
    .factory('authInterceptor', ['$location', '$localStorage', '$q',
      function ($location, $localStorage, $q) {
        var authInterceptor = {
          request: function (config) {
            config.headers = config.headers || {};
            if ($localStorage.token) {
              config.headers.Authorization = $localStorage.token;
            }

            return config;
          },

          responseError: function (response) {
            if (response.status === 401) {
              if ($location.url().search(/apply/) < 0) {
                $location.path('/login');
              }

              return $q.reject(response);
            } else {
              if (response.status === 422) {
                var detail = '';
                for (var p in response.data.errors) {
                  detail += '\n' + response.data.errors[p][0];
                }

                //toaster.pop('error', response.data.message, detail);
              } else {
                //toaster.pop('error', '', response.data.message);
              }

              return $q.reject(response);
            }
          }
        };
        return authInterceptor;
      }
    ]);

})();
