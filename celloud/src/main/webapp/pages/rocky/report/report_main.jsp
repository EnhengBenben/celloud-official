<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<div class="selector">
	<div id="batch-sl" class="selector-line">
		<div class="sl-key">标签：</div>
		<div id="selected-batch" class="sl-val selected-val hide">
			<span></span>
			<a id="clear-sl-batch">
				<i class="fa fa-times"></i>
			</a>
		</div>
		<div id="to-sl-batch">
			<div id="batch-lists" class="sl-val">
				<c:forEach items="${batchList }" var="batch">
					<c:if test="${not empty batch}">
						<div class="sl-val-content">
							<div class="celicon checkbox checkbox-un hide"></div>
							<a data-click="report-batch-search" href="javascript:void(0)">
								<span>${batch}</span>
							</a>
						</div>
					</c:if>
				</c:forEach>
				<div class="multisl-btns hide">
					<button id="report-multibatch-search" name="sl-confirm" class="sl-btn disabled" disabled="disabled">确定</button>
					<button data-click="reset-multiselect" class="sl-btn">取消</button>
				</div>
			</div>
			<div class="sl-ext">
				<button id="batch-more" class="sl-more">
					<span>更多</span>
					<i class="fa fa-chevron-down" aria-hidden="true"></i>
				</button>
				<button id="batch-multiselect" data-click="report-select-more" class="sl-multiple">
					多选<i class="fa fa-plus" aria-hidden="true"></i>
				</button>
			</div>
		</div>
	</div>
	<div id="period-sl" class="selector-line">
		<div class="sl-key">状态：</div>
		<div id="selected-period" class="sl-val selected-val hide">
			<span></span>
			<a id="clear-sl-period">
				<i class="fa fa-times"></i>
			</a>
		</div>
		<div id="to-sl-period">
			<div id="period-lists" class="sl-val">
				<div class="sl-val-content">
					<div class="celicon on_check checkbox checkbox-un hide"></div>
					<a data-click="report-period-search" href="javascript:void(0)">
						<input type="hidden" value="2">
						<span>完成</span>
					</a>
				</div>
				<div class="sl-val-content">
					<div class="celicon on_check checkbox checkbox-un hide"></div>
					<a data-click="report-period-search" href="javascript:void(0)">
						<input type="hidden" value="1">
						<span>分析中</span>
					</a>
				</div>
				<div class="sl-val-content">
					<div class="celicon on_check checkbox checkbox-un hide"></div>
					<a data-click="report-period-search" href="javascript:void(0)">
						<input type="hidden" value="0">
						<span>等待分析</span>
					</a>
				</div>
				<div class="sl-val-content">
					<div class="celicon on_check checkbox checkbox-un hide"></div>
					<a data-click="report-period-search" href="javascript:void(0)">
						<input type="hidden" value="3">
						<span>数据上传中</span>
					</a>
				</div>
				<div class="sl-val-content">
					<div class="celicon on_check checkbox checkbox-un hide"></div>
					<a data-click="report-period-search" href="javascript:void(0)">
						<input type="hidden" value="4">
						<span>异常终止</span>
					</a>
				</div>
				<div class="sl-val-content">
					<div class="celicon on_check checkbox checkbox-un hide"></div>
					<a data-click="report-period-search" href="javascript:void(0)">
						<input type="hidden" value="6">
						<span>实验中</span>
					</a>
				</div>
				<div class="sl-val-content">
					<div class="celicon on_check checkbox checkbox-un hide"></div>
					<a data-click="report-period-search" href="javascript:void(0)">
						<input type="hidden" value="5">
						<span>送样中</span>
					</a>
				</div>
				<div class="multisl-btns hide">
					<button id="report-multiperiod-search" name="sl-confirm" class="sl-btn disabled" disabled="disabled">确定</button>
					<button data-click="reset-multiselect" class="sl-btn">取消</button>
				</div>
			</div>
			<div class="sl-ext">
				<button data-click="report-select-more" class="sl-multiple">
					多选<i class="fa fa-plus" aria-hidden="true"></i>
				</button>
			</div>
		</div>
	</div>
	<div class="selector-line">
		<div class="sl-key">时间：</div>
		<div class="sl-val">
			<input id="report-begindate-search" type="text" class="Wdate" onclick="WdatePicker({maxDate:'#F{$dp.$D(\'report-enddate-search\')}'})" readonly="readonly" placeholder="  年    月    日">
			-
			<input id="report-enddate-search" type="text" class="Wdate" onclick="WdatePicker({minDate:'#F{$dp.$D(\'report-begindate-search\')}',maxDate:'%y-%M-%d'})" readonly="readonly" placeholder="  年    月    日">
			<button data-click="report-date-search" class="sl-btn">确定</button>
		</div>
	</div>
	<!-- div class="selector-line">
		<div class="sl-key">搜索：</div>
		<div class="sl-val">
			<input id="report-sample-search" type="text" style="width:232px;" placeholder="样本编号/病历号/文件名/标签">
			<button data-click="report-date-search" class="sl-btn">确定</button>
		</div>
	</div -->
