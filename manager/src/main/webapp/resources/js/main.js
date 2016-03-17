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
function menu(id,content){
	$("#main-content").html(content);
	$("#main-menu li").removeClass("active");
	$("#"+id).addClass("active").parent("li").addClass("active");
}
var console=(function(console){
	var self=console||{};
	self.toConsole=function(){
		$.post("console",function(responseText){
			menu("console-menu",responseText);
		});
	};
	return self;
})(console);

var dataFile=(function(dataFile){
	var self=dataFile||{};
	self.toDataCount=function(){
		$.post("dataCount",function(responseText){
			menu("data-count-menu",responseText);
		});
	};
	
	self.toUserDataCount=function(){
		$.post("userDataCount",function(responseText){
			menu("data-user-menu",responseText);
		});
	};
	self.toCompanyDataCount=function(){
		$.post("companyDataCount",function(responseText){
			menu("data-company-menu",responseText);
		});
	};
	self.toBigCustomerDataCount=function(){
		$.post("bigCustomerDataCount",function(responseText){
			menu("data-bigCustomer-menu",responseText);
		});
	};
	return self;
})(dataFile);

var companyCount=(function(companyCount){
	var self=companyCount||{};
	/**
	 * 医院总览
	 */
	self.toCompanyGuideCount=function(){
		$.post("company/guide",function(responseText){
			menu("company-guide-menu",responseText);
		});
	};
	return self;
})(companyCount);

$(function(){
	console.toConsole();
});