<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<div class="breadcrumbs" id="breadcrumbs">
	<script type="text/javascript">
		try{ace.settings.check('breadcrumbs' , 'fixed')}catch(e){}
	</script>

	<ul class="breadcrumb">
		<li>
			<i class="icon-home home-icon"></i>
			<a href="#">数据统计</a>
		</li>
		<li class="active">全部用户数据量</li>
	</ul><!-- .breadcrumb -->
</div>

<div class="page-content">
	<div class="row">
		<div class="col-xs-12">
			<h3 class="header smaller lighter blue">
				<span onclick="getUserDataList()">全部用户的数据量统计</span>
				<input type="hidden" id="hideUserId">
				<small id="secondTitle" class="hide">
					<i class="icon-double-angle-right"></i>
					<a href="javascript:getUserMonthData()"><span id="_username"></span>每月数据量</a>
				</small>
				<small id="thirdTitle" class="hide">
					<i class="icon-double-angle-right"></i>
					<span id="_month"></span>明细
				</small>
			</h3>
			<div class="table-header hide" id="_companyName">
				
			</div>
			<div class="table-responsive" id="dataDiv">
				<table id="allUserDataList" class="table table-striped table-bordered table-hover">
					<thead>
						<tr>
							<th>用户名</th>
							<th>所在医院</th>
							<th class="hidden-480">数据量(个)</th>
						</tr>
					</thead>

					<tbody>
						<s:if test="%{list.size()>0}">
							<s:iterator id="data" value="list">
								<tr>
									<td><a href="javascript:userMonthDataList(${data.userId },'${data.userName }','${data.companyName }');">${data.userName }</a></td>
									<td>${data.companyName }</td>
									<td>${data.num }</td>
								</tr>
							</s:iterator>
						</s:if>
					</tbody>
				</table>
			</div>
		<!-- PAGE CONTENT ENDS -->
		</div><!-- /.col -->
	</div><!-- /.row -->
</div><!-- /.page-content -->
<script src="./js/jquery.dataTables.min.js"></script>
<script src="./js/jquery.dataTables.bootstrap.js"></script>
<!-- inline scripts related to this page -->

<script type="text/javascript">
	jQuery(function($) {
		var oTable1 = $('#allUserDataList').dataTable( {
		"aoColumns": [
	      null,null,null
		] } );
		
		
		$('table th input:checkbox').on('click' , function(){
			var that = this;
			$(this).closest('table').find('tr > td:first-child input:checkbox')
			.each(function(){
				this.checked = that.checked;
				$(this).closest('tr').toggleClass('selected');
			});
				
		});
	
	
		$('[data-rel="tooltip"]').tooltip({placement: tooltip_placement});
		function tooltip_placement(context, source) {
			var $source = $(source);
			var $parent = $source.closest('table')
			var off1 = $parent.offset();
			var w1 = $parent.width();
	
			var off2 = $source.offset();
			var w2 = $source.width();
	
			if( parseInt(off2.left) < parseInt(off1.left) + parseInt(w1 / 2) ) return 'right';
			return 'left';
		}
	})
	function userMonthDataList(userId,userName,company){
		$("#_username").html(userName);
		$("#_companyName").html(company);
		$("#secondTitle").removeClass("hide");
		$("#_companyName").removeClass("hide");
		$("#hideUserId").val(userId);
		getUserMonthData();
	}
	function getUserMonthData(){
		$("#thirdTitle").addClass("hide");
		var userId = $("#hideUserId").val();
		$.get("data!getUserMonthData",{"userId":userId},function(responseText){
			$("#dataDiv").html(responseText);
		})
	}
</script>
