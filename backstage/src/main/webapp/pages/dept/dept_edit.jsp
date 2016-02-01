<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!-- Basic Setup -->
<div class="panel panel-default">
    <div class="panel-heading">
        <h3 class="panel-title">修改部门信息</h3>
        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
    </div>
    </div>
    <div class="panel-body">
        <form role="form" class="form-horizontal" id="deptForm">
            <input type="hidden" value="${dept.deptId }" name="deptId">
            <div class="form-group">
                <label class="col-sm-2 control-label" for="deptName">部门名称<font color="red">*</font></label>
                <div class="col-sm-10">
                    <input type="text" class="form-control" data-rule-required="true" id="deptName" name="deptName" value="${dept.deptName }" placeholder="部门名称">
                </div>
            </div>
            
            <div class="form-group">
                <label class="col-sm-2 control-label" for="englishName">英语名称<font color="red">*</font></label>
                <div class="col-sm-10">
                    <input type="text" class="form-control" data-rule-required="true" id="englishName" name="englishName" value="${dept.englishName }" placeholder="英语名称">
                </div>
            </div>
            <div class="form-group">
                <label class="col-sm-2 control-label" for="tel">电话</label>
                <div class="col-sm-10">
                    <input type="text" class="form-control" data-rule-isPhone="true" id="tel" name="tel" value="${dept.tel }" placeholder="电话">
                </div>
            </div>
            <div class="form-group">
                <label class="col-sm-2 control-label">部门Logo</label>
                <div class="col-sm-10">
                    <div class="deptIcon-input-hidden"><c:if test="${not empty dept.deptIcon ||fn:trim(dept.deptIcon)!='' }"><input type="hidden" name="deptIcon" value="${dept.deptIcon }"></c:if></div>
                    <div class="img-thumbnail-inline"><c:if test="${not empty dept.deptIcon ||fn:trim(dept.deptIcon)!='' }"><img src="dept/icon?file=${dept.deptIcon }" class="img-thumbnail" style="height: 60px; margin-right: 10px;"></c:if></div>
                    <img id="deptIconUploading" class="img-thumbnail hide" style="height: 60px;margin-right: 10px;" src="images/icon/loading.jpg">
                    <a class="btn btn-default btn-lg" id="uploadDeptIconBtn">
                        <i class="fa fa-plus"></i>
                    </a>
                    <div id="deptIconContainer"></div>
                </div>
            </div>
            
            <div class="form-group">
                <label class="col-sm-2 control-label">所属于公司<font color="red">*</font></label>
                
                <div class="row col-sm-10">
                    <select class="form-control col-sm-5" name="companyId" data-rule-required="true" >
                        <option value=''>--请选择--</option>
                        <c:forEach items="${companyList }" var="company">
                            <option value="${company.companyId }" <c:if test="${dept.companyId==company.companyId ||companyId==company.companyId }">selected</c:if>>${company.companyName }</option>
                        </c:forEach>
                    </select>
                </div>
            </div>
            <div class="form-group-separator"></div>
            <div class="form-group">
                <div class="col-sm-10 text-center">
                    <button type="submit" class="btn btn-success">保存</button>
                    <button type="reset" class="btn btn-white">重置</button>
                </div>
            </div>
        </form>
        
    </div>
</div>
<script type="text/javascript" src="<%=request.getContextPath()%>/plugins/plupload-2.1.2/plupload.full.min.js"></script>
<script type="text/javascript">
$(function(){
    $("#deptForm").validate({
        submitHandler:function(form) {      
            $(form).ajaxSubmit({
                url:"dept/save",
                success:function(responseText){
                    if(responseText>0){
                        $("#dept-editModal").modal("hide");
                        alert("成功");
                        dept.getDeptAsync(dept.currentPage);
                    }
                }
            });     
         }
    });
});
</script>