<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
  <head>
    <meta charset="UTF-8">
    <title>数据管理</title>
    <meta content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no" name="viewport">
    <link rel="stylesheet" href="//cdn.bootcss.com/bootstrap/3.3.5/css/bootstrap.min.css"> 
    <link href="//cdn.bootcss.com/font-awesome/4.4.0/css/font-awesome.min.css" rel="stylesheet">    
    <link href="<%=request.getContextPath() %>/dist/css/celloud.css?version=1.01" rel="stylesheet" type="text/css" />
  </head>
  <style>
  	.tipModal{width:300px;margin:auto}
  </style>
  <body>
    <div class="wrapper">
      <!-- Content Wrapper. Contains page content -->
      <div class="content-wrapper">
        <!-- Content Header (Page header) -->
        <section class="content-header">
          <h1>
            <small>&nbsp;</small>
          </h1>
          <ol class="breadcrumb">
            <li><a href="#"><span class="glyphicon glyphicon-th-list" aria-hidden="true"></span>数据管理</a></li>
            <li class="active">全部</li>
          </ol>
        </section>

        <!-- Main content -->
        <section class="content">
          <div class="row">
            <div class="col-xs-12">
              <!-- COLOR PALETTE -->
<!--               <div class='box box-success color-palette-box'> -->
<!--                 <div class='box-header with-border'> -->
<!--                 </div> -->
<!--                 <div class='box-body'> -->
<!--                   	本周上传：30 个| 数据总量：1230 个|  共删除：12个 | 总存储：800 GB -->
<!--                 </div>/.box-body -->
<!--               </div>/.box -->
              <div class="box">
                <div class="box-header">
                  <h3 class="box-title">
                  	<button onclick="showRunApp()" class="btn btn-success btn-flat" style="margin-right:15px;width:100px" data-toggle="modal" data-target=".bs-example-modal-lg"><i class="fa fa-play"></i>Run</button>
<!--                     <button onclick="uploadFile()" class="btn btn-warning btn-flat" style="position: relative;margin-right:15px;width:100px"><span class="badge bg-green" style="position: absolute;top: -3px;right: -10px;font-size: 10px;font-weight: 400;">...</span><i class="fa fa-cloud-upload"></i>上传</button> -->
                    <div class="btn-group">
                      <button type="button" class="btn btn-info btn-flat" id="appTextBtn" onclick="javascript:runMultiDataNew();">选择App</button>
                      <button type="button" class="btn btn-info btn-flat dropdown-toggle" data-toggle="dropdown" id="dataSelectAppBtn" onclick="javascript:showAppsForData();">
                        <span class="caret"></span>
                        <span class="sr-only">Toggle Dropdown</span>
                      </button>
<!--                       <ul class="dropdown-menu" role="menu" id="appsForDataUl"> -->
<!--                         <li><a href="#">修改</a></li> -->
<!--                         <li><a href="#">归档</a></li> -->
<!--                         <li class="divider"></li> -->
<!--                         <li><a href="#">详细</a></li> -->
<!--                       </ul> -->
                    </div>
                    <button onclick="javascript:deleteDatas();" id="delDataBtn" class="btn btn-warning btn-flat disabled" disabled="disabled">删除</button>
				  	<button onclick="javascript:tobatchManageModel();" id="batchManage" class="btn disabled" disabled="disabled">批量管理</button>
