<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<input type="hidden" id="app-detail-appId" value="${app.appId }">
<div class="item-list">
  <div class="item-left">
	<div class="product-info-top-wrap">
  	  <div class="product-info-hd clearfix">
        <div class="view">
          <img src="<%=request.getContextPath()%>/images/app/${app.pictureName}">
        </div>
        <div class="itemInfo">
          <h5>${app.appName }
            <span id="manageAppBtns" style="display:inline-block;position:relative;margin-left:20px;" data-step="2" data-intro="" data-position="bottom" data-img="changedApp.png">
              <c:choose>
                <c:when test="${app.classifyNames.contains('工具软件') }">
                  <a class="btn btn-celloud-success btn-flat" href="${app.address }" target="_blank"><i class="fa fa-plus"></i>&nbsp;点击使用</a>
                </c:when>
                <c:otherwise>
                  <c:choose>
                    <c:when test="${app.isAdded==0 }">
                      <a class="btn btn-celloud-success btn-flat" href="javascript:void(0);" onclick="appStore.addApp(${app.appId });" id="toAddApp"><i class="fa fa-plus"></i>&nbsp;添加</a>
                    </c:when>
                    <c:otherwise>
                      <a class="btn btn-celloud-close btn-flat" href="javascript:void(0);" onclick="appStore.removeApp(${app.appId });" id="toAddApp"><i class="fa fa-minus"></i>&nbsp;取消添加</a>
                    </c:otherwise>
                  </c:choose>
                </c:otherwise>
              </c:choose>
          </span>
         </h5>
          <div class="unlinedate">上线时间：<span class="date"><fmt:formatDate value="${app.createDate }" type="date"/></span></div>
          <div class="intro">
            <ul>
              <li>分类：<span>${app.classifyNames}</span></li>
              <li>提供者：<span><c:choose>
                        <c:when test="${app.companyName==null }">上海华点云生物科技有限公司</c:when>
                        <c:otherwise>${app.companyName }</c:otherwise>
                      </c:choose></span>
              </li>
            </ul>
          </div>
        </div>
      </div>
      <div class="select-one">
        <c:choose>
            <c:when test="${empty app.discountPrice }">
                <div class="price-con">
		            <span class="text">
		                <span id="app-price-label">金额</span>：
		                <em id="total-price"><c:if test="${empty app.price }">0</c:if>${app.price }</em>
		                <span style="font-size:14px;color:#f60">C</span>
		            </span>
		        </div>
            </c:when>
            <c:otherwise>
                <div id="app-old-price" class="price-con">
		            <span class="text">原价：<span style="text-decoration: line-through;" class="y-light-grey"><span id="old-price">${app.price }</span>C</span> </span>
		        </div>
		        <div class="price-con">
		            <span class="text">
		                <span id="app-price-label">促销</span>：
		                <em id="total-price">${app.discountPrice }</em>
		                <span style="font-size:14px;color:#f60">C</span>
		            </span>
		            <span id="app-cuxiao" class="cuxiao">限时促销</span>
		        </div>
            </c:otherwise>
        </c:choose>
      </div>
    </div>
  </div>
</div>
<div class="item-intro">
  <div class="link">
    <div class="inner-link">
      <ul id="toAppMoreDetailUl">
        <li class="select" style="border-left:0;" id="toAppIntro" onclick="appStore.toAppMoreDetail('toAppIntro')"><a href="#1">产品介绍</a></li>
        <li style="border-right: 0;" class="" id="toAppScreeen" onclick="appStore.toAppMoreDetail('toAppScreeen')"><a href="#2">报告截图</a></li>
      </ul>
    </div>
  </div>
  <div class="box-icon" id="1">
    <h5>应用介绍</h5>
    <p>${app.appDoc }</p>
  </div>
  <div class="box-icon" id="2">
	<h5>报告截图</h5>
	<div class="comment-list2">
	  <div class="app-detail-imgs">
	    <c:choose>
	      <c:when test="${screenList.size()>0 }">
	        <div id="appScreen" class="carousel slide" data-ride="carousel">
                 <ol class="carousel-indicators app-detail-slider">
                   <c:forEach items="${screenList }" var="screen" varStatus="size">
                  <li data-target="#appScreen" data-slide-to="${size.index}" class="<c:if test='${size.index==0}'>active</c:if>"></li>
                   </c:forEach>
                 </ol>
                 <div class="app-detail-imgcontainer">
                 <div class="carousel-inner">
                   <c:forEach items="${screenList }" var="screen" varStatus="size">
                    <div class="item <c:if test='${size.index==0}'>active</c:if>">
                      <img style="height:200px;width:100%" src="<%=request.getContextPath()%>/images/screenshot/${screen.screenName}" alt="First slide">
                    </div>
                   </c:forEach>
                 </div>
                 <a class="left carousel-control" href="#appScreen" data-slide="prev">
                   <span class="fa fa-angle-left"></span>
                 </a>
                 <a class="right carousel-control" href="#appScreen" data-slide="next">
                   <span class="fa fa-angle-right"></span>
                 </a>
                 </div>
                </div>
	      </c:when>
	      <c:otherwise>
	         	无截图
	      </c:otherwise>
	    </c:choose>
      </div>
    </div>
  </div>
</div>

