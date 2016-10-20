<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<div class="pagination text-center">
	<c:if test="${pageList.datas.size()>0}">
		<ul class="checkboxul pull-left">
			<li class="checkbox-li">
				<label class="checkbox-lable"> <input class="checkbox" type="checkbox" name="demo-checkbox1"> <span class="info"></span>
				</label>
			</li>
			<li>全选</li>
			<li>
				<select>
					<option value="0">批量下载</option>
					<option value="1">批量归档</option>
					<option value="2">批量分发</option>
				</select>
			</li>
		</ul>
		<ul class="datanumul pull-right">
			<li>
				<span>共&nbsp;&nbsp;${pageList.page.rowCount }&nbsp;&nbsp;条</span>
			</li>
			<li>
				每页 <select id="page-size-sel">
					<option value="10" <c:if test="${pageList.page.pageSize==10}">selected</c:if>>10</option>
					<option value="20" <c:if test="${pageList.page.pageSize==20}">selected</c:if>>20</option>
					<option value="30" <c:if test="${pageList.page.pageSize==30}">selected</c:if>>30</option>
					<option value="50" <c:if test="${pageList.page.pageSize==50}">selected</c:if>>50</option>
					<option value="100" <c:if test="${pageList.page.pageSize==100}">selected</c:if>>100</option>
				</select>条
			</li>
		</ul>
		<ul id="pagination-ul" class="pages pull-right">
			<!-- 显示prev -->
			<li>
				<a id="prev-page" class="ends" data-click="pagination-btn" data-page="${pageList.page.currentPage>1?pageList.page.currentPage-1:1 }" href="javascript:void(0);">&lt;&lt;</a>
			</li>
			<c:choose>
				<c:when test="${pageList.page.totalPage<=7}">
					<c:forEach begin="1" step="1" end="${pageList.page.totalPage}" var="step">
						<c:choose>
							<c:when test="${step==pageList.page.currentPage}">
								<li class="active">
									<a href="javascript:void(0);">${step }</a>
								</li>
							</c:when>
							<c:otherwise>
								<li>
									<a name="pagination-info" data-click="pagination-btn" data-page="${step }" href="javascript:void(0)">${step }</a>
								</li>
							</c:otherwise>
						</c:choose>
					</c:forEach>
				</c:when>
				<c:otherwise>
					<c:if test="${pageList.page.currentPage<=4}">
						<c:forEach begin="1" step="1" end="5" var="step">
							<c:choose>
								<c:when test="${step==pageList.page.currentPage}">
									<li class="active">
										<a href="javascript:void(0);">${step }</a>
									</li>
								</c:when>
								<c:otherwise>
									<li>
										<a name="pagination-info" data-click="pagination-btn" data-page="${step }" href="javascript:void(0)">${step }</a>
									</li>
								</c:otherwise>
							</c:choose>
						</c:forEach>
						<li>
							<a href="javascript:void(0)">…</a>
						</li>
						<li>
							<a name="pagination-info" data-click="pagination-btn" data-page="${pageList.page.totalPage }" href="javascript:void(0)">${pageList.page.totalPage }</a>
						</li>
					</c:if>
					<c:if test="${pageList.page.currentPage>4 && pageList.page.currentPage<pageList.page.totalPage-3}">
						<li>
							<a name="pagination-info"  data-click="pagination-btn" data-page="1" href="javascript:void(0)">1</a>
						</li>
						<li>
							<a href="javascript:void(0)">…</a>
						</li>
						<li>
							<a name="pagination-info"  data-click="pagination-btn" data-page="${pageList.page.currentPage-1  }" href="javascript:void(0)">${pageList.page.currentPage-1 }</a>
						</li>
						<li class="active">
							<a href="javascript:void(0);">${pageList.page.currentPage }</a>
						</li>
						<li>
							<a name="pagination-info" data-click="pagination-btn" data-page="${pageList.page.currentPage+1  }" href="javascript:void(0)">${pageList.page.currentPage+1 }</a>
						</li>
						<li>
							<a href="javascript:void(0)">…</a>
						</li>
						<li>
							<a name="pagination-info" data-click="pagination-btn" data-page="${pageList.page.totalPage  }" href="javascript:void(0)">${pageList.page.totalPage }</a>
						</li>
					</c:if>
					<c:if test="${pageList.page.currentPage>=pageList.page.totalPage-3}">
						<li>
							<a name="pagination-info" data-click="pagination-btn" data-page="1"  href="javascript:void(0)">1</a>
						</li>
						<li>
							<a href="javascript:void(0)">…</a>
						</li>
						<c:forEach begin="${pageList.page.totalPage-4}" step="1" end="${pageList.page.totalPage}" var="step">
							<c:choose>
								<c:when test="${step==pageList.page.currentPage}">
									<li class="active">
										<a href="javascript:void(0);">${step }</a>
									</li>
								</c:when>
								<c:otherwise>
									<li>
										<a name="pagination-info" data-click="pagination-btn" data-page="${step }"  href="javascript:void(0)">${step }</a>
									</li>
								</c:otherwise>
							</c:choose>
						</c:forEach>
					</c:if>
				</c:otherwise>
			</c:choose>
			<li>
				<a id="next-page" class="ends" data-click="pagination-btn" data-page="${pageList.page.currentPage < pageList.page.totalPage?pageList.page.currentPage+1:pageList.page.totalPage }" href="javascript:void(0)">&gt;&gt;</a>
			</li>
		</ul>
	</c:if>
</div>