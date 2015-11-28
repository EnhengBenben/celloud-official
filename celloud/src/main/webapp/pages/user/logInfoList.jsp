<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<div style="width: 800px;">
<table class="table table-tab table-bordered table-bordered-new">
	<thead>
    	<tr>
    		<th>时间</th>
			<th>Ip地址</th>
			<th>登录地点</th>
			<th>浏览器</th>
			<th>操作系统</th>
        </tr>
    </thead>
	<tbody>
		<s:if test="%{behaviorList.datas.size()>0}">
			<s:iterator value="behaviorList.datas" status="st" id="data">
				<tr>
					<td><s:date name="#data.logDate" format="yyyy-MM-dd HH:mm:ss" /></td>
					<td><s:property value="#data.ip" /></td>
					<td title="<s:property value="#data.address"/>">
						<s:if test="#data.address.length()>11">
							<s:property value="#data.address.substring(0,11)"/>......
						</s:if>
						<s:else>
							<s:property value="#data.address"/>
						</s:else>
					</td>
					<td><s:property value="#data.browser" /></td>
					<td><s:property value="#data.os" /></td>
				</tr>
			</s:iterator>
		</s:if>
		<s:else>
			<tr><td colspan="5">登录日志为空</td></tr>
		</s:else>
   	</tbody>
