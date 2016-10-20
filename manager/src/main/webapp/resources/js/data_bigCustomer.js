(function(dataFile){
	dataFile.bigCustomerMonthData=function(company_id, company_name) {
	    $("#cmpName").html(company_name);
	    $("#_cmpId").html(company_id);
	    $("#cmpName").css("display","inline");
        $.get("data/dataMon/"+company_id, function(data) {
        	$("#dataDiv").html(data);
        	$.post("data/dataMon/"+company_id, function(data) {
        		drawCharts.lineChart("fileNum",data, "数据个数","time","dataNum");
        	});
        });
	}
})(dataFile);
$(function(){
	 $('#bigUserTb').dataTable({
		 language: {
		        processing: "正在加载数据...",
		        search: "搜索",
		        lengthMenu: "每页显示 _MENU_ 条记录",
		        info: "显示第  _START_ 条到第  _END_ 条记录,共  _TOTAL_ 条记录",
		        infoEmpty: "没有数据",
		        zeroRecords: "Nothing found - 没有记录",
		        paginate: {
		            first: "首页",
		            previous: "上一页",
		            next: "下一页",
		            last: "尾页"
		        }
		    },
		    columnDefs: [
		        { type: 'file-size', targets: 3 }
		    ],
		    sorting : [[0, "desc"]]
		});
	$.post("data/dataBigCustomer",function(data){
		var xAxis = new Array(data.length);
		var yAxis = new Array(data.length);
		var fileNumObj = new Array(data.length);
		var fileSizeObj = new Array(data.length);

		var yAxisSize= new Array(data.length);
		for (var i = 0; i < data.length; i++) {
			xAxis[i] = data[i].companyName;
			yAxis[i] = data[i].fileNum;
			yAxisSize[i] = data[i].size;
			fileNumObj[i] = {
					"name" : data[i].companyName,
					"value" : data[i].dataNum
				};
			fileSizeObj[i] = {
					"name" : data[i].companyName,
					"value" : parseFloat((data[i].dataSize/(1024*1024*1024)).toFixed(2))
				};
		}
		var myChart = echarts.init(document.getElementById("fileNumPie"));
		var option = makePieOption('', xAxis, '大客户数据个数', '60%', '30%', '55%', fileNumObj,{'x':'center','y':'top','orient':'horizontal'});
		option = makePieOptionAdd(option,'大客户数据大小(GB)', '60%', '70%', '60%', fileSizeObj);
		myChart.setOption(option);
	});
});