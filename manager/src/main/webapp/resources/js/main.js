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
	$("#"+id).addClass("active").parent("ul").parent("li").addClass("active");
}
var consoleModel=(function(console){
	var self=console||{};
	self.toConsole=function(){
		$.post("console",function(responseText){
			menu("console-menu",responseText);
		});
	};
	return self;
})(consoleModel);

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
	self.toDataExport=function(){
		$.post("dataExport",function(responseText){
			menu("data-export-menu",responseText);
		});
	};
	self.selectAll = function(){
		$("input[name=userList]").prop("checked",true);
	};
	self.selectNone = function(){
		$("input[name=userList]").prop("checked",false);
	};
	self.selectOthers = function(){
		$("input[name=userList]").each(function(){
			$(this).prop("checked",!$(this).prop("checked"));
		});
	}
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
	/**
	 * 医院基本信息
	 */
	self.toCompanyBaseInfo=function(){
		$.post("company/baseInfo",function(responseText){
			menu("company-baseInfo-menu",responseText);
		});
	};
	/**
	 * 医院数据统计
	 */
	self.toCompanyDataCount=function(){
		$.post("companyDataCount",function(responseText){
			menu("company-data-menu",responseText);
		});
	};
	/**
	 * 医院报告统计
	 */
	self.toCompanyReportCount=function(){
		$.post("company/reportCount",function(responseText){
			menu("company-report-menu",responseText);
		});
	};
	/**
	 * 大客户统计
	 */
	self.toBigCustomerCount=function(){
		$.post("company/bigCustomer",function(responseText){
			menu("company-bigcustomer-menu",responseText);
		});
	};
	return self;
})(companyCount);

var company=(function(company){
	var self=company||{};
	self.currentPage = 1;
	self.keyword=null;
	self.search=function(){
		var keyword=$.trim($("#keyword").val());
		if(keyword.length>0&&(!/^[a-zA-Z0-9_\u4e00-\u9fa5]+$/g.test(keyword))){
			jAlert("搜索关键字不能包含特殊字符");
			return;
		}
		self.keyword=$("#keyword").val();
		self.getCompany(1);
	}
	self.toCompanyMain=function(){
		self.keyword=null;
		$.post("company/companyMain",{keyword:self.keyword},function(responseText){
			$("#main-content").html(responseText);
			$("#main-menu li").removeClass("active").removeClass("opened");
			$("#company-menu").addClass("active");
		});
	}
	self.getCompany=function(currentPage){
		self.currentPage=currentPage;
		$.post("company/companyMain",{currentPage:currentPage,keyword:self.keyword},function(responseText){
			$("#main-content").html(responseText);
		});
	};
	self.toEditName = function(currentName){
		$.post("company/toEditName",{currentName:currentName},function(responseText){
			$("#company-editModal .modal-content").html(responseText);
			$("#company-editModal").modal("show");
		});
	}
	return self;
})(company);

