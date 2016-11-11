<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<div class="topbar-menu">
    <header class="common-menu">
        <div class="common-menu-logo">
            <img alt="百菌探" src="<%=request.getContextPath()%>/images/app/bsi.png">
        </div>
        <hr class="-left">
        <div id="common-menu-center" class="info">
            <div id="common-menu" class="common-menu-btn pull-left">
               <a class="item-btn upload-btn" id="to-upload-a" href="javascript:void(0)">
                   <i class="celicon my-upload-icon"></i><br>上传
                   <canvas id="upload-progress" class="upload-progress" width="64" height="64"></canvas>
               </a>
               <a class="item-btn" ng-class="{active: isActive('bsidata')}" id="to-data-a" href="${pageContext.request.contextPath }/index#/product/bsi/bsidata">
                   <i class="celicon my-data-icon"></i><br>数据
               </a>
               <a class="item-btn" ng-class="{active: isActive('bsireport')}" id="to-report-a" href="${pageContext.request.contextPath }/index#/product/bsi/bsireport">
                   <i class="celicon my-report-icon"></i><br>报告
               </a>
            </div>
        </div>
        <div id="common-menu-right" class="searchs common-menu-btn">
            <div class="data-search">
                <input id="condition-input" class="input-sm" type="text" placeholder="搜索" />
                <a id="condition-find" class="input-group-btn">
                    <i class="fa fa-search"></i>
                </a>
            </div>
        </div>
        <hr class="-right">
    </header>
</div>