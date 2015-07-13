<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<s:if test="%{pageList.datas.size()>0}">
	<s:iterator id="db" value="pageList.datas" status="st">
		<s:if test='#db.offLine==0'>
		<li class="appGroup">
         	<div class="app-icon">
             	<div class="center"><a href="javascript:void(0)" onclick="softDetail('<s:property value="#db.softwareId"/>','<s:property value="#db.isAdd"/>',1)"><img src="<%=request.getContextPath()%>/images/app/<s:property value="#db.pictureName"/>"></a></div>
             	<div class="center">
             		<s:if test='#db.isAdd=="yes"'>
             			<img src="<%=request.getContextPath()%>/images/software/added.png" />
	             	</s:if>
	             	<s:elseif test='#db.softwareId==6||#db.softwareId==9||#db.softwareId==10||#db.softwareId==12||#db.softwareId==74||#db.softwareId==75||#db.softwareId==76||#db.softwareId==77||#db.softwareId==78||#db.softwareId==79||#db.softwareId==81||#db.softwareId==83||#db.softwareId==105||#db.softwareId==106||#db.softwareId==107||#db.softwareId==108||#db.softwareId==109||#db.softwareId==110'>
	             		<img src="<%=request.getContextPath()%>/images/software/developing.png" />
	             	</s:elseif>
	             	<s:elseif test='#db.isAdd=="no"'>
	             		<a id="addAppInList<s:property value="#db.softwareId"/>" href="javascript:addBhri('addAppInList<s:property value="#db.softwareId"/>','<s:property value="#db.softwareId"/>','<s:property value="#db.softwareName"/>','<s:property value="#db.pictureName"/>','<s:property value="#db.host"/>','<s:property value="#db.isAdd"/>',1);" class="add"><img src="<%=request.getContextPath()%>/images/software/icon_add.png" />添加</a>
	             	</s:elseif>
             	</div>
             </div>
         	<div class="app-item">
             	<div class="appTitle"><s:property value="#db.softwareName"/></div>
             	<div class="appContent">
	             	<s:if test="#db.description.length()>30">
	             		<s:property value="#db.description.substring(0,30)"/>......
	             	</s:if>
	             	<s:else>
	             		<s:property value="#db.description"/>
	             	</s:else>
             	</div>
             	<div class="appStar sa<s:property value="#db.bhri"/>"></div>
             	<div class="appCount"><s:property value="#db.bhri"/></div>
             </div>
         </li>
         </s:if>
	</s:iterator>
</s:if>
<s:else>
<div class="tip" style="margin-left: 200px;margin-top: 100px;">
	<img src="<%=request.getContextPath()%>/images/database/tips.png" />没有符合条件的应用
