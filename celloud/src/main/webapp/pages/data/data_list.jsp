<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<table class="table data-table">
	<thead>
    	<tr>
        	<th>
        		<input id="data-checkall" type="checkbox" style="border:none;"/>
        	</th>
        	<th class="file-name">文件名称  <a id="data-sort-name" class="a-gray" href="javascript:void(0);"><i id="data-sort-name-icon" class="fa fa-sort-amount-asc"></i></a></th>
        	<th class="data-key">数据编号</th>
        	<th class="another-name">文件别名</th>
        	<th class="strain">样本类型/物种</th>
        	<th class="data-tags">数据标签</th>
        	<th class="sample">样本</th>
        	<th class="data-size">数据大小</th>
        	<th class="create-date" style="min-width:80px">上传时间  <a id="data-sort-createdate" class="a-green-normal" href="javascript:void(0);" ><i id="data-sort-date-icon" class="fa fa-sort-amount-desc"></i></a></th>
        	<th class="run-state">运行状态</th>
        </tr>
    </thead>
	<tbody id="data-list-tbody">
		<c:choose>
			<c:when test="${dataList.datas.size()>0}">
				<c:forEach items="${dataList.datas }" var="data">
					<tr>
						<td>
			        		<input id="chk${data.fileId }" name="data-checkone" type="checkbox" value="${data.fileId }" style="border:none;"/>
			        		<input id="filename-${data.fileId }" type="hidden" value="${data.fileName }">
			        	</td>
						<td class="file-name" title="${data.fileName }" name="data-name-td" >
							<c:choose><c:when test="${fn:length(data.fileName)>60 }"><c:out value="${fn:substring(data.fileName, 0, 60) }"/>...</c:when><c:otherwise>${data.fileName }</c:otherwise></c:choose>
							<c:if test="${data.isRunning>0}">
								<img src="<%=request.getContextPath()%>/images/icon/icon-running.png" title="running" style="position: absolute;margin-top: 1px;"/>
							</c:if>
						</td>
						<td>${data.dataKey }</td>
						<td title="${data.anotherName }">
							<c:choose><c:when test="${fn:length(data.anotherName)>10 }"><c:out value="${fn:substring(data.anotherName, 0, 10) }"/>...</c:when><c:otherwise>${data.anotherName }</c:otherwise></c:choose>
						</td>
						<td title="${data.strain }">
							<c:choose><c:when test="${fn:length(data.strain)>10 }"><c:out value="${fn:substring(data.strain, 0, 10) }"/>...</c:when><c:otherwise>${data.strain }</c:otherwise></c:choose>
						</td>
						<td title="${data.dataTags }">
							<c:choose><c:when test="${fn:length(data.dataTags)>10 }"><c:out value="${fn:substring(data.dataTags, 0, 10) }"/>...</c:when><c:otherwise>${data.dataTags }</c:otherwise></c:choose>
						</td>
						<td title="${data.sample }">
							<c:choose><c:when test="${fn:length(data.sample)>10 }"><c:out value="${fn:substring(data.sample, 0, 10) }"/>...</c:when><c:otherwise>${data.sample }</c:otherwise></c:choose>
						</td>
						<td>
							<c:choose><c:when test="${data.size>1048576 }"><fmt:formatNumber pattern="0.00" value="${data.size/1048576 }"/>MB</c:when><c:otherwise><fmt:formatNumber pattern="0.00" value="${data.size/1024 }"/>KB</c:otherwise></c:choose>
						</td>
						<td><fmt:formatDate type="date" value="${data.createDate }"/></td>
						<td>
							<c:choose>
								<c:when test="${data.reportNum>0}"><span class="label label-success">已运行</span></c:when>
								<c:otherwise><span class="label label-warning">未运行</span></c:otherwise>
							</c:choose>
						</td>
					</tr>
				</c:forEach>
			</c:when>
			<c:otherwise>
				<tr><td colspan="8">数据记录为空</td></tr>
			</c:otherwise>
		</c:choose>
   	</tbody>
