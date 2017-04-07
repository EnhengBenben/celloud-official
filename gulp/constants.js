/**
 * Created by yong on 2017/3/31.
 */
var minimist = require('minimist');
var gutil = require('gulp-util');
var gulp = require('gulp');
var conf = require('./conf');

//默认development环境
var knowOptions = {
    string: 'env',
    default: {
        env: process.env.NODE_ENV || 'qa'
    }
};
var options = minimist(process.argv.slice(2), knowOptions);

//生成filename文件，存入string内容
function string_src(filename, string) {
    var src = require('stream').Readable({ objectMode: true });
    src._read = function () {
        this.push(new gutil.File({ cwd: "", base: "", path: filename, contents: new Buffer(string) }));
        this.push(null);
    };
    return src;
}

gulp.task('constants', function() {
    //读入config.json文件
    var myConfig = conf.apiconf;
    //取出对应的配置信息

    var envConfig = myConfig[options.env];
    /*var conConfig = 'appconfig = ' + JSON.stringify(envConfig);*/
    var conConfig = "angular.module('ngDemo').constant('ENDPOINT', " + JSON.stringify(envConfig) + ");";

    //生成config.js文件
    return string_src("config.js", conConfig)
        .pipe(gulp.dest('src/app'))
});
