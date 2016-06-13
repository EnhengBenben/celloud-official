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
    <div class="sl-val show-more">
      <c:forEach items="${batchList }" var="batch">
        <a href=""><c:if test="${not empty batch}"><span>${batch}</span></c:if></a>
      </c:forEach>
    </div>
    <div class="sl-ext">
      <a class="sl-more" href="javascript:void(0)">更多<i class="fa fa-angle-down" aria-hidden="true"></i></a>
      <a class="sl-multiple" href="javascript:void(0)">多选<i class="fa fa-plus" aria-hidden="true"></i></a>
    </div>
  </div>
  <div class="selector-line">
    <div class="sl-key">状态：</div>
    <div class="sl-val">
      <span>ddd</span>
    </div>
    <div class="sl-ext">
      <a class="sl-multiple" href="javascript:void(0)">多选<i class="fa fa-plus" aria-hidden="true"></i></a>
    </div>
  </div>
  <div class="selector-line">
    <div class="sl-key">时间：</div>
    <div class="sl-val">
      <span>ddd</span>
    </div>
  </div>
  <div class="selector-line">
    <div class="sl-key">是否分发：</div>
    <div class="sl-val">
      <span>ddd</span>
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
