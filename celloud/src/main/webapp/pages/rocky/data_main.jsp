<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<table class="table table-main">
  <thead>
    <tr>
      <th width="40"></th>
      <th width="140">
        <input id="sample-selector" type="text" placeholder="样本编号/病历号">
      </th>
      <th>文件名</th>
      <th>标签<a href="javascript:void(0);"><i class="fa fa-sort" aria-hidden="true"></i></a></th>
      <th>文件大小</th>
      <th>状态<a href="javascript:void(0);"><i class="fa fa-sort" aria-hidden="true"></i></a></th>
      <th>上传时间<a id="sort-date" href="javascript:void(0);"><i id="sort-date-icon" class="fa fa-sort-amount-asc"></i></a></th>
      <th>操作</th>
    </tr>
  </thead>
  <tbody id="data-list-tbody">
    <tr>
      <td>
        <label class="checkbox-lable">
            <input class="checkbox" type="checkbox" name="demo-checkbox1">
            <span class="info"></span>
        </label>
      </td>
      <td>633747872828</td>
      <td title="123456789.R1.fastq.gz" name="data-name-td" >123456789.R1.fastq.gz</td>
      <td>中日</td>
      <td>345M</td>
      <td>分析中</td>
      <td>2016-07-07 07:07:07</td>
      <td>
         <a title="查看报告"
           <c:choose>
             <c:when test="${task.period ==2 }">
               href="javascript:$.report.detail.patient('${task.dataKey}',${task.projectId},${task.appId},${size.count},${pageList.page.currentPage })"
             </c:when>
             <c:otherwise>
             class="disabled"  disabled="disabled"
             </c:otherwise>
           </c:choose>><i class="fa fa-eye"></i></a>
         <a title="打印患者报告" 
           <c:choose>
             <c:when test="${task.period ==2 }">
               target="_blank"
               href="<%=request.getContextPath()%>/report/printBSIReport?projectId=${task.projectId }&dataKey=${task.dataKey }&appId=${task.appId }&templateType=print_patient"
             </c:when>
             <c:otherwise>
             class="disabled"  disabled="disabled"
             </c:otherwise>
           </c:choose>><i class="fa fa-print"></i></a>
         <a title="共享报告" href="javascript:void(0)"><i class="fa fa-share-square-o"></i></a>
       </td>
    </tr>
    <tr>
      <td>
        <label class="checkbox-lable">
            <input class="checkbox" type="checkbox" name="demo-checkbox1">
            <span class="info"></span>
        </label>
      </td>
      <td>633747872828</td>
      <td title="123456789.R1.fastq.gz" name="data-name-td" >123456789.R1.fastq.gz</td>
      <td>中日</td>
      <td>345M</td>
      <td>分析中</td>
      <td>2016-07-07 07:07:07</td>
      <td>
         <a title="查看报告"
           <c:choose>
             <c:when test="${task.period ==2 }">
               href="javascript:$.report.detail.patient('${task.dataKey}',${task.projectId},${task.appId},${size.count},${pageList.page.currentPage })"
             </c:when>
             <c:otherwise>
             class="disabled"  disabled="disabled"
             </c:otherwise>
           </c:choose>><i class="fa fa-eye"></i></a>
         <a title="打印患者报告" 
           <c:choose>
             <c:when test="${task.period ==2 }">
               target="_blank"
               href="<%=request.getContextPath()%>/report/printBSIReport?projectId=${task.projectId }&dataKey=${task.dataKey }&appId=${task.appId }&templateType=print_patient"
             </c:when>
             <c:otherwise>
             class="disabled"  disabled="disabled"
             </c:otherwise>
           </c:choose>><i class="fa fa-print"></i></a>
         <a title="共享报告" href="javascript:void(0)"><i class="fa fa-share-square-o"></i></a>
       </td>
    </tr>
    <tr>
      <td>
        <label class="checkbox-lable">
            <input class="checkbox" type="checkbox" name="demo-checkbox1">
            <span class="info"></span>
        </label>
      </td>
      <td>633747872828</td>
      <td title="123456789.R1.fastq.gz" name="data-name-td" >123456789.R1.fastq.gz</td>
      <td>中日</td>
      <td>345M</td>
      <td>分析中</td>
      <td>2016-07-07 07:07:07</td>
      <td>
         <a title="查看报告"
           <c:choose>
             <c:when test="${task.period ==2 }">
               href="javascript:$.report.detail.patient('${task.dataKey}',${task.projectId},${task.appId},${size.count},${pageList.page.currentPage })"
             </c:when>
             <c:otherwise>
             class="disabled"  disabled="disabled"
             </c:otherwise>
           </c:choose>><i class="fa fa-eye"></i></a>
         <a title="打印患者报告" 
           <c:choose>
             <c:when test="${task.period ==2 }">
               target="_blank"
               href="<%=request.getContextPath()%>/report/printBSIReport?projectId=${task.projectId }&dataKey=${task.dataKey }&appId=${task.appId }&templateType=print_patient"
             </c:when>
             <c:otherwise>
             class="disabled"  disabled="disabled"
             </c:otherwise>
           </c:choose>><i class="fa fa-print"></i></a>
         <a title="共享报告" href="javascript:void(0)"><i class="fa fa-share-square-o"></i></a>
       </td>
    </tr>
  </tbody>
</table>
<%@ include file="pagination.jsp" %>
<%@ include file="statistic.jsp" %>