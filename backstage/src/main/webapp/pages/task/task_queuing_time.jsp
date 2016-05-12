<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div class="panel panel-default">
<div class="panel-heading">
    <h3 class="panel-title">排队时间</h3>
</div>
<div class="panel-body">
    <table class="table table-bordered table-striped" cellspacing="0" width="100%">
        <thead>
            <tr>
                <th>总等待时间</th>
                <th>平均等待时间</th>
            </tr>
        </thead>
        <tbody>
            <c:if test="${empty map }">
                <tr>
                    <td colspan="2">暂无数据</td>
                </tr>
            </c:if>
            <c:if test="${not empty map }">
	            <tr>
	                <td>${map.totalSecond }</td>
	                <td>${map.avgSecond }</td>
	            </tr>
            </c:if>
        </tbody>
    </table>
</div>
</div>
<div class="modal fade bs-example-modal-lg" id="user-sendEmailModal" tabindex="-1" role="dialog" aria-labelledby="myLargeModalLabel">
      <div class="modal-dialog modal-lg">
        <div class="modal-content">
        </div>
      </div>
</div>