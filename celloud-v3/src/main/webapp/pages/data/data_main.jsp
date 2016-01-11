<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<link href="<%=request.getContextPath() %>/css/data.css" rel="stylesheet" type="text/css" />
<!-- Content Header (Page header) -->
<section class="content-header">
  <h1>
    <small>&nbsp;</small>
  </h1>
  <ol class="breadcrumb">
    <li><a href="javascript:void(0)"><i class="fa fa-tasks"></i> 数据管理</a></li>
    <li class="active">全部</li>
  </ol>
</section>

<!-- Main content -->
<section class="content">
  <div class="row">
    <div class="col-xs-12">
      <!-- COLOR PALETTE -->
<!--       <div class='box box-success color-palette-box'> -->
<!--         <div class='box-header with-border'> -->
<!--         </div> -->
<!--         <div class='box-body'> -->
<!--           	本周上传：30 个| 数据总量：1230 个|  共删除：12个 | 总存储：800 GB -->
<!--         </div> -->
<!--       </div> -->
      <div class="box box-success">
        <div class="box-header">
          <h3 id="manage-data-h3" class="box-title manage-btn" data-step="2" data-position="bottom" data-intro="" data-img="managerdata.png">
          	<a id="run-app-btn" class="btn btn-success btn-flat" data-toggle="modal" data-target=".bs-example-modal-lg"><i class="fa fa-play"></i> 运行</a>
            <a id="del-data-btn" class="btn btn-warning btn-flat disabled" disabled><i class="fa fa-university"></i> 删除</a>
            <a id="manage-data-btn" class="btn btn-info btn-flat disabled" disabled><i class="fa fa-pencil-square-o"></i> 编辑</a>
          </h3>
          <div id="data-spin-div" class="box-tools col-xs-7"></div>
          <div class="box-tools col-xs-4">
            <div class="input-group">
              <input id="data-condition-input" class="form-control input-sm pull-right" name="table_search" type="text" placeholder="搜索文件名/数据标签/文件别名"/>
              <div class="input-group-btn">
                <button id="data-condition-find" class="btn btn-sm btn-default"><i class="fa fa-search"></i></button>
              </div>
            </div>
          </div>
        </div><!-- /.box-header -->
        <div id="data-list-div" class="box-body">
        
        </div><!-- /.box-body -->
      </div><!-- /.box -->
    </div>
  </div><!--/.row-->
</section><!-- /.content -->
  
<!-- All Modal -->
<div id="run-modal" class="modal modal-green-header">
  <div class="modal-dialog">
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
        <h4 class="modal-title">运行</h4>
      </div>
      <div class="modal-body row">
        <div class="col-xs-12">
         <h5>已选数据(点击文件名称取消选择)</h5>
         <ul id="added-data-ul" class="list-inline"></ul>
        </div>
        <div class="col-xs-12">
         <h5>可选App(点击选中)</h5>
         <ul id="apps-data-ul" class="list-inline"></ul>
        </div>
        <div class="col-xs-12">
        </div>
      </div>
      <div class="modal-footer">
        <button type="button" class="btn btn-celloud-close btn-flat pull-left" data-dismiss="modal">关闭</button>
        <button id="run-btn" type="button" class="btn btn-celloud-success btn-flat" disabled="disabled">运行</button>
      </div>
    </div><!-- /.modal-content -->
  </div><!-- /.modal-dialog -->
</div><!-- /.modal -->
<div id="run-error-modal" class="modal modal-celloud-green">
  <div class="modal-dialog">
    <div id="run-error-content" class="modal-content tipModal">
      <div id="run-error-head" class="modal-header">
        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
        <h4 class="modal-title"><i class="icon fa fa-warning"></i>提示</h4>
      </div>
      <div class="modal-body">
        <p id="run-error-text">&hellip;</p>
      </div>
      <div class="modal-footer">
        <button class="btn btn-outline pull-left" type="button" data-dismiss="modal" >返回重选</button>
        <button id="confirm-run" class="btn btn-outline" type="button" data-dismiss="modal" onclick="okToRun()">确定选中</button>
      </div>
    </div><!-- /.modal-content -->
  </div><!-- /.modal-dialog -->
