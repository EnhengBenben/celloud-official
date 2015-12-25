







function bigUesrMonthData(company_id,company_name) {
	getBIgUserDataById(company_id);
}
function getBIgUserDataById(company_id){
	var fileSizeId = "fileSize";
	var fileNumId = "fileNum";
	var bigUesrURL = "data!getBigUserMonth";
	var bigUesrTableURL = "data!getBigUserMonthTable";
	var param = {"companyId":company_id};
	var type =  'line';
	var optNumName = "'数据大小'";
	var optSizeName =  '数据量';
	
	$.get(bigUesrURL,param,function(data) {
			console.log(data);
			data = data==null?[]:data;
			var xAxis = new Array(data.length);
			var yAxis = new Array(data.length);
			var fileNum = new Array(data.length);
			for (var i = 0; i < data.length; i++) {
				xAxis[i] = data[i].yearMonth;
				fileNum[i] = data[i].fileNum;
				yAxis[i] = parseFloat(data[i].size / (1024 * 1024 * 1024)).toFixed(2);
			}
			var fileSizeOpt = makeOptionScroll('', xAxis, yAxis, optNumName,lineType,0,30);
			var fileNumOpt = makeOptionScroll('', xAxis, fileNum,optSizeName, barType,0,30);
			
			var sizeChart = echarts.init(document.getElementById(fileSizeId));
			var numChart= echarts.init(document.getElementById(fileNumId));

			sizeChart.setOption(fileSizeOpt);
			numChart.setOption(fileNumOpt);
	});
	
	$.get(bigUesrTableURL,param,function(responseText) {
		$("#userDataList").html(responseText);
	});
	
	
	
	
}