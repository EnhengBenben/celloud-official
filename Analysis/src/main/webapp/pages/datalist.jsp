<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<div class="breadcrumbs" id="breadcrumbs">
	<script type="text/javascript">
		try {
			ace.settings.check('breadcrumbs', 'fixed')
		} catch (e) {
		}
	</script>

	<ul class="breadcrumb">
		<li><i class="icon-tasks"></i> <a href="#">数据统计</a></li>
		<li class="active">总用户数据量</li>
	</ul>
	<!-- .breadcrumb -->
</div>

<div class="page-content">
	<div class="row">
		<h3 class="header smaller lighter blue">
			<span onclick="getUserDataList()">总用户数据量</span> <input type="hidden"
				id="hideUserId"> <small id="secondTitle" class="hide">
				<i class="icon-double-angle-right"></i> <a
				href="javascript:getUserMonthData()"><span id="_username"></span>每月数据量</a>
			</small> <small id="thirdTitle" class="hide"> <i
				class="icon-double-angle-right"></i> <span id="_month"></span>明细
			</small>
		</h3>
		<div class="col-xs-12" style="height: 450px;" id="echartView"></div>
		<div style="htight: 10px"></div>
		<div class="col-xs-11"  style=" margin-left:60px;margin-top:15px">
			<div class="table-header hide" id="_companyName"></div>
			<div class="table-responsive" id="dataDiv">
				<table id="allUserDataList"
					class="table table-striped table-bordered table-hover">
					<thead>
						<tr>
							<th>用户名</th>
							<th>所在医院</th>
							<th class="hidden-480">数据量(个)</th>
							<th class="hidden-480">数据大小(GB)</th>
						</tr>
					</thead>

					<tbody>
						<s:if test="%{list.size()>0}">
							<s:iterator id="data" value="list">
								<tr>
									<td><a
										href="javascript:userMonthDataList('${data.user_id }','${data.username }','${data.company_name }');">${data.username }</a></td>
									<td>${data.company_name }</td>
									<td>${data.num }</td>
									<td><fmt:formatNumber
											value="${data.size/(1024*1024*1024)}" pattern="#00.0#" /></td>
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
	var getUserDataJson = 'getUserDataJson";'
	$.get("data!" + getUserDataJson, {}, function(data) {
		var xAxis = new Array(data.length);
		var yAxis = new Array(data.length);
		var yAxisSize = new Array(data.length);
		for (var i = 0; i < data.length; i++) {
			xAxis[i] = data[i].username;
			yAxis[i] = data[i].num;
			yAxisSize[i] = (parseFloat(data[i].size / (1024 * 1024 * 1024)).toFixed(2));
		}
		option = {
			title : {
				text : '',
				subtext : ''
			},
			tooltip : {
				trigger : 'axis',
				axisPointer : { // 坐标轴指示器，坐标轴触发有效
					type : 'shadow' // 默认为直线，可选为：'line' | 'shadow'
				}
			},
			legend : {
				data : [ '文件个数', '数据大小(GB)' ]
			},
			toolbox : {
				show : true,
				feature : {
					dataView : {
						show : true,
						readOnly : false
					},
					magicType : {
						show : true,
						type : [ 'line', 'bar' ]
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
			dataZoom : {
				show : true,
				realtime : true,
				start : 50,
				end : 100
			},
			xAxis : [ {
				type : 'category',
				data : xAxis,
				scale:true,
				lenght:15,
				interval:0,
				axisLabel : {
					rotate : 60
				}
			} ],
			yAxis : [ {
				type : 'value'
			}, {
				type : 'value',
				name : '大小',
				axisLabel : {
					formatter : '{value} (MB)'
				}
			} ],
			series : [ {
				name : '文件个数',
				type : 'bar',
				data : yAxis,
				itemStyle : {
					normal : {
						label : {
							show : true
						}
					}
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
				markLine : {
					data : [ {
						type : 'average',
						name : '平均值'
					} ]
				}
			}, {
				name : '数据大小(GB)',
				type : 'bar',
				data : yAxisSize,
				itemStyle : {
					normal : {
						label : {
							show : true
						}
					}
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
				markLine : {
					data : [ {
						type : 'average',
						name : '平均值'
					} ]
				}
			} ]
		};
		var myChart = echarts.init(document.getElementById('echartView'));
		myChart.setOption(option);
	});

	jQuery(function($) {
		var oTable1 = $('#allUserDataList').dataTable({
			"aoColumns" : [ null, null, null ],
			iDisplayLength : 100
		});

		$('table th input:checkbox').on(
				'click',
				function() {
					var that = this;
					$(this).closest('table').find(
							'tr > td:first-child input:checkbox').each(
							function() {
								this.checked = that.checked;
								$(this).closest('tr').toggleClass('selected');
							});

				});

		$('[data-rel="tooltip"]').tooltip({
			placement : tooltip_placement
		});
		function tooltip_placement(context, source) {
			var $source = $(source);
			var $parent = $source.closest('table')
			var off1 = $parent.offset();
			var w1 = $parent.width();

			var off2 = $source.offset();
			var w2 = $source.width();

			if (parseInt(off2.left) < parseInt(off1.left) + parseInt(w1 / 2))
				return 'right';
			return 'left';
		}
	})
	function userMonthDataList(userId, userName, company) {
		$("#_username").html(userName);
		$("#_companyName").html(company);
		$("#secondTitle").removeClass("hide");
		$("#_companyName").removeClass("hide");
		$("#hideUserId").val(userId);
		getUserMonthData(userId);
	}
	function getUserMonthData(userId) {
		$("#thirdTitle").addClass("hide");
		var userId = $("#hideUserId").val();
		$.get("data!getUserMonthData", {
			"userId" : userId
		}, function(responseText) {

			$("#dataDiv").html(responseText);
		})
	}
</script>
