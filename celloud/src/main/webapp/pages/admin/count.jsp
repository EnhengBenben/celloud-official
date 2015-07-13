<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<div class="tabbable"> <!-- tabs -->
    <div class="tab-content"> 
		<div class="tab-pane active" id="tab1">
			<div id="userDataList">
			</div>
			<div id="countUserAll">
			</div>
			<div id="everyUser">
			</div>
			<div id="everyMounth">
			</div>
		</div>
	</div>
</div>
<script type="text/javascript">
$.ajaxSetup ({
	cache: false //关闭AJAX相应的缓存
});
$(document).ready(function(){
	initAnalysis();
});
</script>