<!--                     <input type="checkbox">已运行 -->
<!--                     <input type="checkbox">未运行 -->
                  </h3>
                  <div class="box-tools col-xs-4">
                    <div class="input-group">
                      <input type="text" name="table_search" class="form-control input-sm pull-right" style="width: 150px;" placeholder="Search"/>
                      <div class="input-group-btn">
                        <button class="btn btn-sm btn-default"><i class="fa fa-search"></i></button>
                      </div>
                    </div>
                  </div>
                </div><!-- /.box-header -->
                <div class="box-body" id="selfDataDiv">
                  <table class="table table-hover">
                    <tr>
                      <th style="width: 10px">#</th>
                      <th>数据编号</th>
                      <th>数据名称</th>
                      <th>数据别名</th>
                      <th>数据大小</th>
                      <th>上传时间</th>
                      <th>运行状态</th>
                    </tr>
                    <tr>
                      <td><input type="checkbox"></td>
                      <td>20150722134128</td>
                      <td>CelLoudYACZ8O6D.ab1 </td>
                      <td>test</td>
                      <td>140KB</td>
                      <td>2015-07-22</td>
                      <td><span class="label label-success">已运行</span></td>
                    </tr>
                    <tr>
                      <td><input type="checkbox"></td>
                      <td>20150722134128</td>
                      <td>CelLoudYACZ8O6D.ab1 </td>
                      <td>test</td>
                      <td>140KB</td>
                      <td>2015-07-22</td>
                      <td><span class="label label-warning">未运行</span></td>
                    </tr>
                    <tr>
                      <td><input type="checkbox"></td>
                      <td>20150722134128</td>
                      <td>CelLoudYACZ8O6D.ab1 </td>
                      <td>test</td>
                      <td>140KB</td>
                      <td>2015-07-22</td>
                      <td><span class="label label-success">已运行</span></td>
                    </tr>
                    <tr>
                      <td><input type="checkbox"></td>
                      <td>20150722134128</td>
                      <td>CelLoudYACZ8O6D.ab1 </td>
                      <td>test</td>
                      <td>140KB</td>
                      <td>2015-07-22</td>
                      <td><span class="label label-warning">未运行</span></td>
                    </tr>
                  </table>
                </div><!-- /.box-body -->
              </div><!-- /.box -->
            </div>
          </div><!--/.row-->
        </section><!-- /.content -->
      </div><!-- /.content-wrapper -->
    </div><!-- ./wrapper -->
    
    <!-- All Modal -->
    <div class="modal" id="runApp">
      <div class="modal-dialog">
        <div class="modal-content">
          <div class="modal-header">
            <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
            <h4 class="modal-title">运行APP</h4>
          </div>
          <div class="modal-body">
            <p>已选数据：</p>
            <ul class="list-inline" id="addedDataUl"></ul>
            <p>可选App：</p>
            <ul class="list-inline" id="appsForDataUl"></ul>
            <div id="runTips"></div>
            <p>已选App：</p>
            <ul id="toRunApp"></ul>
          </div>
          <div class="modal-footer">
            <button type="button" class="btn btn-default btn-flat pull-left" data-dismiss="modal">关闭</button>
            <button type="button" class="btn btn-success btn-flat">运行</button>
          </div>
        </div><!-- /.modal-content -->
      </div><!-- /.modal-dialog -->
    </div><!-- /.modal -->
    
     <div class="modal modal-warning" id="warningModal">
       <div class="modal-dialog">
         <div class="modal-content tipModal">
           <div class="modal-header">
             <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
             <h4 class="modal-title">提示</h4>
           </div>
           <div class="modal-body">
             <p id="warningText">&hellip;</p>
           </div>
           <div class="modal-footer">
             <button type="button" class="btn btn-outline pull-left" data-dismiss="modal">取消</button>
             <button type="button" class="btn btn-outline" data-dismiss="modal">确定</button>
           </div>
         </div><!-- /.modal-content -->
       </div><!-- /.modal-dialog -->
     </div><!-- /.modal -->
     <!-- /All Modal-->
    <script src="//cdn.bootcss.com/jquery/1.11.3/jquery.min.js"></script>
    <script src="//cdn.bootcss.com/jqueryui/1.11.4/jquery-ui.min.js"></script>
    <script src="//cdn.bootcss.com/bootstrap/3.3.5/js/bootstrap.min.js"></script>
    <!-- FastClick -->
    <script src="//cdn.bootcss.com/fastclick/1.0.6/fastclick.min.js"></script>
    <!-- jquery_alert_dialogs begin -->
	<script src="<%=request.getContextPath()%>/plugins/jquery_alert_dialogs/jquery.ui.draggable.js" type="text/javascript"></script>
	<script src="<%=request.getContextPath()%>/plugins/jquery_alert_dialogs/jquery.alerts.js" type="text/javascript"></script>
	<!-- jquery_alert_dialogs end -->
    <script src="<%=request.getContextPath() %>/dist/js/celloud.js" type="text/javascript"></script>
    <script src="<%=request.getContextPath()%>/plugins/spin.min.js" type="text/javascript"></script>
	<script src="<%=request.getContextPath()%>/js/data.js?version=1.02" type="text/javascript"></script>
	<!-- spin:loading效果 end-->
	<script type="text/javascript">
		var session_userId = <%=session.getAttribute("userId")%>;
		var sessionUserName = "<%=session.getAttribute("userName")%>";
		$.ajaxSetup ({
			cache: false //关闭AJAX相应的缓存
		});
		$(document).ready(function(){
			initData();
		});
	</script>
  </body>
</html>