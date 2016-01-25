	jQuery(function($) {
		loadBigUesr();
		var oTable1 = $('#bigUserTb').dataTable({
			"aoColumns" : [ {
				"bSortable" : false
			}, {
				"bSortable" : false
			}, null, { "sType": "filesize" } ],
			iDisplayLength : 10,
			"aaSorting" : [ [ 2, "desc" ] ]
		});
	});
	
	function loadBigUesr(){
		var bigUserDataURL = "home!getBigUserCountJson";
		  $.get(bigUserDataURL,{}, function(data) {
				var xAxis = new Array(data.length);
				var yAxis = new Array(data.length);
				var fileNumObj = new Array(data.length);
				var fileSizeObj = new Array(data.length);

				var yAxisSize= new Array(data.length);
				for (var i = 0; i < data.length; i++) {
					xAxis[i] = data[i].company_name;
					yAxis[i] = data[i].fileNum;
					yAxisSize[i] = data[i].size;
					fileNumObj[i] = {
							"name" : data[i].company_name,
							"value" : data[i].fileNum
						};
					fileSizeObj[i] = {
							"name" : data[i].company_name,
							"value" : parseFloat((data[i].size/(1024*1024*1024)).toFixed(2))
						};
				}
				var myChart = echarts.init(document.getElementById("fileNumPie"));
				var option = makePieOption('', xAxis, '大客户数据个数', '55%', '30%', '55%', fileNumObj,{'x':'center','y':'top','orient':'horizontal'});
				option = makePieOptionAdd(option,'大客户数据大小(GB)', '30%', '70%', '60%', fileSizeObj);
				console.log(option);
				myChart.setOption(option);
				
		  
			//	var mySizeChart = echarts.init(document.getElementById("fileSizePie"));
			//	var sizeOption = makePieOption('', xAxis, '大客户数据大小(GB)', '40%', '60%', '45%', fileSizeObj,null);
			//	mySizeChart.setOption(sizeOption);
		  });
	}
	
	function bigUesrMonthData(company_id, company_name) {
	    $("#cmpName").html(company_name);
	    $("#_cmpId").html(company_id);
	    $("#cmpName").css("display","inline");
	    var param = {
	            "companyId" : company_id
	        };
	        var bigUesrOneURL = "home!toBigUserOne";
	        $.get(bigUesrOneURL, param, function(data) {
	        	$("#dataDiv").html(data);
	        });
	}