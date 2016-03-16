$.ajaxSetup ({
	  complete:function(request,textStatus){
		  var sessionstatus=request.getResponseHeader("sessionstatus"); //通过XMLHttpRequest取得响应头，sessionstatus，  
		  if(sessionstatus=="timeout"){
			  jAlert("登录超时,请重新登录！","登录超时",function(){
				  window.location.href="login";
			  });
		  }
	  },
	  cache: false //关闭AJAX相应的缓存
  });
var console=(function(console){
	var self=console||{};
	self.toConsole=function(){
		$.post("console",function(responseText){
			$("#main-content").html(responseText);
			$("#main-menu li").removeClass("active");
			$("#console-menu").addClass("active");
		});
	};
	return self;
})(console);

var dataFile=(function(dataFile){
	var self=dataFile||{};
	self.toDataCount=function(){
		$.post("dataCount",function(responseText){
			$("#main-content").html(responseText);
			$("#main-menu li").removeClass("active");
			$("#data-count-menu").addClass("active");
		});
	};
	
	self.toUserDataCount=function(){
		$.post("userDataCount",function(responseText){
			$("#main-content").html(responseText);
			$("#main-menu li").removeClass("active");
			$("#data-user-menu").addClass("active");
		});
	};
	self.toCompanyDataCount=function(){
		$.post("companyDataCount",function(responseText){
			$("#main-content").html(responseText);
			$("#main-menu li").removeClass("active");
			$("#data-company-menu").addClass("active");
		});
	};
	return self;
})(dataFile);

$(function(){
	console.toConsole();
});