</div>
<div id="rocky-report-list">
	<table class="table table-main">
		<thead>
			<tr>
				<th width="40"></th>
				<th width="140">
					<input id="report-sample-filter" type="text" placeholder="样本编号/病历号" value="${sampleFilter }">
				</th>
				<th>
					文件名
					<c:if test="${sidx=='filename'&&sord=='asc' }">
						<a id="reportSortBtn-filename-desc" href="javascript:void(0);">
							<i class="fa fa-sort-amount-asc"></i>
						</a>
					</c:if>
					<c:if test="${sidx=='filename'&&sord=='desc' }">
						<a id="reportSortBtn-filename-asc" href="javascript:void(0);">
							<i class="fa fa-sort-amount-desc"></i>
						</a>
					</c:if>
					<c:if test="${sidx!='filename' }">
						<a id="reportSortBtn-filename" href="javascript:void(0);">
							<i class="fa fa-sort" aria-hidden="true"></i>
						</a>
					</c:if>
				</th>
				<th>
					标签
					<c:if test="${sidx=='batch'&&sord=='asc' }">
						<a id="reportSortBtn-batch-desc" href="javascript:void(0);">
							<i class="fa fa-sort-amount-asc"></i>
						</a>
					</c:if>
					<c:if test="${sidx=='batch'&&sord=='desc' }">
						<a id="reportSortBtn-batch-asc" href="javascript:void(0);">
							<i class="fa fa-sort-amount-desc"></i>
						</a>
					</c:if>
					<c:if test="${sidx!='batch' }">
						<a id="reportSortBtn-batch" href="javascript:void(0);">
							<i class="fa fa-sort" aria-hidden="true"></i>
						</a>
					</c:if>
				</th>
				<th>状态</th>
				<th>
					更新时间
					<c:if test="${sidx=='updateDate'&&sord=='asc' }">
						<a id="reportSortBtn-updateDate-desc" href="javascript:void(0);">
							<i class="fa fa-sort-amount-asc"></i>
						</a>
					</c:if>
					<c:if test="${sidx=='updateDate'&&sord=='desc' }">
						<a id="reportSortBtn-updateDate-asc" href="javascript:void(0);">
							<i class="fa fa-sort-amount-desc"></i>
						</a>
					</c:if>
					<c:if test="${sidx!='updateDate' }">
						<a id="reportSortBtn-updateDate" href="javascript:void(0);">
							<i class="fa fa-sort" aria-hidden="true"></i>
						</a>
					</c:if>
				</th>
				<th>操作</th>
			</tr>
		</thead>
		<tbody id="data-list-tbody">
			<c:forEach items="${pageList.datas }" var="report">
				<tr id="report-${report.dataKey}-${report.projectId}-${report.appId}">
					<td>
						<label class="checkbox-lable">
							<input class="checkbox" type="checkbox" name="demo-checkbox1">
							<span class="info"></span>
						</label>
					</td>
					<td>${report.sampleName }</td>
					<td title="${report.fileName }" style="text-align: left;" name="data-name-td">
						<c:choose>
							<c:when test="${report.period ==2 }">
								<a title="查看报告" style="color: #323232; font-size: 12px;" href="javascript:$report.showReport('${report.dataKey}','${report.projectId}','${report.appId}')"> ${report.fileName }</a>
							</c:when>
							<c:otherwise>
								${report.fileName }
							</c:otherwise>
						</c:choose>

					</td>
					<td>${report.batch }</td>
					<td>
						<c:if test="${report.period==0 }">等待运行</c:if>
						<c:if test="${report.period==1 }">正在运行</c:if>
						<c:if test="${report.period==2 }">完成</c:if>
						<c:if test="${report.period==3 }">数据上传中</c:if>
						<c:if test="${report.period==4 }">异常终止</c:if>
						<c:if test="${report.period==5 }">送样中</c:if>
						<c:if test="${report.period==6 }">实验中</c:if>
						<c:if test="${empty report.period }">
							<a href="javascript:void(0)" onclick="$.report.period.error('${report.fileName }')" class="wrong">运行异常</a>
						</c:if>
					</td>
					<td>
						<fmt:formatDate value="${report.updateDate }" type="both" dateStyle="long" pattern="yyyy-MM-dd HH:mm:ss" />
					</td>
					<td>
						<c:choose>
							<c:when test="${report.period ==2 }">
								<a title="查看报告" href="javascript:$report.showReport('${report.dataKey}','${report.projectId}','${report.appId}')">
									<i class="fa fa-eye"></i>
								</a>
								<a title="打印患者报告" target="_blank" href="<%=request.getContextPath()%>/report/printRockyReport?projectId=${report.projectId}&dataKey=${report.dataKey}&appId=${report.appId}">
									<i class="fa fa-print"></i>
								</a>
								<a title="共享报告" href="javascript:void(0)">
									<i class="fa fa-share-square-o"></i>
								</a>
							</c:when>
							<c:otherwise>
								<a title="查看报告" class="disabled bg-gray" href="javascript:;">
									<i class="fa fa-eye"></i>
								</a>
								<a title="打印患者报告" class="disabled bg-gray" disabled="disabled">
									<i class="fa fa-print"></i>
								</a>
								<a title="共享报告" href="javascript:void(0)">
									<i class="fa fa-share-square-o"></i>
								</a>
							</c:otherwise>
						</c:choose>

					</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
	<c:if test="${pageList.datas.size()<=0 }">
		<div style="display: table; width: 100%; margin-top: -10px;">
			<div class="text-center" style="display: table-cell; height: 350px; font-size: 18px; vertical-align: middle; background-color: #fff;">
				<i class="glyphicon glyphicon-exclamation-sign" style="color: #f39c12;"></i> 未检索到数据！
			</div>
		</div>
	</c:if>
	<div id="rocky_report_page">
		<jsp:include page="../pagination.jsp"></jsp:include>
	</div>
	<jsp:include page="../statistic.jsp"></jsp:include>
</div>