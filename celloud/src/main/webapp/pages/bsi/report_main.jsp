<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<div class="content-header">
  <ol class="breadcrumb">
    <li>主页</li>
    <li>应用</li>
    <li>百菌探</li>
    <li><a data-click="report-list" href="javascript:void(0)">报告</a></li>
    <li id="to-my-report">报告列表</li>
  </ol>
</div>
<div class="selector">
  <div class="selector-line">
    <div class="sl-key">标签：</div>
    <div id="batch-lists" class="sl-val">
      <c:forEach items="${batchList }" var="batch">
        <c:if test="${not empty batch}">
          <div class="sl-val-content">
	        <div class="celicon checkbox checkbox-un hide"></div>
	        <a data-click="report-batch-search" href="javascript:void(0)"><span>${batch}</span></a>
          </div>
        </c:if>
      </c:forEach>
      <div class="multisl-btns hide">
        <a id="report-multibatch-search" class="sl-btn disabled" href="javascript:void(0)" disabled="disabled">确定</a>
        <a data-click="reset-multiselect" class="sl-btn" href="javascript:void(0)">取消</a>
      </div>
    </div>
    <div class="sl-ext">
      <a id="batch-more" class="sl-more" href="javascript:void(0)">更多<i class="fa fa-chevron-down" aria-hidden="true"></i></a>
      <a id="batch-multiselect" data-click="report-select-more" class="sl-multiple" href="javascript:void(0)">多选<i class="fa fa-plus" aria-hidden="true"></i></a>
    </div>
  </div>
  <div class="selector-line">
    <div class="sl-key">状态：</div>
    <div id="period-lists" class="sl-val">
      <div class="sl-val-content">
        <div class="celicon on_check checkbox checkbox-un hide"></div>
        <a data-click="report-period-search" href="javascript:void(0)"><input type="hidden" value="2"><span>完成</span></a>
      </div>
      <div class="sl-val-content">
        <div class="celicon on_check checkbox checkbox-un hide"></div>
        <a data-click="report-period-search" href="javascript:void(0)"><input type="hidden" value="1"><span>分析中</span></a>
      </div>
      <div class="sl-val-content">
        <div class="celicon on_check checkbox checkbox-un hide"></div>
        <a data-click="report-period-search" href="javascript:void(0)"><input type="hidden" value="0"><span>等待分析</span></a>
      </div>
      <div class="sl-val-content">
        <div class="celicon on_check checkbox checkbox-un hide"></div>
        <a data-click="report-period-search" href="javascript:void(0)"><input type="hidden" value="3"><span>数据上传中</span></a>
      </div>
      <div class="sl-val-content">
        <div class="celicon on_check checkbox checkbox-un hide"></div>
        <a data-click="report-period-search" href="javascript:void(0)"><input type="hidden" value="4"><span>异常终止</span></a>
      </div>
      <div class="sl-val-content">
        <div class="celicon on_check checkbox checkbox-un hide"></div>
        <a data-click="report-period-search" href="javascript:void(0)"><input type="hidden" value="6"><span>实验中</span></a>
      </div>
      <div class="sl-val-content">
        <div class="celicon on_check checkbox checkbox-un hide"></div>
        <a data-click="report-period-search" href="javascript:void(0)"><input type="hidden" value="5"><span>送样中</span></a>
      </div>
      <div class="multisl-btns hide">
        <a id="report-multiperiod-search" class="sl-btn disabled" href="javascript:void(0)" disabled="disabled">确定</a>
        <a data-click="reset-multiselect" class="sl-btn" href="javascript:void(0)">取消</a>
      </div>
    </div>
    <div class="sl-ext">
      <a data-click="report-select-more" class="sl-multiple" href="javascript:void(0)">多选<i class="fa fa-plus" aria-hidden="true"></i></a>
    </div>
  </div>
  <div class="selector-line">
    <div class="sl-key">时间：</div>
    <div class="sl-val">
      <input id="report-begindate-search" type="text" class="Wdate" onclick="WdatePicker()" readonly="readonly" placeholder="  年    月    日"> - 
      <input id="report-enddate-search" type="text" class="Wdate" onclick="WdatePicker()" readonly="readonly" placeholder="  年    月    日">
      <a data-click="report-date-search" class="sl-btn" href="javascript:void(0)">确定</a>
    </div>
  </div>
  <div class="selector-line">
    <div class="sl-key">是否分发：</div>
    <div class="sl-val">
      <a data-click="report-distributed-search" class="sl-judge" href="javascript:void(0)">
        <span class="sl-judge-yes">是</span>
        <span class="sl-judge-no hide">否</span>
      </a>
    </div>
  </div>
</div>
<div id="report-list">
</div>
<div class="report-statistics">
  数据汇总：
  <span>已出报告[${periodMap.done }份] </span>
  <span>待出报告[${periodMap.wait }份] </span>
  <span>已传数据[${periodMap.uploaded }批] </span>
  <span>正在上传[${periodMap.uploading }批] </span>
  <span>状态异常[${periodMap.error }份] </span>
  <span class="pull-right">统计时间：<fmt:formatDate value="${nowDate }" type="both" dateStyle="long" pattern="yyyy-MM-dd" /> </span>
</div>