var user=(function(user){
	var self=user||{};
	self.currentPage = 1;
	self.searchFiled="username";
	self.keyword=null;
	self.search=function(){
		var keyword=$.trim($("#keyword").val());
		if(keyword.length>0&&(!/^[a-zA-Z0-9_\u4e00-\u9fa5]+$/g.test(keyword))){
			jAlert("搜索关键字不能包含特殊字符");
			return;
		}
		self.searchFiled=$("#searchFiled").val();
		self.keyword=$("#keyword").val();
		self.getUserList(1);
	}
	self.checkEmail=function(email){
		if(email){
			if(!email.match(/^\w+((-\w+)|(\.\w+))*\@[A-Za-z0-9]+((\.|-)[A-Za-z0-9]+)*\.[A-Za-z0-9]+$/)){
				return false;
			}else{
				return true;
			}
		}
	}
	self.sendEmail=function(){
		var isPass = true;
		$("#emailForm").find("input:text").each(function(){
			var email = $(this).val();
			if(self.checkEmail(email)){
				$(this).parent().parent().removeClass("error");
				$(this).parent().parent().find(".help-inline").html("");
			}else{
				isPass = false;
				$(this).parent().parent().addClass("error");
				if($.trim(email).length==0){
					$(this).parent().parent().find(".help-inline").html("邮箱不能为空！");
				}else{
					$(this).parent().parent().find(".help-inline").html("邮箱格式不正确！");
				}
			}
		});
		$("#emailForm").find("select").each(function(){
			value = $(this).val() == null ? "" : $(this).val();
			if(value.length>0){
				$(this).parent().parent().removeClass("error");
				$(this).parent().parent().find(".help-inline").html("");
			}else{
				isPass = false;
				$(this).parent().parent().addClass("error");
				$(this).parent().parent().find(".help-inline").html("该项为必选项，请选择");
			}
		});
		if(isPass){
			var params = $("#emailForm").serialize(); 
			$.get("user/checkEmail",params,function(flag){
				if(flag.indexOf("1")>=0){
						if(flag==1){
							$("#emailArray").parent().parent().addClass("error");
							$("#emailArray").parent().parent().find(".help-inline").html("此邮箱已经添加！");
						}else{
							$("#emailArray").parent().parent().removeClass("error");
							$("#emailArray").parent().parent().find(".help-inline").html("");
						}
				}else{
					alert("邮件发送成功");
					$("#sendEmail").modal("hide");
					$.get("user/sendEmail",params);
					$("#user-sendEmailModal").modal("hide");
				}
			});
		}
	};
	self.toUserMain=function(){
		$.post("user/userList",{searchFiled:self.searchFiled,keyword:self.keyword},function(responseText){
			$("#main-content").html(responseText);
			$("#main-menu li").removeClass("active").removeClass("opened");
			$("#user-menu").addClass("active");
		});
	};
	self.showChangePwd=function(){
		$("#main-content").load("./pages/user/user_pwd_reset.jsp");
		$("#main-menu li").removeClass("active").removeClass("opened");
	};
	self.getUserList=function(currentPage){
		$.post("user/userList",{currentPage:currentPage,searchFiled:self.searchFiled,keyword:self.keyword},function(responseText){
			$("#main-content").html(responseText);
		});
	};
	
	self.toSendEmail=function(){
		$.post("user/toSendEmail",function(responseText){
			$("#user-sendEmailModal .modal-content").html(responseText);
			$("#user-sendEmailModal").modal("show");
		});
	};
	self.changeDeptByCompanyId = function(option){
		var oldWidth = $("#dept .select2-search__field").attr("style");
		$("#dept li.select2-selection__choice").remove();
		$("#dept span.select2-selection__clear").remove();
		$("#dept .select2-search__field").attr("placeholder","请选择部门");
		$("#dept .select2-search__field").attr("style","oldWidth");
		$("#deptList option").remove();
        var companyId = null;
		var optionVal = $(option).val();
		if(optionVal != null){
	        if(!isNaN(optionVal)){
	        	companyId = parseInt(optionVal);
	        }
			$.ajax({
				  url : "user/getDeptByCompanyId",
		          type : 'post',
		          data:{
		        	  'companyId' : companyId
		          },
		          success : function(data) {
		              // 从后台获取json数据
		              var json = eval(data);
		              $("#deptList").select2({
		                  data : json,
		                  tags : true,
		                  placeholder : '请选择部门',
		                  allowClear : true,
		                  maximumSelectionLength: 1
		              })
		          }
		      });
		}
	};
	self.toGrantApp = function(userId){
		$.post("user/toGrantApp",{userId,userId},function(responseText){
			$("#user-sendEmailModal .modal-content").html(responseText);
			$("#user-sendEmailModal").modal("show");
		});
	};
	self.toGrantRole = function(userId){
		$.post("user/toGrantRole",{userId,userId},function(responseText){
			$("#user-sendEmailModal .modal-content").html(responseText);
			$("#user-sendEmailModal").modal("show");
		});
	};
	self.grantApp = function(){
		var params = $("#grantForm").serialize();
		$.post("user/grantApp",params,function(){
			jAlert("授权成功");
			$("#user-sendEmailModal").modal("hide");
		});
	}
	self.grantRole = function(){
		var params = $("#grantForm").serialize();
		$.post("user/grantRole",params,function(){
			jAlert("授权成功");
			$("#user-sendEmailModal").modal("hide");
		});
	}
	self.grantTip = function(currentInput,isAdd){
		// 取消选中并且已添加过该app
		if($(currentInput).prop("checked") == false && isAdd == 1){
			jConfirm("该用户已添加过该App,确定取消授权码?","取消授权",function(r){
				if(!r){
					$(currentInput).prop("checked",true);
				}
			});
		}
	};
	return self;
})(user);

$(function(){
	consoleModel.toConsole();
	$("body").on("click","[data-click='to-app-price-list']",function(){
	  $.appManager.price.list();
	});
});