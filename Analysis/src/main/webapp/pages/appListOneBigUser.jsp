<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

	<div class="row">
		<div class="col-xs-12">
			<div class="table-header hide" id="_appName"></div>
				<table id="appList" class="table table-striped table-bordered table-hover">
					<thead>
						<tr>
							<th class="min-w-80">APP名称</th>
							<th class="min-w-80">研发机构</th>
							<th class="min-w-80">上线时间</th>
							<th class="min-w-80">运行次数</th>
							<th class="min-w-80">软件介绍</th>
						</tr>
					</thead>

					<tbody>
						<c:if test="${appList!=null}">
							<c:forEach var="item" items="${ appList}">
								<tr>
									<td>${item.app_name }</td>
									<td>${item.company_name }</td>
									<td>
										<fmt:formatDate type="both" value="${item.create_date }" pattern="yyyy-MM-dd" />
									</td>
									<td>${item.runNum }</td>
									<td>${item.description }</td>
								</tr>
							</c:forEach>
						</c:if>
					</tbody>
				</table>
				<div class="col-sm-10" style="height: 350px;" id="echartView"></div>
			</div>
		</div>
