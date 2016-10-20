<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<section class="content">
	<div class="col-xs-12">
		<div class="col-xs-4" data-toggle="tab">
			<!-- small box -->
			<div class="small-box bg-aqua">
				<div class="inner">
					<h4>总文件大小:</h4>
					<h4>
						<c:choose>
							<c:when test="${size>1073741824 }">
								<fmt:formatNumber pattern="0.00" value="${size/1073741824 }" />
								GB
							</c:when>
							<c:when test="${size>1048576 }">
								<fmt:formatNumber pattern="0.00" value="${size/1048576 }" />
								MB
							</c:when>
							<c:otherwise>
								<fmt:formatNumber pattern="0.00" value="${size/1024 }" />
								KB
							</c:otherwise>
						</c:choose>
					</h4>
					<h4>已运行数据:</h4>
					<h4 id = "run_File_Num"></h4>
					<h4>未运行数据:</h4>
					<h4 id = "unRun_Num"></h4>
				</div>
				<div class="icon">
					<i class="fa fa-pie-chart"></i>
				</div>
			</div>
		</div>
		<!-- ./col -->
		<div class="col-xs-6" data-toggle="tab">
			<h2></h2>
		</div>
	</div>
	<!-- Small boxes (Stat box) -->
	<div class="row nav-tabs"></div>
	<!-- /.row -->
	<!-- Main row -->
	<div class="row tab-content"></div>
	<!--/.Main row-->
	<section class="col-lg-12 connectedSortable tab-pane active" id="count-data-charts">
		<!-- Custom tabs (Charts with tabs)-->
		<div class="nav-tabs-custom">
			<!-- Tabs within a box -->
			<ul class="nav nav-tabs pull-right">
				<li class="active">
					<a href="#data_week_id" data-toggle="tab">每周</a>
				</li>
				<li>
					<a href="#data_month_id" data-toggle="tab">每月</a>
				</li>
				<li class="pull-left header">
					<i class="fa fa-inbox"></i>
					数据量统计
				</li>
			</ul>
			<div class="tab-content no-padding">
				<!-- Morris chart - Sales -->
				<div id="data_month_id" class="chart tab-pane "  style="position: relative; height: 300px; width: 1165px;"></div>
				<div id="data_week_id" class="chart tab-pane active"  style="position: relative; height: 300px; width: 1165px;"></div>
			</div>
		</div>
		<!-- /.nav-tabs-custom -->
	</section>
	<!-- /.Left col -->
	<!-- Left col -->
	<section class="col-lg-12 connectedSortable tab-pane" id="count-source-charts">
		<!-- Custom tabs (Charts with tabs)-->
		<div class="nav-tabs-custom">
			<!-- Tabs within a box -->
			<ul class="nav nav-tabs pull-right">
				<li class="active">
					<a href="#size_week_id" data-toggle="tab">每周</a>
				</li>
				<li>
					<a href="#size_month_id" data-toggle="tab">每月</a>
				</li>
				<li class="pull-left header">
					<i class="fa fa-inbox"></i>
					数据大小统计
				</li>
			</ul>
			<div class="tab-content no-padding">
				<!-- Morris chart - Sales -->
				<div id="size_month_id"  class="chart tab-pane " style="position: relative; height: 300px; width: 1165px;"></div>
				<div id="size_week_id" class="chart tab-pane active"  style="position: relative; height: 300px; width: 1165px;"></div>
			</div>
		</div>
		<!-- /.nav-tabs-custom -->
	</section>
	<!-- /.Left col -->
	<!-- Left col -->
	<section class="col-lg-12 connectedSortable tab-pane" id="count-report-charts">
		<!-- Custom tabs (Charts with tabs)-->
		<div class="nav-tabs-custom">
			<!-- Tabs within a box -->
			<ul class="nav nav-tabs pull-right">
				<li class="active">
					<a href="#report_week_id" data-toggle="tab">每周</a>
				</li>
				<li>
					<a href="#report_month_id" data-toggle="tab">每月</a>
				</li>
				<li class="pull-left header">
					<i class="fa fa-inbox"></i>
					报告量统计
				</li>
			</ul>
			<div class="tab-content no-padding">
				<!-- Morris chart - Sales -->
				<div id="report_month_id" class="chart tab-pane "  style="position: relative; height: 300px; width: 1165px;"></div>
				<div id="report_week_id" class="chart tab-pane active"  style="position: relative; height: 300px; width: 1165px;"></div>
			</div>
		</div>
		<div class="nav-tabs-custom">
			<div id="app_run_id" style="position: relative; height: 300px; width: 1165px;"></div>
		</div>
		<!-- /.nav-tabs-custom -->
	</section>
	<!-- /.Left col -->
	<!-- Left col -->
	<section class="col-lg-12 connectedSortable tab-pane" id="count-app-charts">
		<!-- Custom tabs (Charts with tabs)-->
		<div class="nav-tabs-custom">
			<!-- Tabs within a box -->
			<ul class="nav nav-tabs pull-right">
				<li class="active">
					<a href="#app_week_id" data-toggle="tab">每周</a>
				</li>
				<li>
					<a href="#app_month_id" data-toggle="tab">每月</a>
				</li>
				<li class="pull-left header">
					<i class="fa fa-inbox"></i>
					APP运行统计
				</li>
			</ul>
			<div class="tab-content no-padding">
				<!-- Morris chart - Sales -->
				<div  id="app_month_id"  class="chart tab-pane "style="position: relative; height: 300px; width: 1165px;"></div>
				<div  id="app_week_id" class="chart tab-pane active" style="position: relative; height: 300px; width: 1165px;"></div>
			</div>
		</div>
		<!-- /.nav-tabs-custom -->
	</section>
	<!-- /.Left col -->

</section>
<!-- /.content -->
