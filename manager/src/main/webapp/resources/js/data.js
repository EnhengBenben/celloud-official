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
		if(data.dataMon.length>0){
			drawCharts.incrementCumulantLineChart('fileNumView','fileTotalNum',data.dataMon,'time','dataNum','数据增量曲线图','数据个数累计图','hide','hide',themes.green);
		}else{
			$("#fileNumView").parent().parent().hide();
		}
	});
});