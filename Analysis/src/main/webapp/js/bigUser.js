	jQuery(function($) {
		var oTable1 = $('#bigUserTb').dataTable({
			"aoColumns" : [ {
				"bSortable" : false
			}, {
				"bSortable" : false
			}, null, null ],
			iDisplayLength : 10,
			"aaSorting" : [ [ 2, "desc" ] ]
		});
	});
	function bigUesrMonthData(company_id, company_name) {
	    $("#_oneApp").html(company_name);
	    var param = {
	            "companyId" : company_id
	        };
	        var type = 'line';
	        var optNumName = "'数据大小'";
	        var optSizeName = '数据量';
	        var bigUesrOneURL = "home!toBigUserOne";

	        $.get(bigUesrOneURL, param, function(data) {
	        	console.log(data);
	        	$("#dataDiv").html(data);
	        });
	}