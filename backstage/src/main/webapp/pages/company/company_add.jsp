<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!-- Basic Setup -->
<div class="panel panel-default">
    <div class="panel-heading">
        <h3 class="panel-title">添加公司</h3>
        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
    </div>
    </div>
    <div class="panel-body">
        <form role="form" class="form-horizontal" id="companyForm">
            
            <div class="form-group">
                <label class="col-sm-2 control-label" for="companyName">公司名称</label>
                
                <div class="col-sm-10">
                    <input type="text" class="form-control" id="companyName" name="companyName" placeholder="公司名称">
                </div>
            </div>
            
            <div class="form-group">
                <label class="col-sm-2 control-label" for="englishName">英文名称</label>
                <div class="col-sm-10">
                    <input type="text" class="form-control" id="englishName" name="englishName" placeholder="英文名称">
                </div>
            </div>
            <div class="form-group">
                <label class="col-sm-2 control-label">医院Logo</label>
                <div class="col-sm-10">
                    <div class="companyIcon-input-hidden"></div>
                    <div class="img-thumbnail-inline"></div>
                    <img id="companyIconUploading" class="img-thumbnail hide" style="height: 60px;margin-right: 10px;" src="images/icon/loading.jpg">
                    <a class="btn btn-default btn-lg" id="uploadCompanyIconBtn">
                        <i class="fa fa-plus"></i>
                    </a>
                    <div id="companyIconContainer"></div>
                </div>
            </div>
            <div class="form-group has-error" id="warning-group" style="color: #a94442">
                 <label class="col-sm-2 control-label">WARNING：</label>
                 <div class="col-sm-9">
                     <p class="form-control-static">Your browser doesn't have Flash, Silverlight or HTML5 support.</p>
                 </div>
             </div>
            <div class="form-group">
                <label class="col-sm-2 control-label" for="address">打印地址</label>
                <div class="col-sm-10">
                    <input type="text" class="form-control" id="address" name="address" placeholder="打印地址">
                </div>
            </div>
            <div class="form-group">
                <label class="col-sm-2 control-label" for="addressEn">英语地址</label>
                <div class="col-sm-10">
                    <input type="text" class="form-control" id="addressEn" name="addressEn" placeholder="英语地址">
                </div>
            </div>
            <div class="form-group">
                <label class="col-sm-2 control-label" for="zipCode">邮编</label>
                <div class="col-sm-10">
                    <input type="text" class="form-control" id="zipCode" name="zipCode" placeholder="邮编">
                </div>
            </div>
            <div class="form-group">
                <label class="col-sm-2 control-label" for="tel">联系电话</label>
                <div class="col-sm-10">
                    <input type="text" class="form-control" id="tel" name="tel" placeholder="联系电话">
                </div>
            </div>
            <div class="form-group">
                <label class="col-sm-2 control-label">所在地区</label>
                
                <div class="row col-sm-10">
                <div class="col-sm-5">
                    <select class="form-control col-sm-5" name="province" onChange="company.changeProvince(this.value,false,'add-city')">
                        <option value=''>--请选择省份--</option>
                        <c:forEach items="${provinces }" var="province">
                            <option value="${province }">${province }</option>
                        </c:forEach>
                    </select>
                </div>
                <div class="col-sm-5">
                    <select class="form-control col-sm-5 invisible" id="add-city" name="city">
                    </select>
                </div>
                </div>
            </div>
             <div class="form-group">
                <label class="col-sm-2 control-label" for="addressDetail">详细地址</label>
                <div class="col-sm-10">
                    <input type="text" class="form-control" id="addressDetail" name="addressDetail" placeholder="详细地址">
                </div>
            </div>
            <div class="form-group-separator"></div>
            <div class="form-group">
                <div class="col-sm-10 text-center">
                    <button type="button" class="btn btn-success" onclick="company.saveCompany()">添加</button>
                    <button type="reset" class="btn btn-white">重置</button>
                </div>
            </div>
        </form>
        
    </div>
</div>
<script type="text/javascript" src="<%=request.getContextPath()%>/plugins/plupload-2.1.2/plupload.full.min.js"></script>