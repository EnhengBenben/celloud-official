<%@ page language="java" pageEncoding="UTF-8"%>
<style>
font{
	color: red;
}
</style>
<div>
	<font>
		<p>本统计排除了开发人员常用的帐号的登录信息：lin、xiawt、lqx、zl、demo、administrator、test</p>
		<p>本统计标准为向下取整，即2：59分的登录统计入2：00</p>
		<p>小球提示信息格式为：年-月-日 时刻：登录次数</p>
	</font>
</div>
<div style="min-height: 400px;" id="showPic">

</div>
<script src="<%=request.getContextPath() %>/plugins/amcharts/amcharts.js"></script>
<script>
$(document).ready(function(){
	getData();
});
</script>