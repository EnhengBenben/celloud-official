$(function(){
	 var oTable1 = $('#allUserDataList').dataTable({
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
	            "aoColumns" : [  {
	    			"bSortable" : false
	    		}, {
	    			"bSortable" : false
	    		}, null, { "sType": "filesize" } ],
	    		iDisplayLength : 10,
	    		"aaSorting":[[2,"desc"]]
     });
	$.post("data/dataUser",function(data){
		if(data.length>0){
			drawCharts.barChart("userFileNum",data,'数据个数','username','dataNum',themes.green,20);
		}else{
			$("#userFileNum").parent().parent().hide();
		}
	});
});