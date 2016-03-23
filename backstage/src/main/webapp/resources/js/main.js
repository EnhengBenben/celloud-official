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
/**
*公司管理
**/
var company=(function(company){
	var self=company||{};
	var uploader = null;
	self.currentPage = 1;
	self.keyword=null;
	self.initUploader = function(){
		if(uploader){
			uploader.destroy();
		}
		uploader = new plupload.Uploader({
			browse_button : 'uploadCompanyIconBtn', 
			url : "../company/upload",
			container:'companyIconContainer',
	        chunk_size : "1mb",
	        file_data_name:'file',
	        filters : {
				max_file_size : "2mb",
				mime_types: [
				    {title : "Image files", extensions : "jpg,jpeg,png" }
				],
				prevent_duplicates : true //不允许选取重复文件
			},
			multi_selection:false,
	        flash_swf_url : '../plugins/plupload-2.1.2/Moxie.swf',
	        init:{
	        	PostInit: function() {
	        		$("#companyForm").find("#warning-group").hide();
	            },
	        	FilesAdded: function(up, files) {
	        		$("#companyIconUploading").show();
	        		uploader.start();
	            },
	            FileUploaded:function(up,file,response){
	            	$("#companyIconUploading").hide();
	            	var $img = $("<img />");
	            	$img.attr("src","company/icon/temp?file="+response.response);
	            	$img.addClass("img-thumbnail");
	            	$img.css("height","60px");
	            	$img.css("margin-right","10px");
	            	var $input = $('<input type="hidden" name="companyIcon"/>');
	            	$input.val(response.response);
	            	$("#companyIconUploading").siblings(".img-thumbnail-inline").html($img);
	            	$("#companyForm .companyIcon-input-hidden").html($input);
	            	uploader.splice();
	            }
	        }
	    });
		uploader.init();
	}
	self.search=function(){
		var keyword=$("#keyword").val();
		if(!/^[a-zA-Z0-9_\u4e00-\u9fa5]+$/g.test(keyword)){
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
	self.addCompany=function(){
		$.post("company/companyEdit",function(responseText){
			$("#company-editModal .modal-content").html(responseText);
			$("#company-editModal .modal-content .panel-title").html("添加公司");
			self.initUploader();
			$("#company-editModal").modal("show");
		});
			
	}
	
	self.editCompany=function(companyId){
		$.post("company/companyEdit",{companyId:companyId},function(responseText){
			$("#company-editModal .modal-content").html(responseText);
			self.initUploader();
			$("#company-editModal").modal("show");
		});
			
	}
	self.deleteCompany=function(companyId){
		jConfirm("确定删除该公司信息吗？", '确认提示框', function(r) {
			if(r){
				$.post("company/companyDelete",{companyId:companyId},function(responseText){
					if(responseText>0){
						alert("成功");
						self.getCompany(self.currentPage);
					}
				});
			}
		});
	}
	
	self.changeProvince=function(p,async,id){
		 $.ajax({
		        type: "POST",
		        url: "company/getcity",
		        data:'pCity='+p,
		        async:async,
		        cache: false,
		        success: function(data){
		            var options="<option value=''>--请选择城市--</option>";
		            for(var i in data){
		                options+="<option value='"+data[i]+"'>"+data[i]+"</option>";
		            }
		            $("#"+id).html(options).removeClass("invisible");
		            if(data.length==1){
		                $("#"+id).val(data[i]);
		            }
		        }
		    });
	}
	return self;
})(company);

var dept=(function(dept){
	self=dept||{};
	
	var uploader = null;
	self.currentPage = 1;
	self.companyId=null;
	self.initUploader = function(){
		if(uploader){
			uploader.destroy();
		}
		uploader = new plupload.Uploader({
			browse_button : 'uploadDeptIconBtn', 
			url : "../dept/upload",
			container:'deptIconContainer',
	        chunk_size : "1mb",
	        file_data_name:'file',
	        filters : {
				max_file_size : "2mb",
				mime_types: [
				    {title : "Image files", extensions : "jpg,jpeg,png" }
				],
				prevent_duplicates : true //不允许选取重复文件
			},
			multi_selection:false,
	        flash_swf_url : '../plugins/plupload-2.1.2/Moxie.swf',
	        init:{
	        	PostInit: function() {
	        		$("#deptForm").find("#warning-group").hide();
	            },
	        	FilesAdded: function(up, files) {
	        		$("#deptIconUploading").show();
	        		uploader.start();
	            },
	            FileUploaded:function(up,file,response){
	            	$("#deptIconUploading").hide();
	            	var $img = $("<img />");
	            	$img.attr("src","dept/icon/temp?file="+response.response);
	            	$img.addClass("img-thumbnail");
	            	$img.css("height","60px");
	            	$img.css("margin-right","10px");
	            	var $input = $('<input type="hidden" name="deptIcon"/>');
	            	$input.val(response.response);
	            	$("#deptIconUploading").siblings(".img-thumbnail-inline").html($img);
	            	$("#deptForm .deptIcon-input-hidden").html($input);
	            	uploader.splice();
	            }
	        }
	    });
		uploader.init();
	}
	self.getDept=function(currentPage){
		self.currentPage=currentPage;
		$.post("dept/deptList",{currentPage:currentPage,companyId:self.companyId},function(responseText){
			$("#dept-manager").html(responseText);
		});
	};
	self.getDeptByCompany=function(companyId,currentPage){
		self.companyId=companyId;
		self.currentPage=currentPage;
		$.post("dept/deptList#dept-manager",{companyId:companyId,currentPage:currentPage},function(responseText){
			$("#dept-manager").html(responseText);
			window.location.hash = "#dept-manager";  
		});
	};
	self.addDept=function(){
		$.post("dept/deptEdit",{companyId:self.companyId},function(responseText){
			$("#dept-editModal .modal-content").html(responseText);
			$("#dept-editModal .modal-content .panel-title").html("添加部门");
			self.initUploader();
			$("#dept-editModal").modal("show");
		});
			
	}
	
	self.editDept=function(deptId){
		$.post("dept/deptEdit",{deptId:deptId},function(responseText){
			$("#dept-editModal .modal-content").html(responseText);
			self.initUploader();
			$("#dept-editModal").modal("show");
		});
			
	}
	
	self.deleteDept=function(deptId){
		
		jConfirm("确定删除该部门信息吗？", '确认提示框', function(r) {
			if(r){
				$.post("dept/deptDelete",{deptId:deptId},function(responseText){
					if(responseText>0){
						alert("成功");
						self.getDept(self.currentPage);
					}
				});
			}
		});
	}
	
	
	return self;
})(dept);

var user=(function(user){
	var self=user||{};
	self.currentPage = 1;
	self.searchFiled="username";
	self.keyword=null;
	self.search=function(){
		var keyword=$("#keyword").val();
		if(!/^[a-zA-Z0-9_\u4e00-\u9fa5]+$/g.test(keyword)){
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
			var value = $(this).val();
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
	}
	
	self.toSendEmail=function(){
		$.post("user/toSendEmail",function(responseText){
			$("#user-sendEmailModal .modal-content").html(responseText);
			$("#user-sendEmailModal").modal("show");
		});
			
	}
	self.changeCompany=function(dom,eleId){
		var companyId=$(dom).val();
		if(companyId!=''){
			$.post("user/getDept",{companyId:companyId},function(data){
				var options="<option value=''>--请选择--</option>";
	            for(var i in data){
	                options+="<option value='"+data[i].deptId+"'>"+data[i].deptName+"</option>";
	            }
	            $("#"+eleId).html(options);
			});
		}
	}
	self.changeAppCompany=function(dom,id){
		var companyId=$(dom).val();
		$.post("user/getAppList",{companyId:companyId},function(data){
			var checkboxs="";
            for(var i in data){
            	checkboxs+="<label class='checkbox-inline add'><input name='appIdArray' type='checkbox' checked='checked' value='"+data[i].appId+"'>"+data[i].appName+"</label>";
            }
            $("#"+id).find('.add').remove();
            $("#"+id).append(checkboxs).parent().removeClass("hide");
		});
	}
	return self;
})(user);

var notice=(function(notice){
	var self=notice||{};
	self.currentPage = 1;
	self.toNoticeMain=function(){
		$.post("notice/noticeList",function(responseText){
			$("#main-content").html(responseText);
			$("#main-menu li").removeClass("active").removeClass("opened");
			$("#notice-menu").addClass("active");
		});
	}
	self.getNoticeList=function(currentPage){
		self.currentPage=currentPage;
		$.post("notice/noticeList",{currentPage:currentPage},function(responseText){
			$("#main-content").html(responseText);
		});
	}
	
	self.addNotice=function(){
		$.post("notice/noticeEdit",function(responseText){
			$("#notice-editModal .modal-content").html(responseText);
			$("#notice-editModal .modal-content .panel-title").html("新增公告");
			$("#notice-editModal").modal("show");
		});
			
	}
	
	self.editNotice=function(noticeId){
		$.post("notice/noticeEdit",{noticeId:noticeId},function(responseText){
			$("#notice-editModal .modal-content").html(responseText);
			CKEDITOR.instances.noticeContext.setData($("#noticeContext").val());
			$("#notice-editModal").modal("show");
		});
			
	}
	return self;
})(notice);

var client=(function(client){
	var self=client||{};
	self.currentPage = 1;
	self.toClientMain=function(){
		$.post("client/clientList",function(responseText){
			$("#main-content").html(responseText);
			$("#main-menu li").removeClass("active").removeClass("opened");
			$("#client-menu").addClass("active");
		});
	}
	
	self.getClientList=function(currentPage){
		self.currentPage=currentPage;
		$.post("client/clientList",{currentPage:currentPage},function(responseText){
			$("#main-content").html(responseText);
		});
	}
	
	self.addClient=function(){
		$.post("client/clientEdit",function(responseText){
			$("#client-editModal .modal-content").html(responseText);
			$("#client-editModal").modal("show");
		});
			
	}
	return self;
})(client);

var dataFile=(function(dataFile){
	var self=dataFile||{};
	self.toDataFileUpload=function(){
		$.post("dataFile/toDataFileUpload",function(responseText){
			$("#main-content").html(responseText);
			$("#main-menu li").removeClass("active").removeClass("opened").removeClass("expanded");
			$("#file-upload-menu").addClass("active").parent().parent("li").addClass("active").addClass("opened").addClass("expanded");
		});
	}
	self.toDataClean=function(){
		$.post("dataFile/toDataClean",function(responseText){
			$("#main-content").html(responseText);
			$("#main-menu li").removeClass("active").removeClass("opened").removeClass("expanded");
			$("#file-clean-menu").addClass("active").parent().parent("li").addClass("active").addClass("opened").addClass("expanded");
		});
	}
	self.empty=function(){
		$("#data-upload-set-panel").html('');
		$("#DataAmountForm")[0].reset();
	}
	return self;
})(dataFile);

var app=(function(app){
	var self=app||{};
	self.currentPage = 1;
	var pictureUploader = null;
	var screenUploader=null;
	
	self.initPictureNameUploader = function(){
		if(pictureUploader){
			pictureUploader.destroy();
		}
		pictureUploader = new plupload.Uploader({
			browse_button : 'uploadPictureNameBtn', 
			url : "../app/upload",
			container:'pictureNameContainer',
	        chunk_size : "1mb",
	        file_data_name:'file',
	        filters : {
				max_file_size : "2mb",
				mime_types: [
				    {title : "Image files", extensions : "jpg,jpeg,png" }
				],
				prevent_duplicates : true //不允许选取重复文件
			},
			multi_selection:false,
	        flash_swf_url : '../plugins/plupload-2.1.2/Moxie.swf',
	        init:{
	        	PostInit: function() {
	        		$("#appForm").find("#warning-group").hide();
	            },
	        	FilesAdded: function(up, files) {
	        		$("#pictureNameUploading").show();
	        		pictureUploader.start();
	            },
	            FileUploaded:function(up,file,response){
	            	$("#pictureNameUploading").hide();
	            	var $img = $("<img />");
	            	$img.attr("src","app/icon/temp?file="+response.response);
	            	$img.addClass("img-thumbnail");
	            	$img.css("height","60px");
	            	$img.css("margin-right","10px");
	            	var $input = $('<input type="hidden" name="pictureName"/>');
	            	$input.val(response.response);
	            	$("#pictureNameUploading").siblings(".img-thumbnail-inline").html($img);
	            	$("#appForm .pictureName-input-hidden").html($input);
	            	pictureUploader.splice();
	            }
	        }
	    });
		pictureUploader.init();
	}
	
	self.initScreenUploader = function(){
		if(screenUploader){
			screenUploader.destroy();
		}
		screenUploader = new plupload.Uploader({
			browse_button : 'uploadScreenBtn', 
			url : "../app/upload",
			container:'screenContainer',
	        chunk_size : "1mb",
	        file_data_name:'file',
	        filters : {
				max_file_size : "2mb",
				mime_types: [
				    {title : "Image files", extensions : "jpg,jpeg,png" }
				],
				prevent_duplicates : true //不允许选取重复文件
			},
			multi_selection:false,
	        flash_swf_url : '../plugins/plupload-2.1.2/Moxie.swf',
	        init:{
	        	PostInit: function() {
	        		$("#appForm").find("#screen-group").removeClass("hide");
	        		$("#appForm").find("#screen-group").show();
	        		$("#appForm").find("#warning-group").hide();
	            },
	        	FilesAdded: function(up, files) {
	        		$("#screenUploading").show();
	        		screenUploader = up;
	        		up.start();
	            },
	            QueueChanged:function(up){
	            	screenUploader = up;
	            	up.disableBrowse(up.files.length>=5);
	            },
	            FileUploaded:function(up,file,response){
	            	screenUploader = up;
	            	$("#screenUploading").hide();
	            	var $input = $('<input type="hidden" name="screenName"/>');
	            	$input.val(response.response);
	            	var $imgdiv=$("<div class='inline'></div>")
	            	var $img = $("<img style='height: 60px; margin-right: 10px;'/>");
	            	$img.attr("src","app/icon/temp?file="+response.response);
	            	$img.addClass("img-thumbnail");
	            	$img.attr("name","screen_"+response.response)
	            	$imgdiv.append($img);
	            	var $imgdel=$('<a href="javascript:void(0)"><span style="position: relative; right: 18px; top: -25px;">×</span></a>');
	            	$imgdel.click(function(){
	            		$imgdiv.remove();
	            		$input.remove();
	            		screenUploader.removeFile(file);
	            	});
	            	$imgdiv.append($imgdel);
	            	$("#screenUploading").before($imgdiv);
	            	$("#appForm").append($input);
	            }
	        }
	    });
		screenUploader.init();
	}
	
	self.toAppMain=function(){
		$.post("app/appList",function(responseText){
			$("#main-content").html(responseText);
			$("#main-menu li").removeClass("active").removeClass("opened").removeClass("expanded");
			$("#app-list-menu").addClass("active").parent().parent("li").addClass("active").addClass("opened").addClass("expanded");
		});
	}
	self.getAppList=function(currentPage){
		self.currentPage=currentPage;
		$.post("app/appList",{currentPage:currentPage},function(responseText){
			$("#main-content").html(responseText);
		});
	}
	self.on=function(appId){
		jConfirm("确定恢复该App上线吗？", '确认提示框', function(r) {
			if(r){
				$.post("app/on",{appId:appId},function(data){
					if(data>0){
						alert("app上线成功");
						self.getAppList(self.currentPage);
					}
				});
			}
		});
	}
	self.off=function(appId){
		jConfirm("确定下线该App吗？", '确认提示框', function(r) {
			if(r){
				$.post("app/off",{appId:appId},function(data){
					if(data>0){
						alert("app下线成功");
						self.getAppList(self.currentPage);
					}
				});
			}
		});
	}
	self.toAddApp=function(){
		$.post("app/toEditApp",function(responseText){
			$("#main-content").html(responseText);
			self.initPictureNameUploader();
			self.initScreenUploader();
			$("#main-menu li").removeClass("active").removeClass("opened").removeClass("expanded");
			$("#app-add-menu").addClass("active").parent().parent("li").addClass("active").addClass("opened").addClass("expanded");
		});
	}
	
	self.toEditApp=function(appId){
		$.post("app/toEditApp",{appId:appId},function(responseText){
			$("#main-content").html(responseText);
			$("#main-content .panel-heading").html("<h3 class='panel-title'>编辑应用</h3><div class='panel-options'><button type='button' class='btn btn-warning' onclick='javascript:app.getAppList("+self.currentPage+")' style='margin-bottom:0'>返回</button></div>");
			self.initPictureNameUploader();
			self.initScreenUploader();
			CKEDITOR.instances.editordescription.setData($("#editordescription").val());
			CKEDITOR.instances.editorappDoc.setData($("#editorappDoc").val());
		});
	}
	self.delScreen=function(dom,screenName){
		var $input = $('<input type="hidden" name="delScreenName"/>');
		$input.val(screenName);
		$("#appForm").append($input);
		$(dom).parent().remove();
	}
	return self;
})(app);

var mailing=(function(mailing){
	self=mailing||{};
	self.toMailingMain=function(){
		$.post("mailing",function(responseText){
			$("#main-content").html(responseText);
			$("#main-menu li").removeClass("active").removeClass("opened");
			$("#mailing-menu").addClass("active");
		});
	}
	self.checkedAll=function(flag){
		if(flag==1){
			/*$("input[name='emails']:checkbox").each(function(i,dom){
				dom.checked=true;
			});*/
			$("input[name='emails']:checkbox").prop("checked",true);
		}else if(flag==0){
			/*$("input[name='emails']:checkbox").each(function(i,dom){
				dom.checked=false;
			});*/
			$("input[name='emails']:checkbox").prop("checked",false);
		}else{
			$("input[name='emails']:checkbox").each(function(i,dom){
				dom.checked=!(dom.checked);
			});
		}
	}
	
	return self;
})(mailing);

var feedback=(function(feedback){
	var self=feedback||{};
	self.currentPage=1;
	self.sortFiled=null;
	self.sortType=null;
	var uploader = null;
	var validateReply = function(){
		var value = $("#feedbackReplyContent").val();
		if(!$.trim(value)){
			$("#feedbackReplyBtn").attr("disabled","disabled");
			return false;
		}
		return true;
	}
	self.tofeedbackMain=function(){
		$.post("feedback/list",{sortFiled:self.sortFiled,sortType:self.sortType},function(responseText){
			$("#main-content").html(responseText);
			$("#main-menu li").removeClass("active").removeClass("opened");
			$("#feedback-menu").addClass("active");
		});
	}
	self.getFeedbackList=function(currentPage){
		self.currentPage=currentPage;
		$.post("feedback/list",{currentPage:currentPage,sortFiled:self.sortFiled,sortType:self.sortType},function(responseText){
			$("#main-content").html(responseText);
		});
	}
	self.listReplies = function(id){
		$("#feedbackReplyList").load("feedback/replies/"+id);
	}
	self.detail=function(id){
		$.get("feedback/"+id,function(responseText){
			$("#feedback-Modal .modal-content").html(responseText);
			self.listReplies(id);
			$("#feedback-Modal").modal("show");
		});
	}
	self.solve = function(id){
		jConfirm('确认要关闭这个问题吗?', '问题反馈', function(result) {
			if(!result){
				return;
			}
			$.post('feedback/solve/'+id,function(data){
				if(data>0){
					$("#feedback-Modal").modal("hide");
					$('#feedback-Modal').on('hidden.bs.modal', function (e) {//此事件在模态框被隐藏（并且同时在 CSS 过渡效果完成）之后被触发。
						self.getFeedbackList(self.currentPage);
					});
				}else{
					jAlert("系统繁忙，请您稍后再试！");
				}
			});
		});
	}
	self.reply = function(id){
		if(!validateReply()){
			return;
		}
		var $form=$("#feedbackReplyForm");
		var url = $form.attr("action");
		$.post(url,$form.serialize(),function(data){
			if(data>0){
				self.listReplies(id);
				$("#feedbackReplyBtn").attr("disabled","disabled");
				$("#feedbackReplyContent").val('');
			}
		});
	}
	
	self.sortFeedback=function(filed){
		if(self.sortFiled!= filed){
			self.sortType=null;
			self.sortFiled= filed;
		}
        if(self.sortType == "desc"){
        	self.sortType="asc";
		}else{
			self.sortType="desc";
		}
        self.getFeedbackList(1);
	}
	self.sortDefault=function(){
		self.sortType=null;
		self.sortFiled= null;
		self.getFeedbackList(1);
	}
	$(document).on("keyup","#feedbackReplyContent",function(e){
		var value = $(this).val();
		if($.trim(value)){
			$("#feedbackReplyBtn").removeAttr("disabled");
		}else{
			$("#feedbackReplyBtn").attr("disabled","disabled");
		}
	});
	return self;
})(feedback);
