<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<div class="panel panel-default">
  <div class="panel-heading">
    <h3 class="panel-title">账户充值</h3>
    <div class="panel-options">
        <form class="form-inline">
         <div class="form-group">
          <input type="text" class="form-control" data-rule-required="true" id="condition" name="condition" value="${condition }" placeholder="请输入关键字">
         </div>
         <button id="search-btn" class="btn btn-warning" type="button" style="margin-bottom:0;">检索</button>
       </form>
     </div>
  </div>
  <div class="panel-body">
    <table class="table table-bordered table-striped" cellspacing="0" width="100%">
        <thead>
            <tr>
                <th width="100px">用户名</th>
                <th width="85px">邮箱</th>
                <th width="85px">所属医院</th>
                <th width="70px">账户余额</th>
                <th width="60px">操作</th>
            </tr>
        </thead>
        <tbody>
            <c:forEach items="${pageList.datas }" var="user">
                <tr>
                    <td>${user.username }</td>
	                <td>${user.email }</td>
	                <td>${user.companyName } </td>
	                <td>${user.balances }</td>
	                <td><a href="javascript:void(0)" data-user="${user.userId }" data-click="to-recharge">充值</a></td>
                </tr>
            </c:forEach>
        </tbody>
    </table>
    <%@ include file="../pagination.jsp" %>
  </div>
</div>
<div class="modal fade bs-example-modal-lg" id="recharge-modal" tabindex="-1" role="dialog" aria-labelledby="myLargeModalLabel">
  <div class="modal-dialog modal-lg">
    <div class="modal-content">
		<div class="panel panel-default">
		    <div class="panel-heading">
		        <h3 class="panel-title">账户充值</h3>
		        <div class="alert alert-warning hide" id="recharge-alert">
                    <strong>Warning!</strong> <span id="recharge-info"></span>
                </div>
		        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
		    </div>
		    <div class="panel-body">
		        <form class="form-horizontal" id="rechargeForm" >
		            <input type="hidden" name="userId" id="userid-hide">
		            <div class="form-group">
		                <label class="col-sm-2 control-label">充值金额<font color="red">*</font></label>
		                <div class="col-sm-10">
		                   <input type="text" class="form-control" name="amount" placeholder="请正确输入金额" required="required">
		                </div>
		            </div>
		            <div class="form-group">
                        <label class="col-sm-2 control-label">户名<font color="red">*</font></label>
                        <div class="col-sm-10">
                           <input type="text" class="form-control" name="accountName" required="required">
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-2 control-label">账户<font color="red">*</font></label>
                        <div class="col-sm-10">
                           <input type="text" class="form-control" name="accountNo" required="required">
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-2 control-label">开户银行<font color="red">*</font></label>
                        <div class="col-sm-10">
                           <input type="text" class="form-control" name="bank" required="required">
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-2 control-label">交易流水</label>
                        <div class="col-sm-10">
                           <input type="text" class="form-control" name="tradeNo">
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-2 control-label">电子回单</label>
                        <div class="col-sm-10">
                           <input type="text" class="form-control" name="backNo">
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-2 control-label">摘要</label>
                        <div class="col-sm-10">
                           <input type="text" class="form-control" name="summary">
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-2 control-label">验证码<font color="red">*</font></label>
                        <div class="col-sm-10">
                           <div class="yzm">
	                            <input type="text" class="yzm input-bottom" placeholder="验证码" id="captcha"
	                                name="kaptchaCode" value="${requestScope.kapcode }" required="required"/>
	                            <img title="看不清，换一张"
	                                src="<%=request.getContextPath()%>/kaptcha.jpg" id="kaptchaImage"
	                                alt="验证码" class="validateCode" style="cursor: pointer;" />
	                        </div>
                        </div>
                    </div>
		            <div class="form-group-separator"></div>
		            <div class="form-group">
		                <div class="col-sm-10 text-center">
		                    <a id="commit-recharge" class="btn btn-success">充值</a>
		                    <button type="reset" class="btn btn-white">重置</button>
		                </div>
		            </div>
		        </form>
		    </div>
		</div>
    </div>
  </div>
</div>