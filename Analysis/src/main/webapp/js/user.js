function gotoOutputData(){
	$("#content").load("pages/outputData.jsp");
}
function initUserList(){
	$.get("user!getBigUsersUser",function(list){
		var length = list.length;
		var tdNum = 7;//每个tr多少个td
		var context="<tr>";
		for ( var i = 0; i < length; i++) {
			context+="<td><div class='checkbox'><label><input name='userList' type='checkbox' class='ace' value='"+list[i].user_id+"'><span class='lbl'>  " +list[i].username+" </span></label></div></td>";
			if(i>0&&(i+1)%tdNum==0){
				context+="</tr><tr>";
			}
		}
		context+="</tr>";
		$("#userList").html(context);
	});
}
function outputData(){
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
	$.get("data!outputData",{"userIds":chk_value.join(","),"start":start,"end":end},function(filename){
		$("#downLink").css("display","");
		$("#downLink").attr("href","data!download?fileName="+filename);
	});
}
function selectAll(){
	$("input[name='userList']").prop('checked',true);
}
function selectNone(){
	$("input[name='userList']").prop('checked',false);
}
function selectOthers(){
	$("input[name='userList']").each(function(){
		$(this).prop("checked", !$(this).prop("checked"));  
	});	
}