$(function(){
	 $('#newCompanyList').dataTable({"oLanguage": {
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
			"aoColumns" : [ null, null ],
			iDisplayLength : 10,
			"aaSorting" : [ [ 0, "desc" ] ],
		});
	$.post("company/guideData",function(data){
		drawCharts.mapchars(data.provinceData,'map','医院');
		if(data.adminData!=null){
			drawCharts.manyLineChart("newCmpBigUser",data.adminData,'seriesName','userMon','time','userNum');
		}
		drawCharts.incrementCumulantLineChart('main','newCompany',data.companyData,'time','companyNum','月新增医院数量','新增医院累积曲线图','hide','阴影',themes.macarons);
	});
});