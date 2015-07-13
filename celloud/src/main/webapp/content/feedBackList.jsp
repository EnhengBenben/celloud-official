<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<ul id="talkList">
  <s:if test="%{fbList.datas.size()>0}">
  <s:iterator value="fbList.datas" status="st" id="data">
	  <li>
	  	<div class="userPic">
	  		<a><img src="/celloud/favicon.ico"/></a>
	  		<strong>
    			<s:property value="#data.userName" />：
    		</strong>
    		<!-- title -->
    		<span><s:property value="#data.title" /></span>
	  	</div>
	  	<div class="msgBox">
	    	<div class="replyBox">
    			<!-- content -->
    			<div class="msgCnt">
    				<s:property value="#data.content" escapeHtml="false"/>
    			</div>
				<div class="pubInfo c_tx5">
					<span class="left c_tx5">
						<!-- time -->
						<a class="time"><s:date name="#data.createDate" format="yyyy-MM-dd HH:mm:ss" /></a> 
					</span>
				</div>
			</div>
			<div class="pubInfo c_tx5">
			</div>
		</div>
	  </li>
  </s:iterator>
  </s:if>
  <s:else>
	<div class="msgBox">
		暂时没有任何反馈意见
	</div>
  </s:else>
</ul>

<div class="pagination" >
	<s:if test="%{fbList.datas.size()>0}">
	    <ul class="pages">
	        <s:if test="%{fbList.page.hasPrev}">
				<li><a href="javascript:searchFbInfo('<s:property value="fbList.page.currentPage-1" />')">&lt;</a></li>
			</s:if>
			<s:else>
				<li><a href="#">&lt;</a></li>
			</s:else>
			<s:if test="%{fbList.page.currentPage==1}">
				<li class="active"><a href="#">1</a></li>
			</s:if>
			<s:else>
				<li><a href="javascript:searchFbInfo(1)">1</a></li>
			</s:else>
			<s:if test="%{fbList.page.currentPage>3}">
				<li><a href="#">..</a></li>
			</s:if>
			<s:if test="%{fbList.page.currentPage>2}">
				<li><a href="javascript:searchFbInfo(<s:property value="fbList.page.currentPage-1"/>)"><s:property value="fbList.page.currentPage-1"/></a></li>
			</s:if>
			<s:if test="%{fbList.page.currentPage>1&&fbList.page.currentPage<fbList.page.totalPage}">
				<li class="active"><a href="#"><s:property value="fbList.page.currentPage"/></a></li>
			</s:if>
			<s:if test="%{fbList.page.totalPage-fbList.page.currentPage>1}">
				<li><a href="javascript:searchFbInfo(<s:property value="fbList.page.currentPage+1"/>)"><s:property value="fbList.page.currentPage+1"/></a></li>
			</s:if>
			<s:if test="%{fbList.page.totalPage-fbList.page.currentPage>2}">
				<li><a href="#">..</a></li>
			</s:if>
			<s:if test="%{fbList.page.currentPage==fbList.page.totalPage&&fbList.page.totalPage>1}">
				<li class="active"><a href="#"><s:property value="fbList.page.totalPage"/></a></li>
			</s:if>
			<s:elseif test="%{fbList.page.totalPage>1}">
				<li><a href="javascript:searchFbInfo(<s:property value="fbList.page.totalPage"/>)"><s:property value="fbList.page.totalPage"/></a></li>
			</s:elseif>
			<s:if test="%{page.hasNext}">
				<li><a href="javascript:searchFbInfo(<s:property value="fbList.page.currentPage+1"/>)">&gt;</a></li>
			</s:if>
			<s:else>
				<li><a href="#">&gt;</a></li>
			</s:else>
			<li>
				共<s:property value="fbList.page.totalPage"/>页&nbsp;|&nbsp;合计<s:property value="fbList.page.rowCount"/>条
			</li>
	    </ul>
    </s:if>
</div> 