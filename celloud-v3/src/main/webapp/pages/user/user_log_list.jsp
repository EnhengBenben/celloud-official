<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<table class="table_" style="text-align: center;">
	<c:set var="datas" value="${pageList.datas }"></c:set>
	<c:set var="page" value="${pageList.page }"></c:set>
	<thead>
		<tr>
			<th>操作</th>
			<th>描述</th>
			<th>时间</th>
			<th>Ip地址</th>
			<th>登录地点</th>
			<th>浏览器</th>
			<th>操作系统</th>
		</tr>
	</thead>
	<tbody>

		<c:forEach items="${datas }" var="log">
			<tr>
				<td>${log.operate }</td>
				<td>${log.message }</td>
				<td>
					<fmt:formatDate value="${log.logDate }" pattern="yyyy-MM-dd HH:mm:ss" />
				</td>
				<td>${log.ip }</td>
				<td>${log.address }</td>
				<td>${log.browser }${' '}${log.browserVersion }</td>
				<td>${log.os }${' '}${log.osVersion }</td>
			</tr>
		</c:forEach>
		<c:if test="${datas.size()==0 }">
			<tr>
				<td colspan="7">操作日志为空</td>
			</tr>
		</c:if>
	</tbody>
</table>
<div class="pagination center">
	<ul class="pages">
		<li class="${page.hasPrev?'':'active' }">
			<a href="javascript:users.searchLogInfo('${page.hasPrev?(page.currentPage-1):'' }');">&lt;</a>
		</li>
		<li class="${page.hasPrev?'':'active' }">
			<a href="javascript:users.searchLogInfo('${page.hasPrev?1:'' }');">1</a>
		</li>
		<c:if test="${page.currentPage > 4 && page.totalPage>10}">
			<li>...</li>
		</c:if>
		<c:choose>
			<c:when test="${ page.currentPage <= 4}">
				<c:set var="maxPage" value="${page.totalPage > 9 ? 9 : page.totalPage-1 }" />
				<c:forEach begin="2" end="${maxPage }" step="1" var="pageNum">
					<li class="${pageNum != page.currentPage?'':'active' }">
						<a href="javascript:users.searchLogInfo('${pageNum != page.currentPage?pageNum:'' }');">${pageNum }</a>
					</li>
				</c:forEach>
			</c:when>
			<c:when test="${page.currentPage > 4 && page.currentPage < page.totalPage - 6 }">
				<c:forEach begin="${page.currentPage-1 }" end="${page.currentPage+6 }" step="1" var="pageNum">
					<li class="${pageNum != page.currentPage?'':'active' }">
						<a href="javascript:users.searchLogInfo('${pageNum != page.currentPage?pageNum:'' }');">${pageNum }</a>
					</li>
				</c:forEach>
			</c:when>
			<c:when test="${page.currentPage > 4 && page.currentPage >= page.totalPage -  6 }">
				<c:forEach begin="${page.totalPage-8>=1?page.totalPage-8:2 }" end="${page.totalPage-1 }" step="1" var="pageNum">
					<li class="${pageNum != page.currentPage?'':'active'}">
						<a href="javascript:users.searchLogInfo('${pageNum != page.currentPage?pageNum:'' }');">${pageNum }</a>
					</li>
				</c:forEach>
			</c:when>
		</c:choose>
		<c:if test="${page.totalPage>10 && page.totalPage- page.currentPage > 7}">
			<li>...</li>
		</c:if>
		<li class="${page.hasNext?'':'active' }">
			<a href="javascript:users.searchLogInfo('${page.hasNext?page.totalPage:'' }');">${page.totalPage }</a>
		</li>
		<li class="${page.hasNext?'':'active' }">
			<a href="javascript:users.searchLogInfo('${page.hasNext?page.currentPage+1:'' }');">&gt;</a>
		</li>
		<li>共 ${page.totalPage } 页&nbsp;|&nbsp;合计 ${page.rowCount} 条</li>
	</ul>
</div>
