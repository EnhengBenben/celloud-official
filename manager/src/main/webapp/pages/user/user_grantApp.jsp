<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div class="panel panel-default">
  <div class="panel-heading">
        <h3 class="panel-title">修改用户App权限</h3>
        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
    </div>
  <div class="panel-body">
      <form role="form" class="form-horizontal" id="grantForm">
           <div class="form-group">
               <input type="hidden" name="userId" value="${userId }">
           </div>
           <div class="form-group">
               <label class="col-sm-2 control-label" for="deptName">App<font color="red">*</font></label>
               <div class="col-sm-10" id="email-appIds">
                <c:forEach items="${companyApps }" var="companyApp">
                    <c:set value="false" var="b" />
                    <c:set value="0" var="isAdd" />
                    <c:forEach items="${userApps }" var="userApp">
                        <c:if test="${companyApp.appId == userApp.appId }">
	                        <c:set value="true" var="b" />
	                        <c:set value="${userApp.isAdd }" var="isAdd" />
                        </c:if>
                    </c:forEach>
                    <label class='checkbox-inline'><input name='appIdArray' type='checkbox' ${b?'checked':'' } value="${companyApp.appId }-${isAdd }" onclick="user.grantTip(this,${isAdd})">${companyApp.appName }</label>
                </c:forEach>
                <br/>
                <span class="help-inline text-danger"></span>
               </div>
           </div>
           <div class="form-group-separator"></div>
           <div class="form-group">
               <div class="col-sm-10 text-center">
                   <button type="button" class="btn btn-success" id="send" onclick="user.grantApp()">确定</button>
                   <button type="reset" class="btn btn-white" data-dismiss="modal" aria-label="Close">取消</button>
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
                  placeholder : '请选择公司',
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
          allowClear : true,
          maximumSelectionLength: 1
      })
  });
  </script>
</div>