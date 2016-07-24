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
var $base={
    pageination: function(callback){
      $(document).on("click","#pagination-ul a",function(e){
        var id = $(this).attr("id");
        var currentPage = parseInt($("#current-page-hide").val());
        var totalPage = parseInt($("#total-page-hide").val());
        if(id == undefined){
          page = $(this).html();
        }else if(id.indexOf("prev")>=0){
          if(currentPage == 1){
            page = 1;
          }else{
            page = currentPage-1;
          }
        }else if(id.indexOf("next")>=0){
          page = currentPage+1;
          if(currentPage == totalPage){
            page = currentPage;
          }else{
            page = currentPage+1;
          }
        }else if(id.indexOf("first")>=0){
          page = 1;
        }else if(id.indexOf("last")>=0){
          page = totalPage;
        }
        if( callback ) callback(page);
      });
    }
}
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
	self.toUploadPdf = function(){
	  $.get("company/toUploadPdf",{},function(responseText){
	    $("#main-content").html(responseText);
	    $("#company-id-select").select2({
        placeholder: 'Choose your favorite US Countries',
        allowClear: true
      }).on('select2-open', function(){
        $(this).data('select2').results.addClass('overflow-hidden').perfectScrollbar();
      });

	    var uploader = new plupload.Uploader({
	      runtimes : 'html5,flash,silverlight,html4',
	      browse_button : 'plupload-content',
	      url : "../company/uploadPdf",
	      chunk_size : '1mb',
	      drop_element : 'plupload-content',
	      filters : {
	        max_file_size : '3gb',
	        prevent_duplicates : true, // 不允许选取重复文件
	        mime_types : [
	          {title : "pdf", extensions : "pdf"}
	        ]
	      },
	      max_retries : 5,
	      multiple_queues : true,
	      flash_swf_url : '//cdn.bootcss.com/plupload/2.1.8/Moxie.swf'
	    });
	    uploader.init();
	    $(document).on("click", "[data-click='del-upload-file']", function() {
	      var id = $(this).data("id");
	      $("#"+id).remove();
	      var file = uploader.getFile(id);
	      uploader.removeFile(file);
	    });
	    uploader.bind("StateChanged", function() {
	      if (uploader.state === plupload.STARTED) {
	        window.parent.isUploading = true;
	        refresh = setInterval("self.refreshSession()",600000);
	      }else if(uploader.state === plupload.STOPPED){
	        window.parent.isUploading = false;
	        clearInterval(refresh);
	      }
	    });
	    uploader.bind("UploadProgress", function(uploader, file) {
	      $("#" + file.id +" .percent").html(file.percent+"%");
	    });
	    uploader.bind("FilesAdded", function(uploader, files) {
	      $("#alert-tips").addClass("hide");
	      $("#upload-list-table").removeClass("hide");
	      $.each(files, function(index, item) {
	        var $fileDom = $('<tr id="' + item.id + '"></tr>');
	        $fileDom.append($('<td class="filename">' + item.name + '</td>'));
	        $fileDom.append($('<td class="percent">0</td>'));
	        $fileDom.append($('<td><a data-click="del-upload-file" data-id="'+item.id+'"  href="javascript:void(0)"><i class="fa fa-times-circle" aria-hidden="true"></i></a></td>'));
	        $("#upload-list-tbody").append($fileDom);
	      });
	    });
	    uploader.bind("BeforeUpload", function(uploader, file) {
	      $("#" + file.id +" .percent").html("正在上传");
	      var args = Array.from($("#company-id-select").val());
	      var companyIds = {};
	      for(i=0; i<args.length; i++){
	        companyIds[i] = args[i];
	      }
	      uploader.setOption("multipart_params",{"ids":companyIds});
	    });
	    uploader.bind("FileUploaded", function(uploader, file, response) {
	      var res = JSON.parse(response.response);
	      if(res == "1"){
	        $("#alert-info").html("<strong>OK!</strong>上传完成");
	      }else{
	        $("#alert-info").html("<strong>warning!</strong>上传失败");
	      }
	      $("#alert-tips").removeClass("hide");
	    });
	    uploader.bind("UploadComplete",function(uploader,files,response){
	      uploader.splice(0,uploader.files.length);
	    });
	    $("#upload-pdf-a").on("click",function(){
	      uploader.start();
	    });
	  });
	}
	
	return self;
})(company);

