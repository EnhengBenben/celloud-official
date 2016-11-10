(function() {
	celloudApp.controller("bsiReportController", function($scope, bsiReportService) {
		$scope.params = {
		    condition: null,
		    sort: 0,
		    sortBatch: "asc",
		    sortName: "asc",
		    sortPeriod: "asc",
		    sortDate: "desc",
		    pageSize: $("#page-size-sel").val(),
		    reportType: 0,  //0:患者报告  1：分析报告
		    batch: null,
		    period: null,
		    beginDate: null,
		    endDate: null,
		    distributed: null, //0:是   1： 否
		    sampleName: null
		},
		$scope.pageQuery = function(){
			bsiReportService.pageQuery($scope.params).
			success(function(dataMap){
				$scope.batchList = dataMap.batchList;
				$scope.pageList = dataMap.pageList;
				$scope.periodMap = dataMap.periodMap;
				$scope.nowDate = dataMap.nowDate;
			})
		}
		/**
		 * 点击单个标签时的搜索
		 */
		$scope.reportBatchSearch = function(batchId){
			if(!$("#batch-sl").hasClass("select-more")){
				$scope.params.batch = "'" + $("#" + batchId).next().find("span").text() + "'";
				$scope.params.page = 1;
				$scope.pageQuery();
				$("#selected-batch span").html($("#" + batchId).next().find("span").text());
				$("#selected-batch").removeClass("hide");
				$("#to-sl-batch").addClass("hide");
			}
		}
		/**
		 * 取消单个标签时的搜索
		 */
		$scope.clearSlBatch = function(){
			$("#selected-batch").addClass("hide");
	        $("#to-sl-batch").removeClass("hide");
	        $("#batch-more").removeClass("disabled");
	        $("#batch-more").attr("disabled",false);
	        $("#batch-lists").find(".multisl-btns").addClass("hide");
	        $scope.params.batch = null;
	        $scope.params.page = 1;
	        $scope.pageQuery();
		}
		/**
		 * 更多, 收起
		 */
		$scope.batchMore = function(){
			if($("#batch-lists").hasClass("show-more")){
				$("#batch-lists").removeClass("show-more");
				$("#batch-more span").html("更多");
	            $("#batch-more i").removeClass("fa-chevron-up").addClass("fa-chevron-down");
			}else{
	            $("#batch-lists").addClass("show-more");
	            $("#batch-more span").html("收起");
	            $("#batch-more i").removeClass("fa-chevron-down").addClass("fa-chevron-up");
			}
		}
		/**
		 * 标签(批次)多选按钮
		 */
		$scope.batchMultiselect = function(){
			$(".selector-line").removeClass("select-more");
	        $("#report-multibatch-search").addClass("disabled");
	        $("#report-multibatch-search").attr("disabled",true);
	        $(".selector-line .checkbox").removeClass("checkbox-ed").addClass("checkbox-un").addClass("hide");
	        $(".selector-line").find("[name='sl-confirm']").addClass("disabled");
	        $(".selector-line").find("[name='sl-confirm']").attr("disabled",true);
	        $(".selector-line").find(".multisl-btns").addClass("hide");
	        var selectorline = $("#batch-sl");
	        selectorline.addClass("select-more");
	        selectorline.find(".sl-val").addClass("show-more");
	        selectorline.find(".checkbox").removeClass("hide");
	        selectorline.find(".multisl-btns").removeClass("hide");
	        
	        $("#batch-lists").addClass("show-more");
            $("#batch-more span").html("收起");
            $("#batch-more i").removeClass("fa-chevron-down").addClass("fa-chevron-up");
            $("#batch-lists .checkbox").removeClass("checkbox-ed").addClass("checkbox-un");
            $("#report-multibatch-search").addClass("disabled");
            $("#report-multibatch-search").attr("disabled",true);
            $("#batch-more").addClass("disabled");
            $("#batch-more").attr("disabled",true);
		}
		/**
		 * 标签(批次)多选框按钮
		 */
		$scope.batchLists = function(batchId){
			$("#" + batchId).toggleClass("checkbox-un");
	        $("#" + batchId).toggleClass("checkbox-ed");
	        if($("#batch-lists .checkbox-ed").size() > 0){
	            $("#report-multibatch-search").removeClass("disabled");
	            $("#report-multibatch-search").attr("disabled",false);
	        }else{
	            $("#report-multibatch-search").addClass("disabled");
	            $("#report-multibatch-search").attr("disabled",true);
	        }
		}
		/**
		 * 确定标签(批次)多选框按钮
		 */
		$scope.reportMultibatchSearch = function(){
			var show_val = [];
	        $("#batch-lists .checkbox-ed").each(function(){
	            $scope.params.batch == null? $scope.params.batch = "'"+$(this).next().text()+"'" : $scope.params.batch += ",'"+$(this).next().text() + "'";
	            show_val.push($(this).next().text());
	        });
	        $scope.pageQuery();
	        $("#selected-batch span").html(show_val.toString());
	        $("#selected-batch").removeClass("hide");
	        $("#to-sl-batch").addClass("hide");
	        $("#batch-sl").removeClass("select-more");
	        $("#batch-lists").removeClass("show-more");
	        $("#batch-lists").find(".checkbox").addClass("hide");
	        $("#batch-more span").html("更多");
	        $("#batch-more i").removeClass("fa-chevron-up").addClass("fa-chevron-down");
		}
		/**
		 * 取消标签(批次)多选
		 */
		$scope.resetBatchMultiSelect = function(){
			var selectorline = $("#batch-sl");
			selectorline.removeClass("select-more");
			selectorline.find(".sl-val").removeClass("show-more");
			selectorline.find(".checkbox").addClass("hide").addClass("checkbox-un").removeClass("checkbox-ed");
			selectorline.find(".multisl-btns").addClass("hide");
			if($("#batch-more").hasClass("disabled")){
				$("#batch-more").removeClass("disabled");
				$("#batch-more").attr("disabled",false);
				$("#batch-more span").html("更多");
				$("#batch-more i").removeClass("fa-chevron-up").addClass("fa-chevron-down");
			}
		}
		/**
		 * 点击单个状态搜索
		 */
		$scope.reportPeriodSearch = function(periodId){
			if(!$("#period-sl").hasClass("select-more")){
				$scope.params.period = $("#" + periodId).next().find("input").val();
				$scope.params.page = 1;
				$scope.pageQuery();
				$("#selected-period span").html($("#" + periodId).next().find("span").text());
				$("#selected-period").removeClass("hide");
				$("#to-sl-period").addClass("hide");
			}
		}
		/**
		 * 取消单个状态搜索
		 */
		$scope.clearSlPeriod = function(){
			$("#selected-period").addClass("hide");
			$("#to-sl-period").removeClass("hide");
			$scope.params.period = null;
			$scope.pageQuery();
		}
		/**
		 * 状态多选按钮
		 */
		$scope.periodMultiselect = function(){
			$(".selector-line").removeClass("select-more");
	        $("#report-multibatch-search").addClass("disabled");
	        $("#report-multibatch-search").attr("disabled",true);
	        $(".selector-line .checkbox").removeClass("checkbox-ed").addClass("checkbox-un").addClass("hide");
	        $(".selector-line").find("[name='sl-confirm']").addClass("disabled");
	        $(".selector-line").find("[name='sl-confirm']").attr("disabled",true);
	        $(".selector-line").find(".multisl-btns").addClass("hide");
	        var selectorline = $("#period-sl");
	        selectorline.addClass("select-more");
	        selectorline.find(".sl-val").addClass("show-more");
	        selectorline.find(".checkbox").removeClass("hide");
	        selectorline.find(".multisl-btns").removeClass("hide");
		}
		/**
		 * 取消状态多选
		 */
		$scope.resetPeriodMultiSelect = function(){
			var selectorline = $("#period-sl");
	        selectorline.removeClass("select-more");
	        selectorline.find(".sl-val").removeClass("show-more");
	        selectorline.find(".checkbox").addClass("hide").addClass("checkbox-un").removeClass("checkbox-ed");
	        selectorline.find(".multisl-btns").addClass("hide");
	        if($("#batch-more").hasClass("disabled")){
	        	$("#batch-more").removeClass("disabled");
	        	$("#batch-more").attr("disabled",false);
	        	$("#batch-more span").html("更多");
	        	$("#batch-more i").removeClass("fa-chevron-up").addClass("fa-chevron-down");
	        }
		}
		/**
		 * 按照日期查询
		 */
		$scope.dateQuery = function(){
          $scope.params.beginDate = $("#report-begindate-search").val();
          $scope.params.endDate = $("#report-enddate-search").val();
          $scope.pageQuery();
        }
		/**
		 * 是否分发
		 */
		$scope.distributeQuery = function(){
			var distribute = $("#distribute");
			if(distribute.find(".sl-judge-no").hasClass("hide")){
				distribute.find(".sl-judge-no").removeClass("hide");
				distribute.find(".sl-judge-yes").addClass("hide");
	            $scope.params.distributed = 1;
	        }else{
	        	distribute.find(".sl-judge-yes").removeClass("hide");
	            distribute.find(".sl-judge-no").addClass("hide");
	            $scope.params.distributed = 0;
	        }
	        $scope.pageQuery();
		}
//		$("#condition-find").unbind("click");
//        $("#condition-find").on("click",function(){
//          $.report.options.condition = $("#condition-input").val();
//          $.report.find.condition();
//        });
		
//        $("body").on("click","[data-click='report-check-all']",function(){
//          if($(this).hasClass("checkbox-ed")){
//            $(this).removeClass("checkbox-ed").addClass("checkbox-un");
//            $(".table>tbody .checkbox,.pagination .checkbox").removeClass("checkbox-ed").addClass("checkbox-un");
//          }else{
//            $(this).removeClass("checkbox-un").addClass("checkbox-ed");
//            $(".table>tbody .checkbox,.pagination .checkbox").addClass("checkbox-ed").removeClass("checkbox-un");
//          }
//        });
//        $("#report-multiperiod-search").on("click",function(){
//          var show_val = [];
//          $("#period-lists .checkbox-ed").each(function(){
//            $.report.options.period == null? $.report.options.period = $(this).next().find("input[type='hidden']").val() : $.report.options.period += ","+$(this).next().find("input[type='hidden']").val();
//            show_val.push($(this).next().find("span").html());
//          });
//          $.report.find.condition();
//          $("#selected-period span").html(show_val.toString());
//          $("#selected-period").removeClass("hide");
//          $("#to-sl-period").addClass("hide");
//          $("#period-sl").removeClass("select-more");
//          $("#period-sl").find(".checkbox").addClass("hide");
//          $("#period-sl").find(".multisl-btns").addClass("hide");
//          $("#period-lists .checkbox").removeClass("checkbox-ed").addClass("checkbox-un");
//        });
//		    all: function(){
//		      $.get("report/bsi/reportList",function(response){
//		        $.report.loadlist(response);
//		        $.report.options = {
//		            condition: null,
//		            sort: 0,
//		            sortBatch: "asc",
//		            sortName: "asc",
//		            sortPeriod: "asc",
//		            sortDate: "desc",
//		            pageSize: $("#page-size-sel").val(),
//		            reportType: 0,  //0:患者报告  1：分析报告
//		            batch: null,
//		            period: null,
//		            beginDate: null,
//		            endDate: null,
//		            distributed: null, //是否分发  0:是   1： 否 
//		            sampleName: null
//		        };
//		      });
//		    },
//		    condition: function(){
//		      var options = $.report.options;
//		      $.get("report/bsi/searchReportList",{"sampleName":options.sampleName,"batch":options.batch,"period":options.period,"beginDate":options.beginDate,"endDate":options.endDate,"condition":options.condition,"sort":options.sort,"sortDate":options.sortDate,"sortPeriod":options.sortPeriod,"sortBatch":options.sortBatch,"sortName":options.sortName,"size":options.pageSize},function(response){
//		        $.report.loadlist(response);
//		      });
//		    },
//		    pagination: function(currentPage){
//		      var options = $.report.options;
//		      $.get("report/bsi/searchReportList",{"sampleName":options.sampleName,"batch":options.batch,"period":options.period,"beginDate":options.beginDate,"endDate":options.endDate,"page":currentPage,"condition":options.condition,"sort":options.sort,"sortDate":options.sortDate,"sortPeriod":options.sortPeriod,"sortBatch":options.sortBatch,"sortName":options.sortName,"size":options.pageSize},function(response){
//		        $.report.loadlist(response);
//		      });
//		    }
//		  },
//		  loadlist: function(response){
//		    $("#report-list").html(response);
//		    $("#sample-selector").val($.report.options.sampleName);
//		    $("#sample-selector").on("keyup",function(e){
//		      e = e || window.event;
//		      if (e.keyCode == "13") {//keyCode=13是回车键
//		        $.report.options.sampleName = $("#sample-selector").val();
//		        $.report.find.condition();
//		      }
//		    });
//		    $("#data-list-tbody").find("td[name='data-name-td']").each(function(){
//		      var _data = $(this).attr("title");
//		      if(_data.length>40){
//		        var newData = utils.splitDataByInfo(_data, "\r\n" ,40);
//		        $(this).attr("title",newData);
//		      }
//		    });
//		    $.base.sortIcon($.report.options.sortDate,$.report.options.sortBatch,$.report.options.sortName,$.report.options.sortPeriod);
//		    $("#pagination-task").on("click","a",function(e){
//		      var id = $(this).attr("id");
//		      var currentPage = parseInt($("#current-page-hide").val());
//		      var totalPage = parseInt($("#total-page-hide").val());
//		      var page;
//		      if(id == undefined){
//		        page = $(this).html();
//		      }else if(id.indexOf("prev")>=0){
//		        if(currentPage == 1){
//		          page = 1;
//		        }else{
//		          page = currentPage-1;
//		        }
//		      }else if(id.indexOf("next")>=0){
//		        page = currentPage+1;
//		        if(currentPage == totalPage){
//		          page = currentPage;
//		        }else{
//		          page = currentPage+1;
//		        }
//		      }else if(id.indexOf("first")>=0){
//		        page = 1;
//		      }else if(id.indexOf("last")>=0){
//		        page = totalPage;
//		      }
//		      $.report.find.pagination(page);
//		    });
//		    $("#sort-date").on("click",function(e){
//		      $.report.options.sort = 0;
//		      $.report.options.sortDate = $.report.options.sortDate=="desc"?"asc":"desc";
//		      $.report.find.condition();
//		    });
//		    $("#sort-batch").on("click",function(e){
//		      $.report.options.sort = 1;
//		      $.report.options.sortBatch = $.report.options.sortBatch=="desc"?"asc":"desc";
//		      $.report.find.condition();
//		    });
//		    $("#sort-name").on("click",function(e){
//		      $.report.options.sort = 2;
//		      $.report.options.sortName = $.report.options.sortName=="desc"?"asc":"desc";
//		      $.report.find.condition();
//		    });
//		    $("#sort-period").on("click",function(e){
//		      $.report.options.sort = 3;
//		      $.report.options.sortPeriod = $.report.options.sortPeriod=="desc"?"asc":"desc";
//		      $.report.find.condition();
//		    });
//		    $("#page-size-sel").on("change",function(e){
//		      $.report.options.pageSize = $("#page-size-sel").val()
//		      $.report.find.condition();
//		    });
//		  },
//		  reRun: function(dataKey,appId,projectId){
//		    $.get("data/reRun",{"dataKey":dataKey,"appId":appId,"projectId":projectId},function(result){
//		      $.report.find.condition();
//		    });
//		  },
//		  detail: {
//		    option: {
//		      batchPage: 1
//		    },
//		    patient: function(dataKey,projectId,appId,reportIndex,currentPage){
//		      var options = $.report.options;
//		      $.post("report/getBSIPatientReport",{"sampleName":options.sampleName,"reportIndex":reportIndex,"dataKey":dataKey,"projectId":projectId,"appId":appId,"page":currentPage,"condition":options.condition,"sort":options.sort,"sortDate":options.sortDate,"sortPeriod":options.sortPeriod,"sortBatch":options.sortBatch,"sortName":options.sortName,"size":options.pageSize},function(response){
//		        $("#container").html(response);
//		      });
//		    },
//		    analy: function(dataKey,projectId,appId){
//		      $.get("report/getBSIAnalyReport",{"dataKey":dataKey,"projectId":projectId,"appId":appId},function(response){
//		        $("#myTabContent").html(response);
//		      });
//		    },
//		    prev: function(currentPage){
//		      if(currentPage > 1){
//		        var options = $.report.options;
//		        $.post("report/getPrevOrNextBSIReport",{"sampleName":options.sampleName,"batch":options.batch,"period":options.period,"beginDate":options.beginDate,"endDate":options.endDate,"isPrev":true,"page":currentPage-1,"condition":options.condition,"sort":options.sort,"sortDate":options.sortDate,"sortPeriod":options.sortPeriod,"sortBatch":options.sortBatch,"sortName":options.sortName},function(response){
//		          if(response != null &&response !=""){
//		            $("#container").html(response);
//		          }
//		        });
//		      }
//		    },
//		    next: function(currentPage){
//		      var totalPage = $("#total-page-hide").val();
//		      currentPage = parseInt(currentPage);
//		      if(currentPage < totalPage){
//		        var options = $.report.options;
//		        $.post("report/getPrevOrNextBSIReport",{"sampleName":options.sampleName,"batch":options.batch,"period":options.period,"beginDate":options.beginDate,"endDate":options.endDate,"isPrev":false,"page":currentPage+1,"totalPage":totalPage,"condition":options.condition,"sort":options.sort,"sortDate":options.sortDate,"sortPeriod":options.sortPeriod,"sortBatch":options.sortBatch,"sortName":options.sortName},function(response){
//		          if(response != null &&response !=""){
//		            $("#container").html(response);
//		          }
//		        });
//		      }
//		    }
//		  },
//		  period: {
//		      error: function(dataName){
//		        $("#run-error-data").html(dataName);
//		        $("#running-error-modal").modal("show");
//		      }
//		  }
		$scope.pageQuery();
	});
	celloudApp.controller("bsiDataController", function($scope) {
		
	});
})();
