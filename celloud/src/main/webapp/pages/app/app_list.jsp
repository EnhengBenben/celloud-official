<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<link href="<%=request.getContextPath()%>/css/app.css" rel="stylesheet" type="text/css" />
<div class="pro-body">
	<div class="y-row clearfix">
	  <div class="J_single kind box box-success" style="top: 0px;">
	    <div class="list-left">分类方式：</div>
	    <div class="list-right list-kind-right">
	      <c:forEach items="${pclassifys }" var="pc">
		    <a href="javascript:appStore.toMoreApp(${pc.classifyId },0,1,0)" class="linkage<c:if test='${pc.classifyId==classifyPid ||pc.classifyId==classifyId}'> current</c:if>" id="pid${pc.classifyId }">${pc.classifyName }</a>
	      </c:forEach>
	    </div>
	  </div>
	  <div id="JS_listBox0" class="tabBox">
	    <ul data-spm="201">
	      <c:if test="${!empty sclassifys}">
		    <li class="J_single" data-spm="2">
		      <div class="list-left">分类详细：</div>
		      <div class="list-right">
		        <a href="javascript:appStore.toMoreApp(${classifyPid },0,1,0)" <c:if test='${classifyId==0 }'>class="current"</c:if> id="sid0">全部</a>
		        <c:forEach items="${sclassifys }" var="sc">
		          <a <c:if test='${sc.classifyId==classifyId }'>class="current"</c:if> href="javascript:appStore.toMoreApp(${sc.classifyPid },${sc.classifyId },1,1)" id="sid${sc.classifyId }">${sc.classifyName }</a>
		        </c:forEach>
		      </div>
		    </li>
	      </c:if>
	      <!-- <li class="J_single" data-spm="1">
	        <div class="list-left">价格：</div>
	        <div class="list-right">
	          <a class="current" target="_self" href="#">全部</a>
	          <a target="_self" href="#" >免费</a>
	        </div>
	      </li> -->
		</ul>
	  </div>
	  <ul class="sort-list" data-spm="202" id="sort-listUl">
	    <li class="current" id="defaultSort">
	      <a href="javascript:appStore.toMoreApp(${classifyPid },${classifyId },1,${classifyFloor });" id="defaultSort">默认排序</a>
	    </li>
	    <li class="" data-spm="1" id="sortByCreateDate">
	      <a target="_self" href="javascript:void(0);" id="sortByCreateDate">上线时间<i class="down"></i></a>
	    </li>
	<!--     <li class="" data-spm="2"> -->
	<!--       <a d-order="price-desc" a-order="price-asc" default="asc" target="_self" href="javascript:void(0);">价格<i class="down"></i></a> -->
	<!--     </li> -->
	<!--     <li class="" data-spm="3"> -->
	<!--         <a d-order="score-desc" a-order="score-asc" target="_self" href="javascript:void(0);">评分<i class="down"></i></a> -->
	<!--     </li> -->
	  </ul>
	  <div id="JS_listBox" class="page-list">
	    <ul class="act-list">
	      <c:choose>
	        <c:when test="${appPageList.datas.size()>0 }">
	          <c:forEach items="${appPageList.datas }" var="app" varStatus="status">
		        <li class="list-item show">
			      <div class="item-box">
			          <div class="box-btn">
			            <p>
	<%-- 		              <span class="sync_price" id="price_cmjz000559" code="cmjz000559" servicepackage="" inquerytype="cloudmarket">${app.price }</span> --%>
	<!-- 		              <font id="price_unit_cmjz000559">&nbsp;C</font> -->
			            </p>
			            <a class="xq" href="#/app/detail/${app.appId }" style="display: none;">查看详情</a>
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
			              <a href="#/app/detail/${app.appId }">
			                <img alt="产品logo" src="<%=request.getContextPath()%>/app/image?file=${app.pictureName}">
			              </a>
			            </div>
			            <div class="box-info">
			              <h4><a href="#/app/detail/${app.appId }">${app.appName }</a></h4>
			              <p>${app.intro }</p>
	<%-- 		              <p class="label">标签：${app.description }</p><!-- 标签内容待定 --> --%>
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
	  <div class="pagination text-center">
        <c:if test="${appPageList.datas.size()>0}">
           <ul class="datanumul pull-right">
              <li>
                  <span>共&nbsp;&nbsp;${appPageList.page.rowCount }&nbsp;&nbsp;条</span>
              </li>
          </ul>
          <ul class="pages pull-right">
              <!-- 显示prev -->
              <c:choose>
                <c:when test="${appPageList.page.hasPrev}">
                  <li><a class="ends" href="javascript:appStore.toMoreApp(${classifyPid },${classifyId },${appPageList.page.currentPage-1 },${classifyFloor })">&lt;&lt;</a></li>
                </c:when>
                <c:otherwise>
                  <li><a class="ends" href="javascript:void(0)">&lt;&lt;</a></li>
                </c:otherwise>
              </c:choose>
              <!-- 显示第一页 -->
              <c:choose>
                <c:when test="${appPageList.page.currentPage==1}"><li class="active"><a href="javascript:void(0);">1</a></li></c:when>
                <c:otherwise><li><a href="javascript:appStore.toMoreApp(${classifyPid },${classifyId },1,${classifyFloor })">1</a></li></c:otherwise>
              </c:choose>
              
              <c:if test="${appPageList.page.currentPage>4&&appPageList.page.totalPage>10}">
                  <li>...</li>
              </c:if>
              <c:choose>
                <c:when test="${appPageList.page.totalPage-appPageList.page.currentPage>=7}">
                  <c:if test="${appPageList.page.currentPage==3}">
                      <li><a href="javascript:appStore.toMoreApp(${classifyPid },${classifyId },${appPageList.page.currentPage-1 },${classifyFloor })">${appPageList.page.currentPage-1 }</a></li>
                  </c:if>
                  <c:if test="${appPageList.page.currentPage==4}">
                      <li><a href="javascript:appStore.toMoreApp(${classifyPid },${classifyId },${appPageList.page.currentPage-2 },${classifyFloor })">${appPageList.page.currentPage-2 }</a></li>
                  </c:if>
                  <c:if test="${appPageList.page.currentPage>3}">
                      <li><a href="javascript:appStore.toMoreApp(${classifyPid },${classifyId },${appPageList.page.currentPage-1 },${classifyFloor })">${appPageList.page.currentPage-1 }</a></li>
                  </c:if>
                  <c:if test="${appPageList.page.currentPage>1&&appPageList.page.currentPage<appPageList.page.totalPage}">
                      <li class="active"><a href="javascript:void(0);">${appPageList.page.currentPage }</a></li>
                  </c:if>
                  <c:if test="${appPageList.page.totalPage-appPageList.page.currentPage>1}">
                      <li><a href="javascript:appStore.toMoreApp(${classifyPid },${classifyId },${appPageList.page.currentPage+1 },${classifyFloor })">${appPageList.page.currentPage+1 }</a></li>
                  </c:if>
                  <c:if test="${appPageList.page.totalPage-appPageList.page.currentPage>2}">
                      <li><a href="javascript:appStore.toMoreApp(${classifyPid },${classifyId },${appPageList.page.currentPage+2 },${classifyFloor })">${appPageList.page.currentPage+2 }</a></li>
                  </c:if>
                  <c:if test="${appPageList.page.totalPage-appPageList.page.currentPage>3}">
                      <li><a href="javascript:appStore.toMoreApp(${classifyPid },${classifyId },${appPageList.page.currentPage+3 },${classifyFloor })">${appPageList.page.currentPage+3 }</a></li>
                  </c:if>
                  <c:if test="${appPageList.page.totalPage-appPageList.page.currentPage>4}">
                      <li><a href="javascript:appStore.toMoreApp(${classifyPid },${classifyId },${appPageList.page.currentPage+4 },${classifyFloor })">${appPageList.page.currentPage+4 }</a></li>
                  </c:if>
                  <c:if test="${appPageList.page.totalPage-appPageList.page.currentPage>5}">
                      <li><a href="javascript:appStore.toMoreApp(${classifyPid },${classifyId },${appPageList.page.currentPage+5 },${classifyFloor })">${appPageList.page.currentPage+5 }</a></li>
                  </c:if>
                  <c:if test="${appPageList.page.currentPage<4}">
                      <c:if test="%{appPageList.page.totalPage-appPageList.page.currentPage>6}">
                          <li><a href="javascript:appStore.toMoreApp(${classifyPid },${classifyId },${appPageList.page.currentPage+6 },${classifyFloor })">${appPageList.page.currentPage+6 }</a></li>
                      </c:if>
                  </c:if>
                  <c:choose>
                    <c:when test="${appPageList.page.currentPage==1}">
                      <c:if test="%{appPageList.page.totalPage-appPageList.page.currentPage>7}">
                          <li><a href="javascript:appStore.toMoreApp(${classifyPid },${classifyId },${appPageList.page.currentPage+7 },${classifyFloor })">${appPageList.page.currentPage+7 }</a></li>
                      </c:if>
                      <c:if test="%{appPageList.page.totalPage-appPageList.page.currentPage>8}">
                          <li><a href="javascript:appStore.toMoreApp(${classifyPid },${classifyId },${appPageList.page.currentPage+8 },${classifyFloor })">${appPageList.page.currentPage+8 }</a></li>
                      </c:if>
                    </c:when>
                    <c:otherwise>
                      <c:choose>
                        <c:when test="${appPageList.page.currentPage==2}">
                          <c:if test="${appPageList.page.totalPage-appPageList.page.currentPage>7}">
                              <li><a href="javascript:appStore.toMoreApp(${classifyPid },${classifyId },${appPageList.page.currentPage+7 },${classifyFloor })">${appPageList.page.currentPage+7 }</a></li>
                          </c:if>
                        </c:when>
                        <c:otherwise>
                          <c:if test="${appPageList.page.currentPage>4 && (appPageList.page.totalPage-appPageList.page.currentPage>6)}">
                              <li><a href="javascript:appStore.toMoreApp(${classifyPid },${classifyId },${appPageList.page.currentPage+6 },${classifyFloor })">${appPageList.page.currentPage+6 }</a></li>
                          </c:if>
                        </c:otherwise>
                      </c:choose>
                    </c:otherwise>
                  </c:choose>
                </c:when>
                <c:otherwise>
                  <c:choose>
                    <c:when test="${appPageList.page.totalPage-8>0}">
                      <c:forEach begin="${appPageList.page.totalPage-8}" step="1" end="${appPageList.page.totalPage-1}" var="step">
                        <c:choose>
                          <c:when test="${step==appPageList.page.currentPage}">   
                              <li class="active"><a href="javascript:void(0);">${step }</a></li>
                          </c:when>
                          <c:otherwise>
                              <li><a href="javascript:appStore.toMoreApp(${classifyPid },${classifyId },${step },${classifyFloor })">${step }</a></li>
                          </c:otherwise>
                        </c:choose>
                      </c:forEach>
                    </c:when>
                    <c:otherwise>
                      <c:forEach begin="2" step="1" end="${appPageList.page.totalPage-1}" var="step">
                        <c:choose>
                          <c:when test="${step==appPageList.page.currentPage}">   
                              <li class="active"><a href="javascript:void(0);">${step }</a></li>
                          </c:when>
                          <c:otherwise>
                              <li><a href="javascript:appStore.toMoreApp(${classifyPid },${classifyId },${step },${classifyFloor })">${step }</a></li>
                          </c:otherwise>
                        </c:choose>
                      </c:forEach>
                    </c:otherwise>
                  </c:choose>
                </c:otherwise>
              </c:choose>
              <c:if test="${appPageList.page.totalPage-appPageList.page.currentPage>=8&&appPageList.page.totalPage>10}">
                  <li>...</li>
              </c:if>
              <c:choose>
                <c:when test="${appPageList.page.currentPage==appPageList.page.totalPage&&appPageList.page.totalPage>1}"> 
                  <li class="active"><a href="javascript:void(0);">${appPageList.page.totalPage }</a></li>
                </c:when>
                <c:otherwise>
                  <c:if test="${appPageList.page.totalPage>1}">   
                    <li><a href="javascript:appStore.toMoreApp(${classifyPid },${classifyId },${appPageList.page.totalPage },${classifyFloor })">${appPageList.page.totalPage }</a></li>
                  </c:if>
                </c:otherwise>
              </c:choose>
              <c:choose>
                <c:when test="${appPageList.page.hasNext}">
                  <li><a class="ends" href="javascript:appStore.toMoreApp(${classifyPid },${classifyId },${appPageList.page.currentPage+1 },${classifyFloor })">&gt;&gt;</a></li>
                </c:when>
                <c:otherwise>
                  <li><a class="ends" href="javascript:void(0)">&gt;&gt;</a></li>
                </c:otherwise>
              </c:choose>
          </ul>
        </c:if>
      </div>
	</div>
</div>
<script type="text/javascript">
$(function () {
    $(".list-item").hover(function(){
        $(this).find(".xq").css('display','block');
    },function(){
        $(this).find(".xq").hide();
    });
    $(".J_single a").click(function(){
        $(this).addClass("current").siblings("a").removeClass("current");
    });
});
</script>