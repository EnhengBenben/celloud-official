<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<div class="breadcrumbs" id="breadcrumbs">
    <script type="text/javascript">
        try {
            ace.settings.check('breadcrumbs', 'fixed');
        } catch (e) {
        }
    </script>
    <ul class="breadcrumb">
        <li>
             <i class="icon-bar-chart"></i>
             <a href="#" onclick="toActivity()">活跃度统计</a>
        </li>
    </ul>
    <!-- .breadcrumb -->
    <span id="_app_id" class="hide"></span>
</div>
<div class="page-content">
	<div>
		<div style="padding-left: 65px">
			<a style="font-size: 16px; cursor: pointer;" onclick="javascript:localWeek()">本周</a>
			&nbsp;&nbsp;
			<a style="font-size: 16px; cursor: pointer;" onclick="javascript:localMonth()">本月</a>
			&nbsp;&nbsp; &nbsp;&nbsp;
			<label>开始时间： </label>
			<input id="timeId" type="date" name="startDate" onchange="loadContent()">
			<label>结束时间： </label>
			<input id="timeId2" type="date" name="endDate" onchange="loadContent()">
			<label>Top N:</label>
			&nbsp;&nbsp;
			<select id="topId" style="width: 120px"  onchange="loadContent()">
				<option value="0" selected></option>
				<option value="5">5</option>
				<option value="10">10</option>
				<option value="15">15</option>
			</select>
		</div>
	</div>
	<div class="row" id="contentView"></div>
</div>
<script>
	localMonth();
	function loadContent() {
		var start = $("#timeId").val();
		var end = $("#timeId2").val();
		var top = $("#topId").val();
		var param = {
			"startDate" : start,
			"endDate" : end,
			"topN" : top
		};
		console.log(param);
		$.get("company!getContent", param, function(responseText) {
			$("#contentView").html(responseText);
		});
	}

	function localWeek() {
		console.log(showWeekFirstDay());
		console.log(showWeekLastDay());
		
		$("#timeId").val(showWeekFirstDay());
		$('#timeId2').val(showWeekLastDay());
		loadContent();
	}
	function localMonth() {
		console.log(showMonthFirstDay());
		console.log(showMonthLastDay());
		$("#timeId").val(showMonthFirstDay());
		$('#timeId2').val(showMonthLastDay());
		loadContent();
	}
</script>