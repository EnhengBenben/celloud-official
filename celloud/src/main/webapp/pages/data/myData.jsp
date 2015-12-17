<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<style>
.dataautocomplete{list-style-type:none;margin-left:30px;border:1px solid #ccc;width:281px;position:absolute;left:170px;top:83px; background:#fff}
.dataautocomplete li{font-size:12px; font-family:"Lucida Console", Monaco, monospace; cursor:pointer; height:21px; line-height:20px}
.datahovers{ background-color:#3368c4; color:fff}
select{display: inline-block;margin-bottom: 0;background-color: #f3fafd;height: 28px;padding: 4px;font-size: 13px;line-height: 18px;color: #555555;border: 1px solid #a0d1e3;font-family: "Helvetica Neue",Helvetica,Arial,sans-serif;font-weight: normal;margin: 0;vertical-align: middle;-webkit-appearance: menulist;box-sizing: border-box;align-items: center;white-space: pre;-webkit-rtl-ordering: logical;cursor: default;font: -webkit-small-control;letter-spacing: normal;word-spacing: normal;text-transform: none;text-indent: 0px;text-shadow: none;text-align: start;-webkit-writing-mode: horizontal-tb;}
</style>
<input type="hidden" id="pageRecordNumHidden" value='<s:property value="pageRecordNum"/>'/>
<input type="hidden" id="currentPageRecordNum" value='<s:property value="%{dataPageList.datas.size()}" />'>
<table class="table">
	<thead>
    	<tr>
        	<th class="center">
        		<input type="checkbox" id="selAll" class="selAll" style="border:none;" data-step="2" data-position="right" data-intro="" data-img="checkdata.png"/>
        	</th>
        	<th style="min-width: 418px;">文件名称  <a href="javascript:sortByFileName();" id="sortNameA" class="a-gray"><i class="fa fa-sort-amount-asc" id="sortFileName"></i></a></th>
        	<th class="center" style="min-width:115px;">数据编号</th>
        	<th class="center" style="min-width:70px;">文件别名</th>
        	<th class="center" style="min-width:100px;">样本类型/物种</th>
        	<th class="center" style="min-width:70px;">数据标签</th>
        	<th class="center" style="min-width:50px;">样本</th>
        	<th class="center" style="min-width:80px">数据大小</th>
        	<th class="center" style="min-width:80px">上传时间  <a href="javascript:sortByCreateDate();" id="sortDateA" class="a-green-normal"><i class="fa fa-sort-amount-desc" id="sortCreateDate"></i></th>
        	<th class="center">运行状态</th>
<!-- 			<th class="center" style="min-width:40px">操作</th> -->
        </tr>
    </thead>
	<tbody>
		<c:choose>
			<c:when test="${dataPageList.datas.size()>0}">
				<c:forEach items="${dataPageList.datas }" var="data">
					<tr>
						<td class="center">
			        		<input name="datachk" type="checkbox" id='chk${data.fileId }' value='${data.fileId }' onclick="javascript:chkOnChange(this);" style="border:none;"/>
			        		<input type="hidden" value="${data.fileName }" id="fileName${data.fileId }">
			        	</td>
						<td title="${data.fileName }">
							<c:choose><c:when test="${fn:length(data.fileName)>60 }"><c:out value="${fn:substring(data.fileName, 0, 60) }"/>...</c:when><c:otherwise>${data.fileName }</c:otherwise></c:choose>
							<c:if test="${data.isRunning>0}">
								<img src="<%=request.getContextPath()%>/images/publicIcon/icon-running.png" title="running" style="position: absolute;margin-top: 1px;"/>
							</c:if>
						</td>
						<td class="center">${data.dataKey }</td>
						<td class="center" title="${data.anotherName }">
							<c:choose><c:when test="${fn:length(data.anotherName)>10 }"><c:out value="${fn:substring(data.anotherName, 0, 10) }"/>...</c:when><c:otherwise>${data.anotherName }</c:otherwise></c:choose>
						</td>
						<td class="center" title="${data.strain }">
							<c:choose><c:when test="${fn:length(data.strain)>10 }"><c:out value="${fn:substring(data.strain, 0, 10) }"/>...</c:when><c:otherwise>${data.strain }</c:otherwise></c:choose>
						</td>
						<td class="center" title="${data.dataTags }">
							<c:choose><c:when test="${fn:length(data.dataTags)>10 }"><c:out value="${fn:substring(data.dataTags, 0, 10) }"/>...</c:when><c:otherwise>${data.dataTags }</c:otherwise></c:choose>
						</td>
						<td class="center" title="${data.sample }">
							<c:choose><c:when test="${fn:length(data.sample)>10 }"><c:out value="${fn:substring(data.sample, 0, 10) }"/>...</c:when><c:otherwise>${data.sample }</c:otherwise></c:choose>
						</td>
						<td class="center">
							<c:choose><c:when test="${data.size>1048576 }"><fmt:formatNumber pattern="0.00" value="${data.size/1048576 }"/>MB</c:when><c:otherwise><fmt:formatNumber pattern="0.00" value="${data.size/1024 }"/>KB</c:otherwise></c:choose>
						</td>
						<td class="center"><fmt:formatDate type="date" value="${data.createDate }"/></td>
						<td class="center">
							<c:choose>
								<c:when test="${data.reportNum>0}"><span class="label label-success">已运行</span></c:when>
								<c:otherwise><span class="label label-warning">未运行</span></c:otherwise>
							</c:choose>
						</td>
<!-- 						<td class="center"> -->
<%-- 	        				<a href="javascript:toMoreDataInfoModel(${data.fileId },'${data.fileName }');"><img  title="更多" alt="更多" class="more" src="<%=request.getContextPath()%>/images/publicIcon/more.png"/></a> --%>
<!-- 	        			</td> -->
					</tr>
				</c:forEach>
			</c:when>
			<c:otherwise>
				<tr><td colspan="8">数据记录为空</td></tr>
			</c:otherwise>
		</c:choose>
   	</tbody>
</table>
<div class="pagination-new center">
	<s:if test="%{dataPageList.datas.size()>0}">
		<ul class="pages">
	    	<!-- 显示prev -->
	    	<li>
	    		每页
				<select id="pageRecordSel" onchange="javascript:changePageRecordNum();">
		   			<option value="10">10</option>
		   			<option value="20">20</option>
		   			<option value="30">30</option>
		   			<option value="50">50</option>
		   			<option value="100">100</option>
		   		</select>
				条
	    	</li>
	        <s:if test="%{dataPageList.page.hasPrev}">
				<li><a href="javascript:getDataByCondition('<s:property value="dataPageList.page.currentPage-1" />')">&lt;</a></li>
			</s:if>
			<!-- 显示第一页 -->
			<s:if test="%{dataPageList.page.currentPage==1}">
				<li class="active"><a href="#">1</a></li>
			</s:if>
			<s:else>
				<li><a href="javascript:getDataByCondition(1)">1</a></li>
			</s:else>
			
			<s:if test="%{dataPageList.page.currentPage>4&&dataPageList.page.totalPage>10}">
				<li>...</li>
			</s:if>
			
			<s:if test="%{dataPageList.page.totalPage-dataPageList.page.currentPage>=7}">
				<s:if test="%{dataPageList.page.currentPage==3}">
					<li><a href="javascript:getDataByCondition(<s:property value="dataPageList.page.currentPage-1"/>)"><s:property value="dataPageList.page.currentPage-1"/></a></li>
				</s:if>
				<s:if test="%{dataPageList.page.currentPage==4}">
					<li><a href="javascript:getDataByCondition(<s:property value="dataPageList.page.currentPage-2"/>)"><s:property value="dataPageList.page.currentPage-2"/></a></li>
				</s:if>
				<s:if test="%{dataPageList.page.currentPage>3}">
					<li><a href="javascript:getDataByCondition(<s:property value="dataPageList.page.currentPage-1"/>)"><s:property value="dataPageList.page.currentPage-1"/></a></li>
				</s:if>
				<s:if test="%{dataPageList.page.currentPage>1&&dataPageList.page.currentPage<dataPageList.page.totalPage}">
					<li class="active"><a href="#"><s:property value="dataPageList.page.currentPage"/></a></li>
				</s:if>
				<s:if test="%{dataPageList.page.totalPage-dataPageList.page.currentPage>1}">
					<li><a href="javascript:getDataByCondition(<s:property value="dataPageList.page.currentPage+1"/>)"><s:property value="dataPageList.page.currentPage+1"/></a></li>
				</s:if>
				<s:if test="%{dataPageList.page.totalPage-dataPageList.page.currentPage>2}">
					<li><a href="javascript:getDataByCondition(<s:property value="dataPageList.page.currentPage+2"/>)"><s:property value="dataPageList.page.currentPage+2"/></a></li>
				</s:if>
				<s:if test="%{dataPageList.page.totalPage-dataPageList.page.currentPage>3}">
					<li><a href="javascript:getDataByCondition(<s:property value="dataPageList.page.currentPage+3"/>)"><s:property value="dataPageList.page.currentPage+3"/></a></li>
				</s:if>
				<s:if test="%{dataPageList.page.totalPage-dataPageList.page.currentPage>4}">
					<li><a href="javascript:getDataByCondition(<s:property value="dataPageList.page.currentPage+4"/>)"><s:property value="dataPageList.page.currentPage+4"/></a></li>
				</s:if>
				<s:if test="%{dataPageList.page.totalPage-dataPageList.page.currentPage>5}">
					<li><a href="javascript:getDataByCondition(<s:property value="dataPageList.page.currentPage+5"/>)"><s:property value="dataPageList.page.currentPage+5"/></a></li>
				</s:if>
				<s:if test="%{dataPageList.page.currentPage<4}">
					<s:if test="%{dataPageList.page.totalPage-dataPageList.page.currentPage>6}">
						<li><a href="javascript:getDataByCondition(<s:property value="dataPageList.page.currentPage+6"/>)"><s:property value="dataPageList.page.currentPage+6"/></a></li>
					</s:if>
				</s:if>
				<s:if test="%{dataPageList.page.currentPage==1}">
					<s:if test="%{dataPageList.page.totalPage-dataPageList.page.currentPage>7}">
						<li><a href="javascript:getDataByCondition(<s:property value="dataPageList.page.currentPage+7"/>)"><s:property value="dataPageList.page.currentPage+7"/></a></li>
					</s:if>
					<s:if test="%{dataPageList.page.totalPage-dataPageList.page.currentPage>8}">
						<li><a href="javascript:getDataByCondition(<s:property value="dataPageList.page.currentPage+8"/>)"><s:property value="dataPageList.page.currentPage+8"/></a></li>
					</s:if>
				</s:if>
				<s:elseif test="%{dataPageList.page.currentPage==2}">
					<s:if test="%{dataPageList.page.totalPage-dataPageList.page.currentPage>7}">
						<li><a href="javascript:getDataByCondition(<s:property value="dataPageList.page.currentPage+7"/>)"><s:property value="dataPageList.page.currentPage+7"/></a></li>
					</s:if>
				</s:elseif>
				<s:elseif test="%{dataPageList.page.currentPage>4}">
					<s:if test="%{dataPageList.page.totalPage-dataPageList.page.currentPage>6}">
						<li><a href="javascript:getDataByCondition(<s:property value="dataPageList.page.currentPage+6"/>)"><s:property value="dataPageList.page.currentPage+6"/></a></li>
					</s:if>
				</s:elseif>
			</s:if>
			<s:else>
				<s:if test="%{dataPageList.page.totalPage-8>0}">
					<s:iterator begin="%{dataPageList.page.totalPage-8}" step="1" end="%{dataPageList.page.totalPage-1}" var="step">
						<s:if test="#step==dataPageList.page.currentPage">
							<li class="active"><a href="#"><s:property value="#step"/></a></li>
						</s:if>
						<s:else>
							<li><a href="javascript:getDataByCondition(<s:property value="#step"/>)"><s:property value="#step"/></a></li>
						</s:else>
					</s:iterator>
				</s:if>
				<s:else>
					<s:iterator begin="2" step="1" end="%{dataPageList.page.totalPage-1}" var="step">
						<s:if test="#step==dataPageList.page.currentPage">
							<li class="active"><a href="#"><s:property value="#step"/></a></li>
						</s:if>
						<s:else>
							<li><a href="javascript:getDataByCondition(<s:property value="#step"/>)"><s:property value="#step"/></a></li>
						</s:else>
					</s:iterator>
				</s:else>
			</s:else>
			
			<s:if test="%{dataPageList.page.totalPage-dataPageList.page.currentPage>=8&&dataPageList.page.totalPage>10}">
				<li>...</li>
			</s:if>
			
			<s:if test="%{dataPageList.page.currentPage==dataPageList.page.totalPage&&dataPageList.page.totalPage>1}">
				<li class="active"><a href="#"><s:property value="dataPageList.page.totalPage"/></a></li>
			</s:if>
			<s:elseif test="%{dataPageList.page.totalPage>1}">
				<li><a href="javascript:getDataByCondition(<s:property value="dataPageList.page.totalPage"/>)"><s:property value="dataPageList.page.totalPage"/></a></li>
			</s:elseif>
			<s:if test="%{dataPageList.page.hasNext}">
				<li><a href="javascript:getDataByCondition(<s:property value="dataPageList.page.currentPage+1"/>)">&gt;</a></li>
			</s:if>
			<li>
				共<s:property value="dataPageList.page.totalPage"/>页&nbsp;|&nbsp;合计<s:property value="dataPageList.page.rowCount"/>条
			</li>
	</ul>
    </s:if>
</div> 
<script type="text/javascript">
	$.ajaxSetup ({
		cache: false //关闭AJAX相应的缓存
	});
	$(document).ready(function(){
		initDataList();
	});
</script>