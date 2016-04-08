<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<div class="breadcrumbs" id="breadcrumbs">
    <ul class="breadcrumb">
        <li>
            <i class="icon-hospital"></i>
            <a href="#">医院统计</a>
        </li>
        <li onclick="companyCount.toCompanyBaseInfo()">医院基本信息</li>
        <li>${company.companyName }</li>
    </ul>
</div>
<div class="page-content">
    <div class="row">
        <form role="form" class="form-horizontal">
<!--         <div class="col-sm-12"> -->
            <div class="form-group">
                <label class="col-sm-2 control-label" for="companyName">公司名称</label>
                <div class="col-sm-10">
                    ${company.companyName }
                </div>
            </div>
            
            <div class="form-group">
                <label class="col-sm-2 control-label" for="englishName">英文名称</label>
                <div class="col-sm-10">
                    ${company.englishName }
                </div>
            </div>
            <div class="form-group">
                <label class="col-sm-2 control-label">医院Logo</label>
                <div class="col-sm-10">
                    <div class="img-thumbnail-inline"><c:if test="${not empty company.companyIcon ||fn:trim(company.companyIcon)!='' }"><img src="company/icon?file=${company.companyIcon }" class="img-thumbnail" style="height: 60px; margin-right: 10px;"></c:if></div>
                </div>
            </div>
            <div class="form-group">
                <label class="col-sm-2 control-label" for="address">打印地址</label>
                <div class="col-sm-10">
                    ${company.address }
                </div>
            </div>
            <div class="form-group">
                <label class="col-sm-2 control-label" for="addressEn">英语地址</label>
                <div class="col-sm-10">
                   ${company.addressEn }
                </div>
            </div>
            <div class="form-group">
                <label class="col-sm-2 control-label" for="zipCode" >邮编</label>
                <div class="col-sm-10">
                   ${company.zipCode }
                </div>
            </div>
            <div class="form-group">
                <label class="col-sm-2 control-label" for="tel" data-rule-isPhone="true">联系电话</label>
                <div class="col-sm-10">
                    ${company.tel }
                </div>
            </div>
            <div class="form-group">
                <label class="col-sm-2 control-label" for="addressDetail">详细地址</label>
                <div class="col-sm-10">
                    ${company.addressDetail }
                </div>
            </div>
            <div class="form-group">
                <label class="col-sm-2 control-label" for="addressDetail">打印模板</label>
                <div class="col-sm-10">
                    <c:forEach items="${pdfPathList }" var="pdfPath">
	                    <a href="<%=request.getContextPath()%>/templates/report/${company.companyId }/${pdfPath }" target="_blank">${pdfPath }</a><br>
                    </c:forEach>
                </div>
            </div>
        </form>
<!--         </div> -->
    </div>
</div>