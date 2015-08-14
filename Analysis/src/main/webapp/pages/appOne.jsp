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
						<dl id="dt-list-1" class="dl-horizontal" style="word-break:break-all;word-wrap:break-word;line-height:2em;vertical-align: middle">
							<dt>APP图标：</dt>
							<dd><img src="http://celloud.org/images/app/${app.pictureName }"></dd>
							<dt>APP名称：</dt>
							<dd>${app.softwareName }</dd>
							<dt>运行次数：</dt>
							<dd>${app.runNum }</dd>
							<dt>最少数据个数：</dt>
							<dd>${app.dataNum }</dd>
							<dt>可运行数据格式：</dt>
							<dd>${app.dataType }</dd>
							<dt>人气指数：</dt>
							<dd>${app.bhri }</dd>
							<dt>上线时间：</dt>
							<dd><fmt:formatDate type="both" value="${app.createDate }"/></dd>
							<dt>软件功能：</dt>
							<dd>${app.description }</dd>
							<dt>软件介绍：</dt>
							<dd>${app.intro }</dd>
						</dl>
					</div>
				</div>
			</div>
		</div>
	</div>
	<div class="space-6"></div>
</div>