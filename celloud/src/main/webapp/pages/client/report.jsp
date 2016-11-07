<%@page import="com.celloud.constants.Bank"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<link href="<%=request.getContextPath()%>/css/rocky_main.min.css" rel="stylesheet">
<div class="report-content" id="report-content">
    
</div>
<script>
$("#report-content").load(window.CONTEXT_PATH + "/report/rocky/data/report", {
  dataKey : '16102704872886',
  appId : 123,
  projectId : 16768
});
</script>