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
				<i class="fa fa-user"></i> 实验流程
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
							<a href="javascript:experiment.getPageList();">Doing</a>
						</li>
						<li id="doneTab">
							<a href="javascript:experiment.getDonePageList();">Done</a>
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
<script type="text/javascript" src="<%=request.getContextPath()%>/js/experiment.js?version=3.0"></script>

<div id="addExp" class="modal modal-green-header">
</div>
