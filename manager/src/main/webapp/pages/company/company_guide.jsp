<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<script src="js/drawCharts.js"></script>
<script src="js/company_guide.js"></script>
<div class="breadcrumbs" id="breadcrumbs">
	<ul class="breadcrumb">
		<li>
			<i class="icon-hospital"></i>
			<a href="#">医院统计</a>
		</li>
		<li class="active" onclick="getPreDataView()">医院总览</li>
	</ul>
	<!-- .breadcrumb -->
</div>
<div class="page-content">
	<div class="col-xs-12">
		<div class="row">
			<div class="col-sm-3">
				<div class="xe-widget xe-counter-block" data-count=".num" data-from="0" data-to="${resultMap.companyNum }" data-suffix="(个)" data-duration="2">
					<div class="xe-upper">
						<div class="xe-label">
							<strong>医院数量:</strong>
							<strong class="num">${resultMap.companyNum }(个)</strong>
						</div>
					</div>
					<div class="xe-lower">
						<div class="border"></div>
					</div>
				</div>
			</div>
			<div class="col-sm-3">
				<div class="xe-widget xe-counter-block xe-counter-block-blue" data-suffix="(个)" data-count=".num" data-from="0" data-to="${resultMap.userNum }"
					data-duration="2" data-easing="false">
					<div class="xe-upper">
						<div class="xe-label">
							<strong>用户数量:</strong>
							<strong class="num">${resultMap.userNum }(个)</strong>
						</div>
					</div>
					<div class="xe-lower">
						<div class="border"></div>
					</div>
				</div>
			</div>
		</div>
		<h3 class="header smaller lighter green ">新增用户统计</h3>
		<c:if test="${sessionScope.loginUserInSession.role == 2 }">
			<div id="newCmpBigUser" style="height: 300px;"></div>
		</c:if>
		<div id="main" style="height: 300px"></div>
		<div id="newCompany" style="height: 300px"></div>

		<div class="row">
			<c:if test="${resultMap.companyData!=null && fn:length(resultMap.companyData)>0 }">
				<div class="table-responsive table-div" id="dataDiv">
					<table id="newCompanyList" class="table table-striped table-bordered table-hover">
						<thead>
							<tr>
								<th class="min-w-200">月份</th>
								<th class="min-w-200">医院新增数量</th>
							</tr>
						</thead>
						<tbody>
							<c:forEach items="${resultMap.companyData}" var="item">
								<tr>
									<td>${item.time }</td>
									<td>${item.companyNum }</td>
								</tr>
							</c:forEach>
						</tbody>
					</table>
				</div>
			</c:if>
		</div>
		<div class="row">
			<div class="col-xs-12">
				<h3 class="header smaller lighter green">用户地理分布</h3>
				<div class="col-sm-9" style="height: 500px;" id="map"></div>
				<div class="col-sm-2" style="height: 500px;">
					<ul id="listCompany">
					</ul>
				</div>
			</div>
		</div>
	</div>
</div>