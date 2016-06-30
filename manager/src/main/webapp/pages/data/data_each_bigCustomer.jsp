<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<script src="js/data_each_bigCustomer.js"></script>
<div class="row">
    <div class="col-xs-12">

	<h3 class="header smaller lighter green">数据统计</h3>
	<div style="height: 300px;" id="fileNum"></div>
	</div>
	<div class="col-xs-12">
		<c:if test="${fn:length(dataMon) > 0}">
			<div class="table-responsive">
				<table id="allUserDataList" class="table table-striped table-bordered table-hover">
					<thead>
						<tr>
							<th>月份</th>
							<th class="hidden-480">数据个数(个)</th>
							<th class="hidden-480">数据大小</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach items="${dataMon}" var="data">
							<tr>
								<td>${data.time }</td>
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

	</div>
</div>