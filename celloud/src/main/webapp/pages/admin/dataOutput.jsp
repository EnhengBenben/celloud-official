<%@ page language="java" pageEncoding="UTF-8"%>
<div style="margin-bottom: 10px;">
	&nbsp;&nbsp;&nbsp;用户名：<div id="userChoose" style="min-width: 333px;"></div>
</div>
<div id="superDiv">
	上传时间：From <input type="text" id="_searchDate" class="input Wdate" onclick="WdatePicker()" readonly="readonly" style="cursor: pointer;width: 120px;"/>
	To <input type="text" id="_endDate" class="input Wdate" onclick="WdatePicker()" readonly="readonly" style="cursor: pointer;width: 120px;"/>
</div>
<a href="javascript:void(0)" onclick="outPutSubmit()" class="btn btn-info">导出</a>
<a id="downLink" style="display: none;" href="" class="btn btn-success">下载</a>
<script type="text/javascript">
$(function(){
	getAllUser();
});
</script>