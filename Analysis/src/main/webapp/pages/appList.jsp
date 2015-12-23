<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<div class="breadcrumbs" id="breadcrumbs">
	<script type="text/javascript">
		try {
			ace.settings.check('breadcrumbs', 'fixed');
		} catch (e) {
		}
	</script>
	<ul class="breadcrumb">
		<li><i class="icon-cloud"></i> <a href="#">APP统计</a></li>
		<li class="active">APP详细信息</li>
	</ul>
	<!-- .breadcrumb -->
</div>
<div class="page-content">
	<div class="row">
		<div class="col-xs-12">
			<h3 class="header smaller lighter blue">
				<span onclick="toAPPList()">APP详细信息</span> <small id="secondTitle"
					class="hide"> <i class="icon-double-angle-right"></i> <span
					id="_oneApp"></span>
				</small>
			</h3>
			<div class="col-sm-10" style="height: 350px;" id="echartView"></div>
			<div class="table-header hide" id="_appName"></div>
			<div class="col-xs-11 table-responsive "  style=" margin-left:60px;margin-top:15px" id="appListDiv">
				<table id="appList"
					class="table table-striped table-bordered table-hover">
					<thead>
						<tr>
							<th>APP名称</th>
							<th class="min-w-80">运行次数</th>
							<th class="min-w-80">人气指数</th>
							<th class="min-w-80">软件类型</th>
							<th class="min-w-110">最小数据个数</th>
							<th class="min-w-80">数据格式</th>
							<th class="w160">上线时间</th>
							<th class="min-w-110">描述</th>
						</tr>
					</thead>

					<tbody>
						<s:if test="%{appList.size()>0}">
							<s:iterator id="data" value="appList">
								<tr>
									<td><a
										href="javascript:getAppDetail(${data.softwareId },'${data.softwareName }')">${data.softwareName }</a></td>
									<td>${data.runNum }</td>
									<td>${data.bhri }</td>
									<td>${data.type }</td>
									<td>${data.dataNum }</td>
									<td>${data.dataType }</td>
									<td><fmt:formatDate type="both"
											value="${data.createDate }" /></td>
									<td>${data.description }</td>
								</tr>
							</s:iterator>
						</s:if>
					</tbody>
				</table>
			</div>
			<!-- PAGE CONTENT ENDS -->
		</div>
		<!-- /.col -->
	</div>
	<!-- /.row -->
</div>
<!-- /.page-content -->
<script type="text/javascript">
	$.get("app!getAppListByBigUserJson", {}, function(res) {
		var xAxis = new Array(res.length);
		var runNum = new Array(res.length);
		var bhri = new Array(res.length);
		var dataNum = new Array(res.length);
		
		for (var i = 0; i < res.length; i++) {
			xAxis[i] = res[i].softwareName;
			runNum[i] = res[i].runNum;
			bhri[i] = res[i].bhri;
			dataNum[i] = res[i].dataNum;
		}
		option = {
			tooltip : {
				trigger : 'axis',
				axisPointer : { // 坐标轴指示器，坐标轴触发有效
					type : 'shadow' // 默认为直线，可选为：'line' | 'shadow'
				}
			},
			legend : {
				data : [ '运行次数', '人气指数', '最小数据个数' ]
			},
			toolbox : {
				show : true,
				feature : {
					magicType : {
						show : true,
						type : [ 'line', 'bar', 'stack', 'tiled' ]
					},
					restore : {
						show : true
					},
					saveAsImage : {
						show : true
					}
				}
			},
			calculable : true,
			xAxis : [ {
				type : 'category',
				data : xAxis,
				axisLabel : {
					rotate : 60
				}
			} ],
			yAxis : [ {
				type : 'value',
			} ],
			series : [ {
				name : '运行次数',
				type : 'bar',
				data : runNum,
				markLine : {
					data : [ {
						type : 'average',
						name : '平均值'
					} ]
				},
				markPoint : {
					data : [ {
						type : 'max',
						name : '最大值'
					}, {
						type : 'min',
						name : '最小值'
					} ]
				},
			}, {
				name : '人气指数',
				type : 'bar',
				data : bhri,
				markLine : {
					data : [ {
						type : 'average',
						name : '平均值'
					} ]
				},
				markPoint : {
					data : [ {
						type : 'max',
						name : '最大值'
					} ]
				},
			}, {
				name : '最小数据个数',
				type : 'bar',
				data : dataNum
			} ]
		};
		var myChart = echarts.init(document.getElementById('echartView'));
		myChart.setOption(option);
	});
	jQuery(function($) {
		var oTable1 = $('#appList').dataTable({
			// 			bFilter:false,
			"aoColumns" : [ {
				"bSearchable" : true,
				"aTargets" : [ 0 ]
			}, {
				"bSearchable" : false,
				"aTargets" : [ 0 ]
			}, {
				"bSearchable" : false,
				"aTargets" : [ 0 ]
			}, {
				"bSearchable" : false,
				"aTargets" : [ 0 ]
			}, {
				"bSearchable" : false,
				"aTargets" : [ 0 ]
			}, {
				"bSearchable" : false,
				"aTargets" : [ 0 ]
			}, {
				"bSearchable" : false,
				"aTargets" : [ 0 ]
			}, {
				"bSearchable" : false,
				"aTargets" : [ 0 ]
			} ],
			iDisplayLength : 100

		});
	})
	function getAppDetail(id, name) {
		$("#_oneApp").html(name);
		$("#secondTitle").removeClass("hide");
		$.get("app!getAppById", {
			"app.softwareId" : id
		}, function(responseText) {
			$("#appListDiv").html(responseText);
		});
	}

	
</script>