<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div class="modal-dialog">
  <div class="modal-content">
    <div class="modal-header">
      <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
      <h4 class="modal-title">编辑实验流程</h4>
    </div>
    <div class="modal-body row">
      <form id="exp-add-form" class="form-horizontal form-cel exp-form">
   	<div class="col-xs-6">
		<div class="form-group">
			<div class="control-label form-label col-xs-4">编号</div>
			<div class="col-xs-8">
				<input type="text" name="number" value="${experiment.number }" id="expnumber" onchange="experiment.changeName()"/>
				<input type="hidden" value="${experiment.number }" id="expOriginNum"/>
			</div>
		</div>
		<div class="form-group">
			<div class="control-label form-label col-xs-4">浓度</div>
			<div class="col-xs-8">
				<input type="text" name="concentration" value="${experiment.concentration }" id="expCon"/>
			</div>
		</div>
		<div class="form-group">
			<div class="control-label form-label col-xs-4">质控</div>
			<div class="col-xs-8">
				<select name="quality" id="quality" style="width:60%;">
					<option value="0" <c:if test="${experiment.quality==0 }">selected="selected"</c:if>>合格</option>
					<option value="1" <c:if test="${experiment.quality==1 }">selected="selected"</c:if>>不合格</option>
				</select>
			</div>
		</div>
		<div class="form-group">
			<div class="control-label form-label col-xs-4">库浓度</div>
			<div class="col-xs-8">
				<input type="text" name="libraryConcentration" value="${experiment.libraryConcentration }" id="expLibCon"/>
				<input type="hidden" value="${dataStep }" id="dataStep"/>
			</div>
		</div>
		<div class="form-group">
			<div class="control-label form-label col-xs-4">阶段</div>
			<div class="col-xs-8">
				<select name="step" id="step" style="width:80%;">
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
				<select name="sampleType" id="sampleType" style="width:80%;">
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
				<select name="amplificationMethod" id="amplificationMethod" style="width:80%;">
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
				<select name="sequenator" onchange="experiment.selectSeqIndex(this.value)" id="sequenator" style="width:80%;">
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
				<select name="seqIndex" id="expSeqIndex" style="width:80%;" >
                </select>
			</div>
		</div>
       </div>
       <div class="col-xs-12">
       	<div class="form-group">
			<div class="control-label form-label col-xs-2">其他</div>
			<div class="col-xs-10">
				<textarea rows="3" cols="60" name="other" id="expOther">${experiment.other }</textarea>
			</div>
		</div>
       </div>
       <input type="hidden" name="state" value="${experiment.state }" id="expState"/>
       <input type="hidden" name="id" value="${experiment.id }"/>
       <input type="hidden" name="fileId" id="expFileId" value="${experiment.fileId }"/>
       <input type="hidden" name="dataKey" id="expDataKey" value="${experiment.dataKey }"/>
      </form>
    </div>
    <div id="exp-add-error" class="alert alert-warning-cel alert-dismissable hide">
		<h5 style="text-align: center;" id="expErrorInfo">保存失败！</h5>
	</div>
    <div id="showData" class="hide">
	</div>
    <div class="modal-footer">
      <button type="button" class="btn btn-celloud-close btn-flat pull-left" data-dismiss="modal">关闭</button>
      <button onclick="javascript:experiment.closeExperiment()" type="button" class="btn btn-celloud-success btn-flat">关闭实验</button>
      <button onclick="javascript:experiment.updateExperiment()" id="expEditSubmit" type="button" class="btn btn-celloud-success btn-flat">保存</button>
      <button onclick="javascript:experiment.expBindData()" id="expBindDataSubmit" type="button" class="btn btn-celloud-success btn-flat hide">保存</button>
    </div>
  </div><!-- /.modal-content -->
  <script type="text/javascript">
  $(function(){
      $("#sequenator").select2();
      $("#quality").select2();
      $("#step").select2();
      $("#amplificationMethod").select2();
      $("#sampleType").select2();
      
      var val = $("#sequenator").val();
      $("#expSeqIndex").append("<option value='0'>--请选择--</option>");
      if(val == 14 || val == 15){
          for(var i = 1; i <= 96; i++){
        	  var s = ("${experiment.seqIndex }" == i)?"selected":"";
              $("#expSeqIndex").append("<option value='"+ i +"' "+s+">"+ i +"</option>");
          }
      }else if(val == 16){
          for(var i = 1; i <= 9; i++){
        	  var s = ("${experiment.seqIndex }" == "S503N70" + i)?"selected":"";
              $("#expSeqIndex").append("<option value='S503N70"+ i +"' "+ s +">S503N70"+ i +"</option>");
          }
          for(var i = 10; i <= 12; i++){
              var s = ("${experiment.seqIndex }" == "S503N7" + i)?"selected":"";
              $("#expSeqIndex").append("<option value='S503N7"+ i +"' "+ s +">S503N7"+ i +"</option>");
          }
          
          for(var i = 1; i <= 9; i++){
        	  var s = ("${experiment.seqIndex }" == "S504N70" + i)?"selected":"";
              $("#expSeqIndex").append("<option value='S504N70"+ i +"' " + s + ">S504N70"+ i +"</option>");
          }
          for(var i = 10; i <= 12; i++){
              var s = ("${experiment.seqIndex }" == "S504N7" + i)?"selected":"";
              $("#expSeqIndex").append("<option value='S504N7"+ i +"' "+ s +">S504N7"+ i +"</option>");
          }
      }

      $("#expSeqIndex").select2();
      
  });
  </script>
</div>