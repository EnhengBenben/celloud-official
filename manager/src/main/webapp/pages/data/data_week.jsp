<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<script src="js/drawCharts.js"></script>
<script src="js/data_week.js"></script>
<div class="breadcrumbs" id="breadcrumbs">
	<ul class="breadcrumb">
		<li>
			<i class="icon-tasks"></i>
			<a href="#">数据统计</a>
		</li>
		<li class="active">周统计</li>
	</ul>
	<!-- .breadcrumb -->
</div>
<div class="page-content">
	<div class="col-xs-12 data">
		<div class='row'>
		    <div class="widget-header widget-header-flat">
		         <h3 class="header smaller lighter green">本周Top10</h3>
			</div>
            <h3 class="header smaller lighter green">前10活跃用户及登录次数</h3>
			<div class="col-xs-12" style="height: 350px;" id="topUserLogin"></div>
            <h3 class="header smaller lighter green">前10活跃APP及运行次数</h3>
			<div class="col-xs-12" style="height: 350px;margin-top: 50px;" id="topAppRun"></div>
            <h3 class="header smaller lighter green">数据量前10用户及数据大小</h3>
			<div class="col-xs-12" style="height: 350px;margin-top: 50px;" id="topDataSize"></div>
			<div class="col-xs-11 table-div">
				<div class="table-header hide" id="_companyName"></div>
					<div class="table-responsive " id="dataDiv">
						<table id="weekData" class="table table-striped table-bordered table-hover">
							<thead>
							    <tr>
							        <th colspan="2" style="text-align: center;">前10活跃用户及登录次数</th>
							        <th colspan="2" style="text-align: center;">前10活跃APP及运行次数</th>
							        <th colspan="3" style="text-align: center;">数据量前10用户及数据大小</th>
							    </tr>
								<tr>
									<th>用户名</th>
									<th>登陆次数</th>
									<th>App</th>
                                    <th>运行次数</th>
                                    <th>用户名</th>
                                    <th>数据大小</th>
                                    <th>数据个数</th>
								</tr>
							</thead>
							<tbody>
								<c:if test="${not empty weekData }">
								<c:forEach items="${weekData }" var="data">
									<tr>
										<td>
											${data.logUsername }
										</td>
										<td>${data.logCount }</td>
										<td>${data.appName }</td>
										<td>${data.appCount }</td>
										<td>${data.sizeUsername }</td>
										<td>
											<c:choose>
	                                            <c:when test="${data.sizeSum>1099511627776 }">
	                                                <fmt:formatNumber pattern="0.00" value="${(data.sizeSum-data.sizeSum%1099511627776)/1099511627776 + data.sizeSum%1099511627776/1099511627776 }" />TB</c:when>
	                                            <c:when test="${data.sizeSum>1073741824 }">
	                                                <fmt:formatNumber pattern="0.00" value="${(data.sizeSum-data.sizeSum%1073741824)/1073741824 + data.sizeSum%1073741824/1073741824 }" />GB</c:when>
	                                            <c:when test="${data.sizeSum>1048576 }">
	                                                <fmt:formatNumber pattern="0.00" value="${(data.sizeSum-data.sizeSum%1048576)/1048576 + data.sizeSum%1048576/1048576 }" />MB</c:when>
	                                            <c:otherwise>
	                                                <fmt:formatNumber pattern="0.00" value="${(data.sizeSum-data.sizeSum%1024)/1024 + data.sizeSum%1024/1024 }" />KB</c:otherwise>
	                                        </c:choose>
										</td>
										<td>${data.fileCount }</td>
									</tr>
								</c:forEach>
								</c:if>
								<c:if test="${empty weekData }">
									<tr>
										<td colspan="7" style="text-align: center;">暂无数据!</td>
									</tr>
								</c:if>
							</tbody>
						</table>
					</div>
				<!-- PAGE CONTENT ENDS -->
			</div>
			<!-- /.col -->
		</div>
		<div class="row">
		    <div class="widget-header widget-header-flat">
                 <h3 class="header smaller lighter green">历史比较</h3>
            </div>
            <div class="col-xs-12" style="height: 350px;" id="historyUserLogin"></div>
            <div class="col-xs-12" style="height: 350px;margin-top: 50px;" id="historyUserActive"></div>
            <div class="col-xs-12" style="height: 350px;margin-top: 50px;" id="historyAppRun"></div>
            <div class="col-xs-12" style="height: 350px;margin-top: 50px;" id="historyAppActive"></div>
            <div class="col-xs-12" style="height: 350px;margin-top: 50px;" id="historyDataSize"></div>
            <div class="col-xs-11 table-div">
            <c:if test="${historyWeekData!=null && fn:length(historyWeekData) > 0 }">
                    <div class="table-responsive " id="dataDiv">
                        <table id="historyWeekData" class="table table-striped table-bordered table-hover">
                            <thead>
                                <tr>
                                    <th>开始日期</th>
                                    <th>结束日期</th>
                                    <th>登陆次数</th>
                                    <th>活跃用户数</th>
                                    <th>App运行次数</th>
                                    <th>活跃App</th>
                                    <th>数据大小</th>
                                    <th>数据个数</th>
                                </tr>
                            </thead>
                            <tbody>
                                <c:forEach items="${historyWeekData }" var="data">
                                    <tr>
                                        <td>
                                        	<c:if test="${empty data.historyDate }">无</c:if>
                                        	<c:if test="${not empty data.historyDate }">${data.historyDate.substring(0,10) }</c:if>
                                       	</td>
                                       	<td>
                                        	<c:if test="${empty data.endDate }">无</c:if>
                                        	<c:if test="${not empty data.endDate }">${data.endDate.substring(0,10) }</c:if>
                                       	</td>
                                        <td>
                                        	<c:if test="${empty data.historyWeekUserLogin }">0</c:if>
                                        	<c:if test="${not empty data.historyWeekUserLogin }">${data.historyWeekUserLogin }</c:if>
                                        </td>
                                        <td>
                                        	<c:if test="${empty data.historyWeekActiveUser }">0</c:if>
                                        	<c:if test="${not empty data.historyWeekActiveUser }">${data.historyWeekActiveUser }</c:if>
                                        </td>
                                        <td>
                                        	<c:if test="${empty data.historyWeekAppRun }">0</c:if>
                                        	<c:if test="${not empty data.historyWeekAppRun }">${data.historyWeekAppRun }</c:if>
                                        </td>
                                        <td>
                                        	<c:if test="${empty data.historyWeekAppActive }">0</c:if>
                                        	<c:if test="${not empty data.historyWeekAppActive }">${data.historyWeekAppActive }</c:if>
                                        </td>
                                        <td>
                                        	<c:if test="${empty data.historyWeekDataSize }">0B</c:if>
                                        	<c:if test="${not empty data.historyWeekDataSize }">
                                        		<c:choose>
	                                                <c:when test="${data.historyWeekDataSize>1099511627776 }">
	                                                    <fmt:formatNumber pattern="0.00" value="${(data.historyWeekDataSize-data.historyWeekDataSize%1099511627776)/1099511627776 + data.historyWeekDataSize%1099511627776/1099511627776 }" />TB</c:when>
	                                                <c:when test="${data.historyWeekDataSize>1073741824 }">
	                                                    <fmt:formatNumber pattern="0.00" value="${(data.historyWeekDataSize-data.historyWeekDataSize%1073741824)/1073741824 + data.historyWeekDataSize%1073741824/1073741824 }" />GB</c:when>
	                                                <c:when test="${data.historyWeekDataSize>1048576 }">
	                                                    <fmt:formatNumber pattern="0.00" value="${(data.historyWeekDataSize-data.historyWeekDataSize%1048576)/1048576 + data.historyWeekDataSize%1048576/1048576 }" />MB</c:when>
	                                                <c:otherwise>
	                                                    <fmt:formatNumber pattern="0.00" value="${(data.historyWeekDataSize-data.historyWeekDataSize%1024)/1024 + data.historyWeekDataSize%1024/1024 }" />KB</c:otherwise>
	                                            </c:choose>
                                        	</c:if>
                                        </td>
                                        <td>
                                        	<c:if test="${empty data.historyWeekFileCount }">0</c:if>
                                        	<c:if test="${not empty data.historyWeekFileCount }">${data.historyWeekFileCount }</c:if>
                                        </td>
                                    </tr>
                                </c:forEach>
                            </tbody>
                        </table>
                    </div>
                </c:if>
            </div>
		</div>
	</div>
	<!-- /.row -->
</div>
<!-- /.page-content -->

