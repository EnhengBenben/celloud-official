<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<script src="js/drawCharts.js"></script>
<script src="js/data_user.js"></script>
<div class="breadcrumbs" id="breadcrumbs">
	<ul class="breadcrumb">
		<li>
			<i class="icon-tasks"></i>
			<a href="#">数据统计</a>
		</li>
		<li class="active">用户数据统计</li>
	</ul>
	<!-- .breadcrumb -->
</div>
<div class="page-content">
	<div class="col-xs-12 data">
		<div class='row'>
		    <div class="widget-header widget-header-flat">
		         <h3 class="header smaller lighter green">用户数据个数统计</h3>
			</div>
			<div class="col-xs-12" style="height: 350px;" id="userFileNum"></div>
			<div class="col-xs-11 table-div">
				<div class="table-header hide" id="_companyName"></div>
				<c:if test="${userDataCount!=null && fn:length(userDataCount) > 0 }">
					<div class="table-responsive " id="dataDiv">
						<table id="allUserDataList" class="table table-striped table-bordered table-hover">
							<thead>
								<tr>
									<th>用户名</th>
									<th>所在医院</th>
									<th class="hidden-480">数据个数(个)</th>
									<th class="hidden-480">数据大小</th>
								</tr>
							</thead>
							<tbody>
								<c:forEach items="${userDataCount }" var="data">
									<tr>
										<td>
											${data.username }
											<!--<a href="javascript:userMonthDataList('${data.user_id }','${data.username }','${data.company_name }');">${data.username }</a>-->
										</td>
										<td>${data.company_name }</td>
										<td>${data.dataNum }</td>
										<td>
											<c:choose>
	                                            <c:when test="${data.dataSize>1099511627776 }">
	                                                <fmt:formatNumber pattern="0.00" value="${(data.dataSize-data.dataSize%1099511627776)/1099511627776 + data.dataSize%1099511627776/1099511627776 }" />TB</c:when>
	                                            <c:when test="${data.dataSize>1073741824 }">
	                                                <fmt:formatNumber pattern="0.00" value="${(data.dataSize-data.dataSize%1073741824)/1073741824 + data.dataSize%1073741824/1073741824 }" />GB</c:when>
	                                            <c:when test="${data.dataSize>1048576 }">
	                                                <fmt:formatNumber pattern="0.00" value="${(data.dataSize-data.dataSize%1048576)/1048576 + data.dataSize%1048576/1048576 }" />MB</c:when>
	                                            <c:otherwise>
	                                                <fmt:formatNumber pattern="0.00" value="${(data.dataSize-data.dataSize%1024)/1024 + data.dataSize%1024/1024 }" />KB</c:otherwise>
	                                        </c:choose>
										</td>
									</tr>
								</c:forEach>
							</tbody>
						</table>
					</div>
				</c:if>
				<!-- PAGE CONTENT ENDS -->
			</div>
			<!-- /.col -->
		</div>
	</div>
	<!-- /.row -->
</div>
<!-- /.page-content -->

