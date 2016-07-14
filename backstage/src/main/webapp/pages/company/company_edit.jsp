<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!-- Basic Setup -->
<div class="panel panel-default">
    <div class="panel-heading">
        <h3 class="panel-title">修改公司信息</h3>
        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
    </div>
    <div class="panel-body">
        <form role="form" class="form-horizontal" id="companyForm">
            <input type="hidden" name="companyId" value="${company.companyId }">
            <div class="form-group">
                <label class="col-sm-2 control-label" for="companyName">公司名称<font color="red">*</font></label>
                <div class="col-sm-10">
                    <input type="text" class="form-control" data-rule-required="true" id="companyName" name="companyName" value="${company.companyName }" placeholder="公司名称">
                </div>
            </div>
            
            <div class="form-group">
                <label class="col-sm-2 control-label" for="englishName">英文名称<font color="red">*</font></label>
                <div class="col-sm-10">
                    <input type="text" class="form-control" data-rule-required="true" id="englishName" name="englishName" value="${company.englishName }" placeholder="英文名称">
                </div>
            </div>
            <div class="form-group">
                <label class="col-sm-2 control-label">医院Logo</label>
                <div class="col-sm-10">
                    <div class="companyIcon-input-hidden"><c:if test="${not empty company.companyIcon ||fn:trim(company.companyIcon)!='' }"><input type="hidden" name="companyIcon" value="${company.companyIcon }"></c:if></div>
                    <div class="img-thumbnail-inline"><c:if test="${not empty company.companyIcon ||fn:trim(company.companyIcon)!='' }"><img src="company/icon?file=${company.companyIcon }" class="img-thumbnail" style="height: 60px; margin-right: 10px;"></c:if></div>
                    <img id="companyIconUploading" class="img-thumbnail hide" style="height: 60px;margin-right: 10px;" src="images/icon/loading.jpg">
                    <a class="btn btn-default btn-lg" id="uploadCompanyIconBtn">
                        <i class="fa fa-plus"></i>
                    </a>
                    <div id="companyIconContainer"></div>
                </div>
            </div>
            <div class="form-group">
                <label class="col-sm-2 control-label" for="address">打印地址<font color="red">*</font></label>
                <div class="col-sm-10">
                    <input type="text" class="form-control" data-rule-required="true" id="address" name="address" value="${company.address }" placeholder="打印地址">
                </div>
            </div>
            <div class="form-group">
                <label class="col-sm-2 control-label" for="addressEn">英语地址<font color="red">*</font></label>
                <div class="col-sm-10">
                    <input type="text" class="form-control" data-rule-required="true" id="addressEn" name="addressEn" value="${company.addressEn }" placeholder="英语地址">
                </div>
            </div>
            <div class="form-group">
                <label class="col-sm-2 control-label" for="zipCode" >邮编</label>
                <div class="col-sm-10">
                    <input type="text" class="form-control" id="zipCode" name="zipCode" data-rule-zipcode="true" value="${company.zipCode }" placeholder="邮编">
                </div>
            </div>
            <div class="form-group">
                <label class="col-sm-2 control-label" for="tel" data-rule-isPhone="true">联系电话</label>
                <div class="col-sm-10">
                    <input type="text" class="form-control isPhone" id="tel" name="tel" value="${company.tel }" placeholder="联系电话">
                </div>
            </div>
            <div class="form-group">
                <label class="col-sm-2 control-label">所在地区<font color="red">*</font></label>
                
                <div class="row col-sm-10">
                <div class="col-sm-5">
                    <select class="form-control col-sm-5" data-rule-required="true" data-msg-required="该项为必选项，请选择。"  name="province" onChange="company.changeProvince(this.value,false,'add-city')" >
                        <option value=''>--请选择省份--</option>
                        <c:forEach items="${provinces }" var="province">
                            <option value="${province }" <c:if test="${province==company.province }">selected</c:if>>${province }</option>
                        </c:forEach>
                    </select>
                </div>
                <div class="col-sm-5">
                    <select class="form-control col-sm-5  <c:if test="${empty citys }">invisible</c:if>" id="add-city" name="city" data-rule-required="true" data-msg-required="该项为必选项，请选择。">
                        <option value=''>--请选择城市--</option>
                        <c:if test="${not empty citys }">
	                        <c:forEach items="${citys }" var="city">
	                            <option value="${city }" <c:if test="${city==company.city }">selected</c:if>>${city }</option>
	                        </c:forEach>
                        </c:if>
                    </select>
                </div>
                </div>
            </div>
             <div class="form-group">
                <label class="col-sm-2 control-label" for="addressDetail">详细地址<font color="red">*</font></label>
                <div class="col-sm-10">
                    <input type="text" class="form-control" id="addressDetail" name="addressDetail" value="${company.addressDetail }" placeholder="详细地址" data-rule-required="true">
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
<script type="text/javascript">
$(function(){
	$("#companyForm").validate({
		submitHandler:function(form) {      
            $(form).ajaxSubmit({
            	url:"company/save",
            	success:function(responseText){
                    if(responseText>0){
                        $("#company-editModal").modal("hide");
                        alert("成功");
                        
                    }
                }
            });     
         }
	});
	$('#company-editModal').on('hidden.bs.modal', function (e) {//此事件在模态框被隐藏（并且同时在 CSS 过渡效果完成）之后被触发。
		company.getCompany(company.currentPage);
	});
});
</script>
