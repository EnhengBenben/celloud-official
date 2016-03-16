(function(dataFile){
	dataFile.drawChart=function(data){
		var xAxis = new Array(data.length);
		var yAxis = new Array(data.length);
		var yAxisCount = new Array(data.length);
		
		for (var i = 0; i < data.length; i++) {
			xAxis[i] = data[i]['time'];
			yAxis[i] = data[i]['dataNum'];
		}
		yAxisCount[0] = yAxis[0];
		for (var i = 1; i < yAxis.length; i++) {
			yAxisCount[i] = yAxisCount[i - 1] + yAxis[i];
		}
		
		var fileNumOpt = makeOptionScrollUnit(xAxis, yAxis, '数据增量曲线图', 'line', 100, xAxis.length,null,null,'hide');
		var fileTotalOpt = makeOptionScrollUnit(xAxis, yAxisCount, '数据个数累计图', 'line', 100, xAxis.length,null,null,'hide');
		
		fileNumOpt.legend.data[fileNumOpt.legend.data.length]="数据个数累计图";
		fileTotalOpt.series[0].itemStyle.normal.color=themes.green.color[1];
		
		fileTotalOpt.grid={x:80,y:0,x2:80,y2:100};
		var fileNumChart = echarts.init(document.getElementById('fileNumView'),themes.green);
		var fileTotalNum = echarts.init(document.getElementById('fileTotalNum'),themes.green);
		fileTotalOpt.legend.y=-30;
		fileTotalNum.setOption(fileTotalOpt);
		fileNumChart.setOption(fileNumOpt);
		fileNumChart.connect([fileTotalNum ]);
		fileTotalNum.connect([fileNumChart ]);
	}
})(dataFile)
$(function(){
	 var oTable1 = $('#MonthDataList').dataTable({
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
         "aoColumns" : [ null, null, {
             "sType" : "filesize"
         } ],
         iDisplayLength : 10,
         "aaSorting" : [ [ 0, "desc" ] ],
     });
	$.post("data/dataMon",function(data){
		if(data.adminData!=null){
			drawCharts.manyLineChart("bigUserFileNumView",data.adminData,'seriesName','dataMon','time','dataNum');
		}
		dataFile.drawChart(data.dataMon);
	});
});