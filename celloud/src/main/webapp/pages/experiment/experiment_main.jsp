<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<link href="<%=request.getContextPath()%>/css/experiment.css" rel="stylesheet" type="text/css" />
<section class="content-header">
	<h1>
		<small> </small>
	</h1>
	<ol class="breadcrumb">
		<li>
			<a href="javascript:void(0)">
				<i class="fa fa-stack-overflow"></i> 实验流程
			</a>
		</li>
		<li class="active">
			<span id="subtitle">Doing</span>
		</li>
	</ol>
</section>

<section class="content">
	<div class="row">
		<div class="col-xs-12">
			<div class="mainpage" id="expMain">
				<div class="y-row operation-serve box box-success" data-spm="16">
					<div class="info">
						<p>您可以在这里管理您的实验流程哦。</p>
					</div>
					<ul>
						<li id="doingTab" class="active">
							<a href="javascript:experiment.getDoingPageList(1);">Doing</a>
						</li>
						<li id="doneTab">
							<a href="javascript:experiment.clearDoneCondition();">Done</a>
						</li>
					</ul>
				</div>
				<div class="y-row" style="padding: 10px 10px; background-color: #fff;" data-spm="17">
					<div class="common-normal common-slide common-normals" id="doingExp">
						<div class="box-title manage-btn">
				        	<a onclick="javascript:experiment.toAddExp();" class="btn btn-success btn-flat" data-toggle="modal" data-target=".bs-example-modal-lg"><i class="fa fa-plus"></i> 新建</a>
				        </div>
				        <div id="doingLoadDiv">
							<table class="table_" style="text-align: center;">
						       <tr>
						           <td>数据加载中。。。</td>
						       </tr>
						   </table>
				        </div>
					</div>
					<div class="common-normal common-slide common-normals hide" id="doneExp">
						<div class="box-body report-search-div">
				          <div class="form-inline">
				              <label class="form-inline">样本类型：</label>
				              <c:forEach items="${list }" var="dict">
								<c:if test="${dict.flag==1 }">
					      		  <a href="javascript:void(0)" class="samples" onclick="experiment.changeSample(${dict.id },this)" >${dict.name }</a>&nbsp;&nbsp;&nbsp;&nbsp;
								</c:if>
							  </c:forEach>
				          </div>
				          <div class="form-inline">
				            <label class="form-inline">扩增方法：</label>
				           	<c:forEach items="${list }" var="dict">
								<c:if test="${dict.flag==2 }">
									<a href="javascript:void(0)" class="methods" onclick="experiment.changeMethod(${dict.id },this)" >${dict.name }</a>&nbsp;&nbsp;&nbsp;&nbsp;
								</c:if>
							</c:forEach>
				          </div>
				          <div class="form-inline">
				            <label class="form-inline">进行阶段：</label>
				           	<c:forEach items="${list }" var="dict">
								<c:if test="${dict.flag==0 }">
									<a href="javascript:void(0)" class="steps" onclick="experiment.changeStep(${dict.id },this)" >${dict.name }</a>&nbsp;&nbsp;&nbsp;&nbsp;
								</c:if>
							</c:forEach>
				          </div>
				          <div class="form-inline">
				            <label class="form-inline">建库时间：</label>
				           	<label>From：</label><input type="text" id="_startDate" class="Wdate input" onchange="javascript:experiment.changeDate()" onclick="WdatePicker()" readonly="readonly" style="cursor: pointer;width: 90px;">
				       		<label>To：</label><input type="text" id="_endDate" class="Wdate input" onchange="javascript:experiment.changeDate()" onclick="WdatePicker()" readonly="readonly" style="cursor: pointer;width: 90px;">
				       		<label><button class="btn btn-info btn-flat" type="button" onclick="javascript:experiment.changeDate()" style="height:20px;font-size:14px;padding-top:0px;background-color: #85c540;border-color: #85c540;">Go!</button></label>
				       		<span id="_alertSpan" style="color:red;display:none;"></span>
				          </div>
				        </div>
						<div id="doneLoadDiv">
						   <table class="table_" style="text-align: center;">
						       <tr>
						           <td>数据加载中。。。</td>
						       </tr>
						   </table>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
</section>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/experiment.js?version=3.2.4"></script>
<script language="javascript" src="<%=request.getContextPath()%>/plugins/calendar/WdatePicker.js"></script>
<div id="addExp" class="modal modal-green-header">
</div>
