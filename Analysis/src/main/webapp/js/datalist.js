//URL 
var UserDataURL = "data!getUserDataJson";
var UserFileSizeId = "userFileSize";
var UserFileNumId = "userFileNum";

$.get(UserDataURL, {'orderType':"fileNum"}, function(data) {
	data = data==null?[]:data;
	console.log(data);
	var xAxis = new Array(data.length);
	var yAxis = new Array(data.length);
	for (var i = 0; i < data.length; i++) {
		xAxis[i] = data[i].username;
		yAxis[i] = data[i].fileNum;
	}
	var option = makeOptionScrollUnit( xAxis, yAxis, '数据量', barType,0,20);
	var myChart = echarts.init(document.getElementById(UserFileNumId));
	myChart.setOption(option);
});
/*
$.get(UserDataURL, {'orderType':"size"}, function(data) {
	data = data==null?[]:data;
	var xAxis = new Array(data.length);
	var yAxis = new Array(data.length);
	for (var i = 0; i < data.length; i++) {
		xAxis[i] = data[i].username;
		yAxis[i] = parseFloat( (data[i].size / (1024 * 1024 * 1024) ).toFixed(2));
	}
	      
	var option = makeOptionScrollUnit( xAxis, yAxis, '数据大小(GB)', barType,0,10);
	option. yAxis = [
	    	          {
	    	              type : 'value',
	    	              axisLabel:{formatter:'{value} GB'}
	    	          }
	    	      ];
	var myChart = echarts.init(document.getElementById(UserFileSizeId));
	myChart.setOption(option);
});
*/
jQuery(function($) {
	var oTable1 = $('#allUserDataList').dataTable({
		"aoColumns" : [  {
			"bSortable" : false
		}, {
			"bSortable" : false
		}, null, { "sType": "filesize" } ],
		iDisplayLength : 10,
		"aaSorting":[[2,"desc"]],
	});
	
	$('table th input:checkbox').on('click', function() {
		var that = this;
		$(this).closest('table').find('tr > td:first-child input:checkbox').each(function() {
			this.checked = that.checked;
			$(this).closest('tr').toggleClass('selected');
		});
		
	});
	
	$('[data-rel="tooltip"]').tooltip({
		placement : tooltip_placement
	});
	function tooltip_placement(context, source) {
		var $source = $(source);
		var $parent = $source.closest('table')
		var off1 = $parent.offset();
		var w1 = $parent.width();
		var off2 = $source.offset();
		var w2 = $source.width();
		if (parseInt(off2.left) < parseInt(off1.left) + parseInt(w1 / 2))
			return 'right';
		return 'left';
	}
})
function userMonthDataList(userId, userName, company) {
	$("#_username").html(userName);
	$("#_companyName").html(company);
	$("#secondTitle").removeClass("hide");
	$("#_companyName").removeClass("hide");
	$("#hideUserId").val(userId);
	getUserMonthData(userId);
}
function getUserMonthData(userId) {
	$("#thirdTitle").addClass("hide");
	var userId = $("#hideUserId").val();
	$.get("data!getUserMonthData", {
		"userId" : userId
	}, function(responseText) {
		$("#dataDiv").html(responseText);
	})
}
