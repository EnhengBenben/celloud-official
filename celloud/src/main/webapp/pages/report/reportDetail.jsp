<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<div class="tabbable"> <!-- tabs -->
	<ul class="nav-tabs">
	    <li id="liReportList"><a href="javascript:void(0)" data-toggle="tab" id="reportListTab" onclick="javascript:showTab(0);"><i class="i-reportList"></i>报告列表</a></li>
	    <li id="liReport"><a href="javascript:void(0)" data-toggle="tab" id="reportTab" onclick="javascript:showTab(1);"><i class="i-report"></i>数据报告</a></li>
	</ul>
    <div class="tab-content">
    	<div class="tab-pane" id="reListTab"> 
           	<div id="searchReportDiv" class="m-fileList">
           	  <div class="_super">
      			<label>时间：</label>
      			<a href="javascript:void(0)" onclick="changeDate('allTime',this)" class="cdate _datered">全部</a>
      			<a href="javascript:void(0)" onclick="changeDate(-1,this)" class="cdate">一天以内</a>
      			<a href="javascript:void(0)" onclick="changeDate(-7,this)" class="cdate">七天以内</a>
      			<a href="javascript:void(0)" onclick="changeDate(-30,this)" class="cdate">三十天以内</a>
       			<label>From：</label><input type="text" onchange="changeDate(0,obj)" id="_searchDate" class="Wdate input" onclick="WdatePicker()" readonly="readonly" style="cursor: pointer;width: 90px;"/>
       			<label>To：</label><input type="text" onchange="changeDate(0,obj)" id="_endDate" class="input Wdate" onclick="WdatePicker()" readonly="readonly" style="cursor: pointer;width: 90px;"/>
       			<span id="_alertSpan" style="color:red;display:none;"></span>
      		  </div>
      		  <div class="_super">
           		<label>APP：</label>
           		<a href="javascript:void(0)" class="capp _appred" onclick="changeApp(0,this)">全部</a>
           		<span id="showAppDiv" style="margin-right: 0;">
           		</span>
           		<div style="float: right;" id="showMore">
           			<a href="javascript:void(0)" onclick="showSuper(this,'appList')">更多 <img id="sortCreateDate" src="/celloud/images/report/descending.png"></a>
           		</div>
           		<div id="appList" class="none">
           		
           		</div>
           	  </div>
      		  <div class="_super">
           		<span id="_alertSpan" style="color:red;display:none;"></span>
      			<label>数据：</label>
      			<input type="text" id="_fileName" class="input" style="width: 150px;" onchange="changeFileName()"/>
      			<a href="javascript:void(0)" class="btn" onclick="submitSearch()">检索</a>
      		  </div>
           	</div>
            <div class="clearfix tablebox" id="selfReportDiv">

            </div>
    	</div>
    	<div class="tab-pane" id="tab2"> 
         <!--选择文件-->
            <div class="m-fileList">
            	<div><span>项目名称：<span id="proforReport">14141243793749</span></span><span id="fileNameH4"></span></div>
                <ul id="fileListUl">
                	<li><a href="#" class="forward">prev</a></li>
                	
                	<li class="active"><a href="#"><span>1</span>A572_muF2.ab1</a></li>
                	<li><a href="#"><span>2</span>A572_muF2.ab1</a></li>
                	<li><a href="#"><span>3</span>A572_muF2.ab1</a></li>
                	<li><a href="#"><span>4</span>A572_muF2.ab1</a></li>
                	<li><a href="#"><span>5</span>A572_muF2.ab1</a></li>
                	<li><a href="#"><span>6</span>A572_muF2.ab1</a></li>
                	<li><a href="#" class="backward">next</a></li>
                </ul>
            </div>
            <div id="reportResultDiv">
            
            </div>
      	</div>
    </div>
</div>
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
<script type="text/javascript">
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
