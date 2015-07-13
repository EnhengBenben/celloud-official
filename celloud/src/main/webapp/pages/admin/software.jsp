<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<div class="tabbable"> <!-- tabs -->
    <div class="" style="overflow: hidden;">
		<div class="tab-pane active" id="tab1">
			<div id="softAddModal" class="modal hide fade" tabindex="-1">
				
			</div>
			<div id="softwareList">
				
			</div>
		</div>
	</div>
</div>
<script type="text/javascript">
$.ajaxSetup ({
	cache: false //关闭AJAX相应的缓存
});
$(document).ready(function(){
	initSoftware();
});
</script>