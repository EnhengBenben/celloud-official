$(document).ready(function(){
		//显示public DB
		var content = $("#pdb").html();
		$("#mainContentDiv").html(content);
		//获取公有数据库getPublicDb
		$("#publicDb").click(function(){
			var content = $("#pdb").html();
			$("#mainContentDiv").html(content);
			$("#liPublicDb").addClass("active");
			$("#liPrivateDb").removeClass("active");
		});
		//获取私有数据库
		$("#privateDb").click(function(){
			var content = $("#tip").html();
			$("#mainContentDiv").html(content);
			$("#liPrivateDb").addClass("active");
			$("#liPublicDb").removeClass("active");
		});
		//首页
		$("#home").click(function(){
			$.get("getDetailDataBase.action",{},function(responseData){
				$("#mainContent").html(responseData);
			});
		});
		$.ajaxSetup ({
			cache: false //关闭AJAX相应的缓存
		});
	});