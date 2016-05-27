//----------------------loading效果参数配置----------------------------
var opts = {
  lines: 7, // The number of lines to draw
  length: 10, // The length of each line
  width: 5, // The line thickness
  radius: 10, // The radius of the inner circle
  corners: 1, // Corner roundness (0..1)
  rotate: 0, // The rotation offset
  direction: 1, // 1: clockwise, -1: counterclockwise
  color: '#000', // #rgb or #rrggbb or array of colors
  speed: 1, // Rounds per second
  trail: 60, // Afterglow percentage
  shadow: false, // Whether to render a shadow
  hwaccel: false, // Whether to use hardware acceleration
  className: 'spinner', // The CSS class to assign to the spinner
  zIndex: 2e9, // The z-index (defaults to 2000000000)
  top: 'auto', // Top position relative to parent in px
  left: 'auto' // Left position relative to parent in px
};
var spinner;
//---------------------------------------------------------------------
$.ajaxSetup ({
    cache: false //关闭AJAX相应的缓存
});
var totalSize=0;
var resultData=[];
function initUploadDetail(){
	var fileNum = 0;
    spinner.stop();
    
    $("#saveAnotherName").click(function(){
    	var anotherdata = "";
    	for(var i=0; i<resultData.length; i++){
    		var data = resultData[i].split(",");
    		var id = data[0].substring(1,data[0].length);
    		var anotherName = $("#anotherName"+data[0].substring(1,data[0].length)).val();
    		anotherdata += id +"," + anotherName + ";";
    	}
    	$.get("data!changeAnotherName",{"dataIds":anotherdata.substring(0,anotherdata.length-1)},function(flag){
    		if(flag >0 ){
    			$("#anotherNameModal").modal("hide");
    			$("#anotherNameBody").html("");
    			$('#fileUpload').uploadify('cancel','*');
    			$("#progress").html("");
    			$("#progressTd").css("display","none");
    			resultData = [];
    		}else{
    			$("#saveAnotherNameDiv").html("别名修改失败...");
    		}
    	});
    });
    $("#closeModal").click(function(){
    	$("#anotherNameModal").modal("hide");
		$("#anotherNameBody").html("");
		$('#fileUpload').uploadify('cancel','*');
		$("#progress").html("");
		$("#progressTd").css("display","none");
		resultData = [];
    });
}
function hasFlashVersion(rv) {
    var pv = flashVersion(), v = rv.split(".");
       v[0] = parseInt(v[0], 10);
       v[1] = parseInt(v[1], 10);
       v[2] = parseInt(v[2], 10);
    return (pv[0] > v[0] || (pv[0] == v[0] && pv[1] > v[1]) || (pv[0] == v[0] && pv[1] == v[1] && pv[2] >= v[2])) ? true : false;
}
function flashVersion(){
    var version = [0,0,0], win = window, nav = navigator;
    if (typeof nav.plugins != "undefined" && typeof nav.plugins["Shockwave Flash"] == "object") {
       d = nav.plugins["Shockwave Flash"].description;
       if (d) {
        d = d.replace(/^.*\s+(\S+\s+\S+$)/, "$1");
        version[0] = parseInt(d.replace(/^(.*)\..*$/, "$1"), 10);
        version[1] = parseInt(d.replace(/^.*\.(.*)\s.*$/, "$1"), 10);
        version[2] = /r/.test(d) ? parseInt(d.replace(/^.*r(.*)$/, "$1"), 10) : 0;
       }
    } else if (typeof win.ActiveXObject != "undefined") {
       var a = null, fp6Crash = false;
       try {
        a = new ActiveXObject("ShockwaveFlash.ShockwaveFlash.7");
       }
       catch(e) {
        try { 
         a = new ActiveXObject("ShockwaveFlash.ShockwaveFlash.6");
         version = [6,0,21];
         a.AllowScriptAccess = "always"; // Introduced in fp6.0.47
        }
        catch(e) {
         if (version[0] == 6) {
          fp6Crash = true;
         }
        }
        if (!fp6Crash) {
         try {
          a = new ActiveXObject("ShockwaveFlash.ShockwaveFlash");
         }
         catch(e) {}
        }
       }
       if (!fp6Crash && a) { // a will return null when ActiveX is disabled
        try {
         d = a.GetVariable("$version"); // Will crash fp6.0.21/23/29
         if (d) {
          d = d.split(" ")[1].split(",");
          version = [parseInt(d[0], 10), parseInt(d[1], 10), parseInt(d[2], 10)];
         }
        }
        catch(e) {}
       }
    }
    return version;
}
if (!hasFlashVersion('10.0.124')) {
    $("#_linAlert").css("display","");
    $("#_linTable").css("display","none");
}else{
    $("#_linAlert").css("display","none");
    $("#_linTable").css("display","");
}
function refreshSession(){
    //为了防止上传过程中session超时而随便请求的一个方法
    $.get("uploadFile/checkAdminSessionTimeOut");
}