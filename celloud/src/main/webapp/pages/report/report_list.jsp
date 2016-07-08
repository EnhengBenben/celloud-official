<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<a href="" id="_downFile"></a>
<table class="table_">
    <tbody id="_show">
    	<c:if test="${pageList.datas.size()>0 }">
    		<c:forEach var="report" items="${pageList.datas }">
                <tr>
                    <td class="_firstTd">
                        <div>
                        项目名称：<span id="showPname${report.project_id }">
                               <span id="pnameSpan${report.project_id }">
                               <c:if test="${report.project_name.length()>13 }">
                               	${report.project_name.substring(0,12)}...
                               </c:if>
                               	<c:if test="${report.project_name.length()<=13 }">
                               		${report.project_name}
                               	</c:if>
                               </span>
                               <c:if test="${report.userName=='no_one'}">
    	                            <a href="javascript:void()" class="changeImg" onclick="toChangePname(${report.project_id })"></a>
                               </c:if>
                            </span>
                            <span id="changePname${report.project_id }" class="none">
	                            <input type="text" value="${report.project_name}" id="updatePname${report.project_id }" class="changeInput" onblur="changePname(${report.project_id })"/>
	                            <img src="<%=request.getContextPath() %>/images/report/ok_blue.png" id="okUpdateImg${report.project_id }" class="okImg" onclick="changePname(${report.project_id })" />
                            </span>
                        </div>
                        <div>
                            App名称：${report.app_name }
                        </div>
                        <div>
                           	 文件数量：
                            <span id="rdataNum${report.project_id }">
                            	<c:if test="${report.data_num==0 }">
                            		&nbsp; 
                            	</c:if>
                            	<c:if test="${report.data_num!=0 }">
                            		${report.data_num }
                            	</c:if>
                            </span>
                        </div>
                        <div>
                           	数据大小：
                            <c:choose>
                                <c:when test="${report.data_size==0 }">&nbsp;</c:when>
                                <c:when test="${report.data_size<1024&&report.data_size>0 }">
                                    <fmt:formatNumber value ="${report.data_size}" pattern="#,#00.0#"></fmt:formatNumber> B
                                </c:when>
                                <c:when test="${report.data_size<1048576&&report.data_size>=1024 }">
                                    <fmt:formatNumber value ="${report.data_size/1024}" pattern="#,##0.0#"></fmt:formatNumber> KB
                                </c:when>
                                <c:when test="${report.data_size>=1048576 }">
                                    <fmt:formatNumber value ="${report.data_size/1048576}" pattern="#,##0.0#"></fmt:formatNumber> MB
                                </c:when>
                            </c:choose>
                        </div>
                        <div>
                        	 启动时间：<fmt:formatDate value="${report.create_date }" pattern="yyyy-MM-dd HH:mm:ss"/> 
                        </div>
                        <div>
                           	 结束时间：<fmt:formatDate value="${report.end_date }" pattern="yyyy-MM-dd HH:mm:ss"/> 
                        </div>
                        <c:if test="${report.userName!='no_one' }">
                            <div class="operate">
                                <a class="sharefrom" title="共享" href="javascript:void()"></a><span class="shareU">${report.userName }</span>
                                <a class="delete" title="删除" onclick="cancelProjectShare(${report.project_id},${report.user_id})" href="javascript:void(0)"></a>
                            </div>
                        </c:if>
                        <c:if test="${report.userName=='no_one'}">
                            <div class="operate">
                                <c:if test="${loginUserInSession.companyId == 6 }">
                                    <a class="projectreport" title="项目报告" target="_blank" href="${pageContext.request.contextPath }/report/printPgsProject?projectId=${report.project_id}"></a>
                                </c:if>
                            	<c:if test="${report.app_id>84&&report.app_id!=89&&report.app_id!=90&&report.app_id!=105&&report.app_id!=106&&report.app_id!=107&&report.app_id!=108&&report.app_id!=109&&report.app_id!=110&&report.app_id!=111&&report.app_id!=112&&report.app_id!=113&&report.app_id!=114&&report.app_id!=117 }">
                                	<a class="pdfdown" title="PDF下载" onclick="downPDF(${report.user_id},${report.app_id},${report.project_id})" href="javascript:void(0)"></a>
                            	</c:if>
                            	<c:if test="${report.share_num==0 }">
                                	<a class="share" title="共享" onclick="toShareModal('${report.project_id}','${report.project_name}',${report.data_num})" href="javascript:void(0);"></a>
                            	</c:if>
                            	<c:if test="${report.share_num!=0 }">
                                	<a class="shared" title="已共享" onclick="shareModal('${report.project_id}','${report.user_id}','${report.project_name}',${report.data_num})" href="javascript:void(0);"></a>
                            	</c:if>
                                <a class="delete" title="删除" onclick="removePro('${report.project_id}')" href="javascript:void(0)"></a>
                            </div>
                        </c:if>
                    </td>
                    <td class="none">
                        ${report.app_id},${report.app_name},${report.project_id},${report.user_id}
                    </td>
                    <td class="no">
                    	<c:if test="${report.period>=2}">
                    		${report.context }
                    	</c:if>
                    	<c:if test="${empty report.context }">
                        	<img src="<%=request.getContextPath() %>/images/report/running.png" title="正在运行..."/>
                    	</c:if>
                    </td>
                </tr>
            </c:forEach>
        </c:if>
        <c:if test="${pageList.datas.size()==0 }">
            <tr><td colspan="8">项目记录为空</td></tr>
        </c:if>
    </tbody>
