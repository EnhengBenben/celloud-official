<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<link href="<%=request.getContextPath() %>/css/report.css?version=3,0" rel="stylesheet">
<link href="<%=request.getContextPath() %>/css/buttons.css?version=20150730" rel="stylesheet">
<link href="<%=request.getContextPath() %>/plugins/font-awesome-4.4.0/css/font-awesome.min.css" rel="stylesheet"/>
<link href="<%=request.getContextPath() %>/plugins/select/select2.css" rel="stylesheet"/>
<link href="<%=request.getContextPath() %>/plugins/backToTop/toTop.1.0.css" rel="stylesheet">
<link href="<%=request.getContextPath()%>/plugins/jquery_alert_dialogs/jquery.alerts.css" rel="stylesheet" type="text/css" media="screen" />
<style>
.tab-content>.tab-pane {
    display: block;
}
</style>
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
      		  <a href="javascript:void(0)" onclick="changeDate(-1,this)" class="cdate">一天以内</a>&nbsp;&nbsp;&nbsp;&nbsp;
      		  <a href="javascript:void(0)" onclick="changeDate(-7,this)" class="cdate">七天以内</a>&nbsp;&nbsp;&nbsp;&nbsp;
      		  <a href="javascript:void(0)" onclick="changeDate(-30,this)" class="cdate">三十天以内</a>&nbsp;&nbsp;&nbsp;&nbsp;
       		  <label>From：</label><input type="text" onchange="changeDate(0,obj)" id="_searchDate" class="Wdate input" onclick="WdatePicker()" readonly="readonly" style="cursor: pointer;width: 90px;"/>
       		  <label>To：</label><input type="text" onchange="changeDate(0,obj)" id="_endDate" class="input Wdate" onclick="WdatePicker()" readonly="readonly" style="cursor: pointer;width: 90px;"/>
       		  <span id="_alertSpan" style="color:red;display:none;"></span>
          </div><!-- /.form group -->
          <div class="form-inline">
              <label class="form-inline" style="padding-top:-5px;font-family:黑体;font-size:14px;font-weight: lighter;">APP：</label>
           		<a href="javascript:void(0)" class="capp _appred" onclick="changeApp(0,this)">全部</a>&nbsp;&nbsp;
           		<span id="showAppDiv" style="margin-right: 0;">
           		</span>
           		<div style="float: right;" id="showMore">
           			<a href="javascript:void(0)" onclick="showSuper(this,'appList')">更多 <img id="sortCreateDate" src="/celloud/images/report/descending.png"></a>
           		</div>
           		<div id="appList" class="none">
           		
           		</div>
          </div><!-- /.form group -->
			<div class="form-inline" style="margin-bottom:10px">
				<label class="form-inline" style="font-family:黑体;font-size:14px;font-weight: lighter;">数据：</label>
				<div class="input-group" style="margin-left:10px;width:330px;height:20px;">
					<input type="text" class="form-control" style="height:20px;" id="_fileName" onchange="changeFileName()">
					<span class="input-group-btn">
						<button class="btn btn-info btn-flat" type="button" onclick="submitSearch()" style="height:20px;font-size:14px;padding-top:0px">Go!</button>
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
<div class="modal hide fade" id="shareProjectModal" >
	<div class="modal-header">
		<input type="hidden" id="proIdHidden"/>
		<input type="hidden" id="proOwnerHidden"/>
		<a class="close" data-dismiss="modal">×</a>
        <span id="proNameSpan">项目名称：</span>
    	<h3><img src="<%=request.getContextPath()%>/images/publicIcon/icon-modal-03.png"/>项目共享</h3>
  	</div>
  	<div class="modal-body">
		<p>共计<span id="proDataCountSpan"></span>个数据文件</p>
        <input type="hidden" id="proSel" style="width:564px;" value=""/>
        <span id="shareProPrompt"></span>
  	</div>
  	<div class="modal-footer">
    	<a href="javascript:saveShareProject();" class="btn">确 定</a>
  	</div>
</div>
<!-- jquery_alert_dialogs begin -->
<script src="<%=request.getContextPath()%>/plugins/jquery_alert_dialogs/jquery.ui.draggable.js" type="text/javascript"></script>
<script src="<%=request.getContextPath()%>/plugins/jquery_alert_dialogs/jquery.alerts.js" type="text/javascript"></script>
<!-- jquery_alert_dialogs end -->
<script src="<%=request.getContextPath() %>/plugins/backToTop/toTop.1.0.js"></script>
<script src="<%=request.getContextPath() %>/plugins/select/select2.min.js"></script>
<script src="<%=request.getContextPath() %>/plugins/select/select2_locale_zh-CN.js"></script>
<script src="<%=request.getContextPath() %>/plugins/calendar/WdatePicker.js"></script>
<script src="<%=request.getContextPath() %>/js/report.js?version=1.5"></script>
<script src="<%=request.getContextPath() %>/js/dataformat.js"></script>
<script src="<%=request.getContextPath() %>/js/codon.js"></script>
<script type="text/javascript">
var session_userId = <%=session.getAttribute("userId")%>;
var sessionUserName = "<%=session.getAttribute("userName")%>";
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
