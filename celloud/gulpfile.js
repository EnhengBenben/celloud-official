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
  mainjs: [
      'src/main/webapp/resources/js/utils.js',
      'src/main/webapp/resources/js/charts.js',
      'src/main/webapp/resources/js/report_codon.js',
      'src/main/webapp/resources/js/message.js',
      'src/main/webapp/resources/js/alert.js',
      'src/main/webapp/resources/js/confirm.js',
      'src/main/webapp/resources/js/application.js',
      'src/main/webapp/resources/js/**/*.js'
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
      .pipe(watch(src.less))
      .pipe(rename({suffix: '.min'}))
      .pipe(less())
      .pipe(autoprefix())
      .pipe(cssmin())
      .pipe(gulp.dest(dist.css))
      .pipe(livereload());
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
  gulp.watch(src.lessdir, ['less']);
});