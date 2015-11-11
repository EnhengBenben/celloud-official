<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<div style="padding-bottom:10px;">
<input type="hidden" id="pclassifyIdHide">
<ul class="appitem">
  <c:forEach items="${classifyMap.pclassify}" var="classify">
 	 <li class="types-options big_item" name="pclassify" id="pclassify${classify.classifyId }" onclick="getAppClassify(${classify.classifyId })">${classify.classifyName }</li>
  </c:forEach>
</ul>
<ul class="appitem">
  <li class="types-options second_item" name="sclassify" id="sclassify0">全部</li>
  <c:forEach items="${classifyMap.sclassify}" var="classify">
 	 <li class="types-options second_item" name="sclassify"  id="sclassify${classify.classifyId }" onclick="getAppList(${classify.classifyId },1)">${classify.classifyName }</li>
  </c:forEach>
</ul>
</div>