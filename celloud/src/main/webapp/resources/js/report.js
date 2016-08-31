$.ajaxSetup ({
	cache: false //关闭AJAX相应的缓存
});
//----------------------loading效果参数配置----------------------------
		var opts = {
		  lines: 13, // The number of lines to draw
		  length: 28, // The length of each line
		  width: 14, // The line thickness
		  radius: 42, // The radius of the inner circle
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
		var spinnerTotal;
		//---------------------------------------------------------------------
		var param = null;
		function printPgsProject(projectId){
			$.get("report/checkPgsProject",{projectId:projectId},function(flag){
				if(flag != 3){
					jAlert("该项目尚未运行完毕");
				}else{
					window.open(CONTEXT_PATH + "/report/printPgsProject?projectId=" + projectId);
				}
			});
		}
		function downPDF(userId,appId,projectId){
		  var path = userId + "/" + appId + "/" + projectId + "/" + projectId + ".pdf";
			$.get("report/down",{"path":path},function(flag){
				if(flag==1){
				  jAlert("没有可以下载的pdf文件");
				}else{
				  var url = window.location.href.split("index")[0];
				  window.location.href=url+"report/down?path="+path;
				}
			});
		}
		function initReportDetail(){
			submitSearch();
			// 初始化高级检索app列表
			var _num = 0;
			$.get("app/getRanAPP",function(appList){
				var total = appList.length;
				var context = "";
				if(total<9){
					for ( var i = 0; i < total; i++) {
						var id = appList[i]['app_id'];
						var name = appList[i]['app_name'];
						if(_num>1&&_num%8==0){
							context+="<br/>";
						}
						_num++;
						if(APP==id){
							context+="<a href='javascript:void(0)' class='capp _appred' onclick='changeApp("+id+",this)' id='_app"+id+"'>"+name+"</a>&nbsp;&nbsp;&nbsp;&nbsp;";
						}else{
							context+="<a href='javascript:void(0)' class='capp' onclick='changeApp("+id+",this)' id='_app"+id+"'>"+name+"</a>&nbsp;&nbsp;&nbsp;&nbsp;";
						}
					}
					$("#showMore").attr("style","display:none");
					$("#showAppDiv").html(context);
				}else{
					for ( var i = 0; i < 8; i++) {
						var id = appList[i]['app_id'];
						var name = appList[i]['app_name'];
						if(_num>1&&_num%8==0&&i>0){
							context+="<br/>";
						}
						_num++;
						if(APP==id){
							context+="<a href='javascript:void(0)' class='capp _appred' onclick='changeApp("+id+",this)' id='_app"+id+"'>"+name+"</a>&nbsp;&nbsp;&nbsp;&nbsp;";
						}else{
							context+="<a href='javascript:void(0)' class='capp' onclick='changeApp("+id+",this)' id='_app"+id+"'>"+name+"</a>&nbsp;&nbsp;&nbsp;&nbsp;";
						}
					}
					context += "</div>";
					$("#showAppDiv").html(context);
					context ="<div class='moreApp' style='margin-top:-20px;margin-left: 80px;'>";
					_num = 0;
					for ( var i = 8; i < appList.length; i++) {
						var id = appList[i]['app_id'];
						var name = appList[i]['app_name'];
						if(_num%9==0){
							context+="<br/>";
						}
						_num++;
						if(APP==id){
							context+="<a href='javascript:void(0)' class='capp _appred' onclick='changeApp("+id+",this)' id='_app"+id+"'>"+name+"</a>&nbsp;&nbsp;&nbsp;&nbsp;";
						}else{
							context+="<a href='javascript:void(0)' class='capp' onclick='changeApp("+id+",this)' id='_app"+id+"'>"+name+"</a>&nbsp;&nbsp;&nbsp;&nbsp;";
						}
					}
					context += "</div>";
					$("#appList").html(context);
				}
			});
		}
		
		//切换项目报告
		function toProjectReport(){
		  $.main.show.mainDIV();
		}
		
		//删除共享来的项目报告
		function cancelProjectShare(projectId){
			jConfirm("确定删除该分享来的项目报告吗？", '确认提示框', function(r) {
				if(r){
					$.get("project/deleteShare",{"projectId":projectId},function(response){
						if(response){
							currentPage = 1;
							submitSearch();
						}else{
							jAlert("删除分享来的项目报告失败");
						}
					});
				}
			});
		}
		
		//删除项目报告
		function removePro(proId){
			jConfirm("确定删除该项目报告吗？", '确认提示框', function(r) {
				if(r){
					$.get("project/deleteByState",{"projectId":proId},function(flag){
						if(flag ==1){
							currentPage = 1;
							submitSearch();
						}else{
							jAlert("项目报告删除失败");
						}
					});
				}
			});
		}
		function showConclusion(){
		  $("#conclusion-error").addClass("hide");
		  $("#reportConclusion").find("input:radio[name='qualified']:checked").attr('checked',false);
		  $("#reportConclusion").find("textarea[name='remarks']").val("");
		  $("#reportConclusion").modal("show");
		}
		function saveConclusion(){
		  var qualified = $("#reportConclusion").find("input:radio[name='qualified']:checked").val();
		  if(!qualified){
		    showAddError("请选择是否合格！");
		    return;
		  }
		  $.get("experiment/updateExperiment",$("#reportConclusionForm").serialize(),function(flag){
		    if(flag == 1){
		      showAddError("保存成功，请重新打开数据报告！");
		      setTimeout("hidModal('reportConclusion')",5000);
		    }else{
		      showAddError("保存失败！");
		    }
		  });
		}
		
		function hidModal(id){
		  $("#"+id).modal("hide");
		}
		
		function showAddError(info){
		  $("#conclusionErrorInfo").html(info);
      $("#conclusion-error").removeClass("hide");
		}
		
		function editShowConclusion(){
		  $("#edit-conclusion-error").addClass("hide");
		  $("#editReportConclusion").modal("show");
		}
		function editSaveConclusion(){
		  var qualified = $("#editReportConclusion").find("input:radio[name='qualified']:checked").val();
		  if(!qualified){
		    showEdditError("请选择是否合格！");
		    return;
		  }
		  var remarks = $("#editReportConclusion").find("textarea[name='remarks']").val();
      if(remarks.length>125){
        showEdditError("备注长度不能大于125个字符！！");
        return;
      }
		  $.get("experiment/updateExperiment",$("#editReportConclusionForm").serialize(),function(flag){
		    if(flag == 1){
		      showEdditError("保存成功，请重新打开数据报告！");
          setTimeout("hidModal('editReportConclusion')",5000);
		    }else{
		      showEdditError("保存失败！");
		    }
		  });
		}
		
		function showEdditError(info){
		  $("#edit-conclusionErrorInfo").html(info);
		  $("#edit-conclusion-error").removeClass("hide");
		}
		
		var START = null;		//检索开始时间
		var END = null;	//检索结束时间
		var APP = 0;		//appid
		var FILENAME = null;		//文件名称模糊检索条件
		var currentPage = 1;	//当前页
		var pageSize = 10;	//每页显示记录数
		
		//根据文件名称模糊检索
		function changeFileName(){
			FILENAME = $("#_fileName").val();
			currentPage = 1;
		}
		var isTIMETRUE = true;
		//根据时间精确检索
		function changeDate(flag,obj){
			$(".cdate").removeClass("_datered");
			if(flag=='allTime'){
				$("#_searchDate").val("");
				$("#_endDate").val("");
				$(obj).addClass("_datered");
				START = null;
				END = null;
				isTIMETRUE = true;
			}else if(flag!=0){
				$("#_searchDate").val("");
				$("#_endDate").val("");
				$(obj).addClass("_datered");
				var d = new Date();
				var date = new Date(d.getTime()+1000*60*60*24*flag);
				var year = date.getFullYear();    //获取完整的年份(4位,1970-????)
				var mounth = date.getMonth()+1;       //获取当前月份(0-11,0代表1月)
				var day = date.getDate();        //获取当前日(1-31)
				START = year+"-"+mounth+"-"+day;
				END = d.getFullYear()+"-"+(d.getMonth()+1)+"-"+d.getDate();
				isTIMETRUE = true;
			}else{
				isTIMETRUE = false;
			}
			$("#_alertSpan").css("display","none");
			$("#_alertSpan").html("");
			currentPage = 1;
			if(START != null){
				START = START+" 00:00:00";
			}
			if(END != null){
				END = END+" 23:59:59";
			}
			if(isTIMETRUE){
				submitSearch();
			}
		}
		
		//根据appid精确检索
		function changeApp(appId,obj){
			$(".capp").removeClass("_appred");
			$(obj).addClass("_appred");
			APP = appId;
			currentPage = 1;
			submitSearch();
		}
		//所属人
		var BELONGS = 1;
		function changeBelongs(belongs,obj){
		  $(".belongs").removeClass("_datered");
		  $(obj).addClass("_datered");
		  BELONGS = belongs;
		  currentPage = 1;
		  submitSearch();
		}
		
		// 切换每页显示的记录数
		function changePageSize(){
			pageSize = $("#proPageRecordSel").val();
			currentPage = 1;
			submitSearch();
		}
		
		//翻页
		function changePage(page){
			currentPage = page;
			submitSearch();
		}
		//高级检索
		function submitSearch(){
			if(!isTIMETRUE){
				START =$("#_searchDate").val();
				END = $("#_endDate").val();
				if((!START && END)||(START && !END)){
					$("#_alertSpan").css("display","");
					$("#_alertSpan").html("请同时选择起始时间和结束时间");
					return ;
				}
				if(START>END){
					$("#_alertSpan").css("display","");
					$("#_alertSpan").html("起始日期不能大于结束日期");
					return ;
				}
				if(START != null && START != ""){
					START = START+" 00:00:00";
				}
				if(END != null && END != ""){
					END = END+" 23:59:59";
				}
			}
			//匹配中文 数字 字母 下划线       
      var pattern=/[`~!@#\$%\^\&\*\(\)\+<>\?:"\{\},\.\\\/;'\[\]]/im;
      if(pattern.test(FILENAME)){
        $("#_dataAlertSpan").css("display","");
        $("#_dataAlertSpan").html("输入框禁止输入特殊字符");
        return ;
      }
      $("#_dataAlertSpan").css("display","none");
      $("#_dataAlertSpan").html("");
			spinner = new Spinner(opts);
			var target = document.getElementById('reportLoading');
			spinner.spin(target);
			$.get("report/getReportPageList",{"belongs":BELONGS,"appId":APP,"start":START,"end":END,"condition":FILENAME,"size":pageSize,"page":currentPage},function(responseText){
				$("#selfReportDiv").html(responseText);
				$("body").scrollTop(0);
				loadReportList();
				spinner.stop();
			});
		}
		
		function loadReportList(){
			$("#_show").find(".no").each(function(){
			    var info = $.trim($(this).prev().html()).split(",");
			    var appId = info[0];
			    var appName = info[1];
			    var proId = info[2];
			    var userId = info[3];
			    var proName = $("#pnameSpan" + proId).html();
			    var length = 0;
			    var th_size = 0;
			    var td_size = 0;
			    var tr_size = $(this).find("tr").length;
			    $(this).find("tr").each(function(j){
			    	if(j==0){
			    		th_size = $(this).children().length;
			    	}else{
			    		td_size = $(this).children().length;
			    	}
			        var trc = $(this).children().length;
			        if(appId=="80"){
			            if(j==0){
			                length = $(this).children().length;
			            }
			            if(length>6){
			                if(j>1&&j%2==0){
			                    $(this).addClass("_HCVTR");
			                    $(this).css("display","none");
			                    $(this).children().attr("colspan","7");
			                    $(this).children().css("word-break","break-all");
			                }
			            }
			        }
			        $(this).mouseover(function(){
			        	$(this).find("span").addClass("link_hover");
			        });
			        $(this).mouseout(function(){
			        	$(this).find("span").removeClass("link_hover");
			        });
			        $(this).find("td").each(function(i){
			            if(appId=="90"){
			                param = {"fileName":$.trim($(this).html()),"dataKey":$.trim($(this).prev().html()),"softwareId":appId,"softwareName":appName,"userId":userId,"obj":$(this),"proId":proId,"proName":proName};
			                if(i!=1){
			                    if(trc==4){
			                        if(i==0){
			                            $(this).addClass("none");
			                        }
			                    }else{
			                        $(this).addClass("none");
			                    }
			                }else{
			                    $(this).css("border-left","none");
			                    if(j>0){
			                        $(this).addClass("sub");
			                        var fileName = $(this).html();
			                        if(fileName.length>30){
			                        	fileName = fileName.substring(0,30) + "...";
			                        }
			                        $(this).html("<span id='dataSpan"+proId+$(this).prev().html()+"'>"+$(this).prev().html()+" （"+fileName+"）</span>");
			                        $(this).find("span").bind("click",param,viewDataReport);
			                        $(this).find("span").addClass("link");
			                    }
			                }
			            }
			            if(appId=="128"||appId=="127"||appId=="126"||appId=="118"||appId=="117"||appId=="114"||appId=="113"||appId=="112"||appId=="111"||appId=="110"||appId=="109"||appId=="106"||appId=="107"||appId=="108"||appId=="105"||appId=="82"||appId=="84"||appId=="89"||appId=="73"||appId=="1"){
			                param = {"fileName":$.trim($(this).html()),"dataKey":$.trim($(this).prev().html()),"softwareId":appId,"softwareName":appName,"userId":userId,"obj":$(this),"proId":proId,"proName":proName};
			                if(j>0&&i==1){
			                    $(this).addClass("sub");
			                    var fileName = $(this).html();
		                        if(fileName.length>30&&appId!="113"&&appId!="112"&&appId!="111"&&appId!="110"&&appId!="126"&&appId!="127"&&appId!="128"){
		                        	fileName = fileName.substring(0,30) + "...";
		                        }
		                        if(appId!="114"&&appId!="118"){
		                        	$(this).html("<span id='dataSpan"+proId+$(this).prev().html()+"'>"+$(this).prev().html()+" （"+fileName+"）</span>");
		                        }else{
		                        	$(this).html("<span id='dataSpan"+proId+$(this).prev().html()+"'>"+fileName+"</span>");
		                        }
			                    $(this).find("span").bind("click",param,viewDataReport);
			                    $(this).find("span").addClass("link");
			                }
			                if(i==0){
			                    $(this).addClass("none");
			                }
			                if(i==1){
			                    $(this).css("border-left","none");
			                }
			                if(appId=="82"){
			                    if($(this).html()=="不敏感"){
			                        $(this).addClass("red");
			                    }
			                }
			            }
			            if(appId=="91"||appId=="95"||appId=="92"||appId=="93"||appId=="94"||appId=="86"||appId=="87"||appId=="88"||appId=="81"||appId=="83"||appId=="85"||appId=="96"||appId=="97"||appId=="98"||appId=="99"||appId=="100"||appId=="101"||appId=="102"||appId=="103"||appId=="104"||appId=="116"||appId=="119"||appId=="120"||appId=="121"||appId=="122"||appId=="124"||appId=="125"){
			                if(j>0&&i==0){
			                    param = {"fileName":$.trim($(this).html()),"dataKey":$.trim($(this).next().html()),"softwareId":appId,"softwareName":appName,"userId":userId,"obj":$(this),"proId":proId,"proName":proName};
			                    $(this).addClass("sub");
			                    var fileName = $(this).html();
		                        if(fileName.length>30){
		                        	fileName = fileName.substring(0,30) + "...";
		                        }
			                    $(this).html("<span id='dataSpan"+proId+$(this).next().html()+"'>"+$(this).next().html()+" （"+fileName+"）</span>");
			                    $(this).find("span").bind("click",param,viewDataReport);
			                    $(this).find("span").addClass("link");
			                }
			                if(i==1){
			                    $(this).addClass("none");
			                }
			                if(j>0&&(i==4||i==3)){
        								var val = $(this).html();
        								if(!isNaN(val)&&val>999){
        									$(this).html($.format(val, 3, ','));
        								}
			                }
			            }
			            if(appId=="80"){
			                if(length==5){
			                    param = {"fileName":$.trim($(this).html()),"dataKey":$.trim($(this).prev().html()),"softwareId":appId,"softwareName":appName,"userId":userId,"obj":$(this),"proId":proId,"proName":proName};
			                    if(j>0&&i==1){
			                        $(this).addClass("sub");
			                        var fileName = $(this).html();
			                        if(fileName.length>30){
			                        	fileName = fileName.substring(0,30) + "...";
			                        }
			                        $(this).html("<span id='dataSpan"+proId+$(this).prev().html()+"'>"+$(this).prev().html()+" （"+fileName+"）</span>");
			                        $(this).find("span").bind("click",param,viewDataReport);
			                        $(this).find("span").addClass("link");
			                    }
			                    if(i==0){
			                        $(this).addClass("none");
			                    }
			                    if(i==1){
			                        $(this).css("border-left","none");
			                    }
			                    if($(this).html()=="No_Alignment"||$(this).html()=="Low_Quality"
			                        ||$(this).html()=="No Alignment"||$(this).html()=="Low Quality"){
			                        $(this).attr("colspan","2");
			                    }
			                }
			                if(length==7||length==8){
			                    if($(this).html()=="No_Alignment"||$(this).html()=="Low_Quality"
			                        ||$(this).html()=="No Alignment"||$(this).html()=="Low Quality"){
			                        $(this).attr("colspan","5");
			                    }
			                }
			                if(length==7){
			                        if(j>0&&j%2==1&&i==0){
			                            $(this).addClass("sub");
			                            $(this).html("<span id='dataSpan"+proId+$(this).html()+"'>"+$(this).html()+"</span>");
			                            $(this).find("span").bind("click",viewNext);
			                            $(this).find("span").addClass("link");
			                        }
			                }else if(length==8){
			                        if(i==1){
			                                $(this).css("border-left","none");
			                        }
			                        if((j==0||j%2==1)&&i==0){
			                                $(this).addClass("none");
			                        }
			                        if(j>0&&j%2==1&&i==1){
			                                $(this).addClass("sub");
			                                var fileName = $(this).html();
					                        if(fileName.length>30){
					                        	fileName = fileName.substring(0,30) + "...";
					                        }
			                                $(this).html("<span id='dataSpan"+proId+$(this).prev().html()+"'>"+$(this).prev().html()+" （"+fileName+"）</span>");
			                                $(this).find("span").bind("click",viewNext);
			                                $(this).find("span").addClass("link");
			                        }
			                }
			            }
			            if(appId=="11"){
			                param = {"fileName":$.trim($(this).html()),"projectId":proId,"softwareId":appId,"softwareName":appName,"userId":userId,"obj":$(this),"proId":proId,"proName":proName,"dataKey":$(this).prev().html()};
			                if(j>0&&i==1){
			                    $(this).addClass("sub");
			                    var fileName = $(this).html();
		                        if(fileName.length>30){
		                        	fileName = fileName.substring(0,30) + "...";
		                        }
			                    $(this).html("<span id='dataSpan"+proId+$(this).prev().html()+"'>"+$(this).prev().html()+" （"+fileName+"）</span>");
			                    $(this).find("span").bind("click",param,viewDataReport);
			                    $(this).find("span").addClass("link");
			                }
			                if(i==0){
			                    $(this).addClass("none");
			                }
			                if(i==1){
			                    $(this).css("border-left","none");
			                }
			            }
			            
			            if(td_size<th_size&&i==td_size-1){
			            	$(this).attr("colspan",th_size - td_size+1);
			            }
			            
			        });
			    });
			    var minTdNum = 5;
			    if(appId=="128"||appId=="127"||appId=="126"||appId=="113"||appId=="112"||appId=="111"||appId=="110"){
			    	minTdNum = 4;
			    }
			    var rdataNum = $("#rdataNum"+proId).html();
			    if((appId=="114"||appId=="118") && tr_size-1<rdataNum){
			    	var num = Number(rdataNum)-tr_size+1;
			    	var height = 30*(5-tr_size);
		    		var adHtml = "<tr><td colspan='"+th_size+"' style='border-left-style: none;vertical-align: middle;height:"+height+"px' align='center'><img src='/celloud/images/report/running.png' title='正在运行...'/><br>"+num+"个数据正在运行...</td></tr>";
		    		$(this).find("tbody").append(adHtml);
			    }else if(tr_size<minTdNum){
		    		var num = 5-tr_size;
		    		for(i=0;i<num;i++){
		    			var adHtml = "<tr>";
		    			for(j=0;j<th_size-1;j++){
		    				adHtml+="<td></td>";
		    			}
		    			adHtml+="</tr>";
		    			$(this).find("tbody").append(adHtml);
		    		}
			    }
			});
		}
		
		//标签关闭标志
		var close = 0;
		
		//显示更多检索条件
		function showSuper(obj,divId){
			if(!$("#"+divId).attr("class")){
				$("#"+divId).addClass("none");
				$(obj).html("更多  <i id=\"sortCreateDate\" class=\"fa fa-angle-double-down\"></i>");
			}else{
				$(obj).html("收起  <i id=\"sortCreateDate\" class=\"fa fa-angle-double-up\"></i>");
				$("#"+divId).removeClass("none");
			}
		}
		
		var dataReportParam;
		var dataItem0;
		var obj0;
		var reportIdNow;//记录当前报告ID
		//查看数据报告
		function viewDataReport(event){
		  $("#dataReportDIV").load("pages/report/report_data_main.jsp");
		  $("#reportResultDiv").html("");
		  $.main.show.dataReportDIV();
			if(typeof spinner != "undefined"){
				spinner.stop();
			}
			dataReportParam = event;
			var softwareId = event.data.softwareId;
			var fileName = event.data.fileName;
			var dataKey = event.data.dataKey;
			var softwareName = event.data.softwareName;
			var userId = event.data.userId;
			var obj = event.data.obj;
			var proId = event.data.proId;
			var proName = event.data.proName;
			obj.children(":first").addClass("link_visited");
			$("#fileNameH4").html("APP：" + softwareName);
			$("#proforReport").html(proName);
			$("#fileListUl").html("");
			spinner = new Spinner(opts);
			var target = document.getElementById('reportLoading');
			spinner.spin(target);
			
			$.get("data/getDatasInProject",{"projectId":proId},function(fileList){
				$("#fileListUl").append("<button type='button' id='prevA' class='btn btn-success'><span class='fa fa-sort-asc'></span></button>");
				var fileNames = new Array();
				var newList = "";
				if(softwareId == 110||softwareId == 111||softwareId == 112||softwareId == 113||softwareId == 126||softwareId == 127||softwareId == 128){
					$.each(fileList,function(index,item){
					  if(!utils.isConfigure(item.fileName)){
					    fileNames.push(item.fileName);
					  }
					});
					newList = "[{";
					fileNames.sort();
					var fileId_n = "fileId:";
					var fileName_n=",fileName:'";
					var dataKey_n=",dataKey:'"
					for(var i=0;i<fileNames.length;i++){
						fileName_n+=fileNames[i]
						if(i<fileNames.length-1){
							fileName_n+="_&_"
						}
					}
					$.each(fileList,function(index,item){
						if(fileNames[0] == item.fileName){
							fileId_n+=item.fileId;
							dataKey_n+=item.dataKey;
						}
					});
					newList+=fileId_n+fileName_n+"'"+dataKey_n+"'}]";
					newList=eval(newList);
				}else{
					newList=fileList;
				}
				$.each(newList,function(index,item){
					var inner = "";
					var seq = index+1;
					var obj1 = $("#dataSpan"+proId+item.dataKey);
					if(index == 0){
						dataItem0 = item;
						obj1 = $("#dataSpan"+proId+dataItem0.dataKey);
					}
					var isActive = "";
					if(item.dataKey == dataKey){
						isActive = "active";
						reportIdNow = 'fileA'+proId+item.fileId;
					}
					inner += "<button type='button' class='btn btn-success "+isActive+"' style='font-size:12px;' id='fileA"+proId+item.fileId+"' title='"+item.fileName+"'>"+(item.fileName.length>15?(item.fileName.substring(0,15)+"..."):item.fileName)+"</button>";
					$("#fileListUl").append(inner);
					$("#fileA"+proId+item.fileId).bind("click", function() {
						viewADataReport(item.dataKey,item.fileName,userId,softwareId,item.fileId,proId,obj1);
						$.get("report/clickItemDataReport",{},function(state){});
					});
				});
				$("#fileListUl").append("<button type='button' id='nextA' class='btn btn-success'><span class='caret'></span></button>");
				
				$("#prevA").bind("click",function(){
					var preId = $("#"+reportIdNow).prev().attr("id");
					if(preId=='prevA'){
						jAlert("已经是第一条数据");
						return ;
					}
					$("#fileListUl").find("#"+preId).trigger("click");
					$("#fileListUl").removeClass("active");
					$("#"+preId).addClass("active");
					$.get("report/prevDataReport",{},function(state){});
				});
				$("#nextA").bind("click",function(){
					var nextId = $("#"+reportIdNow).next().attr("id");
					if(nextId=='nextA'){
						jAlert("已经是最后一条数据");
						return ;
					}
					$("#fileListUl").find("#"+nextId).trigger("click");
					$("#fileListUl").removeClass("active");
					$("#"+nextId).addClass("active");
					$.get("report/nextDataReport",{},function(state){});
				});
			});
			viewADataReport(dataKey,fileName,userId,softwareId,0,proId,obj.children(":first"));
		}
		function viewADataReport(dataKey,fileName,userId,softwareId,fileId,proId,obj){
			reportIdNow = 'fileA'+proId+fileId;
			obj.addClass("link_visited");
			if(fileId != 0){
				$("#fileListUl").find(".active").removeClass("active");
				$("#fileListUl").removeClass("active");
				$("#"+reportIdNow).addClass("active");
			}
      $.get("report/"+appMethod[softwareId],{"projectId":proId,"dataKey":dataKey,"appId":softwareId},function(responseText){
        toDataReport(responseText,softwareId,charMap[softwareId],DATAPATH);
        if(softwareId == 114 || softwareId == 118){
          var mib_readsDisInfo = $("#reads-distribution-char").text();
          var mib_familyDisInfo = $("#family-distribution-char").text();
          var min_genusDisInfo = $("#genus-distribution-char").text();
          if(mib_readsDisInfo != ""){
            $.reportChar.draw.circularGraph("reads-distribution-char","Reads","Distribution",eval("("+mib_readsDisInfo+")"));
          }
          if(mib_familyDisInfo != ""){
            $.reportChar.draw.circularGraph("family-distribution-char","Family","Distribution",eval("("+mib_familyDisInfo+")"));
          }
          if(min_genusDisInfo != ""){
            $.reportChar.draw.singleBar("genus-distribution-char","Top 10 genus distribution","",eval("("+min_genusDisInfo+")"),"Depth","Depth");
          }
        }
      });
		}
		
		function toDataReport(responseText,appId,columns,DATAPATH){
			$("#reportResultDiv").html(responseText);
			if(appId==105){
				var mutant = $("#_hidMutant").val();
				var wild = $("#_hidWild").val();
				var neither = $("#_hidNeither").val();
				var data = [{name:'Mutant strain',value:mutant},{name:'Wild type',value:wild},{name:'No Result',value:neither}];
				$.reportChar.draw.echartsShowPie("_showPie","Samples Statistic",data);
			}
			if(appId==81||appId==83||appId==85||appId==86||appId==87||appId==88||appId==91||appId==92||appId==93||appId==94||appId==119||appId==120||appId==121||appId==122||appId==124||appId==125){
				var T = 0,M = 0;
				$("#_table").find("th").each(function(i){
					if($(this).html()=="Total_Reads"){
						T = i;
					}
					if($(this).html()=="Map_Reads"){
						M = i;
					}
				});
				$("#_table").find("td").each(function(i){
					if(i==T||i==M){
						var n = $(this).html();
						if(!isNaN(n)){
							$(this).html($.format(n, 3, ','));
						}
					}
				});
				$.get("count/pgsCompare",{"appId":appId,"columns":columns},function(data){
					var sp = data.split(";");
					$("#charResult").html("");
					for ( var i = 1; i < sp.length; i++) {
						var big = 0;
						var div = $("<div id='char"+i+"' class='col-lg-5' style='width: 410px;height:400px;'></div>");
						$("#charDiv").append(div);
						var ev = sp[i].split(":");
						var one = getCountValue(ev[0],"_table");
						var value = ev[1].split(",");
						var context="[";
						for(var k=0;k<value.length-1;k++){
							context += "[1,"+value[k]+"],";
							if(value[k]>one){
								big++;
							}
						}
						context+="]";
						var percent = 0;
						if(ev[0] == "Duplicate" || ev[0] == "Duplicate(%)" || ev[0] == "*SD"){
							percent = Math.round(big/value.length*100);
						}else{
							percent = 100 - Math.round(big/value.length*100);
						}
						$("#charResult").append("本数据的 "+ev[0]+" 打败了 <span class='green'>"+percent+"%</span> 的数据；");
						var single = "[[2,"+one+"]]";
						$.reportChar.draw.echartsShowScatter("char" + i, ev[0], eval(context),eval(single));
					}
				});
			}
			if(appId==80){
				$.get("count/hcvCompare",{},function(data){
						var div = $("<div id='char0' class='col-lg-6' style='width: 1000px;height:400px;'></div>");
						$("#charDiv").append(div);
						var one = getCountValue("Subtype","nomal");
						var X = "[";
						var Y = "[";
						var value = data.split(";");
						for(var k=0;k<value.length-1;k++){
							var n = value[k].split(",");
							X+="'"+n[0]+"',";
							if(n[0]==one){
								Y+="{ dataLabels: { enabled: true, y: 0, crop: false, style: { fontWeight: 'bold', fontSize:16 }, }, y:"+ n[1]+"},";
							}else{
								Y+=n[1]+",";
							}
						}
						X = X.substring(0,X.length-1)+"]";
						Y = Y.substring(0,Y.length-1)+"]";
						$.reportChar.draw.echartsShowBar("char0", "Subtype", eval(X), eval(Y), 0, 500, 300);
				});
			}
			if(appId==82){
				$.get("count/hbvCompare",{"appId":appId,"path":DATAPATH},function(data){
					var div0 = $("<div id='char0' class='col-lg-12' style='width: 1000px;height: 450px;'></div>");
					$("#charDiv").append(div0);
					var sType = $("#snpType").html();
					if(!sType){
						sType = "";
					}
					var hbvtype=eval(data.split("@")[1]);
					var aType = "[";
					if(sType.indexOf("A")>=0){
						aType += "['A'," + hbvtype[0]/2 + "]";
					}
					if(sType.indexOf("B")>=0){
						aType += ("'null',['B'," + hbvtype[1]/2 + "]");
					}
					if(sType.indexOf("C")>=0){
						aType += ("'null','null',['C'," + hbvtype[2]/2 + "]");
					}
					if(sType.indexOf("D")>=0){
						for(var i = 0;i < 3;i++){
							aType += 'null';
						}
						aType += ("['D'," + hbvtype[3]/2 + "]");
					}
					if(sType.indexOf("E")>=0){
						for(var i = 0;i < 4;i++){
							aType += 'null';
						}
						aType += ("['E'," + hbvtype[4]/2 + "]");
					}
					if(sType.indexOf("F")>=0){
						for(var i = 0;i < 5;i++){
							aType += 'null';
						}
						aType += ("['F'," + hbvtype[5]/2 + "]");
					}
					if(sType.indexOf("G")>=0){
						for(var i = 0;i < 6;i++){
							aType += 'null';
						}
						aType += ("['G'," + hbvtype[6]/2 + "G");
					}
					if(sType.indexOf("H")>=0){
						for(var i = 0;i < 7;i++){
							aType += 'null';
						}
						aType += ("['H'," + hbvtype[7]/2 + "H");
					}
					if(sType.indexOf("no match")>=0){
						for(var i = 0;i < 8;i++){
							aType += 'null';
						}
						aType += ("['比对失败'," + hbvtype[8]/2 + "]");
					}
					if(sType==""){
						for(var i = 0;i < 9;i++){
							aType += 'null';
						}
						aType += ("['异常数据'," + hbvtype[9]/2 + "]");
					}
					aType += "]";
					$.reportChar.draw.echartsShowHBVType('char0',hbvtype,aType,45);
					
					
					var result = $("#resultDiv").html();
					if(result){
						var temp = result.split("<br>");
						var rType = new Array();
						if(temp[0].indexOf("未检测到")<0){
							rType.push("ADV");
						}
						if(temp.length>1 && temp[1].indexOf("未检测到")<0){
							rType.push("TDF");
						}
						if(temp.length>2 && temp[2].indexOf("未检测到")<0){
							rType.push("LAM");
						}
						if(temp.length>3 && temp[3].indexOf("未检测到")<0){
							rType.push("LDT");
						}
						if(temp.length>4 && temp[4].indexOf("未检测到")<0){
							rType.push("FTC");
						}
						if(temp.length>5 && temp[5].indexOf("未检测到")<0){
							rType.push("ETV");
						}
					}
					
					var div1 = $("<div id='char1' class='col-lg-12' style='width: 1000px;height: 535px;margin-left:-15px;'></div>");
					$("#charDiv").append(div1);
					var one = getCountValue("Subtype","nomal");
					var X = "[";
					var Y = "[";
					var value = data.split("@")[0].split(";");
					var num = value.length;
					for(var k=0;k<value.length-1;k++){
						var n = value[k].split(",");
						X+="'"+n[0]+"',";
						var tag=n[0].split("_");
						if(tag.length==rType.length){
							var isOne = false;
							for(var i=0;i<tag.length;i++){
								if($.inArray(tag[i],rType)>=0){
									isOne=true;
								}else{
									isOne=false;
								}
							}
							if(isOne){
								Y+="{value : "+ n[1] +", itemStyle : {normal : {color : '#00cccc'}}},";
							}else{
								Y+="'" + n[1] + "',";
							}
						}else if(rType.length==0&&n[0]=="none"){
							Y+="{value : "+ n[1] +", itemStyle : {normal : {color : '#00cccc'}}},";
						}else{
							Y+="'" + n[1] + "',";
						}
					}
					X = X.substring(0,X.length-1)+"]";
					Y = Y.substring(0,Y.length-1)+"]";
					$.reportChar.draw.echartsShowBar("char1", "耐药类型", eval(X), eval(Y), -45, 800, 350);
				});
			}
			if(appId==90){
				$.get("count/tbCompare",{},function(data){
						var div = $("<div id='char0' class='col-lg-6' style='width: 1000px;height:400px;'></div>");
						$("#charDiv").append(div);
						var X = "[";
						var Y = "[";
						var value = data.split("\n");
						if(value.length > 1){
							for(var k=0;k<value.length-1;k++){
								var n = value[k].split("\t");
								X+="'"+n[0]+"',";
								Y+=n[1]+",";
							}
						}else{
							var n = data.split("\t");
							X+="'"+n[0]+"',";
							Y+=n[1]+",";
						}
						X = X.substring(0,X.length-1)+"]";
						Y = Y.substring(0,Y.length-1)+"]";
						$.reportChar.draw.echartsShowBar("char0", "位点", eval(X), eval(Y), 0, 500, 300);
				});
			}
			
			if(appId == 89){//kras
				var length = $("#seq_length").val();
				if(length==0 || isNaN(length)){
					$("#charDiv").html("<p style=\"color: red;\">数据异常，没有同比结果</p>");
				}else{  
					$.get("count/krasCompare",{"length":length},function(data){
							var div = $("<div id='char0' class='col-lg-6' style='width: 1000px;height:400px;'></div>");
							$("#charDiv").append(div);
							var X = "[";
							var Y = "[";
							var value = data.split("\n");
							if(value.length > 1){
								for(var k=0;k<value.length-1;k++){
									var n = value[k].split("\t");
									X+="'"+n[0]+"',";
									Y+=n[1]+",";
								}
							}else{
								var n = data.split("\t");
								X+="'"+n[0]+"',";
								Y+=n[1]+",";
							}
							X = X.substring(0,X.length-1)+"]";
							Y = Y.substring(0,Y.length-1)+"]";
							$.reportChar.draw.echartsShowBar("char0", "位点", eval(X), eval(Y), 0, 500, 300);
					});
				}
			}
			
			if(appId==84){//egfr
				var length = $("#seq_length").val();
				if(length==0 || isNaN(length)){
					$("#charDiv").html("<p style=\"color: red;\">数据异常，没有同比结果</p>");
				}else{  
					$.get("count/egfrCompare",{"length":length},function(data){
							var div = $("<div id='char0' class='col-lg-6' style='width: 1000px;height:400px;'></div>");
							$("#charDiv").append(div);
							var X = "[";
							var Y = "[";
							var value = data.split("\n");
							if(value.length > 1){
								for(var k=0;k<value.length-1;k++){
									var n = value[k].split("\t");
									X+="'"+n[0]+"',";
									Y+=n[1]+",";
								}
							}else{
								var n = data.split("\t");
								X+="'"+n[0]+"',";
								Y+=n[1]+",";
							}
							X = X.substring(0,X.length-1)+"]";
							Y = Y.substring(0,Y.length-1)+"]";
							$.reportChar.draw.echartsShowBar("char0", "位点", eval(X), eval(Y), 0, 500, 300);
					});
				}
			}
			spinner.stop();
		}
		
		function getCountValue(key,id){
			var num = 0;
			var val;
			$("#"+id).find("tr").each(function(i){
				if(i==0){
					$(this).find("th").each(function(j){
						var title = $(this).html();
						if(title==key){
							num = j;
						}
					});
				}else{
					$(this).find("td").each(function(k){
						if(num==k){
							val = $(this).html();
							if(val.indexOf(",") > 0){
								var reg=new RegExp(",","g"); //创建正则RegExp对象  
								val=val.replace(reg,"");  
							}
						}
					});
				}
			});
			return val;
		}
		
		/** 
		* 打开项目共享 （未共享）
		* proId：项目id
		* proName：项目名称
		* fileCount：项目下文件数量
		*/
		function toShareModal(proId,proName,fileCount){
			$("#shareProPrompt").html("");
			$("#proIdHidden").val(proId);
			$("#proNameSpan").html("项目名称：" + proName);
			$("#proDataCountSpan").html(fileCount);
			$("#proSel").select2({
				minimumInputLength: 1,
				query: function (query) {
					var data = {results: [{id: query.term, text: query.term}]};
					query.callback(data);
			 	},
				multiple: true
			});
			$("#proSel").val('');
			$("#shareProjectModal").modal("show");
		}
		
		/** 打开项目共享（已有共享）
		* proId：项目id
		* owner：所有者id
		* proName：项目名称
		* fileCount：项目下文件数量
		*/
		function shareModal(proId,owner,proName,fileCount){
			$("#proOwnerHidden").val(owner);
			$("#shareProPrompt").html("");
			$("#proIdHidden").val(proId);
			$("#proNameSpan").html("项目名称：" + proName);
			$("#proDataCountSpan").html(fileCount);
			$("#proSel").select2({
				minimumInputLength: 1,
				query: function (query) {
					var data = {results: [{id: query.term, text: query.term}]};
					query.callback(data);
			 	},
				multiple: true
			});
			$.get("project/getShareTo",{"projectId":proId},function(data){
				if(data.length>0){
					$("#proSel").select2("data",data);
				}
			});
			$("#proSel").val('');
			$("#shareProjectModal").modal("show");
		}
		
		//保存共享项目
		function saveShareProject(){
			var proId = $("#proIdHidden").val();
			var data = $("#proSel").select2("data");
			var empty = false;
			$.each(data,function(index,item){
				var id = $.trim(item.id);
				if(id==""){
					empty = true;
				}
			});
			if(empty){
				$("#shareProPrompt").html("用户名不能有空值！");
				return;
			}
			var userNames = "";
			$.each(data,function(id,value){
				userNames += value.text + ",";
			});
			//全部转化成小写再比较
            userNames = userNames.toLowerCase();
            sessionUserName = sessionUserName.toLowerCase();
			if(userNames.substring(0, userNames.length-1)==sessionUserName||userNames.indexOf(sessionUserName, 0)!=-1){
				$("#shareProPrompt").html("项目不能共享给自己！");
				return;
			}else{
				$.get("project/shareProject",{"projectId":proId,"userNames":userNames},function(data){
					if(data==""){
						$("#shareProjectModal").modal("hide");
						submitSearch();
						//发送邮件
//						$.get("projectJson_sendProShareEmail.action",{"userNames":userNames,"project.projectId":proId},function(result){
//							if(result==0){
//								jAlert("邮件发送失败！");
//							}
//						});
					}else{
						$("#shareProPrompt").html("<br/>" + data);
					}
				});
			}
		}
		function viewNext(){
		    var obj = $(this).parent().parent().next();
		    if(obj.attr("style")){
		         $("._HCVTR").css("display","none");
		         obj.css("display","");
		         if(!obj.children().html()){
		             obj.children().html("Low_Quality");
		         }
		    }else{
		        obj.css("display","none");
		    }
		}
		function toChangePname(id){
		    $("#showPname"+id).addClass("none");
		    $("#changePname"+id).removeClass("none");
		}
		function changePname(id){
		    var name = $("#updatePname"+id).val();
		    var oldName = $("#pnameSpan"+id).text().trim();
		    //名称修改才进行后台提交，未修改则不提交后台
		    if(name==oldName){
		        $("#showPname"+id).removeClass("none");
		        $("#changePname"+id).addClass("none");
		    }else{
		        $.get("project/update",{"projectId":id,"projectName":name},function(flag){
		            if(flag){
		            	if(name.length>13){
		            		name=name.substring(0,12)+"...";
		            	}
		                $("#pnameSpan"+id).html(name);
		                $("#showPname"+id).removeClass("none");
		                $("#changePname"+id).addClass("none");
		                if(dataReportParam.data.proId == id){
		                	dataReportParam.data.proName = name;
		                	$("#proforReport").html(name);
		                }
		            }
		        });
		    }
		}
		
var printReport = {
    main: function(method,param){
      $.get(method,param,function(responseText){
        var obj = window.open("");
        obj.document.write(responseText);
        obj.document.close();
      });
    },
    mongoParam: function(projectId,dataKey,appId){
      var _param = {"projectId":projectId,"dataKey":dataKey,"appId":appId};
      return _param;
    }
}
function printSplit(projectId,dataKey,appId){
  printReport.main("report/printSplitReport",printReport.mongoParam(projectId,dataKey,appId));
}