</table>
<div class="pagination text-center">
  <c:if test="${pageList.datas.size()>0}">
    <input id="data-current-page-hide" value="${pageList.page.currentPage }" type="hidden" >
    <ul class="pages" id="pagination-data">
      <li> 每页
        <select id="proPageRecordSel" onchange="javascript:changePageSize();">
            <option value="10" <c:if test="${pageList.page.pageSize==10}">selected</c:if>>10</option>
            <option value="20" <c:if test="${pageList.page.pageSize==20}">selected</c:if>>20</option>
            <option value="30" <c:if test="${pageList.page.pageSize==30}">selected</c:if>>30</option>
            <option value="50" <c:if test="${pageList.page.pageSize==50}">selected</c:if>>50</option>
            <option value="100" <c:if test="${pageList.page.pageSize==100}">selected</c:if>>100</option>
        </select>条
      </li>
      <!-- 显示prev -->
      <c:if test="${pageList.page.hasPrev}">
          <li><a href="javascript:changePage(${pageList.page.currentPage-1 })">&lt;</a></li>
      </c:if>
      <!-- 显示第一页 -->
      <c:choose>
        <c:when test="${pageList.page.currentPage==1}"><li class="active"><a href="javascript:changePage(1)">1</a></li></c:when>
        <c:otherwise><li><a href="javascript:changePage(1)">1</a></li></c:otherwise>
      </c:choose>
      
      <c:if test="${pageList.page.currentPage>4&&pageList.page.totalPage>10}">
          <li>...</li>
      </c:if>
      <c:choose>
        <c:when test="${pageList.page.totalPage-pageList.page.currentPage>=7}">
          <c:if test="${pageList.page.currentPage==3}">
              <li><a href="javascript:changePage(${pageList.page.currentPage-1 })">${pageList.page.currentPage-1 }</a></li>
          </c:if>
          <c:if test="${pageList.page.currentPage==4}">
              <li><a href="javascript:changePage(${pageList.page.currentPage-2 })">${pageList.page.currentPage-2 }</a></li>
          </c:if>
          <c:if test="${pageList.page.currentPage>3}">
              <li><a href="javascript:changePage(${pageList.page.currentPage-1 })">${pageList.page.currentPage-1 }</a></li>
          </c:if>
          <c:if test="${pageList.page.currentPage>1&&pageList.page.currentPage<pageList.page.totalPage}">
              <li class="active"><a href="javascript:changePage(${pageList.page.currentPage })">${pageList.page.currentPage }</a></li>
          </c:if>
          <c:if test="${pageList.page.totalPage-pageList.page.currentPage>1}">
              <li><a href="javascript:changePage(${pageList.page.currentPage+1 })">${pageList.page.currentPage+1 }</a></li>
          </c:if>
          <c:if test="${pageList.page.totalPage-pageList.page.currentPage>2}">
              <li><a href="javascript:changePage(${pageList.page.currentPage+2 })">${pageList.page.currentPage+2 }</a></li>
          </c:if>
          <c:if test="${pageList.page.totalPage-pageList.page.currentPage>3}">
              <li><a href="javascript:changePage(${pageList.page.currentPage+3 })">${pageList.page.currentPage+3 }</a></li>
          </c:if>
          <c:if test="${pageList.page.totalPage-pageList.page.currentPage>4}">
              <li><a href="javascript:changePage(${pageList.page.currentPage+4 })">${pageList.page.currentPage+4 }</a></li>
          </c:if>
          <c:if test="${pageList.page.totalPage-pageList.page.currentPage>5}">
              <li><a href="javascript:changePage(${pageList.page.currentPage+5 })">${pageList.page.currentPage+5 }</a></li>
          </c:if>
          <c:if test="${pageList.page.currentPage<4}">
              <c:if test="%{pageList.page.totalPage-pageList.page.currentPage>6}">
                  <li><a href="javascript:changePage(${pageList.page.currentPage+6 })">${pageList.page.currentPage+6 }</a></li>
              </c:if>
          </c:if>
          <c:choose>
            <c:when test="${pageList.page.currentPage==1}">
              <c:if test="%{pageList.page.totalPage-pageList.page.currentPage>7}">
                  <li><a href="javascript:changePage(${pageList.page.currentPage+7 })">${pageList.page.currentPage+7 }</a></li>
              </c:if>
              <c:if test="%{pageList.page.totalPage-pageList.page.currentPage>8}">
                  <li><a href="javascript:changePage(${pageList.page.currentPage+8 })">${pageList.page.currentPage+8 }</a></li>
              </c:if>
            </c:when>
            <c:otherwise>
              <c:choose>
                <c:when test="${pageList.page.currentPage==2}">
                  <c:if test="${pageList.page.totalPage-pageList.page.currentPage>7}">
                      <li><a href="javascript:changePage(${pageList.page.currentPage+7 })">${pageList.page.currentPage+7 }</a></li>
                  </c:if>
                </c:when>
                <c:otherwise>
                  <c:if test="${pageList.page.currentPage>4 && (pageList.page.totalPage-pageList.page.currentPage>6)}">
                      <li><a href="javascript:changePage(${pageList.page.currentPage+6 })">${pageList.page.currentPage+6 }</a></li>
                  </c:if>
                </c:otherwise>
              </c:choose>
            </c:otherwise>
          </c:choose>
        </c:when>
        <c:otherwise>
          <c:choose>
            <c:when test="${pageList.page.totalPage-8>0}">
              <c:forEach begin="${pageList.page.totalPage-8}" step="1" end="${pageList.page.totalPage-1}" var="step">
                <c:choose>
                  <c:when test="${step==pageList.page.currentPage}">   
                      <li class="active"><a href="javascript:changePage(${step })">${step }</a></li>
                  </c:when>
                  <c:otherwise>
                      <li><a href="javascript:changePage(${step })">${step }</a></li>
                  </c:otherwise>
                </c:choose>
              </c:forEach>
            </c:when>
            <c:otherwise>
              <c:forEach begin="2" step="1" end="${pageList.page.totalPage-1}" var="step">
                <c:choose>
                  <c:when test="${step==pageList.page.currentPage}">   
                      <li class="active"><a href="javascript:changePage(${step })">${step }</a></li>
                  </c:when>
                  <c:otherwise>
                      <li><a href="javascript:changePage(${step })">${step }</a></li>
                  </c:otherwise>
                </c:choose>
              </c:forEach>
            </c:otherwise>
          </c:choose>
        </c:otherwise>
      </c:choose>
      <c:if test="${pageList.page.totalPage-pageList.page.currentPage>=8&&pageList.page.totalPage>10}">
          <li>...</li>
      </c:if>
      <c:choose>
        <c:when test="${pageList.page.currentPage==pageList.page.totalPage&&pageList.page.totalPage>1}"> 
          <li class="active"><a href="javascript:changePage(${pageList.page.totalPage })">${pageList.page.totalPage }</a></li>
        </c:when>
        <c:otherwise>
          <c:if test="${pageList.page.totalPage>1}">   
            <li><a href="javascript:changePage(${pageList.page.totalPage })">${pageList.page.totalPage }</a></li>
          </c:if>
        </c:otherwise>
      </c:choose>
      <c:if test="${pageList.page.hasNext}">
          <li><a href="javascript:changePage(${pageList.page.currentPage+1 })">&gt;</a></li>
      </c:if>
      <li>
                  共${pageList.page.totalPage }页&nbsp;|&nbsp;合计${pageList.page.rowCount }条
      </li>
    </ul>
  </c:if>
</div>
