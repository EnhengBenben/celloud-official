$(document).ready(function(){
  var companyId = $("#sessionCompanyId").val();
  if(companyId==6){
  	$.get("report3!toPgsCount",{"pgs.userId":$("#sessionUserId").val(),"pgs.username":$("#sessionUserName").val()},function(responseText){
  		$("#countDiv").html(responseText);
  	});
  }else if (companyId==3){
	$.get("report3!toHBVCount",function(responseText){
		$("#countDiv").html(responseText);
		var url = $("#downUrl").val();
		$("#_down").attr("href",url);
	});
  }else if(companyId==33){
	$.get("cmpReport!toCmpCount",function(responseText){
		$("#countDiv").html(responseText);
		var url = $("#downUrl").val();
		$("#_down").attr("href",url);
	});
  }
});
function showSeq(seq){
  $("#showSeq").html(seq);
  $("#seqModal").modal("show");
}
function showGeneResult(result){
  $("#showGeneResult").html(result);
  $("#geneResultModal").modal("show");
}