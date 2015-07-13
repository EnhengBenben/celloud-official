<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<div class="row-fluid">
	<div class="span12">
		<table class="table table-bordered table-striped" style="margin-bottom: 1px">
			<thead>
				<tr>
					<th>
						用户编号
					</th>
					<th>
						用户名
					</th>
					<th>
						真实姓名
					</th>
					<th>
						邮箱
					</th>
					<th>
						手机号码
					</th>
					<th>
						角色
					</th>
					<th>
						操作
					</th>
				</tr>
			</thead>
			<tbody>
				<s:if test="%{userPageList.datas.size()>0}">
					<s:iterator id="user" value="userPageList.datas" status="st">
						<tr>
							<td><s:property value="#user.userId" /></td>
							<td><s:property value="#user.username" /></td>
							<td><s:property value="#user.truename" /></td>
							<td><s:property value="#user.email" /></td>
							<td><s:property value="#user.cellPhone" /></td>
							<td>
								<!-- 0，1 普通用户，2超级管理员，3管理员 -->
								<s:if test="%{#user.role==0||#user.role==1}">
									普通用户
								</s:if>
								<s:elseif test="%{#user.role==2}">
									超级管理员
								</s:elseif>
								<s:elseif test="%{#user.role==3}">
									管理员
								</s:elseif>
							</td> 
							<td>
								<s:if test="#user.remark==''||#user.remark==null"></s:if>
								<s:else>
									<a href="javascript:void(0)" onclick="javascript:detailUser('<s:property value="#user.remark" />')">查看备注</a>|
								</s:else>
								<a href="javascript:void(0)" onclick="javascript:editUser(<s:property value="#user.userId" />)">编辑</a>|
								<a href="javascript:void(0)" onclick="javascript:delUser(<s:property value="#user.userId" />)">删除</a>|
								<a href="javascript:void(0)" onclick="javascript:resetPwd(<s:property value="#user.userId" />)">重置密码</a>
							</td>
						</tr>
					</s:iterator>
				</s:if>
				<s:else>
					<tr><td colspan="8">对不起，没有数据</td></tr>
				</s:else>
			</tbody>
		</table>
</div>
</div>
<s:if test="%{userPageList.datas.size()>0}">
	<div class="row-fluid"  align="right">
		<div class="span12 keepRight">
			<nav id="pageView">
				<ul class="pagination">
			        <s:if test="%{userPageList.page.hasPrev}">
						<li><a href="javascript:searchUser('<s:property value="userPageList.page.currentPage-1" />')">&lt;</a></li>
					</s:if>
					<s:else>
						<li><a href="#">&lt;</a></li>
					</s:else>
					<s:if test="%{userPageList.page.currentPage==1}">
						<li class="active"><a href="#">1</a></li>
					</s:if>
					<s:else>
						<li><a href="javascript:searchUser(1)">1</a></li>
					</s:else>
					<s:if test="%{userPageList.page.currentPage>3}">
						<li><a href="#">..</a></li>
					</s:if>
					<s:if test="%{userPageList.page.currentPage>2}">
						<li><a href="javascript:searchUser(<s:property value="userPageList.page.currentPage-1"/>)"><s:property value="userPageList.page.currentPage-1"/></a></li>
					</s:if>
					<s:if test="%{userPageList.page.currentPage>1&&userPageList.page.currentPage<userPageList.page.totalPage}">
						<li class="active"><a href="#"><s:property value="userPageList.page.currentPage"/></a></li>
					</s:if>
					<s:if test="%{userPageList.page.totalPage-userPageList.page.currentPage>1}">
						<li><a href="javascript:searchUser(<s:property value="userPageList.page.currentPage+1"/>)"><s:property value="userPageList.page.currentPage+1"/></a></li>
					</s:if> 
					<s:if test="%{userPageList.page.totalPage-userPageList.page.currentPage>2}">
						<li><a href="#">..</a></li>
					</s:if>
					<s:if test="%{userPageList.page.currentPage==userPageList.page.totalPage&&userPageList.page.totalPage>1}">
						<li class="active"><a href="#"><s:property value="userPageList.page.totalPage"/></a></li>
					</s:if>
					<s:elseif test="%{userPageList.page.totalPage>1}">
						<li><a href="javascript:searchUser(<s:property value="userPageList.page.totalPage"/>)"><s:property value="userPageList.page.totalPage"/></a></li>
					</s:elseif>
					<s:if test="%{userPageList.page.hasNext}">
						<li><a href="javascript:searchUser(<s:property value="userPageList.page.currentPage+1"/>)">&gt;</a></li>
					</s:if>
					<s:else>
						<li><a href="#">&gt;</a></li>
					</s:else>
					<li>
						<a>共<s:property value="userPageList.page.totalPage"/>页&nbsp;|&nbsp;合计<s:property value="userPageList.page.rowCount"/>条</a>
					</li>
			    </ul>
			</nav>
		</div>
	</div>
</s:if>