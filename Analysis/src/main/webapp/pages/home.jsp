<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<script type="text/javascript" src="./js/tableExport.js"></script>
<script type="text/javascript" src="./js/home.js"></script>
<style>
.table {
	margin-left: 40px;
	margin-right: 20px;
}

.btn-position {
	margin-left: 45px;
}

.btn {
	margin-left: 5px;
	margin-top: 7px;
	margin-bootom: 5px;
}
</style>

<div class="breadcrumbs" id="breadcrumbs">
	<script type="text/javascript">
		try {
			ace.settings.check('breadcrumbs', 'fixed')
		} catch (e) {
		}
	</script>
	<ul class="breadcrumb">
		<li>
			<i class="icon-dashboard"></i>
			<a href="#">控制台</a>
		</li>
	</ul>
	<!-- .breadcrumb -->
</div>
<div class="page-content">

	<div class="row">
		<div class="col-xs-12">
			<div class="row">
				<div class="widget-box transparent">
					<div class="widget-header widget-header-flat">
						<h3 class="lighter">
							<i class="icon-signal"></i>
							总量统计
						</h3>
						<div class="widget-toolbar">
							<a href="#" data-action="collapse">
								<i class="icon-chevron-up"></i>
							</a>
						</div>
					</div>

					<div class="widget-body">
						<div class="widget-main padding-4">
							<div class="col-sm-7 infobox-container" style="min-width: 600px">
								<div class="infobox infobox-green  ">
									<div class="infobox-icon">医院总量：</div>
									<div class="infobox-data">
										<span class="infobox-data-number">${resultMap.companyNum }</span>
										<span class="infobox-content">（个）</span>
									</div>
								</div>
								<div class="infobox infobox-blue  ">
									<div class="infobox-icon">用户总量：</div>
									<div class="infobox-data">
										<span class="infobox-data-number">${resultMap.userNum }</span>
										<span class="infobox-content">（个）</span>
									</div>
								</div>
								<div class="infobox infobox-pink  ">
									<div class="infobox-icon">报告总量：</div>
									<div class="infobox-data">
										<span class="infobox-data-number">${resultMap.reportNum }</span>
										<span class="infobox-content">（个）</span>
									</div>
								</div>
								<div class="infobox infobox-pink  ">
									<div class="infobox-icon">APP总量：</div>
									<div class="infobox-data">
										<span class="infobox-data-number">${resultMap.appNum }</span>
										<span class="infobox-content">（个）</span>
									</div>
								</div>
								<div class="infobox infobox-red  ">
									<div class="infobox-icon">数据总量：</div>
									<div class="infobox-data">
										<span class="infobox-data-number">${resultMap.dataNum }</span>
										<span class="infobox-content">(个)</span>
									</div>
								</div>

								<div class="infobox infobox-orange2  ">
									<div class="infobox-icon">数据总大小：</div>
									<div class="infobox-data">
										<span class="infobox-data-number">
											<fmt:formatNumber pattern="0.00" value="${resultMap.dataSize }"></fmt:formatNumber>
										</span>
										<span class="infobox-content">(GB)</span>
									</div>
								</div>
								<div class="space-6"></div>
							</div>
						</div>
						<!-- /widget-main -->
					</div>
					<!-- /widget-body -->
				</div>
				<!-- /widget-box -->
				<div class="space-6"></div>
			</div>
			<!-- /row -->
			<div class="row">
				<div class="col-xs-12">
					<h3 class="header smaller lighter green">医院地理分布</h3>
					<div class="col-sm-10" style="height: 550px;" id="map"></div>
				</div>
			</div>
			<!-- /row -->
			<c:if test="${userRole=='2' || userRole=='3' || 1==1}">
				<div class="row">
					<div class="col-xs-12">
						<h3 class="header smaller lighter green">浏览器统计</h3>
						<div class="col-sm-12" style="height: 450px" id="browserDistribute"></div>
					</div>
				</div>
			</c:if>
			
			<div class="row">
                <div class="col-xs-12">
                    <h3 class="header smaller lighter green">APP使用统计</h3>
                    <div class="col-sm-12" style="height: 450px;" id="AppRunNum"></div>
                    <!-- row -->
                </div>
            </div>
			
			<!-- row -->
			<div class="row">
				<div class="col-xs-12">
					<h3 class="header smaller lighter green">用户运行统计</h3>
					<div class="col-sm-12" style="height: 450px;" id="UserRunNum"></div>
				</div>
			</div>

			<div class="row">
				<div class="col-xs-12">
					<h3 class="header smaller lighter green">登陆排序</h3>
					<div id="loginId" class="col-sm-12" style="height: 450px;"></div>
					<!-- row -->
					<div class="space"></div>
					<div class="hr hr32 hr-dotted"></div>
					<!-- PAGE CONTENT ENDS -->
				</div>
				<!-- /.col -->
			</div>
			<!-- /.row -->
		</div>
	</div>
	<!-- /.page-content -->
	</div>