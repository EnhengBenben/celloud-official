<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<div class="sample-left">
  <div class="data-search">
    <input id="sample-input" class="input-sm" type="text" placeholder="扫描样本编号/病历号"/>
    <a id="sample-add-a" class="input-group-btn">添加</a>
  </div>
  <div class="sample-instructions">
    <p>* 请持条码枪扫描样品管上的条码</p>
    <img src="<%=request.getContextPath() %>/images/icon/sample_scan.jpg">
    <p>无条码样品请按以下方式操作：<br>
    1. 在样品管上记录样品病历号<br>
    2. 将病历号输入上面窗口后回车
    </p>
  </div>
</div>
<div class="sample-right">
  <div class="sample-opera">
    <div id="sample-error" class="error-tips hide">
      <p> 此样品信息已经收集过，请核查或者采集下一管样品信息！
         <a id="close-s-error" href="javascript:void(0)"><i class="fa fa-times-circle-o" aria-hidden="true"></i></a>
      </p>
    </div>
    <div class="btns">
      <button id="sample-reset" class="btn btn-cancel">取消</button>
      <button id="sample-commit-a" class="btn btn-confirm">提交样本信息</button>
    </div>
  </div>
  <div class="sample-list">
    <form id="sample-form" method="post">
      <table class="table table-main sample-table">
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