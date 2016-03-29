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
					<div class="box-title manage-btn">
			        	<a onclick="javascript:experiment.toAddExp();" class="btn btn-success btn-flat" data-toggle="modal" data-target=".bs-example-modal-lg"><i class="fa fa-plus"></i> 新建</a>
			        </div>
					<div class="common-normal common-slide common-normals" id="doingExp">
						<table class="table_" style="text-align: center;">
					       <tr>
					           <td>数据加载中。。。</td>
					       </tr>
					   </table>
					</div>
					<div class="common-normal common-slide common-normals hide" id="doneExp">
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
</section>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/experiment.js?version=3.0"></script>

<div id="addExp" class="modal modal-green-header">
  <div class="modal-dialog">
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
        <h4 class="modal-title">新增样本</h4>
      </div>
      <div class="modal-body row">
        <form id="batch-editdata-form" class="form-horizontal form-cel exp-form">
	    	<div class="col-xs-6">
				<div class="form-group">
					<div class="control-label form-label col-xs-4">编号</div>
					<div class="col-xs-8">
						<input type="text" name="number"/>
					</div>
				</div>
				<div class="form-group">
					<div class="control-label form-label col-xs-4">浓度</div>
					<div class="col-xs-8">
						<input type="text" name="concentration"/>
					</div>
				</div>
				<div class="form-group">
					<div class="control-label form-label col-xs-4">质控</div>
					<div class="col-xs-8">
						<input type="text" name="quality"/>
					</div>
				</div>
				<div class="form-group">
					<div class="control-label form-label col-xs-4">库浓度</div>
					<div class="col-xs-8">
						<input type="text" name="libraryConcentration"/>
					</div>
				</div>
				<div class="form-group">
					<div class="control-label form-label col-xs-4">阶段</div>
					<div class="col-xs-8">
						<input type="text" name="step"/>
					</div>
				</div>
	        </div>
	        <div class="col-xs-6">
			    <div class="form-group">
					<div class="control-label form-label col-xs-4">样本类型</div>
					<div class="col-xs-8">
						<input type="text" name="sampleType"/>
					</div>
				</div>
			    <div class="form-group">
					<div class="control-label form-label col-xs-4">扩增方法</div>
					<div class="col-xs-8">
						<input type="text" name="amplificationMethod"/>
					</div>
				</div>
			    <div class="form-group">
					<div class="control-label form-label col-xs-4">测序仪</div>
					<div class="col-xs-8">
						<input type="text" name="sequenator"/>
					</div>
				</div>
			    <div class="form-group">
					<div class="control-label form-label col-xs-4">Index</div>
					<div class="col-xs-8">
						<input type="text" name="seqIndex"/>
					</div>
				</div>
	        </div>
	        <div class="col-xs-12">
	        	<div class="form-group">
					<div class="control-label form-label col-xs-2">其他</div>
					<div class="col-xs-10">
						<textarea rows="3" cols="60" name="其他"></textarea>
					</div>
				</div>
	        </div>
        </form>
        <div id="batch-editdata-error" class="alert alert-warning-cel alert-dismissable hide">
		   <button type="button" class="close"><i class="fa fa-close"></i></button>
		   <h5><i class="icon fa fa-warning"></i>保存失败！</h5>
		</div>
      </div>
      <div class="modal-footer">
        <button type="button" class="btn btn-celloud-close btn-flat pull-left" data-dismiss="modal">关闭</button>
        <button onclick="javascript:experiment.addExperiment()" type="button" class="btn btn-celloud-success btn-flat">保存</button>
      </div>
    </div><!-- /.modal-content -->
  </div><!-- /.modal-dialog -->
</div>
