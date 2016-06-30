<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"
	import="com.celloud.constants.RechargeType"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<table class="table data-table">
	<thead>
		<tr>
			<th width="30px">
				<input type="checkbox" />
			</th>
			<th class="text-center">发票</th>
			<th>充值方式</th>
			<th class="text-center">充值时间</th>
			<th class="text-right">充值金额</th>
			<th class="text-right">充值后余额</th>
		</tr>
	</thead>
	<tbody>
		<c:forEach items="${recharges.datas }" var="recharge">
			<tr>
				<td>
					<c:choose>
						<c:when test="${recharge.invoiceState!=0 }">
							<input type="checkbox" disabled="disabled" />
						</c:when>
						<c:otherwise>
							<input type="checkbox" name="rechargeIds" value="${recharge.id }" />
						</c:otherwise>
					</c:choose>
				</td>
				<td class="text-center">
				    ${recharge.invoiceState==0?"未开":(recharge.invoiceState==1?"已开":"不开" )}
				</td>
				<td>${recharge.rechargeTypeName }</td>
				<td class="text-center">
					<fmt:formatDate value="${recharge.createDate }" pattern="yyyy-MM-dd HH:mm:ss" />
				</td>
				<td class="text-right">${recharge.amount }</td>
				<td class="text-right">${recharge.balances }</td>
			</tr>
		</c:forEach>
		<c:if test="${ recharges.datas.size() == 0  }">
			<tr height="250px">
				<td colspan="6" class="text-center" style="vertical-align: middle; font-size: 18px;">
					<i class="glyphicon glyphicon-exclamation-sign text-yellow"></i> 您好，还没有充值记录哦！
				</td>
			</tr>
		</c:if>
	</tbody>
