<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<ul class="nav nav-tabs">
  <li class="active"><a id="toClean" href="#dataClean" data-toggle="tab">数据清理</a></li>
  <li><a id="toUpload" href="#dataUpload" data-toggle="tab">数据上传</a></li>
</ul>
<div class="tab-content">
  <div class="tab-pane active" id="dataClean"></div>
  <div class="tab-pane" id="dataUpload"></div>
  <div class="tab-pane" id="dataExport"></div>
</div>
<script>
	$(function(){
		$("#dataClean").load("admin/data.jsp");
		$.get("checkAdminSessionTimeOut.action",{},function(userName){
			if(userName==null||userName==""){
				window.location = "pages/index.jsp"; 
			}else{
				$("#dataUpload").load("admin/fileUpload_bigData.jsp");
			}
		});
	})
</script>