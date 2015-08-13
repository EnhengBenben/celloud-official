<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<div class="col-sm-8">
	<div class="row">
		<div class="col-xs-12">
			<div class="widget-box">
				<div class="widget-header widget-header-flat">
					<h4 class="smaller">基本信息</h4>
				</div>
				<div class="widget-body">
					<div class="widget-main">
						<dl id="dt-list-1" class="dl-horizontal" style="word-break:break-all;word-wrap:break-word;line-height:2em">
							<dt>用户名称：</dt>
							<dd>${user.username }</dd>
							<dt>Email：</dt>
							<dd>${user.email }</dd>
							<dt>用户电话：</dt>
							<dd>${user.cellphone }</dd>
							<dt>所属医院：</dt>
							<dd>${user.companyName }</dd>
							<dt>所属部门：</dt>
							<dd>${user.deptName }</dd>
							<dt>数据数量：</dt>
							<dd>${user.fileNum }&nbsp;个</dd>
							<dt>数据大小：</dt>
							<dd><fmt:formatNumber pattern="0.00" value="${user.fileSize/ (1024 * 1024 * 1024) }"/>&nbsp;(GB)</dd>
							<dt>报告数量：</dt>
							<dd>${user.reportNum }&nbsp;个</dd>
							<dt>注册时间：</dt>
							<dd><fmt:formatDate type="both" value="${user.createDate }"/></dd>
						</dl>
					</div>
				</div>
			</div>
		</div>
	</div>
	<div class="space-6"></div>
</div>