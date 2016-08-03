<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div class="panel panel-default">
  <div class="panel-heading">
        <h3 class="panel-title">金钱赠予</h3>
        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
    </div>
  <div class="panel-body">
      <form role="form" class="form-horizontal" id="moneyGivenForm">
           <div class="form-group">
               <input type="hidden" id="givenUserId" value="${userId }">
           </div>
           <div class="form-group">
               <label class="col-sm-5 control-label">您拥有余额</label>
               <div class="col-sm-7">
               	<input id="balances" value="${balances }" readonly="readonly">
               </div>
           </div>
           <div class="form-group">
               <label class="col-sm-5 control-label">赠予${username }金额</label>
               <div class="col-sm-7">
               	<input id="givenNumber">
               	<span class="help-inline text-danger"></span>
               </div>
           </div>
           <div class="form-group-separator"></div>
           <div class="form-group">
               <div class="col-sm-10 text-center">
                   <button type="button" class="btn btn-success" onclick="user.moneyGiven()">确定</button>
                   <button type="reset" class="btn btn-white" data-dismiss="modal" aria-label="Close">取消</button>
               </div>
           </div>
       </form>
  </div>
</div>