</table>
<div class="pagination-new" style="text-align:center;">
	<s:if test="%{behaviorList.datas.size()>0}">
	    <ul class="pages">
	    	<!-- 显示prev -->
	        <s:if test="%{behaviorList.page.hasPrev}">
				<li><a href="javascript:searchLogInfo('<s:property value="behaviorList.page.currentPage-1" />')">&lt;</a></li>
			</s:if>
			<!-- 显示第一页 -->
			<s:if test="%{behaviorList.page.currentPage==1}">
				<li class="active"><a href="#">1</a></li>
			</s:if>
			<s:else>
				<li><a href="javascript:searchLogInfo(1)">1</a></li>
			</s:else>
			
			<s:if test="%{behaviorList.page.currentPage>4&&behaviorList.page.totalPage>10}">
				<li>...</li>
			</s:if>
			
			<s:if test="%{behaviorList.page.totalPage-behaviorList.page.currentPage>=7}">
				<s:if test="%{behaviorList.page.currentPage==3}">
					<li><a href="javascript:searchLogInfo(<s:property value="behaviorList.page.currentPage-1"/>)"><s:property value="behaviorList.page.currentPage-1"/></a></li>
				</s:if>
				<s:if test="%{behaviorList.page.currentPage==4}">
					<li><a href="javascript:searchLogInfo(<s:property value="behaviorList.page.currentPage-2"/>)"><s:property value="behaviorList.page.currentPage-2"/></a></li>
				</s:if>
				<s:if test="%{behaviorList.page.currentPage>3}">
					<li><a href="javascript:searchLogInfo(<s:property value="behaviorList.page.currentPage-1"/>)"><s:property value="behaviorList.page.currentPage-1"/></a></li>
				</s:if>
				<s:if test="%{behaviorList.page.currentPage>1&&behaviorList.page.currentPage<behaviorList.page.totalPage}">
					<li class="active"><a href="#"><s:property value="behaviorList.page.currentPage"/></a></li>
				</s:if>
				<s:if test="%{behaviorList.page.totalPage-behaviorList.page.currentPage>1}">
					<li><a href="javascript:searchLogInfo(<s:property value="behaviorList.page.currentPage+1"/>)"><s:property value="behaviorList.page.currentPage+1"/></a></li>
				</s:if>
				<s:if test="%{behaviorList.page.totalPage-behaviorList.page.currentPage>2}">
					<li><a href="javascript:searchLogInfo(<s:property value="behaviorList.page.currentPage+2"/>)"><s:property value="behaviorList.page.currentPage+2"/></a></li>
				</s:if>
				<s:if test="%{behaviorList.page.totalPage-behaviorList.page.currentPage>3}">
					<li><a href="javascript:searchLogInfo(<s:property value="behaviorList.page.currentPage+3"/>)"><s:property value="behaviorList.page.currentPage+3"/></a></li>
				</s:if>
				<s:if test="%{behaviorList.page.totalPage-behaviorList.page.currentPage>4}">
					<li><a href="javascript:searchLogInfo(<s:property value="behaviorList.page.currentPage+4"/>)"><s:property value="behaviorList.page.currentPage+4"/></a></li>
				</s:if>
				<s:if test="%{behaviorList.page.totalPage-behaviorList.page.currentPage>5}">
					<li><a href="javascript:searchLogInfo(<s:property value="behaviorList.page.currentPage+5"/>)"><s:property value="behaviorList.page.currentPage+5"/></a></li>
				</s:if>
				<s:if test="%{behaviorList.page.currentPage<4}">
					<s:if test="%{behaviorList.page.totalPage-behaviorList.page.currentPage>6}">
						<li><a href="javascript:searchLogInfo(<s:property value="behaviorList.page.currentPage+6"/>)"><s:property value="behaviorList.page.currentPage+6"/></a></li>
					</s:if>
				</s:if>
				<s:if test="%{behaviorList.page.currentPage==1}">
					<s:if test="%{behaviorList.page.totalPage-behaviorList.page.currentPage>7}">
						<li><a href="javascript:searchLogInfo(<s:property value="behaviorList.page.currentPage+7"/>)"><s:property value="behaviorList.page.currentPage+7"/></a></li>
					</s:if>
					<s:if test="%{behaviorList.page.totalPage-behaviorList.page.currentPage>8}">
						<li><a href="javascript:searchLogInfo(<s:property value="behaviorList.page.currentPage+8"/>)"><s:property value="behaviorList.page.currentPage+8"/></a></li>
					</s:if>
				</s:if>
				<s:elseif test="%{behaviorList.page.currentPage==2}">
					<s:if test="%{behaviorList.page.totalPage-behaviorList.page.currentPage>7}">
						<li><a href="javascript:searchLogInfo(<s:property value="behaviorList.page.currentPage+7"/>)"><s:property value="behaviorList.page.currentPage+7"/></a></li>
					</s:if>
				</s:elseif>
				<s:elseif test="%{behaviorList.page.currentPage>4}">
					<s:if test="%{behaviorList.page.totalPage-behaviorList.page.currentPage>6}">
						<li><a href="javascript:searchLogInfo(<s:property value="behaviorList.page.currentPage+6"/>)"><s:property value="behaviorList.page.currentPage+6"/></a></li>
					</s:if>
				</s:elseif>
			</s:if>
			<s:else>
				<s:if test="%{behaviorList.page.totalPage-8>0}">
					<s:iterator begin="%{behaviorList.page.totalPage-8}" step="1" end="%{behaviorList.page.totalPage-1}" var="step">
						<s:if test="#step==behaviorList.page.currentPage">
							<li class="active"><a href="#"><s:property value="#step"/></a></li>
						</s:if>
						<s:else>
							<li><a href="javascript:searchLogInfo(<s:property value="#step"/>)""><s:property value="#step"/></a></li>
						</s:else>
					</s:iterator>
				</s:if>
				<s:else>
					<s:iterator begin="2" step="1" end="%{behaviorList.page.totalPage-1}" var="step">
						<s:if test="#step==behaviorList.page.currentPage">
							<li class="active"><a href="#"><s:property value="#step"/></a></li>
						</s:if>
						<s:else>
							<li><a href="javascript:searchLogInfo(<s:property value="#step"/>)""><s:property value="#step"/></a></li>
						</s:else>
					</s:iterator>
				</s:else>
			</s:else>
			
			<s:if test="%{behaviorList.page.totalPage-behaviorList.page.currentPage>=8&&behaviorList.page.totalPage>10}">
				<li>...</li>
			</s:if>
			
			<s:if test="%{behaviorList.page.currentPage==behaviorList.page.totalPage&&behaviorList.page.totalPage>1}">
				<li class="active"><a href="#"><s:property value="behaviorList.page.totalPage"/></a></li>
			</s:if>
			<s:elseif test="%{behaviorList.page.totalPage>1}">
				<li><a href="javascript:searchLogInfo(<s:property value="behaviorList.page.totalPage"/>)"><s:property value="behaviorList.page.totalPage"/></a></li>
			</s:elseif>
			<s:if test="%{behaviorList.page.hasNext}">
				<li><a href="javascript:searchLogInfo(<s:property value="behaviorList.page.currentPage+1"/>)">&gt;</a></li>
			</s:if>
			<li>
				共<s:property value="behaviorList.page.totalPage"/>页&nbsp;|&nbsp;合计<s:property value="behaviorList.page.rowCount"/>条
			</li>
	    </ul>
    </s:if>
</div> 
</div>