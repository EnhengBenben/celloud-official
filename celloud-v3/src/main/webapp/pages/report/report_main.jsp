<%@page import="com.celloud.model.User"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<link href="<%=request.getContextPath() %>/plugins/backToTop/toTop.1.0.css" rel="stylesheet">
<link href="<%=request.getContextPath() %>/css/report.css?version=3.6" rel="stylesheet">
<section class="content-header">
  <h1>
    <small>&nbsp;</small>
  </h1>
  <ol class="breadcrumb">
    <li><a href="javascript:void(0)"><i class="fa fa-files-o"></i> 报告</a></li>
    <li class="active">全部</li>
  </ol>
</section>
<section class="content">
  <div class="row">
    <div class="col-xs-12">
      <div class="box box-success color-palette-bo">
<!--         <div class="box-header with-border" style="padding-left:30px;padding-bottom: 0px;font-family:黑体;font-size:14px;"> -->
<!--         	<p style="font-family:黑体;font-size:14px"> 本周数据报告：30 | 数据报告总量：230 个</p> -->
<!--         </div> -->
        <div class="box-body" style="padding-left:30px;padding-bottom: 0px;font-family:黑体;font-size:14px;">
          <div class="form-inline">
              <label class="form-inline" style="font-family:黑体;font-size:14px;font-weight: lighter;">时间：</label>
              <a href="javascript:void(0)" onclick="changeDate('allTime',this)" class="cdate _datered">全部</a>&nbsp;&nbsp;&nbsp;&nbsp;
      		  <a href="javascript:void(0)" onclick="changeDate(-1,this)" class="cdate">24小时</a>&nbsp;&nbsp;&nbsp;&nbsp;
      		  <a href="javascript:void(0)" onclick="changeDate(-3,this)" class="cdate">3天</a>&nbsp;&nbsp;&nbsp;&nbsp;
      		  <a href="javascript:void(0)" onclick="changeDate(-7,this)" class="cdate">7天</a>&nbsp;&nbsp;&nbsp;&nbsp;
      		  <a href="javascript:void(0)" onclick="changeDate(-30,this)" class="cdate">30天</a>&nbsp;&nbsp;&nbsp;&nbsp;
       		  <label>From：</label><input type="text" onchange="changeDate(0,obj)" id="_searchDate" class="Wdate input" onclick="WdatePicker()" readonly="readonly" style="cursor: pointer;width: 90px;"/>
       		  <label>To：</label><input type="text" onchange="changeDate(0,obj)" id="_endDate" class="input Wdate" onclick="WdatePicker()" readonly="readonly" style="cursor: pointer;width: 90px;"/>
       		  <span id="_alertSpan" style="color:red;display:none;"></span>
          </div><!-- /.form group -->
          <div class="form-inline">
              <label class="form-inline" style="padding-top:-5px;font-family:黑体;font-size:14px;font-weight: lighter;">应用：</label>
           		<a href="javascript:void(0)" class="capp _appred" onclick="changeApp(0,this)">全部</a>
           		<span id="showAppDiv" style="margin-right: 0;">
           		</span>
           		<div style="float: right;" id="showMore">
           			<a href="javascript:void(0)" onclick="showSuper(this,'appList')">更多 <i id="sortCreateDate" class="fa fa-angle-double-down"></i></a>
           		</div>
           		<div id="appList" class="none">
           		
           		</div>
          </div><!-- /.form group -->
			<div class="form-inline" style="margin-bottom:10px">
				<label class="form-inline" style="font-family:黑体;font-size:14px;font-weight: lighter;">数据：</label>
				<div class="input-group" style="width:330px;height:20px;">
					<input type="text" class="form-control" style="height:20px;padding: 0px 12px;" id="_fileName" onchange="changeFileName()" placeholder="检索文件名/别名/数据编号">
					<span class="input-group-btn">
						<button class="btn btn-info btn-flat" type="button" onclick="submitSearch()" style="height:20px;font-size:14px;padding-top:0px;background-color: #85c540;border-color: #85c540;">Go!</button>
					</span>
				</div>
			</div><!-- /.form group -->
        </div><!-- /.box-body -->
      </div><!-- /.box -->
      <!-- COLOR PALETTE -->
      <div id="reportLoading" style="padding-left: 500px;"></div>
      <div class="box color-palette-box">
        <div class="box-body" id="selfReportDiv">
        
        </div><!-- /.box-body -->
      </div><!-- /.box -->
    </div>
  </div><!--/.row-->
</section>
<!--项目共享-->
<div class="modal modal-green-header in" id="shareProjectModal" >
	<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-header">
				<input type="hidden" id="proIdHidden"/>
				<a class="close" data-dismiss="modal">×</a>
		    	<h4 class="modal-title">
		    		项目共享
		    		<a href="javascript:void(0);" class="a-white" style="margin-left:30px"><span id="proNameSpan">项目名称：</span></a>
		    	</h4>
		  	</div>
		  	<div class="modal-body row">
		  		<div class="col-xs-1">
		  		</div>
		  		<div class="col-xs-10">
					<p>共计<span id="proDataCountSpan"></span>个数据文件</p>
			        <input type="hidden" id="proSel" style="width:470px;" value=""/>
			        <span id="shareProPrompt"></span>
		  		</div>
		  	</div>
		  	<div class="modal-footer">
		    	<a href="javascript:saveShareProject();" class="btn btn-celloud-success btn-flat">确 定</a>
		  	</div>
		</div>
	</div>
</div>

<script src="<%=request.getContextPath() %>/plugins/backToTop/toTop.1.0.js"></script>
<script src="<%=request.getContextPath() %>/plugins/calendar/WdatePicker.js"></script>
<script src="<%=request.getContextPath() %>/js/report.js?version=1.0"></script>
<script src="<%=request.getContextPath() %>/js/report_codon.js"></script>
<script type="text/javascript">
var session_userId = <%=session.getAttribute("userId")%>;
var sessionUserName = "<%=((User)session.getAttribute("loginUserInSession")).getUsername() %>";
$.ajaxSetup ({
	cache: false //关闭AJAX相应的缓存
});
$(document).ready(function(){
	document.onkeydown = function(e){
		var ev = document.all ? window.event : e;
		if(ev.keyCode==13) {
			changeFileName();
			submitSearch();
		}
	};
	initReportDetail();
});
</script>
