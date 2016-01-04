<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<div class="breadcrumbs" id="breadcrumbs">
	<script type="text/javascript">
		try {
			ace.settings.check('breadcrumbs', 'fixed')
		} catch (e) {
		}
	</script>
	<ul class="breadcrumb">
		<li>
			<i class="icon-bar-chart"></i>
			<a href="#">活跃度统计</a>
		</li>
		<li class="active">App活跃度统计</li>
	</ul>
	<!-- .breadcrumb -->
</div>
<div class="col-sm-12">
	<div class="row">
		<!-- 
	   <h3 class="header smaller lighter green">
            <i class="icon-signal"></i>
            App活跃度统计
        </h3>
	 -->
		<!-- App统计 -->

		<div class="title">
			<h3 class="header smaller lighter green">APP运行统计</h3>
		</div>
		<div class="col-xs-12" id="appListDiv" style="height: 450px"></div>
	</div>
	<div class="space-6"></div>
</div>
<script src="./js/appActivity.js"></script>