</table>
<div class="pagination text-center">
  <c:if test="${dataList.datas.size()>0}">
    <input id="data-current-page-hide" value="${dataList.page.currentPage }" type="hidden" >
    <ul id="pagination-data" class="pages">
      <li> 每页
        <select id="data-page-size-sel" class="data-page-select">
          <option value="10" <c:if test="${dataList.page.pageSize==10}">selected</c:if>>10</option>
          <option value="20" <c:if test="${dataList.page.pageSize==20}">selected</c:if>>20</option>
          <option value="30" <c:if test="${dataList.page.pageSize==30}">selected</c:if>>30</option>
          <option value="50" <c:if test="${dataList.page.pageSize==50}">selected</c:if>>50</option>
          <option value="100" <c:if test="${dataList.page.pageSize==100}">selected</c:if>>100</option>
        </select>条
      </li>
      <!-- 显示prev -->
      <c:if test="${dataList.page.hasPrev}">
          <li><a id="prev-page-data" href="javascript:void(0);">&lt;</a></li>
      </c:if>
      <!-- 显示第一页 -->
      <c:choose>
        <c:when test="${dataList.page.currentPage==1}"><li class="active"><a href="#">1</a></li></c:when>
        <c:otherwise><li><a name="pagination-data" href="javascript:void(0);">1</a></li></c:otherwise>
      </c:choose>
      
      <c:if test="${dataList.page.currentPage>4&&dataList.page.totalPage>10}">
          <li>...</li>
      </c:if>
      <c:choose>
        <c:when test="${dataList.page.totalPage-dataList.page.currentPage>=7}">
          <c:if test="${dataList.page.currentPage==3}">
              <li><a name="pagination-data" href="javascript:void(0)">${dataList.page.currentPage-1 }</a></li>
          </c:if>
          <c:if test="${dataList.page.currentPage==4}">
              <li><a name="pagination-data" href="javascript:void(0)">${dataList.page.currentPage-2 }</a></li>
          </c:if>
          <c:if test="${dataList.page.currentPage>3}">
              <li><a name="pagination-data" href="javascript:void(0)">${dataList.page.currentPage-1 }</a></li>
          </c:if>
          <c:if test="${dataList.page.currentPage>1&&dataList.page.currentPage<dataList.page.totalPage}">
              <li class="active"><a href="#">${dataList.page.currentPage }</a></li>
          </c:if>
          <c:if test="${dataList.page.totalPage-dataList.page.currentPage>1}">
              <li><a name="pagination-data" href="javascript:void(0)">${dataList.page.currentPage+1 }</a></li>
          </c:if>
          <c:if test="${dataList.page.totalPage-dataList.page.currentPage>2}">
              <li><a name="pagination-data" href="javascript:void(0)">${dataList.page.currentPage+2 }</a></li>
          </c:if>
          <c:if test="${dataList.page.totalPage-dataList.page.currentPage>3}">
              <li><a name="pagination-data" href="javascript:void(0)">${dataList.page.currentPage+3 }</a></li>
          </c:if>
          <c:if test="${dataList.page.totalPage-dataList.page.currentPage>4}">
              <li><a name="pagination-data" href="javascript:void(0)">${dataList.page.currentPage+4 }</a></li>
          </c:if>
          <c:if test="${dataList.page.totalPage-dataList.page.currentPage>5}">
              <li><a name="pagination-data" href="javascript:void(0)">${dataList.page.currentPage+5 }</a></li>
          </c:if>
          <c:if test="${dataList.page.currentPage<4}">
              <c:if test="%{dataList.page.totalPage-dataList.page.currentPage>6}">
                  <li><a name="pagination-data" href="javascript:void(0)">${dataList.page.currentPage+6 }</a></li>
              </c:if>
          </c:if>
          <c:choose>
            <c:when test="${dataList.page.currentPage==1}">
              <c:if test="%{dataList.page.totalPage-dataList.page.currentPage>7}">
                  <li><a name="pagination-data" href="javascript:void(0)">${dataList.page.currentPage+7 }</a></li>
              </c:if>
              <c:if test="%{dataList.page.totalPage-dataList.page.currentPage>8}">
                  <li><a name="pagination-data" href="javascript:void(0)">${dataList.page.currentPage+8 }</a></li>
              </c:if>
            </c:when>
            <c:otherwise>
              <c:choose>
                <c:when test="${dataList.page.currentPage==2}">
                  <c:if test="${dataList.page.totalPage-dataList.page.currentPage>7}">
                      <li><a name="pagination-data" href="javascript:void(0)">${dataList.page.currentPage+7 }</a></li>
                  </c:if>
                </c:when>
                <c:otherwise>
                  <c:if test="${dataList.page.currentPage>4 && (dataList.page.totalPage-dataList.page.currentPage>6)}">
                      <li><a name="pagination-data" href="javascript:void(0)">${dataList.page.currentPage+6 }</a></li>
                  </c:if>
                </c:otherwise>
              </c:choose>
            </c:otherwise>
          </c:choose>
        </c:when>
        <c:otherwise>
          <c:choose>
            <c:when test="${dataList.page.totalPage-8>0}">
              <c:forEach begin="${dataList.page.totalPage-8}" step="1" end="${dataList.page.totalPage-1}" var="step">
                <c:choose>
                  <c:when test="${step==dataList.page.currentPage}">   
                      <li class="active"><a href="#">${step }</a></li>
                  </c:when>
                  <c:otherwise>
                      <li><a name="pagination-data" href="javascript:void(0)">${step }</a></li>
                  </c:otherwise>
                </c:choose>
              </c:forEach>
            </c:when>
            <c:otherwise>
              <c:forEach begin="2" step="1" end="${dataList.page.totalPage-1}" var="step">
                <c:choose>
                  <c:when test="${step==dataList.page.currentPage}">   
                      <li class="active"><a href="#">${step }</a></li>
                  </c:when>
                  <c:otherwise>
                      <li><a name="pagination-data" href="javascript:void(0)">${step }</a></li>
                  </c:otherwise>
                </c:choose>
              </c:forEach>
            </c:otherwise>
          </c:choose>
        </c:otherwise>
      </c:choose>
      <c:if test="${dataList.page.totalPage-dataList.page.currentPage>=8&&dataList.page.totalPage>10}">
          <li>...</li>
      </c:if>
      <c:choose>
        <c:when test="${dataList.page.currentPage==dataList.page.totalPage&&dataList.page.totalPage>1}"> 
          <li class="active"><a href="#">${dataList.page.totalPage }</a></li>
        </c:when>
        <c:otherwise>
          <c:if test="${dataList.page.totalPage>1}">   
            <li><a name="pagination-data" href="javascript:void(0)">${dataList.page.totalPage }</a></li>
          </c:if>
        </c:otherwise>
      </c:choose>
      <c:if test="${dataList.page.hasNext}">
          <li><a id="next-page-data" href="javascript:void(0)">&gt;</a></li>
      </c:if>
      <li>
                  共${dataList.page.totalPage }页&nbsp;|&nbsp;合计${dataList.page.rowCount }条
      </li>
    </ul>
  </c:if>
</div>