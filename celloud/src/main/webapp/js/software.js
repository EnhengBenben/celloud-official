		//记录当前查看的App,以备前进使用
		var currentAppId="";
		var currentIsAdd="";
		var currentPage = 1; 
		var pageSize = 8;
		var classifyId = 0;//默认按照类别进行全查
		var requestType = 0;//记录是根据classifyId 查询还是 条件查询，0 是classifyId，1 是条件查询
		$.ajaxSetup ({
			cache: false //关闭AJAX相应的缓存
		});
		function initSoftware(){
			//搜索
			$("#search").click(function(){
				requestType = 1;
				currentPage = 1;
				refreshTable();
			});
			//初始进入时全部查询
			refreshTable();
			//按回车键查询
			$("#search_softName").bind('keydown', function(event){
			   if (event.keyCode=="13"){
				  requestType = 1;
				  currentPage = 1;
				  refreshTable();
			   }
			});
		}
		//分页查询
		function selectByPage(pageNow){
			currentPage = pageNow;
			refreshTable();
		}
		//查询某个软件的详细信息
		function softDetail(softId,isAdd){
			currentAppId = softId;
			currentIsAdd = isAdd;
			$.get("getOneSoftware.action",{"soft.softwareId":softId,"soft.isAdd":isAdd},function(resposeText){
				$("#softDetailDiv").html(resposeText);
				$("#softListDiv").attr("style","display:none;");
				$("#softDetailDiv").attr("style","width: 635px;margin-left: 240px;margin-top: -720px;");
				$("#leftBarDiv").attr("style","height:700px;");
			});
		}
		//新增软件并修改人气指数,flag表示更新人气指数后是否返回列表页面，0不返回 1返回
		function addBhri(objId,softId,softwareName,image,src,isAdd,flag){
			var href = $("#"+objId).attr("href");
			$("#"+objId).attr("href","");
			if(softwareName=="454"){
				if(sessionUserRole!=3){//只有CDC用户可以添加
					jAlert("对不起，您没有权限添加该App！");
					$("#"+objId).attr("href",href);
					return;
				}
			}
			var deskNo = parent.getDeskNo();
			$.get("updateBhri.action",{"soft.softwareId":softId,"deskNo":(deskNo+1)},function(responseData){
				if(responseData){
					$.get("getAppFormats.action",{"soft.softwareId":softId},function(formats){
						var newSrc = src;
						if(formats!=""){
							newSrc += "?formats="+formats;
						}
						parent.addApp(deskNo,softId,softwareName,image,newSrc);
						if(flag==1){
							refreshTable();
						}else{
							currentIsAdd = "yes";
							$.get("getOneSoftware.action",{"soft.softwareId":softId,"soft.isAdd":currentIsAdd},function(resposeText){
								$("#softDetailDiv").html(resposeText);
							});
						}
					});
				}else{
					jAlert("添加失败！");
					$("#"+objId).attr("href",href);
				}
			});
		}
		//-------------------------------------------------------------------------------
		//加载左侧导航栏，加载所有软件一级分类，点击一级分类获取二级分类
		$.get("getAllClassifyJson.action",{},function(list){
			$("#showList").html("<h2 class='h2_active' id='allsoft' onclick=javascript:selectByClassifyId(0,1,0);><span class='arrow'></span><i class='appmenu'></i>全部软件</h2>");
			var ulContext = "";
			for ( var i = 0; i < list.length; i++) {
				var liContext = "<h2 id='accordionBody"+list[i].classifyId+"' onclick=javascript:selectSubByClassifyId('"+list[i].classifyId+"',0,0)><span class='arrow'></span><i class='appmenu'></i>"+list[i].classifyName+"</h2><div class='leftMenu'><ul class='leftMenuList' id='subClassifyDiv"+list[i].classifyId+"' style='display:none;' isOpen='0'></ul></div>";
				ulContext += liContext;
			}
			$("#showList").append(ulContext);
		});
		//统计所有软件数量
		$.post("getAllSoftwareNum.action",{},function(response){
			$("#num").html("共有<font color='red'>"+response+"</font>个应用");
		});
		//查询子分类
		function selectSubByClassifyId(classifyId){
			$.each($(".h2_active"),function(index,item){
				$(this).removeClass("h2_active");
			})
			$("#accordionBody"+classifyId).addClass("h2_active");
			$.each($(".leftMenu>ul"),function(index,item){
				$(this).attr("style","display:none;");
				$(this).attr("isOpen","0");
			});
			//先判断是否是展开的
			var open = $("#subClassifyDiv" + classifyId).attr("isOpen");
			if(open=="0"){//不为空代表隐藏状态
				$.get("getSubClassifyById.action",{"classify.classifyId":classifyId},function(list){
					$("#subClassifyDiv" + classifyId).html("");
					var subClasses = "";
					for ( var i = 0; i < list.length; i++) {
						subClasses += "<li><a href=\"javascript:selectByClassifyId('"+list[i].classifyId+"',0,1)\"><span id='subClassify"+list[i].classifyId+"'>"+list[i].classifyName+"</span</a></li>";
					}
					$("#subClassifyDiv" + classifyId).html(subClasses);
					$("#menuLi"+classifyId).addClass("active");
					$("#subClassifyDiv" + classifyId).attr("style","margin-left:50px;");
				});
				$("#subClassifyDiv" + classifyId).attr("isOpen","1");
			}else{
				$("#subClassifyDiv" + classifyId).attr("isOpen","0");
				$("#subClassifyDiv" + classifyId).html("");
				$("#subClassifyDiv" + classifyId).attr("style","display:none;");
			}
		}
		//查询子分类下的软件
		//按照classifyId查询,flag区分是否由返回首页触发,1代表由返回首页图标触发;type:层级 
		function selectByClassifyId(classify_id,flag,type){
			if(classify_id==0){
				$.each($(".leftMenu>ul"),function(index,item){
					$(this).attr("style","display:none;");
				});
			}
			var pitems = $(".accordion-body>div>p");
			$.each(pitems,function(index,item){
				$(item).attr("class","");
			});
			if(flag==0){
				$("#accordionBody" + classify_id).attr("class","pshadow");
				if(type==0){
					$.each($(".h2_active"),function(index,item){
						$(this).removeClass("h2_active");
					})
					$("#accordionBody" + classify_id).addClass("h2_active");
				}else{
					$.each($(".h2_active"),function(index,item){
						$(this).removeClass("h2_active");
					})
					$.each($(".a_active"),function(index,item){
						$(this).removeClass("a_active");
					})
					$("#subClassify" + classify_id).addClass("a_active");
				}
			}else if(flag==1){//返回首页
				$.each($(".h2_active"),function(index,item){
					$(this).removeClass("h2_active");
				})
				$("#allsoft").addClass("h2_active");
				$("#search_softName").val("");
				
			}
			classifyId = classify_id;
			currentPage = 1;
			requestType = 0;
			refreshTable();
		}
		//刷新App列表
		function refreshTable(){
			if(requestType == 1){
				var softName = $.trim($("#search_softName").val());
				if(softName==""||softName==null){
					$.post("getSoftwareList.action",{"soft.classifyId":classifyId,"page.currentPage":currentPage,"page.pageSize":pageSize},function(responseText){
						$("#showDiv").html(responseText);
					});
				}else{
					$.get("selectConditonSoft.action",{"soft.softwareName":softName,"page.currentPage":currentPage,"page.pageSize":pageSize},function(responseData){
						$("#showDiv").html(responseData);
					});
				}
			}else{
				$.post("getSoftwareList.action",{"soft.classifyId":classifyId,"page.currentPage":currentPage,"page.pageSize":pageSize},function(responseText){
					$("#showDiv").html(responseText);
				});
			}
			$("#softListDiv").attr("style","width: 500px;margin-left: 200px;padding:0;margin-top: -557px");
			$("#softDetailDiv").attr("style","display:none;");
			$("#leftBarDiv").attr("style","height:500px;");
		}
			//返回到首页
			function goToSoftListPage(){
				refreshTable();
			}
			//提交评论
			function submitComment(softwareId){
				var comment = $.trim($("#inputComment").val());
				if(comment==""){
					$("#commentInfoDiv").html("请输入评论内容！");
					return;
				}else{
					$("#commentInfoDiv").html("");
					$.get("addComment.action",{"comment.softwareId":softwareId,"comment.comment":comment},function(flag){
						if(flag>0){
							$.get("getOneSoftware.action",{"soft.softwareId":softwareId,"soft.isAdd":"yes"},function(resposeText){
								$("#softDetailDiv").html(resposeText);
							});
						}else{
							$("#commentInfoDiv").attr("style","");
							$("#commentInfoDiv").html("评论保存失败！");
						}
					});
				}
			}
			//删除评论,delType:0:删除评论  1：删除回复
			function deleteComment(softwareId,commentId,delType){
				var info;
				if(delType==0){
					info = "删除该评论，所有回复也会删除，确定删除该评论吗？";
				}else{
					info = "确定删除该回复吗？";
				}
		        jConfirm(info, '确认提示框', function(r) {
					if(r){
						$.get("delComment.action",{"comment.id":commentId,"delType":delType},function(flag){
		    				if(flag>0){
		    					$.get("getOneSoftware.action",{"soft.softwareId":softwareId,"soft.isAdd":"yes"},function(resposeText){
		    						$("#softDetailDiv").html(resposeText);
		    					});
		    				}else{
		    					jAlert("删除失败！");
		    				}
		    			});
					}
				});
			}
			//回复
			function replyComment(commentId){
				$("#"+commentId+"Textarea>textarea").val("");
				$("#"+commentId+"SpanInfo").html("");
				$("#"+commentId+"Textarea").attr("style","");
				$("#"+commentId+"Asubmit").attr("style","");
				$("#"+commentId+"Acancel").attr("style","");
			}
			
			//取消回复
			function cancelReply(commentId){
				$("#"+commentId+"SpanInfo").html("");
				$("#"+commentId+"Textarea").attr("style","display: none;");
				$("#"+commentId+"Asubmit").attr("style","display: none;");
				$("#"+commentId+"Acancel").attr("style","display: none;");
			}
			
			//提交回复
			function submitReply(commentId,softwareId){
				var comment = $.trim($("#"+commentId+"Textarea>textarea").val());
				if(comment==""){
					$("#"+commentId+"SpanInfo").html("请输入回复内容！");
					return;
				}else{
					$("#"+commentId+"SpanInfo").html("");
					$.get("replyComment.action",{"comment.id":commentId,"comment.comment":comment},function(flag){
						if(flag>0){
							$.get("getOneSoftware.action",{"soft.softwareId":softwareId,"soft.isAdd":"yes"},function(resposeText){
								$("#softDetailDiv").html(resposeText);
							});
						}else{
							jAlert("回复失败！");
						}
					});
				}
			}
			
			//回复列表翻页
			function selectCommentByPage(softwareId,isAdd,page){
				$.get("selectCommentByPage.action",{"soft.softwareId":softwareId,"page.currentPage":page,"page.pageSize":10},function(responseText){
					$("#commentDiv").html(responseText);
				});
			}
			function goToAppInfo(id){
		    	var appId = $("#goToAppInfoBtn" + id).attr("_appId");
		    	var appName = $("#goToAppInfoBtn" + id).attr("_appName");
		    	var iframeSrc = $("#goToAppInfoBtn" + id).attr("_iframeSrc");
		    	var imgSrc = $("#goToAppInfoBtn" + id).attr("_appImg");
		    	var isOpen = $("#goToAppInfoBtn" + id).attr("_isOpen");
		    	parent.appDetailToAppInfo(appId,appName,iframeSrc,imgSrc,isOpen);
		    }