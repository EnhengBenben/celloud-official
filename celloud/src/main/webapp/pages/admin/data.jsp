<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<div class="row" style="padding-left:30px;">
	<div style="float: left;">
		<form class="form-inline">
			请输入需要清数据的用户：<input type="text" name="username" id="search_username" placeholder="用户名"/>
			<a id="search" href="javascript:search();" class="btn btn-primary"><i class="icon-search icon-white"></i> 确定</a>
		</form>
	</div>
	<div id="dataList">
		
	</div>
</div>
<script type="text/javascript">
$.ajaxSetup ({
	cache: false //关闭AJAX相应的缓存
});
var username = null;
var PAGE = 0;
function search(){
	username = $.trim($("#search_username").val());
	if(!username){
		alert("请输入用户名");
		return ;
	}
	searchData(1);
}
function searchData(page){
	PAGE = page;
	$.get("data!getUserData",{"userNames":username,"page.pageSize":2000,"page.currentPage":PAGE,"type":2,"sort":"desc"},function(responseText){
		$("#dataList").html(responseText);
	});
}
function deleteData(id,name){
	$.get("data!deleteData",{"dataIds":id,"fileName":name},function(flag){
		if(flag==1){
			searchData(PAGE);
		}else{
			alert("失败");
		}
	});
}
</script>