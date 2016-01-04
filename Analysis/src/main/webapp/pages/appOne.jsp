<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<div class="col-sm-8">
	<div class="row">
		<div class="col-xs-12">
			<div class="widget-box">
				<div class="widget-header widget-header-flat">
					<h4 class="smaller">基本信息</h4>
				</div>
				<div class="widget-body">
					<div class="widget-main">
						<dl id="dt-list-1" class="dl-horizontal"
							style="word-break: break-all; word-wrap: break-word; line-height: 2em; vertical-align: middle">
							<dt>APP名称：</dt>
							<dd>${app.app_name }</dd>
							<dt>上线时间：</dt>
							<dd>
								<fmt:formatDate type="both" value="${app.create_date }" pattern="yyyy-MM-dd" />
							</dd>
							<dt>运行次数：</dt>
							<dd>${app.runNum }</dd>
							<dt>运行需文件个数：</dt>
							<dd>${app.data_num }</dd>
							<dt>可运行数据格式：</dt>
							<dd>${app.dataType }</dd>
							<dt>APP图标：</dt>
							<dd>
								<img src="http://celloud.org/images/app/${app.picture_name }">
							</dd>
							<dt>软件功能：</dt>
							<dd>${app.description }</dd>
							<dt>软件介绍：</dt>
							<dd>${app.intro }</dd>
						</dl>
					</div>
				</div>
				<div class="col-sm-10" style="height: 350px;" id="appRun"></div>
			</div>
		</div>
	</div>
	<div class="space-6"></div>
</div>
<script type="text/javascript">
	var id = $("#_app_id").html();
	var vid = "appRun";
	console.log(id);
	$.get("app!getAppRun", {
		"app.app_id" : id
	}, function(res) {
		   if(res==null||res.length<1){
			   $('#'+vid).hide();
			   return ;
		   }else{
			   $('#'+vid).show();
		   }
		var xAxis = new Array(res.length);
		var runNum = new Array(res.length);
		for (var i = 0; i < res.length; i++) {
			xAxis[i] = res[i].yearMonth;
			runNum[i] = res[i].runNum;
		}
		var option = makeOptionScrollUnit(xAxis, runNum, "运行次数", barType, 0, 12);
		var myChart = echarts.init(document.getElementById(vid));
		myChart.setOption(option);
	});
</script>