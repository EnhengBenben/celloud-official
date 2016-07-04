<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<script src="js/drawCharts.js"></script>
<script src="js/console.js"></script>
<div class="col-xs-12 console">
    <div class="row">
        <div class="widget-box transparent">
            <div class="widget-header widget-header-flat">
                <h3 class="header smaller lighter green">总量统计</h3>
            </div>
            <div class="col-sm-12 col-md-12 home-numbers">
                <ul style='margin-left: 15px'>
                    <li>
                        <a class="number">${resultMap.companyNum }</a>
                        <div class="info">
                            <b>
                                ${resultMap.companyNum }
                                <span class="unit">(个)</span>
                            </b>
                            医院数量:
                        </div>
                    </li>
                    <li>
                        <a class="number">${resultMap.userNum }</a>
                        <div class="info">
                            <b>
                                ${resultMap.userNum }
                                <span class="unit">(个)</span>
                            </b>
                            用户数量:
                        </div>
                    </li>
                    <li>
                        <a class="number">${resultMap.appNum }</a>
                        <div class="info">
                            <b>
                                ${resultMap.appNum }
                                <span class="unit">(个)</span>
                            </b>
                            APP数量:
                        </div>
                    </li>
                    <li>
                        <a class="number">${resultMap.dataNum }</a>
                        <div class="info">
                            <b>
                                ${resultMap.dataNum }
                                <span class="unit">(个)</span>
                            </b>
                            数据个数:
                        </div>
                    </li>
                    <li>
                        <a class="number">${empty resultMap.reportNum?0:resultMap.reportNum }</a>
                        <div class="info">
                            <b>
                                ${empty resultMap.reportNum?0:resultMap.reportNum }
                                <span class="unit">(个)</span>
                            </b>
                            报告数量:
                        </div>
                    </li>
                    <li>
                        <a class="number">
                            <c:choose>
                                <c:when test="${resultMap.dataSize>1099511627776 }">
                                    <fmt:formatNumber pattern="0.00" value="${(resultMap.dataSize-(resultMap.dataSize%1099511627776))/1099511627776 + resultMap.dataSize%1099511627776/1099511627776 }" />
                                </c:when>
                                <c:when test="${resultMap.dataSize>1073741824 }">
                                    <fmt:formatNumber pattern="0.00" value="${(resultMap.dataSize-(resultMap.dataSize%1073741824))/1073741824 + resultMap.dataSize%1073741824/1073741824 }" />
                                </c:when>
                                <c:when test="${resultMap.dataSize>1048576 }">
                                    <fmt:formatNumber pattern="0.00" value="${(resultMap.dataSize-(resultMap.dataSize%1048576))/1048576 + resultMap.dataSize%1048576/1048576 }" />
                                </c:when>
                                <c:otherwise>
                                    <fmt:formatNumber pattern="0.00" value="${(resultMap.dataSize-(resultMap.dataSize%1024))/1024 + resultMap.dataSize%1024/1024 }" />
                                </c:otherwise>
                            </c:choose>
                        </a>
                        <div class="info">
                            <b>
                                <c:choose>
                                    <c:when test="${resultMap.dataSize>1099511627776 }">
                                        <fmt:formatNumber pattern="0.00" value="${(resultMap.dataSize-(resultMap.dataSize%1099511627776))/1099511627776 + resultMap.dataSize%1099511627776/1099511627776 }" />
                                        <span class="unit">(TB)</span>
                                    </c:when>
	                                <c:when test="${resultMap.dataSize>1073741824 }">
	                                    <fmt:formatNumber pattern="0.00" value="${(resultMap.dataSize-(resultMap.dataSize%1073741824))/1073741824 + resultMap.dataSize%1073741824/1073741824 }" />
                                        <span class="unit">(GB)</span>
                                    </c:when>
	                                <c:when test="${resultMap.dataSize>1048576 }">
	                                    <fmt:formatNumber pattern="0.00" value="${(resultMap.dataSize-(resultMap.dataSize%1048576))/1048576 + resultMap.dataSize%1048576/1048576 }" />
                                        <span class="unit">(MB)</span>
                                    </c:when>
	                                <c:otherwise>
	                                    <fmt:formatNumber pattern="0.00" value="${(resultMap.dataSize-(resultMap.dataSize%1024))/1024 + resultMap.dataSize%1024/1024 }" />
                                        <span class="unit">(KB)</span>
                                    </c:otherwise>
	                            </c:choose>
                            </b>
                            数据大小:
                        </div>
                    </li>
                </ul>
                <div class="space-6"></div>
            </div>
            <!-- /widget-main -->
        </div>
        <!-- /widget-body -->
    </div>
    <!-- /widget-box -->
    <div class="space-6"></div>
</div>
<!-- /row -->
<div class="col-xs-12 console">
	<div class="row">
	    <div class="widget-box transparent">
	        <div class="widget-header widget-header-flat">
	            <h3 class="header smaller lighter green">用户地理分布</h3>
	        </div>
	        <div class="col-sm-10" style="height: 500px; width: 70%" id="map"></div>
	    </div>
	</div>
</div>
<div class="col-xs-12 console">
    <div class="row">
        <div class="widget-box transparent">
            <div class="widget-header widget-header-flat">
	           <h3 class="header smaller lighter green">用户登陆统计</h3>
	        </div>
            <div id="loginId" class="col-sm-12" style="height: 350px;"></div>
        </div>
    </div>
</div>
<div class="col-xs-12 console">
    <div class="row">
        <div class="widget-box transparent">
            <div class="widget-header widget-header-flat">
	           <h3 class="header smaller lighter green">APP使用统计</h3>
	         </div>
            <div class="col-sm-12" style="height: 350px;" id="AppRunNum"></div>
        </div>
    </div>
</div>
<!-- row -->
<div class="col-xs-12 console">
    <div class="row">
        <div class="widget-box transparent">
            <div class="widget-header widget-header-flat">
               <h3 class="header smaller lighter green">用户运行统计</h3>
             </div>
            <div class="col-sm-12" style="height: 350px;" id="UserRunNum"></div>
        </div>
    </div>
</div>
<!-- /.row -->
<div class="col-xs-12 console">
    <div class="row">
        <div class="widget-box transparent">
            <div class="widget-header widget-header-flat">
               <h3 class="header smaller lighter green">浏览器统计</h3>
            </div>
            <div class="col-xs-4 table-responsiv " style="height: 350px; overflow-y: scroll">
		        <table id="allUserDataList" class="table table-striped table-bordered table-hover">
		            <thead>
		                <tr>
		                    <th>浏览器</th>
		                    <th class="hidden-180">使用数量</th>
		                </tr>
		            </thead>
		            <tbody>
		                <c:forEach items="${browserData }" var="data">
		                    <tr>
		
		                        <td>${data.browser }</td>
		                        <td>${data.logNum }</td>
		                    </tr>
		                </c:forEach>
		            </tbody>
		        </table>
		    </div>
		    <div class="col-xs-8">
		        <div class="col-sm-12" style="height: 350px" id="browserDistribute"></div>
		    </div>
        </div>
    </div>
</div>