</div>
</s:else>
<s:if test="%{pageList.datas.size()>0}">
<div class="pagination-new center">
	<ul class="pages">
	    	<!-- 显示prev -->
	        <s:if test="%{pageList.page.hasPrev}">
				<li><a href="javascript:selectByPage('<s:property value="page.currentPage-1" />')">&lt;</a></li>
			</s:if>
			<!-- 显示第一页 -->
			<s:if test="%{pageList.page.currentPage==1}">
				<li class="active"><a href="#">1</a></li>
			</s:if>
			<s:else>
				<li><a href="javascript:selectByPage(1)">1</a></li>
			</s:else>
			
			<s:if test="%{pageList.page.currentPage>4&&pageList.page.totalPage>10}">
				<li>...</li>
			</s:if>
			
			<s:if test="%{pageList.page.totalPage-pageList.page.currentPage>=7}">
				<s:if test="%{pageList.page.currentPage==3}">
					<li><a href="javascript:selectByPage(<s:property value="pageList.page.currentPage-1"/>)"><s:property value="pageList.page.currentPage-1"/></a></li>
				</s:if>
				<s:if test="%{pageList.page.currentPage==4}">
					<li><a href="javascript:selectByPage(<s:property value="pageList.page.currentPage-2"/>)"><s:property value="pageList.page.currentPage-2"/></a></li>
				</s:if>
				<s:if test="%{pageList.page.currentPage>3}">
					<li><a href="javascript:selectByPage(<s:property value="pageList.page.currentPage-1"/>)"><s:property value="pageList.page.currentPage-1"/></a></li>
				</s:if>
				<s:if test="%{pageList.page.currentPage>1&&pageList.page.currentPage<pageList.page.totalPage}">
					<li class="active"><a href="#"><s:property value="pageList.page.currentPage"/></a></li>
				</s:if>
				<s:if test="%{pageList.page.totalPage-pageList.page.currentPage>1}">
					<li><a href="javascript:selectByPage(<s:property value="pageList.page.currentPage+1"/>)"><s:property value="pageList.page.currentPage+1"/></a></li>
				</s:if>
				<s:if test="%{pageList.page.totalPage-pageList.page.currentPage>2}">
					<li><a href="javascript:selectByPage(<s:property value="pageList.page.currentPage+2"/>)"><s:property value="pageList.page.currentPage+2"/></a></li>
				</s:if>
				<s:if test="%{pageList.page.totalPage-pageList.page.currentPage>3}">
					<li><a href="javascript:selectByPage(<s:property value="pageList.page.currentPage+3"/>)"><s:property value="pageList.page.currentPage+3"/></a></li>
				</s:if>
				<s:if test="%{pageList.page.totalPage-pageList.page.currentPage>4}">
					<li><a href="javascript:selectByPage(<s:property value="pageList.page.currentPage+4"/>)"><s:property value="pageList.page.currentPage+4"/></a></li>
				</s:if>
				<s:if test="%{pageList.page.totalPage-pageList.page.currentPage>5}">
					<li><a href="javascript:selectByPage(<s:property value="pageList.page.currentPage+5"/>)"><s:property value="pageList.page.currentPage+5"/></a></li>
				</s:if>
				<s:if test="%{pageList.page.currentPage<4}">
					<s:if test="%{pageList.page.totalPage-pageList.page.currentPage>6}">
						<li><a href="javascript:selectByPage(<s:property value="pageList.page.currentPage+6"/>)"><s:property value="pageList.page.currentPage+6"/></a></li>
					</s:if>
				</s:if>
				<s:if test="%{pageList.page.currentPage==1}">
					<s:if test="%{pageList.page.totalPage-pageList.page.currentPage>7}">
						<li><a href="javascript:selectByPage(<s:property value="pageList.page.currentPage+7"/>)"><s:property value="pageList.page.currentPage+7"/></a></li>
					</s:if>
					<s:if test="%{pageList.page.totalPage-pageList.page.currentPage>8}">
						<li><a href="javascript:selectByPage(<s:property value="pageList.page.currentPage+8"/>)"><s:property value="pageList.page.currentPage+8"/></a></li>
					</s:if>
				</s:if>
				<s:elseif test="%{pageList.page.currentPage==2}">
					<s:if test="%{pageList.page.totalPage-pageList.page.currentPage>7}">
						<li><a href="javascript:selectByPage(<s:property value="pageList.page.currentPage+7"/>)"><s:property value="pageList.page.currentPage+7"/></a></li>
					</s:if>
				</s:elseif>
				<s:elseif test="%{pageList.page.currentPage>4}">
					<s:if test="%{pageList.page.totalPage-pageList.page.currentPage>6}">
						<li><a href="javascript:selectByPage(<s:property value="pageList.page.currentPage+6"/>)"><s:property value="pageList.page.currentPage+6"/></a></li>
					</s:if>
				</s:elseif>
			</s:if>
			<s:else>
				<s:if test="%{pageList.page.totalPage-8>0}">
					<s:iterator begin="%{pageList.page.totalPage-8}" step="1" end="%{pageList.page.totalPage-1}" var="step">
						<s:if test="#step>1">
							<s:if test="#step==pageList.page.currentPage">
								<li class="active"><a href="#"><s:property value="#step"/></a></li>
							</s:if>
							<s:else>
								<li><a href="javascript:selectByPage(<s:property value="#step"/>)""><s:property value="#step"/></a></li>
							</s:else>
						</s:if>
					</s:iterator>
				</s:if>
				<s:else>
					<s:iterator begin="2" step="1" end="%{pageList.page.totalPage-1}" var="step">
						<s:if test="#step==pageList.page.currentPage">
							<li class="active"><a href="#"><s:property value="#step"/></a></li>
						</s:if>
						<s:else>
							<li><a href="javascript:selectByPage(<s:property value="#step"/>)""><s:property value="#step"/></a></li>
						</s:else>
					</s:iterator>
				</s:else>
			</s:else>
			
			<s:if test="%{pageList.page.totalPage-pageList.page.currentPage>=8&&pageList.page.totalPage>10}">
				<li>...</li>
			</s:if>
			
			<s:if test="%{pageList.page.currentPage==pageList.page.totalPage&&pageList.page.totalPage>1}">
				<li class="active"><a href="#"><s:property value="pageList.page.totalPage"/></a></li>
			</s:if>
			<s:elseif test="%{pageList.page.totalPage>1}">
				<li><a href="javascript:selectByPage(<s:property value="pageList.page.totalPage"/>)"><s:property value="pageList.page.totalPage"/></a></li>
			</s:elseif>
			<s:if test="%{pageList.page.hasNext}">
				<li><a href="javascript:selectByPage(<s:property value="pageList.page.currentPage+1"/>)">&gt;</a></li>
			</s:if>
			<li>
				共<s:property value="pageList.page.totalPage"/>页&nbsp;|&nbsp;合计<s:property value="pageList.page.rowCount"/>条
			</li>
	</ul>
</div>
</s:if>