</table>
<div class="row">
	<div class="col-xs-2">
		<c:if test="${ recharges.datas.size() != 0  }">
		&nbsp;
		<button class="btn btn-default">申请发票</button>
		</c:if>
	</div>
	<div class="col-xs-10">
		<div class="pagination text-right">
			<c:if test="${recharges.datas.size()>0}">
				<input id="expense-current-page-hide" value="${recharges.page.currentPage }" type="hidden">
				<ul id="pagination-pay" class="pages">
					<!-- 显示prev -->
					<c:if test="${recharges.page.hasPrev}">
						<li>
							<a
								href="javascript:$.expense.pay.pageRechargeList('${recharges.page.currentPage-1>0?recharges.page.currentPage-1:1 }');">&lt;</a>
						</li>
					</c:if>
					<!-- 显示第一页 -->
					<c:choose>
						<c:when test="${recharges.page.currentPage==1}">
							<li class="active">
								<a href="javascript:void(0);">1</a>
							</li>
						</c:when>
						<c:otherwise>
							<li>
								<a href="javascript:$.expense.pay.pageRechargeList('1');">1</a>
							</li>
						</c:otherwise>
					</c:choose>

					<c:if test="${recharges.page.currentPage>4&&recharges.page.totalPage>10}">
						<li>...</li>
					</c:if>
					<c:choose>
						<c:when test="${recharges.page.totalPage-recharges.page.currentPage>=7}">
							<c:if test="${recharges.page.currentPage==3}">
								<li>
									<a href="javascript:$.expense.pay.pageRechargeList('${recharges.page.currentPage-1 }');">${recharges.page.currentPage-1 }</a>
								</li>
							</c:if>
							<c:if test="${recharges.page.currentPage==4}">
								<li>
									<a href="javascript:$.expense.pay.pageRechargeList('${recharges.page.currentPage-2 }');">${recharges.page.currentPage-2 }</a>
								</li>
							</c:if>
							<c:if test="${recharges.page.currentPage>3}">
								<li>
									<a href="javascript:$.expense.pay.pageRechargeList('${recharges.page.currentPage-1 }');">${recharges.page.currentPage-1 }</a>
								</li>
							</c:if>
							<c:if test="${recharges.page.currentPage>1&&recharges.page.currentPage<recharges.page.totalPage}">
								<li class="active">
									<a href="javascript:void(0);">${recharges.page.currentPage }</a>
								</li>
							</c:if>
							<c:if test="${recharges.page.totalPage-recharges.page.currentPage>1}">
								<li>
									<a href="javascript:$.expense.pay.pageRechargeList('${recharges.page.currentPage+1 }')">${recharges.page.currentPage+1 }</a>
								</li>
							</c:if>
							<c:if test="${recharges.page.totalPage-recharges.page.currentPage>2}">
								<li>
									<a href="javascript:$.expense.pay.pageRechargeList('${recharges.page.currentPage+2 }')">${recharges.page.currentPage+2 }</a>
								</li>
							</c:if>
							<c:if test="${recharges.page.totalPage-recharges.page.currentPage>3}">
								<li>
									<a href="javascript:$.expense.pay.pageRechargeList('${recharges.page.currentPage+3 }')">${recharges.page.currentPage+3 }</a>
								</li>
							</c:if>
							<c:if test="${recharges.page.totalPage-recharges.page.currentPage>4}">
								<li>
									<a href="javascript:$.expense.pay.pageRechargeList('${recharges.page.currentPage+4 }')">${recharges.page.currentPage+4 }</a>
								</li>
							</c:if>
							<c:if test="${recharges.page.totalPage-recharges.page.currentPage>5}">
								<li>
									<a href="javascript:$.expense.pay.pageRechargeList('${recharges.page.currentPage+5 }')">${recharges.page.currentPage+5 }</a>
								</li>
							</c:if>
							<c:if test="${recharges.page.currentPage<4}">
								<c:if test="%{recharges.page.totalPage-recharges.page.currentPage>6}">
									<li>
										<a href="javascript:$.expense.pay.pageRechargeList('${recharges.page.currentPage+6 }')">${recharges.page.currentPage+6 }</a>
									</li>
								</c:if>
							</c:if>
							<c:choose>
								<c:when test="${recharges.page.currentPage==1}">
									<c:if test="%{recharges.page.totalPage-recharges.page.currentPage>7}">
										<li>
											<a href="javascript:$.expense.pay.pageRechargeList('${recharges.page.currentPage+7 }')">${recharges.page.currentPage+7 }</a>
										</li>
									</c:if>
									<c:if test="%{recharges.page.totalPage-recharges.page.currentPage>8}">
										<li>
											<a href="javascript:$.expense.pay.pageRechargeList('${recharges.page.currentPage+8 }')">${recharges.page.currentPage+8 }</a>
										</li>
									</c:if>
								</c:when>
								<c:otherwise>
									<c:choose>
										<c:when test="${recharges.page.currentPage==2}">
											<c:if test="${recharges.page.totalPage-recharges.page.currentPage>7}">
												<li>
													<a href="javascript:$.expense.pay.pageRechargeList('${recharges.page.currentPage+7 }')">${recharges.page.currentPage+7 }</a>
												</li>
											</c:if>
										</c:when>
										<c:otherwise>
											<c:if test="${recharges.page.currentPage>4 && (recharges.page.totalPage-recharges.page.currentPage>6)}">
												<li>
													<a href="javascript:$.expense.pay.pageRechargeList('${recharges.page.currentPage+6 }')">${recharges.page.currentPage+6 }</a>
												</li>
											</c:if>
										</c:otherwise>
									</c:choose>
								</c:otherwise>
							</c:choose>
						</c:when>
						<c:otherwise>
							<c:choose>
								<c:when test="${recharges.page.totalPage-8>0}">
									<c:forEach begin="${recharges.page.totalPage-8}" step="1" end="${recharges.page.totalPage-1}" var="step">
										<c:choose>
											<c:when test="${step==recharges.page.currentPage}">
												<li class="active">
													<a href="javascript:void(0)">${step }</a>
												</li>
											</c:when>
											<c:otherwise>
												<li>
													<a href="javascript:void(0)">${step }</a>
												</li>
											</c:otherwise>
										</c:choose>
									</c:forEach>
								</c:when>
								<c:otherwise>
									<c:forEach begin="2" step="1" end="${recharges.page.totalPage-1}" var="step">
										<c:choose>
											<c:when test="${step==recharges.page.currentPage}">
												<li class="active">
													<a href="javascript:void(0)">${step }</a>
												</li>
											</c:when>
											<c:otherwise>
												<li>
													<a href="javascript:$.expense.pay.pageRechargeList('${step}')">${step }</a>
												</li>
											</c:otherwise>
										</c:choose>
									</c:forEach>
								</c:otherwise>
							</c:choose>
						</c:otherwise>
					</c:choose>
					<c:if test="${recharges.page.totalPage-recharges.page.currentPage>=8&&recharges.page.totalPage>10}">
						<li>...</li>
					</c:if>
					<c:choose>
						<c:when test="${recharges.page.currentPage==recharges.page.totalPage&&recharges.page.totalPage>1}">
							<li class="active">
								<a href="javascript:void(0)">${recharges.page.totalPage }</a>
							</li>
						</c:when>
						<c:otherwise>
							<c:if test="${recharges.page.totalPage>1}">
								<li>
									<a href="javascript:$.expense.pay.pageRechargeList('${recharges.page.totalPage }')">${recharges.page.totalPage }</a>
								</li>
							</c:if>
						</c:otherwise>
					</c:choose>
					<c:if test="${recharges.page.hasNext}">
						<li>
							<a
								href="javascript:$.expense.pay.pageRechargeList('${recharges.page.currentPage+1>recharges.page.totalPage?recharges.page.totalPage:recharges.page.currentPage+1 }')">&gt;</a>
						</li>
					</c:if>
					<li>共${recharges.page.totalPage }页&nbsp;|&nbsp;合计${recharges.page.rowCount }条</li>
				</ul>
			</c:if>
		</div>
	</div>
</div>
