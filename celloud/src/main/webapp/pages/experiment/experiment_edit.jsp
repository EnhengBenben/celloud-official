<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div class="modal-dialog">
  <div class="modal-content">
    <div class="modal-header">
      <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
      <h4 class="modal-title">编辑样本</h4>
    </div>
    <div class="modal-body row">
      <form id="exp-add-form" class="form-horizontal form-cel exp-form">
   	<div class="col-xs-6">
		<div class="form-group">
			<div class="control-label form-label col-xs-4">编号</div>
			<div class="col-xs-8">
				<input type="text" name="number" value="${experiment.number }"/>
			</div>
		</div>
		<div class="form-group">
			<div class="control-label form-label col-xs-4">浓度</div>
			<div class="col-xs-8">
				<input type="text" name="concentration" value="${experiment.concentration }"/>
			</div>
		</div>
		<div class="form-group">
			<div class="control-label form-label col-xs-4">质控</div>
			<div class="col-xs-8">
				<input type="text" name="quality" value="${experiment.quality }"/>
			</div>
		</div>
		<div class="form-group">
			<div class="control-label form-label col-xs-4">库浓度</div>
			<div class="col-xs-8">
				<input type="text" name="libraryConcentration" value="${experiment.libraryConcentration }"/>
			</div>
		</div>
		<div class="form-group">
			<div class="control-label form-label col-xs-4">阶段</div>
			<div class="col-xs-8">
				<select name="step">
					<option value="0">--请选择--</option>
					<c:forEach items="${list }" var="dict">
						<c:if test="${dict.flag==0 }">
							<option value="${dict.id }" <c:if test="${experiment.step==dict.id }">selected="selected"</c:if>>${dict.name }</option>
						</c:if>
					</c:forEach>
				</select>
			</div>
		</div>
       </div>
       <div class="col-xs-6">
	    <div class="form-group">
			<div class="control-label form-label col-xs-4">样本类型</div>
			<div class="col-xs-8">
				<select name="sampleType">
					<option value="0">--请选择--</option>
					<c:forEach items="${list }" var="dict">
						<c:if test="${dict.flag==1 }">
							<option value="${dict.id }" <c:if test="${experiment.sampleType==dict.id }">selected="selected"</c:if>>${dict.name }</option>
						</c:if>
					</c:forEach>
				</select>
			</div>
		</div>
	    <div class="form-group">
			<div class="control-label form-label col-xs-4">扩增方法</div>
			<div class="col-xs-8">
				<select name="amplificationMethod">
					<option value="0">--请选择--</option>
					<c:forEach items="${list }" var="dict">
						<c:if test="${dict.flag==2 }">
							<option value="${dict.id }" <c:if test="${experiment.amplificationMethod==dict.id }">selected="selected"</c:if>>${dict.name }</option>
						</c:if>
					</c:forEach>
				</select>
			</div>
		</div>
	    <div class="form-group">
			<div class="control-label form-label col-xs-4">测序仪</div>
			<div class="col-xs-8">
				<select name="sequenator">
					<option value="0">--请选择--</option>
					<c:forEach items="${list }" var="dict">
						<c:if test="${dict.flag==3 }">
							<option value="${dict.id }" <c:if test="${experiment.sequenator==dict.id }">selected="selected"</c:if>>${dict.name }</option>
						</c:if>
					</c:forEach>
				</select>
			</div>
		</div>
	    <div class="form-group">
			<div class="control-label form-label col-xs-4">Index</div>
			<div class="col-xs-8">
				<input type="text" name="seqIndex" value="${experiment.seqIndex }"/>
			</div>
		</div>
       </div>
       <div class="col-xs-12">
       	<div class="form-group">
			<div class="control-label form-label col-xs-2">其他</div>
			<div class="col-xs-10">
				<textarea rows="3" cols="60" name="other">${experiment.other }</textarea>
			</div>
		</div>
       </div>
       <input type="hidden" name="state" value="${experiment.state }" id="expState"/>
       <input type="hidden" name="id" value="${experiment.id }"/>
      </form>
		<div id="batch-editdata-error" class="alert alert-warning-cel alert-dismissable hide">
			<button type="button" class="close"><i class="fa fa-close"></i></button>
			<h5><i class="icon fa fa-warning"></i>保存失败！</h5>
		</div>
    </div>
    <div class="modal-footer">
      <button type="button" class="btn btn-celloud-close btn-flat pull-left" data-dismiss="modal">关闭</button>
      <button onclick="javascript:experiment.closeExperiment()" type="button" class="btn btn-celloud-success btn-flat">关闭实验</button>
      <button onclick="javascript:experiment.updateExperiment()" type="button" class="btn btn-celloud-success btn-flat">保存</button>
    </div>
  </div><!-- /.modal-content -->
</div>