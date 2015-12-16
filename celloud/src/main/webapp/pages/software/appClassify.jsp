<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
      <c:choose>
  		<c:when test="${sclassifys.size()>0 }">
    	  <c:forEach items="${sclassifys}" var="sc" varStatus="status">
	  		<c:if test="${classifyAppMap[sc.classifyId].size()>0}">
  	    	  <div class="y-row" style="padding-bottom: 20px;"  data-spm="17">
	      		<div class="common-normal common-slide common-normals">
	        	  <div class="normal-tit normal-title">
	         		<p class="link"><a class="bc-a-tit" target="_blank" href="javascript:toMoreApp(${sc.classifyPid },${sc.classifyId },1,1)" title="" data-step="2" data-intro="" data-img="checkapp.png">获取更多&gt;</a></p>
	         		<h4>${sc.classifyName }</h4>
	        	  </div>
	        	  <div class="normal-slide">
	          		<div class="J_tab">
	            	  <div class="tab-content">
	              		<div class="tab-pannel">
				    	  <ul class="slide-con guess-like-box">
				  	 	    <c:forEach items="${classifyAppMap[sc.classifyId]}" var="app" varStatus="status">
					    	  <c:if test="${status.index<5}">
			              		<li>
		                    	  <div class="detail">
		                      		<div class="picbox">
		                        	  <div class="pic">
		                          		<p><a href="javascript:toAppDetail(${app.softwareId })"><img src="<%=request.getContextPath()%>/images/app/${app.pictureName}"></a></p>
		                        	  </div>
		                      		</div>
		                      		<p class="version">${app.softwareName }</p>
		                      		<p class="company">${app.createDate }</p>
		                    	  </div>
		                    	  <div class="button" style="color: #ff6600"> <a href="javascript:toAppDetail(${app.softwareId })">查看详情<i></i></a> </div>
		                    	  <span class="app_mark"></span>
		                  		</li>
					    	  </c:if>
					  		</c:forEach>
	                	  </ul>
	              		</div>
	            	  </div>
	          		</div>
	        	  </div>
	      		</div>
	    	  </div>
	  		</c:if>
    	  </c:forEach>
  		</c:when>
	  </c:choose>