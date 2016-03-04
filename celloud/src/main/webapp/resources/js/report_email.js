$.ajaxSetup ({
	cache: false //关闭AJAX相应的缓存
});
function showReport(){
	var proId = $("#proIdHidden").val();
	var report = $("#reportHidden").val();
	if(proId==null||proId==""||proId=="null"){//数据报告
		$("#tab2").css({
		 	display:"block"
		});
		$("#tab1").css({
		 	display:"none"
		});
		$("#liData").addClass("active");
		$("#liPro").removeClass("active");
		$("#liPro").attr("style","display:none;");
		$("#tab2").html(report);
	}else{//项目报告
		$("#tab1").css({
		 	display:"block"
		});
		$("#tab2").css({
		 	display:"none"
		});
		$("#liPro").addClass("active");
		$("#liData").removeClass("active");
		$("#liData").attr("style","display:none;");
		$("#tab1").html(report);
	}
}
//查看项目报告
function showProReport(){
	$("#tab1").css({
	 	display:"block"
	});
	$("#tab2").css({
	 	display:"none"
	});
	$("#liPro").addClass("active");
	$("#liData").removeClass("active");
}
//查看数据报告
function showDataReport(){
	$("#tab2").css({
	 	display:"block"
	});
	$("#tab1").css({
	 	display:"none"
	});
	$("#liData").addClass("active");
	$("#liPro").removeClass("active");
}
//在项目中查看数据报告
function viewDataReport(userId,dataKey,softwareName,softwareId){
	$("#liData").attr("style","");
	showDataReport();
	$.get("getPath.action",{},function(responseText){
		var toolsPath = responseText.split(",")[0];
		$.get("getFileNameByDataKey.action",{"dataKey":dataKey},function(rFileName){
			var newPath = toolsPath + "Procedure!readReport" + "?fileName="+rFileName+"&userId=" + userId + "&appId=" + softwareId + "&dataKey=" + dataKey + "&projectId=";
			$.get("getDataReport.action",{"url":newPath},function(responseText){
				$("#tab2").html(responseText);
				//动态调整div的大小
				var height = $("#tab2").children().height();
				if(height>380){
					$(".tab-content").css("height",height+20);
					$("#tab2").children().resize(function(){
						var height = $("#tab2").children().height();
						$(".tab-content").css("height",height+20);
					});
				}
				if(softwareId==10||softwareId==76){
					showAmcharts();
				}
			});
		});
	});
}

function showAmcharts(){
	var responseText = $("#tableValue").html();
 	var data = responseText.split("@");
 	for ( var i = 0; i < data.length-1; i++) {
 		var dataList = eval("("+data[i]+")");
 		getGcCharts(dataList.str,"amcharts"+i);
 	}
 	var dataList = eval("("+data[data.length-1]+")");
 	getGcCharts(dataList.str,"amcharts");
}

function getGcCharts(data,divId){
    var gcChart = new AmCharts.AmSerialChart();
    gcChart.dataProvider = data;
    gcChart.categoryField = "Length";
    gcChart.marginRight = 0;
    gcChart.marginTop = 0;    
    gcChart.autoMarginOffset = 0;
    gcChart.columnWith = 10;
    gcChart.depth3D = 7;
    gcChart.angle = 30;
    gcChart.columnWidth = 0.4;
    gcChart.columnSpacing = 5;
    //设置横坐标
    var categoryAxis = gcChart.categoryAxis;
    categoryAxis.labelRotation = 0;
    categoryAxis.dashLength = 1;
    categoryAxis.gridPosition = "start";

    var valueAxis = new AmCharts.ValueAxis();
    valueAxis.dashLength = 5;
    gcChart.addValueAxis(valueAxis);

    var graph = new AmCharts.AmGraph();
    graph.valueField = "Distinct";
    graph.balloonText = "Distinct: [[value]]";
    graph.type = "column";
    graph.lineAlpha = 0;
    graph.fillAlphas = 1;
    graph.lineColor = ['#4682B4', '#4682B4'];
    gcChart.addGraph(graph);
    var graph2 = new AmCharts.AmGraph();
    graph2.valueField = "Total";
    graph2.balloonText = "Total: [[value]]";
    graph2.type = "column";
    graph2.lineAlpha = 0;
    graph2.fillAlphas = 1;
    graph2.lineColor = ['#CD3333', '#CD3333'];
    gcChart.addGraph(graph2);
    gcChart.write(divId);
}