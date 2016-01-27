/**
*公司管理
**/
$.ajaxSetup ({
	  cache: false //关闭AJAX相应的缓存
  });
var company=(function(company){
	var self=company||{};
	var uploader = null;
	self.currentPage = 1;
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
	            }
	        }
	    });
		uploader.init();
	}
	self.toCompanyMain=function(){
		$.post("company/companyMain",function(responseText){
			$("#main-content").html(responseText);
			$("#main-menu li").removeClass("active").removeClass("opened");
			$("#company-menu").addClass("active");
		});
	}
	self.getCompanyAsync=function(currentPage){
		self.currentPage=currentPage;
		$.ajax({
			url:"company/companyMain",
			data:{currentPage:currentPage},
			async:false,
			success:function(responseText){
				$("#main-content").html(responseText);
			}
		});
	};
	self.getCompany=function(currentPage){
		self.currentPage=currentPage;
		$.post("company/companyMain",{currentPage:currentPage},function(responseText){
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
	
	self.saveCompany=function(){
		$.post("company/save",$("#companyForm").serialize(),function(responseText){
			if(responseText>0){
				$("#company-editModal").modal("hide");
				alert("成功");
				self.getCompanyAsync(self.currentPage);
			}
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
		$.post("company/companyDelete",{companyId:companyId},function(responseText){
			if(responseText>0){
				alert("成功");
				self.getCompanyAsync(self.currentPage);
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
	            }
	        }
	    });
		uploader.init();
	}
	self.getDeptAsync=function(currentPage){
		self.currentPage=currentPage;
		$.ajax({
			url:"dept/deptList",
			data:{currentPage:currentPage,companyId:self.companyId},
			async:false,
			success:function(responseText){
				$("#dept-manager").html(responseText);
			}
		});
	};
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
	
	self.saveDept=function(){
		$.post("dept/save",$("#deptForm").serialize(),function(responseText){
			if(responseText>0){
				$("#dept-editModal").modal("hide");
				alert("成功");
				self.getDeptAsync(self.currentPage);
			}
		});
	}
	self.deleteDept=function(deptId){
		$.post("dept/deptDelete",{deptId:deptId},function(responseText){
			if(responseText>0){
				alert("成功");
				self.getDeptAsync(self.currentPage);
			}
		});
			
	}
	
	
	return self;
})(dept);
