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
               <input type="hidden" name="appCompanyId" value="${appCompanyId }">
           </div>
           <div class="form-group">
               <label class="col-sm-2 control-label" for="tips"></label>
               <div class="col-sm-10" id="tips">
                   <p style="color: red;">提示:每个医院下只能添加一个管理员</p>
               </div>
           </div>
           <div class="form-group">
               <label class="col-sm-2 control-label" for="role">模块</label>
               <div class="col-sm-10" id="email-roleIds">
                   <c:if test="${not empty roleList }">
		                <c:forEach items="${roleList }" var="role">
		                    <label class='checkbox-inline'><input name='roleIdArray' type='checkbox' <c:if test="${role.code!='hospitalmanager' }">checked='checked'</c:if> value='${role.id }'>${role.name }</label>
		                </c:forEach>
		           </c:if>
		           <c:if test="${empty roleList }">
		                 <label class='text-inline'>暂无模块</label>
		           </c:if>
               </div>
           </div>
           <div class="form-group">
               <label class="col-sm-2 control-label" for="deptName">App<font color="red">*</font></label>
               <div class="col-sm-10" id="email-appIds">
                <c:forEach items="${appList }" var="app">
	                <label class='checkbox-inline'><input name='appIdArray' type='checkbox' checked='checked' value='${app.appId }'>${app.appName }</label>
                </c:forEach>
                <br/>
               <span class="help-inline text-danger"></span>
               </div>
           </div>
           
           <div class="form-group">
               <label class="col-sm-2 control-label" for="englishName">单位<font color="red">*</font> </label>
               <div class="col-sm-10">
	               <select class="form-control" id="companyList" name="companyId" style="width: 100%" multiple="multiple" onchange="user.changeDeptByCompanyId(this)" >
	               </select>
	               <span class="help-inline text-danger"></span>
               </div>
           </div>
           <div class="form-group" id="dept">
               <label class="col-sm-2 control-label" for="deptId">部门<font color="red">*</font> </label>
               <div class="col-sm-10">
                   <select class="form-control" name="deptId" id="deptList" style="width: 100%" multiple="multiple" >
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
           <div class="form-group hide">
               <input type="hidden" name="role" value="0">
           </div>
           <div class="form-group-separator"></div>
           <div class="form-group">
               <div class="col-sm-10 text-center">
                   <button type="button" class="btn btn-success" id="send" onclick="user.sendEmail()">发送邮件</button>
                   <button type="reset" class="btn btn-white" data-dismiss="modal" aria-label="Close">取消</button>
               </div>
           </div>
           <div class="form-group">
               <label class="col-sm-2 control-label"></label>
               <div class="col-sm-10">
                   <input type="hidden" class="form-control">
                   <span class="help-inline text-success" id="sendEmailTip"></span>
               </div>
           </div>
           
       </form>
  </div>
  <script type="text/javascript">
  $(function(){
	  $.ajax({
          url : "${pageContext.request.contextPath }/company/getAllToSelect",
          type : 'post',
          dataType : 'text',
          success : function(data) {
              // 从后台获取json数据
              var json = eval('(' + data + ')');
              $("#companyList").select2({
                  data : json,
                  tags : true,
                  placeholder : '请选择单位',
                  language : 'zh-CN',
                  allowClear : true,
                  maximumSelectionLength: 1
              })
          },
          error : function(data) {

          }
      });
	  $("#deptList").select2({
          tags : true,
          placeholder : '请选择部门',
          language : 'zh-CN',
          allowClear : true,
          maximumSelectionLength: 1
      })
  });
  </script>
</div>