(function(dataFile){
	dataFile.exportData=function(){
		var chk_value =[];    
	    $('input[name="userList"]:checked').each(function(){    
	        chk_value.push($(this).val());    
	    });
	    if(!chk_value.join(",")){
	        alert("最少选择一个用户");
	        return;
	    }
	    var time = $("#date-range").val();
	    if(!time){
	        alert("请选择一个时间段");
	        return;
	    }
	    var both = time.split(" ");
	    var start = both[0].trim();
	    var end = both[2].trim();
	    $("#data-export-form input[name='userIds']").val(chk_value.join(","));
	    $("#data-export-form input[name='start']").val(start+" 00:00:00");
	    $("#data-export-form input[name='end']").val(end+" 23:59:59");
	    $("#data-export-form").submit();
	};
})(dataFile);

$(function(){
	 $('input[name=date-picker]').daterangepicker({
		    "locale": {
		        "format": "YYYY-MM-DD"
		     },
		     autoApply:true
	 }).prev().on("click", function(){
        $(this).next().focus();
    });
});