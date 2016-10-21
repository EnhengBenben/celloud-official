<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<script src="js/data_othersite.js"></script>
<div class="breadcrumbs" id="breadcrumbs">
	<ul class="breadcrumb">
		<li>
			<i class="icon-tasks"></i>
			<a href="#">数据统计</a>
		</li>
		<li class="active">其他位点统计</li>
	</ul>
</div>
<div class="page-content">
	<div class="col-xs-12 data">
		<div class='row'>
		    <div class="widget-header widget-header-flat">
		         <h3 class="header smaller lighter green">用户其他位点统计</h3>
			</div>
			<div class="col-xs-11 table-div">
				<c:if test="${othserSiteCount != null && fn:length(othserSiteCount) > 0 }">
					<div class="table-responsive " id="siteDiv">
						<table id="otherSiteDataList" class="table table-striped table-bordered table-hover">
							<thead>
								<tr>
									<th>位点</th>
									<th>出现次数</th>
									<th>比例</th>
								</tr>
							</thead>
							<tbody>
								<c:forEach items="${othserSiteCount }" var="otherSite">
									<tr>
										<td><a href="javascript:void(0);" onclick="">${otherSite.key }</a></td>
										<td>${otherSite.value['count'] }</td>
										<td>${otherSite.value['percent'] }</td>
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
		<div class='row'>
            <div class="widget-header widget-header-flat">
                 <h3 class="header smaller lighter green">用户其他位点统计</h3>
            </div>
            <div class="col-xs-11 table-div">
                <c:if test="${othserSiteCount != null && fn:length(othserSiteCount) > 0 }">
                    <div class="table-responsive " id="siteDiv">
                        <table id="otherSiteDataList" class="table table-striped table-bordered table-hover">
                            <thead>
                                <tr>
                                    <th>医院名称</th>
                                    <th>比例</th>
                                </tr>
                            </thead>
                            <tbody>
                                <c:forEach items="${othserSiteCount }" var="otherSite">
                                    <tr>
                                        <td>${otherSite.value['count'] }</td>
                                        <td>${otherSite.value['percent'] }</td>
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

