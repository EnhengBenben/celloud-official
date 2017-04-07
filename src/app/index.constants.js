/* global malarkey:false, moment:false */
(function () {
    'use strict';

    angular
        .module('ngDemo')
        .constant('malarkey', malarkey)
        .constant('moment', moment);
       // .constant('ENDPOINT', 'https://www.celloud.cc/celloud-oms');
})();