var dept=(function(dept){
	var self=dept||{};
	
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
            	checkboxs+="<label class='checkbox-inline'><input name='appIdArray' type='checkbox' checked='checked' value='"+data[i].appId+"'>"+data[i].appName+"</label>";
            }
            $("#"+id).html(checkboxs).parent().removeClass("hide");
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
	self.checkAll=function(flag){
		var checkArray = new Array();
		if (flag == 1) {
			$("#userList option").each(function() {
				checkArray.push($(this).val());
			});
			$("#userList").val(checkArray).trigger("change");
		} else if (flag == 0) {
			$("#userList").val(null).trigger("change");
		} else if (flag == 2) {
			$("#userList option").each(function() {
				if ($(this).prop("selected") == false) {
					checkArray.push($(this).val());
				}
			});
			$("#userList").val(null).trigger("change");
			$("#userList").val(checkArray).trigger("change");
		}
	}
	var mailTemplate = {
	    currentPage: 1,
      main: function(){
        $.post("mail/allTemplate",{},function(responseText){
          mailTemplate.load(responseText);
        });
      },
      page: function(page){
        $.post("mail/allTemplate",{"currentPage":page},function(responseText){
          mailTemplate.currentPage = page;
          mailTemplate.load(responseText);
        });
      },
      load: function(responseText){
        $("#main-content").html(responseText);
        $base.pageination(function(result) {mailTemplate.page(result)});
        $("[data-click='to-edit-mailtemplate']").on("click",function(){
          mailTemplate.toEdit($(this).data("id"));
        });
        $("[data-click='to-del-mailtemplate']").on("click",function(){
          mailTemplate.del($(this).data("id"));
        });
      },
      toEdit: function(id){
        $.post("mail/toEditTemplate",{"id":id},function(responseText){
          $("#main-content").html(responseText);
          CKEDITOR.replace("editorcontext");
          $("#commit-emailtemplate").on("click",function(){
            mailTemplate.edit();
          });
        });
      },
      edit: function(){
        $("#editorcontext").val(CKEDITOR.instances.editorcontext.getData());
        $.post("mail/editTemplate",$("#emailTemplateForm").serialize(),function(result){
          if(result == "success"){
            $("#mail-alert").addClass("alert-success");
          }else{
            $("#mail-alert").addClass("alert-danger");
          }
          $("#mail-info").html(result);
          $("#mail-alert").removeClass("hide");
        });
      },
      del: function(id){
        jConfirm("确定删除该邮件模板吗？", '确认提示框', function(r) {
          if(r){
            $.post("mail/deleteTemplate",{id:id},function(responseText){
              if(responseText>0){
                alert("成功");
                mailTemplate.page(mailTemplate.currentPage);
              }
            });
          }
        });
      }
  };
	self.mailTemplate = mailTemplate;
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

var task = (function(task){
	var self = task || {};
	
	self.toQuantityStatistics = function(){
		$.post("task/getQuantityStatistics",{},function(responseText){
			$("#main-content").html(responseText);
		});
	}
	
	self.toQueuingTime = function(){
		$.post("task/getQueuingTime",{},function(responseText){
			$("#main-content").html(responseText);
		});
	}
	
	self.toRunningTime = function(currentPage){
		var keyword = $("#keyword").val();
		if(!keyword){
			keyword = "";
		}
		if(keyword.length>0&&(!/^[a-zA-Z0-9_\u4e00-\u9fa5]+$/g.test(keyword))){
			jAlert("搜索关键字不能包含特殊字符");
			return;
		}
		$.post("task/getRunningTime",{"currentPage":currentPage,"keyword":keyword},function(responseText){
			$("#main-content").html(responseText);
		});
	}
	return self;
})(task);

var expense = (function(expense){
	var self = expense || {};
	self.invoice = {};
	self.invoice.toInvoiceMain = function(currentPage,keyword){
		var currentPage = currentPage || 1;
		var keyword = keyword || $("#keyword").val();
		$.post("invoice/list",{"currentPage":currentPage,"keyword":keyword},function(responseText){
			$("#main-content").html(responseText);
		});
	};
	self.invoice.toInvoiceDdetail = function(invoiceId){
		$.post("invoice/detail",{"invoiceId":invoiceId},function(responseText){
			$("#invoice-detailModal .modal-content").html(responseText);
			$("#invoice-detailModal").modal("show");
		});
	};
	self.recharge = {
	    main: function(){
	      $.post("recharge/main",{},function(responseText){
	        self.recharge.load(responseText);
	      });
	    },
	    condition: function(){
        $.post("recharge/main",{"condition":$("#condition").val()},function(responseText){
          self.recharge.load(responseText);
        });
      },
      page: function(page){
        $.post("recharge/main",{"condition":$("#condition").val(),"currentPage":page},function(responseText){
          self.recharge.load(responseText);
        });
      },
      load: function(responseText){
        $("#main-content").html(responseText);
        $base.pageination(function(result) {self.recharge.page(result)});
        $(document).on("click","#search-btn",function(){
          self.recharge.condition();
        });
        $(document).on("click","[data-click='to-recharge']",function(){
          $("#userid-hide").val($(this).data("user"));
          $("#recharge-modal").modal("show");
          //变换随机验证码
          $('#kaptchaImage').click(function() {
            $(this).hide().attr('src','kaptcha.jpg?' + Math.floor(Math.random() * 100)).fadeIn();
          });
        });
        $(document).on("click","#commit-recharge",function(){
          self.recharge.transfer();
        });
      },
      transfer: function(){
        $.post("recharge/transfer",$("#rechargeForm").serialize(),function(result){
          if(result.length>1){
            $("#recharge-alert").removeClass("hide");
            $("#recharge-info").html("验证码错误，请重新输入！");
          }else if(result == "1"){
            $("#recharge-modal").modal("hide");
            $("#recharge-modal").on('hidden.bs.modal', function (e) {//此事件在模态框被隐藏（并且同时在 CSS 过渡效果完成）之后被触发。
              self.recharge.page($("#current-page-hide").val());
            });
          }
        });
      }
	};
	return self;
})(expense);

var permission = (function(permission){
	self = permission || {};
	self.common = {
		confirm : function(text,callback){
			$("#confirm-text").html(text);
			$("#confirm-flase").one("click",function(){
				$("#confirm-modal").modal("hide");
			});
			if(typeof callback == 'function'){
				$("#confirm-true").one("click",function(){
					$("#confirm-modal").modal("hide");
					callback();
				});
			}
			$("#confirm-modal").modal("show");
		},
		tip : function(text){
			$("#tip-text").html(text);
			$("#tip-true").one("click",function(){
				$("#tip-modal").modal("hide");
			});
			$("#tip-modal").modal("show");
		}
	};
	self.resource = {
			moveUp : function(id,parentId,priority){
				$.post("resource/moveUp",{id:id,parentId:parentId,priority:priority},function(data){
					if(data>0){
						self.resource.list();
					}
				});
			},
			moveDown : function(id,parentId,priority){
				$.post("resource/moveDown",{id:id,parentId:parentId,priority:priority},function(data){
					if(data>0){
						self.resource.list();
					}
				});
			},
			onOrOff : function(id,disabled){
				if(disabled == 0){
					self.common.confirm("您确认上线该资源吗?",function(){
						$.post("resource/edit",{id:id,disabled:disabled},function(data){
							if(data > 0){
								self.resource.list();
							}
						});
					});
				}else{
					self.common.confirm("您确认下线该资源吗?",function(){
						$.post("resource/edit",{id:id,disabled:disabled},function(data){
							if(data > 0){
								self.resource.list();
							}
						});
					});
				}
			},
			list : function(){
				$.post("resource/list",{},function(responseText){
					$("#main-content").html(responseText);
					$('.tree').treegrid({
						treeColumn: 1
					});
					$("#saveOrUpdate").click(function(){
						var f = $("#saveUpdateFlag").val();
						if(f=='save'){
							self.resource.doAdd();
						}else{
							self.resource.doEdit();
						}
					});
				});
			},
			toAdd : function(){
				if($(".resourceCheck:checked").length>1){
					self.common.tip("选择的记录数不能大于1");
					return;
				}
				$("#resourceForm input").val("");
				$(".help-inline").html("");
				$("#saveUpdateFlag").val("save");
				$("#type option:selected").removeAttr("selected");
				$("#disabled option:selected").removeAttr("selected");
				/*$("#parentId option:selected").removeAttr("selected");*/
				$("#type").select2({
					minimumResultsForSearch: Infinity
				});
				$("#disabled").select2({
					minimumResultsForSearch: Infinity
				});
				/*$("#parentId").html("");
				$("#parentId").append("<option value='0'>请选择</option>");
				$.post("resource/findAllActive",{},function(data){
					var jsonData = eval(data);
					for(var i=0;i<jsonData.length;i++){
						$("#parentId").append("<option value='"+jsonData[i].id+"'>"+jsonData[i].name+"</option>")
					}
					$("#parentId").select2({
						minimumResultsForSearch: Infinity
					});
				});*/
				$("#parentId").val($(".resourceCheck:checked").length == 0 ? 0 : $(".resourceCheck:checked").val());
				$("#resource-addModal").modal("show");
				$("#resource-addModal h3").html("新增资源");
			},
			doAdd : function(){
				if(self.resource.checkForm()){
					// 添加资源
					$.post("resource/add",$("#resourceForm").serialize(),function(data){
						if(data > 0){
							$("#resource-addModal").modal("hide");
				            $("#resource-addModal").on('hidden.bs.modal', function (e) {//此事件在模态框被隐藏（并且同时在 CSS 过渡效果完成）之后被触发。
				              self.resource.list();
				            });
						}else{
							$("#resource-alert").removeClass("hide");
				            $("#resource-info").html("添加失败");
						}
					})
				}else{
					return false;
				}
			},
			toEdit : function(id){
				$("#saveUpdateFlag").val("update");
				$.post("resource/findOne",{id,id},function(data){
					data = eval(data);
					$(".help-inline").html("");
					$("#name").val(data.name);
					$("#permission").val(data.permission);
					$("#createDate").val(new Date(data.createDate));
					$("#priority").val(data.priority);
					$("#resourceId").val(data.id);
					$("#parentId").val(data.parentId);
					$("#type option").each(function(){
						if($(this).val()==data.type){
							$(this).prop("selected","selected");
						}
					});
					$("#disabled option").each(function(){
						if($(this).val()==data.disabled){
							$(this).prop("selected","selected");
						}
					});
					$("#type").select2({
						minimumResultsForSearch: Infinity
					});
					$("#disabled").select2({
						minimumResultsForSearch: Infinity
					});
				});
				$("#resource-addModal").modal("show");
				$("#resource-addModal h3").html("编辑资源");
			},
			doEdit : function(){
				if(self.resource.checkForm()){
					// 添加资源
					$.post("resource/edit",$("#resourceForm").serialize(),function(data){
						if(data > 0){
							$("#resource-addModal").modal("hide");
				            $("#resource-addModal").on('hidden.bs.modal', function (e) {//此事件在模态框被隐藏（并且同时在 CSS 过渡效果完成）之后被触发。
				            	self.resource.list();
				            });
						}else{
							$("#resource-alert").removeClass("hide");
				            $("#resource-info").html("编辑失败");
						}
					})
				}else{
					return false;
				}
			},
			checkForm : function(){
				var flag = true;
				var name = $("#name").val().trim();
				var id = $("#resourceId").val() || 0;
				if(name == ''){
					$("#name").next().html("资源名称不能为空!");
					flag = false;
				}else{
					// 校验名称是否重复
					$.ajax({
						url : "resource/checkName",
						async : false,
						type : "post",
						data : {name:name,id:id},
						success : function(data){
							if(data > 0){
								$("#name").next().html("资源名称重复!");
								flag = false;
							}else{
								$("#name").next().html("");
							}
						}
					});
				}
				var permission = $("#permission").val().trim();
				if(permission == ''){
					$("#permission").next().html("表达式不能为空!");
					flag = false;
				}else{
					// 校验表达式是否重复
					$.ajax({
						url : "resource/checkPermission",
						async : false,
						type : "post",
						data : {permission:permission,id:id},
						success : function(data){
							if(data > 0){
								$("#permission").next().html("表达式重复!");
								flag = false;
							}else{
								$("#permission").next().html("");
							}
						}
					});
				}
				var priority = $("#priority").val().trim();
				var parentId = $("#parentId").val();
				if(priority == ''){
					$("#priority").next().html("优先级不能为空!");
					flag = false;
				}else{
					if(isNaN(priority) || priority!=parseInt(priority)){
						$("#priority").next().html("请输入整数优先级!");
						flag = false;
					}else{
						// 校验表达式是否重复
						$.ajax({
							url : "resource/checkPriority",
							async : false,
							type : "post",
							data : {priority:priority,id:id,parentId:parentId},
							success : function(data){
								if(data > 0){
									$("#priority").next().html("优先级重复!");
									flag = false;
								}else{
									$("#priority").next().html("");
								}
							}
						});
					}
				}
				
				var type = $("#select2-type-container").html();
				if(type == "请选择"){
					$("#type").next().next().html("请选择资源类型!");
					flag = false;
				}else{
					$("#type").next().next().html("");
				}
				
				var disabled = $("#select2-disabled-container").html();
				if(disabled == "请选择"){
					$("#disabled").next().next().html("请选择资源状态!");
					flag = false;
				}else{
					$("#disabled").next().next().html("");
				}
				return flag;
			}
	};
	self.role = {
			pageQuery : function(currentPage,pageSize){
				currentPage = currentPage || 1;
				pageSize = pageSize || $("#pageSize").val();
				$.post("role/pageQuery",{"currentPage":currentPage,"pageSize":pageSize},function(responseText){
					$("#main-content").html(responseText);
					$("#saveOrUpdate").click(function(){
						var f = $("#saveUpdateFlag").val();
						if(f=='save'){
							self.role.doAdd();
						}else{
							self.role.doEdit(currentPage);
						}
					});
					$("#distribute").click(function(){
						self.role.doDistribution(currentPage);
					});
					$("#grant").click(function(){
						self.role.doGrant(currentPage);
					});
					$('.tree').treegrid({
						treeColumn: 1
					});
				});
			},
			doGrant : function(currentPage){
				$.post("role/grant",$("#roleGrantForm").serialize(),function(data){
					$("#role-grant").modal("hide");
					$("#role-grant").on('hidden.bs.modal', function (e) {//此事件在模态框被隐藏（并且同时在 CSS 过渡效果完成）之后被触发。
						self.role.pageQuery(currentPage);
		            });
				});
			},
			toGrant : function(roleId){
				$("#roleIdGrant").val(roleId);
				$.ajax({
					url:"role/getResourcesByRole",
					async : false,
					type : "post",
					data : {roleId:roleId},
					success : function(data){
						$(".resourceCheck").each(function(){
							$(this).prop("checked",false);
						});
						$(".resourceCheck").each(function(){
							for(var i in data){
								if($(this).val()==data[i].id){
									$(this).prop("checked",true);
									break;
								}else{
									$(this).prop("checked",false);
								}
							}
						});
						$("#role-grant").modal("show");
					}
				});
			},
			doDistribution : function(currentPage){
				$.post("role/distribute",$("#roleDistributionForm").serialize(),function(data){
					$("#role-distribution").modal("hide");
					$("#role-distribution").on('hidden.bs.modal', function (e) {//此事件在模态框被隐藏（并且同时在 CSS 过渡效果完成）之后被触发。
						self.role.pageQuery(currentPage);
		            });
				});
			},
			toDistribution : function(roleId){
				$("#roleIdDis").val(roleId);
				$.ajax({
					url:"role/getBigCustomersByRole",
					async : false,
					type : "post",
					data : {roleId:roleId},
					success : function(data){
						$("#bigCustomerIds input[type='checkbox']").each(function(){
							$(this).prop("checked",false);
						});
						$("#bigCustomerIds input[type='checkbox']").each(function(){
							for(var i in data){
								if($(this).val()==data[i].userId){
									$(this).prop("checked",true);
									break;
								}else{
									$(this).prop("checked",false);
								}
							}
						});
						$("#role-distribution").modal("show");
					}
				});
			},
			onOrOff : function(id,disabled,currentPage){
				if(disabled == 0){
					self.common.confirm("您确认启用该角色吗?",function(){
						$.post("role/edit",{id:id,disabled:disabled},function(data){
							if(data > 0){
								self.role.pageQuery(currentPage);
							}
						});
					});
				}else{
					self.common.confirm("您确认禁用该角色吗?",function(){
						$.post("role/edit",{id:id,disabled:disabled},function(data){
							if(data > 0){
								self.role.pageQuery(currentPage);
							}
						});
					});
				}
			},
			toAdd : function(){
				$("#roleForm input").val("");
				$(".help-inline").html("");
				$("#saveUpdateFlag").val("save");
				$("#disabled option:selected").removeAttr("selected");
				$("#disabled").select2({
					minimumResultsForSearch: Infinity
				});
				$("#role-addModal").modal("show");
				$("#role-addModal h3").html("新增角色");
			},
			doAdd : function(){
				if(self.role.checkForm()){
					// 添加资源
					$.post("role/add",$("#roleForm").serialize(),function(data){
						if(data > 0){
							$("#role-addModal").modal("hide");
				            $("#role-addModal").on('hidden.bs.modal', function (e) {//此事件在模态框被隐藏（并且同时在 CSS 过渡效果完成）之后被触发。
				              self.role.pageQuery('1');
				            });
						}else{
							$("#role-alert").removeClass("hide");
				            $("#role-info").html("添加失败");
						}
					})
				}else{
					return false;
				}
			},
			toEdit : function(id){
				$("#saveUpdateFlag").val("update");
				$.post("role/findOne",{id,id},function(data){
					data = eval(data);
					$(".help-inline").html("");
					$("#name").val(data.name);
					$("#code").val(data.code);
					$("#description").val(data.description);
					$("#roleId").val(data.id);	
					$("#disabled option").each(function(){
						if($(this).val()==data.disabled){
							$(this).prop("selected","selected");
						}
					});
					$("#disabled").select2({
						minimumResultsForSearch: Infinity
					});
				});
				$("#role-addModal").modal("show");
				$("#role-addModal h3").html("编辑角色");
			},
			doEdit : function(currentPage){
				if(self.role.checkForm()){
					// 添加资源
					$.post("role/edit",$("#roleForm").serialize(),function(data){
						if(data > 0){
							$("#role-addModal").modal("hide");
				            $("#role-addModal").on('hidden.bs.modal', function (e) {//此事件在模态框被隐藏（并且同时在 CSS 过渡效果完成）之后被触发。
				            	self.role.pageQuery(currentPage);
				            });
						}else{
							$("#role-alert").removeClass("hide");
				            $("#role-info").html("编辑失败");
						}
					})
				}else{
					return false;
				}
			},
			checkForm : function(){
				var flag = true;
				var name = $("#name").val().trim();
				var id = $("#roleId").val() || 0;
				if(name == ''){
					$("#name").next().html("角色名称不能为空!");
					flag = false;
				}else{
					// 校验名称是否重复
					$.ajax({
						url : "role/checkName",
						async : false,
						type : "post",
						data : {name:name,id:id},
						success : function(data){
							if(data > 0){
								$("#name").next().html("角色名称重复!");
								flag = false;
							}else{
								$("#name").next().html("");
							}
						}
					});
				}
				var code = $("#code").val().trim();
				if(code == ''){
					$("#code").next().html("角色编码不能为空!");
					flag = false;
				}else{
					// 校验表达式是否重复
					$.ajax({
						url : "role/checkCode",
						async : false,
						type : "post",
						data : {code:code,id:id},
						success : function(data){
							if(data > 0){
								$("#code").next().html("角色编码重复!");
								flag = false;
							}else{
								$("#code").next().html("");
							}
						}
					});
				}
				
				var disabled = $("#select2-disabled-container").html();
				if(disabled == "请选择"){
					$("#disabled").next().next().html("请选择资源状态!");
					flag = false;
				}else{
					$("#disabled").next().next().html("");
				}
				return flag;
			}
	};
	return self;
})(permission);