</div><!-- /.modal -->
<div id="tip-modal" class="modal modal-celloud-green">
  <div class="modal-dialog">
    <div id="tip-modal-content" class="modal-content tipModal">
      <div id="tip-modal-head" class="modal-header" id="">
        <button class="close" type="button" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
        <h4 class="modal-title"><i class="icon fa fa-warning"></i>提示</h4>
      </div>
      <div class="modal-body">
        <p id="tip-text">&hellip;</p>
      </div>
      <div class="modal-footer">
        <button type="button" class="btn btn-outline pull-left" data-dismiss="modal">取消</button>
        <button id="check-true" type="button" class="btn btn-outline" data-dismiss="modal" onclick="">确定</button>
      </div>
    </div><!-- /.modal-content -->
  </div><!-- /.modal-dialog -->
</div><!-- /.modal -->
<div id="edit-one-data-modal" class="modal modal-green-header">
  <div class="modal-dialog">
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
        <h4 class="modal-title">更多信息(<span id="edit-dataname-span"></span>)</h4>
      	<input type="hidden" id="dataMoreInfoHidden"/>
      </div>
      <div class="modal-body row">
        <form id="more-data-form" class="form-horizontal form-cel"></form>
      </div>
      <div class="modal-footer">
        <button type="button" class="btn btn-celloud-close btn-flat pull-left" data-dismiss="modal">关闭</button>
        <button type="button" class="btn btn-celloud-success btn-flat" onclick="saveMoreDataInfo()">确 定</button>
      </div>
    </div><!-- /.modal-content -->
  </div><!-- /.modal-dialog -->
</div><!-- /.modal -->
<div id="batch-editdata-modal" class="modal modal-green-header">
  <div class="modal-dialog">
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
        <h4 class="modal-title">批量编辑 <a id="to-each-editdata-modal" href="javascript:void(0);" class="a-white" style="margin-left:30px">单独编辑</a></h4>
      </div>
      <div class="modal-body row">
        <form id="batch-editdata-form" class="form-horizontal form-cel">
          <input id="batch-editdataIds-hide" type="hidden" name="dataIds">
          <div class="form-group">
         	<div class="control-label form-label col-xs-3">文件别名</div>
         	<div class="col-xs-9">
         		<input type="text" name="anotherName" onkeyup="value=value.replace(/[^\u4E00-\u9FA5\w]/g,'') " onbeforepaste="clipboardData.setData('text',clipboardData.getData('text').replace(/[^\u4E00-\u9FA5\w]/g,''))" placeholder="请输入字母\数字\下划线\汉字"/>
         	</div>
          </div>
          <div class="form-group">
         	<div class="control-label form-label col-xs-3">数据标签</div>
         	<div class="col-xs-9">
         		<input type="text" name="dataTags"/>
         	</div>
          </div>
          <div class="form-group">
         	<div class="control-label form-label col-xs-3">样本</div>
         	<div class="col-xs-9">
         		<input type="text" name="sample" maxlength="45"/>
         	</div>
          </div>
          <div class="form-group">
         	<div class="control-label form-label col-xs-3">样本类型/物种</div>
         	<div class="col-xs-9">
         		<span id="dataMoreInfoStrainSpan">
     				<input id="batch-editdatas-strain" type="hidden" name="strain" style="width: 270px;" value=""/>
      			</span>
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
        <button id="save-batch-data" type="button" class="btn btn-celloud-success btn-flat">保存</button>
      </div>
    </div><!-- /.modal-content -->
  </div><!-- /.modal-dialog -->
</div><!-- /.modal -->
<div id="each-editdata-modal" class="modal modal-green-header">
  <div class="modal-dialog">
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
        <h4 class="modal-title">单独编辑<a href="javascript:toManageDatasModel();" class="a-white" style="margin-left:30px">批量编辑</a></h4>
      </div>
      <div id="each-editdatas-div" class="modal-body"></div>
      <div class="modal-footer">
        <button type="button" class="btn btn-celloud-close btn-flat pull-left" data-dismiss="modal">关闭</button>
        <button id="save-each-data" type="button" class="btn btn-celloud-success btn-flat">确 定</button>
      </div>
    </div><!-- /.modal-content -->
  </div><!-- /.modal-dialog -->
</div><!-- /.modal -->
<!-- /All Modal-->
<script src="<%=request.getContextPath()%>/js/data.js" type="text/javascript"></script>