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
		<li class="active">数据量月统计</li>
	</ul><!-- .breadcrumb -->
</div>

<div class="page-content">
	<div class="row">
		<div class="col-xs-12">
			<h3 class="header smaller lighter blue">
				<span onclick="getMonthDataList()">总数据量月统计</span>
				<small id="secondTitle" class="hide">
					<i class="icon-double-angle-right"></i>
					<span id="_month"></span>用户上传数据量
				</small>
			</h3>
			<div class="table-header hide" id="_companyName">
				
			</div>
			<div class="table-responsive" id="dataDiv">
				<table id="MonthDataList" class="table table-striped table-bordered table-hover">
					<thead>
						<tr>
							<th>上传月份</th>
							<th>数据量(个)</th>
						</tr>
					</thead>
				
					<tbody>
						<s:if test="%{list.size()>0}">
							<s:iterator id="data" value="list">
								<tr>
									<td><a href="javascript:getAlluserDataAMonth('${data.month }')">${data.month }</a></td>
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
<script type="text/javascript">
	jQuery(function($) {
		var oTable1 = $('#MonthDataList').dataTable( {
		"aoColumns": [
	      null,null
		],
		iDisplayLength: 100
		} );
		
		
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
	function getAlluserDataAMonth(month){
		$("#secondTitle").removeClass("hide");
		$("#_month").html(month);
		$.get("data!getUserDataInMonth",{"month":month},function(responseText){
			$("#dataDiv").html(responseText);
		});
	}
</script>
