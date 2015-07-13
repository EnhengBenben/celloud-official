<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div class="breadcrumbs">
	<script type="text/javascript">
		try{ace.settings.check('breadcrumbs' , 'fixed');}catch(e){}
	</script>
	<ul class="breadcrumb">
		<li>
			<i class="icon-home home-icon"></i>
			<a href="#">数据统计</a>
		</li>
		<li class="active">数据导出</li>
	</ul><!-- .breadcrumb -->
</div>
<div class="page-content">
	<div class="row">
		<div class="col-xs-12">
			<h3 class="header smaller lighter blue">
				<span>用户列表</span>
			</h3>
			<div>
				<a class="btn" onclick="selectAll();">全选</a>
				<a class="btn" onclick="selectNone();">全不选</a>
				<a class="btn" onclick="selectOthers();">反选</a>
			</div>
			<div>
				<table id="userList">
					
				</table>
			</div>
		</div><!-- /.col -->
	</div>
	<div class="row">
		<div class="col-xs-12">
			<h3 class="header smaller lighter blue">
				<span>时间</span>
			</h3>
			<div class="col-xs-12 col-sm-3">
				<div class="input-group">
					<span class="input-group-addon">
						<i class="icon-calendar bigger-110"></i>
					</span>
					<input class="form-control" name="date-picker" id="date-range" type="text">
				</div>
			</div>
		</div>
	</div>
	<div class="row" style="margin-top: 40px;">
		<div class="col-xs-12">
			<button class="btn btn-success" onclick="outputData()">导出</button>
			<a class="btn btn-success" id="downLink" style="display: none;">下载</a>
		</div>
	</div>
</div><!-- /.page-content -->
<script type="text/javascript">
$(document).ready(function(){
	initUserList();
	$('input[name=date-picker]').daterangepicker({format: 'YYYY-MM-DD'}).prev().on(ace.click_event, function(){
		$(this).next().focus();
	});
});
</script>