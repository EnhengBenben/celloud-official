$(function(){
	 var oTable1 = $('#allUserDataList').DataTable({
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
	$.post("data/dataUser",function(data){
		drawCharts.barChart("userFileNum",data,'数据个数','username','dataNum',themes.green,20);
	});
});