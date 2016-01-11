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
	    $("#cmpName").html(company_name);
	    $("#_cmpId").html(company_id);
	    $("#cmpName").css("display","inline");
	    var param = {
	            "companyId" : company_id
	        };
	        var bigUesrOneURL = "home!toBigUserOne";
	        $.get(bigUesrOneURL, param, function(data) {
	        	$("#dataDiv").html(data);
	        });
	}