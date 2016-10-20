$.lAlert = function(text){
	$("#tip-text").html(text);
	$("#check-true").one("click",function(){
		$("#tip-modal").hide();
	});
	$("#tip-modal").show();
}
$.lConfirm = function(text, callback){
	$("#confirm-text").html(text);
	$("#confirm-flase").one("click",function(){
		$("#confirm-modal").hide();
	});
	if(typeof callback == 'function'){
		$("#confirm-true").one("click",function(){
			$("#confirm-modal").hide();
			callback();
		});
	}
	$("#confirm-modal").show();
}