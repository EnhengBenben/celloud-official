var gulp = require('gulp');

/* plugins:
** npm install --save-dev 
** gulp-uglify gulp-rename gulp-concat gulp-sass 
** gulp-minify-css gulp-autoprefixer 
*/
var uglify     = require('gulp-uglify'), // js compress
    rename     = require('gulp-rename'), // file rename
    concat     = require('gulp-concat'), // file combine
    less       = require('gulp-less'),
    cssmin     = require('gulp-clean-css'),
    autoprefix = require('gulp-autoprefixer'),
    livereload = require('gulp-livereload'),
    watch      = require('gulp-watch');

/* path
** source, build 
*/
var src = {
  js: 'src/main/webapp/resources/js/*.js',
  less: 'src/main/webapp/resources/less/celloud.less',
  lessdir: [
      'src/main/webapp/resources/less/*.less',
      'src/main/webapp/resources/less/**/*.less'
  ],
  clientless: 'src/main/webapp/resources/less/client.less',
  mainjs: [
      'src/main/webapp/resources/js/utils.js',
      'src/main/webapp/resources/js/charts.js',
      'src/main/webapp/resources/js/report_codon.js',
      'src/main/webapp/resources/js/message.js',
      'src/main/webapp/resources/js/alert.js',
      'src/main/webapp/resources/js/confirm.js',
      'src/main/webapp/resources/js/application.js',
      'src/main/webapp/resources/js/directive/href.js',
      'src/main/webapp/resources/js/directive/pagination.js',
      'src/main/webapp/resources/js/upload/service.js',
      'src/main/webapp/resources/js/upload/controller.js',
      'src/main/webapp/resources/js/app/service.js',
      'src/main/webapp/resources/js/app/controller.js',
      'src/main/webapp/resources/js/expense/filter.js',
      'src/main/webapp/resources/js/expense/service.js',
      'src/main/webapp/resources/js/expense/controller.js',
      'src/main/webapp/resources/js/experiment_scan/service.js',
      'src/main/webapp/resources/js/experiment_scan/controller.js',
      'src/main/webapp/resources/js/user/service.js',
      'src/main/webapp/resources/js/user/controller.js',
      'src/main/webapp/resources/js/data/service.js',
      'src/main/webapp/resources/js/data/controller.js',
      'src/main/webapp/resources/js/data/data.js',
      'src/main/webapp/resources/js/report/filter.js',
      'src/main/webapp/resources/js/report/dataReportService.js',
      'src/main/webapp/resources/js/report/dataReportController.js',
      'src/main/webapp/resources/js/report/service.js',
      'src/main/webapp/resources/js/report/controller.js',
      'src/main/webapp/resources/js/config/routeProvider.js',
      'src/main/webapp/resources/js/config/sessionInterceptor.js',
      'src/main/webapp/resources/js/common/service.js',
      'src/main/webapp/resources/js/common/controller.js',
      'src/main/webapp/resources/js/common/filter.js',
      'src/main/webapp/resources/js/overview/service.js',
      'src/main/webapp/resources/js/overview/controller.js',
      'src/main/webapp/resources/js/overview/userCount.js',
      'src/main/webapp/resources/js/notice/service.js',
      'src/main/webapp/resources/js/notice/messageController.js',
      'src/main/webapp/resources/js/notice/noticeController.js',
      'src/main/webapp/resources/js/feedback/service.js',
      'src/main/webapp/resources/js/feedback/controller.js',
      'src/main/webapp/resources/js/rocky/service.js',
      'src/main/webapp/resources/js/rocky/controller.js',
      'src/main/webapp/resources/js/bsi/service.js',
      'src/main/webapp/resources/js/bsi/controller.js',
      'src/main/webapp/resources/js/company/service.js',
      'src/main/webapp/resources/js/company/controller.js'
  ],
  clientjs: [
       'src/main/webapp/resources/js/utils.js',
       'src/main/webapp/resources/js/message.js',
       'src/main/webapp/resources/js/alert.js',
       'src/main/webapp/resources/js/confirm.js',
       'src/main/webapp/resources/js/application.js',
       'src/main/webapp/resources/js/directive/href.js',
       'src/main/webapp/resources/js/directive/pagination.js',
       'src/main/webapp/resources/js/config/clientRouteProvider.js',
       'src/main/webapp/resources/js/client/sessionInterceptor.js',
       'src/main/webapp/resources/js/client/service.js',
       'src/main/webapp/resources/js/expense/service.js',
       'src/main/webapp/resources/js/client/controller.js'
   ]
}
var dist = {
  js: 'src/main/webapp/resources/js/',
  css: 'src/main/webapp/resources/css/'
}

/* minifyjs
** https://github.com/gruntjs/grunt-contrib-uglify#preservecomments
** {preserveComments : 'some'} reserve lisence 
*/
gulp.task('minifyjs', function () {
  gulp.src(src.js)
    .pipe(rename({suffix: '.min1'}))
    .pipe(uglify({mangle:false,preserveComments : 'some'}))
    .pipe(gulp.dest(dist.js));
});

/* combinejs: 
** minify js 
*/
gulp.task('combinejs', function () {
  gulp.src(src.mainjs)
    .pipe(uglify({
      mangle:false,
      preserveComments : 'some'
    }))
    .pipe(concat('main.min.js'))
    .pipe(gulp.dest(dist.js));
});

/* less: 
** compile less
*/
gulp.task('less', function() {
  return gulp.src(src.less)
      .pipe(rename({suffix: '.min'}))
      .pipe(less())
      .pipe(autoprefix())
      .pipe(cssmin())
      .pipe(gulp.dest(dist.css));
});

/* less: 
** compile less
*/
gulp.task('clientless', function() {
  return gulp.src(src.clientless)
      .pipe(rename({suffix: '.min'}))
      .pipe(less())
      .pipe(autoprefix())
      .pipe(cssmin())
      .pipe(gulp.dest(dist.css));
});
gulp.task('combineClientjs', function () {
  gulp.src(src.clientjs)
    .pipe(uglify({
      mangle:false,
      preserveComments : 'some'
    }))
    .pipe(concat('client.min.js'))
    .pipe(gulp.dest(dist.js));
});

/* default */
gulp.task('default', function(){
    gulp.start(['combinejs','less']);
});

/* watch: 
** excute task when less file is changed 
*/
gulp.task('watch', function() {
  gulp.watch(src.mainjs, ['combinejs']);
  gulp.watch(src.clientjs, ['combineClientjs']);
  gulp.watch(src.lessdir, ['less']);
});