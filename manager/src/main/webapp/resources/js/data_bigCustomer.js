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
		 "oLanguage": {
	            "sSearch": "搜索",
	            "sLengthMenu": "每页显示 _MENU_ 条记录",
	            "sZeroRecords": "Nothing found - 没有记录",
	            "sInfo": "显示第  _START_ 条到第  _END_ 条记录,共  _TOTAL_ 条记录",
	           	"sInfoEmpty": "没有数据",   
	            "sProcessing": "正在加载数据...",
	            "oPaginate": {
	            	"sFirst": "首页",
	            	"sPrevious": "上一页",
	            	"sNext": "下一页",
	            	"sLast": "尾页"
	            	},
	            },
			"aoColumns" : [ {
				"bSortable" : false
			}, {
				"bSortable" : false
			}, null, { "sType": "filesize" } ],
			iDisplayLength : 10,
			"aaSorting" : [ [ 2, "desc" ] ]
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