<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div class="panel panel-default">
  <div class="panel-heading">
        <h3 class="panel-title">新增用户</h3>
        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
    </div>
  <div class="panel-body">
      <form role="form" class="form-horizontal" id="emailForm">
           <div class="form-group">
               <label class="col-sm-2 control-label" for="deptName">App提供者<font color="red">*</font></label>
               <div class="col-sm-10">
	               <select class="form-control" name="appCompanyId" onchange="user.changeAppCompany(this,'email-appIds')">
	                  <option value=''>--请选择--</option>
	                   <c:forEach items="${companyList }" var="company">
	                       <option value="${company.companyId }" >${company.companyName }</option>
	                   </c:forEach>
	               </select>
	               <span class="help-inline text-danger"></span>
               </div>
           </div>
           <div class="form-group hide">
               <div class="col-sm-10 col-sm-offset-2 " id="email-appIds">
                   <c:forEach items="${publicApp }" var="app">
                   <label class="checkbox-inline">
					  <input type="checkbox" value="${app.appId }" checked='checked' name="appIdArray">${app.appName }
					</label>
                       
                   </c:forEach>
               </div>
           </div>
           
           <div class="form-group">
               <label class="col-sm-2 control-label" for="englishName">公司<font color="red">*</font> </label>
               <div class="col-sm-10">
	               <select class="form-control" onChange="user.changeCompany(this,'user-deptId')" name="companyId">
	                   <option value=''>--请选择--</option>
	                   <c:forEach items="${companyList }" var="company">
	                       <option value="${company.companyId }" >${company.companyName }</option>
	                   </c:forEach>
	               </select>
	               <span class="help-inline text-danger"></span>
               </div>
           </div>
           <div class="form-group">
               <label class="col-sm-2 control-label" for="deptId">部门<font color="red">*</font> </label>
               <div class="col-sm-10">
                   <select class="form-control" name="deptId" id="user-deptId">
                       <option value=''>--请选择--</option>
                   </select>
                   <span class="help-inline text-danger"></span>
               </div>
           </div>
           <div class="form-group">
               <label class="col-sm-2 control-label" for="tel">邮箱<font color="red">*</font> </label>
               <div class="col-sm-10">
                   <input type="text" class="form-control" id="emailArray" name="emailArray" placeholder="邮箱 ">
                   <span class="help-inline text-danger"></span>
               </div>
           </div>
           
           <div class="form-group-separator"></div>
           <div class="form-group">
               <div class="col-sm-10 text-center">
                   <button type="button" class="btn btn-success" id="send" onclick="user.sendEmail()">发送邮件</button>
                   <button type="reset" class="btn btn-white" data-dismiss="modal" aria-label="Close">取消</button>
               </div>
           </div>
       </form>
  </div>
</div>