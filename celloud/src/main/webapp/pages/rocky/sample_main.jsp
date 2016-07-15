<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<div class="sample row">
  <div class="col-md-3 info">
    <p>* 请持条码枪扫描样品管上的条码</p>
    <img src="<%=request.getContextPath() %>/images/icon/sample_scan.jpg">
    <p>无条码样品请按以下方式操作：<br>
    1. 在样品管上记录样品病历号<br>
    2. 将病历号输入上面窗口后回车
    </p>
  </div>
  <div class="col-md-9 list">
    <form id="sample-form" method="post">
      <table class="table table-main">
	    <thead>
	      <tr>
	        <th>序号</th>
	        <th>样品编号</th>
	        <th>更新时间</th>
	        <th>操作</th>
	      </tr>
	    </thead>
	    <tbody id="sample-list-tbody">
	    </tbody>
      </table>
    </form>
  </div>
</div>