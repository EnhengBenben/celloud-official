<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div class="y-row clearfix">
  <div id="JS_listBox" class="page-list">
    <ul class="act-list">
      <c:choose>
        <c:when test="${expenseList.size()>0 }">
          <c:forEach items="${appPageList.datas }" var="app" varStatus="status">
            <li class="list-item show">
              <div class="item-box">
                  <div class="box-btn">
                    <p>
                      <span class="sync_price" id="price_cmjz000559" code="cmjz000559" servicepackage="" inquerytype="cloudmarket">${app.price }</span>
                      <font id="price_unit_cmjz000559">&nbsp;C</font>
                    </p>
                    <a class="xq" href="javascript:appStore.toAppDetail(${app.appId })" style="display: none;">查看详情</a>
                  </div>
                  <div class="box-star">
                    <div class="unlinedate"> 上线时间：
                       <span class="date"><fmt:formatDate value="${app.createDate }" type="date"/></span>
                    </div>
                    <div class="service-com">提供者：
                      <c:choose>
                        <c:when test="${app.companyName==null }">上海华点云生物科技有限公司</c:when>
                        <c:otherwise>${app.companyName }</c:otherwise>
                      </c:choose>
                    </div>
                  </div>
                  <div class="box-info-wrap">
                    <div class="box-pic" <c:if test="${status.first}"> data-step="2" data-intro="" data-position="right" data-img="appDetail.png" </c:if>>
                      <a href="javascript:appStore.toAppDetail(${app.appId })">
                        <img alt="产品logo" src="<%=request.getContextPath()%>/images/app/${app.pictureName}">
                      </a>
                    </div>
                    <div class="box-info">
                      <h4><a href="javascript:appStore.toAppDetail(${app.appId })">${app.appName }</a></h4>
                      <p>${app.intro }</p>
<%--                      <p class="label">标签：${app.description }</p><!-- 标签内容待定 --> --%>
                    </div>
                  </div>
              </div>
            </li>
          </c:forEach>
        </c:when>
        <c:otherwise>
          <li class="list-item show">
            <div class="list-left"></div>
            <div class="list-center">
              <div class="emptyInfo">
                <strong>暂没开放此类应用的权限，请选择其他分类!</strong>
              </div>
            </div>
            <div class="list-right"></div>
          </li>
        </c:otherwise>
      </c:choose>
    </ul>
  </div>
</div>