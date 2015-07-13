<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<div class="row">
	<div class="tabbable"> <!-- tabs -->
		<ul class="nav nav-tabs">
            <li class="tab1 active" id="liData"><a href="#tab1" data-toggle="tab">数据报告管理</a></li>
            <li class="tab2" id="liProject"><a href="#tab2" data-toggle="tab" id="projectTab">项目报告管理</a></li>
        </ul>
        <div class="tab-content">
        	<!-- 数据报告管理 -->
            <div class="tab-pane active" id="tab1"> 
            	请输入文件编号：<input type="text" id="dataKey">&nbsp;&nbsp;
				请选择软件：
				<select id="dataSoftSel">
					<option value="" selected="selected">---请选择软件---</option>
				</select>
				&nbsp;&nbsp;
				状态：
				<input type="radio" name="dst" value="2" checked="checked">正在运行且产生报告&nbsp;&nbsp;
				<input type="radio" name="dst" value="3">运行完成
				<a class="btn" href="javascript:saveDataReport();">保存</a>
				<div class="alert" id="dataAlertInfo" style="display: none;">
				</div>
            </div> 
            <!-- 项目报告管理 -->
            <div class="tab-pane" id="tab2"> 
            	请输入项目编号：<input type="text" id="proId">&nbsp;&nbsp;
				请选择软件：
				<select id="proSoftSel">
					<option value="" selected="selected">---请选择软件---</option>
				</select>
				&nbsp;&nbsp;
				状态：
				<input type="radio" name="pst" value="2" checked="checked">正在运行且产生报告&nbsp;&nbsp;
				<input type="radio" name="pst" value="3">运行完成
				<a class="btn" href="javascript:saveProReport();">保存</a>
				<div class="alert" id="proAlertInfo" style="display: none;">
				</div>
            </div> 
        </div>
	</div>
</div>
<script type="text/javascript">
	$(document).ready(function(){
		initReport();
	});

</script>