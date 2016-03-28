$(function(){
	 $('#hospitalList').dataTable({
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
			}, {
				"bSortable" : false
			}, null, null, {
				"sType" : "filesize"
			} ],//列支持排序方式
			"aaSorting" : [ [ 4, "desc" ] ],//默认列排序
			iDisplayLength : 10
		});
	$.post("data/dataCompany",function(data){
		drawCharts.barChart("fileNumView",data,'数据个数','companyName','dataNum',null,20);
	});
});