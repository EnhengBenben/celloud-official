<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<script src="js/data_siteInfo.js"></script>
<div class="widget-header widget-header-flat">
     <h3 class="header smaller lighter green">医院名称</h3>
</div>
<div class="col-xs-11 table-div">
    <c:if test="${siteInfo != null && fn:length(siteInfo) > 0 }">
        <div class="table-responsive " id="siteInfoDiv">
            <table id="siteInfoList" class="table table-striped table-bordered table-hover">
                <thead>
                    <tr>
                        <th>文件名称</th>
                        <th>上传时间</th>
                        <th>上传账号</th>
                        <th>所属医院</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach items="${siteInfo }" var="siteInfo">
                        <tr>
                            <td>${siteInfo.value['fileName'] }</td>
                            <td>${siteInfo.value['createDate'] }</td>
                            <td>${siteInfo.value['userName'] }</td>
                            <td>${siteInfo.value['companyName'] }</td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </div>
    </c:if>
    <!-- PAGE CONTENT ENDS -->
</div>
<!-- /.col -->

