<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
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
                        <th>医院名称</th>
                        <th>DataKey</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach items="${siteInfo }" var="siteInfo">
                        <tr>
                            <td>${siteInfo.value['companyName'] }</td>
                            <td>${siteInfo.value['dataKey'] }</td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </div>
    </c:if>
    <!-- PAGE CONTENT ENDS -->
</div>
<!-- /.col -->

