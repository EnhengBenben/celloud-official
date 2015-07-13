<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<div class="row-fluid">
	<div class="span12">
		<table class="table table-bordered table-striped" style="margin-bottom: 1px">
			<thead>
				<tr>
					<th>软件编号</th>
					<th>软件名称</th>
					<th>软件分类</th>
					<th>人气指数</th>
					<th>应用类型</th>
					<th>创建时间</th>
					<th>状态</th>
					<th>操作| <a onclick="javascript:toAddSoft();">新增</a></th>
				</tr>
			</thead>
			<tbody>
				<s:if test="%{list.size()>0}">
					<s:iterator id="li" value="list" status="st">
						<tr>
							<td><s:property value="#li.softwareId" /></td>
							<td><s:property value="#li.softwareName" /></td>
							<td><s:property value="#li.classify" /></td>
							<td><s:property value="#li.bhri" /></td>
							<td>
								<s:if test="#li.type==1">C/S</s:if>
								<s:if test="#li.type==2">数据库</s:if>
								<s:if test="#li.type==3">B/S</s:if>
								<s:if test="#li.type==4">JS</s:if>
							</td>
							<td><s:date name="#li.createDate" format="yyyy-MM-dd HH:mm:ss"/></td>
							<td>
								<s:if test="#li.offLine==0">已上线</s:if>
								<s:else><font color="red">已下线</font></s:else>
							</td>
							<td>
								<a href="javascript:void(0)" onclick="javascript:toDetailSoft('<s:property value="#li.softwareId" />')">详细</a>|
								<s:if test="#li.flag==0">
									<a href="javascript:void(0)" onclick="javascript:recommend('<s:property value="#li.softwareId" />')">推荐</a>
								</s:if>
								<s:else>
									<a href="javascript:void(0)" onclick="javascript:noRecommend('<s:property value="#li.softwareId" />')">取消推荐</a>
								</s:else>
								|<a href="javascript:void(0)" onclick="javascript:toEditSoft('<s:property value="#li.softwareId" />')">编辑</a>
								|
								<s:if test="#li.offLine==0">
									<a href="javascript:void(0)" onclick="javascript:softOffLine('<s:property value="#li.softwareId" />','<s:property value="#li.softwareName" />',1)">下线</a>
								</s:if>
								<s:if test="#li.offLine==1">
									<a href="javascript:void(0)" onclick="javascript:softOffLine('<s:property value="#li.softwareId" />','<s:property value="#li.softwareName" />',0)">上线</a>
								</s:if>
								|<a href="javascript:void(0)" onclick="javascript:deleteSoft('<s:property value="#li.softwareId" />')">删除</a>
							</td>
						</tr>
					</s:iterator>
				</s:if>
				<s:else>
					<tr><td colspan="13">对不起，没有数据</td></tr>
				</s:else>
			</tbody>
		</table>
	</div>
</div>
<s:if test="%{list.size()>0}">
	<div class="row-fluid"  align="right">
		<div class="span12">
			<nav id="pageView">
				<ul class="pagination">
					<s:if test="%{page.hasPre}">
						<li><a href="javascript:init(<s:property value="page.currentPage-1" />)">&lt;</a></li>
					</s:if>
					<s:else>
						<li><a href="javascript:void(0)">&lt;</a></li>
					</s:else>
					<s:if test="%{page.currentPage==1}">
						<li class="active"><a href="javascript:void(0)">1</a></li>
					</s:if>
					<s:else>
						<li><a href="javascript:init(1)">1</a></li>
					</s:else>
					<s:if test="%{page.currentPage>3}">
						<li><a href="javascript:void(0)">..</a></li>
					</s:if>
					<s:if test="%{page.currentPage>2}">
						<li><a href="javascript:init(<s:property value="page.currentPage-1"/>)"><s:property value="page.currentPage-1"/></a></li>
					</s:if>
					<s:if test="%{page.currentPage>1&&page.currentPage<page.totalPage}">
						<li class="active"><a href="javascript:void(0)"><s:property value="page.currentPage"/></a></li>
					</s:if>
					<s:if test="%{page.totalPage-page.currentPage>1}">
						<li><a href="javascript:init(<s:property value="page.currentPage+1"/>)"><s:property value="page.currentPage+1"/></a></li>
					</s:if>
					<s:if test="%{page.totalPage-page.currentPage>2}">
						<li><a href="javascript:void(0)">..</a></li>
					</s:if>
					<s:if test="%{page.currentPage==page.totalPage&&page.totalPage>1}">
						<li class="active"><a href="javascript:void(0)"><s:property value="page.totalPage"/></a></li>
					</s:if>
					<s:elseif test="%{page.totalPage>1}">
						<li><a href="javascript:init(<s:property value="page.totalPage"/>)"><s:property value="page.totalPage"/></a></li>
					</s:elseif>
					<s:if test="%{page.hasNext}">
						<li><a href="javascript:init(<s:property value="page.currentPage+1"/>)">&gt;</a></li>
					</s:if>
					<s:else>
						<li><a href="javascript:void(0)">&gt;</a></li>
					</s:else>
					<li>
						<a>共<s:property value="page.totalPage"/>页&nbsp;|&nbsp;合计<s:property value="page.rowCount"/>条</a>
					</li>
				</ul>
			</nav>
		</div>
	</div>
</s:if>