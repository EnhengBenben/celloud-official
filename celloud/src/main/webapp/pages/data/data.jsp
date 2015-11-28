<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
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
                  	<a onclick="showRunApp()" class="btn btn-success btn-flat" style="margin-right:15px;width:100px" data-toggle="modal" data-target=".bs-example-modal-lg"><i class="fa fa-play"></i>Run</a>
                    <a onclick="deleteData()" id="delDataBtn" class="btn btn-warning btn-flat disabled" disabled="disabled">删除</a>
				  	<a onclick="toManageDatasModel()" id="batchManage" class="btn btn-info btn-flat disabled" disabled="disabled">批量管理</a>
                  </h3>
                  <div class="box-tools col-xs-4">
                    <div class="input-group">
                      <input type="text" name="table_search" class="form-control input-sm pull-right" style="width: 200px;" placeholder="搜索文件名/数据标签/文件别名" id="dataTagSearch" onkeyup="javascript:getDataByCondition(1);"/>
                      <div class="input-group-btn">
                        <button class="btn btn-sm btn-default" onclick="getDataByCondition(1)"><i class="fa fa-search"></i></button>
                      </div>
                    </div>
                  </div>
                </div><!-- /.box-header -->
                <div class="box-body" id="selfDataDiv">
                
                </div><!-- /.box-body -->
              </div><!-- /.box -->
            </div>
          </div><!--/.row-->
        </section><!-- /.content -->
    
    <!-- All Modal -->
    <div class="modal modal-green-header" id="runApp">
      <div class="modal-dialog">
        <div class="modal-content">
          <div class="modal-header">
            <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
            <h4 class="modal-title">运行APP</h4>
          </div>
          <div class="modal-body row">
            <div class="col-xs-12">
	            <h5>已选数据(点击文件名称取消选择)</h5>
	            <ul class="list-inline" id="addedDataUl"></ul>
            </div>
            <div class="col-xs-12">
	            <h5>可选App(点击选中)</h5>
	            <ul class="list-inline" id="appsForDataUl"></ul>
            </div>
            <div class="col-xs-12">
	            <div class="alert alert-warning-cel alert-dismissable hide" id="runErrorDiv">
	               <button type="button" class="close" onclick="okToRun(1)"><i class="fa fa-check"></i></button>
	               <button type="button" class="close" onclick="okToRun(0)"><i class="fa fa-close"></i></button>
	               <h5><i class="icon fa fa-warning"></i> <span id="runErrorTitle">Alert!</span></h5>
	               <span id="runError"></span>
	             </div>
             </div>
          </div>
          <div class="modal-footer">
            <button type="button" class="btn btn-celloud-close btn-flat pull-left" data-dismiss="modal">关闭</button>
            <button type="button" class="btn btn-celloud-success btn-flat" onclick="toRunApp()" id="toRunApp" disabled="disabled">运行</button>
          </div>
        </div><!-- /.modal-content -->
      </div><!-- /.modal-dialog -->
    </div><!-- /.modal -->
    <div class="modal modal-celloud-green" id="runErrorModal">
       <div class="modal-dialog">
         <div class="modal-content tipModal" id="runErrorContent">
           <div class="modal-header" id="runErrorHead">
             <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
             <h4 class="modal-title"><i class="icon fa fa-warning"></i>提示</h4>
           </div>
           <div class="modal-body">
             <p id="runErrorText">&hellip;</p>
           </div>
           <div class="modal-footer">
             <button type="button" class="btn btn-outline pull-left" data-dismiss="modal" >返回重选</button>
             <button type="button" class="btn btn-outline" data-dismiss="modal" onclick="okToRun()">确定选中</button>
           </div>
         </div><!-- /.modal-content -->
       </div><!-- /.modal-dialog -->
     </div><!-- /.modal -->
     <div class="modal modal-celloud-green" id="warningModal">
       <div class="modal-dialog">
         <div class="modal-content tipModal" id="tipModal">
           <div class="modal-header" id="tipModalHead">
             <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
             <h4 class="modal-title"><i class="icon fa fa-warning"></i>提示</h4>
           </div>
           <div class="modal-body">
             <p id="warningText">&hellip;</p>
           </div>
           <div class="modal-footer">
             <button type="button" class="btn btn-outline pull-left" data-dismiss="modal">取消</button>
             <button type="button" class="btn btn-outline" data-dismiss="modal" id="checkTrue" onclick="">确定</button>
           </div>
         </div><!-- /.modal-content -->
       </div><!-- /.modal-dialog -->
     </div><!-- /.modal -->
     
    <div class="modal modal-green-header" id="dataMoreInfoModal">
      <div class="modal-dialog">
        <div class="modal-content">
          <div class="modal-header">
            <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
            <h4 class="modal-title">更多信息(<span id="dataMoreNameInfoSpan"></span>)</h4>
          	<input type="hidden" id="dataMoreInfoHidden"/>
          </div>
          <div class="modal-body row">
          	<div style="right: 50px;position:absolute;z-index:9999;">
	 			<a href="javascript:showDataMoreInfoEdit();" class="a-green"><i class="fa fa-edit"></i></a>&nbsp;&nbsp;
	 			<a href="javascript:cancelEditMoreInfo();" class="a-green"><i class="fa fa-close"></i></a>
	 		</div>
            <form class="form-horizontal form-cel" id="moreDatasForm">
	 		</form>
          </div>
          <div class="modal-footer">
            <button type="button" class="btn btn-celloud-close btn-flat pull-left" data-dismiss="modal">关闭</button>
            <button type="button" class="btn btn-celloud-success btn-flat" onclick="saveMoreDataInfo()">确 定</button>
          </div>
        </div><!-- /.modal-content -->
      </div><!-- /.modal-dialog -->
    </div><!-- /.modal -->
    <div class="modal modal-green-header" id="manageDatasModal">
      <div class="modal-dialog">
        <div class="modal-content">
          <div class="modal-header">
            <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
            <h4 class="modal-title">统一编辑数据 <a href="javascript:toManageEachDataModel();" class="a-white" style="margin-left:30px">编辑单个数据</a></h4>
          	<input type="hidden" id="strainDataHide">
          </div>
          <div class="modal-body row">
            <form class="form-horizontal form-cel" id="manageDatasForm">
            	<input type="hidden" name="dataIds" id="dataIdsHide">
	            <div class="form-group">
	            	<div class="control-label form-label col-xs-3">文件别名</div>
	            	<div class="col-xs-9">
	            		<input type="text" name="data.anotherName" onkeyup="value=value.replace(/[^\u4E00-\u9FA5\w]/g,'') " onbeforepaste="clipboardData.setData('text',clipboardData.getData('text').replace(/[^\u4E00-\u9FA5\w]/g,''))" placeholder="请输入字母\数字\下划线\汉字"/>
	            	</div>
	            </div>
	            <div class="form-group">
	            	<div class="control-label form-label col-xs-3">数据标签</div>
	            	<div class="col-xs-9">
	            		<input type="text" name="data.dataTags"/>
	            	</div>
	            </div>
	            <div class="form-group">
	            	<div class="control-label form-label col-xs-3">样本</div>
	            	<div class="col-xs-9">
	            		<input type="text" name="data.sample" maxlength="45"/>
	            	</div>
	            </div>
	            <div class="form-group">
	            	<div class="control-label form-label col-xs-3">样本类型/物种</div>
	            	<div class="col-xs-9">
	            		<span id="dataMoreInfoStrainSpan">
	        				<input type="hidden" name="data.strain" id="manageDatasStrainSel" style="width: 270px;" value=""/>
	        			</span>
	            	</div>
	            </div>
            </form>
            <div class="alert alert-warning-cel alert-dismissable hide" id="manageDataErrorDiv">
			   <button type="button" class="close"><i class="fa fa-close"></i></button>
			   <h5><i class="icon fa fa-warning"></i>保存失败！</h5>
			</div>
          </div>
          <div class="modal-footer">
            <button type="button" class="btn btn-celloud-close btn-flat pull-left" data-dismiss="modal">关闭</button>
            <button type="button" class="btn btn-celloud-success btn-flat" onclick="saveManageDatas()">保存</button>
          </div>
        </div><!-- /.modal-content -->
      </div><!-- /.modal-dialog -->
    </div><!-- /.modal -->
	<div class="modal modal-green-header" id="manageEachDataModal">
      <div class="modal-dialog">
        <div class="modal-content">
          <div class="modal-header">
            <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
	   	  	<h4 class="modal-title">编辑单个数据<a href="javascript:toManageDatasModel();" class="a-white" style="margin-left:30px">统一编辑数据</a></h4>
          </div>
          <div class="modal-body" id="eachDatasDiv">
          </div>
          <div class="modal-footer">
            <button type="button" class="btn btn-celloud-close btn-flat pull-left" data-dismiss="modal">关闭</button>
            <button type="button" class="btn btn-celloud-success btn-flat" onclick="saveEachData()">确 定</button>
          </div>
        </div><!-- /.modal-content -->
      </div><!-- /.modal-dialog -->
    </div><!-- /.modal -->
     <!-- /All Modal-->
	<script src="<%=request.getContextPath()%>/js/data.js?version=2.24" type="text/javascript"></script>
	<!-- spin:loading效果 end-->
	<script type="text/javascript">
		var session_userId = <%=session.getAttribute("userId")%>;
		var sessionUserName = "<%=session.getAttribute("userName")%>";
		//检验session是否超时
		if(!session_userId){
			window.top.location = "<%=request.getContextPath() %>/toLogin";
		}
		$.ajaxSetup ({
			cache: false //关闭AJAX相应的缓存
		});
		$(document).ready(function(){
			initData();
		});
	</script>
