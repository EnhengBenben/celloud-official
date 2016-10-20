$(function(){
	$("#historyWeekData").DataTable({
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
		    { type: 'file-size', targets: 5 }
	    ],
	    "order": [[ 0, "desc" ]]
	});
	$.post("data/topUserLogin",function(data){
		drawCharts.barChart("topUserLogin",data,'前10用户登录次数','username','log_count',themes.green,20);
	});
	$.post("data/topAppRun",function(data){
		drawCharts.barChart("topAppRun",data,'前10App运行次数','app_name','app_count',themes.green,20);
	});
	$.post("data/topDataSize",function(data){
		drawCharts.barChart("topDataSize",data,'前10用户数据大小','username','size_sum',themes.green,20);
	});
	
	$.post("data/historyUserLogin",function(data){
		drawCharts.barChart("historyUserLogin",data,'历史周登录次数','start_date','log_count',themes.green,20);
	});
	$.post("data/historyUserActive",function(data){
		drawCharts.barChart("historyUserActive",data,'历史周活跃用户','start_date','active_user',themes.green,20);
	});
	$.post("data/historyAppRun",function(data){
		drawCharts.barChart("historyAppRun",data,'历史周App运行次数','start_date','run_app',themes.green,20);
	});
	$.post("data/historyAppActive",function(data){
		drawCharts.barChart("historyAppActive",data,'历史周活跃App','start_date','active_app',themes.green,20);
	});
	$.post("data/historyDataSize",function(data){
		drawCharts.barChart("historyDataSize",data,'历史周数据大小','start_date','size_sum',themes.green,20);
	});
});