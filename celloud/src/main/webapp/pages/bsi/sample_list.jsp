<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:if test="${samples.size()>0 }">
  <c:forEach var="sample" items="${samples }" varStatus="size">
	<tr>
	   <td>${samples.size() - size.index }</td>
	   <td>${sample.sampleName }</td>
	   <td><fmt:formatDate value="${task.updateDate }" type="both" dateStyle="long" pattern="yyyy-MM-dd HH:mm:ss" /></td>
	   <td><a><i class="fa fa-times-circle" aria-hidden="true"></i></a></td>
	</tr>
  </c:forEach>
</c:if>
<c:if test="${samples.size()==0 }">
  <tr>
     <td colspan="4">请按左侧提示进行操作</td>
  </tr>
</c:if>