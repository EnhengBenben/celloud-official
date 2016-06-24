<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<div class="pagination text-right">
	<c:if test="${page.rowCount>0}">
		<input id="data-current-page-hide" value="${page.currentPage }" type="hidden">
		<ul class="pages " id="pagination-data">
			<!-- 显示prev -->
			<c:if test="${page.hasPrev}">
				<li>
					<a href="javascript:<%=request.getParameter("method") %>(${page.currentPage-1 },${page.pageSize })">&lt;</a>
				</li>
			</c:if>
			<!-- 显示第一页 -->
			<c:choose>
				<c:when test="${page.currentPage==1}">
					<li class="active">
						<a href="javascript:<%=request.getParameter("method") %>(1,${page.pageSize })">1</a>
					</li>
				</c:when>
				<c:otherwise>
					<li>
						<a href="javascript:<%=request.getParameter("method") %>(1,${page.pageSize })">1</a>
					</li>
				</c:otherwise>
			</c:choose>

			<c:if test="${page.currentPage>4&&page.totalPage>10}">
				<li>...</li>
			</c:if>
			<c:choose>
				<c:when test="${page.totalPage-page.currentPage>=7}">
					<c:if test="${page.currentPage==3}">
						<li>
							<a href="javascript:<%=request.getParameter("method") %>(${page.currentPage-1 },${page.pageSize })">${page.currentPage-1 }</a>
						</li>
					</c:if>
					<c:if test="${page.currentPage==4}">
						<li>
							<a href="javascript:<%=request.getParameter("method") %>(${page.currentPage-2 },${page.pageSize })">${page.currentPage-2 }</a>
						</li>
					</c:if>
					<c:if test="${page.currentPage>3}">
						<li>
							<a href="javascript:<%=request.getParameter("method") %>(${page.currentPage-1 },${page.pageSize })">${page.currentPage-1 }</a>
						</li>
					</c:if>
					<c:if test="${page.currentPage>1&&page.currentPage<page.totalPage}">
						<li class="active">
							<a href="javascript:<%=request.getParameter("method") %>(${page.currentPage },${page.pageSize })">${page.currentPage }</a>
						</li>
					</c:if>
					<c:if test="${page.totalPage-page.currentPage>1}">
						<li>
							<a href="javascript:<%=request.getParameter("method") %>(${page.currentPage+1 },${page.pageSize })">${page.currentPage+1 }</a>
						</li>
					</c:if>
					<c:if test="${page.totalPage-page.currentPage>2}">
						<li>
							<a href="javascript:<%=request.getParameter("method") %>(${page.currentPage+2 },${page.pageSize })">${page.currentPage+2 }</a>
						</li>
					</c:if>
					<c:if test="${page.totalPage-page.currentPage>3}">
						<li>
							<a href="javascript:<%=request.getParameter("method") %>(${page.currentPage+3 },${page.pageSize })">${page.currentPage+3 }</a>
						</li>
					</c:if>
					<c:if test="${page.totalPage-page.currentPage>4}">
						<li>
							<a href="javascript:<%=request.getParameter("method") %>(${page.currentPage+4 },${page.pageSize })">${page.currentPage+4 }</a>
						</li>
					</c:if>
					<c:if test="${page.totalPage-page.currentPage>5}">
						<li>
							<a href="javascript:<%=request.getParameter("method") %>(${page.currentPage+5 },${page.pageSize })">${page.currentPage+5 }</a>
						</li>
					</c:if>
					<c:if test="${page.currentPage<4}">
						<c:if test="$(page.totalPage-page.currentPage>6}">
							<li>
								<a href="javascript:<%=request.getParameter("method") %>(${page.currentPage+6 },${page.pageSize })">${page.currentPage+6 }</a>
							</li>
						</c:if>
					</c:if>
					<c:choose>
						<c:when test="${page.currentPage==1}">
							<c:if test="$(page.totalPage-page.currentPage>7}">
								<li>
									<a href="javascript:<%=request.getParameter("method") %>(${page.currentPage+7 },${page.pageSize })">${page.currentPage+7 }</a>
								</li>
							</c:if>
							<c:if test="$(page.totalPage-page.currentPage>8}">
								<li>
									<a href="javascript:<%=request.getParameter("method") %>(${page.currentPage+8 },${page.pageSize })">${page.currentPage+8 }</a>
								</li>
							</c:if>
						</c:when>
						<c:otherwise>
							<c:choose>
								<c:when test="${page.currentPage==2}">
									<c:if test="${page.totalPage-page.currentPage>7}">
										<li>
											<a href="javascript:<%=request.getParameter("method") %>(${page.currentPage+7 },${page.pageSize })">${page.currentPage+7 }</a>
										</li>
									</c:if>
								</c:when>
								<c:otherwise>
									<c:if test="${page.currentPage>4 && (page.totalPage-page.currentPage>6)}">
										<li>
											<a href="javascript:<%=request.getParameter("method") %>(${page.currentPage+6 },${page.pageSize })">${page.currentPage+6 }</a>
										</li>
									</c:if>
								</c:otherwise>
							</c:choose>
						</c:otherwise>
					</c:choose>
				</c:when>
				<c:otherwise>
					<c:choose>
						<c:when test="${page.totalPage-8>0}">
							<c:forEach begin="${page.totalPage-8}" step="1" end="${page.totalPage-1}" var="step">
								<c:choose>
									<c:when test="${step==page.currentPage}">
										<li class="active">
											<a href="javascript:<%=request.getParameter("method") %>(${step },${page.pageSize })">${step }</a>
										</li>
									</c:when>
									<c:otherwise>
										<li>
											<a href="javascript:<%=request.getParameter("method") %>(${step },${page.pageSize })">${step }</a>
										</li>
									</c:otherwise>
								</c:choose>
							</c:forEach>
						</c:when>
						<c:otherwise>
							<c:forEach begin="2" step="1" end="${page.totalPage-1}" var="step">
								<c:choose>
									<c:when test="${step==page.currentPage}">
										<li class="active">
											<a href="javascript:<%=request.getParameter("method") %>(${step },${page.pageSize })">${step }</a>
										</li>
									</c:when>
									<c:otherwise>
										<li>
											<a href="javascript:<%=request.getParameter("method") %>(${step },${page.pageSize })">${step }</a>
										</li>
									</c:otherwise>
								</c:choose>
							</c:forEach>
						</c:otherwise>
					</c:choose>
				</c:otherwise>
			</c:choose>
			<c:if test="${page.totalPage-page.currentPage>=8&&page.totalPage>10}">
				<li>...</li>
			</c:if>
			<c:choose>
				<c:when test="${page.currentPage==page.totalPage&&page.totalPage>1}">
					<li class="active">
						<a href="javascript:<%=request.getParameter("method") %>(${page.totalPage },${page.pageSize })">${page.totalPage }</a>
					</li>
				</c:when>
				<c:otherwise>
					<c:if test="${page.totalPage>1}">
						<li>
							<a href="javascript:<%=request.getParameter("method") %>(${page.totalPage },${page.pageSize })">${page.totalPage }</a>
						</li>
					</c:if>
				</c:otherwise>
			</c:choose>
			<c:if test="${page.hasNext}">
				<li>
					<a href="javascript:<%=request.getParameter("method") %>(${page.currentPage+1 },${page.pageSize })">&gt;</a>
				</li>
			</c:if>
			<li>共${page.totalPage }页&nbsp;|&nbsp;合计${page.rowCount }条</li>
		</ul>
	</c:if>
</div>
