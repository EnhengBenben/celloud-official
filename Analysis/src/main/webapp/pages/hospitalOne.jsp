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
							<dt>医院名称：</dt>
							<dd>${company.company_name }</dd>
							<dt>医院地址：</dt>
							<dd>${company.address }</dd>
							<dt>医院电话：</dt>
							<dd>${company.tel }</dd>
							<dt>用户数量：</dt>
							<dd>${company.userNum }</dd>
							<dt>用户名称：</dt>
							<dd>${company.userNames }</dd>
							<dt>用户数量：</dt>
							<dd>${company.userNum }&nbsp;个</dd>
							<dt>包含部门：</dt>
							<dd>${company.deptNames }</dd>
							<dt>数据数量：</dt>
							<dd>${company.fileNum }&nbsp;个</dd>
							<dt>数据大小：</dt>
							<dd><fmt:formatNumber pattern="0.00" value="${company.fileSize/ (1024 * 1024 * 1024) }"/>&nbsp;(GB)</dd>
							<dt>报告数量：</dt>
							<dd>${company.reportNum }&nbsp;个</dd>
							<dt>入驻时间：</dt>
							<dd><fmt:formatDate type="both" value="${company.create_date }"/></dd>
						</dl>
					</div>
				</div>
			</div>
		</div>
	</div>
	<div class="space-6"></div>
</div>