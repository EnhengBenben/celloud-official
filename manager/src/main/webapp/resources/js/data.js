$(function(){
	var oTable1 = $('#MonthDataList').DataTable({
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
	        { type: 'file-size', targets: 2 }
	    ],
	    sorting : [[0, "desc"]]
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