<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:if test="${samples.size()>0 }">
  <c:forEach var="sample" items="${samples }" varStatus="size">
    <tr>
       <td>${samples.size() - size.index }<input type="hidden" name="sampleIds" value="${sample.sampleId }"></td>
       <td>${sample.sampleName }</td>
       <td><fmt:formatDate value="${sample.createDate }" type="both" dateStyle="long" pattern="yyyy-MM-dd HH:mm:ss" /></td>
       <td><a data-click="del-sample" data-id="${sample.sampleId }" href="javascript:void(0)"><i class="fa fa-times-circle" aria-hidden="true"></i></a></td>
    </tr>
  </c:forEach>
</c:if>
<c:if test="${samples.size()==0 }">
  <tr>
     <td colspan="4" class="table-null">请按左侧提示进行操作</td>
  </tr>
</c:if>