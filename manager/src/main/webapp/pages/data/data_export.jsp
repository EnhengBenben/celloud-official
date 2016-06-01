<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<script src="plugins/date-time/moment.js"></script>
<script src="plugins/date-time/daterangepicker.js"></script>
<script src="js/data_export.js"></script>
<link rel="stylesheet" href="plugins/date-time/daterangepicker.css">
<div class="breadcrumbs">
	<ul class="breadcrumb">
		<li>
			<i class="icon-tasks"></i>
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
				<a class="btn btn-info" onclick="selectAll();">全&nbsp;选</a>
				<a class="btn btn-info" onclick="selectNone();">取&nbsp;消</a>
				<a class="btn btn-info" onclick="selectOthers();">反&nbsp;选</a>
			</div>
			<div class="col-sm-12" >
                <c:forEach items="${userList }" var="user">
                    <div class="col-sm-3">
                        <label>
                            <input type="checkbox" name="userList" value="${user.userId }">
                                            ${user.username }
                        </label>
                    </div>
                </c:forEach>
                <div class="clearfix"></div>
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
			<button class="btn btn-success" onclick="dataFile.exportData()">导出</button>
			<a class="btn btn-success" id="downLink" style="display: none;">下载</a>
		</div>
	</div>
</div><!-- /.page-content -->
<form action="data/export" method="post" style="display:none;" id="data-export-form">
<input type="hidden" name="userIds">
<input type="hidden" name="start">
<input type="hidden" name="end">
</form>